/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cebitec.vamp.rnaTrimming;

import de.cebitec.vamp.util.GeneralUtils;
import de.cebitec.vamp.util.TextAreaOutputStream;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.openide.util.Exceptions;

/**
 * The Panel that shows results for a correlation analysis of two tracks in a table
 * 
 * @author Evgeny Anisiforov <evgeny at cebitec.uni-bielefeld.de>
 */
public class TrimResultPanel extends JPanel implements Observer {

    /**
     * Creates new form CorrelationResultPanel
     */
    public TrimResultPanel() {
        initComponents();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createStatisticsWindow();
                } catch (InterruptedException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        });
    }
    
    private TrimProcessResult analysisResult;
 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        paramsLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        statisticsFrame = new javax.swing.JPanel();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(TrimResultPanel.class, "TrimResultPanel.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(paramsLabel, org.openide.util.NbBundle.getMessage(TrimResultPanel.class, "TrimResultPanel.paramsLabel.text")); // NOI18N
        paramsLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        statisticsFrame.setToolTipText(org.openide.util.NbBundle.getMessage(TrimResultPanel.class, "TrimResultPanel.statisticsFrame.toolTipText")); // NOI18N
        statisticsFrame.setAutoscrolls(true);
        statisticsFrame.setPreferredSize(new java.awt.Dimension(146, 100));
        statisticsFrame.setLayout(new java.awt.BorderLayout());

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(statisticsFrame, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
                        .add(14, 14, 14))
                    .add(layout.createSequentialGroup()
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(paramsLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane2)
                    .add(statisticsFrame, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(layout.createSequentialGroup()
                        .add(3, 3, 3)
                        .add(paramsLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 47, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel paramsLabel;
    private javax.swing.JPanel statisticsFrame;
    // End of variables declaration//GEN-END:variables

    
    public void ready() {
        if (this.plattformIsSupported) {
            Platform.runLater(new Runnable() {
            @Override
            public void run() {
                progress.setProgress(1);
                progress.setVisible(false);
                progressLabel.setText("Ready!");
            }
            }
            );
        }
    }
    
    private PrintStream output;
    
    public PrintStream getOutput() {
        if (this.output==null) this.output = new PrintStream(new TextAreaOutputStream(this.jTextArea1, ""));
        return this.output;
    }
    
    /**
     * @return the analysisResult
     */
    public TrimProcessResult getAnalysisResult() {
        return analysisResult;
    }

    /**
     * @param analysisResult the analysisResult to set
     */
    public void setAnalysisResult(TrimProcessResult analysisResult) {
        if (this.analysisResult!=null) this.analysisResult.deleteObserver(this);
        this.analysisResult = analysisResult;
        this.analysisResult.addObserver(this);
        //use html to allow automatic word wrap for the text in the label
        this.paramsLabel.setText("<html>"+GeneralUtils.escapeHtml(GeneralUtils.implodeMap(": ", ", ", analysisResult.getAnalysisParameters()))+"</html>");    
    }
    
    XYChart.Series<String, Number> series1;
    XYChart.Series<String, Number> series2;
    String all = "source";
    String trimmed = "trimmed";
    
    
    XYChart.Data<String, Number> whole_mapped_data;
    XYChart.Data<String, Number> whole_unmapped_data;
    XYChart.Data<String, Number> trimmed_mapped_data;
    XYChart.Data<String, Number> trimmed_unmapped_data;
    StackedBarChart<String, Number> chart;
    
    private boolean plattformIsSupported = true;
    private ProgressBar progress;
    
    private javafx.scene.control.Label progressLabel;
    
    public void createStatisticsWindow() throws InterruptedException {       
        if (this.plattformIsSupported) {
            // SunOS is not supported by JavaFX
            // in this case an exception will be trown. it is safe to ignore it
            // and continue without showing stats window to the user
            try {

                //JFrame statisticsWindow = new JFrame("Read statistics");
                //statisticsWindow.setSize(500, 500);
                //statisticsFrame.setSize(500, 500);
                final JFXPanel fxPanel = new JFXPanel();
                fxPanel.setForeground(Color.red);
                //fxPanel.setSize(500,500);
                //fxPanel.setPreferredSize(new Dimension(500,500));
                statisticsFrame.setPreferredSize(new Dimension(500,500));
                this.statisticsFrame.setLayout(new BorderLayout());
                this.statisticsFrame.setSize(500,500);
                this.statisticsFrame.add(fxPanel, BorderLayout.CENTER);
                this.statisticsFrame.repaint();
                
                /*statisticsWindow.add(fxPanel);
                statisticsWindow.setVisible(true);
                statisticsWindow.toFront();*/
                
                Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Group root = new Group();
                    fxPanel.setScene(new Scene(root));

                    CategoryAxis xAxis = new CategoryAxis();
                    NumberAxis yAxis = new NumberAxis(); 
                    yAxis.setAutoRanging(true);
                    chart =
                    new StackedBarChart<String, Number>(xAxis, yAxis);
                    chart.setAnimated(false);
                    series2 = new XYChart.Series<String, Number>();
                    series1 = new XYChart.Series<String, Number>();

                    series1.setName("mapped");
                    series2.setName("unmapped");

                    series1.getData().clear();
                    series1.getData().clear();
                    whole_unmapped_data = new XYChart.Data<String, Number>(all, 1);
                    whole_mapped_data = new XYChart.Data<String, Number>(all, 1);
                    trimmed_unmapped_data = new XYChart.Data<String, Number>(trimmed, 1);
                    trimmed_mapped_data = new XYChart.Data<String, Number>(trimmed, 1);   

                    series1.getData().add(trimmed_mapped_data);
                    series2.getData().add(trimmed_unmapped_data);
                    series1.getData().add(whole_mapped_data);
                    series2.getData().add(whole_unmapped_data);

                    xAxis.setLabel("Data");
                    xAxis.setCategories(FXCollections.<String>observableArrayList(
                        Arrays.asList(all, trimmed)));
                    yAxis.setLabel("Reads");

                    chart.setCategoryGap(5);
                    chart.getData().addAll(series2, series1);
                    
                    final VBox vb = new VBox(70);
                    vb.setAlignment(Pos.CENTER);
                    vb.getChildren().add(chart);
                    //final HBox hb = new HBox(10);
                    
                    progress = new ProgressBar(-1.0f);
                    //progress.setStyle("margin: 50px;");
                    progress.setPrefWidth(400);
                    //progress.set
                    progress.setPrefHeight(40);
                    //progress.setRotate(30);
                    vb.getChildren().add(progress);
                    
                    progressLabel = new javafx.scene.control.Label("Trimming in progress.. ");
                    vb.getChildren().add(progressLabel);
                    
                    //vb.getChildren().add(hb);
                    root.getChildren().add(vb);

                }});
            } catch(UnsupportedOperationException e) {
                this.showMsg("Could not intialize statistics window: "+e.getLocalizedMessage());
                this.plattformIsSupported = false;
            }
        }
    } 
    
    private void updateChartData() {
        if (this.plattformIsSupported) {
            //the changes have to be run on the javafx thread 
            Platform.runLater(new Runnable() {
            @Override
            public void run() {
                whole_mapped_data.setYValue(analysisResult.getMappedReads());
                whole_unmapped_data.setYValue(analysisResult.getAllReads()-analysisResult.getMappedReads());
                trimmed_mapped_data.setYValue(analysisResult.getTrimmedMappedReads());
                trimmed_unmapped_data.setYValue(analysisResult.getTrimmedReads()-analysisResult.getTrimmedMappedReads());
            }});
        }
    }
    
    /**
     * If any message should be printed to the console, this method is used.
     * If an error occured during the run of the parser, which does not interrupt
     * the parsing process, this method prints the error to the program console.
     * @param msg the msg to print
     */
    private void showMsg(String msg) {
        this.getOutput().println(msg);
    }

    @Override
    public void update(Observable o, Object arg) {
        this.updateChartData();
    }
}
