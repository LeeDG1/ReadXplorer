package de.cebitec.vamp.transcriptionAnalyses;

import de.cebitec.vamp.api.objects.AnalysisI;
import de.cebitec.vamp.databackend.connector.ProjectConnector;
import de.cebitec.vamp.databackend.connector.ReferenceConnector;
import de.cebitec.vamp.databackend.connector.TrackConnector;
import de.cebitec.vamp.databackend.dataObjects.MappingResultPersistant;
import de.cebitec.vamp.databackend.dataObjects.PersistantFeature;
import de.cebitec.vamp.databackend.dataObjects.PersistantMapping;
import de.cebitec.vamp.transcriptionAnalyses.dataStructures.RPKMvalue;
import de.cebitec.vamp.util.FeatureType;
import de.cebitec.vamp.util.Observer;
import de.cebitec.vamp.util.polyTree.Node;
import de.cebitec.vamp.util.polyTree.NodeVisitor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Martin Tötsches, Rolf Hilker <rhilker at cebitec.uni-bielefeld.de>
 */
public class AnalysisRPKM implements Observer, AnalysisI<List<RPKMvalue>> {
    
    private TrackConnector trackConnector;
    private List<RPKMvalue> rpkmValues;
    private int refSeqLength;
    private List<PersistantFeature> genomeFeatures;
    private HashMap<Integer, RPKMvalue> featureReadCount;
    private double totalMappedReads = 0;
    
    private int lastMappingIdx;
    private int currentCount;
    private double geneExonLength;
    private double noFeatureReads;
    private final ParameterSetRPKM parametersRPKM;
    private Map<FeatureType, Integer> featureCountMap;
    private int noSelectedFeatures;
    
    public AnalysisRPKM(TrackConnector trackConnector, ParameterSetRPKM parametersRPKM) {
        this.trackConnector = trackConnector;
        this.rpkmValues = new ArrayList<>();
        this.featureReadCount = new HashMap<>();
        this.lastMappingIdx = 0;
        this.parametersRPKM = parametersRPKM;
        this.initDatastructures();
    }
    
    /**
     * Initializes the genome features and all corresponding data structures.
     */
     private void initDatastructures() {
        ReferenceConnector refConnector = ProjectConnector.getInstance().getRefGenomeConnector(trackConnector.getRefGenome().getId());
        this.refSeqLength = this.trackConnector.getRefSequenceLength();
        this.genomeFeatures = refConnector.getFeaturesForClosedInterval(0, refSeqLength);  
        PersistantFeature.Utils.addParentFeatures(genomeFeatures);
        
        this.featureCountMap = this.fillInFeatureTypes();
        
        for (PersistantFeature feature : this.genomeFeatures) {
            this.featureReadCount.put(feature.getId(), new RPKMvalue(feature, 0, 0, trackConnector.getTrackID()));
            if (parametersRPKM.getSelFeatureTypes().contains(feature.getType())) {
                featureCountMap.put(feature.getType(), featureCountMap.get(feature.getType()) + 1);
            }
        }
        this.calcNoGenomeFeatures();
    }
    
    @Override
    public void update(Object data) {
         MappingResultPersistant mappingResult = new MappingResultPersistant(null, 0, 0);
        
        if (data.getClass() == mappingResult.getClass()) {
            List<PersistantMapping> mappings = ((MappingResultPersistant) data).getMappings();
            this.updateReadCountForFeatures(mappings);
        } else
        if (data instanceof Byte && ((Byte) data) == 2) { //2 means mapping analysis is finished
            this.calculateRPKMvalues();
//            for (RPKMvalue rpkm : rpkmValues) {
//                if (rpkm.getRPKM() != 0) {
//                System.out.println("Feature: " + rpkm.getFeature().getFeatureName());
//                System.out.println("RPKM: " + rpkm.getRPKM());
//                }
//            }
        }
    }

    @Override
    public List<RPKMvalue> getResults() {
        return this.rpkmValues;
    }
    
    /**
     * Updates the read count for all features in the genomeFeatures list by
     * all mappings in the mappings list.
     * @param mappings the list of mappings to add to the feature count
     */
    public void updateReadCountForFeatures(List<PersistantMapping> mappings) {
            PersistantFeature feature;
            boolean fstFittingMapping;
            
            for (int i = 0; i < this.genomeFeatures.size(); ++i) {
                feature = this.genomeFeatures.get(i);
                int featStart = feature.getStart();
                int featStop = feature.getStop();
                fstFittingMapping = true;

                for (int j = this.lastMappingIdx; j < mappings.size(); ++j) {
                    PersistantMapping mapping = mappings.get(j);

                    //mappings identified within a feature
                    if (mapping.getStop() > featStart && feature.isFwdStrand() == mapping.isFwdStrand()
                            && mapping.getStart() < featStop) {

                        if (fstFittingMapping) {
                            this.lastMappingIdx = j;
                            fstFittingMapping = false;
                        }
                        this.currentCount += mapping.getNbReplicates();


                        //still mappings left, but need next feature
                    } else if (mapping.getStart() > featStop) {
                        if (fstFittingMapping) { //until now no mapping was found for current feature
                            lastMappingIdx = j; //even if next feature starts at same start position no mapping will be found until mapping index j 
                        }
                        break;
                    }
                }

                //store read count in feature
                this.featureReadCount.get(feature.getId()).setReadCount(this.featureReadCount.get(feature.getId()).getReadCount() + this.currentCount);
                this.totalMappedReads += this.currentCount;
                this.currentCount = 0;
            }
            
            this.lastMappingIdx = 0;
            //TODO: solution for more than one feature overlapping mapping request boundaries
    }
    
