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


import de.cebitec.readxplorer.databackend.AnalysesHandler;
import de.cebitec.readxplorer.databackend.ParametersReadClasses;
import de.cebitec.readxplorer.databackend.SaveFileFetcherForGUI;
import de.cebitec.readxplorer.databackend.connector.ProjectConnector;
import de.cebitec.readxplorer.databackend.connector.ReferenceConnector;
import de.cebitec.readxplorer.databackend.connector.TrackConnector;
import de.cebitec.readxplorer.databackend.dataObjects.DataVisualisationI;
import de.cebitec.readxplorer.databackend.dataObjects.PersistentChromosome;
import de.cebitec.readxplorer.databackend.dataObjects.PersistentFeature;
import de.cebitec.readxplorer.databackend.dataObjects.PersistentTrack;
import de.cebitec.readxplorer.transcriptionanalyses.differentialexpression.GnuR.JRILibraryNotInPathException;
import de.cebitec.readxplorer.transcriptionanalyses.differentialexpression.GnuR.PackageNotLoadableException;
import de.cebitec.readxplorer.transcriptionanalyses.differentialexpression.GnuR.UnknownGnuRException;
import de.cebitec.readxplorer.utils.Observable;
import de.cebitec.readxplorer.utils.Observer;
import de.cebitec.readxplorer.utils.Pair;
import de.cebitec.readxplorer.utils.Properties;
import de.cebitec.readxplorer.utils.classification.FeatureType;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.openide.util.Exceptions;
import org.rosuda.REngine.Rserve.RserveException;


/**
 * Abstract analysis handler for the differential gene expression. Takes care of
 * collecting all count data from each single AnalysisHandler of each track,
 * starting the processing by the chosen tool and displaying the results after
 * the calculations.
 *
 * @author kstaderm
 */
