package de.cebitec.readXplorer.tools.snp;

import de.cebitec.readXplorer.databackend.AnalysesHandler;
import de.cebitec.readXplorer.databackend.ParametersReadClasses;
import de.cebitec.readXplorer.databackend.SaveFileFetcherForGUI;
import de.cebitec.readXplorer.databackend.connector.TrackConnector;
import de.cebitec.readXplorer.databackend.dataObjects.DataVisualisationI;
import de.cebitec.readXplorer.databackend.dataObjects.PersistantTrack;
import de.cebitec.readXplorer.util.FeatureType;
import de.cebitec.readXplorer.util.GeneralUtils;
import de.cebitec.readXplorer.util.Pair;
import de.cebitec.readXplorer.util.VisualisationUtils;
import de.cebitec.readXplorer.view.dataVisualisation.referenceViewer.ReferenceViewer;
import de.cebitec.readXplorer.view.dialogMenus.OpenTracksWizardPanel;
import de.cebitec.readXplorer.view.dialogMenus.SelectFeatureTypeWizardPanel;
import de.cebitec.readXplorer.view.dialogMenus.SelectReadClassWizardPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
 * Action for opening a new SNP and DIP detection. It opens a track list
 * containing all tracks of the selected reference and creates a new snp
 * detection setup top component when tracks were selected.
 * 
 * @author Rolf Hilker <rhilker at cebitec.uni-bielefeld.de>
 */
@ActionID(category = "Tools",
id = "de.cebitec.readXplorer.tools.snp.OpenSnpDetectionAction")
@ActionRegistration(iconBase = "de/cebitec/readXplorer/tools/snp/snpDetection.png",
displayName = "#CTL_OpenSNPDetection")
@ActionReferences({
    @ActionReference(path = "Menu/Tools", position = 125),
    @ActionReference(path = "Toolbars/Tools", position = 100)
})
@Messages("CTL_OpenSnpDetectionAction=OpenSnpDetectionAction")
public final class OpenSnpDetectionAction implements ActionListener, DataVisualisationI {

    private static final long serialVersionUID = 1L;
    private static final String PROP_WIZARD_NAME = "SNP_Wizard";
    
    private final ReferenceViewer context;
    
    private int referenceId;
    private List<PersistantTrack> tracks;
    private Map<Integer, PersistantTrack> trackMap;
    private SNP_DetectionTopComponent snpDetectionTopComp;
    private OpenTracksWizardPanel openTracksPanel;
    private SelectReadClassWizardPanel readClassWizPanel;
    private SelectFeatureTypeWizardPanel featureTypePanel;
    private Set<FeatureType> selFeatureTypes;
    private ParameterSetSNPs parametersSNPs;
    private boolean combineTracks;
    private Map<Integer, AnalysisSNPs> trackToAnalysisMap;
    
    private int finishedCovAnalyses = 0;
    private SNP_DetectionResultPanel snpDetectionResultPanel;

    /**
     * Action for opening a new snp detection. It opens a track list containing
     * all tracks of the selected reference and creates a new snp detection
     * setup top component when tracks were selected.
     */
    public OpenSnpDetectionAction(ReferenceViewer context) {
        this.context = context;
        this.referenceId = this.context.getReference().getId();
    }

    /**
     * Carries out the calculations for a complete SNP detection + opening the
     * corresponding TopComponent.
     * @param ev the event itself, which is not used currently
     */
    @Override
    public void actionPerformed(ActionEvent ev) {
        
        this.finishedCovAnalyses = 0;
        this.trackToAnalysisMap = new HashMap<>();
        
        //show track list
        this.runWizardAndSnpDetection();
    }
    
    /**
     * Initializes the setup wizard for the snp detection
     * @param trackIds the list of track ids for which the snp detection has to
     * be carried out
     */
    @Messages({ "TTL_SNPWizardTitle=SNP Detection Parameter Wizard",
                "TITLE_SNPDetectionTopComp=SNP Detection Window", 
                "HINT_SNPDetectionTopComp=This is a SNP Detection window"})
    private void runWizardAndSnpDetection() {
        
        @SuppressWarnings("unchecked")
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<>();
        this.openTracksPanel = new OpenTracksWizardPanel(PROP_WIZARD_NAME, referenceId);
        this.readClassWizPanel = new SelectReadClassWizardPanel(PROP_WIZARD_NAME);
        this.featureTypePanel = new SelectFeatureTypeWizardPanel(PROP_WIZARD_NAME);
        this.openTracksPanel.setReadClassVisualPanel(readClassWizPanel.getComponent());
        panels.add(openTracksPanel);
        panels.add(new SNPWizardPanel());
        panels.add(readClassWizPanel);
        panels.add(featureTypePanel);
        WizardDescriptor wiz = new WizardDescriptor(new WizardDescriptor.ArrayIterator<>(VisualisationUtils.getWizardPanels(panels)));
        
        // {0} will be replaced by WizardDesriptor.Panel.getComponent().getName()
        wiz.setTitleFormat(new MessageFormat("{0}"));
        wiz.setTitle(Bundle.TTL_SNPWizardTitle());
        
        //action to perform after successfully finishing the wizard
        boolean cancelled = DialogDisplayer.getDefault().notify(wiz) != WizardDescriptor.FINISH_OPTION;
        List<PersistantTrack> selectedTracks = openTracksPanel.getComponent().getSelectedTracks();
        if (!cancelled && !selectedTracks.isEmpty()) {
            this.tracks = new ArrayList<>();
            this.trackMap = new HashMap<>();
            this.tracks = selectedTracks;
            for (PersistantTrack track : this.tracks) {
                this.trackMap.put(track.getId(), track);
            }

            this.snpDetectionTopComp = (SNP_DetectionTopComponent) WindowManager.getDefault().findTopComponent("SNP_DetectionTopComponent");
            this.snpDetectionTopComp.setName(Bundle.TITLE_SNPDetectionTopComp());
            this.snpDetectionTopComp.setToolTipText(Bundle.HINT_SNP_DetectionTopComp());
            this.snpDetectionTopComp.open();
            this.startSNPDetection(wiz);
        }
    }
    
