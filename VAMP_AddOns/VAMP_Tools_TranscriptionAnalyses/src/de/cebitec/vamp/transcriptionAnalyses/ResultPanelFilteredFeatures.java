/*
 * ResultPanelFilteredFeatures.java
 *
 * Created on 27.01.2012, 14:31:15
 */
package de.cebitec.vamp.transcriptionAnalyses;

import de.cebitec.vamp.databackend.dataObjects.PersistantFeature;
import de.cebitec.vamp.exporter.excel.ExcelExportFileChooser;
import de.cebitec.vamp.transcriptionAnalyses.dataStructures.FilteredFeature;
import de.cebitec.vamp.util.TableRightClickFilter;
import de.cebitec.vamp.util.UneditableTableModel;
import de.cebitec.vamp.view.dataVisualisation.BoundsInfoManager;
import de.cebitec.vamp.view.tableVisualization.TableComparatorProvider;
import de.cebitec.vamp.view.tableVisualization.TableUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * Panel showing a result of an analysis filtering for features with a 
 * min and max certain readcount.
 * 
 * @author -Rolf Hilker-
 */
public class ResultPanelFilteredFeatures extends javax.swing.JPanel {
    
    private static final long serialVersionUID = 1L;

    private BoundsInfoManager bim;
    private FilteredFeaturesResult filterFeaturesResult;
    private HashMap<String, Integer> filterStatisticsMap;
    private TableRightClickFilter<UneditableTableModel> tableFilter = new TableRightClickFilter<>(UneditableTableModel.class);
    
    public static final String FEATURES_FILTERED = "Total number of filtered features";
    public static final String FEATURES_TOTAL = "Total number of reference features";
    
    /**
     * Panel showing a result of an analysis filtering for features with a
     * min and max certain readcount.
     */
    public ResultPanelFilteredFeatures() {
        initComponents();
        this.filteredFeaturesTable.getTableHeader().addMouseListener(tableFilter);
        this.filterStatisticsMap = new HashMap<>();
        this.filterStatisticsMap.put(FEATURES_FILTERED, 0);
        
        DefaultListSelectionModel model = (DefaultListSelectionModel) this.filteredFeaturesTable.getSelectionModel();
        model.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                TableUtils.showPosition(filteredFeaturesTable, 0, bim);
            }
        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        filteredFeaturesPane = new javax.swing.JScrollPane();
        filteredFeaturesTable = new javax.swing.JTable();
        exportButton = new javax.swing.JButton();
        parametersLabel = new javax.swing.JLabel();
        statisticsButton = new javax.swing.JButton();

        filteredFeaturesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Feature", "Track", "Strand", "Start", "Stop", "Read Count"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        filteredFeaturesPane.setViewportView(filteredFeaturesTable);
        filteredFeaturesTable.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(ResultPanelFilteredFeatures.class, "ResultPanelFilteredFeatures.filteredFeaturesTable.columnModel.title0")); // NOI18N
        filteredFeaturesTable.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(ResultPanelFilteredFeatures.class, "ResultPanelFilteredFeatures.filteredFeaturesTable.columnModel.title3_1")); // NOI18N
        filteredFeaturesTable.getColumnModel().getColumn(2).setHeaderValue(org.openide.util.NbBundle.getMessage(ResultPanelFilteredFeatures.class, "ResultPanelFilteredFeatures.filteredFeaturesTable.columnModel.title3")); // NOI18N
        filteredFeaturesTable.getColumnModel().getColumn(3).setHeaderValue(org.openide.util.NbBundle.getMessage(ResultPanelFilteredFeatures.class, "ResultPanelFilteredFeatures.filteredFeaturesTable.columnModel.title4_1")); // NOI18N
        filteredFeaturesTable.getColumnModel().getColumn(4).setHeaderValue(org.openide.util.NbBundle.getMessage(ResultPanelFilteredFeatures.class, "ResultPanelFilteredFeatures.filteredFeaturesTable.columnModel.title5")); // NOI18N
        filteredFeaturesTable.getColumnModel().getColumn(5).setHeaderValue(org.openide.util.NbBundle.getMessage(ResultPanelFilteredFeatures.class, "ResultPanelFilteredFeatures.filteredFeaturesTable.columnModel.title4")); // NOI18N

        exportButton.setText(org.openide.util.NbBundle.getMessage(ResultPanelFilteredFeatures.class, "ResultPanelFilteredFeatures.exportButton.text")); // NOI18N
        exportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportButtonActionPerformed(evt);
            }
        });

        parametersLabel.setText(org.openide.util.NbBundle.getMessage(ResultPanelFilteredFeatures.class, "ResultPanelFilteredFeatures.parametersLabel.text")); // NOI18N

        statisticsButton.setText(org.openide.util.NbBundle.getMessage(ResultPanelFilteredFeatures.class, "ResultPanelFilteredFeatures.statisticsButton.text")); // NOI18N
        statisticsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statisticsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(parametersLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statisticsButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exportButton))
            .addComponent(filteredFeaturesPane, javax.swing.GroupLayout.DEFAULT_SIZE, 607, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(filteredFeaturesPane, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exportButton)
                    .addComponent(statisticsButton)
                    .addComponent(parametersLabel)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void exportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportButtonActionPerformed
        ExcelExportFileChooser fileChooser = new ExcelExportFileChooser(new String[]{"xls"}, "xls", filterFeaturesResult); 
    }//GEN-LAST:event_exportButtonActionPerformed

    private void statisticsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statisticsButtonActionPerformed
        JOptionPane.showMessageDialog(this, new FilterFeaturesStatsPanel(filterStatisticsMap), "Feature Filtering Statistics", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_statisticsButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton exportButton;
    private javax.swing.JScrollPane filteredFeaturesPane;
    private javax.swing.JTable filteredFeaturesTable;
    private javax.swing.JLabel parametersLabel;
    private javax.swing.JButton statisticsButton;
    // End of variables declaration//GEN-END:variables


    public void setBoundsInfoManager(BoundsInfoManager boundsInformationManager) {
        this.bim = boundsInformationManager;
    }

    /**
     * Adds a list of filtered features to this panel contained in the result object.
     * @param filterFeaturesResultNew a filtering for features result 
     */
    public void addFilteredFeatures(final FilteredFeaturesResult filterFeaturesResultNew) {
        final int nbColumns = 6;
        final List<FilteredFeature> features = new ArrayList<>(filterFeaturesResultNew.getResults());

        if (this.filterFeaturesResult == null) {
            this.filterFeaturesResult = filterFeaturesResultNew;
            this.filterStatisticsMap.put(FEATURES_TOTAL, filterFeaturesResultNew.getNoGenomeFeatures());
        } else {
            this.filterFeaturesResult.getResults().addAll(filterFeaturesResultNew.getResults());
        }

        SwingUtilities.invokeLater(new Runnable() { //because it is not called from the swing dispatch thread
            @Override
            public void run() {

                DefaultTableModel model = (DefaultTableModel) filteredFeaturesTable.getModel();

                PersistantFeature feat;
                for (FilteredFeature filteredFeature : features) {
                    feat = filteredFeature.getFilteredFeature();
                    
                    Object[] rowData = new Object[nbColumns];
                    rowData[0] = feat;
                    rowData[1] = filterFeaturesResultNew.getTrackMap().get(filteredFeature.getTrackId());
                    rowData[2] = feat.isFwdStrandString();
                    rowData[3] = feat.isFwdStrand() ? feat.getStart() : feat.getStop();
                    rowData[4] = feat.isFwdStrand() ? feat.getStop() : feat.getStart();
                    rowData[5] = filteredFeature.getReadCount();

                    model.addRow(rowData);
                }

                TableRowSorter<TableModel> sorter = new TableRowSorter<>();
                filteredFeaturesTable.setRowSorter(sorter);
                sorter.setModel(model);
                TableComparatorProvider.setPersistantTrackComparator(sorter, 1);

                ParameterSetFilteredFeatures parameters = (ParameterSetFilteredFeatures) filterFeaturesResult.getParameters();
                parametersLabel.setText(org.openide.util.NbBundle.getMessage(ResultPanelTranscriptionStart.class,
                        "ResultPanelFilteredFeatures.parametersLabel.text", parameters.getMinNumberReads(), parameters.getMaxNumberReads()));

                filterStatisticsMap.put(FEATURES_FILTERED, filterStatisticsMap.get(FEATURES_FILTERED) + features.size());
                filterFeaturesResult.setStatsMap(filterStatisticsMap);
            }
        });
    }
    
    /**
     * @return the number of features filtered during the associated analysis
     */
    public int getResultSize() {
        return this.filterFeaturesResult.getResults().size();
    }
}