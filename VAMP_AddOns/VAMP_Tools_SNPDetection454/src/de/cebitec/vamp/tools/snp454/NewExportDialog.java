/*
 * NewExportDialog.java
 *
 * Created on 24.07.2011, 16:24:29
 */
package de.cebitec.vamp.tools.snp454;

import de.cebitec.vamp.databackend.dataObjects.Snp454;
import de.cebitec.vamp.exporter.ExportContoller;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author msmith
 */
public class NewExportDialog extends java.awt.Dialog {
    
     private List<Snp454> snps;

    /** Creates new form NewExportDialog */
    public NewExportDialog(java.awt.Frame parent, boolean modal, List<Snp454> snps) {
        super(parent, modal);
        this.snps = snps;
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nameLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        dirLabel = new javax.swing.JLabel();
        dirTextField = new javax.swing.JTextField();
        exportButton = new javax.swing.JButton();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        nameLabel.setText(org.openide.util.NbBundle.getMessage(NewExportDialog.class, "NewExportDialog.nameLabel.text")); // NOI18N

        nameTextField.setText(org.openide.util.NbBundle.getMessage(NewExportDialog.class, "NewExportDialog.nameTextField.text")); // NOI18N

        dirLabel.setText(org.openide.util.NbBundle.getMessage(NewExportDialog.class, "NewExportDialog.dirLabel.text")); // NOI18N

        dirTextField.setText(org.openide.util.NbBundle.getMessage(NewExportDialog.class, "NewExportDialog.dirTextField.text")); // NOI18N

        exportButton.setText(org.openide.util.NbBundle.getMessage(NewExportDialog.class, "NewExportDialog.exportButton.text")); // NOI18N
        exportButton.setActionCommand(org.openide.util.NbBundle.getMessage(NewExportDialog.class, "NewExportDialog.exportButton.actionCommand")); // NOI18N
        exportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(exportButton)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dirLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nameTextField)
                            .addComponent(dirTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE))))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dirLabel)
                    .addComponent(dirTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(exportButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void exportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportButtonActionPerformed
        if (nameTextField.getText().isEmpty() || dirTextField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill out the complete form!", "Missing information", JOptionPane.ERROR_MESSAGE);
                } else {
                    this.setVisible(false);
                    ExportContoller e = new ExportContoller();
                    e.setSnp454Data(this.snps);
                    e.setName(nameTextField.getText());
                    e.setFile(dirTextField.getText());
                    e.actionPerformed(evt);
                }
    }//GEN-LAST:event_exportButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel dirLabel;
    private javax.swing.JTextField dirTextField;
    private javax.swing.JButton exportButton;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    // End of variables declaration//GEN-END:variables

}
