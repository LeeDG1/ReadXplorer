package de.cebitec.readXplorer.coverageAnalysis;

import de.cebitec.vamp.databackend.AnalysesHandler;
import de.cebitec.vamp.databackend.ParametersReadClasses;
import de.cebitec.vamp.databackend.connector.TrackConnector;
import de.cebitec.vamp.databackend.dataObjects.DataVisualisationI;
import de.cebitec.vamp.databackend.dataObjects.PersistantTrack;
import de.cebitec.vamp.util.Pair;
import de.cebitec.vamp.util.VisualisationUtils;
import de.cebitec.vamp.view.dataVisualisation.referenceViewer.ReferenceViewer;
import de.cebitec.vamp.view.dialogMenus.OpenTrackPanelList;
import de.cebitec.vamp.view.dialogMenus.SaveTrackConnectorFetcherForGUI;
import de.cebitec.vamp.view.dialogMenus.SelectReadClassWizardPanel;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.openide.DialogDescriptor;
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
 * Action for opening the coverage analysis. It opens the wizard and runs the
 * analysis after successfully finishing the wizard.
 *
 * @author Tobias Zimmermann, Rolf Hilker <rhilker at mikrobio.med.uni-giessen.de>
 */

@ActionID(
        category = "Tools",
        id = "CoverageAnalysis.OpenCoverageAnalysisAction")
@ActionRegistration(
        iconBase = "de/cebitec/readXplorer/coverageAnalysis/coveredIntervals.png",
        displayName = "#CTL_OpenCoverageAnalysisAction")
@ActionReferences({
    @ActionReference(path = "Menu/Tools", position = 149),
    @ActionReference(path = "Toolbars/Tools", position = 240)
})
@Messages("CTL_OpenCoverageAnalysisAction=Coverage Analysis")
public final class OpenCoverageAnalysisAction implements ActionListener, DataVisualisationI {

    private static final String PROP_WIZARD_NAME = "CoverageAnalysisWiz";
    private final ReferenceViewer context;
    private int referenceId;
    private List<PersistantTrack> tracks;
    private Map<Integer, AnalysisCoverage> trackToAnalysisMap;
    ParameterSetCoverageAnalysis parameters;
    private SelectReadClassWizardPanel readClassWizPanel;
    private CoverageAnalysisTopComponent coveredAnnoAnalysisTopComp;
    private HashMap<Integer, PersistantTrack> trackMap;
    private int finishedCovAnalyses = 0;
    private ResultPanelCoverageAnalysis coverageAnalysisResultPanel;

    /**
     * Action for opening the feature coverage analysis. It opens the wizard and
     * runs the analysis after successfully finishing the wizard.
     * @param context the currently active reference viewer
     */
    public OpenCoverageAnalysisAction(ReferenceViewer context) {
        this.context = context;
        this.referenceId = this.context.getReference().getId();
        this.trackToAnalysisMap = new HashMap<>();
    }

