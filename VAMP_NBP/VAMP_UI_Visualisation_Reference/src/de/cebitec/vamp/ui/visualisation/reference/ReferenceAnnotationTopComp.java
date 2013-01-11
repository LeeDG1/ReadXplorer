package de.cebitec.vamp.ui.visualisation.reference;

import de.cebitec.vamp.databackend.dataObjects.PersistantAnnotation;
import de.cebitec.vamp.view.dataVisualisation.referenceViewer.JAnnotation;
import de.cebitec.vamp.view.dataVisualisation.referenceViewer.ReferenceViewer;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Logger;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//de.cebitec.vamp.ui.visualisation.reference//ReferenceAnnotation//EN", autostore = false)
public final class ReferenceAnnotationTopComp extends TopComponent implements LookupListener {

    private static ReferenceAnnotationTopComp instance;
    private static final long serialVersionUID = 1L;
    private Result<ReferenceViewer> result;
    /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";
    private static final String PREFERRED_ID = "ReferenceAnnotationTopComp";

    public ReferenceAnnotationTopComp() {
        this.initComponents();
        this.setName(NbBundle.getMessage(ReferenceAnnotationTopComp.class, "CTL_ReferenceAnnotationTopComp"));
        this.setToolTipText(NbBundle.getMessage(ReferenceAnnotationTopComp.class, "HINT_ReferenceAnnotationTopComp"));
//        this.setIcon(ImageUtilities.loadImage(ICON_PATH, true));
        this.putClientProperty(TopComponent.PROP_CLOSING_DISABLED, Boolean.TRUE);
        this.putClientProperty(TopComponent.PROP_MAXIMIZATION_DISABLED, Boolean.TRUE);
        this.putClientProperty(TopComponent.PROP_KEEP_PREFERRED_SIZE_WHEN_SLIDED_IN, Boolean.TRUE);

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        typeLabel = new javax.swing.JLabel();
        strandLabel = new javax.swing.JLabel();
        typeText = new javax.swing.JTextField();
        productLabel = new javax.swing.JLabel();
        strandText = new javax.swing.JTextField();
        ecNumLabel = new javax.swing.JLabel();
        stopLabel = new javax.swing.JLabel();
        ecNumField = new javax.swing.JTextField();
        stopField = new javax.swing.JTextField();
        locusField = new javax.swing.JTextField();
        locusLabel = new javax.swing.JLabel();
        startField = new javax.swing.JTextField();
        startLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        productText = new javax.swing.JTextArea();
        geneField = new javax.swing.JTextField();
        geneLabel = new javax.swing.JLabel();

        typeLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(typeLabel, org.openide.util.NbBundle.getMessage(ReferenceAnnotationTopComp.class, "ReferenceAnnotationTopComp.typeLabel.text")); // NOI18N
        typeLabel.setToolTipText(org.openide.util.NbBundle.getMessage(ReferenceAnnotationTopComp.class, "ReferenceAnnotationTopComp.typeLabel.toolTipText")); // NOI18N

        strandLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(strandLabel, org.openide.util.NbBundle.getMessage(ReferenceAnnotationTopComp.class, "ReferenceAnnotationTopComp.strandLabel.text")); // NOI18N
        strandLabel.setToolTipText(org.openide.util.NbBundle.getMessage(ReferenceAnnotationTopComp.class, "ReferenceAnnotationTopComp.strandLabel.toolTipText")); // NOI18N

        typeText.setEditable(false);
        typeText.setToolTipText(org.openide.util.NbBundle.getMessage(ReferenceAnnotationTopComp.class, "ReferenceAnnotationTopComp.typeText.toolTipText")); // NOI18N
        typeText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeTextActionPerformed(evt);
            }
        });

        productLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(productLabel, org.openide.util.NbBundle.getMessage(ReferenceAnnotationTopComp.class, "ReferenceAnnotationTopComp.productLabel.text")); // NOI18N
        productLabel.setToolTipText(org.openide.util.NbBundle.getMessage(ReferenceAnnotationTopComp.class, "ReferenceAnnotationTopComp.productLabel.toolTipText")); // NOI18N

        strandText.setEditable(false);
        strandText.setToolTipText(org.openide.util.NbBundle.getMessage(ReferenceAnnotationTopComp.class, "ReferenceAnnotationTopComp.strandText.toolTipText")); // NOI18N

        ecNumLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(ecNumLabel, org.openide.util.NbBundle.getMessage(ReferenceAnnotationTopComp.class, "ReferenceAnnotationTopComp.ecNumLabel.text")); // NOI18N
        ecNumLabel.setToolTipText(org.openide.util.NbBundle.getMessage(ReferenceAnnotationTopComp.class, "ReferenceAnnotationTopComp.ecNumLabel.toolTipText")); // NOI18N

        stopLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(stopLabel, org.openide.util.NbBundle.getMessage(ReferenceAnnotationTopComp.class, "ReferenceAnnotationTopComp.stopLabel.text")); // NOI18N
        stopLabel.setToolTipText(org.openide.util.NbBundle.getMessage(ReferenceAnnotationTopComp.class, "ReferenceAnnotationTopComp.stopLabel.toolTipText")); // NOI18N

        ecNumField.setEditable(false);
        ecNumField.setToolTipText(org.openide.util.NbBundle.getMessage(ReferenceAnnotationTopComp.class, "ReferenceAnnotationTopComp.ecNumField.toolTipText")); // NOI18N

        stopField.setEditable(false);
        stopField.setToolTipText(org.openide.util.NbBundle.getMessage(ReferenceAnnotationTopComp.class, "ReferenceAnnotationTopComp.stopField.toolTipText")); // NOI18N

        locusField.setEditable(false);
        locusField.setToolTipText(org.openide.util.NbBundle.getMessage(ReferenceAnnotationTopComp.class, "ReferenceAnnotationTopComp.locusField.toolTipText")); // NOI18N

        locusLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(locusLabel, org.openide.util.NbBundle.getMessage(ReferenceAnnotationTopComp.class, "ReferenceAnnotationTopComp.locusLabel.text")); // NOI18N
        locusLabel.setToolTipText(org.openide.util.NbBundle.getMessage(ReferenceAnnotationTopComp.class, "ReferenceAnnotationTopComp.locusLabel.toolTipText")); // NOI18N

        startField.setEditable(false);
        startField.setToolTipText(org.openide.util.NbBundle.getMessage(ReferenceAnnotationTopComp.class, "ReferenceAnnotationTopComp.startField.toolTipText")); // NOI18N

        startLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(startLabel, org.openide.util.NbBundle.getMessage(ReferenceAnnotationTopComp.class, "ReferenceAnnotationTopComp.startLabel.text")); // NOI18N
        startLabel.setToolTipText(org.openide.util.NbBundle.getMessage(ReferenceAnnotationTopComp.class, "ReferenceAnnotationTopComp.startLabel.toolTipText")); // NOI18N

        productText.setEditable(false);
        productText.setColumns(20);
        productText.setLineWrap(true);
        productText.setRows(5);
        productText.setWrapStyleWord(true);
        productText.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(productText);

        geneField.setEditable(false);
        geneField.setToolTipText(org.openide.util.NbBundle.getMessage(ReferenceAnnotationTopComp.class, "ReferenceAnnotationTopComp.geneField.toolTipText")); // NOI18N

        geneLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(geneLabel, org.openide.util.NbBundle.getMessage(ReferenceAnnotationTopComp.class, "ReferenceAnnotationTopComp.geneLabel.text")); // NOI18N
        geneLabel.setToolTipText(org.openide.util.NbBundle.getMessage(ReferenceAnnotationTopComp.class, "ReferenceAnnotationTopComp.geneLabel.toolTipText")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(strandLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(productLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(geneLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(stopLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ecNumLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(locusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(typeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(startLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(locusField, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(geneField, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(startField, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(stopField, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ecNumField)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                    .addComponent(strandText, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(typeText, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(typeText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(typeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(locusField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(locusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(geneField)
                    .addComponent(geneLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(startLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(startField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(stopField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stopLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ecNumField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ecNumLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(productLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(strandText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(strandLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(181, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void typeTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeTextActionPerformed
        
    }//GEN-LAST:event_typeTextActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ecNumField;
    private javax.swing.JLabel ecNumLabel;
    private javax.swing.JTextField geneField;
    private javax.swing.JLabel geneLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField locusField;
    private javax.swing.JLabel locusLabel;
    private javax.swing.JLabel productLabel;
    private javax.swing.JTextArea productText;
    private javax.swing.JTextField startField;
    private javax.swing.JLabel startLabel;
    private javax.swing.JTextField stopField;
    private javax.swing.JLabel stopLabel;
    private javax.swing.JLabel strandLabel;
    private javax.swing.JTextField strandText;
    private javax.swing.JLabel typeLabel;
    private javax.swing.JTextField typeText;
    // End of variables declaration//GEN-END:variables
    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link #findInstance}.
     * @return the ReferenceAnnotationTopComp
     */
    public static synchronized ReferenceAnnotationTopComp getDefault() {
        if (instance == null) {
            instance = new ReferenceAnnotationTopComp();
        }
        return instance;
    }

    /**
     * Obtain the ReferenceAnnotationTopComp instance. Never call {@link #getDefault} directly!
     * @return the ReferenceAnnotationTopComp
     */
    public static synchronized ReferenceAnnotationTopComp findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(ReferenceAnnotationTopComp.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof ReferenceAnnotationTopComp) {
            return (ReferenceAnnotationTopComp) win;
        }
        Logger.getLogger(ReferenceAnnotationTopComp.class.getName()).warning(
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
        result = Utilities.actionsGlobalContext().lookupResult(ReferenceViewer.class);
        result.addLookupListener(this);
        resultChanged(new LookupEvent(result));
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        for (ReferenceViewer refViewer : result.allInstances()) {
            JAnnotation annotation = refViewer.getCurrentlySelectedAnnotation();
            this.showAnnotationDetails(annotation != null ? annotation.getPersistantAnnotation() : null);

            refViewer.addPropertyChangeListener(ReferenceViewer.PROP_ANNOTATION_SELECTED, new PropertyChangeListener() {

                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    JAnnotation annotation = (JAnnotation) evt.getNewValue();
                    showAnnotationDetails(annotation.getPersistantAnnotation());
                }
            });
        }
    }

    @Override
    public void componentClosed() {
        result.removeLookupListener(this);
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

    /**
     * Displays the annotation details in their belonging visual components.
     * @param anno the annotation whose details should be shown
     */
    public void showAnnotationDetails(PersistantAnnotation anno) {
        
        String strand = "";
        if (anno != null) {
            this.ecNumField.setText(anno.getEcNumber());
            this.startField.setText(String.valueOf(anno.getStart()));
            this.stopField.setText(String.valueOf(anno.getStop()));
            this.productText.setText(anno.getProduct());
            this.productText.setToolTipText(anno.getProduct());
            this.locusField.setText(anno.getLocus());
            this.geneField.setText(anno.getGeneName());
            this.typeText.setText(anno.getType().getTypeString());

            strand = anno.isFwdStrand() ? "forward" : "reverse";
        } else {
            this.ecNumField.setText("");
            this.startField.setText("");
            this.stopField.setText("");
            this.productText.setText("");
            this.productText.setToolTipText("");
            this.locusField.setText("");
            this.geneField.setText("");
            this.typeText.setText("");
        }
        this.strandText.setText(strand);
        this.productText.setCaretPosition(0);
    }

}
