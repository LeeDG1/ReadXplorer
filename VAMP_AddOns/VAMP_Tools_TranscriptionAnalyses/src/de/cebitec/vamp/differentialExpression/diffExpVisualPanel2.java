/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cebitec.vamp.differentialExpression;

import de.cebitec.vamp.databackend.dataObjects.PersistantTrack;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public final class diffExpVisualPanel2 extends JPanel implements ListSelectionListener {

    private DefaultListModel<PersistantTrack> trackListModel = new DefaultListModel<PersistantTrack>();
    private DefaultListModel<String> groupListModel = new DefaultListModel<String>();
    private List<Integer[]> createdGroups = new ArrayList<Integer[]>();
    private List<PersistantTrack> selectedTraks = null;
    private Integer[] currentGroupBeingCreated = null;
    private int currentGroupNumber = 1;
    private int selectedIndex = -1;

    /**
     * Creates new form diffExpVisualPanel2
     */
    public diffExpVisualPanel2() {
        initComponents();
    }

    public void updateTrackList(List<PersistantTrack> selectedTraks) {
        if (this.selectedTraks == null) {
            this.selectedTraks = selectedTraks;
        }
        trackListModel.clear();
        for (Iterator<PersistantTrack> it = selectedTraks.iterator(); it.hasNext();) {
            PersistantTrack persistantTrack = it.next();
            trackListModel.addElement(persistantTrack);
        }
    }

    @Override
    public String getName() {
        return "Create groups";
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        trackList = new javax.swing.JList(trackListModel);
        groupCreationField = new javax.swing.JTextField();
        addButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        createdGroupsList = new javax.swing.JList(groupListModel);
        jLabel2 = new javax.swing.JLabel();
        addGroupButton = new javax.swing.JButton();
        removeGroupButton = new javax.swing.JButton();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(diffExpVisualPanel2.class, "diffExpVisualPanel2.jLabel1.text")); // NOI18N

        jScrollPane1.setViewportView(trackList);

        groupCreationField.setEditable(false);
        groupCreationField.setText(org.openide.util.NbBundle.getMessage(diffExpVisualPanel2.class, "diffExpVisualPanel2.groupCreationField.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(addButton, org.openide.util.NbBundle.getMessage(diffExpVisualPanel2.class, "diffExpVisualPanel2.addButton.text")); // NOI18N
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        createdGroupsList.addListSelectionListener(this);
        createdGroupsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(createdGroupsList);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(diffExpVisualPanel2.class, "diffExpVisualPanel2.jLabel2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(addGroupButton, org.openide.util.NbBundle.getMessage(diffExpVisualPanel2.class, "diffExpVisualPanel2.addGroupButton.text")); // NOI18N
        addGroupButton.setEnabled(false);
        addGroupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addGroupButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(removeGroupButton, org.openide.util.NbBundle.getMessage(diffExpVisualPanel2.class, "diffExpVisualPanel2.removeGroupButton.text")); // NOI18N
        removeGroupButton.setEnabled(false);
        removeGroupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeGroupButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(addButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(addGroupButton)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(removeGroupButton))
                                    .addComponent(groupCreationField))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(groupCreationField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addGroupButton)
                            .addComponent(removeGroupButton))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        if (currentGroupBeingCreated == null) {
            currentGroupBeingCreated = new Integer[selectedTraks.size()];
        }
        if (!trackList.isSelectionEmpty()) {
            List<PersistantTrack> tracks = trackList.getSelectedValuesList();
            StringBuilder strBuilder = new StringBuilder(groupCreationField.getText() + "{");
            for (Iterator<PersistantTrack> it = tracks.iterator(); it.hasNext();) {
                PersistantTrack persistantTrack = it.next();
                currentGroupBeingCreated[selectedTraks.indexOf(persistantTrack)] = currentGroupNumber;
                strBuilder.append(persistantTrack.getDescription());
                trackListModel.removeElement(persistantTrack);
                if (it.hasNext()) {
                    strBuilder.append(",");
                } else {
                    strBuilder.append("}");
                }
            }
            groupCreationField.setText(strBuilder.toString());
            currentGroupNumber++;
        }
        if (trackListModel.isEmpty()) {
            addGroupButton.setEnabled(true);
            addButton.setEnabled(false);
        }
    }//GEN-LAST:event_addButtonActionPerformed

    private void addGroupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addGroupButtonActionPerformed

        createdGroups.add(currentGroupBeingCreated);
        currentGroupBeingCreated = null;
        groupListModel.addElement(groupCreationField.getText());
        groupCreationField.setText("");
        updateTrackList(selectedTraks);
        addButton.setEnabled(true);
        addGroupButton.setEnabled(false);
    }//GEN-LAST:event_addGroupButtonActionPerformed

    private void removeGroupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeGroupButtonActionPerformed
        createdGroups.remove(selectedIndex);
        groupListModel.remove(selectedIndex);
        selectedIndex = -1;
        removeGroupButton.setEnabled(false);        
    }//GEN-LAST:event_removeGroupButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton addGroupButton;
    private javax.swing.JList createdGroupsList;
    private javax.swing.JTextField groupCreationField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton removeGroupButton;
    private javax.swing.JList trackList;
    // End of variables declaration//GEN-END:variables

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (selectedIndex != e.getFirstIndex()) {
            selectedIndex = e.getFirstIndex();
            removeGroupButton.setEnabled(true);
        }
    }

    public List<Integer[]> getCreatedGroups() {
        return createdGroups;
    }
    
    public boolean noGroupCreated(){
        return createdGroups.isEmpty();
    }
    
}
