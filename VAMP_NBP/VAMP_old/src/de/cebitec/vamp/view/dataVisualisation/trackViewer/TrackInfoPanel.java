package de.cebitec.vamp.view.dataVisualisation.trackViewer;

import de.cebitec.vamp.databackend.dataObjects.PersistantCoverage;
import de.cebitec.vamp.view.dataVisualisation.basePanel.AbstractInfoPanel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ddoppmeier
 */
public class TrackInfoPanel extends AbstractInfoPanel {

    private static final long serialVersionUID = 72348356;
    private PersistantCoverage cov;
    private boolean mouseOverWanted;
    private TrackViewer trackView;

    /** Creates new form TrackInfoPanel */
    public TrackInfoPanel() {
        initComponents();
      
    }

        public TrackInfoPanel(boolean twoTracks) {
        initComponents();
        colorOptionTab.remove(colorOptionPanel1);
      coverageInfoPanel1.renameFields();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        colorOptionTab = new javax.swing.JTabbedPane();
        coverageInfoPanel1 = new de.cebitec.vamp.view.dataVisualisation.trackViewer.CoverageInfoPanel();
        colorOptionPanel1 = new de.cebitec.vamp.view.dataVisualisation.trackViewer.ColorOptionPanel(this);

        setMinimumSize(new java.awt.Dimension(237, 250));
        setPreferredSize(new java.awt.Dimension(237, 250));

        coverageInfoPanel1.setToolTipText("");
        colorOptionTab.addTab("Current position", coverageInfoPanel1);
        coverageInfoPanel1.getAccessibleContext().setAccessibleParent(null);

        colorOptionTab.addTab("Color option", colorOptionPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(colorOptionTab, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(colorOptionTab, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void setCurrentMousePosition(int logPos) {

        if (mouseOverWanted && cov != null) {
            
            if (!cov.isTwoTracks()) {

                coverageInfoPanel1.setZeroFwd(cov.getzFwMult(logPos));
                coverageInfoPanel1.setZeroRev(cov.getzRvMult(logPos));
                coverageInfoPanel1.setBmFwd(cov.getBmFwMult(logPos));
                coverageInfoPanel1.setBmRev(cov.getBmRvMult(logPos));
                coverageInfoPanel1.setNFwd(cov.getnFwMult(logPos));
                coverageInfoPanel1.setNRev(cov.getnRvMult(logPos));

            } else {
                coverageInfoPanel1.setZeroFwd(cov.getnFwMult(logPos));
                coverageInfoPanel1.setZeroRev(cov.getnRvMult(logPos));
                coverageInfoPanel1.setBmFwd(cov.getNFwMultTrack1(logPos));
                coverageInfoPanel1.setBmRev(cov.getNRvMultTrack1(logPos));
                coverageInfoPanel1.setNFwd(cov.getNFwMultTrack2(logPos));
                coverageInfoPanel1.setNRev(cov.getNRvMultTrack2(logPos));
            }
        } else {

            coverageInfoPanel1.setZeroFwd(-1);
            coverageInfoPanel1.setZeroRev(-1);
            coverageInfoPanel1.setBmFwd(-1);
            coverageInfoPanel1.setBmRev(-1);
            coverageInfoPanel1.setNFwd(-1);
            coverageInfoPanel1.setNRev(-1);
        }
    }

    @Override
    public void setMouseOverPaintingRequested(boolean requested) {
        mouseOverWanted = requested;
        if(!requested){
            setCurrentMousePosition(-1);
        }
    }

    public void setCoverage(PersistantCoverage cov, int leftBound, int rightBound){
        this.cov = cov;
    }

    private int getIntervalCoverage(PersistantCoverage cov, boolean isForwardStrand, int covType, int from, int to){
        int sum = 0;

        if(isForwardStrand){
            if(covType == PersistantCoverage.PERFECT){
                for(int i = from; i<= to; i++){
                    sum += cov.getzFwMult(i);
                }
            } else if(covType == PersistantCoverage.BM){
                for(int i = from; i<= to; i++){
                    sum += cov.getBmFwMult(i);
                }
            } else if(covType == PersistantCoverage.NERROR){
                for(int i = from; i<= to; i++){
                    sum += cov.getnFwMult(i);
                }
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "found unknown coverage type!");
            }
        } else {
            if(covType == PersistantCoverage.PERFECT){
                for(int i = from; i<= to; i++){
                    sum += cov.getzRvMult(i);
                }
            } else if(covType == PersistantCoverage.BM){
                for(int i = from; i<= to; i++){
                    sum += cov.getBmRvMult(i);
                }
            } else if(covType == PersistantCoverage.NERROR){
                for(int i = from; i<= to; i++){
                    sum += cov.getnRvMult(i);
                }
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "found unknown coverage type!");
            }
        }

        return sum;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cebitec.vamp.view.dataVisualisation.trackViewer.ColorOptionPanel colorOptionPanel1;
    private javax.swing.JTabbedPane colorOptionTab;
    private de.cebitec.vamp.view.dataVisualisation.trackViewer.CoverageInfoPanel coverageInfoPanel1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void close() {
        cov = null;
    }

    public void setTrackViewer(TrackViewer view){
        this.trackView = view;
    }

    public void colorChanges(){
        trackView.colorChanges();
    }

}
