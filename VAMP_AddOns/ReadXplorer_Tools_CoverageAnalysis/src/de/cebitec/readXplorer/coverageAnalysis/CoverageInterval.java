package de.cebitec.readXplorer.coverageAnalysis;

import de.cebitec.vamp.util.SequenceUtils;

/**
 * A coverage interval container.
 * 
 * @author Tobias Zimmermann, Rolf Hilker <rhilker at mikrobio.med.uni-giessen.de>
 */
public class CoverageInterval {

    private boolean isFwdStrand;
    private int trackId;
    private byte strand;
    private int start;
    private int stop;
    private int length;
    private int meanCoverage;
    
    /**
     * A complete coverage interval object.
     * @param trackId the track id of the track to which the interval belongs
     * @param strand the strand, for which the interval shall be created
     * @param start the start position of the interval
     * @param stop the stop position of the interval
     * @param meanCoverage the mean coverage of the interval
     */
    public CoverageInterval(int trackId, byte strand, int start, int stop, int meanCoverage) {
        this.trackId = trackId;
        this.strand = strand;
        this.start = start;
        this.stop = stop;
        this.length = stop - start;
        this.meanCoverage = meanCoverage;
        this.isFwdStrand = strand != SequenceUtils.STRAND_REV;
    }

    /**
     * Constructor for a blank coverage interval. Start and stop are -1, length
     * and meanCoverage are 0.
     * @param trackId the track id of the track to which the interval belongs
     * @param strand the strand, for which the interval shall be created
     */
    public CoverageInterval(int trackId, byte strand) {
        this(trackId, strand, -1, -1, 0);
    }

    /**
     * @return true for intervals on forward and false on reverse strand
     */
    public boolean isFwdStrand() {
        return this.isFwdStrand;
    }

    /**
     * @return The String representing the strand.
     */
    public String getStrandString() {
        String output = "no strand";
        if (strand == SequenceUtils.STRAND_BOTH) {
            output = SequenceUtils.STRAND_BOTH_STRING;
        } else if (strand == SequenceUtils.STRAND_FWD) {
            output = SequenceUtils.STRAND_FWD_STRING;
        } else if (strand == SequenceUtils.STRAND_REV) {
            output = SequenceUtils.STRAND_REV_STRING;
        }
        return output;

    }

    /**
     * @return the track id of the track to which the interval belongs
     */
    public int getTrackId() {
        return trackId;
    }

    /**
     * @return the start position of the interval
     */
    public int getStart() {
        return start;
    }

    /**
     * @return the stop position of the interval
     */
    public int getStop() {
        return stop;
    }

    /**
     * @return the length of the interval
     */
    public int getLength() {
        return length;
    }

    /**
     * @return the mean coverage of the interval
     */
    public int getMeanCoverage() {
        return meanCoverage;
    }

    /**
     * @param trackId the track id of the track to which the interval belongs
     */
    public void setTrack(int trackId) {
        this.trackId = trackId;
    }

    /**
     * @param strand the strand of this interval
     */
    public void setStrand(byte strand) {
        this.strand = strand;
    }

    /**
     * @param start the start position of the interval
     */
    public void setStart(int start) {
        this.start = start;
        this.updateLength();
    }

    /**
     * @param stop the stop position of the interval
     */
    public void setStop(int stop) {
        this.stop = stop;
        this.updateLength();
    }

    /**
     * @param meanCoverage the mean coverage of the interval
     */
    public void setMeanCoverage(int meanCoverage) {
        this.meanCoverage = meanCoverage;
    }
    
    /**
     * Updates the length of the interval.
     */
    private void updateLength() {
        this.length = this.stop + 1 - this.start;
    }
}
