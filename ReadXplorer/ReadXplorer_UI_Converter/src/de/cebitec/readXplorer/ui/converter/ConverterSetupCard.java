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
package de.cebitec.readXplorer.ui.converter;


import de.cebitec.readXplorer.databackend.connector.ProjectConnector;
import de.cebitec.readXplorer.databackend.dataObjects.PersistentChromosome;
import de.cebitec.readXplorer.databackend.dataObjects.PersistentReference;
import de.cebitec.readXplorer.parser.output.ConverterI;
import de.cebitec.readXplorer.parser.output.JokToBamConverter;
import de.cebitec.readXplorer.util.GeneralUtils;
import de.cebitec.readXplorer.util.fileChooser.ReadXplorerFileChooser;
import de.cebitec.readXplorer.view.dialogMenus.FileSelectionPanel;
import java.io.File;
import java.util.Collection;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import org.openide.util.NbBundle;


/**
 * Visual wizard panel for selection of files to convert and selection of a
 * converter.
 *
 * @author Rolf Hilker <rhilker at cebitec.uni-bielefeld.de>
 */
public class ConverterSetupCard extends FileSelectionPanel {

    private static final long serialVersionUID = 1L;

    private String fileExtension; //TODO: add file extension to converter
    private ConverterI[] availableParsers = new ConverterI[]{ new JokToBamConverter() };
    private ConverterI currentConverter;
    private String refChromName;
    private int chromLength;
    private boolean canConvert;
    private boolean isConnected;
    private PersistentChromosome selectedChrom;
    private PersistentReference[] genomesAsArray;


    /**
     * Visual wizard panel for selection of files to convert and selection of a
     * converter.
     */
    public ConverterSetupCard() {
        isConnected = ProjectConnector.getInstance().isConnected();
        this.genomesAsArray = new PersistentReference[0];
        if( isConnected ) {
            genomesAsArray = ProjectConnector.getInstance().getGenomesAsArray();
        }
        initComponents();
        this.initAdditionalData();
        this.selectedChrom = (PersistentChromosome) this.chromComboBox.getSelectedItem();
        this.setVisibleComponents( isConnected );
        this.multiTrackScrollPane.setVisible( false );
        this.multiTrackListLabel.setVisible( false );
        this.updateChromComboBox();
    }


