package de.cebitec.vamp.view.dataVisualisation.trackViewer;

import de.cebitec.vamp.databackend.dataObjects.PersistantCoverage;
import de.cebitec.vamp.view.dataVisualisation.MousePositionListener;
import java.awt.Dimension;

/**
 *
 * @author jwinneba
 */
public class CoverageInfoLabel extends javax.swing.JPanel implements CoverageInfoI, MousePositionListener{

    private static final long serialVersionUID = 812385;
    private PersistantCoverage cov;
    private boolean mouseOverWanted;
    private boolean doubleTrackHackBoolean;

    /** Creates new form CoverageInfoPanel */
    public CoverageInfoLabel() {
        initComponents();
        this.setInitialSize();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        perfectCovLabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        zeroFwdField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        zeroRevField = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        bmCovLabel = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        bestMatchFwdField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        bestMatchRevField = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        complCovLabel = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        nFwdField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        nRevField = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        currentPositionLabel = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        perfectCovLabel.setText("Perfect cov.:");
        perfectCovLabel.setToolTipText("Number of perfect matches");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 42, 0, 0);
        add(perfectCovLabel, gridBagConstraints);

        jLabel5.setText("+");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(9, 4, 0, 0);
        add(jLabel5, gridBagConstraints);

        zeroFwdField.setEditable(false);
        zeroFwdField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        zeroFwdField.setToolTipText("");
        zeroFwdField.setMinimumSize(new java.awt.Dimension(25, 20));
        zeroFwdField.setPreferredSize(new java.awt.Dimension(25, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 36;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 22, 0);
        add(zeroFwdField, gridBagConstraints);

        jLabel6.setText("/ -");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 0);
        add(jLabel6, gridBagConstraints);

