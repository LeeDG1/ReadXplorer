package de.cebitec.vamp.view.dataVisualisation.seqPairViewer;

import de.cebitec.vamp.databackend.dataObjects.PersistantMapping;
import de.cebitec.vamp.databackend.dataObjects.PersistantSeqPairGroup;
import de.cebitec.vamp.databackend.dataObjects.PersistantSequencePair;
import de.cebitec.vamp.util.ColorProperties;
import de.cebitec.vamp.util.FeatureType;
import de.cebitec.vamp.util.Properties;
import de.cebitec.vamp.view.dataVisualisation.abstractViewer.AbstractViewer;
import de.cebitec.vamp.view.dataVisualisation.abstractViewer.PhysicalBaseBounds;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import org.openide.util.NbBundle;

/**
 * A BlockComponent represents one sequence pair and displays all mappings of the 
 * pair in the currently shown interval of the genome.
 * TODO: think about overlaps in pairs: enlarge layer height!
 * 
 * @author Rolf Hilker <rhilker at cebitec.uni-bielefeld.de>
 */
public class BlockComponentPair extends JComponent implements ActionListener {

    private static final long serialVersionUID = 1324672345;
    private static final float satAndBrightSubtrahend = 0.3f;
    
    /*
     * In order to be efficient this class holds its main information in 4 arraylists.
     * Therefore it is important to check that the rectangles representing mappings,
     * their corresponding colors and their mappingType are added in the SAME order 
     * to the lists, to have access to one mappings data via the same index in
     * all three arrays. The line list is a bit independent, since it is always grey.
     */
    
    private ArrayList<Rectangle> rectList = new ArrayList<>();
    private ArrayList<Color> colorList = new ArrayList<>();
    private ArrayList<Line2D> lineList = new ArrayList<>();
    private ArrayList<Color> pairColors = new ArrayList<>();
    
    private BlockPair block;
    private int length;
    private int height;
    private String pairType;
    private AbstractViewer parentViewer;
    private int phyLeft;
    private int phyRight;
    private float minSatAndBright;
    private static String COPY_SEQUENCE = NbBundle.getMessage(BlockComponentPair.class, "CopyRefSeq");
    private JPopupMenu copyMenu = new JPopupMenu();
    private SeqPairPopup seqPairPopup;

