package de.cebitec.vamp.genomeAnalyses;

/**
 * Data storage for all parameters associated with filtering annotations.
 *
 * @author Rolf Hilker <rhilker at cebitec.uni-bielefeld.de>
 */
public class ParameterSetCoveredAnnos {
    
    private int minCoveredPercent;
    private int minCoverageCount;

    /**
     * Data storage for all parameters associated with filtering annotations.
     * @param performFilterAnalysis true, if the filtering should be carries out
     * @param minCoveredPercent the minimum percent of an annotation that has to 
     *      be covered with at least minCoverageCount reads at each position
     * @param minCoverageCount the minimum coverage at a position to be counted
     *      as covered
     * 
     */
    public ParameterSetCoveredAnnos(int minCoveredPercent, int minCoverageCount) {
        this.minCoveredPercent = minCoveredPercent;
        this.minCoverageCount = minCoverageCount;
    }

    /**
     * @return the minimum percent of an annotation that has to be covered with
     * at least minCoverageCount reads at each position
     */
    public int getMinCoveredPercent() {
        return minCoveredPercent;
    }

    /**
     * Set the minimum percent of an annotation that has to be covered with at
     * least minCoverageCount reads at each position
     * @param minCoverageCount the minimum percent of an annotation that has to
     * be covered with at least minCoverageCount reads at each position
     */
    public void setMinCoveredPercent(int minCoveredPercent) {
        this.minCoveredPercent = minCoveredPercent;
    }

    /**
     * @return the minimum coverage at a position to be counted as covered
     */
    public int getMinCoverageCount() {
        return minCoverageCount;
    }

    /**
     * Sets the minimum coverage at a position to be counted as covered
     * @param minCoverageCount the minimum coverage at a position to be counted
     *      as covered
     */
    public void setMinCoverageCount(int minCoverageCount) {
        this.minCoverageCount = minCoverageCount;
    }
}
