package de.cebitec.vamp.differentialExpression.wizard;

import de.cebitec.vamp.databackend.dataObjects.PersistantTrack;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public final class DeSeqVisualPanelDesign extends JPanel {
    
    private DefaultTableModel tm;
    private DefaultComboBoxModel cbm = new DefaultComboBoxModel(new Integer[]{1});

    /**
     * Creates new form DeSeqVisualPanelDesign
     */
    public DeSeqVisualPanelDesign() {
        initComponents();
    }
    
    @Override
    public String getName() {
        return "Experimental design";
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        addRowButton = new javax.swing.JButton();
        removeRow = new javax.swing.JButton();
        rowNumberSelection = new javax.swing.JComboBox();

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable2.setRowSelectionAllowed(false);
        jScrollPane2.setViewportView(jTable2);

        org.openide.awt.Mnemonics.setLocalizedText(addRowButton, org.openide.util.NbBundle.getMessage(DeSeqVisualPanelDesign.class, "DeSeqVisualPanelDesign.addRowButton.text")); // NOI18N
        addRowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRowButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(removeRow, org.openide.util.NbBundle.getMessage(DeSeqVisualPanelDesign.class, "DeSeqVisualPanelDesign.removeRow.text")); // NOI18N
        removeRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeRowActionPerformed(evt);
            }
        });

        rowNumberSelection.setModel(cbm);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addRowButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(removeRow)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rowNumberSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addRowButton)
                    .addComponent(removeRow)
                    .addComponent(rowNumberSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addRowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRowButtonActionPerformed
        String[] rowData = new String[tm.getColumnCount()];
        tm.addRow(rowData);
        cbm.addElement(tm.getRowCount());
    }//GEN-LAST:event_addRowButtonActionPerformed

    private void removeRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeRowActionPerformed
        int rowIndex = ((int) cbm.getSelectedItem())-1;
        tm.removeRow(rowIndex);
        cbm.removeElementAt(cbm.getSize()-1);
    }//GEN-LAST:event_removeRowActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addRowButton;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JButton removeRow;
    private javax.swing.JComboBox rowNumberSelection;
    // End of variables declaration//GEN-END:variables

    protected void setTracks(List<PersistantTrack> tracks) {
        String[] columnNames = new String[tracks.size()];
        for (int i=0; i<tracks.size();i++) {
            columnNames[i]=tracks.get(i).getDescription();
        }
        tm = new DefaultTableModel(columnNames,1);
        jTable2.setModel(tm);
    }
    
    protected Vector getTableData(){
        //If a cell is still selected when you click on "next" the input you
        //have done won't be used unless you change the edited cell first.
        //This is done automatically by this command.
        jTable2.editCellAt(-1, -1);
        return tm.getDataVector();
    }
}
