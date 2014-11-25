package de.cebitec.readXplorer.transcriptomeAnalyses.mainWizard;

import de.cebitec.readXplorer.databackend.dataObjects.PersistantTrack;
import de.cebitec.readXplorer.view.dialogMenus.OpenTracksWizardPanel;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;

/**
 * 
 * @author jritter
 */
public final class TranscriptomeAnalysisWizardIterator implements WizardDescriptor.Iterator<WizardDescriptor> {

    private static final String PROP_WIZARD_NAME = "TransAnalyses";
    // DATA type selection strings
    public static final String PROP_FIVEPRIME_DATASET = "fiveprime";
    public static final String PROP_WHOLEGENOME_DATASET = "wholegenome";
    // Fiveprime data set analyses
    public static final String PROP_TSS_ANALYSIS = "tssAnalysis";
    public static final String PROP_RBS_ANALYSIS = "rbsAnalysis";
    // RBS Analysis properties
    public static final String PROP_RBS_ANALYSIS_BASES_BFORE_I = "numberOfBasesBeforeI";
    public static final String PROP_RBS_ANALYSIS_BASES_AFTER_I = "numberOfBasesAfterI";
    public static final String PROP_RBS_ANALYSIS_WORKINGDIRECTORY_PATH = "pathToWorkinigDir";
    // Whole genome data set analyses
    public static final String PROP_NOVEL_ANALYSIS = "novel";
    public static final String PROP_OPERON_ANALYSIS = "operon";
    public static final String PROP_RPKM_ANALYSIS = "rpkm";
    public static final String PROP_MIN_BOUNDRY_LENGTH = "minimum boundry for length of new region";
    public static final String PROP_FRACTION_NOVELREGION_DETECTION = "fraction for novel region detection background calculations";
    // TSS, Leaderless, Antisense - detection params
    public static final String PROP_Fraction = "fraction";
    public static final String PROP_RATIO = "ratio";
    public static final String PROP_EXCLUDE_INTERNAL_TSS = "excludeInternalTss";
    public static final String PROP_EXCLUDE_TSS_DISTANCE = "excludeTssDistance";
    public static final String PROP_LEADERLESS_LIMIT = "leaderlessLimit";
    public static final String PROP_LEADERLESS_CDSSHIFT = "cdsShiftChoosen";
    public static final String PROP_NORMAL_RPKM_ANALYSIS = "normalRPKMs";
    public static final String PROP_KEEPINTERNAL_DISTANCE = "keepingInternalDistance";
    public static final String PROP_PUTATIVE_UNANNOTATED = "putative unannotated";
    public static final String PROP_PERCENTAGE_FOR_CDS_ANALYSIS = "percentage for cds-shift analysis";
    public static final String PROP_RAIO_NOVELREGION_DETECTION = "increase ratio value for novel region detection";
    public static final String PROP_INCLUDE_RATIOVALUE_IN_NOVEL_REGION_DETECTION = "inclusion of ratio value for novel region detection";
    // Wizard descriptors
    private List<WizardDescriptor.Panel<WizardDescriptor>> allPanels;
    private List<WizardDescriptor.Panel<WizardDescriptor>> initPanels;
    private List<WizardDescriptor.Panel<WizardDescriptor>> fivePrimeAnalyses;
    private List<WizardDescriptor.Panel<WizardDescriptor>> wholegenomeAnalyses;
    private List<WizardDescriptor.Panel<WizardDescriptor>> fivePrimeSelectedAnalyses;
    private List<WizardDescriptor.Panel<WizardDescriptor>> wholeGenomeSelectedAnayses;
    private List<WizardDescriptor.Panel<WizardDescriptor>> currentPanels;
    private String[] initPanelsIndex;
    private String[] fivePrimeIndex;
    private String[] wholeGenomeIndex;
    WizardDescriptor wiz;
    private int index;
    private OpenTracksWizardPanel openTracksPanel;
    private int referenceId;

    /**
     * 
     * @param referenceId 
     */
    public TranscriptomeAnalysisWizardIterator(int referenceId) {
        this.referenceId = referenceId;
        this.initializePanels();
    }

