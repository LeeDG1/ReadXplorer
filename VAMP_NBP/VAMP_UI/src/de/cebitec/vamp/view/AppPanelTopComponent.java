package de.cebitec.vamp.view;

import de.cebitec.centrallookup.CentralLookup;
import de.cebitec.vamp.controller.ViewController;
import de.cebitec.vamp.cookies.CloseRefGenCookie;
import de.cebitec.vamp.cookies.CloseTrackCookie;
import de.cebitec.vamp.cookies.OpenRefGenCookie;
import de.cebitec.vamp.cookies.OpenTrackCookie;
import de.cebitec.vamp.dataAdministration.RunningTaskI;
import de.cebitec.vamp.databackend.dataObjects.PersistantTrack;
import de.cebitec.vamp.view.dataVisualisation.basePanel.BasePanel;
import de.cebitec.vamp.view.dataVisualisation.trackViewer.TrackItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.logging.Logger;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
//import org.openide.util.ImageUtilities;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.cookies.CloseCookie;
import org.openide.nodes.Node;
import org.openide.nodes.Node.Cookie;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//de.cebitec.vamp.view//AppPanel//EN",
autostore = false)
public final class AppPanelTopComponent extends TopComponent implements ApplicationFrameI{

    private static AppPanelTopComponent instance;
    /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";
    private static final String PREFERRED_ID = "AppPanelTopComponent";
    private static final long serialVersionUID = 1L;
    private InstanceContent content = new InstanceContent();

    public AppPanelTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(AppPanelTopComponent.class, "CTL_AppPanelTopComponent"));
        setToolTipText(NbBundle.getMessage(AppPanelTopComponent.class, "HINT_AppPanelTopComponent"));
//        setIcon(ImageUtilities.loadImage(ICON_PATH, true));
        putClientProperty(TopComponent.PROP_CLOSING_DISABLED, Boolean.FALSE);
        putClientProperty(TopComponent.PROP_DRAGGING_DISABLED, Boolean.TRUE);
        putClientProperty(TopComponent.PROP_UNDOCKING_DISABLED, Boolean.TRUE);

    }

    private void removeAllCookies(){
        Collection<? extends Cookie> allCookies = getLookup().lookupAll(Cookie.class);

        for (Cookie cookie : allCookies) {
            content.remove(cookie);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        visualPanel = new javax.swing.JPanel();

        visualPanel.setLayout(new javax.swing.BoxLayout(visualPanel, javax.swing.BoxLayout.PAGE_AXIS));
        jScrollPane1.setViewportView(visualPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel visualPanel;
    // End of variables declaration//GEN-END:variables
    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link #findInstance}.
     */
    public static synchronized AppPanelTopComponent getDefault() {
        if (instance == null) {
            instance = new AppPanelTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the AppPanelTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized AppPanelTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(AppPanelTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof AppPanelTopComponent) {
            return (AppPanelTopComponent) win;
        }
        Logger.getLogger(AppPanelTopComponent.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID
                + "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_NEVER;
    }

    @Override
    public void componentOpened() {
        content.add(new OpenRefGenCookie() {

            @Override
            public void open() {
                CentralLookup.getDefault().lookup(ViewController.class).openRefGen();
                content.remove(this);
            }
        });
        associateLookup(new AbstractLookup(content));
    }

    @Override
    public void componentClosed() {
        // TODO remove all cookies? or is this done automatically?
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
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
        // TODO read your settings according to their version
    }

    @Override
    protected String preferredID() {
        return PREFERRED_ID;
    }

    // ===================== AppFrameI stuff ============================== //

    @Override
    public void setViewController(ViewController viewController) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void releaseButtons() {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void blockControlsByRunningTask(RunningTaskI runninTask) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void showRefGenPanel(BasePanel refGenPanel) {
        visualPanel.add(refGenPanel);

        // enable/disable buttons
//        closeRefgenItem.setEnabled(true);
//        openRefgenItem.setEnabled(false);
//        openTrackItem.setEnabled(true);

        visualPanel.updateUI();

        content.add(new CloseRefGenCookie() {

            @Override
            public boolean close() {
                CentralLookup.getDefault().lookup(ViewController.class).closeRefGen();
                content.remove(this);
                return true;
            }
        });
        content.add(new OpenTrackCookie() {

            @Override
            public void open() {
                CentralLookup.getDefault().lookup(ViewController.class).openTrack();
            }
        });
    }

    @Override
    public void removeRefGenPanel(BasePanel genomeViewer) {
        // enable/disable buttons
//        closeRefgenItem.setEnabled(false);
//        openRefgenItem.setEnabled(true);
//        openTrackItem.setEnabled(false);

        visualPanel.remove(genomeViewer);

        visualPanel.updateUI();

        // remove all cookies
        removeAllCookies();
        
        content.add(new OpenRefGenCookie() {

            @Override
            public void open() {
                CentralLookup.getDefault().lookup(ViewController.class).openRefGen();
                content.remove(this);
            }
        });
    }

    @Override
    public void showTrackPanel(BasePanel trackPanel, final TrackItem trackMenuItem) {
        // create a new menu item for this track
        trackMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TrackItem t = (TrackItem) e.getSource();
                PersistantTrack track = t.getTrack();
//                viewController.closeTrack(track);
            }
        });

        // add the menu item
//        visualisationMenu.add(trackMenuItem);

        // add the trackPanel
        visualPanel.add(trackPanel);
        visualPanel.updateUI();

        content.add(new CloseTrackCookie() {

            @Override
            public boolean close() {
                CentralLookup.getDefault().lookup(ViewController.class).closeTrack(trackMenuItem.getTrack());
                content.remove(this);
                return true;
            }

            @Override
            public String getTrackName() {
                return trackMenuItem.getTrack().getDescription();
            }
        });
    }

    @Override
    public void closeTrackPanel(BasePanel trackPanel, TrackItem trackMenuItem) {
        //visualisationMenu.remove(trackMenuItem);
        visualPanel.remove(trackPanel);
        visualPanel.updateUI();
    }
}
