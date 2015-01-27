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
import de.cebitec.readxplorer.databackend.connector.ProjectConnector;
import de.cebitec.readxplorer.parser.ReadPairJobContainer;
import de.cebitec.readxplorer.parser.ReferenceJob;
import de.cebitec.readxplorer.parser.TrackJob;
import de.cebitec.readxplorer.parser.mappings.ReadPairClassifierI;
import de.cebitec.readxplorer.ui.dialogmenus.ImportTrackBasePanel;
import de.cebitec.readxplorer.ui.importer.actions.ImportWizardAction;
import java.awt.Component;
import java.awt.Dialog;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.sql.Timestamp;
import java.util.List;
import javax.swing.JPanel;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;


/**
 * Allows to create new jobs to import and displays the summary of all currently
 * created jobs.
 *
 * @author ddoppmeier, rhilker
 */
public class ImportSetupCard extends JPanel {

    private static final long serialVersionUID = 127732323;
    private boolean canImport;
    public static final String PROP_HAS_JOBS = "hasJobs";
    public static final String PROP_JOB_SELECTED = "jobSelected";
    private int trackID = 0;


    /**
     * Allows to create new jobs to import and displays the summary of all
     * currently created jobs.
     */
    public ImportSetupCard() {
        initComponents();
        refJobView.addPropertyChangeListener( this.getJobPropListener() );
        trackJobView.addPropertyChangeListener( this.getJobPropListener() );
        readPairTrackJobsView.addPropertyChangeListener( this.getJobPropListener() );
        trackID = ProjectConnector.getInstance().getLatestTrackId();
    }


    private PropertyChangeListener getJobPropListener() {
        return new PropertyChangeListener() {

            @Override
            public void propertyChange( PropertyChangeEvent evt ) {
                if( evt.getPropertyName().equals( PROP_JOB_SELECTED ) ) {
                    if( (Boolean) evt.getNewValue() ) {
                        removeJob.setEnabled( true );
                    }
                    else {
                        removeJob.setEnabled( false );
                    }
                }
                else if( evt.getPropertyName().equals( PROP_HAS_JOBS ) ) {
                    setCanImport( (Boolean) evt.getNewValue() );
                }
            }


        };
    }


    void setRemoveButtonEnabled( boolean b ) {
        removeJob.setEnabled( b );
    }


    public void setCanImport( boolean canOrcannot ) {
        canImport = canOrcannot;
        firePropertyChange( ImportWizardAction.PROP_CAN_IMPORT, null, canImport );
    }


    public List<ReferenceJob> getRefJobList() {
        return refJobView.getJobs();
    }


    public List<TrackJob> getTrackJobList() {
        return trackJobView.getJobs();
    }


    public List<ReadPairJobContainer> getReadPairTrackJobList() {
        return readPairTrackJobsView.getJobs();
    }