    /**
     * Carries out the logic behind the coverage analysis action. This means, it
     * opens a configuration wizard and starts the analysis after successfully
     * finishing the wizard.
     * @param ev the event, which is currently not used
     */
    @Override
    public void actionPerformed(ActionEvent ev) {
        OpenTrackPanelList otp = new OpenTrackPanelList(referenceId);
        DialogDescriptor dialogDescriptor = new DialogDescriptor(otp, NbBundle.getMessage(OpenCoverageAnalysisAction.class, "CTL_OpenTrackList"));
        Dialog selectTracksDialog = DialogDisplayer.getDefault().createDialog(dialogDescriptor);
        selectTracksDialog.setVisible(true);

        if (dialogDescriptor.getValue().equals(DialogDescriptor.OK_OPTION) && !otp.getSelectedTracks().isEmpty()) {
            this.tracks = otp.getSelectedTracks();
            this.trackMap = new HashMap<>();
            this.trackToAnalysisMap = new HashMap<>();
            this.finishedCovAnalyses = 0;
            this.coverageAnalysisResultPanel = null;
            for (PersistantTrack track : otp.getSelectedTracks()) {
                this.trackMap.put(track.getId(), track);
            }

            if (this.coveredAnnoAnalysisTopComp == null) {
                this.coveredAnnoAnalysisTopComp = (CoverageAnalysisTopComponent) WindowManager.getDefault().findTopComponent("CoverageAnalysisTopComponent");
            }
            this.coveredAnnoAnalysisTopComp.open();

            this.runWizarAndCoverageAnnoAnalysis();

        } else if (dialogDescriptor.getValue().equals(DialogDescriptor.OK_OPTION) && otp.getSelectedTracks().isEmpty()) {
            String msg = NbBundle.getMessage(OpenCoverageAnalysisAction.class, "CTL_OpenCoverageDetectionInfoTitle",
                    "No track selected. To start a coverage analysis at least one track has to be selected.");
            String title = NbBundle.getMessage(OpenCoverageAnalysisAction.class, "CTL_OpenCoverageDetectionInfo", "Info");
            JOptionPane.showMessageDialog(this.context, msg, title, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Runs the wizard and starts the feature coverage anaylsis, if the wizard
     * finished successfully.
     */
    private void runWizarAndCoverageAnnoAnalysis() {

        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<>();
        this.readClassWizPanel = new SelectReadClassWizardPanel(PROP_WIZARD_NAME);
        panels.add(new CoverageAnalysisWizardPanel());
        panels.add(readClassWizPanel);
        WizardDescriptor wiz = new WizardDescriptor(new WizardDescriptor.ArrayIterator<>(VisualisationUtils.getWizardPanels(panels)));
        // {0} will be replaced by WizardDesriptor.Panel.getComponent().getName()
        wiz.setTitleFormat(new MessageFormat("{0}"));
        wiz.setTitle(NbBundle.getMessage(OpenCoverageAnalysisAction.class, "CoverageAnalysisAction_Title"));

        //action to perform after successfully finishing the wizard
        boolean cancelled = DialogDisplayer.getDefault().notify(wiz) != WizardDescriptor.FINISH_OPTION;
        if (!cancelled) {
            this.startCoverageDetection(wiz);
        } else {
            if (!this.coveredAnnoAnalysisTopComp.hasComponents()) {
                this.coveredAnnoAnalysisTopComp.close();
            }
        }
    }

    /**
     * Starts the feature coverage analysis.
     * @param wiz the wizard containing the coverage analysis parameters
     */
    private void startCoverageDetection(WizardDescriptor wiz) {
        //get parameters
        int minCoverageCount = (int) wiz.getProperty(CoverageAnalysisWizardPanel.MIN_COVERAGE_COUNT);
        boolean isSumCoverage = (boolean) wiz.getProperty(CoverageAnalysisWizardPanel.SUM_COVERAGE);
        boolean detectCoveredIntervals = (boolean) wiz.getProperty(CoverageAnalysisWizardPanel.COVERED_INTERVALS);
        ParametersReadClasses readClassesParams = (ParametersReadClasses) wiz.getProperty(readClassWizPanel.getPropReadClassParams());

        parameters = new ParameterSetCoverageAnalysis(minCoverageCount, isSumCoverage, detectCoveredIntervals, readClassesParams);

        TrackConnector connector;
        for (PersistantTrack track : this.tracks) {
            try {
                connector = (new SaveTrackConnectorFetcherForGUI()).getTrackConnector(track);
            } catch (SaveTrackConnectorFetcherForGUI.UserCanceledTrackPathUpdateException ex) {
                JOptionPane.showMessageDialog(null, "You did not complete the track path selection. The track panel cannot be opened.", "Error resolving path to track", JOptionPane.INFORMATION_MESSAGE);
                continue;
            }

            AnalysesHandler covAnalysisHandler = connector.createAnalysisHandler(this,
                    NbBundle.getMessage(OpenCoverageAnalysisAction.class, "MSG_AnalysesWorker.progress.name"), readClassesParams); //every track has its own analysis handler

            AnalysisCoverage analysisCoverage = new AnalysisCoverage(connector, parameters);
            covAnalysisHandler.registerObserver(analysisCoverage);
            covAnalysisHandler.setCoverageNeeded(true);
            trackToAnalysisMap.put(track.getId(), analysisCoverage);
            covAnalysisHandler.startAnalysis();
        }
    }

    /**
     * Shows the results in the corresponding top component.
     * @param dataTypeObject the pair describing the result data. It has to
     * consist of the track id as the first element and the data type string as
     * the second element.
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

                AnalysisCoverage analysisCoverage = trackToAnalysisMap.get(trackId);
                analysisCoverage.finishAnalysis();
                final CoverageAnalysisResult result = new CoverageAnalysisResult(analysisCoverage.getResults(), trackMap);
           
                result.setParameters(parameters);
                
                SwingUtilities.invokeLater(new Runnable() { //because it is not called from the swing dispatch thread
                    @Override
                    public void run() {

                        if (coverageAnalysisResultPanel == null) {
                            coverageAnalysisResultPanel = new ResultPanelCoverageAnalysis();
                            coverageAnalysisResultPanel.setBoundsInfoManager(context.getBoundsInformationManager());
                        }
                        coverageAnalysisResultPanel.addCoverageAnalysis(result);

                        if (finishedCovAnalyses >= tracks.size()) {

                            //get track name(s) for tab descriptions
                            String trackNames = "";
                            if (tracks != null && !tracks.isEmpty()) {
                                for (PersistantTrack track : tracks) {
                                    trackNames = trackNames.concat(track.getDescription()).concat(" and ");
                                }
                                trackNames = trackNames.substring(0, trackNames.length() - 5);
                                if (trackNames.length() > 120) {
                                    trackNames = trackNames.substring(0, 120).concat("...");
                                }
                            }


                            String title;
                            if (parameters.isDetectCoveredIntervals()) {
                                title = "Detected covered intervals for ";
                            } else {
                                title = "Detected uncovered intervals for ";
                            }
                            String panelName = title + trackNames + " (" + coverageAnalysisResultPanel.getResultSize() + " hits)";
                            coveredAnnoAnalysisTopComp.openAnalysisTab(panelName, coverageAnalysisResultPanel);
                        }
                    }
                });
            }

        } catch (ClassCastException e) {
            //do nothing, we dont handle other data in this class
        }

    }
}
