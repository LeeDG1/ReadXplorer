/*
 * OpenTrackPanel.java
 *
 * Created on 31.01.2011, 16:00:51
 */

package de.cebitec.vamp.view.dialogMenus;

import de.cebitec.vamp.databackend.connector.ProjectConnector;
import de.cebitec.vamp.databackend.connector.ReferenceConnector;
import de.cebitec.vamp.databackend.dataObjects.PersistantTrack;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jwinneba
 */
public class OpenTrackPanel extends javax.swing.JPanel {

    public final static long serialVersionUID = 724742798;
    private ReferenceConnector refGenConnector;

    /** Creates new form OpenTrackPanel */
    public OpenTrackPanel() {
        initComponents();
    }
    
    public OpenTrackPanel(long referenceID){
        refGenConnector = ProjectConnector.getInstance().getRefGenomeConnector(referenceID);
        initComponents();
    }

    private PersistantTrack[] fillList(){
        List<PersistantTrack> list = refGenConnector.getAssociatedTracks();

        PersistantTrack[] tracks = new PersistantTrack[list.size()];
        for(int i = 0; i < list.size(); i++){
            tracks[i] = list.get(i);
        }

        return tracks;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tracklist = new javax.swing.JList(this.fillList());

        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setViewportView(tracklist);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList tracklist;
    // End of variables declaration//GEN-END:variables

    public List<PersistantTrack> getSelectedTracks(){
        List<PersistantTrack> selectedTracks = new ArrayList<PersistantTrack>();

        Object[] trackArray = tracklist.getSelectedValues();
        for (Object track : trackArray) {
            selectedTracks.add((PersistantTrack) track);
        }

        return selectedTracks;
    }

}