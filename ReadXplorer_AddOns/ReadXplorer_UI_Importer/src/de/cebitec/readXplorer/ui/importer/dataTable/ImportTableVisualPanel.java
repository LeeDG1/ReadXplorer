package de.cebitec.readXplorer.ui.importer.dataTable;

import de.cebitec.readXplorer.api.objects.JobPanel;
import de.cebitec.readXplorer.databackend.connector.ProjectConnector;
import de.cebitec.readXplorer.databackend.dataObjects.PersistantReference;
import de.cebitec.readXplorer.parser.common.ParserI;
import de.cebitec.readXplorer.parser.tables.CsvPreferenceForUsers;
import de.cebitec.readXplorer.parser.tables.CsvTableParser;
import de.cebitec.readXplorer.parser.tables.TableType;
import de.cebitec.readXplorer.parser.tables.TableParserI;
import de.cebitec.readXplorer.util.fileChooser.ReadXplorerFileChooser;
import de.cebitec.readXplorer.view.dialogMenus.ChangeListeningWizardPanel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ScrollPaneConstants;
import org.openide.util.NbBundle;
import org.supercsv.prefs.CsvPreference;

/**
 * Creates a panel for displaying the selection of different table parsers and
 * their options.
 *
 * @author Rolf Hilker <rhilker at mikrobio.med.uni-giessen.de>
 */
public final class ImportTableVisualPanel extends JobPanel {
    
    private static final long serialVersionUID = 1L;
    
    private String fileLocation;

    /**
     * Creates a panel for displaying the selection of different table parsers
     * and their options.
     */
    public ImportTableVisualPanel() {
        initComponents();
        this.descriptionScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
    }

    @NbBundle.Messages("StepName=Choose table parser")
    @Override
    public String getName() {
        return Bundle.StepName();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        descriptionScrollPane = new javax.swing.JScrollPane();
        descriptionTextArea = new javax.swing.JTextArea();
        tableComboBox = new javax.swing.JComboBox<>(new javax.swing.DefaultComboBoxModel<>(TableType.values()));
        fileTextField = new javax.swing.JTextField();
        fileButton = new javax.swing.JButton();
        PersistantReference[] refArray = new PersistantReference[0];
        refArray = ProjectConnector.getInstance().getGenomes().toArray(refArray);
        refComboBox = new javax.swing.JComboBox<>(new DefaultComboBoxModel<>(refArray));
        tableLabel = new javax.swing.JLabel();
        fileLabel = new javax.swing.JLabel();
        refLabel = new javax.swing.JLabel();
        delimiterCheckBox = new javax.swing.JCheckBox();
        csvPrefComboBox = new javax.swing.JComboBox<>(CsvPreferenceForUsers.values());

        descriptionTextArea.setEditable(false);
        descriptionTextArea.setColumns(20);
        descriptionTextArea.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setRows(5);
        descriptionTextArea.setText(org.openide.util.NbBundle.getMessage(ImportTableVisualPanel.class, "ImportTableVisualPanel.descriptionTextArea.text")); // NOI18N
        descriptionTextArea.setWrapStyleWord(true);
        descriptionScrollPane.setViewportView(descriptionTextArea);

        tableComboBox.setSelectedIndex(0);

        fileTextField.setEditable(false);
        fileTextField.setText(org.openide.util.NbBundle.getMessage(ImportTableVisualPanel.class, "ImportTableVisualPanel.fileTextField.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(fileButton, org.openide.util.NbBundle.getMessage(ImportTableVisualPanel.class, "ImportTableVisualPanel.fileButton.text")); // NOI18N
        fileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileButtonActionPerformed(evt);
            }
        });

