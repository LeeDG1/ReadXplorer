/*
 * OpenTrackPanelList.java
 *
 * Created on 05.12.2011, 15:04:48
 */
package de.cebitec.vamp.view.dialogMenus;

import de.cebitec.vamp.databackend.connector.ProjectConnector;
import de.cebitec.vamp.databackend.connector.ReferenceConnector;
import de.cebitec.vamp.databackend.dataObjects.PersistantTrack;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import org.japura.gui.CheckList;
import org.japura.gui.event.ListCheckListener;
import org.japura.gui.event.ListEvent;
import org.japura.gui.model.DefaultListCheckModel;
import org.japura.gui.model.ListCheckModel;
import org.openide.util.NbBundle;

/**
 * A JPanel which contains a list of tracks and displays them as a list of checkboxes and
 * is capable of returning the selected PersistantTracks.
 * 
 * @author rhilker
 */
public class OpenTrackPanelList extends javax.swing.JPanel {

    public final static long serialVersionUID = 724742799;
    private ReferenceConnector refGenConnector;
    private CheckList checkList;
    
    public final static String PROP_SELECTED_ITEMS = "PROP_SELECTED_ITEMS";
    
    /** Creates new form OpenTrackPanelList */
    public OpenTrackPanelList() {
        this.initComponents();
        this.initAdditionalComponents();
    }
    
    @Override
    public String getName() {
        return NbBundle.getMessage(OpenTrackPanelList.class, "OpenTrackPanelList.name");
    }

    /** 
     * A JPanel which contains a list of tracks and displays them as a list of checkboxes and
     * is capable of returning the selected PersistantTracks.
     * @param referenceID the id of the reference whose tracks should be displayed in the list.
     */
    public OpenTrackPanelList(int referenceID) {
        this.refGenConnector = ProjectConnector.getInstance().getRefGenomeConnector(referenceID);
        this.initComponents();
        this.initAdditionalComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    /**
     * Initializes the checkbox list of tracks and adds it to the panel.
     */
    private void initAdditionalComponents() {
        
        this.setLayout(new BorderLayout());
        PersistantTrack[] tracks = new PersistantTrack[0];
        tracks = refGenConnector.getAssociatedTracks().toArray(tracks);

        JScrollPane scrollPane = new JScrollPane();
        this.checkList = new CheckList();

        ListCheckModel model = new DefaultListCheckModel();
        for (PersistantTrack track : tracks) {
            model.addElement(track);
        }

        model.addCheck(0);
        this.checkList.setModel(model);
        scrollPane.setViewportView(this.checkList);
        this.add(scrollPane, BorderLayout.CENTER);
        this.checkList.getModel().addListCheckListener(new ListCheckListener() {

            @Override
            public void removeCheck(ListEvent le) {
                firePropertyChange(PROP_SELECTED_ITEMS, null, null);
            }

            @Override
            public void addCheck(ListEvent le) {
                firePropertyChange(PROP_SELECTED_ITEMS, null, null);
            }
        });
        
    }

    private final static Logger LOG = Logger.getLogger(OpenTrackPanelList.class.getName());
    
    /**
     * @return The tracks selected in the selection list.
     */
    public List<PersistantTrack> getSelectedTracks() {
        List<PersistantTrack> selectedTracks = new ArrayList<>();

        List<Object> trackArray = this.checkList.getModel().getCheckeds();
        for (Object track : trackArray) {
            if (track instanceof PersistantTrack) {
                selectedTracks.add((PersistantTrack) track);
            }
        }

        return selectedTracks;
    }
}