/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cebitec.vamp.transcriptomeAnalyses.wizard;

import java.util.prefs.Preferences;
import javax.swing.JPanel;
import org.openide.util.NbPreferences;

public final class WholeTranscriptTracksVisualPanel extends JPanel {

    private final String wizardName;
    /**
     * Creates new form WholeTranscriptTracksVisualPanel
     */
    public WholeTranscriptTracksVisualPanel(String wizardName) {
        this.wizardName = wizardName;
        initComponents();
        updateCheckBoxes();
    }

    @Override
    public String getName() {
        return "Analyses for whole genome Tracks";
    }

    public boolean isNewRegions () {
        return this.newRegionsCheckBox.isSelected();
    }
    
    public boolean isOperonDetection () {
        return this.operonsCheckBox.isSelected();
    }
    
    public boolean isLogRPKM () {
        return this.logRpkmCheckBox.isSelected();
    }
    
    public boolean isNormalRPKM () {
        return this.rpkmCheckBox.isSelected();
    }
    
    /**
     * Updates the checkboxes for the read classes with the globally stored
     * settings for this wizard. If no settings were stored, the default
     * configuration is chosen.
     */
    private void updateCheckBoxes() {
        Preferences pref = NbPreferences.forModule(Object.class);
        this.logRpkmCheckBox.setSelected(pref.getBoolean(wizardName+TranscriptomeAnalysisWizardIterator.PROP_RPKM_ANALYSIS, false));
        this.rpkmCheckBox.setSelected(pref.getBoolean(wizardName+TranscriptomeAnalysisWizardIterator.PROP_NORMAL_RPKM_ANALYSIS, false));
        this.operonsCheckBox.setSelected(pref.getBoolean(wizardName+TranscriptomeAnalysisWizardIterator.PROP_OPERON_ANALYSIS, false));
        this.newRegionsCheckBox.setSelected(pref.getBoolean(wizardName+TranscriptomeAnalysisWizardIterator.PROP_NOVEL_ANALYSIS, false));
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        logRpkmCheckBox = new javax.swing.JCheckBox();
        newRegionsCheckBox = new javax.swing.JCheckBox();
        operonsCheckBox = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        rpkmCheckBox = new javax.swing.JCheckBox();

        org.openide.awt.Mnemonics.setLocalizedText(logRpkmCheckBox, org.openide.util.NbBundle.getMessage(WholeTranscriptTracksVisualPanel.class, "WholeTranscriptTracksVisualPanel.logRpkmCheckBox.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(newRegionsCheckBox, org.openide.util.NbBundle.getMessage(WholeTranscriptTracksVisualPanel.class, "WholeTranscriptTracksVisualPanel.newRegionsCheckBox.text")); // NOI18N
        newRegionsCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newRegionsCheckBoxActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(operonsCheckBox, org.openide.util.NbBundle.getMessage(WholeTranscriptTracksVisualPanel.class, "WholeTranscriptTracksVisualPanel.operonsCheckBox.text")); // NOI18N
        operonsCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                operonsCheckBoxActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jScrollPane3.setViewportView(jTextArea3);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(WholeTranscriptTracksVisualPanel.class, "WholeTranscriptTracksVisualPanel.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(rpkmCheckBox, org.openide.util.NbBundle.getMessage(WholeTranscriptTracksVisualPanel.class, "WholeTranscriptTracksVisualPanel.rpkmCheckBox.text")); // NOI18N
        rpkmCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rpkmCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(newRegionsCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(operonsCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 140, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(logRpkmCheckBox)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addComponent(jLabel1))
                            .addComponent(rpkmCheckBox))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(newRegionsCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(operonsCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(logRpkmCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rpkmCheckBox))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void operonsCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_operonsCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_operonsCheckBoxActionPerformed

    private void newRegionsCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newRegionsCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newRegionsCheckBoxActionPerformed

    private void rpkmCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rpkmCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rpkmCheckBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JCheckBox logRpkmCheckBox;
    private javax.swing.JCheckBox newRegionsCheckBox;
    private javax.swing.JCheckBox operonsCheckBox;
    private javax.swing.JCheckBox rpkmCheckBox;
    // End of variables declaration//GEN-END:variables
}