    private void initAdditionalData() {
        this.converterComboBox.setSelectedIndex( 0 );
        this.currentConverter = availableParsers[0];
        this.chromLength = -1;
        this.selectConverter();
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerLabel = new javax.swing.JLabel();
        converterComboBox = new javax.swing.JComboBox<>(this.availableParsers);
        converterLabel = new javax.swing.JLabel();
        fileLabel = new javax.swing.JLabel();
        fileTextField = new javax.swing.JTextField();
        openFileButton = new javax.swing.JButton();
        referenceNameLabel = new javax.swing.JLabel();
        referenceNameField = new javax.swing.JTextField();
        referenceLengthField = new javax.swing.JTextField();
        referenceLengthLabel = new javax.swing.JLabel();
        refComboBox = new javax.swing.JComboBox<>(this.genomesAsArray);
        refComboLabel = new javax.swing.JLabel();
        refCheckBox = new javax.swing.JCheckBox();
        refSelectionLabel = new javax.swing.JLabel();
        multiTrackScrollPane = new javax.swing.JScrollPane();
        multiTrackList = new javax.swing.JList<>();
        multiTrackListLabel = new javax.swing.JLabel();
        chromComboBox = new javax.swing.JComboBox<>();
        chromComboLabel = new javax.swing.JLabel();

        headerLabel.setText(org.openide.util.NbBundle.getMessage(ConverterSetupCard.class, "ConverterSetupCard.headerLabel.text")); // NOI18N

        converterComboBox.setToolTipText(org.openide.util.NbBundle.getMessage(ConverterSetupCard.class, "ConverterSetupCard.converterComboBox.toolTipText")); // NOI18N
        converterComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                converterComboBoxActionPerformed(evt);
            }
        });

        converterLabel.setText(org.openide.util.NbBundle.getMessage(ConverterSetupCard.class, "ConverterSetupCard.converterLabel.text")); // NOI18N

        fileLabel.setText(org.openide.util.NbBundle.getMessage(ConverterSetupCard.class, "ConverterSetupCard.fileLabel.text")); // NOI18N

        fileTextField.setEditable(false);
        fileTextField.setText(org.openide.util.NbBundle.getMessage(ConverterSetupCard.class, "ConverterSetupCard.fileTextField.text")); // NOI18N

        openFileButton.setText(org.openide.util.NbBundle.getMessage(ConverterSetupCard.class, "ConverterSetupCard.openFileButton.text")); // NOI18N
        openFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFileButtonActionPerformed(evt);
            }
        });

        referenceNameLabel.setText(org.openide.util.NbBundle.getMessage(ConverterSetupCard.class, "ConverterSetupCard.referenceNameLabel.text")); // NOI18N

        referenceNameField.setText(org.openide.util.NbBundle.getMessage(ConverterSetupCard.class, "ConverterSetupCard.referenceNameField.text")); // NOI18N
        referenceNameField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                referenceNameFieldKeyTyped(evt);
            }
        });

        referenceLengthField.setText(org.openide.util.NbBundle.getMessage(ConverterSetupCard.class, "ConverterSetupCard.referenceLengthField.text")); // NOI18N
        referenceLengthField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                referenceLengthFieldKeyTyped(evt);
            }
        });

        referenceLengthLabel.setText(org.openide.util.NbBundle.getMessage(ConverterSetupCard.class, "ConverterSetupCard.referenceLengthLabel.text")); // NOI18N

        refComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refComboBoxActionPerformed(evt);
            }
        });

        refComboLabel.setText(org.openide.util.NbBundle.getMessage(ConverterSetupCard.class, "ConverterSetupCard.refComboLabel.text")); // NOI18N

        refCheckBox.setSelected(true);
        refCheckBox.setText(org.openide.util.NbBundle.getMessage(ConverterSetupCard.class, "ConverterSetupCard.refCheckBox.text")); // NOI18N
        refCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refCheckBoxActionPerformed(evt);
            }
        });

        refSelectionLabel.setText(org.openide.util.NbBundle.getMessage(ConverterSetupCard.class, "ConverterSetupCard.refSelectionLabel.text")); // NOI18N

        multiTrackScrollPane.setViewportView(multiTrackList);

        multiTrackListLabel.setText(org.openide.util.NbBundle.getMessage(ConverterSetupCard.class, "ConverterSetupCard.multiTrackListLabel.text")); // NOI18N

        chromComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chromComboBoxActionPerformed(evt);
            }
        });

        chromComboLabel.setText(org.openide.util.NbBundle.getMessage(ConverterSetupCard.class, "ConverterSetupCard.chromComboLabel.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(headerLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(converterLabel)
                                    .addComponent(fileLabel)
                                    .addComponent(refComboLabel)
                                    .addComponent(refSelectionLabel)
                                    .addComponent(multiTrackListLabel)))
                            .addComponent(chromComboLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(referenceNameLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(referenceLengthLabel, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(multiTrackScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE)
                            .addComponent(converterComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(referenceNameField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(fileTextField)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(openFileButton))
                            .addComponent(referenceLengthField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(refComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(refCheckBox)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(chromComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(headerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(converterComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(converterLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fileLabel)
                    .addComponent(fileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(openFileButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(refCheckBox)
                    .addComponent(refSelectionLabel))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(refComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refComboLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chromComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chromComboLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(referenceNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(referenceNameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(referenceLengthField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(referenceLengthLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(multiTrackListLabel)
                        .addGap(0, 96, Short.MAX_VALUE))
                    .addComponent(multiTrackScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void openFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openFileButtonActionPerformed
        ReadXplorerFileChooser fc = new ReadXplorerFileChooser( currentConverter.getFileExtensions(), currentConverter.getInputFileDescription() ) {

            private static final long serialVersionUID = 1L;


            @Override
            public void save( String fileLocation ) {
                throw new UnsupportedOperationException( "Operation not supported!" );
            }


            @Override
            public void open( String fileLocation ) {

                updateGuiForMultipleFiles( this.getSelectedFiles().length > 1, multiTrackScrollPane, multiTrackList, multiTrackListLabel, fileTextField );
                if( useMultipleImport() ) {
                    File[] files = this.getSelectedFiles();
                    getMappingFiles().clear();

                    for( int i = 0; i < files.length; ++i ) {
                        addFile( files[i], fileTextField );
                        fileTextField.setText( files[i].getName() );
                    }

                    fileTextField.setText( getMappingFiles().size() + " tracks to import" );
                    DefaultListModel<String> model = new DefaultListModel<>();
                    fillMultipleImportTable( model, getMappingFiles(), "Mapping file list:" );
                    multiTrackList.setModel( model );
                }
                else {
                    File file = this.getSelectedFile();
                    addFile( file, fileTextField );
                }
                isRequiredInfoSet();
            }


        };
        fc.setDirectoryProperty( "Converter.Filepath" );
        fc.setMultiSelectionEnabled( true );
        fc.openFileChooser( ReadXplorerFileChooser.OPEN_DIALOG );
    }//GEN-LAST:event_openFileButtonActionPerformed

    private void converterComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_converterComboBoxActionPerformed
        this.selectConverter();
        this.isRequiredInfoSet();
    }//GEN-LAST:event_converterComboBoxActionPerformed

    private void referenceNameFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_referenceNameFieldKeyTyped
        char input = evt.getKeyChar();
        if( input != '\b' ) {
            this.refChromName = this.referenceNameField.getText() + evt.getKeyChar();
        }
        else {
            this.refChromName = this.referenceNameField.getText();
        }
        this.isRequiredInfoSet();
    }//GEN-LAST:event_referenceNameFieldKeyTyped

    private void referenceLengthFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_referenceLengthFieldKeyTyped
        String input = String.valueOf( evt.getKeyChar() );
        String value = this.referenceLengthField.getText();
        String wholeInput = value.concat( input );
        if( input.equals( "\b" ) ) {
            if( GeneralUtils.isValidPositiveNumberInput( value ) ) {
                this.chromLength = Integer.valueOf( value );
            }
            else {
                this.chromLength = -1;
            }
        }
        else if( GeneralUtils.isValidPositiveNumberInput( wholeInput ) ) {
            this.chromLength = Integer.valueOf( wholeInput );
        }
        else {
            JOptionPane.showMessageDialog( this, "Please enter a numerical reference length larger than 0!", "Invalid Length", JOptionPane.ERROR_MESSAGE );
            this.chromLength = -1;
        }
        this.isRequiredInfoSet();

    }//GEN-LAST:event_referenceLengthFieldKeyTyped

    private void refComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refComboBoxActionPerformed
        this.updateChromComboBox();
    }//GEN-LAST:event_refComboBoxActionPerformed

    private void refCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refCheckBoxActionPerformed
        boolean useRefFromDb = this.refCheckBox.isSelected();
        this.setVisibleComponents( useRefFromDb );
        this.isRequiredInfoSet();
    }//GEN-LAST:event_refCheckBoxActionPerformed

    private void chromComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chromComboBoxActionPerformed
        this.selectedChrom = (PersistentChromosome) chromComboBox.getSelectedItem();
    }//GEN-LAST:event_chromComboBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<de.cebitec.readXplorer.databackend.dataObjects.PersistentChromosome> chromComboBox;
    private javax.swing.JLabel chromComboLabel;
    private javax.swing.JComboBox<ConverterI> converterComboBox;
    private javax.swing.JLabel converterLabel;
    private javax.swing.JLabel fileLabel;
    private javax.swing.JTextField fileTextField;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JList<String> multiTrackList;
    private javax.swing.JLabel multiTrackListLabel;
    private javax.swing.JScrollPane multiTrackScrollPane;
    private javax.swing.JButton openFileButton;
    private javax.swing.JCheckBox refCheckBox;
    private javax.swing.JComboBox<de.cebitec.readXplorer.databackend.dataObjects.PersistentReference> refComboBox;
    private javax.swing.JLabel refComboLabel;
    private javax.swing.JLabel refSelectionLabel;
    private javax.swing.JTextField referenceLengthField;
    private javax.swing.JLabel referenceLengthLabel;
    private javax.swing.JTextField referenceNameField;
    private javax.swing.JLabel referenceNameLabel;
    // End of variables declaration//GEN-END:variables


    /**
     * Selects the correct converter depending on the chosen one.
     */
    private void selectConverter() {
        Object selectedItem = this.converterComboBox.getSelectedItem();
        if( selectedItem instanceof ConverterI ) {
            ConverterI newConverter = (ConverterI) selectedItem;
            if( this.currentConverter != newConverter ) {
                this.currentConverter = newConverter;
                this.fileTextField.setText( "" );
            }
        }
    }


    /**
     * Fires the appropriate event, if the required info is set or not and the
     * conversion can be started or not.
     */
    public void isRequiredInfoSet() {
        canConvert = !getMappingFiles().isEmpty() && currentConverter != null
                     && (refChromName != null && !refChromName.isEmpty() && chromLength >= 0 || refCheckBox.isSelected());
        firePropertyChange( ConverterAction.PROP_CAN_CONVERT, null, canConvert );
    }


    public ConverterI getSelectedConverter() {
        return currentConverter;
    }


    /**
     * @return The length of the reference sequence.
     */
    public int getChromosomeLength() {
        if( this.refCheckBox.isSelected() && selectedChrom != null ) {
            return selectedChrom.getLength();
        }
        else {
            return chromLength;
        }
    }


    /**
     * @return The name of the chromosome to use as reference chromosome
     *         identifier for all mappings.
     */
    public String getRefChromosomeName() {
        if( this.refCheckBox.isSelected() && selectedChrom != null ) {
            return selectedChrom.getName();
        }
        else {
            return refChromName;
        }
    }


    @Override
    public String getName() {
        return NbBundle.getMessage( ConverterSetupCard.class, "CTL_ConverterSetupCard.name" );
    }


    /**
     * Set the reference genome components to their correct visibility state.
     * <p>
     * @param useRefFromDb true, if the options for a reference sequence from
     *                     the DB should be visible, false, if the options for manually entering
     *                     the reference data should be visible.
     */
    private void setVisibleComponents( boolean useRefFromDb ) {
        this.refCheckBox.setSelected( useRefFromDb );
        this.refComboBox.setVisible( useRefFromDb );
        this.refComboLabel.setVisible( useRefFromDb );
        this.chromComboBox.setVisible( useRefFromDb );
        this.chromComboLabel.setVisible( useRefFromDb );
        this.referenceLengthField.setVisible( !useRefFromDb );
        this.referenceLengthLabel.setVisible( !useRefFromDb );
        this.referenceNameField.setVisible( !useRefFromDb );
        this.referenceNameLabel.setVisible( !useRefFromDb );
    }


    /**
     * Updates the chromosome combo box with the chromosomes from the currently
     * selected refrence genome from the db.
     */
    private void updateChromComboBox() {
        if( this.refComboBox.getSelectedItem() != null ) {
            Collection<PersistentChromosome> chromCollection = ((PersistentReference) this.refComboBox.getSelectedItem()).getChromosomes().values();
            PersistentChromosome[] chroms = new PersistentChromosome[0];
            chroms = chromCollection.toArray( chroms );
            this.chromComboBox.setModel( new DefaultComboBoxModel<>( chroms ) );
            this.selectedChrom = (PersistentChromosome) chromComboBox.getSelectedItem();
        }
    }


}
