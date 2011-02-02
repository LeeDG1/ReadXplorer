/*
 * OpenRefGenPanel.java
 *
 * Created on 31.01.2011, 14:43:58
 */

package de.cebitec.vamp.view.dialogPanels;

import de.cebitec.vamp.databackend.connector.ProjectConnector;
import de.cebitec.vamp.databackend.dataObjects.PersistantReference;
import java.util.List;

/**
 *
 * @author jwinneba
 */
public class OpenRefGenPanel extends javax.swing.JPanel {

    public static final long serialVersionUID = 792723463;

    private PersistantReference selectedReference;

    /** Creates new form OpenRefGenPanel */
    public OpenRefGenPanel() {
        initComponents();
    }

    /**
     * Get a list of available genomes from the database.
     * @return Array of genomes WITHOUT actual sequence
     */
    private PersistantReference[] fillList(){
        List<PersistantReference> gens = ProjectConnector.getInstance().getGenomes();

        PersistantReference[] genArray = new PersistantReference[gens.size()];
        for( int i = 0; i< gens.size() ; i++ ){
            genArray[i] = gens.get(i);
        }

        return genArray;
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
        refGenList = new javax.swing.JList(this.fillList());

        setLayout(new java.awt.BorderLayout());

        refGenList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        refGenList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                refGenListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(refGenList);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void refGenListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_refGenListValueChanged
        selectedReference = (PersistantReference) refGenList.getSelectedValue();
}//GEN-LAST:event_refGenListValueChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList refGenList;
    // End of variables declaration//GEN-END:variables

    public PersistantReference getSelectedReference() {
        return selectedReference;
    }

}