public abstract class DeAnalysisHandler extends Thread implements Observable,
                                                                  DataVisualisationI {

    private ReferenceConnector referenceConnector;
    private List<PersistentFeature> genomeAnnos;
    private final List<PersistentTrack> selectedTracks;
    private Map<Integer, CollectCoverageData> collectCoverageDataInstances;
    private final int refGenomeID;
    private List<ResultDeAnalysis> results;
    private final List<de.cebitec.readxplorer.utils.Observer> observerList = new ArrayList<>();
    private File saveFile = null;
    private final Set<FeatureType> selectedFeatureTypes;
    private final Map<Integer, Map<PersistentFeature, Integer>> allCountData = new HashMap<>();
    private int resultsReceivedBack = 0;
    private final int startOffset;
    private final int stopOffset;
    private final ParametersReadClasses readClassParams;


    public static enum Tool {

        ExpressTest( "Express Test" ), DeSeq( "DESeq" ), DeSeq2( "DESeq2" ), BaySeq( "baySeq" ), ExportCountTable( "Export only count table" );


        private Tool( String stringRep ) {
            this.stringRep = stringRep;
        }


        private final String stringRep;


        @Override
        public String toString() {
            return stringRep;
        }


        public static Tool[] usableTools() {
//            if( GnuR.SecureGnuRInitiliser.isGnuRSetUpCorrect() && GnuR.SecureGnuRInitiliser.isGnuRInstanceFree() ) {
//                return Tool.values();
//                //If one Tool should not be available to the user return something like :
//                //new Tool[]{ ExpressTest, DeSeq, BaySeq, ExportCountTable };
//
//            }
//            else {
//                Tool[] ret = new Tool[]{ ExpressTest, ExportCountTable };
//                return ret;
            return Tool.values();
//            }
        }


    }


    public static enum AnalysisStatus {

        RUNNING, FINISHED, ERROR;

    }


    /**
     * Abstract analysis handler for the differential gene expression. Takes
     * care of collecting all count data from each single AnalysisHandler of
     * each track, starting the processing by the chosen tool and displaying the
     * results after the calculations.
     *
     * @param selectedTracks       list of selected tracks for the analysis
     * @param refGenomeID          id of the selected reference genome
     * @param saveFile             file, in which some data for this analysis
     *                             can be stored
     * @param selectedFeatureTypes list of selected feature types to keep in the
     *                             list of analyzed genomic features
     * @param startOffset          offset in bases left of each feature start
     * @param stopOffset           offset in bases right of each feature stop
     * @param readClassParams      Parameter set of the selected read classes
     *                             for
     *                             this analysis
     */
    public DeAnalysisHandler( List<PersistentTrack> selectedTracks, int refGenomeID,
                              File saveFile, Set<FeatureType> selectedFeatureTypes, int startOffset, int stopOffset,
                              ParametersReadClasses readClassParams ) {
        ProcessingLog.getInstance().resetLog();
        this.selectedTracks = selectedTracks;
        this.refGenomeID = refGenomeID;
        this.saveFile = saveFile;
        this.selectedFeatureTypes = selectedFeatureTypes;
        this.startOffset = startOffset;
        this.stopOffset = stopOffset;
        this.readClassParams = readClassParams;
    }


    /**
     * Acutally starts the differential gene expression analysis.
     */
    private void startAnalysis() {
        collectCoverageDataInstances = new HashMap<>();
        Date currentTimestamp = new Timestamp( Calendar.getInstance().getTime().getTime() );
        Logger.getLogger( this.getClass().getName() ).log( Level.INFO, "{0}: Starting to collect the necessary data for the differential gene expression analysis.", currentTimestamp );
        referenceConnector = ProjectConnector.getInstance().getRefGenomeConnector( refGenomeID );
        List<AnalysesHandler> allHandler = new ArrayList<>();
        genomeAnnos = new ArrayList<>();

        for( PersistentChromosome chrom : referenceConnector.getRefGenome().getChromosomes().values() ) {
            genomeAnnos.addAll( referenceConnector.getFeaturesForRegionInclParents( 1, chrom.getLength(), selectedFeatureTypes, chrom.getId() ) );
        }

        for( PersistentTrack currentTrack : selectedTracks ) {
            try {
                TrackConnector tc = (new SaveFileFetcherForGUI()).getTrackConnector( currentTrack );

                CollectCoverageData collCovData = new CollectCoverageData( genomeAnnos, startOffset, stopOffset, readClassParams );
                collectCoverageDataInstances.put( currentTrack.getId(), collCovData );
                AnalysesHandler handler = new AnalysesHandler( tc, this, "Collecting coverage data for track "
                                                                         + currentTrack.getDescription() + ".", readClassParams );
                handler.setMappingsNeeded( true );
                handler.setDesiredData( Properties.REDUCED_MAPPINGS );
                handler.registerObserver( collCovData );
                allHandler.add( handler );
            }
            catch( SaveFileFetcherForGUI.UserCanceledTrackPathUpdateException ex ) {
                SaveFileFetcherForGUI.showPathSelectionErrorMsg();
                ProcessingLog.getInstance().addProperty( "Unresolved track", currentTrack );
                notifyObservers( AnalysisStatus.ERROR );
                this.interrupt();
                return;
            }
        }
        for( AnalysesHandler handler : allHandler ) {
            handler.startAnalysis();
        }
    }


    protected void prepareFeatures( DeAnalysisData analysisData ) {
        analysisData.setFeatures( genomeAnnos );
        analysisData.setSelectedTracks( selectedTracks );
    }


    protected void prepareCountData( final DeAnalysisData analysisData, final Map<Integer, Map<PersistentFeature, Integer>> allCountData ) {

        for( PersistentTrack pt : selectedTracks ) {
            Integer key = pt.getId();
            int[] data = new int[getPersAnno().size()];
            Map<PersistentFeature, Integer> currentTrack = allCountData.get( key );
            int j = 0;
            for( PersistentFeature persistentFeature : getPersAnno() ) {
                data[j] = currentTrack.get( persistentFeature );
                j++;
            }
            analysisData.addCountDataForTrack( data );
        }

    }


    /**
     * When all countData is collected this method is called and the processing
     * with the tool corresponding to the implementing class should start.
     *
     * @return
     */
    protected abstract List<ResultDeAnalysis> processWithTool() throws PackageNotLoadableException,
                                                                       JRILibraryNotInPathException, IllegalStateException,
                                                                       UnknownGnuRException, RserveException;


    /**
     * This is the final Method which is called when all windows associated with
     * the analysis are closed. So you should clean up everything and release
     * the Gnu R instance at this point.
     */
    public abstract void endAnalysis() throws RserveException;


    public void setResults( List<ResultDeAnalysis> results ) {
        this.results = results;
    }


    public Map<Integer, Map<PersistentFeature, Integer>> getAllCountData() {
        return allCountData;
    }


    public File getSaveFile() {
        return saveFile;
    }


    public List<PersistentFeature> getPersAnno() {
        return genomeAnnos;
    }


    public List<PersistentTrack> getSelectedTracks() {
        return selectedTracks;
    }


    public List<ResultDeAnalysis> getResults() {
        return results;
    }


    /**
     * @return Id of the reference, for which the analysis is carried out.
     */
    public int getRefGenomeID() {
        return refGenomeID;
    }


    public Map<Integer, CollectCoverageData> getCollectCoverageDataInstances() {
        return collectCoverageDataInstances;
    }


    @Override
    public void run() {
        notifyObservers( AnalysisStatus.RUNNING );
        startAnalysis();
    }


    @Override
    public void registerObserver( Observer observer ) {
        this.observerList.add( observer );
    }


    @Override
    public void removeObserver( Observer observer ) {
        this.observerList.remove( observer );
        if( this.observerList.isEmpty() ) {
            try {
                endAnalysis();
            } catch (RserveException ex) {
                Exceptions.printStackTrace(ex);
            }
            this.interrupt();
        }
    }


    @Override
    public void notifyObservers( Object data ) {
        //Copy the observer list to avoid concurrent modification exception
        List<Observer> tmpObserver = new ArrayList<>( observerList );
        for( Observer currentObserver : tmpObserver ) {
            currentObserver.update( data );
        }
    }


    @Override
    public synchronized void showData( Object data ) {
        Pair<Integer, String> res = (Pair<Integer, String>) data;
        allCountData.put( res.getFirst(), getCollectCoverageDataInstances().get( res.getFirst() ).getCountData() );

        if( ++resultsReceivedBack == getCollectCoverageDataInstances().size() ) {
            try {
                results = processWithTool();
            }
            catch( PackageNotLoadableException | UnknownGnuRException ex ) {
                Date currentTimestamp = new Timestamp( Calendar.getInstance().getTime().getTime() );
                Logger.getLogger( this.getClass().getName() ).log( Level.SEVERE, "{0}: " + ex.getMessage(), currentTimestamp );
                notifyObservers( AnalysisStatus.ERROR );
                JOptionPane.showMessageDialog( null, ex.getMessage(), "Gnu R Error", JOptionPane.WARNING_MESSAGE );
                this.interrupt();
            }
            catch( IllegalStateException ex ) {
                Date currentTimestamp = new Timestamp( Calendar.getInstance().getTime().getTime() );
                Logger.getLogger( this.getClass().getName() ).log( Level.WARNING, "{0}: " + ex.getMessage(), currentTimestamp );
                JOptionPane.showMessageDialog( null, ex.getMessage(), "Gnu R Error", JOptionPane.WARNING_MESSAGE );
            }
            catch( JRILibraryNotInPathException ex ) {
                Date currentTimestamp = new Timestamp( Calendar.getInstance().getTime().getTime() );
                Logger.getLogger( this.getClass().getName() ).log( Level.SEVERE, "{0}: " + ex.getMessage(), currentTimestamp );
                JOptionPane.showMessageDialog( null, ex.getMessage(), "Gnu R Error", JOptionPane.WARNING_MESSAGE );
            } catch (RserveException ex) {
                Date currentTimestamp = new Timestamp( Calendar.getInstance().getTime().getTime() );
                Logger.getLogger( this.getClass().getName() ).log( Level.SEVERE, "{0}: " + ex.getMessage(), currentTimestamp );
                JOptionPane.showMessageDialog( null, ex.getMessage(), "Gnu R Error", JOptionPane.WARNING_MESSAGE );
            }
            notifyObservers( AnalysisStatus.FINISHED );
        }
    }


    protected ReferenceConnector getReferenceConnector() {
        return referenceConnector;
    }


}
