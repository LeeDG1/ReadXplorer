/* 
 * Copyright (C) 2014 Institute for Bioinformatics and Systems Biology, University Giessen, Germany
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.cebitec.readXplorer.options;

import de.cebitec.readXplorer.util.Properties;
import java.util.prefs.Preferences;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import org.openide.util.NbPreferences;

/**
 * Panel for configuring the options of viewers.
 * 
 * @author Rolf Hilker <rhilker at mikrobio.med.uni-giessen.de>
 */
public final class ViewerPanel extends OptionsPanel {
    private static final long serialVersionUID = 1L;

    private final ViewerOptionsPanelController controller;
    private final Preferences pref;
    
    /**
     * Panel for configuring the options of viewers.
     * @param controller The controller of the panel
     */
    public ViewerPanel(ViewerOptionsPanelController controller) {
        this.controller = controller;
        this.pref = NbPreferences.forModule(Object.class);
        initComponents();
        // TODO listen to changes in form fields and call controller.changed()
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        descriptionLabel = new javax.swing.JLabel();
        autoScalingBox = new javax.swing.JCheckBox();
        viewerSizeSlider = new javax.swing.JSlider();
        jSeparator1 = new javax.swing.JSeparator();
        classificationBox = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();

        org.openide.awt.Mnemonics.setLocalizedText(descriptionLabel, org.openide.util.NbBundle.getMessage(ViewerPanel.class, "ViewerPanel.descriptionLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(autoScalingBox, org.openide.util.NbBundle.getMessage(ViewerPanel.class, "ViewerPanel.autoScalingBox.text")); // NOI18N

        viewerSizeSlider.setMajorTickSpacing(30);
        viewerSizeSlider.setMaximum(Properties.MAX_HEIGHT);
        viewerSizeSlider.setMinimum(Properties.MIN_HEIGHT);
        viewerSizeSlider.setMinorTickSpacing(5);
        viewerSizeSlider.setPaintLabels(true);
        viewerSizeSlider.setPaintTicks(true);
        viewerSizeSlider.setSnapToTicks(true);
        viewerSizeSlider.setToolTipText(org.openide.util.NbBundle.getMessage(ViewerPanel.class, "ViewerPanel.viewerSizeSlider.toolTipText")); // NOI18N
        viewerSizeSlider.setValue(200);

        org.openide.awt.Mnemonics.setLocalizedText(classificationBox, org.openide.util.NbBundle.getMessage(ViewerPanel.class, "ViewerPanel.classificationBox.text")); // NOI18N

        jEditorPane1.addHyperlinkListener(this.getHyperlinkListener());
        jEditorPane1.setEditable(false);
        jEditorPane1.setBackground(new java.awt.Color(240, 240, 240));
        jEditorPane1.setContentType("text/html"); // NOI18N
        jEditorPane1.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        jEditorPane1.setText(org.openide.util.NbBundle.getMessage(ViewerPanel.class, "ViewerPanel.jEditorPane1.text")); // NOI18N
        jScrollPane2.setViewportView(jEditorPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(descriptionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(autoScalingBox)
                            .addComponent(viewerSizeSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(classificationBox))
                        .addGap(0, 266, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(descriptionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(viewerSizeSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(autoScalingBox)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(classificationBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(151, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    void load() {
        this.viewerSizeSlider.setValue(pref.getInt(Properties.VIEWER_HEIGHT, Properties.DEFAULT_HEIGHT));
        this.autoScalingBox.setSelected(pref.getBoolean(Properties.VIEWER_AUTO_SCALING, false));
        this.classificationBox.setSelected(pref.getBoolean(Properties.VIEWER_CLASSIFICATION, false));
    }

    @Override
    void store() {
        pref.putInt(Properties.VIEWER_HEIGHT, this.viewerSizeSlider.getValue());
        pref.putBoolean(Properties.VIEWER_AUTO_SCALING, this.autoScalingBox.isSelected());
        pref.putBoolean(Properties.VIEWER_CLASSIFICATION, this.classificationBox.isSelected());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox autoScalingBox;
    private javax.swing.JCheckBox classificationBox;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSlider viewerSizeSlider;
    // End of variables declaration//GEN-END:variables

    private HyperlinkListener getHyperlinkListener() {
        return new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
//                    HelpCtx.setHelpIDString(jEditorPane1, e.getURL().toString());
//                    HelpCtx help = new HelpCtx(e.getURL().toString());
//                    help.display();
                }
            }
        };
    }

}
