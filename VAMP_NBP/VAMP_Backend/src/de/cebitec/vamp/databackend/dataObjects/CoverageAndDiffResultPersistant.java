package de.cebitec.vamp.databackend.dataObjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Able to store the result for coverage, diffs and gaps. Called persistant, because
 * it needs the persistant data types from its own package.
 *
 * @author Rolf Hilker <rhilker at cebitec.uni-bielefeld.de>
 */
public class CoverageAndDiffResultPersistant extends PersistantResult implements Serializable {
    
    /** important: this id is used, when saving a CoverageAndDiffResultPersistant in the objectcache
     * raise this number, if any change to the class structure will be made in the future */
    public static final long serialVersionUID = 42L;
    
    private PersistantCoverage coverage;
    private PersistantCoverage readStarts;
    private List<PersistantDiff> diffs;
    private List<PersistantReferenceGap> gaps;
    private boolean diffsAndGapsUsed;
    
    /**
     * Data storage for coverage, diffs and gaps.
     * @param coverage the coverage container to store. If it is not used, you can
     *      add an empty coverage container.
     * @param diffs the list of diffs to store, if they are not used, you can add null
     *      or an empty list.
     * @param gaps the list of gaps to store, if they are not use, you can add null
     *      or an empty list
     * @param diffsAndGapsUsed true, if this is a result from querying also diffs and gaps
     */
    public CoverageAndDiffResultPersistant(PersistantCoverage coverage, List<PersistantDiff> diffs, List<PersistantReferenceGap> gaps, 
            boolean diffsAndGapsUsed) {
        super(coverage.getLeftBound(), coverage.getRightBound());
        this.coverage = coverage;
        this.readStarts = null;
        this.diffs = diffs;
        this.gaps = gaps;
        this.diffsAndGapsUsed = diffsAndGapsUsed;
    }
    
    /**
     * @return the diffs, if they are stored. If they are not, 
     * the list is empty.
     */
    public List<PersistantDiff> getDiffs() {
        if (this.diffs != null) {
            return this.diffs;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * @return the gaps, if they are stored. If they are not, 
     * the list is empty.
     */
    public List<PersistantReferenceGap> getGaps() {
        if (this.gaps != null) {
            return this.gaps;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * @return the coverage container, if it is stored. If it is not, 
     * it returns an empty coverage container covering only 0.
     */
    public PersistantCoverage getCoverage() {
        if (this.coverage != null) {
            return this.coverage;
        } else {
            return new PersistantCoverage(0, 0);
        }
    }

    /**
     * @return the coverage object containing only the read start counts.
     */
    public PersistantCoverage getReadStarts() {
        return readStarts;
    }

    /**
     * @param readStarts The coverage object containing only the read start counts.
     */
    public void setReadStarts(PersistantCoverage readStarts) {
        this.readStarts = readStarts;
    }
    

    /**
     * @return true, if diffs and gaps were querried, false, if not
     */
    public boolean isDiffsAndGapsUsed() {
        return this.diffsAndGapsUsed;
    }       
}