    /**
     * Starts the SNP detection.
     * @param wiz the wizard containing the SNP detection parameters
     */
    @SuppressWarnings("unchecked")
    private void startSNPDetection(final WizardDescriptor wiz) {
        int minVaryingBases = (int) wiz.getProperty(SNPWizardPanel.PROP_MIN_VARYING_BASES);
        int minPercentage = (int) wiz.getProperty(SNPWizardPanel.PROP_MIN_PERCENT);
        boolean useMainBase = (boolean) wiz.getProperty(SNPWizardPanel.PROP_USE_MAIN_BASE);
        this.selFeatureTypes = (Set<FeatureType>) wiz.getProperty(featureTypePanel.getPropSelectedFeatTypes());
        ParametersReadClasses readClassParams = (ParametersReadClasses) wiz.getProperty(readClassWizPanel.getPropReadClassParams());
        this.combineTracks = (boolean) wiz.getProperty(openTracksPanel.getPropCombineTracks());
        
        this.parametersSNPs = new ParameterSetSNPs(minVaryingBases, minPercentage, useMainBase, selFeatureTypes, readClassParams);
        TrackConnector connector;
        if (!combineTracks) {
            for (PersistantTrack track : tracks) {
                try {
                    connector = (new SaveFileFetcherForGUI()).getTrackConnector(track);
                } catch (SaveFileFetcherForGUI.UserCanceledTrackPathUpdateException ex) {
                    SaveFileFetcherForGUI.showPathSelectionErrorMsg();
                    continue;
                }
                
                 //every track has its own analysis handlers
                this.createAnalysis(connector, readClassParams);
            }
        } else {
            try {
                connector = (new SaveFileFetcherForGUI()).getTrackConnector(tracks, combineTracks);
                this.createAnalysis(connector, readClassParams);
                
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
    private void createAnalysis(TrackConnector connector, ParametersReadClasses readClassParams) {
        AnalysesHandler snpAnalysisHandler = connector.createAnalysisHandler(this,
                NbBundle.getMessage(OpenSnpDetectionAction.class, "MSG_AnalysesWorker.progress.name"), readClassParams);
        AnalysisSNPs analysisSNPs = new AnalysisSNPs(connector, parametersSNPs);
        snpAnalysisHandler.registerObserver(analysisSNPs);
        snpAnalysisHandler.setCoverageNeeded(true);
        snpAnalysisHandler.setDiffsAndGapsNeeded(true);
        trackToAnalysisMap.put(connector.getTrackID(), analysisSNPs);
        snpAnalysisHandler.startAnalysis();
    }

    /**
     * Actually prepares and shows the SNP detection result.
     * @param dataTypeObject a pair of an integer and a string = trackId and 
     * dataType. dataType has to be AnalysesHandler.DATA_TYPE_COVERAGE, if the
     * SNPs shall be shown
     */
    @Override
    public void showData(Object dataTypeObject) {
        try {
            @SuppressWarnings("unchecked")
            Pair<Integer, String> dataTypePair = (Pair<Integer, String>) dataTypeObject;
            int trackId = dataTypePair.getFirst();
            String dataType = dataTypePair.getSecond();

            if (dataType.equals(AnalysesHandler.DATA_TYPE_COVERAGE)) {

                ++finishedCovAnalyses;

                AnalysisSNPs analysisSNPs = trackToAnalysisMap.get(trackId);
                final SnpDetectionResult result = new SnpDetectionResult(analysisSNPs.getResults(), 
                        trackMap, referenceId, combineTracks, 2, 0);
                result.setParameters(parametersSNPs);

                SwingUtilities.invokeLater(new Runnable() { //because it is not called from the swing dispatch thread
                    @Override
                    public void run() {
                        if (snpDetectionResultPanel == null) {
                            snpDetectionResultPanel = new SNP_DetectionResultPanel();
                            snpDetectionResultPanel.setBoundsInfoManager(context.getBoundsInformationManager());
                        }
                        snpDetectionResultPanel.setReferenceGenome(context.getReference());
                        snpDetectionResultPanel.addResult(result);

                        if (finishedCovAnalyses >= tracks.size() || combineTracks) {

                            //get track name(s) for tab descriptions
                            String trackNames = GeneralUtils.generateConcatenatedString(result.getTrackNameList(), 120);
                            String panelName = "SNP Detection for " + trackNames + " (" + snpDetectionResultPanel.getDataSize() + " hits)";
                            snpDetectionTopComp.openDetectionTab(panelName, snpDetectionResultPanel);
                        }
                    }
                });
            }

        } catch (ClassCastException e) {
            //do nothing, we dont handle other data in this class
        }
    }

}