    @Override
    public String getName() {
        return NbBundle.getMessage( ImportSetupCard.class, "CTL_ImportSetupCard.name" );
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        refJobView = new de.cebitec.readxplorer.ui.importer.RefJobView();
        trackJobView = new de.cebitec.readxplorer.ui.importer.TrackJobView();
        readPairTrackJobsView = new de.cebitec.readxplorer.ui.importer.ReadPairJobView();
        addJob = new javax.swing.JButton();
        removeJob = new javax.swing.JButton();

        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });
        jTabbedPane1.addTab("References", refJobView);
        jTabbedPane1.addTab("Tracks", trackJobView);

        final ReadPairClassifierI readPairCalculator = Lookup.getDefault().lookup(ReadPairClassifierI.class);
        if (readPairCalculator != null) {
            jTabbedPane1.addTab("Read Pair Tracks", readPairTrackJobsView);
        }

        addJob.setText(org.openide.util.NbBundle.getMessage(ImportSetupCard.class, "ImportSetupCard.button.newJob")); // NOI18N
        addJob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addJobActionPerformed(evt);
            }
        });

        removeJob.setText(org.openide.util.NbBundle.getMessage(ImportSetupCard.class, "ImportSetupCard.button.removeJob")); // NOI18N
        removeJob.setEnabled(false);
        removeJob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeJobActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(333, Short.MAX_VALUE)
                .addComponent(removeJob)
                .addGap(7, 7, 7)
                .addComponent(addJob)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addJob, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(removeJob, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );
    }// </editor-fold>//GEN-END:initComponents


    /**
     * Automatically uses the correct track id for the trackjobs which are
     * created here.
     * <p>
     * @param evt
     */
    private void addJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addJobActionPerformed
        Component c = jTabbedPane1.getSelectedComponent();
        if( c != null ) {
            String title;
            NewJobDialogI dialogPane;

            // initialise NewJobDialog parameters
            if( c instanceof RefJobView ) {
                title = NbBundle.getMessage( ImportSetupCard.class, "TTL_ImportSetupCard.dialog.title.reference" );
                dialogPane = new NewReferenceDialogPanel();
            }
            else if( c instanceof ReadPairJobView ) {
                title = NbBundle.getMessage( ImportSetupCard.class, "TTL_ImportSetupCard.dialog.title.readPairTrack" );
                dialogPane = new NewReadPairTracksDialogPanel();
                ((NewReadPairTracksDialogPanel) dialogPane).setReferenceJobs( refJobView.getJobs() );
            }
            else if( c instanceof TrackJobView ) {
                title = NbBundle.getMessage( ImportSetupCard.class, "TTL_ImportSetupCard.dialog.title.track" );
                dialogPane = new NewTrackDialogPanel();
                ((NewTrackDialogPanel) dialogPane).setReferenceJobs( this.refJobView.getJobs() );
            }
            else {
                title = null;
                dialogPane = null;
            }

            // create dialog
            DialogDescriptor newDialog = new DialogDescriptor( dialogPane, title, true, DialogDescriptor.OK_CANCEL_OPTION, DialogDescriptor.OK_OPTION, null );
            Dialog dialog = DialogDisplayer.getDefault().createDialog( newDialog );
            dialog.setVisible( true );

            // keep the dialog open until the required info is provided or the dialog is canceled
            while( newDialog.getValue() == DialogDescriptor.OK_OPTION && !dialogPane.isRequiredInfoSet() ) {
                DialogDisplayer.getDefault().notify( new NotifyDescriptor.Message( NbBundle.getMessage( ImportSetupCard.class, "MSG_ImportSetupCard.dialog.fillout" ), NotifyDescriptor.INFORMATION_MESSAGE ) );
                dialog.setVisible( true );
            }

            // do dialog specific stuff
            if( newDialog.getValue() == DialogDescriptor.OK_OPTION && dialogPane.isRequiredInfoSet() ) {
                if( dialogPane instanceof NewReferenceDialogPanel ) {
                    NewReferenceDialogPanel nrdp = (NewReferenceDialogPanel) dialogPane;
                    refJobView.add( nrdp.getReferenceJob() );

                }
                else if( dialogPane instanceof NewReadPairTracksDialogPanel ) {
                    NewReadPairTracksDialogPanel readPairPane = (NewReadPairTracksDialogPanel) dialogPane;

                    if( readPairPane.useMultipleImport() ) {
                        List<File> mappingFiles1 = readPairPane.getMappingFiles();
                        List<File> mappingFiles2 = readPairPane.getMappingFiles2();

                        int largestSize = mappingFiles1.size() > mappingFiles2.size() ? mappingFiles1.size() : mappingFiles2.size();

                        for( int i = 0; i < largestSize; i++ ) {
                            File file1 = null;
                            File file2 = null;
                            if( i < mappingFiles1.size() ) {
                                file1 = mappingFiles1.get( i );
                            }
                            if( i < mappingFiles2.size() ) {
                                file2 = mappingFiles2.get( i );
                            }
                            addReadPairJobToList( readPairPane, file1, file2 );
                        }
                    }
                    else {
                        addReadPairJobToList( readPairPane, readPairPane.getMappingFile1(), readPairPane.getMappingFile2() );
                    }

                }
                else if( dialogPane instanceof NewTrackDialogPanel ) {
                    NewTrackDialogPanel newTrackPanel = (NewTrackDialogPanel) dialogPane;

                    for( File mappingFile : newTrackPanel.getMappingFiles() ) {

                        TrackJob trackJob = createTrackJob( newTrackPanel, mappingFile );
                        trackJobView.add( trackJob );
                    }
                }

            }
        }
        else {
            // do nothing
        }
}//GEN-LAST:event_addJobActionPerformed

    private void removeJobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeJobActionPerformed
        Component c = jTabbedPane1.getSelectedComponent();
        if( c == null ) {
        }
        else if( c instanceof RefJobView ) {
            ReferenceJob job = refJobView.getSelectedItem();
            if( job.hasRegisteredTrackswithoutrRunJob() ) {
                NotifyDescriptor nd = new NotifyDescriptor.Message( NbBundle.getMessage( ImportSetupCard.class, "MSG_ImportSetupCard.dialog.problem.dependency" ), NotifyDescriptor.WARNING_MESSAGE );
                DialogDisplayer.getDefault().notify( nd );
            }
            else {
                refJobView.remove( job );
            }
        }
        else if( c instanceof ReadPairJobView ) {
            readPairTrackJobsView.remove( readPairTrackJobsView.getSelectedItem() );
        }
        else if( c instanceof TrackJobView ) {
            trackJobView.remove( trackJobView.getSelectedItem() );
        }
    }//GEN-LAST:event_removeJobActionPerformed

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        Component c = jTabbedPane1.getSelectedComponent();
        boolean isSelected = false;
        if( c instanceof RefJobView ) {
            if( refJobView.IsRowSelected() ) {
                isSelected = true;
            }
        }
        else if( c instanceof ReadPairJobView ) {
            if( readPairTrackJobsView.isRowSelected() ) {
                isSelected = true;
            }
        }
        else if( c instanceof TrackJobView ) {
            if( trackJobView.IsRowSelected() ) {
                isSelected = true;
            }
        }

        if( isSelected ) {
            setRemoveButtonEnabled( true );
        }
        else {
            setRemoveButtonEnabled( false );
        }
    }//GEN-LAST:event_jTabbedPane1StateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addJob;
    private javax.swing.JTabbedPane jTabbedPane1;
    private de.cebitec.readxplorer.ui.importer.ReadPairJobView readPairTrackJobsView;
    private de.cebitec.readxplorer.ui.importer.RefJobView refJobView;
    private javax.swing.JButton removeJob;
    private de.cebitec.readxplorer.ui.importer.TrackJobView trackJobView;
    // End of variables declaration//GEN-END:variables


    /**
     * Creates and adds a read pair job to the list of read pair jobs.
     * <p>
     * @param readPairPane
     */
    private void addReadPairJobToList( NewReadPairTracksDialogPanel readPairPane, File mappingFile1, File mappingFile2 ) {

        if( mappingFile1 == null ) {
            mappingFile1 = mappingFile2;
            mappingFile2 = null;
        }

        TrackJob trackJob1 = createTrackJob( readPairPane, mappingFile1 );
        TrackJob trackJob2 = null;
        if( mappingFile2 != null ) {
            trackJob2 = createTrackJob( readPairPane, mappingFile2 );
        }

        readPairTrackJobsView.add( new ReadPairJobContainer( trackJob1, trackJob2,
                                                                  readPairPane.getDistance(), readPairPane.getDeviation(), readPairPane.getOrientation() ) );
    }


    /**
     * Creates a new track job for a read pair import.
     * <p>
     * @param importPanel       panel with details
     * @param mappingFile       mapping file to add to the track job
     * @param useMultipleImport true, if multiple files were selected in the
     *                          panel
     * <p>
     * @return the new track job
     */
    private TrackJob createTrackJob( ImportTrackBasePanel importPanel, File mappingFile ) {
        ReferenceJob refJob = importPanel.getReferenceJob();
        TrackJob trackJob = new TrackJob( trackID, mappingFile,
                                          importPanel.useMultipleImport() && mappingFile != null ? mappingFile.getName() : importPanel.getTrackName(),
                                          refJob,
                                          importPanel.getCurrentParser(),
                                          importPanel.isAlreadyImported(),
                                          new Timestamp( System.currentTimeMillis() ) );
        trackID++;
        refJob.registerTrackWithoutRunJob( trackJob );
        return trackJob;
    }


}
