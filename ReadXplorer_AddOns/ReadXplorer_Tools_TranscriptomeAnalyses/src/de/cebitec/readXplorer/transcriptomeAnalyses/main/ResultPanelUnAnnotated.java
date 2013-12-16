package de.cebitec.readXplorer.transcriptomeAnalyses.main;

import de.cebitec.readXplorer.databackend.ResultTrackAnalysis;
import de.cebitec.readXplorer.databackend.dataObjects.PersistantFeature;
import de.cebitec.readXplorer.transcriptomeAnalyses.datastructures.NovelRegion;
import de.cebitec.readXplorer.util.SequenceUtils;
import de.cebitec.readXplorer.util.UneditableTableModel;
import de.cebitec.readXplorer.view.analysis.ResultTablePanel;
import de.cebitec.readXplorer.view.dataVisualisation.BoundsInfoManager;
import de.cebitec.readXplorer.view.dataVisualisation.referenceViewer.ReferenceViewer;
import de.cebitec.readXplorer.view.tableVisualization.TableUtils;
import de.cebitec.readXplorer.view.tableVisualization.tableFilter.TableRightClickDeletion;
import de.cebitec.readXplorer.view.tableVisualization.tableFilter.TableRightClickFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.DefaultListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jritter
 */
public class ResultPanelUnAnnotated extends ResultTablePanel {

    private BoundsInfoManager boundsInfoManager;
    private ResultsUnAnnotated results;
    private ReferenceViewer referenceViewer;
    private TableRightClickFilter<UneditableTableModel> tableFilter = new TableRightClickFilter<>(UneditableTableModel.class);
    private TableRightClickDeletion<DefaultTableModel> rowDeletion = new TableRightClickDeletion();
    private HashMap<Integer, NovelRegion> nRInHash;

    /**
     * Creates new form ResultPanelUnAnnotated
     */
    public ResultPanelUnAnnotated() {
        initComponents();
        this.unAnnotatedTable.getTableHeader().addMouseListener(tableFilter);
        this.unAnnotatedTable.addMouseListener(rowDeletion);

        DefaultListSelectionModel model = (DefaultListSelectionModel) this.unAnnotatedTable.getSelectionModel();
        model.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int posColumnIdx = 0;
                int chromColumnIdx = 1;
                TableUtils.showPosition(unAnnotatedTable, posColumnIdx, chromColumnIdx, getBoundsInfoManager());
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

        jScrollPane1 = new javax.swing.JScrollPane();
        unAnnotatedTable = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        unAnnotatedTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Start", "Chromosome", "Direction", "Dropoff pos", "Site", "Track ID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
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
        jScrollPane1.setViewportView(unAnnotatedTable);
        unAnnotatedTable.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(ResultPanelUnAnnotated.class, "ResultPanelUnAnnotated.unAnnotatedTable.columnModel.title0_1")); // NOI18N
        unAnnotatedTable.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(ResultPanelUnAnnotated.class, "ResultPanelUnAnnotated.unAnnotatedTable.columnModel.title3_1_1")); // NOI18N
        unAnnotatedTable.getColumnModel().getColumn(2).setHeaderValue(org.openide.util.NbBundle.getMessage(ResultPanelUnAnnotated.class, "ResultPanelUnAnnotated.unAnnotatedTable.columnModel.title1_1")); // NOI18N
        unAnnotatedTable.getColumnModel().getColumn(3).setHeaderValue(org.openide.util.NbBundle.getMessage(ResultPanelUnAnnotated.class, "ResultPanelUnAnnotated.unAnnotatedTable.columnModel.title2_1")); // NOI18N
        unAnnotatedTable.getColumnModel().getColumn(4).setHeaderValue(org.openide.util.NbBundle.getMessage(ResultPanelUnAnnotated.class, "ResultPanelUnAnnotated.unAnnotatedTable.columnModel.title4_1")); // NOI18N
        unAnnotatedTable.getColumnModel().getColumn(5).setHeaderValue(org.openide.util.NbBundle.getMessage(ResultPanelUnAnnotated.class, "ResultPanelUnAnnotated.unAnnotatedTable.columnModel.title4_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(ResultPanelUnAnnotated.class, "ResultPanelUnAnnotated.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(ResultPanelUnAnnotated.class, "ResultPanelUnAnnotated.jButton2.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable unAnnotatedTable;
    // End of variables declaration//GEN-END:variables

    @Override
    public void addResult(ResultTrackAnalysis newResult) {
        if (newResult instanceof TSSDetectionResults) {
            final ResultsUnAnnotated unAnnoResults = (ResultsUnAnnotated) newResult;
            final List<NovelRegion> novelRegions = new ArrayList<>(unAnnoResults.getResults());
//            this.tssInHash = new HashMap<>();

            if (results == null) {
                results = unAnnoResults;
            } else {
                results.getResults().addAll(unAnnoResults.getResults());
            }

            final int nbColumns = 6;
            int noFwdFeatures = 0;
            int noRevFeatures = 0;

            final DefaultTableModel model = (DefaultTableModel) this.unAnnotatedTable.getModel();

            String strand;
            PersistantFeature feature;

            for (NovelRegion nR : novelRegions) {

                if (nR.isFwdStrand()) {
                    strand = SequenceUtils.STRAND_FWD_STRING;
                    ++noFwdFeatures;
                } else {
                    strand = SequenceUtils.STRAND_REV_STRING;
                    ++noRevFeatures;
                }

                final Object[] rowData = new Object[nbColumns];
                int position = nR.getPos();
                this.nRInHash.put(position, nR);
                int i = 0;
                rowData[i++] = position;
                rowData[i++] = newResult.getChromosomeMap().get(nR.getChromId());
                rowData[i++] = strand;
                rowData[i++] = strand;
//                rowData[i++] = nR.getOffset();
                rowData[i++] = nR.getTrackId();

                SwingUtilities.invokeLater(new Runnable() { //because it is not called from the swing dispatch thread
                    @Override
                    public void run() {
                        model.addRow(rowData);
                    }
                });
            }
        }
    }

    @Override
    public int getResultSize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Set the reference viewer needed for updating the currently shown position
     * and extracting the reference sequence.
     *
     * @param referenceViewer the reference viewer belonging to this analysis
     * result
     */
    public void setReferenceViewer(ReferenceViewer referenceViewer) {
        this.boundsInfoManager = referenceViewer.getBoundsInformationManager();
        this.referenceViewer = referenceViewer;
    }
}
