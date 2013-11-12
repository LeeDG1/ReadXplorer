/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cebitec.vamp.transcriptomeAnalyses.wizard;

import de.cebitec.readxplorer.transcriptomeAnalyses.verifier.DoubleVerifier;
import javax.swing.JPanel;

public final class NewRegionDetectionParamsVisualPanel extends JPanel {

    private final String wizardName;
    
    /**
     * Creates new form NewRegionDetectionParamsVisualPanel
     */
    public NewRegionDetectionParamsVisualPanel(String wizardName) {
        initComponents();
        this.wizardName = wizardName;
        this.fractionTF.setInputVerifier(new DoubleVerifier(this.fractionTF));
    }

    @Override
    public String getName() {
        return "Parameters for new-feature detection";
    }

    public Double getFraction() {
        return Double.valueOf(this.fractionTF.getText());
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        fractionTF = new javax.swing.JTextField();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(NewRegionDetectionParamsVisualPanel.class, "NewRegionDetectionParamsVisualPanel.jLabel1.text_1")); // NOI18N

        fractionTF.setText(org.openide.util.NbBundle.getMessage(NewRegionDetectionParamsVisualPanel.class, "NewRegionDetectionParamsVisualPanel.fractionTF.text")); // NOI18N
        fractionTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fractionTFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(fractionTF, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(296, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(fractionTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(269, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void fractionTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fractionTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fractionTFActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField fractionTF;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
