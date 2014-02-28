package de.cebitec.readXplorer.differentialExpression;

import de.cebitec.centrallookup.CentralLookup;
import de.cebitec.readXplorer.controller.ViewController;
import de.cebitec.readXplorer.differentialExpression.DeAnalysisHandler.AnalysisStatus;
import static de.cebitec.readXplorer.differentialExpression.DeAnalysisHandler.AnalysisStatus.ERROR;
import static de.cebitec.readXplorer.differentialExpression.DeAnalysisHandler.AnalysisStatus.FINISHED;
import static de.cebitec.readXplorer.differentialExpression.DeAnalysisHandler.AnalysisStatus.RUNNING;
import static de.cebitec.readXplorer.differentialExpression.DeAnalysisHandler.Tool.BaySeq;
import static de.cebitec.readXplorer.differentialExpression.DeAnalysisHandler.Tool.DeSeq;
import static de.cebitec.readXplorer.differentialExpression.DeAnalysisHandler.Tool.ExpressTest;
import de.cebitec.readXplorer.differentialExpression.plot.BaySeqGraphicsTopComponent;
import de.cebitec.readXplorer.differentialExpression.plot.DeSeqGraphicsTopComponent;
import de.cebitec.readXplorer.differentialExpression.plot.ExpressTestGraphicsTopComponent;
import de.cebitec.readXplorer.exporter.excel.ExcelExportFileChooser;
import de.cebitec.readXplorer.exporter.excel.TableToExcel;
import de.cebitec.readXplorer.ui.visualisation.reference.ReferenceFeatureTopComp;
import de.cebitec.readXplorer.util.GenerateRowSorter;
import de.cebitec.readXplorer.util.Observer;
import de.cebitec.readXplorer.util.UneditableTableModel;
import de.cebitec.readXplorer.view.TopComponentExtended;
import de.cebitec.readXplorer.view.dataVisualisation.BoundsInfoManager;
import de.cebitec.readXplorer.view.tableVisualization.TableUtils;
import de.cebitec.readXplorer.view.tableVisualization.tableFilter.TableRightClickFilter;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

/**
 * Top component which displays the results of differential expression analyses.
 */
@ConvertAsProperties(dtd = "-//de.cebitec.readXplorer.differentialExpression//DiffExpResultViewer//EN",
        autostore = false)
