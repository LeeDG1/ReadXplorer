package de.cebitec.readXplorer.tools.snp;

import de.cebitec.readXplorer.view.dialogMenus.ChangeListeningWizardPanel;
import org.openide.WizardDescriptor;

/**
 * The SNP detection wizard main panel.
 */
public class SNPWizardPanel extends ChangeListeningWizardPanel {
    
    public static final String PROP_MIN_PERCENT = "minPercent";
    public static final String PROP_MIN_VARYING_BASES = "minNoBases";
    public static final String PROP_USE_MAIN_BASE = "useMainBase";
    
    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private SNPVisualPanel component;

    /**
     * The SNP detection wizard main panel.
     * @param referenceId reference id 
     */
    public SNPWizardPanel() {
        super("Please enter valid parameters (only number and percent values)");
    }
    
    

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public SNPVisualPanel getComponent() {
        if (component == null) {
            component = new SNPVisualPanel();
        }
        return component;
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        if (this.isValid()) {
            wiz.putProperty(PROP_MIN_PERCENT, this.component.getMinPercentage());
            wiz.putProperty(PROP_MIN_VARYING_BASES, this.component.getMinMismatchingBases());
            wiz.putProperty(PROP_USE_MAIN_BASE, this.component.isUseMainBase());
        }
    }
}