    /**
     * Initializes all Wizard Panels.
     */
    private void initializePanels() {
        if (allPanels == null) {
            allPanels = new ArrayList<>();
            allPanels.add(new DataSetChoicePanel());// 0
            allPanels.add(new WholeTranscriptTracksPanel(PROP_WIZARD_NAME)); // 1
            allPanels.add(new FivePrimeEnrichedTracksPanel(PROP_WIZARD_NAME, referenceId)); // 2
            openTracksPanel = new OpenTracksWizardPanel(PROP_WIZARD_NAME, referenceId); // 3
            allPanels.add(openTracksPanel); 


            String[] steps = new String[allPanels.size()];
            for (int i = 0; i < allPanels.size(); i++) {
                Component c = allPanels.get(i).getComponent();
                // Default step name to component name of panel.
                steps[i] = c.getName();
                if (c instanceof JComponent) { // assume Swing components
                    JComponent jc = (JComponent) c;
                    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, i);
                    jc.putClientProperty(WizardDescriptor.PROP_AUTO_WIZARD_STYLE, true);
                    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DISPLAYED, true);
                    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_NUMBERED, true);
                }
            }

            initPanels = new ArrayList<>();
            initPanels.add(this.allPanels.get(3));
            initPanels.add(this.allPanels.get(0));
            initPanels.add(this.allPanels.get(1));
            initPanelsIndex = new String[]{steps[3], steps[0], "..."};


            fivePrimeAnalyses = new ArrayList<>();
            fivePrimeAnalyses.add(this.allPanels.get(3));
            fivePrimeAnalyses.add(this.allPanels.get(0));
            fivePrimeAnalyses.add(this.allPanels.get(2));
            fivePrimeIndex = new String[]{steps[3], steps[0], steps[2], "..."};

            wholegenomeAnalyses = new ArrayList<>();
            wholegenomeAnalyses.add(this.allPanels.get(3));
            wholegenomeAnalyses.add(this.allPanels.get(0));
            wholegenomeAnalyses.add(this.allPanels.get(1));
            wholeGenomeIndex = new String[]{steps[3], steps[0], steps[1], "..."};

            fivePrimeSelectedAnalyses = new ArrayList<>();
            fivePrimeSelectedAnalyses.add(this.allPanels.get(3));
            fivePrimeSelectedAnalyses.add(this.allPanels.get(0));
            fivePrimeSelectedAnalyses.add(this.allPanels.get(2));

            wholeGenomeSelectedAnayses = new ArrayList<>();
            wholeGenomeSelectedAnayses.add(this.allPanels.get(3));
            wholeGenomeSelectedAnayses.add(this.allPanels.get(0));
            wholeGenomeSelectedAnayses.add(this.allPanels.get(1));

            this.currentPanels = initPanels;
            Component c = allPanels.get(3).getComponent();
            if (c instanceof JComponent) {
                JComponent jc = (JComponent) c;
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, initPanelsIndex);
            }
        }
    }

    @Override
    public WizardDescriptor.Panel<WizardDescriptor> current() {
        return this.currentPanels.get(index);
    }

    @Override
    public String name() {
        return index + 1 + ". from " + this.currentPanels.size();
    }

    @Override
    public boolean hasNext() {
        return index < currentPanels.size() - 1;
    }

    @Override
    public boolean hasPrevious() {
        return index > 0;
    }

    @Override
    public void nextPanel() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        if (index == 1) { //whole genome dataset
            String[] contentData = initPanelsIndex;

            if ((boolean) wiz.getProperty(PROP_FIVEPRIME_DATASET)) {
                this.currentPanels = this.fivePrimeAnalyses;
                contentData = this.fivePrimeIndex;
            }

            if ((boolean) wiz.getProperty(PROP_WHOLEGENOME_DATASET)) {
                this.currentPanels = this.wholegenomeAnalyses;
                contentData = this.wholeGenomeIndex;
            }

            if (contentData != null) {
                wiz.putProperty(WizardDescriptor.PROP_CONTENT_DATA, contentData);
            }
        }


        if (index == 2 && (boolean) wiz.getProperty(PROP_FIVEPRIME_DATASET)) { // we are in fifeprime analyses   
            List<String> contentData = new ArrayList<>();
            contentData.add(allPanels.get(3).getComponent().getName());
            contentData.add(allPanels.get(0).getComponent().getName());
            contentData.add(allPanels.get(2).getComponent().getName());

            this.currentPanels = this.fivePrimeSelectedAnalyses;
            if (!contentData.isEmpty()) {
                wiz.putProperty(WizardDescriptor.PROP_CONTENT_DATA, contentData);
            }
        }

        if (index == 2 && (boolean) wiz.getProperty(PROP_WHOLEGENOME_DATASET)) { // we are in wholegenome analyses
            List<String> contentData = new ArrayList<>();
            contentData.add(allPanels.get(3).getComponent().getName());
            contentData.add(allPanels.get(0).getComponent().getName());
            contentData.add(allPanels.get(1).getComponent().getName());
            
            this.currentPanels = this.wholeGenomeSelectedAnayses;
            if (!contentData.isEmpty()) {
                wiz.putProperty(WizardDescriptor.PROP_CONTENT_DATA, contentData);
            }
        }
        index++;
        wiz.putProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, index);
    }

    @Override
    public void previousPanel() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        String[] contentData = null;
        if (index == 1) {
            currentPanels = initPanels;
//            contentData = initPanelsIndex;
            if (contentData != null) {
                wiz.putProperty(WizardDescriptor.PROP_CONTENT_DATA, contentData);
            }
        }
        index--;
        wiz.putProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, index);
    }

    // If nothing unusual changes in the middle of the wizard, simply:
    @Override
    public void addChangeListener(ChangeListener l) {
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
    }

    /**
     * @param wiz the wizard, in which this wizard iterator is contained. If it
     * is not set, no properties can be stored, thus it always has to be set.
     */
    public void setWiz(WizardDescriptor wiz) {
        this.wiz = wiz;
    }

    /**
     * @return the wizard, in which this wizard iterator is contained.
     */
    public WizardDescriptor getWiz() {
        return wiz;
    }

    /**
     * @return The list of track selected in this wizard.
     */
    public List<PersistantTrack> getSelectedTracks() {
        return this.openTracksPanel.getComponent().getSelectedTracks();
    }
}