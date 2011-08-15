package de.cebitec.vamp.thumbnail;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Logger;
import javax.swing.JComponent;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.netbeans.api.settings.ConvertAsProperties;
import org.netbeans.api.visual.widget.BirdViewController;
import org.netbeans.api.visual.widget.Scene;
import org.openide.util.Lookup;

/**
 * TopComponent to display a Scene for the Track-Widgets.
 */
@ConvertAsProperties(dtd = "-//de.cebitec.vamp.thumbnail//ThumbNailView//EN",
autostore = false)
public final class ThumbNailViewTopComponent extends TopComponent implements MouseListener {

    private JComponent myView;
    private Scene scene;
    private BirdViewController birdCont;
    private ThumbnailController thumbCon;

    public Scene getScene() {
        return scene;
    }
    private static ThumbNailViewTopComponent instance;
    /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";
    private static final String PREFERRED_ID = "ThumbNailViewTopComponent";

    public ThumbNailViewTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(ThumbNailViewTopComponent.class, "CTL_ThumbNailViewTopComponent"));
        setToolTipText(NbBundle.getMessage(ThumbNailViewTopComponent.class, "HINT_ThumbNailViewTopComponent"));
//        setIcon(ImageUtilities.loadImage(ICON_PATH, true));
        scene = new Scene();
        myView = scene.createView();
        //BirdViewController hinzufügen
        birdCont = scene.createBirdView();

        birdCont.setWindowSize(new Dimension(400, 400));
        birdCont.setZoomFactor(2.0);

        jScrollPane1.setViewportView(myView);

        myView.addMouseListener(this);
        associateLookup(Lookup.getDefault().lookup(ThumbnailController.class).getLookup());
        thumbCon = Lookup.getDefault().lookup(ThumbnailController.class);

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();

        setLayout(new java.awt.BorderLayout());
        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    // End of variables declaration
    // End of variables declaration
    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link #findInstance}.
     */
    public static synchronized ThumbNailViewTopComponent getDefault() {
        if (instance == null) {
            instance = new ThumbNailViewTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the ThumbNailViewTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized ThumbNailViewTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(ThumbNailViewTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof ThumbNailViewTopComponent) {
            return (ThumbNailViewTopComponent) win;
        }
        Logger.getLogger(ThumbNailViewTopComponent.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID
                + "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_NEVER;
    }

    @Override
    public void componentClosed() {
        scene.removeChildren();
        if (thumbCon != null) {
            thumbCon.removeThumbSpecificCookies();
            thumbCon.addOpenCookie();
        }
     
    }

    @Override
    protected void componentOpened() {
        if (thumbCon != null) {
            thumbCon.removeOpenCookie();
        }
    }

    @Override
    protected void componentActivated() {
        if (thumbCon != null) {
            thumbCon.setMeAsActive(this);
            thumbCon.removeOpenCookie();
        }
    }


    @Override
    protected void componentHidden() {
        if (thumbCon != null) {
            thumbCon.addOpenCookie();
        }        
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

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            birdCont.show();
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            birdCont.hide();
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
