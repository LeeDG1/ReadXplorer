/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cebitec.vamp.rnaTrimming;

import de.cebitec.vamp.correlationAnalysis.*;
import de.cebitec.vamp.util.TabWithCloseX;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.util.logging.Logger;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.WindowManager;

/**
 * This top component displays multiple tabs with results of a rna trimming process.
 * 
 * @author Evgeny Anisiforov <evgeny at cebitec.uni-bielefeld.de>
 */
@ConvertAsProperties(
    dtd = "-//de.cebitec.vamp.correlationAnalysis//TrimResult//EN",
autostore = false)
@TopComponent.Description(
    preferredID = "TrimResultTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "de.cebitec.vamp.correlationAnalysis.TrimResultTopComponent")
@ActionReference(path = "Menu/Window"/* , position = 951*/)
@TopComponent.OpenActionRegistration(
    displayName = "#CTL_TrimResultAction",
preferredID = "TrimResultTopComponent")
@Messages({
    "CTL_TrimResultAction=TrimResult",
    "CTL_TrimResultTopComponent=Trim unmapped RNA reads",
    "HINT_TrimResultTopComponent="
})
public final class TrimResultTopComponent extends TopComponent {
    
    private static TrimResultTopComponent instance;
    private static final String PREFERRED_ID = "TrimResultTopComponent";
    
    
    public TrimResultTopComponent() {
        initComponents();
        setName(Bundle.CTL_TrimResultTopComponent());
        setToolTipText(Bundle.HINT_TrimResultTopComponent());
        
        resultTabs.addContainerListener(new ContainerListener() {

            @Override
            public void componentAdded(ContainerEvent e) {
            }

            @Override
            public void componentRemoved(ContainerEvent e) {
                if (resultTabs.getTabCount() == 0) {
                    WindowManager.getDefault().findTopComponent(PREFERRED_ID).close();
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        resultTabs = new javax.swing.JTabbedPane();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(resultTabs, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(resultTabs, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane resultTabs;
    // End of variables declaration//GEN-END:variables
    
    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link #findInstance}.
     */
    public static synchronized TrimResultTopComponent getDefault() {
        if (instance == null) {
            instance = new TrimResultTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the SNP_DetectionTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized TrimResultTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(CorrelationResultTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof TrimResultTopComponent) {
            return (TrimResultTopComponent) win;
        }
        Logger.getLogger(TrimResultTopComponent.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID
                + "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }
    
    @Override
    protected String preferredID() {
        return PREFERRED_ID;
    }
    
    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_ALWAYS;
    }
    
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
    
    /**
     * This method needs to be called in order to open a new tab for trim result detection.
     * @param referenceViewer the reference viewer for which the snp detection should be carried out.
     * @param trackIds the list of track ids (associated to the reference viewer) for which the snp 
     *          detection should be carried out.
     */
    public TrimResultPanel openResultTab(String title) {
        TrimResultPanel resultView = new TrimResultPanel();
        resultTabs.addTab(title, resultView);
        resultTabs.setTabComponentAt(resultTabs.getTabCount() - 1, new TabWithCloseX(resultTabs));
        return resultView;
    }
    
    
}
