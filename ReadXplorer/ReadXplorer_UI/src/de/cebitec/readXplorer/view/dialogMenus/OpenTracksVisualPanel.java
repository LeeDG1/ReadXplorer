package de.cebitec.readXplorer.view.dialogMenus;

import de.cebitec.readXplorer.api.objects.JobPanel;
import de.cebitec.readXplorer.databackend.connector.ProjectConnector;
import de.cebitec.readXplorer.databackend.connector.ReferenceConnector;
import de.cebitec.readXplorer.databackend.dataObjects.PersistantTrack;
import de.cebitec.readXplorer.view.dialogMenus.explorer.CustomOutlineCellRenderer;
import de.cebitec.readXplorer.view.dialogMenus.explorer.StandardItem;
import de.cebitec.readXplorer.view.dialogMenus.explorer.StandardNode;
import java.awt.BorderLayout;
import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.view.OutlineView;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 *
 * @author Rolf Hilker <rhilker at mikrobio.med.uni-giessen.de>
 */
public class OpenTracksVisualPanel extends JobPanel implements ExplorerManager.Provider {
    
    private static final long serialVersionUID = 1L;
    
    private ExplorerManager explorerManager;
    private OutlineView outlineView;
    private ReferenceConnector refGenConnector;
    private List<PersistantTrack> selectedTracks;

