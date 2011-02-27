package de.cebitec.vamp.tools.externalViewer;

import de.cebitec.vamp.controller.ViewController;
import de.cebitec.vamp.databackend.connector.TrackConnector;
import de.cebitec.vamp.view.dataVisualisation.basePanel.BasePanel;
import de.cebitec.vamp.view.dataVisualisation.basePanel.BasePanelFactory;
import de.cebitec.vamp.view.dataVisualisation.histogramViewer.HistogramViewer;
import java.awt.CardLayout;
import java.util.logging.Logger;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.openide.util.ImageUtilities;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.util.Utilities;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//de.cebitec.vamp.tools.externalViewer//ExternalViewer//EN", autostore = false)
public final class ExternalViewerTopComponent extends TopComponent {

    private static ExternalViewerTopComponent instance;
    private static final long serialVersionUID = 1L;
    /** path to the icon used by the component and its open action */
    static final String ICON_PATH = "de/cebitec/vamp/tools/externalViewer/externalViewer.png";
    private static final String PREFERRED_ID = "ExternalViewerTopComponent";

    private TrackConnector trackConnector;
    private BasePanel alignmentBasePanel;
    private BasePanel logoBasePanel;
    private CardLayout cards;

    private static String LOGOCARD = "logo";
    private static String ALIGNMENTCARD = "alignment";

    public ExternalViewerTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(ExternalViewerTopComponent.class, "CTL_ExternalViewerTopComponent"));
        setToolTipText(NbBundle.getMessage(ExternalViewerTopComponent.class, "HINT_ExternalViewerTopComponent"));
        setIcon(ImageUtilities.loadImage(ICON_PATH, true));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        switchPanel = new javax.swing.JPanel();
        seqlogoButton = new javax.swing.JButton();
        alignmentButton = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        viewerPanel = new javax.swing.JPanel();
        cardPanel = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        org.openide.awt.Mnemonics.setLocalizedText(seqlogoButton, org.openide.util.NbBundle.getMessage(ExternalViewerTopComponent.class, "ExternalViewerTopComponent.seqlogoButton.text")); // NOI18N
        seqlogoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seqlogoButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(alignmentButton, org.openide.util.NbBundle.getMessage(ExternalViewerTopComponent.class, "ExternalViewerTopComponent.alignmentButton.text")); // NOI18N
        alignmentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alignmentButtonActionPerformed(evt);
            }
        });

        jCheckBox1.setFont(new java.awt.Font("Dialog", 1, 10));
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox1, org.openide.util.NbBundle.getMessage(ExternalViewerTopComponent.class, "ExternalViewerTopComponent.jCheckBox1.text")); // NOI18N
        jCheckBox1.setMinimumSize(new java.awt.Dimension(50, 22));
        jCheckBox1.setPreferredSize(new java.awt.Dimension(100, 22));
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout switchPanelLayout = new javax.swing.GroupLayout(switchPanel);
        switchPanel.setLayout(switchPanelLayout);
        switchPanelLayout.setHorizontalGroup(
            switchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(switchPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(switchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(seqlogoButton)
                    .addComponent(alignmentButton))
                .addContainerGap(51, Short.MAX_VALUE))
            .addGroup(switchPanelLayout.createSequentialGroup()
                .addComponent(jCheckBox1, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );
        switchPanelLayout.setVerticalGroup(
            switchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(switchPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(seqlogoButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(alignmentButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(252, Short.MAX_VALUE))
        );

        add(switchPanel, java.awt.BorderLayout.WEST);

        viewerPanel.setPreferredSize(new java.awt.Dimension(490, 400));
        viewerPanel.setLayout(new java.awt.BorderLayout());

        cardPanel.setPreferredSize(new java.awt.Dimension(470, 400));
        cardPanel.setLayout(new java.awt.CardLayout());
        viewerPanel.add(cardPanel, java.awt.BorderLayout.CENTER);

        add(viewerPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void seqlogoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seqlogoButtonActionPerformed
        logoBasePanel.getViewer().setActive(true);
        alignmentBasePanel.getViewer().setActive(false);
        cards.show(cardPanel, LOGOCARD);
}//GEN-LAST:event_seqlogoButtonActionPerformed

    private void alignmentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alignmentButtonActionPerformed
        logoBasePanel.getViewer().setActive(false);
        alignmentBasePanel.getViewer().setActive(true);
        cards.show(cardPanel, ALIGNMENTCARD);
}//GEN-LAST:event_alignmentButtonActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        HistogramViewer vi = (HistogramViewer) logoBasePanel.getViewer();
        vi.isColored(jCheckBox1.isSelected());
        vi.boundsChangedHook();
        vi.repaint();
}//GEN-LAST:event_jCheckBox1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton alignmentButton;
    private javax.swing.JPanel cardPanel;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JButton seqlogoButton;
    private javax.swing.JPanel switchPanel;
    private javax.swing.JPanel viewerPanel;
    // End of variables declaration//GEN-END:variables
    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link #findInstance}.
     */
    public static synchronized ExternalViewerTopComponent getDefault() {
        if (instance == null) {
            instance = new ExternalViewerTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the ExternalViewerTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized ExternalViewerTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(ExternalViewerTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof ExternalViewerTopComponent) {
            return (ExternalViewerTopComponent) win;
        }
        Logger.getLogger(ExternalViewerTopComponent.class.getName()).warning(
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
        ViewController viewCon = Utilities.actionsGlobalContext().lookup(ViewController.class);
        BasePanelFactory factory = viewCon.getBasePanelFac();

        alignmentBasePanel = factory.getDetailTrackBasePanel(trackConnector);
        alignmentBasePanel.getViewer().setActive(false);
        logoBasePanel = factory.getSequenceLogoBasePanel(trackConnector);
        logoBasePanel.getViewer().setActive(true);

        cards = (CardLayout) cardPanel.getLayout();
        cardPanel.add(alignmentBasePanel, ALIGNMENTCARD);
        cardPanel.add(logoBasePanel, LOGOCARD);
        cards.show(cardPanel, LOGOCARD);
    }

    @Override
    public void componentClosed() {
        alignmentBasePanel.close();
        alignmentBasePanel = null;
        logoBasePanel.close();
        logoBasePanel = null;
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

    public void setTrackConnector(TrackConnector trackConnector){
        this.trackConnector = trackConnector;
    }
}
