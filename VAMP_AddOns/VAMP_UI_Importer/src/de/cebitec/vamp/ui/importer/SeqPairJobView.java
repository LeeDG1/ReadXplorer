/*
 * SeqPairJobView.java
 *
 * Created on 18.05.2011, 14:57:49
 */
package de.cebitec.vamp.ui.importer;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rolf Hilker
 */
public class SeqPairJobView extends javax.swing.JPanel implements ListSelectionListener {

    public final static long serialVersionUID = 774342377;
    private List<SeqPairJobContainer> seqPairJobContainerList;
    private boolean hasJobs;

    /** Creates new form TaskViewerTemplate */
    public SeqPairJobView() {
        seqPairJobContainerList = new ArrayList<SeqPairJobContainer>();
        initComponents();
    }

    public SeqPairJobContainer getSelectedItem() {
        return seqPairJobContainerList.get(trackTable.getSelectedRow());
    }

    public JTable add(SeqPairJobContainer seqPairJobContainer){
        DefaultTableModel model = (DefaultTableModel) trackTable.getModel();
        
        String orientation = "fr";
        if (seqPairJobContainer.getOrientation() == 1){ orientation = "rf"; }
        else if (seqPairJobContainer.getOrientation() == 2){ orientation = "ff/rr"; }
       
        model.addRow(new Object[]{
                seqPairJobContainer.getTrackJob1().getFile().getName(),
                seqPairJobContainer.getTrackJob2().getFile().getName(),
                seqPairJobContainer.getTrackJob1().getDescription(),
                seqPairJobContainer.getTrackJob1().getRefGen().getDescription(), 
                seqPairJobContainer.getDistance(),
                seqPairJobContainer.getDeviation(), 
                orientation});
        
        this.seqPairJobContainerList.add(seqPairJobContainer);

        if (!hasJobs){
            hasJobs = true;
            firePropertyChange(ImportSetupCard.PROP_HAS_JOBS, null, hasJobs);
        }
        return trackTable;
    }

    public void remove(SeqPairJobContainer seqPairJobContainer){
        int index = seqPairJobContainerList.indexOf(seqPairJobContainer);
        seqPairJobContainerList.remove(seqPairJobContainer);

        DefaultTableModel model = (DefaultTableModel) trackTable.getModel();
        model.removeRow(index);

        if (seqPairJobContainerList.isEmpty()){
            hasJobs = false;
            firePropertyChange(ImportSetupCard.PROP_HAS_JOBS, null, hasJobs);
        }
    }

    public List<SeqPairJobContainer> getJobs(){
        return seqPairJobContainerList;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        ListSelectionModel model = (ListSelectionModel) e.getSource();
        if(model.isSelectionEmpty()){
            firePropertyChange(ImportSetupCard.PROP_JOB_SELECTED, null, Boolean.FALSE);
        } else {
            firePropertyChange(ImportSetupCard.PROP_JOB_SELECTED, null, Boolean.TRUE);
        }
    }

    public boolean isRowSelected(){
        ListSelectionModel model = trackTable.getSelectionModel();
        return !model.isSelectionEmpty();
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
        trackTable = new javax.swing.JTable();
        trackTable.getSelectionModel().addListSelectionListener(this);

        trackTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "File 1", "File 2", "Description", "Reference", "Distance", "% Deviation", "Orientation"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        trackTable.setFillsViewportHeight(true);
        jScrollPane1.setViewportView(trackTable);
        trackTable.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(SeqPairJobView.class, "SeqPairJobView.trackTable.columnModel.title0")); // NOI18N
        trackTable.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(SeqPairJobView.class, "SeqPairJobView.trackTable.columnModel.title6")); // NOI18N
        trackTable.getColumnModel().getColumn(2).setHeaderValue(org.openide.util.NbBundle.getMessage(SeqPairJobView.class, "SeqPairJobView.trackTable.columnModel.title1")); // NOI18N
        trackTable.getColumnModel().getColumn(3).setHeaderValue(org.openide.util.NbBundle.getMessage(SeqPairJobView.class, "SeqPairJobView.trackTable.columnModel.title2")); // NOI18N
        trackTable.getColumnModel().getColumn(4).setHeaderValue(org.openide.util.NbBundle.getMessage(SeqPairJobView.class, "SeqPairJobView.trackTable.columnModel.title3")); // NOI18N
        trackTable.getColumnModel().getColumn(5).setHeaderValue(org.openide.util.NbBundle.getMessage(SeqPairJobView.class, "SeqPairJobView.trackTable.columnModel.title4")); // NOI18N
        trackTable.getColumnModel().getColumn(6).setHeaderValue(org.openide.util.NbBundle.getMessage(SeqPairJobView.class, "SeqPairJobView.trackTable.columnModel.title5")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable trackTable;
    // End of variables declaration//GEN-END:variables

        //TODO: add values to table model!

}