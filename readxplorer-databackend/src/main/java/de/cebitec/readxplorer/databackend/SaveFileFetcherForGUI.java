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

package de.cebitec.readxplorer.databackend;


import de.cebitec.readxplorer.databackend.connector.MultiTrackConnector;
import de.cebitec.readxplorer.databackend.connector.ProjectConnector;
import de.cebitec.readxplorer.databackend.connector.DatabaseException;
import de.cebitec.readxplorer.databackend.connector.TrackConnector;
import de.cebitec.readxplorer.databackend.dataobjects.PersistentChromosome;
import de.cebitec.readxplorer.databackend.dataobjects.PersistentReference;
import de.cebitec.readxplorer.databackend.dataobjects.PersistentTrack;
import de.cebitec.readxplorer.utils.FastaUtils;
import htsjdk.samtools.SAMException;
import htsjdk.samtools.reference.IndexedFastaSequenceFile;
import htsjdk.samtools.util.RuntimeIOException;
import java.awt.Dialog;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.JOptionPane;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.util.NbBundle.Messages;
import org.openide.util.NbPreferences;

import static java.util.logging.Level.FINE;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Level.WARNING;
import static java.util.logging.Logger.getLogger;


/**
 * A class for GUI Components to safely fetching files within ReadXplorer.
 *
 * @author kstaderm, rhilker
 */
@Messages( { "TITLE_FileReset=Reset track file path",
             "MSG_FileReset=If you do not reset the track file location, it cannot be opened",
             "MSG_FileReset_StorageError=An error occured during storage of the new file path. Please try again" } )
public class SaveFileFetcherForGUI {

    private static final Logger LOG = getLogger( SaveFileFetcherForGUI.class.getName() );


    /**
     * A class for GUI Components to safely fetching files within ReadXplorer.
     */
    public SaveFileFetcherForGUI() {
    }


    /**
     * Returns the Persistent^Connector for the given track. If the track is
     * stored in a sam/bam file and the path to this file has changed, the
     * method will open a window and ask for the new file path.
     * <p>
     * @throws UserCanceledTrackPathUpdateException if the no track path could
     *                                              be resolved.
     * @param track Track the TrackConnector should be received for.
     * <p>
     * @return TrackConnector for the Track handed over
     */
    public TrackConnector getTrackConnector( PersistentTrack track ) throws UserCanceledTrackPathUpdateException {
        TrackConnector tc = null;
        ProjectConnector connector = ProjectConnector.getInstance();
        try {
            tc = connector.getTrackConnector( track );
        } catch( FileNotFoundException e ) {
            PersistentTrack newTrack = getNewFilePath( track, connector );
            if( newTrack != null ) {
                try {
                    tc = connector.getTrackConnector( newTrack );
                } catch( FileNotFoundException ex ) {
                    Date currentTimestamp = new Timestamp( Calendar.getInstance().getTime().getTime() );
                    LOG.log( SEVERE, "{0}: Unable to open data associated with track: " + track.getId(), currentTimestamp );
                }
            } else {
                //If the new path is not set by the user throw exception notifying about this
                throw new UserCanceledTrackPathUpdateException();
            }
        }
        return tc;
    }


    /**
     * Returns the TrackConnector for multiple given tracks. If the tracks are
     * stored in a sam/bam file and the path to this file has changed, the
     * method will open a window and ask for the new file path.
     * <p>
     * @throws UserCanceledTrackPathUpdateException if the no track path could
     *                                              be resolved.
     * @param tracks        List of tracks the TrackConnector should be received
     *                      for.
     * @param combineTracks boolean if the Tracks should be combined or not.
     * <p>
     * @return TrackConnector for the list of Tracks handed over. CAUTION:
     *         tracks are removed if their path cannot be resolved and the user
     *         refuses to set a new one.
     */
    public TrackConnector getTrackConnector( List<PersistentTrack> tracks, boolean combineTracks ) throws UserCanceledTrackPathUpdateException {
        TrackConnector tc = null;
        ProjectConnector connector = ProjectConnector.getInstance();
        try {
            tc = connector.getTrackConnector( tracks, combineTracks );
        } catch( FileNotFoundException e ) {
            //we keep track about the number of tracks with unresolved path errors.
            int unresolvedTracks = 0;
            for( int i = 0; i < tracks.size(); ++i ) {
                PersistentTrack track = tracks.get( i );
                if( !(new File( track.getFilePath() )).exists() ) {
                    PersistentTrack newTrack = getNewFilePath( track, connector );
                    //Everything is fine, path is set correctly
                    if( newTrack != null ) {
                        tracks.set( i, newTrack );
                    } else {
                        //User canceled path update, add an unresolved track
                        unresolvedTracks++;
                        //And remove the track with wrong path from the list of processed tracks.
                        tracks.remove( i );
                    }
                }
            }
            //All track paths are tested, if no path can be resolved an exception is thrown.
            if( unresolvedTracks == tracks.size() ) {
                throw new UserCanceledTrackPathUpdateException();
            }
            try {
                tc = connector.getTrackConnector( tracks, combineTracks );
            } catch( FileNotFoundException ex ) {
                Date currentTimestamp = new Timestamp( Calendar.getInstance().getTime().getTime() );
                LOG.log( SEVERE, "{0}: Unable to open data associated with track: " + tracks.toString(), currentTimestamp );
            }
        }
        return tc;
    }


