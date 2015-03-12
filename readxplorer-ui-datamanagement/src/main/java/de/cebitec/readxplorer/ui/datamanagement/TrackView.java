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

package de.cebitec.readxplorer.ui.datamanagement;


import de.cebitec.readxplorer.parser.TrackJob;
import de.cebitec.readxplorer.ui.dialogmenus.ChangeListeningWizardPanel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author ddoppmeier
 */
public class TrackView extends javax.swing.JPanel implements TableModelListener {

    private static final long serialVersionUID = 762498252;

    private final List<TrackJob> jobs;
    private Boolean hasCheckedJobs;

    public static final String PROP_DESELECT = "deselect";


    /**
     * Creates new form MappingView
     */
    public TrackView() {

        initComponents();
        jobs = new ArrayList<>();

    }


    public void setTrackJobs( List<TrackJob> trackJobs ) {

        jobs.clear();
        jobs.addAll( trackJobs );
        clearTableRows();

        DefaultTableModel model = (DefaultTableModel) jobTable.getModel();
        for( TrackJob trackJob : trackJobs ) {
            model.addRow( new Object[]{ false, trackJob.getDescription(), trackJob.getTimestamp() } );
        }
    }


    public List<TrackJob> getJobs2Del() {

        List<TrackJob> jobs2del = new ArrayList<>();
        for( int row = 0; row <= jobTable.getRowCount() - 1; row++ ) {
            if( (Boolean) jobTable.getValueAt( row, 0 ) ) {
                jobs2del.add( jobs.get( row ) );
            }
        }
        return jobs2del;

    }


    private void clearTableRows() {
        DefaultTableModel model = (DefaultTableModel) jobTable.getModel();
        while( model.getRowCount() > 0 ) {
            model.removeRow( model.getRowCount() - 1 );
        }
    }


    private void checkColumnSelection() {
        List<Boolean> selection = new ArrayList<>();

        for( int row = 0; row <= jobTable.getRowCount() - 1; row++ ) {
            selection.add( (Boolean) jobTable.getValueAt( row, 0 ) );
        }

        hasCheckedJobs = selection.contains( Boolean.TRUE );
        firePropertyChange( ChangeListeningWizardPanel.PROP_VALIDATE, null, hasCheckedJobs );
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jobTable = new javax.swing.JTable();

        jobTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Delete", "Description", "Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jobTable.setFillsViewportHeight(true);
        jScrollPane1.setViewportView(jobTable);
        if (jobTable.getColumnModel().getColumnCount() > 0) {
            jobTable.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(TrackView.class, "JobTable.delete")); // NOI18N
            jobTable.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(TrackView.class, "JobTable.description")); // NOI18N
            jobTable.getColumnModel().getColumn(2).setHeaderValue(org.openide.util.NbBundle.getMessage(TrackView.class, "JobTable.date")); // NOI18N
        }
        jobTable.getModel().addTableModelListener(this);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jobTable;
    // End of variables declaration//GEN-END:variables


    @Override
    public void tableChanged( TableModelEvent e ) {
        int row = e.getFirstRow();
        int column = e.getColumn();

        if( row >= 0 && column >= 0 ) {
            TrackJob trackJob = jobs.get( row );
            DefaultTableModel model = (DefaultTableModel) e.getSource();
            boolean selected = (Boolean) model.getValueAt( row, column );

            if( selected ) {
                // unregister dependencies
                trackJob.getRefGen().unregisterTrackwithoutRunJob( trackJob );
            } else {
                // re-register dependencies
                trackJob.getRefGen().registerTrackWithoutRunJob( trackJob );
                // deselect refgen
                firePropertyChange( PROP_DESELECT, null, trackJob.getRefGen() );
            }
        }
        checkColumnSelection();
    }


}
