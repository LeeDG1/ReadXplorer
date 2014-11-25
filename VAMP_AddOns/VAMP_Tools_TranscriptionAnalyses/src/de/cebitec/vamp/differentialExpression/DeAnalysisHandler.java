package de.cebitec.vamp.differentialExpression;

import de.cebitec.vamp.databackend.AnalysesHandler;
import de.cebitec.vamp.databackend.connector.ProjectConnector;
import de.cebitec.vamp.databackend.connector.ReferenceConnector;
import de.cebitec.vamp.databackend.connector.TrackConnector;
import de.cebitec.vamp.databackend.dataObjects.DataVisualisationI;
import de.cebitec.vamp.databackend.dataObjects.PersistantFeature;
import de.cebitec.vamp.databackend.dataObjects.PersistantTrack;
import de.cebitec.vamp.differentialExpression.GnuR.JRILibraryNotInPathException;
import de.cebitec.vamp.differentialExpression.GnuR.PackageNotLoadableException;
import de.cebitec.vamp.differentialExpression.GnuR.UnknownGnuRException;
import de.cebitec.vamp.util.FeatureType;
import de.cebitec.vamp.util.Observable;
import de.cebitec.vamp.util.Pair;
import de.cebitec.vamp.view.dialogMenus.SaveTrackConnectorFetcherForGUI;
import java.io.File;
import java.sql.Timestamp;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * @author kstaderm
 */
public abstract class DeAnalysisHandler extends Thread implements Observable, DataVisualisationI {

    private ReferenceConnector referenceConnector;
    private int genomeSize;
    private List<PersistantFeature> persAnno;
    private List<PersistantTrack> selectedTraks;
    private Map<Integer, CollectCoverageData> collectCoverageDataInstances;
    private Integer refGenomeID;
    private List<ResultDeAnalysis> results;
    private List<de.cebitec.vamp.util.Observer> observer = new ArrayList<>();
    private File saveFile = null;
    private List<FeatureType> selectedFeatures;
    private Map<Integer, Map<PersistantFeature, Integer>> allCountData = new HashMap<>();
    private int resultsReceivedBack = 0;
    private int startOffset;
    private int stopOffset;
    public static boolean TESTING_MODE = false;

    public static enum Tool {

        SimpleTest("Simple Test"), DeSeq("DESeq"), BaySeq("baySeq");

        private Tool(String stringRep) {
            this.stringRep = stringRep;
        }
        private String stringRep;

        @Override
        public String toString() {
            return stringRep;
        }
    }

    public static enum AnalysisStatus {

        RUNNING, FINISHED, ERROR;
    }

    public DeAnalysisHandler(List<PersistantTrack> selectedTraks, Integer refGenomeID,
            File saveFile, List<FeatureType> selectedFeatures, int startOffset, int stopOffset) {
        ProcessingLog.getInstance().resetLog();
        this.selectedTraks = selectedTraks;
        this.refGenomeID = refGenomeID;
        this.saveFile = saveFile;
        this.selectedFeatures = selectedFeatures;
        this.startOffset = startOffset;
        this.stopOffset = stopOffset;
    }

