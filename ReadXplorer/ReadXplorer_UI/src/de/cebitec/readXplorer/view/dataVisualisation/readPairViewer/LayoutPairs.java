package de.cebitec.readXplorer.view.dataVisualisation.readPairViewer;

import de.cebitec.readXplorer.databackend.dataObjects.PersistantMapping;
import de.cebitec.readXplorer.databackend.dataObjects.PersistantReadPair;
import de.cebitec.readXplorer.databackend.dataObjects.PersistantReadPairGroup;
import de.cebitec.readXplorer.util.FeatureType;
import de.cebitec.readXplorer.view.dataVisualisation.GenomeGapManager;
import de.cebitec.readXplorer.view.dataVisualisation.alignmentViewer.BlockContainer;
import de.cebitec.readXplorer.view.dataVisualisation.alignmentViewer.BlockI;
import de.cebitec.readXplorer.view.dataVisualisation.alignmentViewer.LayerI;
import de.cebitec.readXplorer.view.dataVisualisation.alignmentViewer.LayoutI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * A LayoutPairs holds all information to display for read pair alignments 
 * in different, non-overlapping layers.
 * 
 * @author rhilker
 */
public class LayoutPairs implements LayoutI {

    private int absStart;
    private int absStop;
    private ArrayList<LayerI> reverseLayers;
    private BlockContainer reverseBlockContainer;
    private final List<FeatureType> exclusionList;

    /**
     * Creates a new layout for read pairs.
     * @param absStart start of the interval
     * @param absStop end of the interval
     * @param readPairs all read pairs to add to the layout
     * @param exclusionList list of excluded feature types 
     */
    public LayoutPairs(int absStart, int absStop, Collection<PersistantReadPairGroup> readPairs, List<FeatureType> exclusionList) {
        this.absStart = absStart;
        this.absStop = absStop;
        this.reverseLayers = new ArrayList<>();
        this.reverseBlockContainer = new BlockContainer();
        this.exclusionList = exclusionList;

        this.createBlocks(readPairs);
        this.layoutBlocks(this.reverseLayers, this.reverseBlockContainer);
    }

    /**
     * Each read pair group gets one block.
     * @param readPairList read pairs in current interval
     */
    private void createBlocks(Collection<PersistantReadPairGroup> readPairList) {
        Iterator<PersistantReadPairGroup> groupIt = readPairList.iterator();
        while (groupIt.hasNext()) {
            PersistantReadPairGroup group = groupIt.next();
            List<PersistantReadPair> readPairs = group.getReadPairs();
            List<PersistantMapping> singleMappings = group.getSingleMappings();
            Iterator<PersistantReadPair> pairIt = readPairs.iterator();
            Iterator<PersistantMapping> singleIt = singleMappings.iterator();
            long start = Long.MAX_VALUE;
            long stop = Long.MIN_VALUE;
            boolean containsVisibleMapping = false;
            //handle pairs
            while (pairIt.hasNext()) {
                PersistantReadPair pair = pairIt.next();
                containsVisibleMapping = !inExclusionList(pair.getVisibleMapping()) || !inExclusionList(pair.getVisibleMapping2());

                if (containsVisibleMapping) {
                    // get start position
                    if (pair.getStart() > this.absStart && pair.getStart() < start) {
                        start = pair.getStart();
                    }

                    // get stop position
                    if (pair.getStop() < this.absStop && pair.getStop() > stop) {
                        stop = pair.getStop();
                    }
                }
            }

            //handle single mappings
            while (singleIt.hasNext()) {
                PersistantMapping mapping = singleIt.next();
                containsVisibleMapping = containsVisibleMapping ? containsVisibleMapping : inExclusionList(mapping);

                //update start position, if necessary
                if (mapping.getStart() > this.absStart && mapping.getStart() < start) {
                    start = mapping.getStart();
                }

                //update start position, if necessary
                if (mapping.getStop() < this.absStop && mapping.getStop() > stop) {
                    stop = mapping.getStop();
                }
            }

            start = start == Long.MAX_VALUE ? this.absStart : start;
            stop = stop == Long.MIN_VALUE ? this.absStop : stop;

            BlockI block = new BlockPair((int) start, (int) stop, group);
            this.reverseBlockContainer.addBlock(block);

        }
    }

    /**
     * Fills each single layer until all blocks were added from the block container
     * to the layer list
     * @param layers list of layers to add the blocks to
     * @param blocks block container to add to layers
     */
    private void layoutBlocks(ArrayList<LayerI> layers, BlockContainer blocks) {
        LayerI l;
        while (!blocks.isEmpty()) {
            l = new LayerPair();
            this.fillLayer(l, blocks);
            layers.add(l);
        }
    }

    /**
     * Fills a single layer with as many blocks as possible, while obeying to the
     * rule, that the blocks in one layer are not allowed to overlap.
     * @param l single layer to fill with blocks
     * @param blocks block container
     */
    private void fillLayer(LayerI l, BlockContainer blocks) {
        BlockI block = blocks.getNextByPositionAndRemove(0);
        int counter = 0;
        while (block != null) {
            counter++;
            l.addBlock(block);
            block = blocks.getNextByPositionAndRemove(block.getAbsStop() + 1);
        }
    }

    /**
     * @return Since all mappings are shown on the "reverse strand" aka below the
     * sequence bar, it only returns null!
     */
    @Override
    public Iterator<LayerI> getForwardIterator() {
//        return forwardLayers.iterator();
        return null;
    }

    /**
     * @return The iterator for the reverse layer
     */
    @Override
    public Iterator<LayerI> getReverseIterator() {
        return reverseLayers.iterator();
    }

    /**
     * @return null, because this implementation of Layout does not need a GenomeGapManager
     */
    @Override
    public GenomeGapManager getGenomeGapManager() {
        return null;
    }
    
    /**
     * Returns true if the type of the current mapping is in the exclusion list.
     * This means it should not be displayed.
     * @param m the mapping to test, if it should be displayed
     * @return true, if the mapping should be excluded from being displayed,
     * false otherwise
     */
    private boolean inExclusionList(PersistantMapping m) {
        return (m.getDifferences() == 0 && this.exclusionList.contains(FeatureType.PERFECT_COVERAGE))
                || (m.getDifferences() > 0 && m.isBestMatch() && this.exclusionList.contains(FeatureType.BEST_MATCH_COVERAGE))
                || (!m.isUnique() && this.exclusionList.contains(FeatureType.MULTIPLE_MAPPED_READ))
                || (m.getDifferences() > 0 && !m.isBestMatch() && this.exclusionList.contains(FeatureType.COMMON_COVERAGE));
    }
}