package de.cebitec.readXplorer.ui.dataAdministration;

import de.cebitec.readXplorer.databackend.connector.ProjectConnector;
import de.cebitec.readXplorer.databackend.dataObjects.PersistantReference;
import de.cebitec.readXplorer.databackend.dataObjects.PersistantTrack;
import de.cebitec.readXplorer.parser.Job;
import de.cebitec.readXplorer.parser.ReferenceJob;
import de.cebitec.readXplorer.parser.TrackJob;
import de.cebitec.readXplorer.ui.dataAdministration.actions.DataAdminWizardAction;
import de.cebitec.readXplorer.util.VisualisationUtils;
import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

public class DataAdminWizardSelectionPanel implements WizardDescriptor.FinishablePanel<WizardDescriptor> {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private SelectionCard component;
    private boolean isValid;
    private final Set<ChangeListener> listeners = new HashSet<>(1); // or can use ChangeSupport in NB 6.0

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public Component getComponent() {
        if (component == null) {
            component = new SelectionCard();
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
            it = new HashSet<>(listeners).iterator();
        }
        ChangeEvent ev = new ChangeEvent(this);
        while (it.hasNext()) {
            it.next().stateChanged(ev);
        }
    }

    private Map<String, List<? extends Job>> getDeletableReferencesAndTracks(){
        List<ReferenceJob> refJobs = new ArrayList<>();
        List<TrackJob> trackJobs = new ArrayList<>();
        HashMap<Integer, ReferenceJob> indexedRefs = new HashMap<>();
        
        try {

            List<PersistantReference> dbGens = ProjectConnector.getInstance().getGenomes();
            for (Iterator<PersistantReference> it = dbGens.iterator(); it.hasNext();) {
                PersistantReference dbGen = it.next();
                // File and parser parameter meaningless in this context
                ReferenceJob r = new ReferenceJob(dbGen.getId(), null, null, dbGen.getDescription(), dbGen.getName(), dbGen.getTimeStamp());
                indexedRefs.put(r.getID(), r);
                refJobs.add(r);
            }

            List<PersistantTrack> dbTracks = ProjectConnector.getInstance().getTracks();
            for (Iterator<PersistantTrack> it = dbTracks.iterator(); it.hasNext();) {
                PersistantTrack dbTrack = it.next();

                // File and parser, refgenjob, runjob parameters meaningless in this context
                boolean isDbUsed = dbTrack.getFilePath().isEmpty();
                TrackJob t = new TrackJob(dbTrack.getId(), isDbUsed, new File(dbTrack.getFilePath()), 
                        dbTrack.getDescription(), indexedRefs.get(dbTrack.getRefGenID()),
                        null, false, dbTrack.getTimestamp());

                // register dependent tracks at genome and run
                ReferenceJob gen = indexedRefs.get(dbTrack.getRefGenID());
                gen.registerTrackWithoutRunJob(t);
                trackJobs.add(t);
            }
        
        } catch (OutOfMemoryError e) {
            VisualisationUtils.displayOutOfMemoryError(this.component);
        }

        // fill result map
        Map<String, List<? extends Job>> deletableStuff = new HashMap<>();
        deletableStuff.put("references", refJobs);
        deletableStuff.put("tracks", trackJobs);
        return deletableStuff;
    }

    // You can use a settings object to keep track of state. Normally the
    // settings object will be the WizardDescriptor, so you can use
    // WizardDescriptor.getProperty & putProperty to store information entered
    // by the user.
    @Override
    @SuppressWarnings("unchecked")
    public void readSettings(WizardDescriptor settings) {
        // get deletable references and tracks
        Map<String, List<? extends Job>> possibleJobs = getDeletableReferencesAndTracks();

        List<ReferenceJob> refJobs = (List<ReferenceJob>) possibleJobs.get("references");
        List<TrackJob> trackJobs = (List<TrackJob>) possibleJobs.get("tracks");

        component.setSelectableJobs(refJobs, trackJobs);

        component.addPropertyChangeListener(SelectionCard.PROP_HAS_CHECKED_JOBS, new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                isValid = (Boolean) evt.getNewValue();
                fireChangeEvent();
            }
        });
    }

    @Override
    public void storeSettings(WizardDescriptor settings) {
        settings.putProperty(DataAdminWizardAction.PROP_REFS2DEL, component.getRef2DelJobs());
        settings.putProperty(DataAdminWizardAction.PROP_TRACK2DEL, component.getTrack2DelJobs());
    }
}