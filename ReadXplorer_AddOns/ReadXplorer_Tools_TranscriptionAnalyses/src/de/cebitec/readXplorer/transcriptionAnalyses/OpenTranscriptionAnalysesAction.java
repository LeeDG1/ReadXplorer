package de.cebitec.readXplorer.transcriptionAnalyses;

import de.cebitec.readXplorer.databackend.AnalysesHandler;
import de.cebitec.readXplorer.databackend.ParametersReadClasses;
import de.cebitec.readXplorer.databackend.connector.TrackConnector;
import de.cebitec.readXplorer.databackend.dataObjects.DataVisualisationI;
import de.cebitec.readXplorer.databackend.dataObjects.PersistantTrack;
import de.cebitec.readXplorer.transcriptionAnalyses.wizard.TranscriptionAnalysesWizardIterator;
import de.cebitec.readXplorer.util.FeatureType;
import de.cebitec.readXplorer.util.GeneralUtils;
import de.cebitec.readXplorer.util.Pair;
import de.cebitec.readXplorer.util.Properties;
import de.cebitec.readXplorer.view.dataVisualisation.referenceViewer.ReferenceViewer;
import de.cebitec.readXplorer.databackend.SaveFileFetcherForGUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.WindowManager;

/**
 * Action for opening a new transcription analyses frame. It opens a track list
 * containing all tracks of the selected reference and creates a new
 * transcription analyses frame when a track was chosen.
 *
 * @author Rolf Hilker <rhilker at cebitec.uni-bielefeld.de>
 */
@ActionID(category = "Tools",
        id = "de.cebitec.readXplorer.transcriptionAnalyses.OpenTranscriptionAnalysesAction")
@ActionRegistration(iconBase = "de/cebitec/readXplorer/transcriptionAnalyses/transcriptionAnalyses.png",
        displayName = "#CTL_OpenTranscriptionAnalysesAction")
@ActionReferences({
    @ActionReference(path = "Menu/Tools", position = 142, separatorAfter = 150),
    @ActionReference(path = "Toolbars/Tools", position = 187)
})
@Messages("CTL_OpenTranscriptionAnalysesAction=Transcription Analyses")
public final class OpenTranscriptionAnalysesAction implements ActionListener, DataVisualisationI {

    private TranscriptionAnalysesTopComponent transcAnalysesTopComp;
    private final ReferenceViewer refViewer;
    private int referenceId;
    private List<PersistantTrack> tracks;
    private int finishedCovAnalyses = 0;
    private int finishedMappingAnalyses = 0;
    private ParameterSetTSS parametersTss;
    private ParameterSetOperonDet parametersOperonDet;
    private ParameterSetRPKM parametersRPKM;
    private boolean combineTracks;
    private Map<Integer, PersistantTrack> trackMap;
    private Map<Integer, AnalysisContainer> trackToAnalysisMap;
    private ResultPanelTranscriptionStart transcriptionStartResultPanel;
    private ResultPanelOperonDetection operonResultPanel;
    private ResultPanelRPKM rpkmResultPanel;
    
    private boolean performTSSAnalysis;
    private boolean performOperonAnalysis;
    private boolean performRPKMAnalysis;
    private boolean autoTssParamEstimation = false;
    private int minTotalIncrease = 0;
    private int minPercentIncrease = 0;
    private int maxLowCovInitCount = 0;
    private int minLowCovIncrease = 0;
    private boolean performUnannotatedTranscriptDet = false;
    private int minTranscriptExtensionCov = 0;
    private int maxLeaderlessDistance = 0;
    private int minNumberReads = 0;
    private int maxNumberReads = 0;
    private boolean autoOperonParamEstimation = false;
    private int minSpanningReads = 0;
    private String readClassPropString;
    private Set<FeatureType> selRPKMFeatureTypes = new HashSet<>();
    private Set<FeatureType> selOperonFeatureTypes = new HashSet<>();
    private String selRPKMFeatureTypesPropString;
    private String selOperonFeatureTypesPropString;
    private String combineTracksPropString;

