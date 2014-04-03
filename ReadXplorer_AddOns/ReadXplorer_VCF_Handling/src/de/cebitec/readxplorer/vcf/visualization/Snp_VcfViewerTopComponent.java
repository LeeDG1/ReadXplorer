package de.cebitec.readxplorer.vcf.visualization;

import de.cebitec.readXplorer.util.TabWithCloseX;
import de.cebitec.readXplorer.view.dataVisualisation.basePanel.BasePanel;
import static de.cebitec.readxplorer.vcf.visualization.Snp_VcfResultTopComponent.PREFERRED_ID;
import java.awt.BorderLayout;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import javax.swing.JPanel;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.WindowManager;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//de.cebitec.readxplorer.vcf.visualization//SNP_VCFViewer//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "SNP_VCFViewerTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "de.cebitec.readxplorer.vcf.visualization.SNP_VCFViewerTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_SNP_VCFViewerAction",
        preferredID = "SNP_VCFViewerTopComponent"
)
@Messages({
    "CTL_SNP_VCFViewerAction=SNP_VCFViewer",
    "CTL_SNP_VCFViewerTopComponent=SNP_VCFViewer Window",
    "HINT_SNP_VCFViewerTopComponent=This is a SNP_VCFViewer window"
})
public final class Snp_VcfViewerTopComponent extends TopComponent {

    public Snp_VcfViewerTopComponent() {
        initComponents();
        setName(Bundle.CTL_SNP_VCFViewerTopComponent());
        setToolTipText(Bundle.HINT_SNP_VCFViewerTopComponent());
        putClientProperty(TopComponent.PROP_KEEP_PREFERRED_SIZE_WHEN_SLIDED_IN, Boolean.TRUE);  // from CA

        }
    

         // add listener to close TopComponent when no tabs are shown
    // from AC
//        this.viewerPanel.addContainerListener(new ContainerListener() {
//            @Override
//            public void componentAdded(ContainerEvent e) {
//            }
//
//            @Override
//            public void componentRemoved(ContainerEvent e) {
//                if (viewerPanel.getTabCount() == 0) {
//                    WindowManager.getDefault().findTopComponent(PREFERRED_ID).close();
//                }
//            }
//        });
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        viewPanel = new javax.swing.JPanel();

        viewPanel.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel viewPanel;
    // End of variables declaration//GEN-END:variables
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

//    public void openViewer(String panelName, JPanel resultPanel) {
//        this.viewerPanel.add(panelName, resultPanel);
//    }

     public void openViewer(BasePanel viewPanel) {
        this.viewPanel.add(viewPanel, BorderLayout.CENTER);
     }
    
    /**
     * @return true, if this component already contains other components, false
     * otherwise.
     */
    public boolean hasComponents() {
        return true;
//        return this.viewerPanel.getComponentCount() > 0;
    }
}
