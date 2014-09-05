/* 
 * Copyright (C) 2014 Institute for Bioinformatics and Systems Biology, University Giessen, Germany
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.cebitec.readXplorer.parser.reference;

import de.cebitec.readXplorer.parser.ReferenceJob;
import de.cebitec.readXplorer.parser.common.ParsedChromosome;
import de.cebitec.readXplorer.parser.common.ParsedFeature;
import de.cebitec.readXplorer.parser.common.ParsedReference;
import de.cebitec.readXplorer.parser.common.ParsingException;
import de.cebitec.readXplorer.parser.reference.Filter.FeatureFilter;
import de.cebitec.readXplorer.util.classification.FeatureType;
import de.cebitec.readXplorer.util.Observer;
import de.cebitec.readXplorer.util.SequenceUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.broad.tribble.AbstractFeatureReader;
import org.broad.tribble.annotation.Strand;
import org.broad.tribble.bed.BEDCodec;
import org.broad.tribble.bed.BEDFeature;
import org.broad.tribble.bed.FullBEDFeature.Exon;
import org.broad.tribble.readers.LineIterator;

/**
 * Parses the sequence from a fasta file contained in the ReferenceJob and the
 * BED annotations from the BED file contained in the ReferenceJob.
 * 
 * @author Rolf Hilker <rhilker at mikrobio.med.uni-giessen.de>
 */
public class TribbleBEDParser implements ReferenceParserI {

    // File extension used by Filechooser to choose files to be parsed by this parser
    private static final String[] fileExtension = new String[]{"bed", "BED"};
    // name of this parser for use in ComboBoxes
    private static final String parserName = "BED file";
    private static final String fileDescription = "BED file";
    private ArrayList<Observer> observers = new ArrayList<>();
    
    /**
     * Parses the sequence from a fasta file contained in the ReferenceJob and
     * the BED annotations from the BED file contained in the ReferenceJob.
     * @param referenceJob the reference job containing the files
     * @param filter the feature filter to use (removes undesired features)
     * @return the parsed reference object with all parsed features
     * @throws ParsingException
     */
    @Override
    public ParsedReference parseReference(ReferenceJob referenceJob, FeatureFilter filter) throws ParsingException {
        
        FastaReferenceParser fastaParser = new FastaReferenceParser();
        ParsedReference refGenome = fastaParser.parseReference(referenceJob, filter);
        refGenome.setFeatureFilter(filter);
        final Map<String, ParsedChromosome> chromMap = CommonsRefParser.generateStringMap(refGenome.getChromosomes());
        //at first store all eonxs in one data structure and add them to the ref genome at the end
        Map<FeatureType, List<ParsedFeature>> featMap = new HashMap<>();

        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Start reading file  \"{0}\"", referenceJob.getFile());
        try {
            String parsedType;
            String locusTag;
            String product;
            int start;
            int stop;
            int strand;
            String ecNumber;
            String geneName;
            List<ParsedFeature> subFeatures;
            int subStart;
            int subStop;
            ParsedFeature currentFeature;
            ParsedChromosome currentChrom;
            List<Exon> exons;
            
            BEDCodec bedCodec = new BEDCodec(BEDCodec.StartOffset.ZERO);
            AbstractFeatureReader<BEDFeature, LineIterator> reader = AbstractFeatureReader.getFeatureReader(referenceJob.getFile().getAbsolutePath(), bedCodec, false);
            if (bedCodec.canDecode(referenceJob.getFile().getAbsolutePath())) {
                
                Object header = reader.getHeader(); //TODO: something to do with the header?

                Iterator<BEDFeature> featIt = reader.iterator();
                while (reader.hasIndex()) {
                    BEDFeature feat = featIt.next();

                    if (chromMap.containsKey(feat.getChr())) {
                        currentChrom = chromMap.get(feat.getChr());
                        subFeatures = new ArrayList<>();

                        start = feat.getStart();
                        stop = feat.getEnd();
                        geneName = feat.getName();
                        strand = feat.getStrand().equals(Strand.POSITIVE) ? SequenceUtils.STRAND_FWD : SequenceUtils.STRAND_REV;
                        locusTag = feat.getDescription();
                        ecNumber = feat.getDescription(); //TODO: check this and test it
                        product = feat.getDescription();
                        parsedType = feat.getType();

                        /* 
                         * If the type of the feature is unknown to readXplorer (see below),
                         * an undefined type is used.
                         */
                        FeatureType type = FeatureType.getFeatureType(parsedType);
                        if (type == FeatureType.UNDEFINED) {
                            this.notifyObservers(referenceJob.getFile().getName()
                                    + ": Using unknown feature type for " + parsedType);
                        }

                        exons = feat.getExons();
                        for (Exon exon : exons) {
                            subStart = exon.getCdStart();
                            subStop = exon.getCdEnd();
//                            exon.getNumber();
                            
                            subFeatures.add(new ParsedFeature(type, subStart, subStop, strand, locusTag, product, ecNumber, geneName, null, null));
                        }

//                        feat.getLink(); could be used in upcoming versions
//                        feat.getScore();
//                        feat.getColor();

                        currentFeature = new ParsedFeature(type, start, stop, strand, locusTag, product, ecNumber, geneName, subFeatures, null);
                        currentChrom.addFeature(currentFeature);

                    }
                }
            }
            
        } catch (Exception ex) {
            throw new ParsingException(ex);
        }
        
        return refGenome;
    }

    @Override
    public String getName() {
        return parserName;
    }

    @Override
    public String getInputFileDescription() {
        return fileDescription;
    }

    @Override
    public String[] getFileExtensions() {
        return fileExtension;
    }

    @Override
    public void registerObserver(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers(Object data) {
        for (Observer observer : this.observers) {
            observer.update(data);
        }
    }
    
}
