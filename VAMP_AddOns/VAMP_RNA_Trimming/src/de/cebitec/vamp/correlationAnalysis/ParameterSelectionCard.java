/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cebitec.vamp.correlationAnalysis;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.openide.util.NbBundle;

/**
 * GUI Card for selection of the parameters for the correlation analysis
 * @author Evgeny Anisiforov
 */
public class ParameterSelectionCard extends javax.swing.JPanel {
    /** 
     * Creates new form TrimSelectionCard
     */
    public ParameterSelectionCard() {
        initComponents();
        
        this.intervalLengthSlider.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                firePropertyChange(CorrelationAnalysisAction.PROP_INTERVALLENGTH, e.getOldValue(), e.getNewValue());
            }
        });
        
        this.minimumCorrelationSlider.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                firePropertyChange(CorrelationAnalysisAction.PROP_MINCORRELATION, e.getOldValue(), e.getNewValue());
            }
        });
        
        this.minimumPeakCoverageSlider.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                firePropertyChange(CorrelationAnalysisAction.PROP_MINPEAKCOVERAGE, e.getOldValue(), e.getNewValue());
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

        intervalLengthLabel = new javax.swing.JLabel();
        minimumCorrelationLabel = new javax.swing.JLabel();
        intervalLengthSlider = new javax.swing.JSlider();
        minimumCorrelationSlider = new javax.swing.JSlider();
        minimumCorrelationLabel1 = new javax.swing.JLabel();
        minimumPeakCoverageSlider = new javax.swing.JSlider();

        org.openide.awt.Mnemonics.setLocalizedText(intervalLengthLabel, org.openide.util.NbBundle.getMessage(ParameterSelectionCard.class, "ParameterSelectionCard.intervalLengthLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(minimumCorrelationLabel, org.openide.util.NbBundle.getMessage(ParameterSelectionCard.class, "ParameterSelectionCard.minimumCorrelationLabel.text")); // NOI18N

        intervalLengthSlider.setMajorTickSpacing(500);
        intervalLengthSlider.setMaximum(2000);
        intervalLengthSlider.setMinorTickSpacing(50);
        intervalLengthSlider.setPaintLabels(true);
        intervalLengthSlider.setPaintTicks(true);
        intervalLengthSlider.setSnapToTicks(true);
        intervalLengthSlider.setToolTipText(org.openide.util.NbBundle.getMessage(ParameterSelectionCard.class, "ParameterSelectionCard.intervalLengthSlider.toolTipText")); // NOI18N
        intervalLengthSlider.setValue(100);

        minimumCorrelationSlider.setMajorTickSpacing(20);
        minimumCorrelationSlider.setMinorTickSpacing(10);
        minimumCorrelationSlider.setPaintLabels(true);
        minimumCorrelationSlider.setPaintTicks(true);
        minimumCorrelationSlider.setSnapToTicks(true);
        minimumCorrelationSlider.setToolTipText(org.openide.util.NbBundle.getMessage(ParameterSelectionCard.class, "ParameterSelectionCard.minimumCorrelationSlider.toolTipText")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(minimumCorrelationLabel1, org.openide.util.NbBundle.getMessage(ParameterSelectionCard.class, "ParameterSelectionCard.minimumCorrelationLabel1.text")); // NOI18N

        minimumPeakCoverageSlider.setMajorTickSpacing(20);
        minimumPeakCoverageSlider.setMinorTickSpacing(10);
        minimumPeakCoverageSlider.setPaintLabels(true);
        minimumPeakCoverageSlider.setPaintTicks(true);
        minimumPeakCoverageSlider.setSnapToTicks(true);
        minimumPeakCoverageSlider.setToolTipText(org.openide.util.NbBundle.getMessage(ParameterSelectionCard.class, "ParameterSelectionCard.minimumPeakCoverageSlider.toolTipText")); // NOI18N
        minimumPeakCoverageSlider.setValue(10);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(intervalLengthLabel)
                    .addComponent(minimumCorrelationLabel))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(minimumCorrelationSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .addComponent(intervalLengthSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(minimumCorrelationLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(minimumPeakCoverageSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(intervalLengthLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(intervalLengthSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(minimumCorrelationLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(minimumCorrelationSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(minimumCorrelationLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(minimumPeakCoverageSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(98, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    public int getIntervalLength() {
        return this.intervalLengthSlider.getValue();
    }
    
    public int getMinimumCorrelation() {
        return this.minimumCorrelationSlider.getValue();
    }
    
        
    @Override
    public String getName() {
        return NbBundle.getMessage(ParameterSelectionCard.class, "CTL_ParameterSelectionCard.name");
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel intervalLengthLabel;
    private javax.swing.JSlider intervalLengthSlider;
    private javax.swing.JLabel minimumCorrelationLabel;
    private javax.swing.JLabel minimumCorrelationLabel1;
    private javax.swing.JSlider minimumCorrelationSlider;
    private javax.swing.JSlider minimumPeakCoverageSlider;
    // End of variables declaration//GEN-END:variables

    public Object getMinimumPeakCoverage() {
        return this.minimumPeakCoverageSlider.getValue();
    }
}