package de.cebitec.readXplorer.tools.snp;

import de.cebitec.readXplorer.api.objects.JobPanel;
import de.cebitec.readXplorer.util.GeneralUtils;
import de.cebitec.readXplorer.view.dialogMenus.ChangeListeningWizardPanel;
import java.util.prefs.Preferences;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import org.openide.util.NbPreferences;

/**
 * Panel displaying all options for a SNP detection.
 * 
 * @author Rolf Hilker <rhilker at cebitec.uni-bielefeld.de>
 */
public final class SNPVisualPanel extends JobPanel {
    
    private static final long serialVersionUID = 1L;
    private Object minPercentage = 90;
    private int minMismatchBases = 15;
    private byte minBaseQuality = 0;
    private byte minAverageBaseQual = 0;
    private byte minAverageMappingQual = 0;

    /**
     * Panel displaying all options for a SNP detection.
     */
    public SNPVisualPanel() {
        initComponents();
        
        this.absNumText.getDocument().addDocumentListener(this.createDocumentListener());
        this.minBaseQualityField.getDocument().addDocumentListener(this.createDocumentListener());
        this.minAvrgBaseQualField.getDocument().addDocumentListener(this.createDocumentListener());
        this.minAvrgMappingQualField.getDocument().addDocumentListener(this.createDocumentListener());
        
        ((DefaultEditor) percentSpinner.getEditor()).getTextField().getDocument().addDocumentListener(this.createDocumentListener());
        this.loadLastParameterSelection();
    }

    @Override
    public String getName() {
        return "SNP parameter setup";
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        percentSpinner = new javax.swing.JSpinner(new SpinnerNumberModel());
        mismatchOptionsPanel = new javax.swing.JPanel();
        absNumText = new javax.swing.JTextField();
        useMainBaseBox = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        qualityOptionsPanel = new javax.swing.JPanel();
        minBaseQualityField = new javax.swing.JTextField();
        minAvrgBaseQualField = new javax.swing.JTextField();
        minAvrgMappingQualField = new javax.swing.JTextField();
        minBaseQualityLabel = new javax.swing.JLabel();
        minAvrgBaseQualLabel = new javax.swing.JLabel();
        minAvrgMappingQualLabel = new javax.swing.JLabel();
        qualFilterCheckBox = new javax.swing.JCheckBox();

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText(org.openide.util.NbBundle.getMessage(SNPVisualPanel.class, "SNPVisualPanel.jTextArea1.text_1")); // NOI18N
        jTextArea1.setWrapStyleWord(true);
        jScrollPane1.setViewportView(jTextArea1);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(SNPVisualPanel.class, "SNPVisualPanel.jLabel2.text")); // NOI18N

