package de.cebitec.readXplorer.transcriptomeAnalyses.main;

import javax.swing.BorderFactory;

/**
 *
 * @author jritter
 */
public class NotificationWhenExportingPanel extends javax.swing.JPanel {
    private static final long serialVersionUID = 1L;

    /**
     * Creates new form NotificationWhenExportingPanel
     */
    public NotificationWhenExportingPanel() {
        initComponents();
        this.notificationTA.setBorder(BorderFactory.createRaisedBevelBorder());
        this.notificationTA.setEditable(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        notificationTA = new javax.swing.JTextArea();

        notificationTA.setColumns(20);
        notificationTA.setRows(5);
        notificationTA.setText(org.openide.util.NbBundle.getMessage(NotificationWhenExportingPanel.class, "NotificationWhenExportingPanel.notificationTA.text")); // NOI18N
        jScrollPane2.setViewportView(notificationTA);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea notificationTA;
    // End of variables declaration//GEN-END:variables
}
