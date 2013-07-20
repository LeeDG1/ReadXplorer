/*
 * NewReferenceDialogPanel.java
 *
 * Created on 12.01.2011, 12:14:37
 */

package de.cebitec.vamp.ui.importer;

import de.cebitec.vamp.api.objects.NewJobDialogI;
import de.cebitec.vamp.parser.ReferenceJob;
import de.cebitec.vamp.parser.common.ParserI;
import de.cebitec.vamp.parser.common.ParsingException;
import de.cebitec.vamp.parser.reference.BioJavaGff3Parser;
import de.cebitec.vamp.parser.reference.BioJavaGffIdParser;
import de.cebitec.vamp.parser.reference.BioJavaParser;
import de.cebitec.vamp.parser.reference.FastaReferenceParser;
import de.cebitec.vamp.parser.reference.ReferenceParserI;
import de.cebitec.vamp.util.fileChooser.VampFileChooser;
import java.awt.Component;
import java.io.File;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.util.NbBundle;

/**
 * Panel displaying the options for importing new references into ReadXplorer.
 *
 * @author jwinneba, rhilker
 */
public class NewReferenceDialogPanel extends JPanel implements NewJobDialogI {
    
    private static final long serialVersionUID = 8362375;
    private File refSeqFile = null;
    private File refFeatureFile = null;
    private String referenceName = null;
    private String[] refSeqIds;
    private ReferenceParserI[] availableParsers = new ReferenceParserI[]{new BioJavaParser(BioJavaParser.EMBL), 
            new BioJavaParser(BioJavaParser.GENBANK), new BioJavaGff3Parser(), new FastaReferenceParser()};
    private ReferenceParserI currentParser;

    /** Panel displaying the options for importing new references into ReadXplorer. */
    public NewReferenceDialogPanel() {
        this.currentParser = this.availableParsers[0];
        this.initComponents();
        this.updateExtraComponents();
    }

    @Override
    public boolean isRequiredInfoSet() {
        if (refSeqFile == null || 
                nameField.getText().isEmpty() || 
                descriptionField.getText().isEmpty() || 
                currentParser instanceof BioJavaGff3Parser && refFeatureFile == null) {
            return false;
        }
        else {
            return true;
        }
    }
    
