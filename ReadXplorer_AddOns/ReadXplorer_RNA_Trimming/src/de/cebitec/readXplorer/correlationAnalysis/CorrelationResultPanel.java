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
package de.cebitec.readXplorer.correlationAnalysis;


import de.cebitec.readXplorer.exporter.tables.TableExportFileChooser;
import de.cebitec.readXplorer.util.GeneralUtils;
import de.cebitec.readXplorer.util.SequenceUtils;
import de.cebitec.readXplorer.util.UneditableTableModel;
import de.cebitec.readXplorer.view.dataVisualisation.BoundsInfoManager;
import de.cebitec.readXplorer.view.tableVisualization.TableUtils;
import de.cebitec.readXplorer.view.tableVisualization.tableFilter.TableRightClickFilter;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;


/**
 * The Panel that shows results for a correlation analysis of two tracks in a
 * table
 * <p>
 * @author Evgeny Anisiforov, rhilker
 */
//@TopComponent.Registration(mode = "output", openAtStartup = false)
public class CorrelationResultPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private BoundsInfoManager bim;
    private TableRightClickFilter<UneditableTableModel> tableFilter;


    /**
     * Creates new form CorrelationResultPanel
     */
    public CorrelationResultPanel() {
        initComponents();
        final int posColumn = 2;
        final int trackColumn = 2;
        final int chromColumn = 3;
        tableFilter = new TableRightClickFilter<>( UneditableTableModel.class, posColumn, trackColumn );
        this.correlationTable.getTableHeader().addMouseListener( tableFilter );
        DefaultListSelectionModel model = (DefaultListSelectionModel) correlationTable.getSelectionModel();
        model.addListSelectionListener( new ListSelectionListener() {

            @Override
            public void valueChanged( ListSelectionEvent e ) {
                TableUtils.showPosition( correlationTable, posColumn, chromColumn, bim );
            }


        } );
    }


    private CorrelationResult analysisResult;


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
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
                "Chromosome", "Direction", "From", "To", "Correlation", "MinPeakCoverage"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
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
        jScrollPane1.setViewportView(correlationTable);
        if (correlationTable.getColumnModel().getColumnCount() > 0) {
            correlationTable.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(CorrelationResultPanel.class, "CorrelationResultPanel.correlationTable.columnModel.title5")); // NOI18N
            correlationTable.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(CorrelationResultPanel.class, "CorrelationResultPanel.correlationTable.columnModel.title0")); // NOI18N
            correlationTable.getColumnModel().getColumn(2).setHeaderValue(org.openide.util.NbBundle.getMessage(CorrelationResultPanel.class, "CorrelationResultPanel.correlationTable.columnModel.title1")); // NOI18N
            correlationTable.getColumnModel().getColumn(3).setHeaderValue(org.openide.util.NbBundle.getMessage(CorrelationResultPanel.class, "CorrelationResultPanel.correlationTable.columnModel.title2")); // NOI18N
            correlationTable.getColumnModel().getColumn(4).setHeaderValue(org.openide.util.NbBundle.getMessage(CorrelationResultPanel.class, "CorrelationResultPanel.correlationTable.columnModel.title3")); // NOI18N
            correlationTable.getColumnModel().getColumn(5).setHeaderValue(org.openide.util.NbBundle.getMessage(CorrelationResultPanel.class, "CorrelationResultPanel.correlationTable.columnModel.title4")); // NOI18N
        }

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
        if( this.getAnalysisResult() != null ) {
            TableExportFileChooser fileChooser = new TableExportFileChooser( TableExportFileChooser.getTableFileExtensions(), this.getAnalysisResult() );
        }
    }//GEN-LAST:event_exportButtonActionPerformed


    public void setBoundsInfoManager( BoundsInfoManager boundsInformationManager ) {
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


    public void addData( CorrelatedInterval data ) {
        DefaultTableModel model = (DefaultTableModel) this.correlationTable.getModel();
        //TODO: get chromosome map and set chromosome correctly
        String strandString = data.getDirection() == SequenceUtils.STRAND_FWD ? SequenceUtils.STRAND_FWD_STRING : SequenceUtils.STRAND_REV_STRING;
        model.addRow( new Object[]{ data.getChromId(), strandString, data.getFrom(), data.getTo(), data.getCorrelation(), data.getMinPeakCoverage() } );
    }


    public void ready( CorrelationResult analysisResult ) {
        //correlationTable.setAutoCreateRowSorter(true);
        this.setAnalysisResult( analysisResult );
        correlationTable.setRowSorter( new TableRowSorter( this.correlationTable.getModel() ) );
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
    public void setAnalysisResult( CorrelationResult analysisResult ) {
        this.analysisResult = analysisResult;
        this.paramsLabel.setText( GeneralUtils.implodeMap( ": ", ", ", analysisResult.getAnalysisParameters() ) );
        this.tracksLabel.setText( GeneralUtils.implode( ", ", analysisResult.getTrackNameList().toArray() ) );
    }


}
