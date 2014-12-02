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
package de.cebitec.readXplorer.transcriptionAnalyses;


import java.util.Map;
import org.openide.util.NbBundle;


/**
 * Panel for showing the the statistics of a TSS detection result.
 * <p>
 * @author Rolf Hilker <rhilker at cebitec.uni-bielefeld.de>
 */
public class TssDetectionStatsPanel extends javax.swing.JPanel {

    private static final long serialVersionUID = 1L;
    private Map<String, Integer> tssStatisticsMap;


    /**
     * Panel for showing the the statistics of a TSS detection result.
     * <p>
     * @param tssStatisticsMap the snp results statistics to display
     */
    public TssDetectionStatsPanel( Map<String, Integer> tssStatisticsMap ) {
        this.tssStatisticsMap = tssStatisticsMap;
        initComponents();
        this.initAdditionalComponents();
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tssDetectionStatsScrollpane = new javax.swing.JScrollPane();
        tssDetectionStatsTable = new javax.swing.JTable();

        tssDetectionStatsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Bla", "Blubb"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tssDetectionStatsScrollpane.setViewportView(tssDetectionStatsTable);
        if (tssDetectionStatsTable.getColumnModel().getColumnCount() > 0) {
            tssDetectionStatsTable.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(TssDetectionStatsPanel.class, "TssDetectionStatsPanel.tssDetectionStatsTable.columnModel.title0_1")); // NOI18N
            tssDetectionStatsTable.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(TssDetectionStatsPanel.class, "TssDetectionStatsPanel.tssDetectionStatsTable.columnModel.title1")); // NOI18N
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tssDetectionStatsScrollpane, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tssDetectionStatsScrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane tssDetectionStatsScrollpane;
    private javax.swing.JTable tssDetectionStatsTable;
    // End of variables declaration//GEN-END:variables


    private void initAdditionalComponents() {
        int noUnannotatedTrans = this.tssStatisticsMap.get( TssDetectionResult.TSS_NOVEL );
        String unannotatedTransValue = noUnannotatedTrans
                                       == TssDetectionResult.UNUSED_STATISTICS_VALUE ? "-" : String.valueOf( noUnannotatedTrans );
        tssDetectionStatsTable.setModel( new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    { TssDetectionResult.TSS_TOTAL, String.valueOf( this.tssStatisticsMap.get( TssDetectionResult.TSS_TOTAL ) ) },
                    { TssDetectionResult.TSS_CORRECT, String.valueOf( this.tssStatisticsMap.get( TssDetectionResult.TSS_CORRECT ) ) },
                    { TssDetectionResult.TSS_UPSTREAM, String.valueOf( this.tssStatisticsMap.get( TssDetectionResult.TSS_UPSTREAM ) ) },
                    { TssDetectionResult.TSS_DOWNSTREAM, String.valueOf( this.tssStatisticsMap.get( TssDetectionResult.TSS_DOWNSTREAM ) ) },
                    { TssDetectionResult.TSS_LEADERLESS, String.valueOf( this.tssStatisticsMap.get( TssDetectionResult.TSS_LEADERLESS ) ) },
                    { TssDetectionResult.TSS_FWD, String.valueOf( this.tssStatisticsMap.get( TssDetectionResult.TSS_FWD ) ) },
                    { TssDetectionResult.TSS_REV, String.valueOf( this.tssStatisticsMap.get( TssDetectionResult.TSS_REV ) ) },
                    { TssDetectionResult.TSS_NOVEL, unannotatedTransValue }
                },
                new String[]{
                    NbBundle.getMessage( TssDetectionStatsPanel.class, "TssDetectionStatsPanel.tssDetectionStatsTable.columnModel.title0_1" ),
                    NbBundle.getMessage( TssDetectionStatsPanel.class, "TssDetectionStatsPanel.tssDetectionStatsTable.columnModel.title1" )

                }
        ) {
            private static final long serialVersionUID = 1L;
            Class[] types = new Class[]{
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean[]{
                false, false
            };


            @Override
            public Class getColumnClass( int columnIndex ) {
                return types[columnIndex];
            }


            @Override
            public boolean isCellEditable( int rowIndex, int columnIndex ) {
                return canEdit[columnIndex];
            }


        } );
    }


}