@TopComponent.Description(preferredID = "DiffExpResultViewerTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_NEVER)
@TopComponent.Registration(mode = "bottomSlidingSide", openAtStartup = false)
@ActionID(category = "Window", id = "de.cebitec.readXplorer.differentialExpression.DiffExpResultViewerTopComponent")
@ActionReference(path = "Menu/Window" /*
         * , position = 333
         */)
@TopComponent.OpenActionRegistration(displayName = "#CTL_DiffExpResultViewerAction",
        preferredID = "DiffExpResultViewerTopComponent")
@Messages({
    "CTL_DiffExpResultViewerAction=DiffExpResultViewer",
    "# {0} - tool",
    "CTL_DiffExpResultViewerTopComponent={0} Differential Gene Expression Results",
    "HINT_DiffExpResultViewerTopComponent=This is a Differential Gene Expression Result Window"
})
public final class DiffExpResultViewerTopComponent extends TopComponentExtended implements Observer, ItemListener {

    private static final long serialVersionUID = 1L;
    private TableModel tm;
    private ComboBoxModel<Object> cbm;
    private ArrayList<DefaultTableModel> tableModels = new ArrayList<>();
    private TopComponent graphicsTopComponent;
    private ExpressTestGraphicsTopComponent ptc;
    private TopComponent LogTopComponent;
    private DeAnalysisHandler analysisHandler;
    private DeAnalysisHandler.Tool usedTool;
    private ProgressHandle progressHandle = ProgressHandleFactory.createHandle("Differential Gene Expression Analysis");
    private TableRightClickFilter<UneditableTableModel> rktm = new TableRightClickFilter<>(UneditableTableModel.class);
    private ReferenceFeatureTopComp refComp;

    public DiffExpResultViewerTopComponent() {
    }

    public DiffExpResultViewerTopComponent(DeAnalysisHandler handler, DeAnalysisHandler.Tool usedTool) {
        refComp = ReferenceFeatureTopComp.findInstance();
        this.analysisHandler = handler;
        this.usedTool = usedTool;

        tm = new UneditableTableModel();
        cbm = new DefaultComboBoxModel<>();

        initComponents();
        setName(Bundle.CTL_DiffExpResultViewerTopComponent(usedTool));
        setToolTipText(Bundle.HINT_DiffExpResultViewerTopComponent());
        topCountsTable.getTableHeader().addMouseListener(rktm);
        topCountsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                showPosition();
            }
        });
    }

    /**
     * Updates the position in all available bounds info managers to the 
     * reference position of the currently selected genomic feature.
     */
    private void showPosition() {
        Collection<ViewController> viewControllers =
                (Collection<ViewController>) CentralLookup.getDefault().lookupAll(ViewController.class);
        analysisHandler.getRefGenomeID();
        for (Iterator<ViewController> it = viewControllers.iterator(); it.hasNext();) {
            ViewController tmpVCon = it.next();
            BoundsInfoManager bm = tmpVCon.getBoundsManager(); 
            if (bm != null && analysisHandler.getRefGenomeID() == tmpVCon.getCurrentRefGen().getId()) {
                int posIdx = 0;
                int chromIdx = 1;
                TableUtils.showPosition(topCountsTable, posIdx, chromIdx, bm);
            }
        }
        refComp.showTableFeature(topCountsTable, 0);
    }

    /**
     * Adds the results of a finished diff. gene expr. analysis to the table of
     * this top component.
     */
    private void addResults() {
        if (analysisHandler.getResults() != null) {
            List<ResultDeAnalysis> results = analysisHandler.getResults();
            List<String> descriptions = new ArrayList<>();
            for (Iterator<ResultDeAnalysis> it = results.iterator(); it.hasNext();) {
                ResultDeAnalysis currentResult = it.next();
                Vector colNames = new Vector(currentResult.getColnames());
                Vector<Vector> tableContents;
                colNames.remove(0);
                colNames.add(0, "Feature");
                tableContents = currentResult.getTableContents();

                DefaultTableModel tmpTableModel = new UneditableTableModel(tableContents, colNames);
                descriptions.add(currentResult.getDescription());
                tableModels.add(tmpTableModel);
            }

            resultComboBox.setModel(new DefaultComboBoxModel<>(descriptions.toArray()));
            DefaultTableModel dtm = tableModels.get(0);
            topCountsTable.setModel(dtm);
            TableRowSorter<DefaultTableModel> trs = GenerateRowSorter.createRowSorter(dtm);
            topCountsTable.setRowSorter(trs);
            if (usedTool == ExpressTest) {
                List<RowSorter.SortKey> sortKeys = new ArrayList<>();
                sortKeys.add(new RowSorter.SortKey(8, SortOrder.DESCENDING));
                trs.setSortKeys(sortKeys);
                trs.sort();
            }

            createGraphicsButton.setEnabled(true);
            saveTableButton.setEnabled(true);
            showLogButton.setEnabled(true);
            resultComboBox.setEnabled(true);
            topCountsTable.setEnabled(true);
            jLabel1.setEnabled(true);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        resultComboBox = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        topCountsTable = new javax.swing.JTable();
        createGraphicsButton = new javax.swing.JButton();
        saveTableButton = new javax.swing.JButton();
        showLogButton = new javax.swing.JButton();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(DiffExpResultViewerTopComponent.class, "DiffExpResultViewerTopComponent.jLabel1.text")); // NOI18N
        jLabel1.setEnabled(false);

        resultComboBox.setModel(cbm);
        resultComboBox.setEnabled(false);
        resultComboBox.addItemListener(this);

        topCountsTable.setAutoCreateRowSorter(true);
        topCountsTable.setModel(tm);
        topCountsTable.setEnabled(false);
        topCountsTable.setRowSorter(null);
        jScrollPane1.setViewportView(topCountsTable);

        org.openide.awt.Mnemonics.setLocalizedText(createGraphicsButton, org.openide.util.NbBundle.getMessage(DiffExpResultViewerTopComponent.class, "DiffExpResultViewerTopComponent.createGraphicsButton.text")); // NOI18N
        createGraphicsButton.setEnabled(false);
        createGraphicsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createGraphicsButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(saveTableButton, org.openide.util.NbBundle.getMessage(DiffExpResultViewerTopComponent.class, "DiffExpResultViewerTopComponent.saveTableButton.text")); // NOI18N
        saveTableButton.setEnabled(false);
        saveTableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveTableButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(showLogButton, org.openide.util.NbBundle.getMessage(DiffExpResultViewerTopComponent.class, "DiffExpResultViewerTopComponent.showLogButton.text")); // NOI18N
        showLogButton.setEnabled(false);
        showLogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showLogButtonActionPerformed(evt);
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resultComboBox, 0, 253, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(saveTableButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(createGraphicsButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(showLogButton))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(resultComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(createGraphicsButton)
                    .addComponent(saveTableButton)
                    .addComponent(showLogButton))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 479, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void createGraphicsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createGraphicsButtonActionPerformed
        switch (usedTool) {
            case DeSeq:
                graphicsTopComponent = new DeSeqGraphicsTopComponent(analysisHandler,
                        ((DeSeqAnalysisHandler) analysisHandler).moreThanTwoCondsForDeSeq());
                analysisHandler.registerObserver((DeSeqGraphicsTopComponent) graphicsTopComponent);
                graphicsTopComponent.open();
                graphicsTopComponent.requestActive();
                break;
            case BaySeq:
                graphicsTopComponent = new BaySeqGraphicsTopComponent(analysisHandler);
                analysisHandler.registerObserver((BaySeqGraphicsTopComponent) graphicsTopComponent);
                graphicsTopComponent.open();
                graphicsTopComponent.requestActive();
                break;
            case ExpressTest:
                ptc = new ExpressTestGraphicsTopComponent(analysisHandler, usedTool);
                analysisHandler.registerObserver(ptc);
                ptc.open();
                ptc.requestActive();
                break;
        }
    }//GEN-LAST:event_createGraphicsButtonActionPerformed

    private void saveTableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveTableButtonActionPerformed
        ExcelExportFileChooser fc = new ExcelExportFileChooser(new String[]{"xls"},
                "xls", new TableToExcel(resultComboBox.getSelectedItem().toString(), (UneditableTableModel) topCountsTable.getModel()));
    }//GEN-LAST:event_saveTableButtonActionPerformed

    private void showLogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showLogButtonActionPerformed
        LogTopComponent = new DiffExpLogTopComponent(analysisHandler);
        analysisHandler.registerObserver((DiffExpLogTopComponent) LogTopComponent);
        LogTopComponent.open();
        LogTopComponent.requestActive();
    }//GEN-LAST:event_showLogButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createGraphicsButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<Object> resultComboBox;
    private javax.swing.JButton saveTableButton;
    private javax.swing.JButton showLogButton;
    private javax.swing.JTable topCountsTable;
    // End of variables declaration//GEN-END:variables

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        analysisHandler.removeObserver(this);
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
//        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    public void update(Object args) {
        final AnalysisStatus status = (AnalysisStatus) args;
        final DiffExpResultViewerTopComponent cmp = this;
        //Might be called from outside of the EDT, so using swing utils
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                switch (status) {
                    case RUNNING:
                        progressHandle.start();
                        progressHandle.switchToIndeterminate();
                        break;
                    case FINISHED:
                        addResults();
                        progressHandle.switchToDeterminate(100);
                        progressHandle.finish();
                        break;
                    case ERROR:
                        progressHandle.switchToDeterminate(0);
                        progressHandle.finish();
                        LogTopComponent = new DiffExpLogTopComponent();
                        LogTopComponent.open();
                        LogTopComponent.requestActive();
                        cmp.close();
                        break;
                }
            }
        });
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        int state = e.getStateChange();
        if (state == ItemEvent.SELECTED) {
            rktm.resetOriginalTableModel();
            DefaultTableModel dtm = tableModels.get(resultComboBox.getSelectedIndex());
            topCountsTable.setModel(dtm);
            TableRowSorter<DefaultTableModel> trs = GenerateRowSorter.createRowSorter(dtm);
            topCountsTable.setRowSorter(trs);
            if (usedTool == ExpressTest) {
                List<RowSorter.SortKey> sortKeys = new ArrayList<>();
                sortKeys.add(new RowSorter.SortKey(8, SortOrder.DESCENDING));
                trs.setSortKeys(sortKeys);
                trs.sort();
            }
        }
    }
}
