/*
 * NewTrackDialogPanel.java
 *
 * Created on 13.01.2011, 15:18:28
 */
package de.cebitec.vamp.ui.importer;

import de.cebitec.vamp.api.objects.NewJobDialogI;
import de.cebitec.vamp.parser.ReferenceJob;
import de.cebitec.vamp.parser.common.ParserI;
import de.cebitec.vamp.parser.mappings.*;
import de.cebitec.vamp.util.fileChooser.VampFileChooser;
import de.cebitec.vamp.view.dialogMenus.ImportTrackBasePanel;
import java.awt.Component;
import java.io.File;
import java.util.List;
import javax.swing.*;
import javax.swing.text.NumberFormatter;
import org.openide.util.NbBundle;

/**
 * Panel displaying the options for importing new tracks into VAMP.
 *
 * @author jwinneba, rhilker
 */
public class NewTrackDialogPanel extends ImportTrackBasePanel implements NewJobDialogI {

    private static final long serialVersionUID = 774275254;
    private ReferenceJob[] refGenJobs;
    
    private final JokParser jokParser;
    private final JokToBamDirectParser jokToBamDirectParser;
    private final SamBamParser samBamParser;
    private final SamBamStepParser samBamStepParser;
    private final SamBamDirectParser samBamDirectParser;
    private MappingParserI[] parsers;
    
    private int stepSize = 0;
    private static final int maxVal = 1000000000;
    private static final int minVal = 10000;
    private static final int step = 1000;
    private static final int defaultVal = 300000;

    /** 
     * Panel displaying the options for importing new tracks into VAMP. 
     */
    public NewTrackDialogPanel() {
        this.refGenJobs = this.getReferenceJobs();
        this.jokParser = new JokParser();
        this.jokToBamDirectParser = new JokToBamDirectParser();
        this.samBamParser = new SamBamParser();
        this.samBamStepParser = new SamBamStepParser();
        this.samBamDirectParser = new SamBamDirectParser();
        this.parsers = new MappingParserI[] { this.samBamDirectParser, jokToBamDirectParser };
        // choose the default parser. first entry is shown in combobox by default
        this.setCurrentParser(this.parsers[0]);
        this.initComponents();
        this.setStepwiseField(false);
        this.setStepSizeSpinner();
        this.multiTrackScrollPane.setVisible(false);
        this.multiTrackListLabel.setVisible(false);

    }