        percentSpinner.setValue(minPercentage);
        percentSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                percentSpinnerStateChanged(evt);
            }
        });

        mismatchOptionsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        absNumText.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        absNumText.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        absNumText.setText(String.valueOf(minMismatchBases));

        useMainBaseBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(useMainBaseBox, org.openide.util.NbBundle.getMessage(SNPVisualPanel.class, "SNPVisualPanel.useMainBaseBox.text")); // NOI18N
        useMainBaseBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                useMainBaseBoxActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(SNPVisualPanel.class, "SNPVisualPanel.jLabel3.text")); // NOI18N

        javax.swing.GroupLayout mismatchOptionsPanelLayout = new javax.swing.GroupLayout(mismatchOptionsPanel);
        mismatchOptionsPanel.setLayout(mismatchOptionsPanelLayout);
        mismatchOptionsPanelLayout.setHorizontalGroup(
            mismatchOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mismatchOptionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mismatchOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(mismatchOptionsPanelLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(absNumText, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(useMainBaseBox))
                .addContainerGap())
        );
        mismatchOptionsPanelLayout.setVerticalGroup(
            mismatchOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mismatchOptionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mismatchOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(absNumText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(useMainBaseBox)
                .addContainerGap())
        );

        qualityOptionsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        minBaseQualityField.setText(org.openide.util.NbBundle.getMessage(SNPVisualPanel.class, "SNPVisualPanel.minBaseQualityField.text")); // NOI18N

        minAvrgBaseQualField.setText(org.openide.util.NbBundle.getMessage(SNPVisualPanel.class, "SNPVisualPanel.minAvrgBaseQualField.text")); // NOI18N

        minAvrgMappingQualField.setText(org.openide.util.NbBundle.getMessage(SNPVisualPanel.class, "SNPVisualPanel.minAvrgMappingQualField.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(minBaseQualityLabel, org.openide.util.NbBundle.getMessage(SNPVisualPanel.class, "SNPVisualPanel.minBaseQualityLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(minAvrgBaseQualLabel, org.openide.util.NbBundle.getMessage(SNPVisualPanel.class, "SNPVisualPanel.minAvrgBaseQualLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(minAvrgMappingQualLabel, org.openide.util.NbBundle.getMessage(SNPVisualPanel.class, "SNPVisualPanel.minAvrgMappingQualLabel.text")); // NOI18N

        qualFilterCheckBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(qualFilterCheckBox, org.openide.util.NbBundle.getMessage(SNPVisualPanel.class, "SNPVisualPanel.qualFilterCheckBox.text")); // NOI18N

        javax.swing.GroupLayout qualityOptionsPanelLayout = new javax.swing.GroupLayout(qualityOptionsPanel);
        qualityOptionsPanel.setLayout(qualityOptionsPanelLayout);
        qualityOptionsPanelLayout.setHorizontalGroup(
            qualityOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(qualityOptionsPanelLayout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addGroup(qualityOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(minBaseQualityLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(minAvrgBaseQualLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(minAvrgMappingQualLabel, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(qualityOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(minBaseQualityField, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                    .addComponent(minAvrgBaseQualField)
                    .addComponent(minAvrgMappingQualField))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, qualityOptionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(qualFilterCheckBox)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        qualityOptionsPanelLayout.setVerticalGroup(
            qualityOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, qualityOptionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(qualFilterCheckBox)
                .addGap(1, 1, 1)
                .addGroup(qualityOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(minBaseQualityField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(minBaseQualityLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(qualityOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(minAvrgBaseQualField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(minAvrgBaseQualLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(qualityOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(minAvrgMappingQualField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(minAvrgMappingQualLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 235, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(percentSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(qualityOptionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(mismatchOptionsPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(percentSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(mismatchOptionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(qualityOptionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void percentSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_percentSpinnerStateChanged
        this.isRequiredInfoSet();
    }//GEN-LAST:event_percentSpinnerStateChanged

    private void useMainBaseBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_useMainBaseBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_useMainBaseBoxActionPerformed
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField absNumText;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField minAvrgBaseQualField;
    private javax.swing.JLabel minAvrgBaseQualLabel;
    private javax.swing.JTextField minAvrgMappingQualField;
    private javax.swing.JLabel minAvrgMappingQualLabel;
    private javax.swing.JTextField minBaseQualityField;
    private javax.swing.JLabel minBaseQualityLabel;
    private javax.swing.JPanel mismatchOptionsPanel;
    private javax.swing.JSpinner percentSpinner;
    private javax.swing.JCheckBox qualFilterCheckBox;
    private javax.swing.JPanel qualityOptionsPanel;
    private javax.swing.JCheckBox useMainBaseBox;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the minimum percentage of mismatches at a SNP position
     */
    public Object getMinPercentage() {
        return this.minPercentage;
    }

    /**
     * @return the minimum number of mismatches at a SNP position
     */
    public int getMinMismatchingBases() {
        return this.minMismatchBases;
    }

    /**
     * @return <cc>true</cc>, if the minMismatchBases count corresponds to the
     * count of the most frequent base at the current position. <cc>false</cc>,
     * if the minMismatchBases count corresponds to the overall mismatch count at
     * the current position.
     */
    public boolean isUseMainBase() {
        return this.useMainBaseBox.isSelected();
    }

    /**
     * @return <cc>true</cc>, if the quality filter options should be enabled. 
     * <cc>false</cc>, if the quality options should not have any effect.
     */
    public boolean isUseQualFilter() {
        return this.qualFilterCheckBox.isSelected();
    }

    byte getMinBaseQuality() {
        return minBaseQuality;
    }

    byte getMinAverageBaseQual() {
        return minAverageBaseQual;
    }

    byte getMinAverageMappingQual() {
        return minAverageMappingQual;
    }
    
    /**
     * Checks if all required information to start the SNP analysis is set.
     */
    @Override
    public boolean isRequiredInfoSet() {
        boolean isValidated = true;
        if (GeneralUtils.isValidPositiveNumberInput(absNumText.getText())) {
            this.minMismatchBases = Integer.parseInt(absNumText.getText());
        } else {
            isValidated = false;
        }
        if (GeneralUtils.isValidByteInput(minBaseQualityField.getText())
                && GeneralUtils.isValidByteInput(minAvrgBaseQualField.getText())
                && GeneralUtils.isValidByteInput(minAvrgMappingQualField.getText())
                ) {
            this.minBaseQuality = Byte.parseByte(minBaseQualityField.getText());
            this.minAverageBaseQual = Byte.parseByte(minAvrgBaseQualField.getText());
            this.minAverageMappingQual = Byte.parseByte(minAvrgMappingQualField.getText());
        } else {
            isValidated = false;
        }
        JTextField spinnerField = ((DefaultEditor) percentSpinner.getEditor()).getTextField();
        if (GeneralUtils.isValidPercentage(spinnerField.getText())) {
            this.minPercentage = this.percentSpinner.getValue();
        } else {
            isValidated = false;
        }
        
        firePropertyChange(ChangeListeningWizardPanel.PROP_VALIDATE, null, isValidated);
        return isValidated;
    }

    /**
     * Updates the parameters for this panel with the globally stored
     * settings for this wizard panel. If no settings were stored, the default
     * configuration is chosen.
     */
    private void loadLastParameterSelection() {
        Preferences pref = NbPreferences.forModule(Object.class);
        ((DefaultEditor) percentSpinner.getEditor()).getTextField().setText(pref.get(SNPWizardPanel.PROP_MIN_PERCENT, "90"));
        absNumText.setText(pref.get(SNPWizardPanel.PROP_MIN_VARYING_BASES, "15"));
        useMainBaseBox.setSelected(pref.get(SNPWizardPanel.PROP_USE_MAIN_BASE, "1").equals("1"));
        qualFilterCheckBox.setSelected(pref.get(SNPWizardPanel.PROP_SEL_QUAL_FILTER, "1").equals("1"));
        minBaseQualityField.setText(pref.get(SNPWizardPanel.PROP_MIN_BASE_QUAL, "20"));
        minAvrgBaseQualField.setText(pref.get(SNPWizardPanel.PROP_MIN_AVERAGE_BASE_QUAL, "20"));
        minAvrgMappingQualField.setText(pref.get(SNPWizardPanel.PROP_MIN_AVERAGE_MAP_QUAL, "20"));        
    }
    
}
