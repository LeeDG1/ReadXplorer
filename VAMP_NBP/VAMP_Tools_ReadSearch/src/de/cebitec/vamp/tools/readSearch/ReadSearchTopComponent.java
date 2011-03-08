package de.cebitec.vamp.tools.readSearch;

import de.cebitec.vamp.api.objects.Read;
import de.cebitec.vamp.util.TabWithCloseX;
import de.cebitec.vamp.view.dataVisualisation.trackViewer.TrackViewer;
import java.awt.CardLayout;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
//import org.openide.util.ImageUtilities;
import org.netbeans.api.settings.ConvertAsProperties;

/**
 * Top component which displays read searches.
 */
@ConvertAsProperties(dtd = "-//de.cebitec.vamp.tools.readSearch//ReadSearch//EN", autostore = false)
public final class ReadSearchTopComponent extends TopComponent {

    private static ReadSearchTopComponent instance;
    private static final long serialVersionUID = 1L;
    /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";
    private static final String PREFERRED_ID = "ReadSearchTopComponent";

    public ReadSearchTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(ReadSearchTopComponent.class, "CTL_ReadSearchTopComponent"));
        setToolTipText(NbBundle.getMessage(ReadSearchTopComponent.class, "HINT_ReadSearchTopComponent"));
//        setIcon(ImageUtilities.loadImage(ICON_PATH, true));

        // add listener to close TopComponent when no tabs are shown
        readSearchTabbedPane.addContainerListener(new ContainerListener() {

            @Override
            public void componentAdded(ContainerEvent e) {

            }

            @Override
            public void componentRemoved(ContainerEvent e) {
                if (readSearchTabbedPane.getTabCount() == 0){
                    WindowManager.getDefault().findTopComponent(PREFERRED_ID).close();
                }
            }
        });
    }

    /**
     * Creates a complete read search panel, that is used in the JTabbedPane.
     * The <code>TrackViewer</code> instance is used to set up the setup and
     * result panels.
     *
     * @param trackViewer instance used for this panels panels.
     * @return complete read search panel
     */
    private javax.swing.JPanel getReadSearchPanel(TrackViewer trackViewer){
        // initialise components
        final JPanel readSearchPanel = new JPanel();
        ReadSearchSetup setupPanel = new ReadSearchSetup();
        final ReadSearchResults resultPanel = new ReadSearchResults();

        // assign the trackviewer
        setupPanel.setTrackCon(trackViewer.getTrackCon());
        resultPanel.setBoundsInformationManager(trackViewer.getBoundsInformationManager());

        // listen on changes of the search
        setupPanel.addPropertyChangeListener(ReadSearchSetup.PROP_SEARCH_FINISHED, new PropertyChangeListener() {

            @Override
            @SuppressWarnings("unchecked")
            public void propertyChange(PropertyChangeEvent evt) {
                resultPanel.addReads((List<Read>) evt.getNewValue());
                ((CardLayout) readSearchPanel.getLayout()).show(readSearchPanel, "results");
            }
        });

        // setup the layout
        readSearchPanel.setLayout(new java.awt.CardLayout());
        readSearchPanel.add(setupPanel, "setup");
        readSearchPanel.add(resultPanel, "results");

        return readSearchPanel;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        readSearchSetup = new de.cebitec.vamp.tools.readSearch.ReadSearchSetup();
        readSearchResults = new de.cebitec.vamp.tools.readSearch.ReadSearchResults();
        readSearchTabbedPane = new javax.swing.JTabbedPane();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(readSearchTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(readSearchTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cebitec.vamp.tools.readSearch.ReadSearchResults readSearchResults;
    private de.cebitec.vamp.tools.readSearch.ReadSearchSetup readSearchSetup;
    private javax.swing.JTabbedPane readSearchTabbedPane;
    // End of variables declaration//GEN-END:variables
    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link #findInstance}.
     */
    public static synchronized ReadSearchTopComponent getDefault() {
        if (instance == null) {
            instance = new ReadSearchTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the ReadSearchTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized ReadSearchTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(ReadSearchTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof ReadSearchTopComponent) {
            return (ReadSearchTopComponent) win;
        }
        Logger.getLogger(ReadSearchTopComponent.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID
                + "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_ALWAYS;
    }

    @Override
    public void componentOpened() {

    }

    @Override
    public void componentClosed() {
        readSearchTabbedPane.removeAll();
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // store your settings
    }

    Object readProperties(java.util.Properties p) {
        if (instance == null) {
            instance = this;
        }
        instance.readPropertiesImpl(p);
        return instance;
    }

    private void readPropertiesImpl(java.util.Properties p) {
        String version = p.getProperty("version");
        // read your settings according to their version
    }

    @Override
    protected String preferredID() {
        return PREFERRED_ID;
    }

    public void openReadSearchTab(TrackViewer trackViewer){
        readSearchTabbedPane.addTab(trackViewer.getTrackCon().getAssociatedTrackName(), getReadSearchPanel(trackViewer));
        readSearchTabbedPane.setTabComponentAt(readSearchTabbedPane.getTabCount()-1, new TabWithCloseX(readSearchTabbedPane));
    }
    
}