    /**
     * Creates new form OpenTracksVisualPanel
     * @param referenceID id of the reference genome
     */
    public OpenTracksVisualPanel(int referenceID) {
        this.selectedTracks = new ArrayList<>();
        this.refGenConnector = ProjectConnector.getInstance().getRefGenomeConnector(referenceID);
        this.explorerManager = new ExplorerManager();
        //Create the outline view showing the explorer
        this.outlineView = new OutlineView();
        this.initComponents();
        this.initAdditionalComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        trackListPanel = new javax.swing.JPanel();
        combineTracksBox = new javax.swing.JCheckBox();

        javax.swing.GroupLayout trackListPanelLayout = new javax.swing.GroupLayout(trackListPanel);
        trackListPanel.setLayout(trackListPanelLayout);
        trackListPanelLayout.setHorizontalGroup(
            trackListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        trackListPanelLayout.setVerticalGroup(
            trackListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );

        org.openide.awt.Mnemonics.setLocalizedText(combineTracksBox, org.openide.util.NbBundle.getMessage(OpenTracksVisualPanel.class, "OpenTracksVisualPanel.combineTracksBox.text")); // NOI18N
        combineTracksBox.setToolTipText(org.openide.util.NbBundle.getMessage(OpenTracksVisualPanel.class, "OpenTracksVisualPanel.combineTracksBox.toolTipText")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(trackListPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(combineTracksBox)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(trackListPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(combineTracksBox))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox combineTracksBox;
    private javax.swing.JPanel trackListPanel;
    // End of variables declaration//GEN-END:variables

    private void initAdditionalComponents() {
        trackListPanel.setLayout(new BorderLayout());
        
        Node rootNode = new AbstractNode(new RootChildren());
        explorerManager.setRootContext(rootNode);
        
        //Set the columns of the outline view
        //do not show the default property window
        //this outlineview is meant to be a read-only list
        outlineView.setDefaultActionAllowed(false);
        //using the name of the property 
        //followed by the text to be displayed in the column header: 
        outlineView.setPropertyColumns("selected", "Select");
        //Hide the root node, since we only care about the children: 
        outlineView.getOutline().setRootVisible(false); //Add the OutlineView to the TopComponent: 
        outlineView.getOutline().setDefaultRenderer(Node.Property.class, new CustomOutlineCellRenderer());
        
        trackListPanel.add(outlineView, BorderLayout.CENTER);
        outlineView.getOutline().getColumnModel().getColumn(1).setMaxWidth(50);
        //add listener for changes in the cell selection
        outlineView.getOutline().addMouseListener(this.createMouseClickListener(outlineView.getOutline(), 0));
        outlineView.getOutline().addMouseListener(this.createMouseClickListener(outlineView.getOutline(), 1));
    }

    @Override
    public String getName() {
        return "Track Selection";
    }

    @Override
    public ExplorerManager getExplorerManager() {
        return this.explorerManager;
    }
    
    /**
     * @return <cc>true</cc>, if the all selected tracks shall be combined,
     * <cc>false</cc> otherwise.
     */
    public boolean isCombineTracks() {
        return this.combineTracksBox.isSelected();
    }
    
    /**
     * Enables or disables the combine tracks checkbox. Standard value is true.
     * @param enabled true, if the combine tracks checkbox shall be enabled,
     * false otherwise
     */
    public void setCombineTracksEnabled(boolean enabled) {
        this.combineTracksBox.setVisible(enabled);
    }
    
    /**
     * @return The list of selected tracks from this panel.
     */
    public List<PersistantTrack> getSelectedTracks() {
        return this.selectedTracks;
    }
    
    /**
     * Stores all seleceted tracks in the internal selectedTracks list.
     */
    public void storeSelectedTracks() {
        List<PersistantTrack> trackList = new ArrayList<>();
        List<Node> markedNodes = this.getAllMarkedNodes();
        for (Node node : markedNodes) {
            StandardNode markedNode = (StandardNode) node;
            trackList.add(((TrackItem) markedNode.getData()).getTrack());
        }
        this.selectedTracks = trackList;
    }
    
    /** 
     * @return All nodes marked in the explorer manager.
     */
    public List<Node> getAllMarkedNodes() {
        List<Node> nodeList = Arrays.asList(explorerManager.getRootContext().getChildren().getNodes());
        nodeList = StandardNode.getAllMarkedNodes(nodeList);
//        nodeList.addAll(Arrays.asList(explorerManager.getSelectedNodes()));
//        Set<Node> uniqueNodes = new HashSet<>(nodeList); //ensures, that each track only occurs once!
        return nodeList; //new ArrayList<>(uniqueNodes)
    }

    @Override
    public boolean isRequiredInfoSet() {
        this.checkSelectedRowBoxes();
        
        boolean requiredInfoSet = this.getAllMarkedNodes().size() > 0;
        if (requiredInfoSet) { this.storeSelectedTracks(); }
        firePropertyChange(ChangeListeningWizardPanel.PROP_VALIDATE, null, requiredInfoSet);
        return requiredInfoSet;
    }

    /**
     * Checks the boxes of all currently selected nodes in the explorer.
     */
    private void checkSelectedRowBoxes() {
        Node[] selectedNodes = explorerManager.getSelectedNodes();
        for (int i = 0; i < selectedNodes.length; ++i) {
            if (selectedNodes[i] instanceof TrackNode) {
                StandardItem item = ((TrackNode) selectedNodes[i]).getData();
                if (item instanceof TrackItem) {
                    TrackItem trackItem = (TrackItem) item;
                    if (!selectedTracks.contains(trackItem.getTrack())) {
                        if (!item.getSelected()) {
                            item.setSelected(true);
                        }
                    }
                }
            }
        }
        outlineView.repaint();
    }

    /**
     *
     */
    public class TrackItem extends StandardItem {

        private PersistantTrack track;

        /**
         *
         * @param track
         */
        public TrackItem(PersistantTrack track) {
            super();
            this.track = track;
        }

        public PersistantTrack getTrack() {
            return this.track;
        }
    }

    /**
     * A node object for a track.
     */
    public class TrackNode extends StandardNode {

        public TrackNode(TrackItem bean) throws IntrospectionException {
            super(bean);
            this.setDisplayName(bean.getTrack().getDescription());
            setIconBaseWithExtension("de/cebitec/readXplorer/ui/visualisation/trackOpen.png");
        }
    }

    public class RootChildren extends Children.Keys<List<PersistantTrack>> {

        @Override
        protected Node[] createNodes(List<PersistantTrack> trackList) {
            Node[] trackNodes = new Node[trackList.size()];
            for (int i = 0; i < trackList.size(); i++) {
                try {
                    PersistantTrack track = trackList.get(i);
                    trackNodes[i] = new TrackNode(new TrackItem(track));
                } catch (IntrospectionException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
            return trackNodes;
        }

        @Override
        protected void addNotify() {
            super.addNotify();
            Collection<List<PersistantTrack>> list = new ArrayList<>();
            list.add(refGenConnector.getAssociatedTracks());
            this.setKeys(list);
        }
    }
}