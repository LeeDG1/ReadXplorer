package de.cebitec.readXplorer.databackend.dataObjects;

import de.cebitec.readXplorer.util.ColorProperties;
import de.cebitec.readXplorer.util.ReadPairType;
import java.awt.Color;

/**
 * Creates a new persistant read pair. If both mappings of the pair are
 * visible the second mapping has to be added separately.
 * TODO: persistant objects vereinheitlichen, wo möglich
 * 
 * @author Rolf Hilker
 */
public class PersistantReadPair implements PersistantObject {

    private long readPairID;
    private long mapping2Id;
    private ReadPairType readPairType;
    private int readPairReplicates;
    private PersistantMapping visibleMapping;
    private PersistantMapping visiblemapping2;
    
    
    /**
     * Creates a new persistant read pair. If both mappings of the pair are
     * visible the second mapping has to be added separately.
     * @param readPairID id of the pair, will identify all mappings belonging to this pair id
     * @param mapping1ID id of mapping 1 of this pair
     * @param mapping2ID id of mapping 2 of this pair
     * @param readPairType type of the read pair (@see ReadPairClassifier constants with values: 0-6)
     * @param readPairReplicates number of replicates of this pair
     * @param visibleMapping currently visible mapping of the pair
     */
    public PersistantReadPair(long readPairID, long mapping1ID, long mapping2ID, ReadPairType readPairType, 
            int readPairReplicates, PersistantMapping visibleMapping) {
        this.readPairID = readPairID;
        this.mapping2Id = mapping1ID == visibleMapping.getId() ? mapping2ID : mapping1ID;
        this.readPairType = readPairType;
        this.readPairReplicates = readPairReplicates == 0 ? 1 : readPairReplicates;
        this.visibleMapping = visibleMapping;
    }
    
    /**
     * Creates a new persistant read pair. If both mappings of the pair are
     * visible the second mapping has to be added separately.
     * @param readPairID id of the pair, will identify all mappings belonging to
     * this pair id
     * @param mapping1ID id of mapping 1 of this pair
     * @param mapping2ID id of mapping 2 of this pair
     * @param readPairType type of the read pair (
     * @see ReadPairClassifier constants with values: 0-6)
     * @param readPairReplicates number of replicates of this pair
     * @param visibleMapping currently visible mapping of the pair
     * @param mate the mate of the visibleMapping = other read of the pair 
     */
    public PersistantReadPair(long readPairID, long mapping1ID, long mapping2ID, ReadPairType readPairType,
            int readPairReplicates, PersistantMapping visibleMapping, PersistantMapping mate) {
        this.readPairID = readPairID;
        this.mapping2Id = mapping1ID == visibleMapping.getId() ? mapping2ID : mapping1ID;
        this.readPairType = readPairType;
        this.readPairReplicates = readPairReplicates == 0 ? 1 : readPairReplicates;
        this.visibleMapping = visibleMapping;
        this.visiblemapping2 = mate;
    }

    public long getMapping2Id() {
        return mapping2Id;
    }

    public void setMapping2Id(long mapping2Id) {
        this.mapping2Id = mapping2Id;
    }

    @Override
    public long getId() {
        return readPairID;
    }

    public void setReadPairID(long readPairID) {
        this.readPairID = readPairID;
    }

    public int getReadPairReplicates() {
        return readPairReplicates;
    }

    public void setReadPairReplicates(int readPairReplicates) {
        this.readPairReplicates = readPairReplicates;
    }

    public ReadPairType getReadPairType() {
        return readPairType;
    }

    public void setReadPairType(ReadPairType readPairType) {
        this.readPairType = readPairType;
    }

    public PersistantMapping getVisibleMapping() {
        return visibleMapping;
    }

    public void setVisibleMapping(PersistantMapping visibleMapping) {
        this.visibleMapping = visibleMapping;
    }

    /**
     * @return If both mappings of the pair are visible, it returns the second mapping
     * otherwise it returns null
     */
    public PersistantMapping getVisibleMapping2() {
        return visiblemapping2;
    }

    /**
     * If both mappings of the pair are visible, set the second mapping by using this method
     * @param visiblemapping2  the second mapping of the pair
     */
    public void setVisiblemapping2(PersistantMapping visiblemapping2) {
        this.visiblemapping2 = visiblemapping2;
    }
    
    /**
     * @return start position of the visible mapping or, if both mappings of the
     * pair are visible, returns the smaller start position among both
     */
    public long getStart(){
        if (this.visiblemapping2 == null){
            return this.visibleMapping.getStart();
        } else {
            long start1 = this.visibleMapping.getStart();
            long start2 = this.visiblemapping2.getStart();
            return start1 < start2 ? start1 : start2;
        }
    }
    
    /**
     * @return stop position of the visible mapping or, if both mappings of the
     * pair are visible, returns the larger stop position among both
     */
    public long getStop(){
        if (this.visiblemapping2 == null){
            return this.visibleMapping.getStop();
        } else {
            long stop1 = this.visibleMapping.getStop();
            long stop2 = this.visiblemapping2.getStop();
            return stop1 > stop2 ? stop1 : stop2;
        }
    }

    /**
     * @return true, if this read pair already has a second visible mapping, false otherwise
     */
    public boolean hasVisibleMapping2() {
        return this.visiblemapping2 != null;
    }
    
    /**
     * Determines the color according to the type of a read pair.
     * @param type the type of the read pair
     * @return the color representing this read pair
     */
    public static Color determineReadPairColor(ReadPairType type) {
        Color blockColor;
        if (type == ReadPairType.PERFECT_PAIR || type == ReadPairType.PERFECT_UNQ_PAIR) {
            blockColor = ColorProperties.BLOCK_PERFECT;
        } else if (type == ReadPairType.UNPAIRED_PAIR) {
            blockColor = ColorProperties.BLOCK_UNPAIRED;
        } else {
            blockColor = ColorProperties.BLOCK_DIST_SMALL;
        }
        return blockColor;
    }
    
}
