package de.cebitec.readXplorer.coverageAnalysis;

import de.cebitec.readXplorer.exporter.excel.ExcelExportFileChooser;
import de.cebitec.readXplorer.util.UneditableTableModel;
import de.cebitec.readXplorer.view.dataVisualisation.BoundsInfoManager;
import de.cebitec.readXplorer.view.tableVisualization.TableUtils;
import de.cebitec.readXplorer.view.tableVisualization.tableFilter.TableRightClickFilter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * CoverageIntervalContainer panel for the coverage analysis. It displays the table with
 * all covered or uncovered intervals of the reference.
 * 
 * @author Tobias Zimmermann, Rolf Hilker <rhilker at mikrobio.med.uni-giessen.de>
 */
public class ResultPanelCoverageAnalysis extends javax.swing.JPanel {

    public static final String NUMBER_INTERVALS = "Total number of detected intervals";
    public static final String MEAN_INTERVAL_LENGTH = "Mean interval length";
    public static final String MEAN_INTERVAL_COVERAGE = "Global mean interval coverage";
    private static final long serialVersionUID = 1L;
    private BoundsInfoManager bim;
    private CoverageAnalysisResult coverageAnalysisResult;
    private final Map<String, Integer> coverageStatisticsMap;
    private TableRightClickFilter<UneditableTableModel> tableFilter = new TableRightClickFilter<>(UneditableTableModel.class);

