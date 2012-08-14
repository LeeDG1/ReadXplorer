package de.cebitec.vamp.options;

import de.cebitec.vamp.util.Properties;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.prefs.Preferences;
import org.openide.util.Exceptions;
import org.openide.util.NbPreferences;

final class GnuRPanel extends javax.swing.JPanel {
    
    private final GnuROptionsPanelController controller;
    private Preferences pref;
    
    GnuRPanel(GnuROptionsPanelController controller) {
        this.controller = controller;
        initComponents();
        this.pref = NbPreferences.forModule(Object.class);
        setUpListener();
    }
    
    private void setUpListener() {
        cranMirror.addKeyListener(new KeyListener() {
            
            @Override
            public void keyTyped(KeyEvent e) {
                controller.changed();
            }
            
            @Override
            public void keyPressed(KeyEvent e) {
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        cranMirror = new javax.swing.JTextField();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(GnuRPanel.class, "GnuRPanel.jLabel1.text")); // NOI18N

        cranMirror.setText(org.openide.util.NbBundle.getMessage(GnuRPanel.class, "GnuRPanel.cranMirror.text")); // NOI18N
        cranMirror.setToolTipText(org.openide.util.NbBundle.getMessage(GnuRPanel.class, "GnuRPanel.cranMirror.toolTipText")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cranMirror)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 119, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cranMirror, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    void load() {
        cranMirror.setText(pref.get(Properties.CRAN_MIRROR, "http://cran.mirrors.hoobly.com/"));
    }
    
    void store() {
        pref.put(Properties.CRAN_MIRROR, cranMirror.getText());
    }
    
    boolean valid() {
        return true;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField cranMirror;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
