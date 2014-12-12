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

package de.cebitec.readxplorer.ui.importer;


import de.cebitec.readxplorer.api.objects.NewJobDialogI;
import de.cebitec.readxplorer.parser.ReferenceJob;
import de.cebitec.readxplorer.parser.common.ParserI;
import de.cebitec.readxplorer.parser.mappings.JokToBamDirectParser;
import de.cebitec.readxplorer.parser.mappings.MappingParserI;
import de.cebitec.readxplorer.parser.mappings.SamBamParser;
import de.cebitec.readxplorer.ui.dialogmenus.ImportTrackBasePanel;
import de.cebitec.readxplorer.utils.fileChooser.ReadXplorerFileChooser;
import java.awt.Component;
import java.io.File;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;


/**
 * Panel displaying the options for importing new tracks into ReadXplorer.
 *
 * @author jwinneba, rhilker
 */
public class NewTrackDialogPanel extends ImportTrackBasePanel implements
        NewJobDialogI {

    private static final long serialVersionUID = 774275254;
    private final ReferenceJob[] refGenJobs;

    private final JokToBamDirectParser jokToBamDirectParser;
    private final SamBamParser samBamDirectParser;
    private final MappingParserI[] parsers;


    /**
     * Panel displaying the options for importing new tracks into ReadXplorer.
     */
    public NewTrackDialogPanel() {
        this.refGenJobs = this.getReferenceJobs();
        this.jokToBamDirectParser = new JokToBamDirectParser();
        this.samBamDirectParser = new SamBamParser();
        this.parsers = new MappingParserI[]{ this.samBamDirectParser, jokToBamDirectParser };
        // choose the default parser. first entry is shown in combobox by default
        this.setCurrentParser( this.parsers[0] );
        this.initComponents();
        this.multiTrackScrollPane.setVisible( false );
        this.multiTrackListLabel.setVisible( false );

    }


    /**
     * @return true, if all required info for this track job dialog is set,
     *         false otherwise.
     */
    @Override
    public boolean isRequiredInfoSet() {
        if( getMappingFiles().isEmpty() || refGenBox.getSelectedItem() == null || nameField.getText().isEmpty() ) {
            return false;
        }
        else {
            return true;
        }
    }


    /**
     * @return The name of this track.
     */
    @Override
    public String getTrackName() {
        return nameField.getText();
    }


    /**
     * @return The reference genome associated with this track job.
     */
    @Override
    public ReferenceJob getReferenceJob() {
        return (ReferenceJob) refGenBox.getSelectedItem();
    }


    /**
     * @param jobs list of reference jobs which shall be imported now and thus
     *             have to be available for the import of new tracks too.
     */
    public void setReferenceJobs( List<ReferenceJob> jobs ) {
        refGenBox.setModel( new DefaultComboBoxModel<>( this.getReferenceJobs( jobs ) ) );
    }


    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mappingTypeLabel = new javax.swing.JLabel();
        mappingFileLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        refGenLabel = new javax.swing.JLabel();
        refGenBox = new javax.swing.JComboBox<>(refGenJobs);
        mappingFileField = new javax.swing.JTextField();
        chooseButton = new javax.swing.JButton();
        nameField = new javax.swing.JTextField();
        mappingTypeCombo = new javax.swing.JComboBox<>(parsers);
        alreadyImportedBox = new javax.swing.JCheckBox();
        multiTrackScrollPane = new javax.swing.JScrollPane();
        multiTrackList = new javax.swing.JList<>();
        multiTrackListLabel = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(484, 300));

        mappingTypeLabel.setText(org.openide.util.NbBundle.getMessage(NewTrackDialogPanel.class, "NewTrackDialogPanel.mappingTypeLabel.text")); // NOI18N

        mappingFileLabel.setText(org.openide.util.NbBundle.getMessage(NewTrackDialogPanel.class, "NewTrackDialogPanel.mappingFileLabel.text")); // NOI18N

        nameLabel.setText(org.openide.util.NbBundle.getMessage(NewTrackDialogPanel.class, "NewTrackDialogPanel.nameLabel.text")); // NOI18N

        refGenLabel.setText(org.openide.util.NbBundle.getMessage(NewTrackDialogPanel.class, "NewTrackDialogPanel.refGenLabel.text")); // NOI18N

        mappingFileField.setEditable(false);
        mappingFileField.setText(org.openide.util.NbBundle.getMessage(NewTrackDialogPanel.class, "NewTrackDialogPanel.mappingFileField.text")); // NOI18N

        chooseButton.setText(org.openide.util.NbBundle.getMessage(NewTrackDialogPanel.class, "NewTrackDialogPanel.chooseButton.text")); // NOI18N
        chooseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseButtonActionPerformed(evt);
            }
        });

        mappingTypeCombo.setRenderer(new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
                if(value instanceof ParserI){
                    return super.getListCellRendererComponent(list, ((ParserI) value).getName(), index, isSelected, cellHasFocus);
                } else {
                    return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                }
            }
        });
        mappingTypeCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mappingTypeComboActionPerformed(evt);
            }
        });

        alreadyImportedBox.setText(org.openide.util.NbBundle.getMessage(NewTrackDialogPanel.class, "NewTrackDialogPanel.alreadyImportedBox.text")); // NOI18N
        alreadyImportedBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alreadyImportedBoxActionPerformed(evt);
            }
        });

        multiTrackScrollPane.setViewportView(multiTrackList);

        multiTrackListLabel.setText(org.openide.util.NbBundle.getMessage(NewTrackDialogPanel.class, "NewTrackDialogPanel.multiTrackListLabel.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(multiTrackListLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(multiTrackScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(refGenLabel)
                                .addComponent(mappingTypeLabel))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(mappingFileLabel))
                            .addComponent(nameLabel, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(alreadyImportedBox)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(refGenBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(mappingFileField)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(chooseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(nameField, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(mappingTypeCombo, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(refGenBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refGenLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mappingTypeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mappingTypeLabel))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chooseButton)
                    .addComponent(mappingFileField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mappingFileLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(alreadyImportedBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(multiTrackScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(multiTrackListLabel)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void chooseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseButtonActionPerformed
        this.getMappingFiles().clear();
        ReadXplorerFileChooser fc = new ReadXplorerFileChooser( getCurrentParser().getFileExtensions(), getCurrentParser().getInputFileDescription() ) {
            private static final long serialVersionUID = 1L;


            @Override
            public void save( String fileLocation ) {
                throw new UnsupportedOperationException( "Not supported." );
            }


            @Override
            public void open( String fileLocation ) {

                // file chosen
                updateGuiForMultipleFiles( this.getSelectedFiles().length > 1, multiTrackScrollPane, multiTrackList, multiTrackListLabel, mappingFileField );
                if( useMultipleImport() ) {
                    File[] files = this.getSelectedFiles();
                    getMappingFiles().clear();

                    for( int i = 0; i < files.length; ++i ) {
                        addFile( files[i], mappingFileField );
                        nameField.setText( files[i].getName() );
                    }

                    mappingFileField.setText( getMappingFiles().size() + " tracks to import" );
                    DefaultListModel<String> model = new DefaultListModel<>();
                    fillMultipleImportTable( model, getMappingFiles(), "Mapping file list:" );
                    multiTrackList.setModel( model );
                    nameField.setText( "Note: each track gets its file name" );
                }
                else {
                    File file = this.getSelectedFile();
                    addFile( file, mappingFileField );
                    nameField.setText( file.getName() );
                }
            }


        };

        fc.setDirectoryProperty( "NewTrack.Filepath" );
        fc.setMultiSelectionEnabled( true );
        fc.openFileChooser( ReadXplorerFileChooser.OPEN_DIALOG );
}//GEN-LAST:event_chooseButtonActionPerformed

    private void mappingTypeComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mappingTypeComboActionPerformed
        MappingParserI newparser = (MappingParserI) this.mappingTypeCombo.getSelectedItem();
        if( this.getCurrentParser() != newparser ) {
            this.setCurrentParser( newparser );
            this.multiTrackList.setModel( new DefaultListModel<String>() );
            this.getMappingFiles().clear();
            this.mappingFileField.setText( "" );
            this.nameField.setText( "" );
            if( newparser instanceof SamBamParser ) {
                this.alreadyImportedBox.setEnabled( true );
            }
            else {
                this.alreadyImportedBox.setEnabled( false );
                this.alreadyImportedBox.setSelected( false );
                this.setIsAlreadyImported( false );
            }
        }
    }//GEN-LAST:event_mappingTypeComboActionPerformed

    private void alreadyImportedBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alreadyImportedBoxActionPerformed
        this.setIsAlreadyImported( this.alreadyImportedBox.isSelected() );
    }//GEN-LAST:event_alreadyImportedBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox alreadyImportedBox;
    private javax.swing.JButton chooseButton;
    private javax.swing.JTextField mappingFileField;
    private javax.swing.JLabel mappingFileLabel;
    private javax.swing.JComboBox<MappingParserI> mappingTypeCombo;
    private javax.swing.JLabel mappingTypeLabel;
    private javax.swing.JList<String> multiTrackList;
    private javax.swing.JLabel multiTrackListLabel;
    private javax.swing.JScrollPane multiTrackScrollPane;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JComboBox<ReferenceJob> refGenBox;
    private javax.swing.JLabel refGenLabel;
    // End of variables declaration//GEN-END:variables

}
