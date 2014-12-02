/*
 * Copyright (C) 2014 Institute for Bioinformatics and Systems Biology, University Giessen, Germany
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.cebitec.readXplorer.mapping;


import de.cebitec.readXplorer.util.FileUtils;
import java.io.File;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.openide.util.NbBundle;


/**
 * Displays a card for the selection of parameters of the mapping process.
 * <p>
 * @author Evgeny Anisiforov <evgeny at cebitec.uni-bielefeld.de>
 */
public class MappingSelectionCard extends javax.swing.JPanel {

    private static final long serialVersionUID = 1L;


    /**
     * Creates new form TrimSelectionCard
     */
    public MappingSelectionCard() {
        initComponents();
        this.sourceFileField.getDocument().addDocumentListener(
                new DocumentListener() {
                    private String lastContent = "";


                    @Override
                    public void insertUpdate( DocumentEvent e ) {
                        update( e );
                    }


                    @Override
                    public void removeUpdate( DocumentEvent e ) {
                        update( e );
                    }


                    @Override
                    public void changedUpdate( DocumentEvent e ) {
                        update( e );
                    }


                    private void update( DocumentEvent e ) {
                        firePropertyChange( MappingAction.PROP_SOURCEPATH, lastContent, sourceFileField.getText() );
                        lastContent = sourceFileField.getText();
                    }


                } );

    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sourceFileLabel = new javax.swing.JLabel();
        sourceFileField = new javax.swing.JTextField();
        openSourceButton = new javax.swing.JButton();
        sourceFileLabel1 = new javax.swing.JLabel();
        referenceFileField = new javax.swing.JTextField();
        openReferenceButton = new javax.swing.JButton();
        mappingParamLabel = new javax.swing.JLabel();
        mappingParamField = new javax.swing.JTextField();

        org.openide.awt.Mnemonics.setLocalizedText(sourceFileLabel, org.openide.util.NbBundle.getMessage(MappingSelectionCard.class, "MappingSelectionCard.sourceFileLabel.text")); // NOI18N

        sourceFileField.setText(org.openide.util.NbBundle.getMessage(MappingSelectionCard.class, "MappingSelectionCard.sourceFileField.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(openSourceButton, org.openide.util.NbBundle.getMessage(MappingSelectionCard.class, "MappingSelectionCard.openSourceButton.text")); // NOI18N
        openSourceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openSourceButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(sourceFileLabel1, org.openide.util.NbBundle.getMessage(MappingSelectionCard.class, "MappingSelectionCard.sourceFileLabel1.text")); // NOI18N

        referenceFileField.setText(org.openide.util.NbBundle.getMessage(MappingSelectionCard.class, "MappingSelectionCard.referenceFileField.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(openReferenceButton, org.openide.util.NbBundle.getMessage(MappingSelectionCard.class, "MappingSelectionCard.openReferenceButton.text")); // NOI18N
        openReferenceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openReferenceButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(mappingParamLabel, org.openide.util.NbBundle.getMessage(MappingSelectionCard.class, "MappingSelectionCard.mappingParamLabel.text")); // NOI18N

        mappingParamField.setText(org.openide.util.NbBundle.getMessage(MappingSelectionCard.class, "MappingSelectionCard.mappingParamField.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mappingParamLabel)
                            .addComponent(sourceFileLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(sourceFileField)
                            .addComponent(referenceFileField))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(openReferenceButton)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(openSourceButton)
                                .addContainerGap())))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mappingParamField)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(sourceFileLabel)
                                .addGap(0, 602, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sourceFileLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(referenceFileField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(openReferenceButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sourceFileLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sourceFileField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(openSourceButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mappingParamLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mappingParamField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(195, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void openSourceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openSourceButtonActionPerformed
        File file = FileUtils.showFileOpenDialogAndChangePrefs( "TrimSelection.Filepath",
                                                                new FileNameExtensionFilter( "FNA, FASTQ, FASTA File", "fna", "fastq", "fasta" ),
                                                                sourceFileField, MappingSelectionCard.class, this );
    }//GEN-LAST:event_openSourceButtonActionPerformed


    public String getSourcePath() {
        return this.sourceFileField.getText();
    }


    public String getReferencePath() {
        return this.referenceFileField.getText();
    }


    public String getMappingParam() {
        return this.mappingParamField.getText();
    }


    private void openReferenceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openReferenceButtonActionPerformed
        File file = FileUtils.showFileOpenDialogAndChangePrefs( "TrimSelection.Referencepath",
                                                                new FileNameExtensionFilter( "Fasta File", "fasta" ),
                                                                referenceFileField, MappingSelectionCard.class, this );
    }//GEN-LAST:event_openReferenceButtonActionPerformed


    @Override
    public String getName() {
        return NbBundle.getMessage( MappingSelectionCard.class, "CTL_SelectionCard.name" );
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField mappingParamField;
    private javax.swing.JLabel mappingParamLabel;
    private javax.swing.JButton openReferenceButton;
    private javax.swing.JButton openSourceButton;
    private javax.swing.JTextField referenceFileField;
    private javax.swing.JTextField sourceFileField;
    private javax.swing.JLabel sourceFileLabel;
    private javax.swing.JLabel sourceFileLabel1;
    // End of variables declaration//GEN-END:variables


    public void setMappingParam( String lastMappingParams ) {
        this.mappingParamField.setText( lastMappingParams );
    }


}
