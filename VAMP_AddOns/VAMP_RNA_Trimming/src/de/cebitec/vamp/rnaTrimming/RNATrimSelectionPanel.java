/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cebitec.vamp.rnaTrimming;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.WizardDescriptor.Panel;
import org.openide.util.HelpCtx;

/**
 *
 * @author jeff
 */
class RNATrimSelectionPanel implements WizardDescriptor.FinishablePanel<WizardDescriptor> {

    public RNATrimSelectionPanel() {
        this.getComponent().addPropertyChangeListener(RNATrimAction.PROP_SOURCEPATH, new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                isValid = !((String) evt.getNewValue()).isEmpty();
                fireChangeEvent();
            }
        });
    }

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private TrimSelectionCard component;
    private boolean isValid;
    private final Set<ChangeListener> listeners = new HashSet<ChangeListener>(1); // or can use ChangeSupport in NB 6.0

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public Component getComponent() {
        if (component == null) {
            component = new TrimSelectionCard();
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
        settings.putProperty(RNATrimAction.PROP_TRIMMETHOD, component.getTrimMethod());
        settings.putProperty(RNATrimAction.PROP_TRIMMAXIMUM, component.getTrimMaximum());
        settings.putProperty(RNATrimAction.PROP_SOURCEPATH, component.getSourcePath());
        settings.putProperty(RNATrimAction.PROP_REFERENCEPATH, component.getReferencePath());
    }

}