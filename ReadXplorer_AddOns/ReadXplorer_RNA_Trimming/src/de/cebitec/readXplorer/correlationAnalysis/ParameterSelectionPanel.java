package de.cebitec.readXplorer.correlationAnalysis;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

/**
 * Panel for the parameter selection as part of the correlation analysis wizard 
 * @author Evgeny Anisiforov <evgeny at cebitec.uni-bielefeld.de>
 */
class ParameterSelectionPanel implements WizardDescriptor.FinishablePanel<WizardDescriptor> {

    public ParameterSelectionPanel() {
        PropertyChangeListener pc = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                propertyChanged();
            }
        };
        
        this.getComponent().addPropertyChangeListener(CorrelationAnalysisAction.PROP_INTERVALLENGTH, pc);
        this.getComponent().addPropertyChangeListener(CorrelationAnalysisAction.PROP_MINCORRELATION, pc);
    }
    
    
    private void propertyChanged() {
        isValid = (component.getIntervalLength()>2) && (component.getMinimumCorrelation()>0);
        fireChangeEvent();
    }
    
    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private ParameterSelectionCard component;
    private boolean isValid;
    private final Set<ChangeListener> listeners = new HashSet<ChangeListener>(1); // or can use ChangeSupport in NB 6.0

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public Component getComponent() {
        if (component == null) {
            component = new ParameterSelectionCard();
        }
        return component;
    }

    @Override
    public HelpCtx getHelp() {
        // Show no Help button for this panel:
        return HelpCtx.DEFAULT_HELP;
        // If you have context help:
        // return new HelpCtx(SampleWizardPanel1.class);
    }

    @Override
    public boolean isValid() {
        return isValid;
    }

    @Override
    public boolean isFinishPanel() {
        return isValid;
    }

    @Override
    public final void addChangeListener(ChangeListener l) {
        synchronized (listeners) {
            listeners.add(l);
        }
    }

    @Override
    public final void removeChangeListener(ChangeListener l) {
        synchronized (listeners) {
            listeners.remove(l);
        }
    }

    protected final void fireChangeEvent() {
        Iterator<ChangeListener> it;
        synchronized (listeners) {
            it = new HashSet<ChangeListener>(listeners).iterator();
        }
        ChangeEvent ev = new ChangeEvent(this);
        while (it.hasNext()) {
            it.next().stateChanged(ev);
        }
    }

    @Override
    public void readSettings(WizardDescriptor data) {
        
    }

    @Override
    public void storeSettings(WizardDescriptor settings) {
        settings.putProperty(CorrelationAnalysisAction.PROP_INTERVALLENGTH, component.getIntervalLength());
        settings.putProperty(CorrelationAnalysisAction.PROP_MINCORRELATION, component.getMinimumCorrelation());
        settings.putProperty(CorrelationAnalysisAction.PROP_MINPEAKCOVERAGE, component.getMinimumPeakCoverage());
        settings.putProperty(CorrelationAnalysisAction.PROP_CORRELATIONCOEFFICIENT, component.getCorrelationMethod());
    }

}