    private void startAnalysis() {
        collectCoverageDataInstances = new HashMap<>();
        Date currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "{0}: Starting to collect the necessary data for the differential expression analysis.", currentTimestamp);
        referenceConnector = ProjectConnector.getInstance().getRefGenomeConnector(refGenomeID);
        genomeSize = referenceConnector.getRefGenome().getSequence().length();
        persAnno = referenceConnector.getFeaturesForRegion(1, genomeSize, selectedFeatures);
        List<AnalysesHandler> allHandler = new ArrayList<>();
        for (Iterator<PersistantTrack> it = selectedTraks.iterator(); it.hasNext();) {
            PersistantTrack currentTrack = it.next();
            TrackConnector tc = (new SaveTrackConnectorFetcherForGUI()).getTrackConnector(currentTrack);
            CollectCoverageData collCovData = new CollectCoverageData(persAnno, startOffset, stopOffset);
            collectCoverageDataInstances.put(currentTrack.getId(), collCovData);
            AnalysesHandler handler = new AnalysesHandler(tc, this, "Collecting coverage data of track number " + currentTrack.getId() + ".");
            handler.setReducedMappingsNeeded(true);
            handler.registerObserver(collCovData);
            allHandler.add(handler);
        }
        for (Iterator<AnalysesHandler> it = allHandler.iterator(); it.hasNext();) {
            AnalysesHandler handler = it.next();
            handler.startAnalysis();
        }
    }

    protected void prepareFeatures(DeAnalysisData analysisData) {
        analysisData.setFeatures(persAnno);
        analysisData.setSelectedTraks(selectedTraks);
    }

    protected void prepareCountData(DeAnalysisData analysisData, Map<Integer, Map<PersistantFeature, Integer>> allCountData) {
        for (Iterator<PersistantTrack> it = selectedTraks.iterator(); it.hasNext();) {
            Integer key = it.next().getId();
            Integer[] data = new Integer[getPersAnno().size()];
            Map<PersistantFeature, Integer> currentTrack = allCountData.get(key);
            int j = 0;
            for (Iterator<PersistantFeature> it1 = getPersAnno().iterator(); it1.hasNext(); j++) {
                PersistantFeature persistantFeature = it1.next();
                data[j] = currentTrack.get(persistantFeature);
            }
            analysisData.addCountDataForTrack(data);
        }
        //Kill all the references to allCountData...
        allCountData = null;
        //...so the GC will clean them up and free lots of memory.
        System.gc();
    }

    /**
     * When all countData is collected this method is called and the processing
     * with the tool corresponding to the implementing class should start.
     *
     * @return
     */
    protected abstract List<ResultDeAnalysis> processWithTool() throws PackageNotLoadableException,
            JRILibraryNotInPathException, IllegalStateException, UnknownGnuRException;

    /**
     * This is the final Method which is called when all windows associated with
     * the analysis are closed. So you should clean up everything and release
     * the Gnu R instance at this point.
     */
    public abstract void endAnalysis();

    public abstract void saveResultsAsCSV(int selectedIndex, String path);

    public void setResults(List<ResultDeAnalysis> results) {
        this.results = results;
    }

    public int getGenomeSize() {
        return genomeSize;
    }

    public Integer getRefGenomeID() {
        return refGenomeID;
    }

    public Map<Integer, Map<PersistantFeature, Integer>> getAllCountData() {
        return allCountData;
    }

    public File getSaveFile() {
        return saveFile;
    }

    public List<PersistantFeature> getPersAnno() {
        return persAnno;
    }

    public List<PersistantTrack> getSelectedTracks() {
        return selectedTraks;
    }

    public List<ResultDeAnalysis> getResults() {
        return results;
    }

    public Map<Integer, CollectCoverageData> getCollectCoverageDataInstances() {
        return collectCoverageDataInstances;
    }

    @Override
    public void run() {
        notifyObservers(AnalysisStatus.RUNNING);
        startAnalysis();
    }

    @Override
    public void registerObserver(de.cebitec.vamp.util.Observer observer) {
        this.observer.add(observer);
    }

    @Override
    public void removeObserver(de.cebitec.vamp.util.Observer observer) {
        this.observer.remove(observer);
        if (this.observer.isEmpty()) {
            endAnalysis();
            this.interrupt();
        }
    }

    @Override
    public void notifyObservers(Object data) {
        List<de.cebitec.vamp.util.Observer> tmpObserver = new ArrayList<>(observer);
        for (Iterator<de.cebitec.vamp.util.Observer> it = tmpObserver.iterator(); it.hasNext();) {
            de.cebitec.vamp.util.Observer currentObserver = it.next();
            currentObserver.update(data);
        }
    }

    @Override
    public synchronized void showData(Object data) {
        Pair<Integer, String> res = (Pair<Integer, String>) data;
        allCountData.put(res.getFirst(), getCollectCoverageDataInstances().get(res.getFirst()).getCountData());

        if (++resultsReceivedBack == getCollectCoverageDataInstances().size()) {
            try {
                results = processWithTool();
            } catch (PackageNotLoadableException | UnknownGnuRException ex) {
                Date currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "{0}: " + ex.getMessage(), currentTimestamp);
                notifyObservers(AnalysisStatus.ERROR);
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Gnu R Error", JOptionPane.WARNING_MESSAGE);
                this.interrupt();
            } catch (IllegalStateException ex) {
                Date currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
                Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "{0}: " + ex.getMessage(), currentTimestamp);
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Gnu R Error", JOptionPane.WARNING_MESSAGE);
            } catch (JRILibraryNotInPathException ex) {
                Date currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "{0}: " + ex.getMessage(), currentTimestamp);
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Gnu R Error", JOptionPane.WARNING_MESSAGE);
            }
        }
        notifyObservers(AnalysisStatus.FINISHED);
    }
}