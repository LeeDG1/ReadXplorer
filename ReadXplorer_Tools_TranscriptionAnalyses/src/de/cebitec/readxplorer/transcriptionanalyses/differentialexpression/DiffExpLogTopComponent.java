/*
 * Copyright (C) 2014 Kai Bernd Stadermann <kstaderm at cebitec.uni-bielefeld.de>
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

package de.cebitec.readxplorer.transcriptionanalyses.differentialexpression;


import de.cebitec.readxplorer.ui.TopComponentExtended;
import de.cebitec.readxplorer.utils.Observer;
import de.cebitec.readxplorer.utils.fileChooser.ReadXplorerFileChooser;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;


/**
 * Top component which displays something.
 */
@ConvertAsProperties(
         dtd = "-//de.cebitec.readxplorer.transcriptionanalyses.differentialexpression//DiffExpLog//EN",
         autostore = false )
@TopComponent.Description(
         preferredID = "DiffExpLogTopComponent",
         //iconBase="SET/PATH/TO/ICON/HERE",
         persistenceType = TopComponent.PERSISTENCE_NEVER )
@TopComponent.Registration( mode = "output", openAtStartup = false )
@ActionID( category = "Window", id = "de.cebitec.readxplorer.transcriptionanalyses.differentialexpression.DiffExpLogTopComponent" )
@ActionReference( path = "Menu/Window" /*, position = 333 */ )
@TopComponent.OpenActionRegistration(
         displayName = "#CTL_DiffExpLogAction",
         preferredID = "DiffExpLogTopComponent" )
@Messages( {
    "CTL_DiffExpLogAction=DiffExpLog",
    "CTL_DiffExpLogTopComponent=Differential Gene Expression Analysis Log",
    "HINT_DiffExpLogTopComponent=Shows the log for the last run of the differential gene expression analysis."
} )
public final class DiffExpLogTopComponent extends TopComponentExtended
        implements Observer {

    private DeAnalysisHandler analysisHandler = null;


    public DiffExpLogTopComponent() {
        initComponents();
        setName( Bundle.CTL_DiffExpLogTopComponent() );
        setToolTipText( Bundle.HINT_DiffExpLogTopComponent() );
    }


    public DiffExpLogTopComponent( DeAnalysisHandler analysisHandler ) {
        this.analysisHandler = analysisHandler;
        initComponents();
        setName( Bundle.CTL_DiffExpLogTopComponent() );
        setToolTipText( Bundle.HINT_DiffExpLogTopComponent() );
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        saveLogButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        logTextField = new javax.swing.JTextArea();

        org.openide.awt.Mnemonics.setLocalizedText(saveLogButton, org.openide.util.NbBundle.getMessage(DiffExpLogTopComponent.class, "DiffExpLogTopComponent.saveLogButton.text_1")); // NOI18N
        saveLogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveLogButtonActionPerformed(evt);
            }
        });

        logTextField.setEditable(false);
        logTextField.setColumns(20);
        logTextField.setRows(5);
        jScrollPane1.setViewportView(logTextField);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 706, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(saveLogButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(saveLogButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void saveLogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveLogButtonActionPerformed
        ReadXplorerFileChooser fc = new ReadXplorerFileChooser( new String[]{ "txt" }, "txt" ) {
            private static final long serialVersionUID = 1L;


            @Override
            public void save( String fileLocation ) {
                File output = new File( fileLocation );
                String log = ProcessingLog.getInstance().generateLog();
                FileWriter writer;
                try {
                    writer = new FileWriter( output );
                    writer.write( log );
                    writer.close();
                }
                catch( IOException ex ) {
                    Date currentTimestamp = new Timestamp( Calendar.getInstance().getTime().getTime() );
                    Logger.getLogger( this.getClass().getName() ).log( Level.SEVERE, "{0}: " + ex.getMessage(), currentTimestamp );
                    JOptionPane.showMessageDialog( null, ex.getMessage(), "Could not write to file.", JOptionPane.WARNING_MESSAGE );
                }
            }


            @Override
            public void open( String fileLocation ) {
            }


        };
        fc.openFileChooser( ReadXplorerFileChooser.SAVE_DIALOG );
    }//GEN-LAST:event_saveLogButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea logTextField;
    private javax.swing.JButton saveLogButton;
    // End of variables declaration//GEN-END:variables


    @Override
    public void componentOpened() {
        String log = ProcessingLog.getInstance().generateLog();
        logTextField.setText( log );
    }


    @Override
    public void componentClosed() {
        if( analysisHandler != null ) {
            analysisHandler.removeObserver( this );
        }
    }


    void writeProperties( java.util.Properties p ) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty( "version", "1.0" );
        // TODO store your settings
    }


    void readProperties( java.util.Properties p ) {
        String version = p.getProperty( "version" );
        // TODO read your settings according to their version
    }


    @Override
    public void update( Object args ) {
        throw new UnsupportedOperationException( "Not supported yet." );
    }


}
