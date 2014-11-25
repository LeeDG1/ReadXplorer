package de.cebitec.readXplorer.databackend.dataObjects;

import de.cebitec.readXplorer.util.SequenceComparison;
import java.util.ArrayList;
import java.util.List;

/**
 * A SNP. The data structure for storing a basic SNP.
 *
 * @author ddoppmeier, jhess, rhilker
 */
public class Snp extends TrackChromResultEntry implements SnpI {
    
    private int position;
    private char base;
    private char refBase;
    private int aRate;
    private int cRate;
    private int gRate;
    private int tRate;
    private int nRate;
    private int gapRate;
    private int coverage;
    private int frequency;
    private SequenceComparison type;
    List<CodonSnp> codons;
    private SequenceComparison effect;
    private int gapOrderIndex;

    /**
     * A SNP. The data structure for storing a basic SNP.
     * @param position
     * @param trackId The track id of this SNP.
     * @param chromId The chromosome id of this SNP.
     * @param base
     * @param refBase
     * @param aRate
     * @param cRate
     * @param gRate
     * @param tRate
     * @param nRate
     * @param gapRate
     * @param coverage
     * @param frequency
     * @param type type can be among S = substitution, I = insertion, D = deletion, M = match according to
     *  Snp.SUB, Snp.INS, Snp.DEL, Snp.MATCH
     */
    public Snp(int position, int trackId, int chromId, char base, char refBase, int aRate, int cRate, 
                    int gRate, int tRate, int nRate, int gapRate, int coverage,
                    int frequency, SequenceComparison type) {
        this(position, trackId, chromId, base, refBase, aRate, cRate, gRate, tRate, nRate, gapRate, coverage, frequency, type, 0);
    }

    /**
     * A SNP. The data structure for storing a basic SNP.
     * @param position
     * @param trackId The track id of this SNP.
     * @param chromId The chromosome id of this SNP.
     * @param base
     * @param refBase
     * @param aRate
     * @param cRate
     * @param gRate
     * @param tRate
     * @param nRate
     * @param gapRate
     * @param coverage
     * @param frequency
     * @param type type can be among S = substitution, I = insertion, D = deletion, M = match according to
     *  Snp.SUB, Snp.INS, Snp.DEL, Snp.MATCH
     * @param gapOrderIndex 
     */
    public Snp(int position, int trackId, int chromId, char base, char refBase, int aRate, int cRate,
            int gRate, int tRate, int nRate, int gapRate, int coverage,
            int frequency, SequenceComparison type, int gapOrderIndex) {
        super(trackId, chromId);
        
        this.position = position;
        this.base = base;
        this.refBase = refBase;
        this.aRate = aRate;
        this.cRate = cRate;
        this.gRate = gRate;
        this.tRate = tRate;
        this.nRate = nRate;
        this.gapRate = gapRate;
        this.coverage = coverage;
        this.frequency = frequency;
        this.type = type;
        this.codons = new ArrayList<>();
        this.gapOrderIndex = gapOrderIndex;
    }

    
    @Override
    public int getPosition() {
        return position;
    }    
    
    @Override
    public String getBase() {
        return String.valueOf(base);
    }
    
    public String getRefBase() {
        return String.valueOf(refBase);
    }
    
    
    public int getARate() {
        return aRate;
    }
    
    public int getCRate() {
        return cRate;
    }
    
    public int getGRate() {
        return gRate;
    }
    
    public int getTRate() {
        return tRate;
    }
    
    public int getNRate() {
        return nRate;
    }
    
    public int getGapRate() {
        return gapRate;
    }
    
    @Override
    public int getCoverage() {
        return coverage;
    }
    
    public int getFrequency() {
        return frequency;
    }
    
    /**
     * @return One of the available enum types of SequenceComparison.
     */
    public SequenceComparison getType() {
        return type;
    }

    /**
     * @param codon Adds the codon to this snp object. Depending on the amount of
     * features, this genomic position is involved in, each SNP can contain more
     * than one codon. The codon contains an amino acid, its property and the
     * gene id the amino acid belongs to.
     */
    public void addCodon(CodonSnp codon) {
        this.codons.add(codon);
    }
    
    /**
     * @return the list of codon belonging to this snp object. Depending on the amount of
     * features, this genomic position is involved in, each SNP can contain more
     * than one codon. Each codon contains an amino acid, its property and the
     * gene id the amino acid belongs to.
     */
    public List<CodonSnp> getCodons() {
        return this.codons;
    }

    /**
     * @return effect type of the snp on the amino acid sequence. Might be empty
     *         if the snp position is not covered by a gene or the snp type is
     *         SequenceComparison.SUBSTITUTION (in this case each feature needs one
     *         effect tag, since the effect can vary between SequenceComparison.SUBSTITUTION
     *         and SequenceComparison.MATCH)
     */
    public SequenceComparison getEffect() {
        return effect;
        
    }

    /**
     * @param effect Set the effect type of the snp on the amino acid sequence if
     *               the snp type is not SequenceComparison.SUBSTITUTION.
     */
    public void setEffect(SequenceComparison effect) {
        this.effect = effect;
    }

    /**
     * @return The index of the gap at the given reference position. For diffs
     * this value is 0.
     */
    public int getGapOrderIndex() {
        return gapOrderIndex;
    }

    /**
     * Set the gapOrderIndex.
     * @param gapOrderIndex The index of the gap at the given reference
     * position. For diffs this value is 0.
     */
    public void setGapOrderIndex(int gapOrderIndex) {
        this.gapOrderIndex = gapOrderIndex;
    }

    @Override
    public String toString(){
        return "position: "+position+"\ttrack: "+this.getTrackId()+"\tchromosome: "+this.getChromId()+"\trefBase: "+refBase
                +"\taRate: "+aRate+"\tcRate: "+cRate+"\tgRate: "+gRate+ "\ttRate: "+tRate+"\tnRate: "+nRate
                +"\tgapRate: "+gapRate+"\tcoverage: "+coverage+"\tfrequency: "+frequency+"%\ttype: "+type;
    }

    @Override
    public int compareTo(SnpI other) {
        int value = 0;
        if (this.getPosition() < other.getPosition()) {
            value = -1;
        } else if (this.getPosition() > other.getPosition()) {
            value = 1;
        } else { //pos are equal
            value = 0;
        }
        return value;
    }

    
}