    /**
     * CoverageIntervalContainer panel for the coverage analysis. It displays the table
     * with all covered or uncovered intervals of the reference.
     */
    public ResultPanelCoverageAnalysis() {
        initComponents();
        this.coverageAnalysisTable.getTableHeader().addMouseListener(tableFilter);
        this.coverageStatisticsMap = new HashMap<>();
        this.coverageStatisticsMap.put(NUMBER_INTERVALS, 0);
        this.coverageStatisticsMap.put(MEAN_INTERVAL_LENGTH, 0);
        this.coverageStatisticsMap.put(MEAN_INTERVAL_COVERAGE, 0);

        DefaultListSelectionModel model = (DefaultListSelectionModel) this.coverageAnalysisTable.getSelectionModel();
        model.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int posColumnIdx = 3;
                int chromColumnIdx = 1;
                TableUtils.showPosition(coverageAnalysisTable, posColumnIdx, chromColumnIdx, bim);
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        coverageAnalysisPane = new javax.swing.JScrollPane();
        coverageAnalysisTable = new javax.swing.JTable();
        exportButton = new javax.swing.JButton();
        statisticsButton = new javax.swing.JButton();
        parametersLabel = new javax.swing.JLabel();

        coverageAnalysisTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Start", "Stop", "Track", "Chromosome", "Strand", "Length", "Mean Coverage"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        coverageAnalysisPane.setViewportView(coverageAnalysisTable);
        if (coverageAnalysisTable.getColumnModel().getColumnCount() > 0) {
            coverageAnalysisTable.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(ResultPanelCoverageAnalysis.class, "ResultPanelCoverageAnalysis.coverageAnalysisTable.columnModel.title2")); // NOI18N
            coverageAnalysisTable.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(ResultPanelCoverageAnalysis.class, "ResultPanelCoverageAnalysis.coverageAnalysisTable.columnModel.title3")); // NOI18N
            coverageAnalysisTable.getColumnModel().getColumn(2).setHeaderValue(org.openide.util.NbBundle.getMessage(ResultPanelCoverageAnalysis.class, "ResultPanelCoverageAnalysis.coverageAnalysisTable.columnModel.title0")); // NOI18N
            coverageAnalysisTable.getColumnModel().getColumn(3).setHeaderValue(org.openide.util.NbBundle.getMessage(ResultPanelCoverageAnalysis.class, "ResultPanelCoverageAnalysis.coverageAnalysisTable.columnModel.title6")); // NOI18N
            coverageAnalysisTable.getColumnModel().getColumn(4).setHeaderValue(org.openide.util.NbBundle.getMessage(ResultPanelCoverageAnalysis.class, "ResultPanelCoverageAnalysis.coverageAnalysisTable.columnModel.title1")); // NOI18N
            coverageAnalysisTable.getColumnModel().getColumn(5).setHeaderValue(org.openide.util.NbBundle.getMessage(ResultPanelCoverageAnalysis.class, "ResultPanelCoverageAnalysis.coverageAnalysisTable.columnModel.title4")); // NOI18N
            coverageAnalysisTable.getColumnModel().getColumn(6).setHeaderValue(org.openide.util.NbBundle.getMessage(ResultPanelCoverageAnalysis.class, "ResultPanelCoverageAnalysis.coverageAnalysisTable.columnModel.title5")); // NOI18N
        }

        exportButton.setText(org.openide.util.NbBundle.getMessage(ResultPanelCoverageAnalysis.class, "ResultPanelCoverageAnalysis.exportButton.text_1")); // NOI18N
        exportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportButtonActionPerformed(evt);
            }
        });

        statisticsButton.setText(org.openide.util.NbBundle.getMessage(ResultPanelCoverageAnalysis.class, "ResultPanelCoverageAnalysis.statisticsButton.text_1")); // NOI18N
        statisticsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statisticsButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(parametersLabel, org.openide.util.NbBundle.getMessage(ResultPanelCoverageAnalysis.class, "ResultPanelCoverageAnalysis.parametersLabel.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(coverageAnalysisPane, javax.swing.GroupLayout.DEFAULT_SIZE, 760, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(parametersLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(statisticsButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exportButton)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(coverageAnalysisPane, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exportButton)
                    .addComponent(statisticsButton)
                    .addComponent(parametersLabel))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void exportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportButtonActionPerformed
        ExcelExportFileChooser fileChooser = new ExcelExportFileChooser(new String[]{"xls"}, "xls", this.coverageAnalysisResult);
    }//GEN-LAST:event_exportButtonActionPerformed

    private void statisticsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statisticsButtonActionPerformed
        JOptionPane.showMessageDialog(this, new CoverageAnalysisStatsPanel(coverageStatisticsMap), "Coverage Analysis Statistics", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_statisticsButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane coverageAnalysisPane;
    private javax.swing.JTable coverageAnalysisTable;
    private javax.swing.JButton exportButton;
    private javax.swing.JLabel parametersLabel;
    private javax.swing.JButton statisticsButton;
    // End of variables declaration//GEN-END:variables

    /**
     * @param boundsInformationManager The bounds info manager to update, when
     * a result is clicked.
     */
    public void setBoundsInfoManager(BoundsInfoManager boundsInformationManager) {
        this.bim = boundsInformationManager;
    }

    /**
     * Adds a list of covered or uncovered intervals to this panel.
     * @param coverageAnalysisResultNew the new result of intervals to add
     */
    public void addCoverageAnalysis(final CoverageAnalysisResult coverageAnalysisResultNew) {
        
        if (this.coverageAnalysisResult == null) {
            this.coverageAnalysisResult = coverageAnalysisResultNew;
        } else {
            this.coverageAnalysisResult.getResults().getCoverageIntervals().addAll(coverageAnalysisResultNew.getResults().getCoverageIntervals()); 
            this.coverageAnalysisResult.getResults().getCoverageIntervalsRev().addAll(coverageAnalysisResultNew.getResults().getCoverageIntervalsRev());
        }
        
        this.createTableEntries(coverageAnalysisResult.getResults().getCoverageIntervals());
        this.createTableEntries(coverageAnalysisResult.getResults().getCoverageIntervalsRev());
    }

    /**
     * Prepares the results stored in this panel for output in the gui.
     * @param intervalList list to create the entries for
     */
    private void createTableEntries(List<CoverageInterval> intervalList) {
        final int nbColumns = 7;

        DefaultTableModel model = (DefaultTableModel) coverageAnalysisTable.getModel();
        int meanIntervalLength = 0;
        int meanIntervalCoverage = 0;

        for (CoverageInterval interval : intervalList) {
            Object[] rowData = new Object[nbColumns];
            int i = 0;
            rowData[i++] = interval.isFwdStrand() ? interval.getStart() : interval.getStop();
            rowData[i++] = interval.isFwdStrand() ? interval.getStop() : interval.getStart();
            rowData[i++] = coverageAnalysisResult.getTrackEntry(interval.getTrackId(), false);
            rowData[i++] = coverageAnalysisResult.getChromosomeMap().get(interval.getChromId());
            rowData[i++] = interval.getStrandString();
            rowData[i++] = interval.getLength();
            rowData[i++] = interval.getMeanCoverage();
            meanIntervalLength += interval.getLength();
            meanIntervalCoverage += interval.getMeanCoverage();

            model.addRow(rowData);
        }

        if (intervalList.size() > 0) {
            meanIntervalLength /= intervalList.size();
            meanIntervalLength = coverageStatisticsMap.get(MEAN_INTERVAL_LENGTH) > 0 ? meanIntervalLength / 2 : meanIntervalLength;
            meanIntervalCoverage /= intervalList.size();
            meanIntervalCoverage = coverageStatisticsMap.get(MEAN_INTERVAL_COVERAGE) > 0 ? meanIntervalCoverage / 2 : meanIntervalCoverage;

            coverageStatisticsMap.put(NUMBER_INTERVALS, coverageStatisticsMap.get(NUMBER_INTERVALS) + intervalList.size());
            coverageStatisticsMap.put(MEAN_INTERVAL_LENGTH, meanIntervalLength);
            coverageStatisticsMap.put(MEAN_INTERVAL_COVERAGE, meanIntervalCoverage);
        }
        coverageAnalysisResult.setStatsMap(coverageStatisticsMap);

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(); //set a sorter for the table
        coverageAnalysisTable.setRowSorter(sorter);
        sorter.setModel(model);

        ParameterSetCoverageAnalysis parameters = ((ParameterSetCoverageAnalysis) coverageAnalysisResult.getParameters());
        String coverageCount = parameters.isSumCoverageOfBothStrands() ? "both strands" : "each strand separately";
        String uncoveredIntervals = parameters.isDetectCoveredIntervals() ? "no" : "yes";
        parametersLabel.setText(org.openide.util.NbBundle.getMessage(ResultPanelCoverageAnalysis.class,
                "ResultPanelCoverageAnalysis.parametersLabel.text",
                parameters.getMinCoverageCount(), coverageCount, uncoveredIntervals));
    }

    /**
     * @return the number of covered or uncovered intervals during the 
     * associated analysis.
     */
    public int getResultSize() {
        return  coverageStatisticsMap.get(NUMBER_INTERVALS);
    }
}