    /**
     * In case a direct access track was moved to another place this method
     * first tries to find the track in the current directory used for resetting
     * track file paths and if it cannot be found it calls the
     * <tt>openResetFilePathDialog</tt> method to open a dialog for resetting
     * the file path to the current location of the file.
     * <p>
     * @param track     the track whose path has to be reseted
     * @param connector the connector
     * <p>
     * @return the track connector for the updated track or null, if it did not
     *         work
     * <p>
     * @author rhilker, kstaderm
     */
    private PersistentTrack getNewFilePath( PersistentTrack track, ProjectConnector connector ) {
        PersistentTrack newTrack;
        Preferences prefs = NbPreferences.forModule( Object.class );
        File oldTrackFile = new File( track.getFilePath() );
        String basePath = prefs.get( "ResetTrack.Filepath", "." );
        newTrack = this.checkFileExists( basePath, oldTrackFile, track, connector );
        if( newTrack == null ) {
            basePath = new File( connector.getDbLocation() ).getParentFile().getAbsolutePath();
            newTrack = this.checkFileExists( basePath, oldTrackFile, track, connector );
        }
        if( newTrack == null ) {
            newTrack = this.openResetFilePathDialog( track, connector );
        }
        return newTrack;
    }


    /**
     * Checks if a file exists and creates a new track, if it exists.
     * <p>
     * @param basePath
     * @param oldTrackFile the old track file to replace
     * @param track        the old track to replace
     * <p>
     * @return the new track, if the file exists, null otherwise
     *
     * @author rhilker, kstaderm
     */
    private PersistentTrack checkFileExists( String basePath, File oldTrackFile, PersistentTrack track, ProjectConnector connector ) {
        PersistentTrack newTrack = null;
        File newTrackFile = new File( basePath + "/" + oldTrackFile.getName() );
        if( newTrackFile.exists() && newTrackFile.isFile() ) {
            newTrack = new PersistentTrack( track.getId(),
                                            newTrackFile.getAbsolutePath(), track.getDescription(), track.getTimestamp(),
                                            track.getRefGenID(), track.getReadPairId() );
            try {
                SamBamFileReader reader = new SamBamFileReader( newTrackFile, track.getId(), connector.getRefGenomeConnector( track.getRefGenID() ).getRefGenome() );
                try {
                    connector.resetTrackPath( newTrack );
                } catch( DatabaseException ex ) {
                    JOptionPane.showMessageDialog( null, Bundle.MSG_FileReset_StorageError(), Bundle.TITLE_FileReset(), JOptionPane.INFORMATION_MESSAGE );
                }
            } catch( RuntimeIOException e ) {
                //nothing to do, we return a null track
                LOG.log( FINE, e.getMessage(), e );
            }
        }
        return newTrack;
    }


