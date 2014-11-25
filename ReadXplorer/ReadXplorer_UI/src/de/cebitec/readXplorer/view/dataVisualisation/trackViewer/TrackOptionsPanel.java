package de.cebitec.readXplorer.view.dataVisualisation.trackViewer;

import de.cebitec.readXplorer.databackend.connector.TrackConnector;
import de.cebitec.readXplorer.util.ColorProperties;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;

/**
 * Panel containing the display options for a track viewer like
 * automatic sclaing and normalizing the coverage.
 * 
 * @author jstraube, rhilker
 */
public class TrackOptionsPanel extends javax.swing.JPanel {
    private static final long serialVersionUID = 1L;

    private TrackViewer trackViewer;

    /**
     * Creates a new Panel containing the display options for a track viewer like
     * automatic sclaing and normalizing the coverage.
     * @param parentTrackViewer parent track viewer
     */
    public TrackOptionsPanel(TrackViewer parentTrackViewer) {
        this.trackViewer = parentTrackViewer;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(ColorProperties.LEGEND_BACKGROUND);
        this.initOtherComponents();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    
    /**
     * Initializes in fact all components of the panel.
     */
    private void initOtherComponents() {
        
        final JLabel header = new JLabel("General:");
        header.setBackground(ColorProperties.LEGEND_BACKGROUND);
        header.setFont(new Font("Arial", Font.BOLD, 11));
        final JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(ColorProperties.LEGEND_BACKGROUND);
        headerPanel.add(header, BorderLayout.CENTER);
        headerPanel.setPreferredSize(new Dimension(headerPanel.getPreferredSize().width, headerPanel.getPreferredSize().height + 2));
        this.add(headerPanel);
        
        JPanel generalPanel = new JPanel();
        generalPanel.setLayout(new BorderLayout());
        generalPanel.setBackground(ColorProperties.LEGEND_BACKGROUND);
        final JCheckBox scaleBox = new JCheckBox("Automatic scaling enabled");
        scaleBox.setBackground(ColorProperties.LEGEND_BACKGROUND);
        scaleBox.setSelected(false);
        
        //automatic scaling enabled event
        scaleBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox scaleBox = (JCheckBox) e.getSource();
                trackViewer.setAutomaticScaling(scaleBox.isSelected());
            }
        });
        generalPanel.add(scaleBox, BorderLayout.WEST);
        this.add(generalPanel);
        
        // normalization options
        final JLabel header2 = new JLabel("Normalization:");
        header2.setBackground(ColorProperties.LEGEND_BACKGROUND);
        header2.setFont(new Font("Arial", Font.BOLD, 11));
        final JPanel headerPanel2 = new JPanel(new BorderLayout());
        headerPanel2.setBackground(ColorProperties.LEGEND_BACKGROUND);
        headerPanel2.add(header2, BorderLayout.CENTER);
        headerPanel2.setPreferredSize(new Dimension(headerPanel2.getPreferredSize().width, headerPanel2.getPreferredSize().height + 2));
        this.add(headerPanel2);

        if (trackViewer.isTwoTracks() && !trackViewer.isCombineTracks()) {
            int k = 0;
            for (String name : trackViewer.getTrackCon().getAssociatedTrackNames()) {
                this.createNormalizationEntry(name, k++);
            }
        } else if (trackViewer.isTwoTracks()) {
            this.createNormalizationEntry("All tracks: ", 0);
        } else {
            this.createNormalizationEntry(trackViewer.getTrackCon().getAssociatedTrackName(), 0);
        }

        this.updateUI();
    }
    
    /**
     * Creates an entry with a descriptive label and the normalization functionality for one track.
     * @param name the name of the track
     * @param index the index of the track in the trackId array
     */
    private void createNormalizationEntry(String name, int index) {
        JPanel trackPanel = new JPanel();
        trackPanel.setLayout(new BoxLayout(trackPanel, BoxLayout.X_AXIS));

        JPanel placeholder = this.createPlaceholder();
        final JLabel nameLabel = new JLabel("Track: " + name);
        nameLabel.setBackground(ColorProperties.LEGEND_BACKGROUND);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        
        final JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.setBackground(ColorProperties.LEGEND_BACKGROUND);
        labelPanel.add(placeholder, BorderLayout.WEST);
        labelPanel.add(nameLabel, BorderLayout.CENTER);
        labelPanel.setPreferredSize(new Dimension(labelPanel.getPreferredSize().width, labelPanel.getPreferredSize().height + 2));

        final JCheckBox log = new JCheckBox("Log2");
        log.setBackground(ColorProperties.LEGEND_BACKGROUND);

        final JCheckBox factor = new JCheckBox("Factor");
        factor.setBackground(ColorProperties.LEGEND_BACKGROUND);

        final JSpinner scaleFactorSpinner = new JSpinner();
        scaleFactorSpinner.setMaximumSize(new Dimension(50, 20));
        scaleFactorSpinner.setPreferredSize(new Dimension(50, 20));

        TrackConnector trackCon = trackViewer.getTrackCon();
        final int trackID = trackCon.getTrackIds().get(index);

        //scaleFactor init and action 
        scaleFactorSpinner.setMaximumSize(new Dimension(60, 20));
        scaleFactorSpinner.setModel(new SpinnerNumberModel(1.0, 0, 10, 0.1));

        final NormalizationSettings ns;
        if (trackViewer.getNormalizationSettings() == null) {
            ns = setNewNormalizationSettings();
            trackViewer.setNormalizationSettings(ns);
        } else {
            ns = trackViewer.getNormalizationSettings();
            boolean isLogNorm = ns.getIsLogNorm(trackID);
            boolean hasNorm = ns.getHasNormFac(trackID);
            scaleFactorSpinner.setValue(ns.getFactors(trackID));
            log.setSelected(isLogNorm);
            factor.setSelected(hasNorm && !isLogNorm);
        }
        
        JSpinner.NumberEditor editor = (JSpinner.NumberEditor) scaleFactorSpinner.getEditor();
        final JFormattedTextField txt = editor.getTextField();
        ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);
        DecimalFormat format = editor.getFormat();
        format.setMinimumFractionDigits(1);

        scaleFactorSpinner.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {

                JSpinner spinn = (JSpinner) e.getSource();
                double fac = (Double) spinn.getValue();
                NormalizationSettings currentNS = trackViewer.getNormalizationSettings();
                currentNS.setFactors(fac, trackID);
                trackViewer.setNormalizationSettings(currentNS);
                if (factor.isSelected()) {
                    trackViewer.normalizationValueChanged();
                }
            }
        });

        factor.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox check = (JCheckBox) e.getSource();
                boolean isLogNorm = check.isSelected();
                boolean isSel = isLogNorm | log.isSelected();

                double fac = (Double) scaleFactorSpinner.getValue();
                NormalizationSettings currentNS = trackViewer.getNormalizationSettings();
                currentNS.setFactors(fac, trackID);
                currentNS.setIsLogNorm(isSel & !isLogNorm, trackID);
                currentNS.setHasNormFac(isSel, trackID);
                trackViewer.setNormalizationSettings(currentNS);
                trackViewer.normalizationValueChanged();
                log.setSelected(isSel & !isLogNorm);
            }
        });


        //log2 init and action
        log.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                JCheckBox check = (JCheckBox) e.getSource();
                boolean logIsSel = check.isSelected();
                boolean isSel = logIsSel | factor.isSelected();
                factor.setSelected(isSel & !logIsSel);
                NormalizationSettings currentNS = trackViewer.getNormalizationSettings();
                currentNS.setHasNormFac(isSel, trackID);
                currentNS.setIsLogNorm(logIsSel, trackID);
                trackViewer.setNormalizationSettings(currentNS);
                trackViewer.normalizationValueChanged();
            }
        });

        trackPanel.add(labelPanel);
        trackPanel.add(log);
        trackPanel.add(factor);
        trackPanel.add(scaleFactorSpinner);
        this.add(trackPanel);
    }

    /**
     * Creates a new normalization settins object.
     * @return a new normalization settins object.
     */
    private NormalizationSettings setNewNormalizationSettings() {
        List<Boolean> bools = new ArrayList<Boolean>();
        List<Double> factors = new ArrayList<Double>();
        List<Boolean> hasNorm = new ArrayList<Boolean>();
        for (int i = 0; i < trackViewer.getTrackCon().getTrackIds().size(); ++i) {
            bools.add(i, false);
            factors.add(i, 1.0);
            hasNorm.add(i, false);
        }
        return new NormalizationSettings(trackViewer.getTrackCon().getTrackIds(), bools, factors, hasNorm);
    }

    /**
     * @return A placeholder with width 3 and height 20.
     */
    private JPanel createPlaceholder() {
        JPanel placeholder = new JPanel();
        placeholder.setBackground(ColorProperties.LEGEND_BACKGROUND);
        placeholder.setMinimumSize(new Dimension(3, 20));
        placeholder.setPreferredSize(new Dimension(3, 20));
        return placeholder;
    }
}