    public BlockComponentPair(BlockPair block, final AbstractViewer parentViewer, int height, float minSaturationAndBrightness) {
        this.block = block;
        this.height = height;
        this.parentViewer = parentViewer;
        this.minSatAndBright = minSaturationAndBrightness;
        this.pairType = this.determineSeqPairType(this.block);
        // component boundaries //
        PhysicalBaseBounds bounds = parentViewer.getPhysBoundariesForLogPos(block.getAbsStart());
        this.phyLeft = (int) bounds.getLeftPhysBound();
        this.phyRight = (int) parentViewer.getPhysBoundariesForLogPos(block.getAbsStop()).getRightPhysBound();
        this.length = phyRight - phyLeft;
        this.length = this.length < 3 ? 3 : this.length;
        
        this.setPopupMenu();
        this.calcMappingBoundaries();

        this.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (seqPairPopup == null) { createPopup(e); }
                    seqPairPopup.show(e.getComponent(), e.getX(), e.getY());
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    copyMenu.show(BlockComponentPair.this, e.getX(), e.getY());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //not in use
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //not in use
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                parentViewer.forwardChildrensMousePosition(e.getX(), BlockComponentPair.this);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //not in use
            }
        });

    }
    
    /**
     * Calculates the boundaries for each visible mapping and creates the visual
     * content of the BlockComponentPair
     */
    private void calcMappingBoundaries() {
        PersistantSeqPairGroup seqPairGroup = (PersistantSeqPairGroup) this.block.getPersistantObject();
        List<PersistantSequencePair> seqPairs = seqPairGroup.getSequencePairs();
        List<PersistantMapping> singleMappings = seqPairGroup.getSingleMappings();

        Color blockColor;
        PersistantSequencePair seqPair;
        PersistantMapping mapping;

        for (int i = 0; i < seqPairs.size(); ++i) {
            seqPair = seqPairs.get(i);
            mapping = seqPair.getVisibleMapping();
            if (!this.inExclusionList(seqPair.getSeqPairType())) {
                
                blockColor = this.determineBlockColor(seqPair);
                this.pairColors.add(blockColor);
                this.addRectAndItsColor(blockColor, mapping, false);

                if (seqPair.hasVisibleMapping2()) {
                    mapping = seqPair.getVisibleMapping2();
                    this.addRectAndItsColor(blockColor, mapping, true);
                }
                this.length = this.length < 6 ? 6 : this.length;
            }
        }

        if (!parentViewer.getExcludedFeatureTypes().contains(FeatureType.SINGLE_MAPPING)) {
            for (int i = 0; i < singleMappings.size(); ++i) {
                mapping = singleMappings.get(i);
                this.addRectAndItsColor(ColorProperties.BLOCK_UNPAIRED, mapping, false);
            }
        }
    }

    /**
     * @param seqPairType sequence pair type to check
     * @return true, if the given sequence typ is on the exclusion list
     */
    public boolean inExclusionList(short seqPairType) {
        List<FeatureType> excludedFeatureTypes = this.parentViewer.getExcludedFeatureTypes();
        FeatureType typeOfPair;
        if (seqPairType == Properties.TYPE_PERFECT_PAIR || 
              seqPairType == Properties.TYPE_PERFECT_UNQ_PAIR) {
            typeOfPair = FeatureType.PERFECT_PAIR;
        } else if (seqPairType == Properties.TYPE_DIST_LARGE_PAIR || 
                   seqPairType == Properties.TYPE_DIST_LARGE_UNQ_PAIR || 
                   seqPairType == Properties.TYPE_DIST_SMALL_PAIR || 
                   seqPairType == Properties.TYPE_DIST_SMALL_UNQ_PAIR || 
                   seqPairType == Properties.TYPE_ORIENT_WRONG_PAIR || 
                   seqPairType == Properties.TYPE_ORIENT_WRONG_UNQ_PAIR || 
                   seqPairType == Properties.TYPE_OR_DIST_LARGE_PAIR || 
                   seqPairType == Properties.TYPE_OR_DIST_LARGE_UNQ_PAIR || 
                   seqPairType == Properties.TYPE_OR_DIST_SMALL_PAIR || 
                   seqPairType == Properties.TYPE_OR_DIST_SMALL_UNQ_PAIR) {
            typeOfPair = FeatureType.DISTORTED_PAIR;
        } else {
            typeOfPair = FeatureType.SINGLE_MAPPING;
        }
        
        return excludedFeatureTypes.contains(typeOfPair);
    }

    /**
     * Determines the bounds of the rectangle representing the mapping and adjusts its color
     * depending on the mapping type. Also paints the line for a sequence pair, if 
     * both mappings are visible.
     * @param pairColor basic color of the current sequence pair
     * @param mapping mapping to create a colored rectangle for
     * @param addLine true if this is the second mapping of a pair and a connecting line is desired, false otherwise
     */
    private void addRectAndItsColor(Color pairColor, PersistantMapping mapping, boolean addLine) {
        this.colorList.add(this.adjustBlockColor(pairColor, mapping));
        int absStartMapping = (int) parentViewer.getPhysBoundariesForLogPos(mapping.getStart()).getLeftPhysBound();
        int absStopMapping = this.phyRight;
        if (mapping.getStop() < this.block.getAbsStop()) {
            absStopMapping = (int) parentViewer.getPhysBoundariesForLogPos(mapping.getStop()).getRightPhysBound();
        }
        int absLength = absStopMapping - absStartMapping;
        absLength = absLength < 3 ? 3 : absLength;
        this.rectList.add(new Rectangle(absStartMapping - this.phyLeft, 0, absLength, this.height));

        if (addLine) {
            Rectangle rect = rectList.get(rectList.size() - 2);
            int startMapping1 = rect.x;
            int startCurMapping = (absStartMapping - this.phyLeft);
            if (startMapping1 < startCurMapping){
                this.lineList.add(new Line2D.Double(startMapping1 + rect.width, 2, startCurMapping - 1, 2));
            } else { //endMapping2 < endMapping1
//                this.lineList.add(new Line2D.Double(rect.x - 1, 2, absStopMapping, 2));
                this.lineList.add(new Line2D.Double(startCurMapping + absLength, 2, startMapping1 - 1, 2));
            }
        }
    }

    /**
     * Adjusts the color of a mapping according to its mapping type.
     * @param blockColor old color of the block for the mapping
     * @param mapping mapping whose color needs to be adjusted
     * @return new color of the block for the mapping
     */
    private Color adjustBlockColor(Color blockColor, PersistantMapping mapping) {
        // order of addition to blockList and mappingTypeList has to be correct always!
        float hue = Color.RGBtoHSB(blockColor.getRed(), blockColor.getGreen(), blockColor.getBlue(), null)[0];
        if (mapping.getDifferences() == 0) {
            blockColor = Color.getHSBColor(hue, minSatAndBright, minSatAndBright);
        } else if (mapping.isBestMatch()) {
            blockColor = Color.getHSBColor(hue, minSatAndBright - satAndBrightSubtrahend, minSatAndBright - satAndBrightSubtrahend);
        } else {
            blockColor = Color.getHSBColor(hue, minSatAndBright - satAndBrightSubtrahend * 2, minSatAndBright - satAndBrightSubtrahend * 2);
        }
        return blockColor;
    }

    
    /**
     * Creates the popup menu for operating on the mapping (currently only 
     * copying the sequence).
     */
    private void setPopupMenu() {

        JMenuItem copySequence = new JMenuItem();
        copySequence.addActionListener(this);
        copySequence.setActionCommand(BlockComponentPair.COPY_SEQUENCE);
        copySequence.setText(BlockComponentPair.COPY_SEQUENCE);
        copySequence.setToolTipText(NbBundle.getMessage(BlockComponentPair.class, "CopyAttention"));

        this.copyMenu.add(copySequence);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
//        graphics.setFont(new Font(Font.MONOSPACED, Font.BOLD, 11));

        //fill pair background to indicate pair length
        graphics2D.setColor(ColorProperties.BLOCK_BACKGROUND);
        graphics2D.fill(new Rectangle2D.Double(0, 0, this.length, this.height));
        
        // paint this block's mappings
        for (int i = 0; i < this.rectList.size(); ++i) {
            graphics2D.setColor(this.colorList.get(i));
            graphics2D.fill(this.rectList.get(i));
        }

        //paint connecting lines for pairs
        graphics2D.setColor(ColorProperties.CURRENT_POSITION);
        for (int i = 0; i < this.lineList.size(); ++i) {
            graphics2D.draw(this.lineList.get(i));
        }
        
        //paint a bounding rectangle
        //graphics2D.draw(new Line2D.Double(0, this.height-1, this.length-1, this.height-1));

    }

    /**
     * Determines the color and type of a block.
     * @return the color representing this block
     */
    private Color determineBlockColor(PersistantSequencePair seqPair) {

        Color blockColor;
        int type = seqPair.getSeqPairType();
        if (type == Properties.TYPE_PERFECT_PAIR || type == Properties.TYPE_PERFECT_UNQ_PAIR) {
            blockColor = ColorProperties.BLOCK_PERFECT;
        } else if (type == Properties.TYPE_UNPAIRED_PAIR) {
            blockColor = ColorProperties.BLOCK_UNPAIRED;
        } else {
            blockColor = ColorProperties.BLOCK_DIST_SMALL;
        }
        return blockColor;

    }

    public int getPhyStart() {
        return phyLeft;
    }

    public int getPhyWidth() {
        return length;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(COPY_SEQUENCE)) {
            this.setSequence();
        }
    }

    public void setSequence() {
        JTextField j = new JTextField(); //TODO: return read sequence of currently clicked read, not the pair
        int start = (int) ((PersistantSequencePair) block.getPersistantObject()).getStart();
        int stop = (int) ((PersistantSequencePair) block.getPersistantObject()).getStop();
        //string first pos is zero
        String readSequence = parentViewer.getReference().getSequence().substring(start - 1, stop);
        j.setText(readSequence);
        j.selectAll();
        j.copy();
    }

    /**
     * Creates the popup displaying all information regarding this sequence pair
     * and allowing to jump to the position of other mappings of the pair.
     */
    private void createPopup(MouseEvent e) {
        this.seqPairPopup = new SeqPairPopup(this.parentViewer, this.pairType, this.pairColors, this.block);
    }

    /**
     * Determines the type string of the main sequence pair
     * @param block the block containing the sequence pair
     * @return the type string 
     */
    private String determineSeqPairType(BlockPair block) {
        String type = "Not a sequence pair object";
        if (block.getPersistantObject() instanceof PersistantSequencePair) {
            type = PersistantSequencePair.determineType(((PersistantSequencePair) block.getPersistantObject()).getSeqPairType());
        }
        return type;
    }
    
    /**
     * @return true, if this component contains rectangles to paint and thus is paintable;
     * false otherwise.
     */
    public boolean isPaintable(){
        return !this.rectList.isEmpty();
    }

}