    /**
     * In case a track was moved to another place and cannot be found this
     * method opens a dialog for resetting the file path to the current location
     * of the file.
     * <p>
     * @param track     the track whose path has to be resetted
     * @param connector the connector
     * <p>
     * @return the track connector for the updated track or null, if it did not
     *         work
     *
     * @author rhilker, kstaderm
     */
    @Messages( { "MSG_WrongTrackFileChosen=You did not choose a \"bam\" file, please select a bam file to proceed." } )
    private PersistentTrack openResetFilePathDialog( PersistentTrack track, ProjectConnector connector ) {
        PersistentTrack newTrack = null;
        ResetFilePanel resetPanel = new ResetFilePanel( track.getFilePath() );
        DialogDescriptor dialogDescriptor = new DialogDescriptor( resetPanel, "Reset File Path" );
        Dialog resetFileDialog = DialogDisplayer.getDefault().createDialog( dialogDescriptor );
        resetFileDialog.setVisible( true );

        if( dialogDescriptor.getValue().equals( DialogDescriptor.OK_OPTION ) ) {
            if( resetPanel.getNewFileLocation() != null ) {
                File selectedFile = new File( resetPanel.getNewFileLocation() );
                if( selectedFile.exists() && selectedFile.isFile() && selectedFile.getName().endsWith( ".bam" ) ) {
                    try {
                        newTrack = new PersistentTrack( track.getId(),
                                                        resetPanel.getNewFileLocation(), track.getDescription(), track.getTimestamp(),
                                                        track.getRefGenID(), track.getReadPairId() );
                        connector.resetTrackPath( newTrack );
                        try {
                            TrackConnector trackConnector = connector.getTrackConnector( newTrack );
                        } catch( FileNotFoundException ex ) {
                            JOptionPane.showMessageDialog( null, Bundle.MSG_FileReset(), Bundle.TITLE_FileReset(), JOptionPane.INFORMATION_MESSAGE );
                        }
                    } catch( DatabaseException ex ) {
                        JOptionPane.showMessageDialog( null, Bundle.MSG_FileReset_StorageError(), Bundle.TITLE_FileReset(), JOptionPane.INFORMATION_MESSAGE );
                    }
                } else if( !selectedFile.getName().endsWith( ".bam" ) ) {
                    JOptionPane.showMessageDialog( null, Bundle.MSG_WrongTrackFileChosen(), Bundle.TITLE_FileReset(), JOptionPane.INFORMATION_MESSAGE );
                    this.openResetFilePathDialog( track, connector );
                }
            }
        } else {
            JOptionPane.showMessageDialog( null, Bundle.MSG_FileReset(), Bundle.TITLE_FileReset(), JOptionPane.INFORMATION_MESSAGE );
        }
        return newTrack;
    }


    public MultiTrackConnector getMultiTrackConnector( PersistentTrack track ) throws UserCanceledTrackPathUpdateException {
        MultiTrackConnector mtc = null;
        ProjectConnector connector = ProjectConnector.getInstance();
        try {
            mtc = connector.getMultiTrackConnector( track );
        } catch( FileNotFoundException e ) {
            PersistentTrack newTrack = getNewFilePath( track, connector );
            if( newTrack != null ) {
                try {
                    mtc = connector.getMultiTrackConnector( newTrack );
                } catch( FileNotFoundException ex ) {
                    Date currentTimestamp = new Timestamp( Calendar.getInstance().getTime().getTime() );
                    LOG.log( SEVERE, "{0}: Unable to open data associated with track: " + track.getId(), currentTimestamp );
                }
            } else {
                //If the new path is not set by the user throw exception notifying about this
                throw new UserCanceledTrackPathUpdateException();
            }
        }
        return mtc;
    }


    /**
     * Returns the TrackConnector for multiple given tracks. If the tracks are
     * stored in a sam/bam file and the path to this file has changed, the
     * method will open a window and ask for the new file path.
     * <p>
     * @throws UserCanceledTrackPathUpdateException if the no track path could
     *                                              be resolved.
     * @param tracks List of tracks the TrackConnector should be received for.
     * <p>
     * @return
     */
    public MultiTrackConnector getMultiTrackConnector( List<PersistentTrack> tracks ) throws UserCanceledTrackPathUpdateException {
        MultiTrackConnector mtc = null;
        ProjectConnector connector = ProjectConnector.getInstance();
        try {
            mtc = connector.getMultiTrackConnector( tracks );
        } catch( FileNotFoundException e ) {
            //we keep track about the number of tracks with unresolved path errors.
            int unresolvedTracks = 0;
            for( int i = 0; i < tracks.size(); ++i ) {
                PersistentTrack track = tracks.get( i );
                if( !(new File( track.getFilePath() )).exists() ) {
                    PersistentTrack newTrack = getNewFilePath( track, connector );
                    //Everything is fine, path is set correctly
                    if( newTrack != null ) {
                        tracks.set( i, newTrack );
                    } else {
                        //User canceled path update, add an unresolved track
                        unresolvedTracks++;
                        //And remove the track with wrong path from the list of processed tracks.
                        tracks.remove( i );
                    }
                }
            }
            //All track paths are tested, if no path can be resolved an exception is thrown.
            if( unresolvedTracks == tracks.size() ) {
                throw new UserCanceledTrackPathUpdateException();
            }
            try {
                mtc = connector.getMultiTrackConnector( tracks );
            } catch( FileNotFoundException ex ) {
                Date currentTimestamp = new Timestamp( Calendar.getInstance().getTime().getTime() );
                LOG.log( SEVERE, "{0}: Unable to open data associated with track: " + tracks.toString(), currentTimestamp );
            }
        }
        return mtc;
    }