    /**
     * Calculates the RPKM value for a given feature/gene according to the formula
     * given in Mortazavi et al. 2008, Mapping and quantifying mammalian transcriptomes by RNA-Seq:
     * 
     * <br>R = 10^9 * C / (N * L) where 
     * <br>C = number of mappable reads for gene
     * <br>N = total number of mappable reads for experiment/data set
     * <br>L = sum of gene base pairs
     */
    public void calculateRPKMvalues() {
        /*
         * Hierarchy to use for nested features:
         * 1. Gene - use gene, if no other subfeatures are present
         * - mRNA - complete mRNA, this shall be used for RPKM calculation, if no exons are given
         * - Exon - use them for RPKM, if they are available (mostly only in eukaryotes)
         * - CDS - real coding region. They do not cover the whole exons, so this level can be excluded,
         * except when CDS is selected for output
         * In general: We calculate all RPKM values for all feature classes given. 
         * BUT: If we analyze a gene, we check for the subfeatures and output their RPKM. Same for mRNA or Exon
         * When e.g. only Exon or CDS is selected, we only calculate the separate RPKM values for the single
         * exons and CDS and DO NOT combine them, as we do for mRNA and Gene!
         * tRNA and rRNA are same level than mRNA
         */
        Set<FeatureType> selFeatureTypes = parametersRPKM.getSelFeatureTypes();
        PersistantFeature feature;
        double rpkm;
        int readCount;
        for (Integer id : this.featureReadCount.keySet()) {
            feature = this.featureReadCount.get(id).getFeature();
            readCount = this.featureReadCount.get(id).getReadCount();
            if (    selFeatureTypes.contains(feature.getType()) && 
                    readCount >= parametersRPKM.getMinReadCount() && 
                    readCount <= parametersRPKM.getMaxReadCount()) {
                
                geneExonLength = 0; //gene length or sum of the exon length of a gene in bp
                noFeatureReads = 0; //no read for the feature itself or for gene/mRNA of the corresponding exons
                
                //special handling of gene/mRNA/tRNA/rRNA - if they have exons, only the exon reads are counted
                if (    feature.getType() == FeatureType.GENE || feature.getType() == FeatureType.MRNA || 
                        feature.getType() == FeatureType.RRNA || feature.getType() == FeatureType.TRNA) {
                    this.calcFeatureData(feature, FeatureType.EXON);                   
                } 
                if (geneExonLength == 0 && feature.getType() == FeatureType.GENE) {
                    this.calcFeatureData(feature, FeatureType.MRNA);
                    if (geneExonLength == 0) { //if the gene has a rRNA or tRNA instead of an mRNA, we have to check this, too
                        this.calcFeatureData(feature, FeatureType.RRNA);
                    }
                    if (geneExonLength == 0) {
                        this.calcFeatureData(feature, FeatureType.TRNA);
                    }
                }
                
                /* calc gene/mRNA length/ sum of exon length for gene/mRNA for prokaryotes or
                 * gene where no exons are given and all other features */
                if (geneExonLength <= 0) { 
                    geneExonLength = (feature.getStop() - feature.getStart()); //feature length in bp
                    noFeatureReads = this.featureReadCount.get(id).getReadCount();
                }
                
                rpkm = 0;
                if (noFeatureReads > 0) {
                    rpkm = noFeatureReads * 1000000000 / (this.totalMappedReads * geneExonLength); 
                }//1000000000 = 1000000 -> normalization factor * 1000 -> factor for KB of exon length
                this.rpkmValues.add(new RPKMvalue(feature, rpkm, (int) noFeatureReads, trackConnector.getTrackID()));
            }
        }
    }

    /**
     * Calculates the feature type length of the given feature type for all
     * features downward in the feature tree hierarchy (top-down fashion).
     * @param feature the feature whose subfeature length and readcount is needed
     * @param type the feature type for which the length sum and total read count
     * is needed
     */
    private void calcFeatureData(PersistantFeature feature, final FeatureType type) {
        //calc length of all exons of the mRNA and number of reads mapped to exon regions
        feature.topDown(new NodeVisitor() {
            @Override
            public void visit(Node node) {
                PersistantFeature subFeature = (PersistantFeature) node;
                if (subFeature.getType() == type) {
                    geneExonLength += subFeature.getStop() - subFeature.getStart();
                    noFeatureReads += featureReadCount.get(subFeature.getId()).getReadCount();
                }
            }
        });
    }

    /**
     * @return Creates a mapping of each feature type to an integer.
     */
    private Map<FeatureType, Integer> fillInFeatureTypes() {
        Map<FeatureType, Integer> featureTypeMap = new HashMap<>();
        FeatureType[] allFeatTypes = FeatureType.values();
        for (int i = 0; i < allFeatTypes.length; ++i) {
            featureTypeMap.put(allFeatTypes[i], 0);
        }
        return featureTypeMap;
    }
    
    /**
     * Calculates the number of selected genome features of the analyzed reference
     * genome.
     */
    public void calcNoGenomeFeatures() {        
        Iterator<FeatureType> featIt = this.featureCountMap.keySet().iterator();
        this.noSelectedFeatures = 0;
        while (featIt.hasNext()) {
            this.noSelectedFeatures += this.featureCountMap.get(featIt.next());
        }
    }
    
    /**
     * @return the number of selected genome features of the analyzed reference
     * genome.
     */
    public int getNoGenomeFeatures() {
        return this.noSelectedFeatures;
    }
}