    /**
     * @return Creates and returns the reference job containing alle reference
     * data.
     */
    public ReferenceJob getReferenceJob() {
        return new ReferenceJob(null, refSeqFile, refFeatureFile, currentParser,
                descriptionField.getText(), referenceName,
                new Timestamp(System.currentTimeMillis()));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        filetypeBox = new javax.swing.JComboBox<>(availableParsers);
        filetypeLabel = new javax.swing.JLabel();
        fileLabel = new javax.swing.JLabel();
        fileField = new javax.swing.JTextField();
        fileChooserButton = new javax.swing.JButton();
        descriptionLabel = new javax.swing.JLabel();
        descriptionField = new javax.swing.JTextField();
        nameLabel = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        descriptionLabel1 = new javax.swing.JLabel();
        fileGffLabel = new javax.swing.JLabel();
        fileGffField = new javax.swing.JTextField();
        fileGffChooserButton = new javax.swing.JButton();
        referenceBox = new javax.swing.JComboBox<>();
        referenceLabel = new javax.swing.JLabel();

        filetypeBox.setRenderer(new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
                if(value instanceof ParserI){
                    return super.getListCellRendererComponent(list, ((ParserI) value).getName(), index, isSelected, cellHasFocus);
                } else {
                    return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                }
            }

        });
        filetypeBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filetypeBoxActionPerformed(evt);
            }
        });

        filetypeLabel.setText(org.openide.util.NbBundle.getMessage(NewReferenceDialogPanel.class, "NewReferenceDialogPanel.filetypeLabel.text")); // NOI18N

        fileLabel.setText(org.openide.util.NbBundle.getMessage(NewReferenceDialogPanel.class, "NewReferenceDialogPanel.fileLabel.text")); // NOI18N

        fileField.setEditable(false);
        fileField.setText(org.openide.util.NbBundle.getMessage(NewReferenceDialogPanel.class, "NewReferenceDialogPanel.fileField.text")); // NOI18N

        fileChooserButton.setText(org.openide.util.NbBundle.getMessage(NewReferenceDialogPanel.class, "NewReferenceDialogPanel.fileChooserButton.text")); // NOI18N
        fileChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileChooserButtonActionPerformed(evt);
            }
        });

        descriptionLabel.setText(org.openide.util.NbBundle.getMessage(NewReferenceDialogPanel.class, "NewReferenceDialogPanel.descriptionLabel.text")); // NOI18N

        nameLabel.setText(org.openide.util.NbBundle.getMessage(NewReferenceDialogPanel.class, "NewReferenceDialogPanel.nameLabel.text")); // NOI18N

        nameField.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                nameFieldPropertyChange(evt);
            }
        });

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText(org.openide.util.NbBundle.getMessage(NewReferenceDialogPanel.class, "NewReferenceDialogPanel.jTextArea1.text")); // NOI18N
        jTextArea1.setWrapStyleWord(true);
        jScrollPane1.setViewportView(jTextArea1);

        descriptionLabel1.setText(org.openide.util.NbBundle.getMessage(NewReferenceDialogPanel.class, "NewReferenceDialogPanel.descriptionLabel1.text")); // NOI18N

        fileGffLabel.setText(org.openide.util.NbBundle.getMessage(NewReferenceDialogPanel.class, "NewReferenceDialogPanel.fileGffLabel.text")); // NOI18N

        fileGffField.setEditable(false);
        fileGffField.setText(org.openide.util.NbBundle.getMessage(NewReferenceDialogPanel.class, "NewReferenceDialogPanel.fileGffField.text")); // NOI18N
        fileGffField.setToolTipText(org.openide.util.NbBundle.getMessage(NewReferenceDialogPanel.class, "NewReferenceDialogPanel.fileGffField.toolTipText")); // NOI18N

        fileGffChooserButton.setText(org.openide.util.NbBundle.getMessage(NewReferenceDialogPanel.class, "NewReferenceDialogPanel.fileGffChooserButton.text")); // NOI18N
        fileGffChooserButton.setToolTipText(org.openide.util.NbBundle.getMessage(NewReferenceDialogPanel.class, "NewReferenceDialogPanel.fileGffChooserButton.toolTipText")); // NOI18N
        fileGffChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileGffChooserButtonActionPerformed(evt);
            }
        });

        referenceBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                referenceBoxActionPerformed(evt);
            }
        });

        referenceLabel.setText(org.openide.util.NbBundle.getMessage(NewReferenceDialogPanel.class, "NewReferenceDialogPanel.referenceLabel.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(fileGffLabel)
                    .addComponent(fileLabel)
                    .addComponent(filetypeLabel)
                    .addComponent(referenceLabel)
                    .addComponent(nameLabel)
                    .addComponent(descriptionLabel)
                    .addComponent(descriptionLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                    .addComponent(descriptionField, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(nameField, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fileField)
                            .addComponent(fileGffField))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fileGffChooserButton, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(fileChooserButton, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(filetypeBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(referenceBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(filetypeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filetypeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fileField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fileLabel)
                    .addComponent(fileChooserButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fileGffField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fileGffLabel)
                    .addComponent(fileGffChooserButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(referenceBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(referenceLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(descriptionField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(descriptionLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(descriptionLabel1))
                .addContainerGap(58, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void filetypeBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filetypeBoxActionPerformed
        ReferenceParserI newparser = (ReferenceParserI) filetypeBox.getSelectedItem();
        if (currentParser != newparser) {
            currentParser = newparser;
            refSeqFile = null;
            refFeatureFile = null;
            referenceName = "";
            nameField.setText("");
            fileField.setText(NbBundle.getMessage(NewReferenceDialogPanel.class, "NewReferenceDialogPanel.fileField.text"));
            fileGffField.setText(NbBundle.getMessage(NewReferenceDialogPanel.class, "NewReferenceDialogPanel.fileGffField.text"));
            descriptionField.setText("");
            this.updateExtraComponents();
        }
}//GEN-LAST:event_filetypeBoxActionPerformed

    private void fileChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileChooserButtonActionPerformed
        ReferenceParserI usedParser = currentParser instanceof BioJavaGff3Parser ? new FastaReferenceParser() : currentParser;
        VampFileChooser fileChooser = new VampFileChooser(usedParser.getFileExtensions(), usedParser.getInputFileDescription()) {
            private static final long serialVersionUID = 1L;

            @Override
            public void save(String fileLocation) {
                throw new UnsupportedOperationException("Saving not supported here.");
            }

            @Override
            public void open(String fileLocation) {
                File file = new File(fileLocation);

                if (file.canRead()) {
                    refSeqFile = file;
                    Preferences prefs = Preferences.userNodeForPackage(NewReferenceDialogPanel.class);
                    prefs.put("RefGenome.Filepath", refSeqFile.getAbsolutePath());
                    fileField.setText(refSeqFile.getAbsolutePath());
                    nameField.setText(refSeqFile.getName());
                    referenceName = refSeqFile.getName();
                    descriptionField.setText(refSeqFile.getName());
                    try {
                        prefs.flush();
                    } catch (BackingStoreException ex) {
                        Logger.getLogger(NewReferenceDialogPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    Logger.getLogger(NewReferenceDialogPanel.class.getName()).log(Level.WARNING, "Could not read file");
                }
            }
        };
        fileChooser.setDirectoryProperty("RefGenome.Filepath");
        fileChooser.openFileChooser(VampFileChooser.OPEN_DIALOG);
}//GEN-LAST:event_fileChooserButtonActionPerformed

    private void fileGffChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileGffChooserButtonActionPerformed

        VampFileChooser fileChooser = new VampFileChooser(currentParser.getFileExtensions(), currentParser.getInputFileDescription()) {
            private static final long serialVersionUID = 1L;

            @Override
            public void save(String fileLocation) {
                throw new UnsupportedOperationException("Saving not supported here.");
            }

            @Override
            public void open(String fileLocation) {
                File file = new File(fileLocation);
                if (file.canRead()) {
                    refFeatureFile = file;
                    Preferences prefs = Preferences.userNodeForPackage(NewReferenceDialogPanel.class);
                    prefs.put("RefGenome.Filepath", refFeatureFile.getAbsolutePath());
                    fileGffField.setText(refFeatureFile.getAbsolutePath());
                    try {
                        prefs.flush();
                    } catch (BackingStoreException ex) {
                        Logger.getLogger(NewReferenceDialogPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    retrieveSequenceIds();
                } else {
                    Logger.getLogger(NewReferenceDialogPanel.class.getName()).log(Level.WARNING, "Could not read file");
                }
            }
        };
        
        fileChooser.setDirectoryProperty("RefGenome.Filepath");
        fileChooser.openFileChooser(VampFileChooser.OPEN_DIALOG);
    }//GEN-LAST:event_fileGffChooserButtonActionPerformed

    private void referenceBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_referenceBoxActionPerformed
        this.referenceName = (String) this.referenceBox.getSelectedItem();
        this.nameField.setText(this.referenceName);
    }//GEN-LAST:event_referenceBoxActionPerformed

    private void nameFieldPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_nameFieldPropertyChange
       this.referenceName = this.nameField.getText();
    }//GEN-LAST:event_nameFieldPropertyChange


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField descriptionField;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JLabel descriptionLabel1;
    private javax.swing.JButton fileChooserButton;
    private javax.swing.JTextField fileField;
    private javax.swing.JButton fileGffChooserButton;
    private javax.swing.JTextField fileGffField;
    private javax.swing.JLabel fileGffLabel;
    private javax.swing.JLabel fileLabel;
    private javax.swing.JComboBox<ReferenceParserI> filetypeBox;
    private javax.swing.JLabel filetypeLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JComboBox<String> referenceBox;
    private javax.swing.JLabel referenceLabel;
    // End of variables declaration//GEN-END:variables

    /**
     * Updates all components dependent on the chosen input file type.
     */
    private void updateExtraComponents() {
        if (currentParser instanceof BioJavaGff3Parser) {
            this.fileGffChooserButton.setVisible(true);
            this.fileGffField.setVisible(true);
            this.fileGffLabel.setVisible(true);
            this.referenceBox.setVisible(true);
            this.referenceLabel.setVisible(true);
            this.nameField.setVisible(false);
            this.nameLabel.setVisible(false);
            this.fileLabel.setText("Fasta file:");
        } else {
            this.fileGffChooserButton.setVisible(false);
            this.fileGffField.setVisible(false);
            this.fileGffLabel.setVisible(false);
            this.referenceBox.setVisible(false);
            this.referenceLabel.setVisible(false);
            this.nameField.setVisible(true);
            this.nameLabel.setVisible(true);
            this.fileLabel.setText("File:");
        }
    }

    /**
     * Fetches all sequence identifiers available in the currently set GFF 3 file
     * and adds them to the reference selection box.
     */
    private void retrieveSequenceIds() {
        final ProgressHandle ph = ProgressHandleFactory.createHandle(NbBundle.getMessage(NewReferenceDialogPanel.class, "MSG_NewReferenceDialogPanel.progress.name"));
        ph.start();
        ph.progress(NbBundle.getMessage(ImportThread.class, "MSG_NewReferenceDialogPanel.progress.scan"));
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
        BioJavaGffIdParser idParser = new BioJavaGffIdParser();
        try {
            List<String> seqIds = idParser.getSequenceIds(refFeatureFile);
            String[] seqIdArray = new String[1];
            refSeqIds = seqIds.toArray(seqIdArray);
//                    EventQueue.invokeLater(new Runnable() {
//
//                        @Override
//                        public void run() {
            referenceBox.setModel(new DefaultComboBoxModel<>(refSeqIds));
            referenceName = referenceBox.getSelectedItem().toString();
            this.nameField.setText(referenceName);
//                        }
//                    });
        } catch (ParsingException ex) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex.getMessage(), "Parsing Exception", JOptionPane.ERROR_MESSAGE);
        }
//            }
//        });
//        thread.start();
        ph.finish();
        
    }
}