    /**
     * Shows a JOptionPane with a message saying that the track path selection
     * failed.
     */
    public static void showPathSelectionErrorMsg() {
        JOptionPane.showMessageDialog( null, "You did not complete the track path selection. Corresponding viewers cannot be opened and analyses are canceled.", "Error resolving path to track", JOptionPane.INFORMATION_MESSAGE );
    }


    /**
     * Checks if the fasta file belonging to a reference exists and if not, it
     * tries to get a new path from the user. Missing fasta indices are
     * automatically recreated.
     * <p>
     * @param ref The reference whose fasta path has to be resetted
     * <p>
     * @return The indexed fasta file
     * <p>
     * @throws UserCanceledTrackPathUpdateException
     */
    @Messages( { "# {0} - fasta path", "MSG_FastaMissing=The following reference fasta file is missing! Please restore it in order to use this DB:\n {0}",
                 "TITLE_FastaMissing=Fasta missing error" } )
    public IndexedFastaSequenceFile checkRefFile( PersistentReference ref ) throws UserCanceledTrackPathUpdateException {
        File fastaFile = ref.getFastaFile();
        IndexedFastaSequenceFile indexedRefFile;
        FastaUtils fastaUtils = new FastaUtils(); //TODO observers are empty, add observers!
        if( fastaFile.exists() && fastaFile.isFile() ) {
            try( IndexedFastaSequenceFile testRefIndexFile = new IndexedFastaSequenceFile( fastaFile ) ) { //check for index and recreate it with notification, if necessary
                indexedRefFile = testRefIndexFile;
                try { //check if all entries in the file are valid, otherwise delete and recreate index
                    for( PersistentChromosome chrom : ref.getChromosomes().values() ) {
                        //just iterate them to see if they exist
                        indexedRefFile.getSubsequenceAt( chrom.getName(), 1, 2 );
                    }
                } catch( SAMException | NullPointerException e ) {
                    try {
                        indexedRefFile.close();
                        fastaUtils.deleteIndexFile( fastaFile );
                        fastaUtils.recreateMissingIndex( fastaFile );
                    } catch( IOException ex ) {
                        LOG.log( SEVERE, "'{'0'}': Unable to delete erroneous fasta index file. Please delete it manually and restart ReadXplorer.", fastaFile.getAbsolutePath() );
                    }
                }
            } catch( FileNotFoundException e ) {
                fastaUtils.recreateMissingIndex( fastaFile );
            } catch( IOException e ) {
                LOG.log( SEVERE, "'{'0'}': Unable to close fasta index file.", fastaFile.getAbsolutePath() );
            }
            indexedRefFile = fastaUtils.getIndexedFasta( fastaFile );

        } else {
            indexedRefFile = this.resetRefFile( ref );
        }
        if( indexedRefFile == null ) {
            //If the new path is not set by the user throw exception notifying about this
            throw new UserCanceledTrackPathUpdateException();
        }
        return indexedRefFile;
    }


