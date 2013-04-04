package de.cebitec.vamp.correlationAnalysis;

import de.cebitec.vamp.databackend.connector.ProjectConnector;
import de.cebitec.vamp.databackend.dataObjects.PersistantTrack;
import de.cebitec.vamp.rnaTrimming.*;
import java.util.Iterator;
import java.util.List;
import org.openide.util.NbBundle;

/**
 *
 * @author evgeny
 */
public class OverviewCard extends javax.swing.JPanel {
    
    private static final long serialVersionUID = 1L;

    /** Creates new form OverviewCard */
    public OverviewCard() {
        initComponents();
    }

    @Override
    public String getName() {
        return NbBundle.getMessage(OverviewCard.class, "CTL_OverviewCard.name");
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
        overviewTextArea = new javax.swing.JTextArea();

        overviewTextArea.setColumns(20);
        overviewTextArea.setRows(5);
        jScrollPane1.setViewportView(overviewTextArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea overviewTextArea;
    // End of variables declaration//GEN-END:variables

    void showGenereateOverview(String intervallength, String mincorrelation) {
        

    }

    void showGenereateOverview(List<PersistantTrack> list, String intervallength, String mincorrelation) {
        overviewTextArea.setText("Reference:\n");
        overviewTextArea.append(ProjectConnector.getInstance().getRefGenomeConnector(
                list.get(0).getRefGenID()).getRefGenome().getName()+"\n"
        );
        
        overviewTextArea.append("Selected tracks:\n");
        for(PersistantTrack track : list) {
            overviewTextArea.append("Track " + track.getId() + ": " + track.getDescription());
        }
        
        overviewTextArea.setText("Interval length:\n");
        overviewTextArea.append(intervallength+"\n"+"\n");
        
        overviewTextArea.append("Minimum correlation:\n");
        overviewTextArea.append(mincorrelation+"%\n"+"\n");
    }

}
