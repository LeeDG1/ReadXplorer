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
package de.cebitec.readXplorer.view.dialogMenus;

import de.cebitec.readXplorer.databackend.connector.ProjectConnector;
import de.cebitec.readXplorer.databackend.dataObjects.PersistentReference;

/**
 * Panel for selecting and opening reference genomes.
 *
 * @author jwinneba, rhilker
 */
public class OpenRefGenPanel extends javax.swing.JPanel {

    public static final long serialVersionUID = 792723463;

    /** Creates new form OpenRefGenPanel */
    public OpenRefGenPanel() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        refGenList = new javax.swing.JList<>(ProjectConnector.getInstance().getGenomesAsArray());

        setLayout(new java.awt.BorderLayout());

        refGenList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(refGenList);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<PersistentReference> refGenList;
    // End of variables declaration//GEN-END:variables

    /**
     * @return The selected persistent reference
     */
    public PersistentReference getSelectedReference() {
        return refGenList.getSelectedValue();
    }

}
