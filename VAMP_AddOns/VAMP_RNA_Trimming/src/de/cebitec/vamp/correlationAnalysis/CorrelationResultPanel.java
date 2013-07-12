/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cebitec.vamp.correlationAnalysis;

import de.cebitec.vamp.exporter.excel.ExcelExportFileChooser;
import de.cebitec.vamp.util.GeneralUtils;
import de.cebitec.vamp.view.dataVisualisation.BoundsInfoManager;
import java.util.Map.Entry;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 * The Panel that shows results for a correlation analysis of two tracks in a table
 * @author Evgeny Anisiforov
 */
//@TopComponent.Registration(mode = "output", openAtStartup = false)
public class CorrelationResultPanel extends JPanel {
    private BoundsInfoManager bim;

    /**
     * Creates new form CorrelationResultPanel
     */
    public CorrelationResultPanel() {
        initComponents();
        DefaultListSelectionModel model = (DefaultListSelectionModel) correlationTable.getSelectionModel();
        model.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                showItemPosition();
            }
        });
    }
    
    private CorrelationResult analysisResult;
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        correlationTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        exportButton = new javax.swing.JToggleButton();
        jLabel2 = new javax.swing.JLabel();
        tracksLabel = new javax.swing.JLabel();
        paramsLabel = new javax.swing.JLabel();

        correlationTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Direction", "From", "To", "Correlation", "MinPeakCoverage"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(correlationTable);
        correlationTable.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(CorrelationResultPanel.class, "CorrelationResultPanel.correlationTable.columnModel.title0")); // NOI18N
        correlationTable.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(CorrelationResultPanel.class, "CorrelationResultPanel.correlationTable.columnModel.title1")); // NOI18N
        correlationTable.getColumnModel().getColumn(2).setHeaderValue(org.openide.util.NbBundle.getMessage(CorrelationResultPanel.class, "CorrelationResultPanel.correlationTable.columnModel.title2")); // NOI18N
        correlationTable.getColumnModel().getColumn(3).setHeaderValue(org.openide.util.NbBundle.getMessage(CorrelationResultPanel.class, "CorrelationResultPanel.correlationTable.columnModel.title3")); // NOI18N
        correlationTable.getColumnModel().getColumn(4).setHeaderValue(org.openide.util.NbBundle.getMessage(CorrelationResultPanel.class, "CorrelationResultPanel.correlationTable.columnModel.title4")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(CorrelationResultPanel.class, "CorrelationResultPanel.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(exportButton, org.openide.util.NbBundle.getMessage(CorrelationResultPanel.class, "CorrelationResultPanel.exportButton.text")); // NOI18N
        exportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(CorrelationResultPanel.class, "CorrelationResultPanel.jLabel2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(tracksLabel, org.openide.util.NbBundle.getMessage(CorrelationResultPanel.class, "CorrelationResultPanel.tracksLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(paramsLabel, org.openide.util.NbBundle.getMessage(CorrelationResultPanel.class, "CorrelationResultPanel.paramsLabel.text")); // NOI18N

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 788, Short.MAX_VALUE)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel1)
                    .add(jLabel2))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(tracksLabel)
                    .add(paramsLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(exportButton)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, exportButton)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel2)
                            .add(tracksLabel))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel1)
                            .add(paramsLabel))))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void exportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportButtonActionPerformed
        if (this.getAnalysisResult()!=null) {
            new ExcelExportFileChooser(new String[]{"xls"}, "xls", this.getAnalysisResult());
        }
    }//GEN-LAST:event_exportButtonActionPerformed

    /**
     * Centers the position of the selected correlation fragment in the bounds information manager.
     * This leads to an update of all viewers, sharing this bim.
     */
    private void showItemPosition() {
        DefaultListSelectionModel model = (DefaultListSelectionModel) correlationTable.getSelectionModel();
        int selectedView = model.getLeadSelectionIndex();
        int selectedModel = correlationTable.convertRowIndexToModel(selectedView);
        if (selectedModel>=0) {
            Integer pos = (Integer) correlationTable.getModel().getValueAt(selectedModel, 1);
            bim.navigatorBarUpdated(pos);
        }

        
    }
        
    public void setBoundsInfoManager(BoundsInfoManager boundsInformationManager) {
        this.bim = boundsInformationManager;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable correlationTable;
    private javax.swing.JToggleButton exportButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel paramsLabel;
    private javax.swing.JLabel tracksLabel;
    // End of variables declaration//GEN-END:variables

    public void addData(CorrelatedInterval data) {
        DefaultTableModel model = (DefaultTableModel) this.correlationTable.getModel();
        model.addRow(new Object[] {data.getDirection(), data.getFrom(), data.getTo(), data.getCorrelation(), data.getMinPeakCoverage()});
    }
    
    public void ready(CorrelationResult analysisResult) {
        //correlationTable.setAutoCreateRowSorter(true);
        this.setAnalysisResult(analysisResult);
        correlationTable.setRowSorter(new TableRowSorter(this.correlationTable.getModel()));
    }

    /**
     * @return the analysisResult
     */
    public CorrelationResult getAnalysisResult() {
        return analysisResult;
    }

    /**
     * @param analysisResult the analysisResult to set
     */
    public void setAnalysisResult(CorrelationResult analysisResult) {
        this.analysisResult = analysisResult;
        this.paramsLabel.setText(GeneralUtils.implodeMap(": ", ", ", analysisResult.getAnalysisParameters()));
        this.tracksLabel.setText(GeneralUtils.implode(", ", analysisResult.getTrackNameList().toArray()));        
    }
    
    
}