    /**
     * Action for opening a new transcription analyses frame. It opens a track
     * list containing all tracks of the selected reference and creates a new
     * transcription analyses frame when a track was chosen.
     * @param context the context of the action: the reference viewer which is
     * connected with this analysis
     */
    public OpenTranscriptionAnalysesAction(ReferenceViewer context) {
        this.refViewer = context;
        this.referenceId = this.refViewer.getReference().getId();
        this.transcAnalysesTopComp = (TranscriptionAnalysesTopComponent) WindowManager.getDefault().findTopComponent("TranscriptionAnalysesTopComponent");
        this.trackToAnalysisMap = new HashMap<>();
    }

    /**
     * Carries out the logic behind the transcription analyses action. This
     * means, it opens a configuration wizard and starts the analyses after
     * successfully finishing the wizard.
     *
     * @param ev the event, which is currently not used
     */
    @Override
    public void actionPerformed(ActionEvent ev) {
        this.runWizardAndTranscriptionAnalysis();
    }

    /**
     * Initializes the setup wizard for the transcription analyses.
     */
    private void runWizardAndTranscriptionAnalysis() {
        @SuppressWarnings("unchecked")
        TranscriptionAnalysesWizardIterator transWizardIterator = new TranscriptionAnalysesWizardIterator(referenceId);
        this.readClassPropString = transWizardIterator.getReadClassPropForWiz();
        this.selOperonFeatureTypesPropString = transWizardIterator.getPropSelectedOperonFeatTypes();
        this.selRPKMFeatureTypesPropString = transWizardIterator.getPropSelectedRPKMFeatTypes();
        this.combineTracksPropString = transWizardIterator.getCombineTracksPropForWiz();
        WizardDescriptor wiz = new WizardDescriptor(transWizardIterator);
        transWizardIterator.setWiz(wiz);
        // {0} will be replaced by WizardDesriptor.Panel.getComponent().getName()
        wiz.setTitleFormat(new MessageFormat("{0}"));
        wiz.setTitle(NbBundle.getMessage(OpenTranscriptionAnalysesAction.class, "TTL_TransAnalysesWizardTitle"));

        //action to perform after successfully finishing the wizard
        boolean cancelled = DialogDisplayer.getDefault().notify(wiz) != WizardDescriptor.FINISH_OPTION;
        List<PersistantTrack> selectedTracks = transWizardIterator.getSelectedTracks();
        if (!cancelled && !selectedTracks.isEmpty()) {
            this.tracks = selectedTracks;
            this.trackMap = new HashMap<>();
            for (PersistantTrack track : this.tracks) {
                this.trackMap.put(track.getId(), track);
            }

            this.transcAnalysesTopComp.open();
            this.startTransciptionAnalyses(wiz);

        } else if (selectedTracks.isEmpty() && !cancelled) {
            String msg = NbBundle.getMessage(OpenTranscriptionAnalysesAction.class, "CTL_OpenTranscriptionAnalysesInfo",
                    "No track selected. To start a transcription analysis at least one track has to be selected.");
            String title = NbBundle.getMessage(OpenTranscriptionAnalysesAction.class, "CTL_OpenTranscriptionAnalysesInfoTitle", "Info");
            JOptionPane.showMessageDialog(this.refViewer, msg, title, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Starts the transcription analyses.
     *
     * @param wiz the wizard containing the transcription analyses parameters
     */
    @SuppressWarnings("unchecked")
    private void startTransciptionAnalyses(WizardDescriptor wiz) {

        //obtain all analysis parameters
        performTSSAnalysis = (boolean) wiz.getProperty(TranscriptionAnalysesWizardIterator.PROP_TSS_ANALYSIS);
        performOperonAnalysis = (boolean) wiz.getProperty(TranscriptionAnalysesWizardIterator.PROP_OPERON_ANALYSIS);
        performRPKMAnalysis = (boolean) wiz.getProperty(TranscriptionAnalysesWizardIterator.PROP_RPKM_ANALYSIS);
        
        ParametersReadClasses readClassesParams = (ParametersReadClasses) wiz.getProperty(readClassPropString);
        this.combineTracks = (boolean) wiz.getProperty(combineTracksPropString);

        if (performTSSAnalysis) { //set values depending on the selected analysis functions (avoiding null pointers)
            autoTssParamEstimation = (boolean) wiz.getProperty(TranscriptionAnalysesWizardIterator.PROP_AUTO_TSS_PARAMS);
            minTotalIncrease = (int) wiz.getProperty(TranscriptionAnalysesWizardIterator.PROP_MIN_TOTAL_INCREASE);
            minPercentIncrease = (int) wiz.getProperty(TranscriptionAnalysesWizardIterator.PROP_MIN_PERCENT_INCREASE);
            maxLowCovInitCount = (int) wiz.getProperty(TranscriptionAnalysesWizardIterator.PROP_MAX_LOW_COV_INIT_COUNT);
            minLowCovIncrease = (int) wiz.getProperty(TranscriptionAnalysesWizardIterator.PROP_MIN_LOW_COV_INC);
            performUnannotatedTranscriptDet = (boolean) wiz.getProperty(TranscriptionAnalysesWizardIterator.PROP_UNANNOTATED_TRANSCRIPT_DET);
            minTranscriptExtensionCov = (int) wiz.getProperty(TranscriptionAnalysesWizardIterator.PROP_MIN_TRANSCRIPT_EXTENSION_COV);
            maxLeaderlessDistance = (int) wiz.getProperty(TranscriptionAnalysesWizardIterator.PROP_MAX_LEADERLESS_DISTANCE);
        }
        if (performOperonAnalysis) {
            autoOperonParamEstimation = (boolean) wiz.getProperty(TranscriptionAnalysesWizardIterator.PROP_AUTO_OPERON_PARAMS);
            minSpanningReads = (int) wiz.getProperty(TranscriptionAnalysesWizardIterator.PROP_MIN_SPANNING_READS);
            selOperonFeatureTypes = (Set<FeatureType>) wiz.getProperty(selOperonFeatureTypesPropString);
        }
        if (performRPKMAnalysis) {
            minNumberReads = (int) wiz.getProperty(TranscriptionAnalysesWizardIterator.PROP_MIN_NUMBER_READS);
            maxNumberReads = (int) wiz.getProperty(TranscriptionAnalysesWizardIterator.PROP_MAX_NUMBER_READS);
            selRPKMFeatureTypes = (Set<FeatureType>) wiz.getProperty(selRPKMFeatureTypesPropString);
        }
        //create parameter set for each analysis
        parametersTss = new ParameterSetTSS(performTSSAnalysis, autoTssParamEstimation, performUnannotatedTranscriptDet,
                minTotalIncrease, minPercentIncrease, maxLowCovInitCount, minLowCovIncrease, minTranscriptExtensionCov, 
                maxLeaderlessDistance, readClassesParams);
        parametersOperonDet = new ParameterSetOperonDet(performOperonAnalysis, minSpanningReads, autoOperonParamEstimation, selOperonFeatureTypes);
        parametersRPKM = new ParameterSetRPKM(performRPKMAnalysis, minNumberReads, maxNumberReads, selRPKMFeatureTypes);


        TrackConnector connector;
        if (!combineTracks) {
            for (PersistantTrack track : this.tracks) {

                try {
                    connector = (new SaveFileFetcherForGUI()).getTrackConnector(track);
                } catch (SaveFileFetcherForGUI.UserCanceledTrackPathUpdateException ex) {
                    SaveFileFetcherForGUI.showPathSelectionErrorMsg();
                    continue;
                }
                
                //every track has its own analysis handlers
                this.createAnalysis(connector, readClassesParams);
            }
        } else {
            try {
                connector = (new SaveFileFetcherForGUI()).getTrackConnector(tracks, combineTracks);
                this.createAnalysis(connector, readClassesParams); //every track has its own analysis handlers
                
            } catch (SaveFileFetcherForGUI.UserCanceledTrackPathUpdateException ex) {
                SaveFileFetcherForGUI.showPathSelectionErrorMsg();
            }

        }
    }
    
    /**
     * Creates the analysis for a TrackConnector.
     * @param connector the connector
     * @param readClassesParams the read class parameters
     */
    private void createAnalysis(TrackConnector connector, ParametersReadClasses readClassesParams) {
        AnalysisTranscriptionStart analysisTSS = null;
        AnalysisOperon analysisOperon = null;
        AnalysisRPKM analysisRPKM = null;

        AnalysesHandler covAnalysisHandler = connector.createAnalysisHandler(this,
                NbBundle.getMessage(OpenTranscriptionAnalysesAction.class, "MSG_AnalysesWorker.progress.name"), readClassesParams); 
        AnalysesHandler mappingAnalysisHandler = connector.createAnalysisHandler(this,
                NbBundle.getMessage(OpenTranscriptionAnalysesAction.class, "MSG_AnalysesWorker.progress.name"), readClassesParams);

        if (parametersTss.isPerformTSSAnalysis()) {

            if (parametersTss.isPerformUnannotatedTranscriptDet()) {
                analysisTSS = new AnalysisUnannotatedTransStart(connector, parametersTss);
            } else {
                analysisTSS = new AnalysisTranscriptionStart(connector, parametersTss);
            }
            covAnalysisHandler.registerObserver(analysisTSS);
            covAnalysisHandler.setCoverageNeeded(true);
            covAnalysisHandler.setDesiredData(Properties.READ_STARTS);
        }
        if (parametersOperonDet.isPerformOperonAnalysis()) {
            analysisOperon = new AnalysisOperon(connector, parametersOperonDet);

            mappingAnalysisHandler.registerObserver(analysisOperon);
            mappingAnalysisHandler.setMappingsNeeded(true);
            mappingAnalysisHandler.setDesiredData(Properties.REDUCED_MAPPINGS);
        }
        if (parametersRPKM.isPerformRPKMAnalysis()) {
            analysisRPKM = new AnalysisRPKM(connector, parametersRPKM);

            mappingAnalysisHandler.registerObserver(analysisRPKM);
            mappingAnalysisHandler.setMappingsNeeded(true);
            mappingAnalysisHandler.setDesiredData(Properties.REDUCED_MAPPINGS);
        }

        trackToAnalysisMap.put(connector.getTrackID(), new AnalysisContainer(analysisTSS, analysisOperon, analysisRPKM));
        covAnalysisHandler.startAnalysis();
        mappingAnalysisHandler.startAnalysis();
    }

    /**
     * Visualizes the data handed over to this method as defined by the
     * implementation.
     *
     * @param dataTypeObject the data object to visualize.
     */
    @Override
    public void showData(Object dataTypeObject) {
        try {
            @SuppressWarnings("unchecked")
            Pair<Integer, String> dataTypePair = (Pair<Integer, String>) dataTypeObject;
            final int trackId = dataTypePair.getFirst();
            final String dataType = dataTypePair.getSecond();

            SwingUtilities.invokeLater(new Runnable() { //because it is not called from the swing dispatch thread
                @Override
                public void run() {

                    //get track name(s) for tab descriptions
                    String trackNames;
                    
                    if (parametersTss.isPerformTSSAnalysis() && dataType.equals(AnalysesHandler.DATA_TYPE_COVERAGE)) {

                        ++finishedCovAnalyses;

                        //TODO: bp window of neighboring TSS parameter

                        AnalysisTranscriptionStart analysisTSS = trackToAnalysisMap.get(trackId).getAnalysisTSS();
                        parametersTss = analysisTSS.getParametersTSS(); //if automatic is on, the parameters are different now
                        if (transcriptionStartResultPanel == null) {
                            transcriptionStartResultPanel = new ResultPanelTranscriptionStart();
                            transcriptionStartResultPanel.setReferenceViewer(refViewer);
                        }

                        TssDetectionResult tssResult = new TssDetectionResult(analysisTSS.getResults(), trackMap, referenceId, combineTracks, 1, 0);
                        tssResult.setParameters(parametersTss);
                        transcriptionStartResultPanel.addResult(tssResult);

                        if (finishedCovAnalyses >= tracks.size() || combineTracks) {
                            trackNames = GeneralUtils.generateConcatenatedString(tssResult.getTrackNameList(), 120);
                            String panelName = "Detected TSSs for " + trackNames + " (" + transcriptionStartResultPanel.getDataSize() + " hits)";
                            transcAnalysesTopComp.openAnalysisTab(panelName, transcriptionStartResultPanel);
                        }
                    }
                    if (dataType.equals(AnalysesHandler.DATA_TYPE_MAPPINGS)) {
                        ++finishedMappingAnalyses;

                        if (parametersOperonDet.isPerformOperonAnalysis()) {

                            if (operonResultPanel == null) {
                                operonResultPanel = new ResultPanelOperonDetection(parametersOperonDet);
                                operonResultPanel.setBoundsInfoManager(refViewer.getBoundsInformationManager());
                            }
                            OperonDetectionResult operonDetectionResult = new OperonDetectionResult(trackMap,
                                    trackToAnalysisMap.get(trackId).getAnalysisOperon().getResults(), referenceId, combineTracks, 2, 0);
                            operonDetectionResult.setParameters(parametersOperonDet);
                            operonResultPanel.addResult(operonDetectionResult);

                            if (finishedMappingAnalyses >= tracks.size() || combineTracks) {
                                trackNames = GeneralUtils.generateConcatenatedString(operonDetectionResult.getTrackNameList(), 120);
                                String panelName = "Detected operons for " + trackNames + " (" + operonResultPanel.getDataSize() + " hits)";
                                transcAnalysesTopComp.openAnalysisTab(panelName, operonResultPanel);
                            }
                        }

                        if (parametersRPKM.isPerformRPKMAnalysis()) {
                            AnalysisRPKM rpkmAnalysis = trackToAnalysisMap.get(trackId).getAnalysisRPKM();
                            if (rpkmResultPanel == null) {
                                rpkmResultPanel = new ResultPanelRPKM();
                                rpkmResultPanel.setBoundsInfoManager(refViewer.getBoundsInformationManager());
                            }
                            RPKMAnalysisResult rpkmAnalysisResult = new RPKMAnalysisResult(trackMap,
                                    trackToAnalysisMap.get(trackId).getAnalysisRPKM().getResults(), referenceId, combineTracks, 1, 0    );
                            rpkmAnalysisResult.setParameters(parametersRPKM);
                            rpkmAnalysisResult.setNoGenomeFeatures(rpkmAnalysis.getNoGenomeFeatures());
                            rpkmResultPanel.addResult(rpkmAnalysisResult);

                            if (finishedMappingAnalyses >= tracks.size() || combineTracks) {
                                trackNames = GeneralUtils.generateConcatenatedString(rpkmAnalysisResult.getTrackNameList(), 120);
                                String panelName = "RPKM and read count values for " + trackNames + " (" + rpkmResultPanel.getDataSize() + " hits)";
                                transcAnalysesTopComp.openAnalysisTab(panelName, rpkmResultPanel);
                            }
                        }
                    }
                }
            });
        } catch (ClassCastException e) {
            //do nothing, we dont handle other data in this class
        }

    }

    /**
     * Container class for all available transcription analyses.
     */
    private class AnalysisContainer {

        private final AnalysisTranscriptionStart analysisTSS;
        private final AnalysisOperon analysisOperon;
        private final AnalysisRPKM analysisRPKM;

        /**
         * Container class for all available transcription analyses.
         */

        public AnalysisContainer(AnalysisTranscriptionStart analysisTSS, AnalysisOperon analysisOperon, AnalysisRPKM analysisRPKM) {
            this.analysisTSS = analysisTSS;
            this.analysisOperon = analysisOperon;
            this.analysisRPKM = analysisRPKM;
        }

        /**
         * @return The transcription start site analysis stored in this
         * container
         */
        public AnalysisTranscriptionStart getAnalysisTSS() {
            return analysisTSS;
        }

        /**
         * @return The operon detection stored in this container
         */
        public AnalysisOperon getAnalysisOperon() {
            return analysisOperon;
        }        
        
        public AnalysisRPKM getAnalysisRPKM() {
            return analysisRPKM;
        }
    }
}