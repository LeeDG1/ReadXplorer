package de.cebitec.readXplorer.coverageAnalysis;

import de.cebitec.readXplorer.view.dialogMenus.ChangeListeningWizardPanel;
import org.openide.WizardDescriptor;

/**
 * Panel for showing and handling all available options for the coverage
 * analysis.
 * 
 * @author Tobias Zimmermann, Rolf Hilker <rhilker at mikrobio.med.uni-giessen.de>
 */
public class CoverageAnalysisWizardPanel extends ChangeListeningWizardPanel {

    public static final String MIN_COVERAGE_COUNT = "minCoverageCount";
    public static final String SUM_COVERAGE = "sumCoverage";
    public static final String COVERED_INTERVALS = "coveredIntervals";
    
    /**
     * Panel for showing and handling all available options for the coverage
     * analysis.
     */
    public CoverageAnalysisWizardPanel() {
        super("Please enter valid parameters (only positive numbers are allowed)");
    }
    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private CoverageAnalysisVisualPanel component;

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public CoverageAnalysisVisualPanel getComponent() {
        if (component == null) {
            component = new CoverageAnalysisVisualPanel();
        }
        return component;
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        if (isValid()) {
            wiz.putProperty(CoverageAnalysisWizardPanel.MIN_COVERAGE_COUNT, this.component.getMinCoverageCount());
            wiz.putProperty(CoverageAnalysisWizardPanel.SUM_COVERAGE, this.component.isSumCoverageOfBothStrands());
            wiz.putProperty(CoverageAnalysisWizardPanel.COVERED_INTERVALS, this.component.isDetectCoveredIntervals());
        }
    }
}