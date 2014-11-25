package de.cebitec.readXplorer.ui.visualisation.track;

import de.cebitec.readXplorer.databackend.connector.TrackConnector;
import de.cebitec.readXplorer.util.StatsContainer;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 *
 * @author ddoppmeier, rhilker
 */
public class TrackStatisticsPanel extends javax.swing.JPanel {

    private final static long serialVersionUID = 1239345;
    private TrackConnector trackCon;
    private int trackID = -1;
    /** Creates new form TrackNavigator */
    public TrackStatisticsPanel() {
        initComponents();
//        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Do not use empty Constructor for this object! Standard constructor is only meant for GUI builder means");
    }

    public TrackStatisticsPanel(TrackConnector trackCon) {
        this.trackCon = trackCon;
        this.initComponents();
        this.hideSeqPairLabels();
      //  this.computeStats();
    }

    /**
     * Sets a new track connector and recomputes the stats.
     * Use this method when switching to another track.
     * @param trackCon new track connector
     */
    public void setTrackConnector(TrackConnector trackCon) {
        this.trackCon = trackCon;
        if (trackCon != null && trackCon.getTrackID() != trackID) {
            trackID = trackCon.getTrackID();
            nameLabel.setText(trackCon.getAssociatedTrackName());
            this.computeStats();
        }
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        progressbar = new javax.swing.JProgressBar();
        singletonMappingsLabel = new javax.swing.JLabel();
        uniqueSequencesLabel = new javax.swing.JLabel();
        singletonMappingsAbsLabel = new javax.swing.JLabel();
        perfectMappingsLabel = new javax.swing.JLabel();
        bestMatchMappingsLabel = new javax.swing.JLabel();
        numMappingsLabel = new javax.swing.JLabel();
        uniqueSequencesAbsLabel = new javax.swing.JLabel();
        perfectMappingsAbsLabel = new javax.swing.JLabel();
        bestMatchMappingsAbsLabel = new javax.swing.JLabel();
        numMappingsAbsLabel = new javax.swing.JLabel();
        perfPercentageLabel = new javax.swing.JLabel();
        bmPercentageLabel = new javax.swing.JLabel();
        compPercentageLabel = new javax.swing.JLabel();
        perfectPercentage = new javax.swing.JLabel();
        bmPercentage = new javax.swing.JLabel();
        compPercentage = new javax.swing.JLabel();
        labelNameLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        numberReadsLabel = new javax.swing.JLabel();
        numberReadsAbsLabel = new javax.swing.JLabel();
        readPairPanel = new javax.swing.JPanel();
        numReadPairsLabel = new javax.swing.JLabel();
        numReadPairsAbsLabel = new javax.swing.JLabel();
        numPerfReadPairsLabel = new javax.swing.JLabel();
        numPerfReadPairsAbsLabel = new javax.swing.JLabel();
        numSmallerPairsLabel = new javax.swing.JLabel();
        nummallerPairsAbsLabel = new javax.swing.JLabel();
        numLargerPairsLabel = new javax.swing.JLabel();
        numLargerPairsAbsLabel = new javax.swing.JLabel();
        singleMappings = new javax.swing.JLabel();
        singleMappingsLabel = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Global Track Statistics"));
        setPreferredSize(new java.awt.Dimension(200, 400));

        singletonMappingsLabel.setText("Unique Mappings:");

        uniqueSequencesLabel.setText("Unique Seq:");

        singletonMappingsAbsLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        singletonMappingsAbsLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        perfectMappingsLabel.setText("Perfect:");

        bestMatchMappingsLabel.setText("Best-Match:");

        numMappingsLabel.setText("Mappings:");

        uniqueSequencesAbsLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        uniqueSequencesAbsLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        perfectMappingsAbsLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        perfectMappingsAbsLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        bestMatchMappingsAbsLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        bestMatchMappingsAbsLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        numMappingsAbsLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        numMappingsAbsLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        perfPercentageLabel.setText("Perfect Coverage:");

        bmPercentageLabel.setText("Best Match Coverage:");

        compPercentageLabel.setText("Complete Coverage:");

        perfectPercentage.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        perfectPercentage.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        bmPercentage.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        bmPercentage.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        compPercentage.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        compPercentage.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        labelNameLabel.setText("Name:");

        nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        nameLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        numberReadsLabel.setText("Number Reads:");

        numberReadsAbsLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        numberReadsAbsLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        readPairPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Read Pair Statistics"));

        numReadPairsLabel.setText("Read Pairs:");

        numReadPairsAbsLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        numReadPairsAbsLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        numPerfReadPairsLabel.setText("Perfect Read Pairs:");

        numPerfReadPairsAbsLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        numPerfReadPairsAbsLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        numSmallerPairsLabel.setText("Smaller Read Pairs:");

        nummallerPairsAbsLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        nummallerPairsAbsLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        numLargerPairsLabel.setText("Larger Read Pairs:");

        numLargerPairsAbsLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        numLargerPairsAbsLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        singleMappings.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        singleMappings.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        singleMappingsLabel.setText("Single Mappings:");

        javax.swing.GroupLayout readPairPanelLayout = new javax.swing.GroupLayout(readPairPanel);
        readPairPanel.setLayout(readPairPanelLayout);
        readPairPanelLayout.setHorizontalGroup(
            readPairPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(readPairPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(readPairPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(singleMappingsLabel)
                    .addComponent(numPerfReadPairsLabel)
                    .addComponent(numReadPairsLabel)
                    .addComponent(numSmallerPairsLabel)
                    .addComponent(numLargerPairsLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(readPairPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nummallerPairsAbsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                    .addComponent(numLargerPairsAbsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                    .addComponent(singleMappings, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                    .addComponent(numReadPairsAbsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                    .addComponent(numPerfReadPairsAbsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))
                .addContainerGap())
        );
        readPairPanelLayout.setVerticalGroup(
            readPairPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, readPairPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(readPairPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(numReadPairsLabel)
                    .addComponent(numReadPairsAbsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(readPairPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(numPerfReadPairsLabel)
                    .addComponent(numPerfReadPairsAbsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(readPairPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(numSmallerPairsLabel)
                    .addComponent(nummallerPairsAbsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(readPairPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(numLargerPairsLabel)
                    .addComponent(numLargerPairsAbsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(readPairPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(singleMappings, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(singleMappingsLabel))
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(readPairPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(progressbar, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(bmPercentageLabel)
                            .addComponent(labelNameLabel)
                            .addComponent(numMappingsLabel)
                            .addComponent(singletonMappingsLabel)
                            .addComponent(uniqueSequencesLabel)
                            .addComponent(numberReadsLabel)
                            .addComponent(bestMatchMappingsLabel)
                            .addComponent(perfectMappingsLabel)
                            .addComponent(perfPercentageLabel)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(compPercentageLabel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(compPercentage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(perfectPercentage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(perfectMappingsAbsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(numMappingsAbsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                    .addComponent(nameLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                    .addComponent(singletonMappingsAbsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                    .addComponent(uniqueSequencesAbsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                    .addComponent(numberReadsAbsLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                    .addComponent(bestMatchMappingsAbsLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bmPercentage, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelNameLabel)
                    .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(numMappingsAbsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(numMappingsLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(singletonMappingsLabel)
                    .addComponent(singletonMappingsAbsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(uniqueSequencesLabel)
                    .addComponent(uniqueSequencesAbsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(numberReadsLabel)
                    .addComponent(numberReadsAbsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bestMatchMappingsAbsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bestMatchMappingsLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(perfectMappingsAbsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(perfectMappingsLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(perfectPercentage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(perfPercentageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bmPercentage, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bmPercentageLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(compPercentage, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(compPercentageLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(readPairPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(progressbar, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bestMatchMappingsAbsLabel;
    private javax.swing.JLabel bestMatchMappingsLabel;
    private javax.swing.JLabel bmPercentage;
    private javax.swing.JLabel bmPercentageLabel;
    private javax.swing.JLabel compPercentage;
    private javax.swing.JLabel compPercentageLabel;
    private javax.swing.JLabel labelNameLabel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel numLargerPairsAbsLabel;
    private javax.swing.JLabel numLargerPairsLabel;
    private javax.swing.JLabel numMappingsAbsLabel;
    private javax.swing.JLabel numMappingsLabel;
    private javax.swing.JLabel numPerfReadPairsAbsLabel;
    private javax.swing.JLabel numPerfReadPairsLabel;
    private javax.swing.JLabel numReadPairsAbsLabel;
    private javax.swing.JLabel numReadPairsLabel;
    private javax.swing.JLabel numSmallerPairsLabel;
    private javax.swing.JLabel numberReadsAbsLabel;
    private javax.swing.JLabel numberReadsLabel;
    private javax.swing.JLabel nummallerPairsAbsLabel;
    private javax.swing.JLabel perfPercentageLabel;
    private javax.swing.JLabel perfectMappingsAbsLabel;
    private javax.swing.JLabel perfectMappingsLabel;
    private javax.swing.JLabel perfectPercentage;
    private javax.swing.JProgressBar progressbar;
    private javax.swing.JPanel readPairPanel;
    private javax.swing.JLabel singleMappings;
    private javax.swing.JLabel singleMappingsLabel;
    private javax.swing.JLabel singletonMappingsAbsLabel;
    private javax.swing.JLabel singletonMappingsLabel;
    private javax.swing.JLabel uniqueSequencesAbsLabel;
    private javax.swing.JLabel uniqueSequencesLabel;
    // End of variables declaration//GEN-END:variables

    private void computeStats() {
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                boolean recalculated = false;
                boolean recalcReadPair = false;
                boolean isReadPairTrack = trackCon.isReadPairTrack();
                
                readPairPanel.setVisible(isReadPairTrack);
                numReadPairsLabel.setVisible(isReadPairTrack);
                numReadPairsAbsLabel.setVisible(isReadPairTrack);
                numPerfReadPairsLabel.setVisible(isReadPairTrack);
                numPerfReadPairsAbsLabel.setVisible(isReadPairTrack);
                numSmallerPairsLabel.setVisible(isReadPairTrack);
                nummallerPairsAbsLabel.setVisible(isReadPairTrack);
                numLargerPairsLabel.setVisible(isReadPairTrack);
                numLargerPairsAbsLabel.setVisible(isReadPairTrack);
                singleMappingsLabel.setVisible(isReadPairTrack);
                singleMappings.setVisible(isReadPairTrack);
                
                int chromLength = trackCon.getActiveChromeLength();
                StatsContainer statsContainer = trackCon.getTrackStats();
                Map<String, Integer> statsMap = statsContainer.getStatsMap();

                //read pair stuff
                int numReadPairs = statsMap.get(StatsContainer.NO_SEQ_PAIRS);
                if (numReadPairs == -1) {
//                        TrackStatisticsPanel.this.remove(numReadPairsLabel);
//                        TrackStatisticsPanel.this.remove(numReadPairsAbsLabel);
                    numReadPairsLabel.setVisible(false);
                    numReadPairsAbsLabel.setVisible(false);
                } else {
                    if (numReadPairs == -2) {
                        numReadPairs = trackCon.getNumOfReadPairsCalculate();
                        recalcReadPair = true;
                    }
                    numReadPairsAbsLabel.setText(String.valueOf(numReadPairs));
                }

                int numPerfectReadPairs = statsMap.get(StatsContainer.NO_PERF_PAIRS);
                if (numPerfectReadPairs == -1) {
//                        TrackStatisticsPanel.this.remove(numPerfectSeqPairsLabel);
//                        TrackStatisticsPanel.this.remove(numPerfectSeqPairsAbsLabel);
                    numPerfReadPairsLabel.setVisible(false);
                    numPerfReadPairsAbsLabel.setVisible(false);
                } else {
                    if (numPerfectReadPairs == -2) {
                        numPerfectReadPairs = trackCon.getNumOfPerfectReadPairsCalculate();
                        recalcReadPair = true;
                    }
                    numPerfReadPairsAbsLabel.setText(String.valueOf(numPerfectReadPairs));
                }

                int numSmallerReadPairs = statsMap.get(StatsContainer.NO_SMALL_DIST_PAIRS);
                numSmallerReadPairs += statsMap.get(StatsContainer.NO_SMALL_ORIENT_WRONG_PAIRS);
                if (numSmallerReadPairs == -1) {
//                        TrackStatisticsPanel.this.remove(numPerfectSeqPairsLabel);
//                        TrackStatisticsPanel.this.remove(numPerfectSeqPairsAbsLabel);
                    numSmallerPairsLabel.setVisible(false);
                    nummallerPairsAbsLabel.setVisible(false);
                } else {
//                    if (numSmallerSeqPairs == -2) {
//                        numSmallerSeqPairs = trackCon.getNumOfSmallerSeqPairsCalculate();
//                        recalcSeqPair = true;
//                    }
                    nummallerPairsAbsLabel.setText(String.valueOf(numSmallerReadPairs));
                }

                int numLargerSeqPairs = statsMap.get(StatsContainer.NO_LARGE_DIST_PAIRS);
                numLargerSeqPairs += statsMap.get(StatsContainer.NO_LARGE_ORIENT_WRONG_PAIRS);
                if (numLargerSeqPairs == -1) {
//                        TrackStatisticsPanel.this.remove(numPerfectSeqPairsLabel);
//                        TrackStatisticsPanel.this.remove(numPerfectSeqPairsAbsLabel);
                    numLargerPairsLabel.setVisible(false);
                    numLargerPairsAbsLabel.setVisible(false);
                } else {
//                    if (numLargerSeqPairs == -2) {
//                        numLargerSeqPairs = trackCon.getNumOfLargerSeqPairsCalculate();
//                        recalcSeqPair = true;
//                    }
                    numLargerPairsAbsLabel.setText(String.valueOf(numLargerSeqPairs));
                }

                //single mapping stuff
                int numSingleMappings = statsMap.get(StatsContainer.NO_SINGLE_MAPPIGNS);
                if (numSingleMappings == -1) {
//                        TrackStatisticsPanel.this.remove(numPerfUniqSeqPairsLabel);
//                        TrackStatisticsPanel.this.remove(numPerfUniqSeqPairsAbsLabel);
                    singleMappingsLabel.setVisible(false);
                    singleMappings.setVisible(false);
                } else {
                    if (numSingleMappings == -2) {
                        numSingleMappings = trackCon.getNumOfSingleMappingsCalculate();
                        recalcReadPair = true;
                    }
                    singleMappings.setText(String.valueOf(numSingleMappings));
                }

                int numOfMappings = statsMap.get(StatsContainer.NO_COMMON_MAPPINGS);
                if (numOfMappings == -1) {
                    numOfMappings = trackCon.getNumOfMappingsCalculate();
                    Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Updating statistic information");
                    recalculated = true;
                }
                numMappingsAbsLabel.setText(String.valueOf(numOfMappings));

                int numUniqueMappings = statsMap.get(StatsContainer.NO_UNIQ_MAPPINGS);
                if (numUniqueMappings == -1) {
                    numUniqueMappings = trackCon.getNumOfUniqueMappingsCalculate();
                    recalculated = true;
                }
                singletonMappingsAbsLabel.setText(String.valueOf(numUniqueMappings));

                int numUniqueSequences = statsMap.get(StatsContainer.NO_UNIQUE_SEQS); //NOT CALCULATED CURRENTLY
                if (numUniqueSequences == -1) {
                    numUniqueSequences = trackCon.getNumOfUniqueSequencesCalculate();
                    recalculated = true;
                }
                uniqueSequencesAbsLabel.setVisible(false);
                uniqueSequencesLabel.setVisible(false);
                uniqueSequencesAbsLabel.setText(String.valueOf(numUniqueSequences));

                numberReadsAbsLabel.setVisible(false);
                numberReadsLabel.setVisible(false);
                int numReads = statsMap.get(StatsContainer.NO_READS);
                if (numReads == -1) {
                    numberReadsAbsLabel.setText("Not available"); 
                } else {
                    numberReadsAbsLabel.setText(String.valueOf(numReads));
                }

                int numOfPerfectMappings = statsMap.get(StatsContainer.NO_PERFECT_MAPPINGS);
                if (numOfPerfectMappings == -1) {
                    numOfPerfectMappings = trackCon.getNumOfPerfectUniqueMappingsCalculate();
                    recalculated = true;
                }
                perfectMappingsAbsLabel.setText(String.valueOf(numOfPerfectMappings));

                int numOfBestMatchMappings = statsMap.get(StatsContainer.NO_BESTMATCH_MAPPINGS);
                if (numOfBestMatchMappings == -1) {
                    numOfBestMatchMappings = trackCon.getNumOfUniqueBmMappingsCalculate();
                    recalculated = true;
                }
                bestMatchMappingsAbsLabel.setText(String.valueOf(numOfBestMatchMappings));

                double percentagePerfectCovered = (double) statsMap.get(StatsContainer.COVERAGE_PERFECT_GENOME) / chromLength * 100;
                if (percentagePerfectCovered == -1) {
                    percentagePerfectCovered = trackCon.getPercentRefGenPerfectCoveredCalculate();
                    recalculated = true;
                }

                double percentageBMCovered = (double) statsMap.get(StatsContainer.COVERAGE_BM_GENOME) / chromLength * 100;
                if (percentageBMCovered == -1) {
                    percentageBMCovered = trackCon.getPercentRefGenBmCoveredCalculate();
                    recalculated = true;
                }

                double percentageNErrorCovered = (double) statsMap.get(StatsContainer.COVERAGE_COMPLETE_GENOME) / chromLength * 100;;
                if (percentageNErrorCovered == -1) {
                    percentageNErrorCovered = trackCon.getPercentRefGenNErrorCoveredCalculate();
                    recalculated = true;
                }

//                if (recalculated) {
//                    trackCon.setStatistics(numOfMappings, numUniqueMappings, numUniqueSequences,
//                            numOfPerfectMappings, numOfBestMatchMappings, percentagePerfectCovered,
//                            percentageBMCovered, percentageNErrorCovered, numReads);
//                }
//                if (recalcSeqPair) {
//                    trackCon.addSeqPairStatistics(numSeqPairs, numPerfectSeqPairs, numSmallerSeqPairs,
//                            numLargerSeqPairs, numSingleMappings);
//                }

                String perfectCov = String.format("%.2f%%", percentagePerfectCovered);
                String bmCov = String.format("%.2f%%", percentageBMCovered);
                String nErrorCov = String.format("%.2f%%", percentageNErrorCovered);

                perfectPercentage.setText(perfectCov);
                bmPercentage.setText(bmCov);
                compPercentage.setText(nErrorCov);
                statsFinished();
            }
        }) {
        };
        progressbar.setIndeterminate(true);

        t.setPriority(Thread.MIN_PRIORITY);
        t.start();
    }

    private void statsFinished() {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                progressbar.setIndeterminate(false);
                progressbar.setVisible(false);
            }
        });
    }

    public void close() {
        trackCon = null;
    }

    /**
     * Used for hiding all sequence pair associated labels until they are really
     * needed.
     */
    private void hideSeqPairLabels() {
        this.numberReadsLabel.setVisible(false);
        this.numberReadsAbsLabel.setVisible(false);

        this.numReadPairsLabel.setVisible(false);
        this.numReadPairsAbsLabel.setVisible(false);
        this.numPerfReadPairsLabel.setVisible(false);
        this.numPerfReadPairsAbsLabel.setVisible(false);
        this.numSmallerPairsLabel.setVisible(false);
        this.nummallerPairsAbsLabel.setVisible(false);
        this.numLargerPairsLabel.setVisible(false);
        this.numLargerPairsAbsLabel.setVisible(false);
    }
}