    /**
     * In case a reference fasta file is missing and needs to be replaced, this
     * method offers a dialog to replace the old file with a new one in the
     * correct file format.
     * <p>
     * @param ref The reference whose fasta path has to be resetted
     * <p>
     * @return the new file or null, if it did not work
     *
     * @author rhilker
     */
    @Messages( { "MSG_WrongRefFileChosen=You did not choose a \"fasta\" file, please select a fasta file to proceed." } )
    private IndexedFastaSequenceFile resetRefFile( PersistentReference ref ) {
        IndexedFastaSequenceFile newFastaFile = null;
        ProjectConnector connector = ProjectConnector.getInstance();

        File newFile = new File( connector.getDbLocation() ).getParentFile();
        newFile = new File( newFile.getAbsolutePath() + "/" + ref.getFastaFile().getName() );

        if( !newFile.exists() ) {
            List<String> fileEndings = Arrays.asList( ".fasta", ".fa", ".fna", ".ffn" );
            newFile = this.openResetFilePathDialog( ref.getFastaFile(), fileEndings );
        }

        if( newFile != null ) {
            try {
                try {
                    newFastaFile = new IndexedFastaSequenceFile( newFile );
                } catch( FileNotFoundException ex ) { // we know the file exists, so only the index can be missing
                    FastaUtils fastaUtils = new FastaUtils();  //TODO observers are empty, add observers!
                    fastaUtils.recreateMissingIndex( newFile );
                }
                connector.resetReferencePath( newFile, ref );
            } catch( DatabaseException ex ) {
                JOptionPane.showMessageDialog( null, Bundle.MSG_FileReset_StorageError(), Bundle.TITLE_FileReset(), JOptionPane.INFORMATION_MESSAGE );
            }
        } else {
            JOptionPane.showMessageDialog( null, Bundle.MSG_FileReset(), Bundle.TITLE_FileReset(), JOptionPane.INFORMATION_MESSAGE );
        }

        return newFastaFile;
    }


    /**
     * In case a file is missing and needs to be replaced, this method offers a
     * dialog to replace the old file with a new one in the correct file format.
     * <p>
     * @param oldFile     Old file, whose path has to be resetted
     * @param fileEndings List of file endings to check
     * <p>
     * @return the new file or null, if it did not work
     *
     * @author rhilker
     */
    @Messages( { "# {0} - file formats", "MSG_WrongFileChosen=You did not choose a \"{0}\" file, please select a fasta file to proceed." } )
    private File openResetFilePathDialog( File oldFile, List<String> fileEndings ) {
        File newFile = null;
        ResetFilePanel resetPanel = new ResetFilePanel( oldFile.getAbsolutePath() );
        DialogDescriptor dialogDescriptor = new DialogDescriptor( resetPanel, "Reset File Path" );
        Dialog resetFileDialog = DialogDisplayer.getDefault().createDialog( dialogDescriptor );
        resetFileDialog.setVisible( true );

        if( dialogDescriptor.getValue().equals( DialogDescriptor.OK_OPTION ) && resetPanel.getNewFileLocation() != null ) {
            newFile = new File( resetPanel.getNewFileLocation() );

            boolean correctEnding = false;
            for( String fileEnding : fileEndings ) {
                if( newFile.getName().endsWith( fileEnding ) ) {
                    correctEnding = newFile.getName().endsWith( fileEnding );
                    break;
                }
            }

            if( newFile.exists() && newFile.isFile() && correctEnding ) {
                return newFile;

            } else if( !correctEnding ) {
                String msg;
                if( fileEndings.size() > 1 ) {
                    msg = Bundle.MSG_WrongFileChosen( fileEndings.get( 0 ) );
                } else {
                    msg = Bundle.MSG_WrongFileChosen( "correct" );
                }
                JOptionPane.showMessageDialog( null, msg, Bundle.TITLE_FileReset(), JOptionPane.INFORMATION_MESSAGE );
                newFile = this.openResetFilePathDialog( oldFile, fileEndings );
            }
        } else {
            JOptionPane.showMessageDialog( null, Bundle.MSG_FileReset(), Bundle.TITLE_FileReset(), JOptionPane.INFORMATION_MESSAGE );
        }
        return newFile;
    }


    /**
     * Exception which should be thrown if the user cancels the update of a
     * missing track file path.
     */
    public static class UserCanceledTrackPathUpdateException extends Exception {

        private static final String ERROR_MSG = "The user canceled the track path update. Thus, no TrackConnector can be created!";
//        private static final long serialVersionUID = 1L;


        /**
         * Exception which should be thrown if the user cancels the update of a
         * missing track file path.
         */
        public UserCanceledTrackPathUpdateException() {
            super( ERROR_MSG );
            Date currentTimestamp = new Timestamp( Calendar.getInstance().getTime().getTime() );
            LOG.log( WARNING, "{0}: " + ERROR_MSG, currentTimestamp );
        }


    }

}
