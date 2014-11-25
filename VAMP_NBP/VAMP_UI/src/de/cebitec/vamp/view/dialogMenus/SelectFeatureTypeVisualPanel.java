package de.cebitec.vamp.view.dialogMenus;

import de.cebitec.vamp.api.objects.JobPanel;
import de.cebitec.vamp.util.FeatureType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.Preferences;
import org.openide.util.NbPreferences;

/**
 * A visual wizard job panel. It offers to select feature types of annotations
 * for any further processing.
 *
 * @author Rolf Hilker <rhilker at cebitec.uni-bielefeld.de>
 */
public class SelectFeatureTypeVisualPanel extends JobPanel {
    
    private static final long serialVersionUID = 1L;
    private static final String PANEL_NAME = "Feature Type Selection";
    private final String analysisName;
    private String displayName;

    /**
     * A visual wizard job panel. It offers to select feature types of
     * annotations for any further processing.
     * @param analysisName The name of the analysis using this panel
     * panel. It will be used to store the selected settings for this pane
     * under a unique identifier.
     */
    public SelectFeatureTypeVisualPanel(String analysisName) {
        this.analysisName = analysisName;
        this.displayName = PANEL_NAME;
        this.initComponents();
        this.featureList.addListSelectionListener(this.createListSelectionListener());
        this.updateListSelection();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        featureListPane = new javax.swing.JScrollPane();
        featureList = new javax.swing.JList<>(FeatureType.SELECTABLE_FEATURE_TYPES);
        featTypeLabel = new javax.swing.JLabel();

        featureListPane.setViewportView(featureList);

        org.openide.awt.Mnemonics.setLocalizedText(featTypeLabel, org.openide.util.NbBundle.getMessage(SelectFeatureTypeVisualPanel.class, "SelectFeatureTypeVisualPanel.featTypeLabel.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(featTypeLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(featureListPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(featTypeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(featureListPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel featTypeLabel;
    private javax.swing.JList<FeatureType> featureList;
    private javax.swing.JScrollPane featureListPane;
    // End of variables declaration//GEN-END:variables

    /**
     * @return The list of selected feature types.
     */
    public List<FeatureType> getSelectedFeatureTypes() {
        return featureList.getSelectedValuesList();
    }

    /**
     * @return The displayName + "Feature Type Selection".
     */
    @Override
    public String getName() {
        return displayName;
    }
    
    /**
     * Sets the display name of this panel. 
     * @param showDisplayName True, if the display name shall be included in the
     * title, false if only the plain panel name shall be shown
     */
    public void showDisplayName(boolean showDisplayName) {
        if (showDisplayName) {
            this.displayName = this.analysisName + " " + PANEL_NAME;
        } else {
            this.displayName = PANEL_NAME;
        }
        
    }

    /**
     * @return True, if at least one feature type is selected, false otherwise
     */
    @Override
    public boolean isRequiredInfoSet() {
        boolean requiredInfoSet = !this.featureList.getSelectedValuesList().isEmpty();
        firePropertyChange(ChangeListeningWizardPanel.PROP_VALIDATE, null, requiredInfoSet);
        return requiredInfoSet;
    }
    
    /**
     * Updates the checkboxes for the read classes with the globally stored
     * settings for this wizard. If no settings were stored, the default
     * configuration is chosen.
     */
    private void updateListSelection() {
        Preferences pref = NbPreferences.forModule(Object.class);
        String featuresString = pref.get(analysisName + SelectFeatureTypeWizardPanel.PROP_SELECTED_FEAT_TYPES, "Gene,CDS");
        String[] featuresArray = featuresString.split(",");
        
        List<FeatureType> selectedFeatTypes = new ArrayList<>();
        for (String featureString : featuresArray) {
            selectedFeatTypes.add(FeatureType.getFeatureType(featureString));
        }
        
        List<FeatureType> featTypeList = Arrays.asList(FeatureType.SELECTABLE_FEATURE_TYPES);
        List<Integer> selectedInices = new ArrayList<>();
        for (FeatureType selFeatureType : selectedFeatTypes) {
            selectedInices.add(featTypeList.indexOf(selFeatureType));
        }
        
        int[] selIndicesArray = new int[selectedInices.size()];
        for (int i = 0; i < selectedInices.size(); ++i) {
            selIndicesArray[i] = selectedInices.get(i);
        }
        this.featureList.setSelectedIndices(selIndicesArray);
    }
}