        zeroRevField.setEditable(false);
        zeroRevField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        zeroRevField.setToolTipText("");
        zeroRevField.setMinimumSize(new java.awt.Dimension(25, 20));
        zeroRevField.setPreferredSize(zeroFwdField.getPreferredSize());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 36;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 22, 0);
        add(zeroRevField, gridBagConstraints);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setPreferredSize(new java.awt.Dimension(2, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 5, 22, 0);
        add(jSeparator1, gridBagConstraints);

        bmCovLabel.setText("Best-Match cov.:");
        bmCovLabel.setToolTipText("<html>Number of matches<br>that are optimal for their related read");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 0);
        add(bmCovLabel, gridBagConstraints);

        jLabel7.setText("+");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 0);
        add(jLabel7, gridBagConstraints);

        bestMatchFwdField.setEditable(false);
        bestMatchFwdField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        bestMatchFwdField.setToolTipText("");
        bestMatchFwdField.setMinimumSize(new java.awt.Dimension(25, 20));
        bestMatchFwdField.setPreferredSize(zeroFwdField.getPreferredSize());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 22, 0);
        add(bestMatchFwdField, gridBagConstraints);

        jLabel8.setText("/ -");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 0);
        add(jLabel8, gridBagConstraints);

        bestMatchRevField.setEditable(false);
        bestMatchRevField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        bestMatchRevField.setToolTipText("");
        bestMatchRevField.setMinimumSize(new java.awt.Dimension(25, 20));
        bestMatchRevField.setPreferredSize(zeroFwdField.getPreferredSize());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 41;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 22, 0);
        add(bestMatchRevField, gridBagConstraints);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator2.setPreferredSize(new java.awt.Dimension(5, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 11;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 5, 22, 0);
        add(jSeparator2, gridBagConstraints);

        complCovLabel.setText("Complete cov.:");
        complCovLabel.setToolTipText("Complete number of matches");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 2, 0, 1);
        add(complCovLabel, gridBagConstraints);

        jLabel9.setText("+");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 13;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 0);
        add(jLabel9, gridBagConstraints);

        nFwdField.setEditable(false);
        nFwdField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        nFwdField.setToolTipText("");
        nFwdField.setMinimumSize(new java.awt.Dimension(25, 20));
        nFwdField.setPreferredSize(zeroFwdField.getPreferredSize());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 41;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 22, 2);
        add(nFwdField, gridBagConstraints);

        jLabel10.setText("/ -");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 15;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 0);
        add(jLabel10, gridBagConstraints);

        nRevField.setEditable(false);
        nRevField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        nRevField.setToolTipText("");
        nRevField.setMinimumSize(new java.awt.Dimension(25, 20));
        nRevField.setPreferredSize(zeroFwdField.getPreferredSize());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 16;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 43;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 22, 0);
        add(nRevField, gridBagConstraints);

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator3.setPreferredSize(new java.awt.Dimension(5, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 17;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 5, 22, 0);
        add(jSeparator3, gridBagConstraints);

        jLabel1.setText("Position:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 18;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 0);
        add(jLabel1, gridBagConstraints);

        currentPositionLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        currentPositionLabel.setPreferredSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 19;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 41;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 22, 0);
        add(currentPositionLabel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField bestMatchFwdField;
    private javax.swing.JTextField bestMatchRevField;
    private javax.swing.JLabel bmCovLabel;
    private javax.swing.JLabel complCovLabel;
    private javax.swing.JLabel currentPositionLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTextField nFwdField;
    private javax.swing.JTextField nRevField;
    private javax.swing.JLabel perfectCovLabel;
    private javax.swing.JTextField zeroFwdField;
    private javax.swing.JTextField zeroRevField;
    // End of variables declaration//GEN-END:variables

    public void setZeroFwd(int count){
        if(count >= 0){
            zeroFwdField.setText(String.valueOf(count));
        } else {
            zeroFwdField.setText("");
        }
    }

    public void setZeroRev(int count){
        if(count >= 0){
            zeroRevField.setText(String.valueOf(count));
        } else {
            zeroRevField.setText("");
        }
    }

    public void setBmFwd(int count){
        if(count >= 0){
            bestMatchFwdField.setText(String.valueOf(count));
        } else {
            bestMatchFwdField.setText("");
        }
    }

    public void setBmRev(int count){
        if(count >= 0){
            bestMatchRevField.setText(String.valueOf(count));
        } else {
            bestMatchRevField.setText("");
        }
    }

    public void setNFwd(int count){
        if(count >= 0){
            nFwdField.setText(String.valueOf(count));
        } else {
            nFwdField.setText("");
        }
    }

    public void setNRev(int count){
        if(count >= 0){
            nRevField.setText(String.valueOf(count));
        } else {
            nRevField.setText("");
        }
    }

    @Override
    public void setCurrentMousePosition(int logPos) {
        if(/*mouseOverWanted &&*/ cov != null){

            if (!doubleTrackHackBoolean) {
                setZeroFwd(cov.getzFwMult(logPos));
                setZeroRev(cov.getzRvMult(logPos));
                setBmFwd(cov.getBmFwMult(logPos));
                setBmRev(cov.getBmRvMult(logPos));
                setNFwd(cov.getnFwMult(logPos));
                setNRev(cov.getnRvMult(logPos));
                currentPositionLabel.setText(String.valueOf(logPos));
            }
            // TODO there has to be a much nicer way, maybe alter PersistantTrack or something else
            else {
                setZeroFwd(cov.getnFwMult(logPos));
                setZeroRev(cov.getnRvMult(logPos));
                setBmFwd(cov.getNFwMultTrack1(logPos));
                setBmRev(cov.getNRvMultTrack1(logPos));
                setNFwd(cov.getNFwMultTrack2(logPos));
                setNRev(cov.getNRvMultTrack2(logPos));
                currentPositionLabel.setText(String.valueOf(logPos));
            }

        } else {
            setZeroFwd(-1);
            setZeroRev(-1);
            setBmFwd(-1);
            setBmRev(-1);
            setNFwd(-1);
            setNRev(-1);
            currentPositionLabel.setText("");
        }
    }

    @Override
    public void setMouseOverPaintingRequested(boolean requested) {
        mouseOverWanted = requested;
        if(!requested){
            setCurrentMousePosition(-1);
        }
    }

    @Override
    public void setCoverage(PersistantCoverage coverage) {
        this.cov = coverage;
    }

    @Override
    public PersistantCoverage getCoverage() {
        return cov;
    }

    /**
     * little (hack) method to show the two track coverage info
     */
    public void renameFields(){
        perfectCovLabel.setText("Diff cov.:");
        bmCovLabel.setText("Track 1 cov.:");
        complCovLabel.setText("Track 2 cov.:");
        doubleTrackHackBoolean = true;
    }
    
    /**
     * Sets the initial size of the track viewer.
     */
    private void setInitialSize() {
        
        this.setPreferredSize(new Dimension(1, this.getPreferredSize().height));
        this.revalidate();
    }

}