        refComboBox.setToolTipText(org.openide.util.NbBundle.getMessage(ImportTableVisualPanel.class, "ImportTableVisualPanel.refComboBox.toolTipText")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(tableLabel, org.openide.util.NbBundle.getMessage(ImportTableVisualPanel.class, "ImportTableVisualPanel.tableLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(fileLabel, org.openide.util.NbBundle.getMessage(ImportTableVisualPanel.class, "ImportTableVisualPanel.fileLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(refLabel, org.openide.util.NbBundle.getMessage(ImportTableVisualPanel.class, "ImportTableVisualPanel.refLabel.text")); // NOI18N

        delimiterCheckBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(delimiterCheckBox, org.openide.util.NbBundle.getMessage(ImportTableVisualPanel.class, "ImportTableVisualPanel.delimiterCheckBox.text")); // NOI18N
        delimiterCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delimiterCheckBoxActionPerformed(evt);
            }
        });

        csvPrefComboBox.setVisible(!this.delimiterCheckBox.isSelected());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(csvPrefComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(descriptionScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(tableComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(refComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(fileTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fileButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tableLabel)
                            .addComponent(refLabel)
                            .addComponent(fileLabel)
                            .addComponent(delimiterCheckBox))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(descriptionScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tableLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tableComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(refLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(refComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fileLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fileButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(delimiterCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(csvPrefComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void fileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileButtonActionPerformed
        ParserI currentParser = new CsvTableParser();
        ReadXplorerFileChooser chooser = new ReadXplorerFileChooser(currentParser.getFileExtensions(), currentParser.getInputFileDescription()) {
            private static final long serialVersionUID = 1L;
            
            @Override
            public void save(String fileLocation) {
                throw new UnsupportedOperationException("Only opening is supported by this file chooser.");
            }
            
            @Override
            public void open(String fileLocation) {
                ImportTableVisualPanel.this.fileLocation = fileLocation;
            }
        };
        chooser.openFileChooser(ReadXplorerFileChooser.OPEN_DIALOG);
        this.fileTextField.setText(fileLocation);
        this.isRequiredInfoSet();
    }//GEN-LAST:event_fileButtonActionPerformed

    private void delimiterCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delimiterCheckBoxActionPerformed
        this.csvPrefComboBox.setVisible(!delimiterCheckBox.isSelected());
    }//GEN-LAST:event_delimiterCheckBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<CsvPreferenceForUsers> csvPrefComboBox;
    private javax.swing.JCheckBox delimiterCheckBox;
    private javax.swing.JScrollPane descriptionScrollPane;
    private javax.swing.JTextArea descriptionTextArea;
    private javax.swing.JButton fileButton;
    private javax.swing.JLabel fileLabel;
    private javax.swing.JTextField fileTextField;
    private javax.swing.JComboBox<PersistantReference> refComboBox;
    private javax.swing.JLabel refLabel;
    private javax.swing.JComboBox<TableType> tableComboBox;
    private javax.swing.JLabel tableLabel;
    // End of variables declaration//GEN-END:variables

    @Override
    public boolean isRequiredInfoSet() {
        boolean isValidated = tableComboBox.getSelectedIndex() > -1 && refComboBox.getSelectedIndex() > -1
                && fileLocation != null && !fileLocation.isEmpty();
        firePropertyChange(ChangeListeningWizardPanel.PROP_VALIDATE, null, isValidated);
        return isValidated;
    }

    /**
     * @return The parser selected in this panel.
     */
    public TableType getSelectedParser() {
        return (TableType) this.tableComboBox.getSelectedItem();
    }

    /**
     * @return The file location of the file containing the table to import.
     */
    public String getFileLocation() {
        return fileLocation;
    }
    
    /**
     * @return The reference for which the table shall be imported.
     */
    public PersistantReference getReference() {
        return (PersistantReference) refComboBox.getSelectedItem();
    }
    
    /**
     * @return The currently selected CsvPreference.
     */
    public CsvPreference getCsvPref() {
        return ((CsvPreferenceForUsers) this.csvPrefComboBox.getSelectedItem()).getCsvPref();
    }
    
    /**
     * @return <cc>true</cc>, if the delimiter shall be detected automatically,
     * <cc>false</cc>, if the delimiter was selected by the user.
     */
    public boolean isAutodetectDelimiter() {
        return this.delimiterCheckBox.isSelected();
    }
    
}