    /**
     * @return true, if all required info for this track job dialog is set, 
     * false otherwise.
     */
    @Override
    public boolean isRequiredInfoSet() {
        if (getMappingFiles().isEmpty() || refGenBox.getSelectedItem() == null || nameField.getText().isEmpty()) {
            return false;
        } else {
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

    public boolean isFileSorted() {
        return fileSorted.isSelected();
    }

    public Integer getStepSize() {
        stepSize = (Integer) stepSizeSpinner.getValue();

        return stepSize;
    }

    /**
     * Creates the jspinner for setting the step size of the import.
     */
    private void setStepSizeSpinner() {
        stepSizeSpinner.setModel(new SpinnerNumberModel(defaultVal, minVal, maxVal, step));
        JFormattedTextField txt = ((JSpinner.NumberEditor) stepSizeSpinner.getEditor()).getTextField();
        ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);

    }

    private void setStepwiseField(boolean setFields) {
        stepSizeLabel.setVisible(setFields);
        stepSizeSpinner.setVisible(setFields);
        fileSorted.setVisible(setFields);
    }

    /**
     * @param jobs list of reference jobs which shall be imported now and thus
     *      have to be available for the import of new tracks too.
     */
    public void setReferenceJobs(List<ReferenceJob> jobs) {
        refGenBox.setModel(new DefaultComboBoxModel<>(this.getReferenceJobs(jobs)));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
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
        stepSizeLabel = new javax.swing.JLabel();
        stepSizeSpinner = new javax.swing.JSpinner();
        fileSorted = new javax.swing.JCheckBox();
        importTypeCombo = new javax.swing.JComboBox<>();
        importTypeLabel = new javax.swing.JLabel();
        alreadyImportedBox = new javax.swing.JCheckBox();
        multipleImportCheckBox = new javax.swing.JCheckBox();
        multiTrackScrollPane = new javax.swing.JScrollPane();
        multiTrackList = new javax.swing.JList<>();
        multiTrackListLabel = new javax.swing.JLabel();

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

        stepSizeLabel.setText(org.openide.util.NbBundle.getMessage(NewTrackDialogPanel.class, "NewTrackDialogPanel.stepSizeLabel.text")); // NOI18N

        fileSorted.setSelected(true);
        fileSorted.setText(org.openide.util.NbBundle.getMessage(NewTrackDialogPanel.class, "NewTrackDialogPanel.fileSorted.text")); // NOI18N

        importTypeCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Direct File Access", "Database" }));
        importTypeCombo.setRenderer(new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
                if(value instanceof ParserI){
                    return super.getListCellRendererComponent(list, ((ParserI) value).getName(), index, isSelected, cellHasFocus);
                } else {
                    return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                }
            }
        });
        importTypeCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importTypeComboActionPerformed(evt);
            }
        });

        importTypeLabel.setText(org.openide.util.NbBundle.getMessage(NewTrackDialogPanel.class, "NewTrackDialogPanel.importTypeLabel.text")); // NOI18N

        alreadyImportedBox.setText(org.openide.util.NbBundle.getMessage(NewTrackDialogPanel.class, "NewTrackDialogPanel.alreadyImportedBox.text")); // NOI18N
        alreadyImportedBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alreadyImportedBoxActionPerformed(evt);
            }
        });

        multipleImportCheckBox.setText(org.openide.util.NbBundle.getMessage(NewTrackDialogPanel.class, "NewTrackDialogPanel.multipleImportCheckBox.text")); // NOI18N
        multipleImportCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                multipleImportCheckBoxActionPerformed(evt);
            }
        });

        multiTrackScrollPane.setViewportView(multiTrackList);

        multiTrackListLabel.setText(org.openide.util.NbBundle.getMessage(NewTrackDialogPanel.class, "NewTrackDialogPanel.multiTrackListLabel.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(importTypeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(importTypeCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(refGenLabel)
                    .addComponent(stepSizeLabel)
                    .addComponent(nameLabel)
                    .addComponent(mappingFileLabel)
                    .addComponent(mappingTypeLabel)
                    .addComponent(multiTrackListLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(alreadyImportedBox)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(stepSizeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fileSorted))
                            .addComponent(multipleImportCheckBox))
                        .addGap(0, 45, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(refGenBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(mappingFileField)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(chooseButton))
                            .addComponent(multiTrackScrollPane, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mappingTypeCombo, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(importTypeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(importTypeLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(refGenBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refGenLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mappingTypeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mappingTypeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mappingFileField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chooseButton)
                    .addComponent(mappingFileLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stepSizeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fileSorted)
                    .addComponent(stepSizeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(alreadyImportedBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(multipleImportCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(multiTrackListLabel)
                    .addComponent(multiTrackScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(137, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void chooseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseButtonActionPerformed
        this.getMappingFiles().clear();
        VampFileChooser fc = new VampFileChooser(getCurrentParser().getFileExtensions(), getCurrentParser().getInputFileDescription()) {
            private static final long serialVersionUID = 1L;

            @Override
            public void save(String fileLocation) {
                throw new UnsupportedOperationException("Not supported.");
            }

            @Override
            public void open(String fileLocation) {

                // file chosen
                if (useMultipleImport()) {
                    File[] files = this.getSelectedFiles();
                    getMappingFiles().clear();

                    for (int i = 0; i < files.length; ++i) {
                        addFile(files[i], mappingFileField);
                        nameField.setText(files[i].getName());
                    }

                    mappingFileField.setText(getMappingFiles().size() + " tracks to import");
                    DefaultListModel<String> model = new DefaultListModel<>();
                    fillMultipleImportTable(model, getMappingFiles(), "Mapping file list:");
                    multiTrackList.setModel(model);
                    nameField.setText("Note: each track gets its file name");
                } else {
                    File file = this.getSelectedFile();
                    addFile(file, mappingFileField);
                    nameField.setText(file.getName());
                }
            }
        };
        
        fc.setDirectoryProperty("NewTrack.Filepath");
        fc.setMultiSelectionEnabled(this.useMultipleImport());
        fc.openFileChooser(VampFileChooser.OPEN_DIALOG);
}//GEN-LAST:event_chooseButtonActionPerformed

    private void importTypeComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importTypeComboActionPerformed
        //identification of import method by index
        this.setIsDbUsed(this.importTypeCombo.getSelectedIndex() == 1);

        this.mappingTypeCombo.removeAllItems();
        if (!this.isDbUsed()) {
            //update the parsers to only display the bam parser.
            this.mappingTypeCombo.addItem(this.samBamDirectParser);
            this.mappingTypeCombo.addItem(this.jokToBamDirectParser);
        } else {
            this.mappingTypeCombo.addItem(this.samBamParser);
            this.mappingTypeCombo.addItem(this.samBamStepParser);
            this.mappingTypeCombo.addItem(this.jokParser);
        }
        //already imported checkbox only visible, if direct track access
        this.alreadyImportedBox.setVisible(!this.isDbUsed());
            
            //has to be placed in the !useDB block of the if clause, if used
        
            //check if the project folder is already set and if not, set it now!
//            String projectFolder = ProjectConnector.getInstance().getProjectFolder();
//            if (projectFolder.isEmpty()) {
//                ProjectFolderDialogPanel panel = new ProjectFolderDialogPanel();
//                DialogDescriptor newDialog = new DialogDescriptor(panel, "Select Project Folder");
//
//                Dialog dialog = DialogDisplayer.getDefault().createDialog(newDialog);
//                dialog.setVisible(true);
//
//                // keep the dialog open until the required info is provided or the dialog is canceled
//                while (newDialog.getValue() == DialogDescriptor.OK_OPTION && !panel.isRequiredInfoSet()) {
//                    DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(NbBundle.getMessage(NewTrackDialogPanel.class, "MSG_ImportSetupCard.dialog.fillout"), NotifyDescriptor.INFORMATION_MESSAGE));
//                    dialog.setVisible(true);
//                }
//
//                // do dialog specific stuff
//                if (newDialog.getValue() == DialogDescriptor.OK_OPTION && panel.isRequiredInfoSet()) {
//                    if (panel instanceof ProjectFolderDialogPanel) {
//                        ProjectFolderDialogPanel folderPanel = (ProjectFolderDialogPanel) panel;
//                        boolean success = ProjectConnector.getInstance().storeProjectFolder(folderPanel.getProjectFolder());
//                        String msg;
//                        if (success) {
//                            msg = "Project folder successfully stored!";
//                        } else {
//                            msg = "An error occured during storage of the project folder. Please try again!";
//                        }
//                        JOptionPane.showMessageDialog(this, msg, "Success", JOptionPane.INFORMATION_MESSAGE);
//                    }
//                }
//            }
    }//GEN-LAST:event_importTypeComboActionPerformed

    private void mappingTypeComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mappingTypeComboActionPerformed
        MappingParserI newparser = (MappingParserI) this.mappingTypeCombo.getSelectedItem();
        if (this.getCurrentParser() != newparser) {
            this.setCurrentParser(newparser);
            this.multiTrackList.setModel(new DefaultListModel<String>());
            this.getMappingFiles().clear();
            this.mappingFileField.setText("");
            this.nameField.setText("");
            this.setStepwiseField(false);
            if (newparser instanceof SamBamStepParser) {
                this.setStepwiseField(true);
                this.fileSorted.requestFocus();
                JOptionPane.showMessageDialog(this,
                        "Please make sure that your file is sorted by read sequence. \n If not, deselect the checkbox!",
                        "Stepwise parser",
                        JOptionPane.WARNING_MESSAGE);
            }
            if (newparser instanceof SamBamDirectParser) {
                this.alreadyImportedBox.setEnabled(true);
            } else {
                this.alreadyImportedBox.setEnabled(false);
                this.alreadyImportedBox.setSelected(false);
                this.setIsAlreadyImported(false);
            }
        }
    }//GEN-LAST:event_mappingTypeComboActionPerformed

    private void multipleImportCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_multipleImportCheckBoxActionPerformed
        this.updateGuiForMultipleFiles(multipleImportCheckBox, multiTrackScrollPane, multiTrackList, multiTrackListLabel, mappingFileField);
        if (this.useMultipleImport()) {
            nameField.setEnabled(false);
            nameField.setText(NbBundle.getMessage(NewTrackDialogPanel.class, "MultipleImportTrackNameMsg"));
        } else {
            this.nameField.setEnabled(true);
            this.nameField.setText("");
        }
    }//GEN-LAST:event_multipleImportCheckBoxActionPerformed

    private void alreadyImportedBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alreadyImportedBoxActionPerformed
        this.setIsAlreadyImported(this.alreadyImportedBox.isSelected());
    }//GEN-LAST:event_alreadyImportedBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox alreadyImportedBox;
    private javax.swing.JButton chooseButton;
    private javax.swing.JCheckBox fileSorted;
    private javax.swing.JComboBox<String> importTypeCombo;
    private javax.swing.JLabel importTypeLabel;
    private javax.swing.JTextField mappingFileField;
    private javax.swing.JLabel mappingFileLabel;
    private javax.swing.JComboBox<MappingParserI> mappingTypeCombo;
    private javax.swing.JLabel mappingTypeLabel;
    private javax.swing.JList<String> multiTrackList;
    private javax.swing.JLabel multiTrackListLabel;
    private javax.swing.JScrollPane multiTrackScrollPane;
    private javax.swing.JCheckBox multipleImportCheckBox;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JComboBox<ReferenceJob> refGenBox;
    private javax.swing.JLabel refGenLabel;
    private javax.swing.JLabel stepSizeLabel;
    private javax.swing.JSpinner stepSizeSpinner;
    // End of variables declaration//GEN-END:variables

}
