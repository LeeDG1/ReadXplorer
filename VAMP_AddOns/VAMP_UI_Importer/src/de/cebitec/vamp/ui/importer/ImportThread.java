package de.cebitec.vamp.ui.importer;

import de.cebitec.vamp.parser.mappings.ISeqPairClassifier;
import de.cebitec.centrallookup.CentralLookup;
import de.cebitec.vamp.parser.TrackJob;
import de.cebitec.vamp.parser.ReferenceJob;
import de.cebitec.vamp.databackend.connector.ProjectConnector;
import de.cebitec.vamp.databackend.connector.StorageException;
import de.cebitec.vamp.parser.common.ParsedReference;
import de.cebitec.vamp.parser.common.ParsedRun;
import de.cebitec.vamp.parser.common.ParsedTrack;
import de.cebitec.vamp.parser.common.ParsingException;
import de.cebitec.vamp.parser.mappings.TrackParser;
import de.cebitec.vamp.parser.mappings.TrackParserI;
import de.cebitec.vamp.parser.reference.Filter.FeatureFilter;
import de.cebitec.vamp.parser.reference.Filter.FilterRuleSource;
import de.cebitec.vamp.parser.reference.ReferenceParserI;
import de.cebitec.vamp.util.Observer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import de.cebitec.vamp.parser.common.ParsedSeqPairContainer;
import de.cebitec.vamp.parser.mappings.JokSeqPairParser;
import org.openide.util.Lookup;

/**
 * Thread handling the import of data.
 *
 * @author ddoppmeier
 */
public class ImportThread extends SwingWorker<Object, Object> implements Observer {

    private InputOutput io;
    private List<ReferenceJob> references;
    private List<TrackJob> tracksJobs;
    private List<SeqPairJobContainer> seqPairJobs;
    private HashMap<TrackJob, Boolean> validTracksRun;
    private ProgressHandle ph;
    private int workunits;

    public ImportThread(List<ReferenceJob> refJobs, List<TrackJob> trackJobs, List<SeqPairJobContainer> seqPairJobs) {
        super();
        this.io = IOProvider.getDefault().getIO(NbBundle.getMessage(ImportThread.class, "ImportThread.output.name"), false);
        this.tracksJobs = trackJobs;
        this.references = refJobs;
        this.seqPairJobs = seqPairJobs;
        this.ph = ProgressHandleFactory.createHandle(NbBundle.getMessage(ImportThread.class, "MSG_ImportThread.progress.name"));
        this.workunits = refJobs.size() + 2 * trackJobs.size() + 2 * seqPairJobs.size();

        this.validTracksRun = new HashMap<TrackJob, Boolean>();
    }

    private ParsedReference parseRefJob(ReferenceJob refGenJob) throws ParsingException {
        Logger.getLogger(ImportThread.class.getName()).log(Level.INFO, "Start parsing reference genome from source \"{0}\"", refGenJob.getFile().getAbsolutePath());

        ReferenceParserI parser = refGenJob.getParser();
        FeatureFilter filter = new FeatureFilter();
        filter.addBlacklistRule(new FilterRuleSource());
        ParsedReference refGenome = parser.parseReference(refGenJob, filter);

        Logger.getLogger(ImportThread.class.getName()).log(Level.INFO, "Finished parsing reference genome from source \"{0}\"", refGenJob.getFile().getAbsolutePath());
        return refGenome;
    }

    /**
     * 
     * @param trackJob
     * @return
     * @throws ParsingException
     * @deprecated Since the RUN domain has been excluded this method is not needed anymore!
     */
    @Deprecated
    private ParsedRun parseRunfromTrack(TrackJob trackJob) throws ParsingException {
        Logger.getLogger(ImportThread.class.getName()).log(Level.INFO, "Start parsing run data from mapping source \"{0}\"", trackJob.getFile().getAbsolutePath());
        TrackParserI parser = new TrackParser();
        Logger.getLogger(ImportThread.class.getName()).log(Level.INFO, "Start parsing parser: \"{0}\"", parser.toString());
        ParsedRun run = parser.parseMappingforReadData(trackJob);

        Logger.getLogger(ImportThread.class.getName()).log(Level.INFO, "Finished parising run data from mapping source \"{0}\"", trackJob.getFile().getAbsolutePath());
        return run;
    }

    
    private ParsedTrack parseTrack(TrackJob trackJob) throws ParsingException {
        Logger.getLogger(ImportThread.class.getName()).log(Level.INFO, "Start parsing track data from source \"{0}trackjobID{1}\"", new Object[]{trackJob.getFile().getAbsolutePath(), trackJob.getID()});

//        HashMap<String, Integer> readnameToSeqIDmap = ProjectConnector.getInstance().getRunConnector(trackJob.getID(), trackJob.getID()).getReadnameToSeqIDMapping();

        // XXX does this work for all import methods???
        String sequenceString = null;
        try {
            Long id = trackJob.getRefGen().getID();
            sequenceString = ProjectConnector.getInstance().getRefGenomeConnector(id).getRefGen().getSequence();
        } catch (Exception ex) {
            Logger.getLogger(ImportThread.class.getName()).log(Level.WARNING, "Could not get the ref genome\"{0}\"{1}", new Object[]{trackJob.getFile().getAbsolutePath(), ex});
        }

        TrackParserI parser = new TrackParser();
//        ParsedTrack track = parser.parseMappings(trackJob, readnameToSeqIDmap, sequenceString, this);
        ParsedTrack track = parser.parseMappings(trackJob, sequenceString, this);

        Logger.getLogger(ImportThread.class.getName()).log(Level.INFO, "Finished parsing track data from source \"{0}\"", trackJob.getFile().getAbsolutePath());
        return track;
    }


    private void storeRefGenome(ParsedReference refGenome, ReferenceJob refGenJob) throws StorageException {
        Logger.getLogger(ImportThread.class.getName()).log(Level.INFO, "Start storing reference genome from source \"{0}\"", refGenJob.getFile().getAbsolutePath());

        long refGenID = ProjectConnector.getInstance().addRefGenome(refGenome);
        refGenJob.setPersistant(refGenID);

        Logger.getLogger(ImportThread.class.getName()).log(Level.INFO, "Finished storing reference genome from source \"{0}\"", refGenJob.getFile().getAbsolutePath());
    }

   /* private void storeRun(ParsedRun run, RunJob runJob) throws StorageException{
        Logger.getLogger(ImportThread.class.getName()).log(Level.INFO, "Start storing run data from source \""+runJob.getFile().getAbsolutePath()+"\"");

        long runID = ProjectConnector.getInstance().addRun(run);

        runJob.setPersistant(runID);

        Logger.getLogger(ImportThread.class.getName()).log(Level.INFO, "Finished storing run data from source \""+runJob.getFile().getAbsolutePath()+"\"");

    }*/

//    public void storeRunFromTrackData(ParsedRun run , TrackJob trackJob)throws StorageException{
//        Logger.getLogger(ImportThread.class.getName()).log(Level.INFO, "Start storing run data from source \"{0}\"", trackJob.getFile().getAbsolutePath());
//
//        long runID = ProjectConnector.getInstance().addRun(run);
//
//         trackJob.setPersistant(runID);
//
//        Logger.getLogger(ImportThread.class.getName()).log(Level.INFO, "Finished storing run data from source \"{0}runID{1}\"", new Object[]{trackJob.getFile().getAbsolutePath(), runID});
//    }

    
    private void storeTrack(ParsedTrack track, TrackJob trackJob, boolean seqPairs) throws StorageException{
        Logger.getLogger(ImportThread.class.getName()).log(Level.INFO, "Start storing track data from source \"{0}\"", trackJob.getFile().getAbsolutePath());

        Long trackID = ProjectConnector.getInstance().addTrack(track, trackJob.getRefGen().getID(), seqPairs);
        trackJob.setPersistant(trackID);

        Logger.getLogger(ImportThread.class.getName()).log(Level.INFO, "Finished storing track data from source \"{0}\"", trackJob.getFile().getAbsolutePath());
    }


    private void setValidTracksRun(List<TrackJob> trackJobs, boolean valid){
        for(Iterator<TrackJob> it = trackJobs.iterator(); it.hasNext(); ){
            TrackJob t = it.next();
            if(validTracksRun.containsKey(t)){
                // do not change status of tracks back from false to true
                // once false, always false
                if(validTracksRun.get(t) == true){
                    validTracksRun.put(t, valid);
                }
            } else {
                // register new track
                validTracksRun.put(t, valid);
            }
        }
    }

//    private boolean isValidTrackwithoutRun(TrackJob trackJob) {
//        if (!validTracksRun.containsKey(trackJob)) {
//            // track is not dependent on previous run oder refGen, so it is not registered
//            return true;
//        } else if (validTracksRun.containsKey(trackJob) && validTracksRun.get(trackJob) == true) {
//            // if it is registered, it must be a valid one
//            return true;
//        } else {
//            return false;
//        }
//    }

    private void processRefGenomeJobs(){
        if(!references.isEmpty()){
            io.getOut().println(NbBundle.getMessage(ImportThread.class, "MSG_ImportThread.import.start.ref") + ":");

            for(Iterator<ReferenceJob> it = references.iterator(); it.hasNext(); ){
                ReferenceJob r = it.next();
                ph.progress(workunits++);

                try {
                    // parsing
                    ParsedReference refGen = parseRefJob(r);
                    io.getOut().println("\""+r.getName() + "\" " + NbBundle.getMessage(ImportThread.class, "MSG_ImportThread.import.parsed"));

                    // storing
                    try {
                        storeRefGenome(refGen, r);
                        io.getOut().println("\""+r.getName() + "\" " + NbBundle.getMessage(ImportThread.class, "MSG_ImportThread.import.stored"));
                    } catch (StorageException ex) {
                        // if something went wrong, mark all dependent track jobs
                        io.getOut().println("\""+r.getName() + "\" " + NbBundle.getMessage(ImportThread.class, "MSG_ImportThread.import.failed") + "!");
                        if(r.hasRegisteredTrackswithoutrRunJob()){
                            setValidTracksRun(r.getDependentTrackswithoutRunjob(), false);
                        }
                        Logger.getLogger(ImportThread.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    // validate tracks
                    setValidTracksRun(r.getDependentTrackswithoutRunjob(), true);

                } catch (ParsingException ex) {
                    // if something went wrong, mark all dependent track jobs
                    io.getOut().println("\""+r.getName() + "\" " + NbBundle.getMessage(ImportThread.class, "MSG_ImportThread.import.failed") + "!");
                    if(r.hasRegisteredTrackswithoutrRunJob()){
                        setValidTracksRun(r.getDependentTrackswithoutRunjob(), false);
                    }
                    Logger.getLogger(ImportThread.class.getName()).log(Level.SEVERE, null, ex);
                }

                it.remove();
            }

            io.getOut().println("");
        }
    }


//    private void processTrackRUNJobs(){
//        if(!tracksRun.isEmpty()){
//            io.getOut().println(NbBundle.getMessage(ImportThread.class, "MSG_ImportThread.import.start.readtrack") + ":");
//            for(Iterator<TrackJob> it = tracksRun.iterator(); it.hasNext(); ){
//                TrackJob t = it.next();
//                ph.progress(workunits++);
//
//                // only import this track if no problems occured with dependencies
//                if(isValidTrackwithoutRun(t)){
//                    try {
//
//                        //parsing
//                        ParsedRun run = parseRunfromTrack(t);
//                        io.getOut().println("\""+t.getFile().getName() + "\" " + NbBundle.getMessage(ImportThread.class, "MSG_ImportThread.import.parsed"));
//                    //returns the reads that couldnt be read by the parser
//                    if(!run.getErrorList().isEmpty() || run.getSequences().isEmpty()){
//                    io.getOut().println(NbBundle.getMessage(ImportThread.class, "MSG_ImportThread.import.error.readload") + ": " + run.getErrorList().toString());
//                    }
//                    //storing
//                    try {
//                        storeRunFromTrackData(run, t);
//                        io.getOut().println("\""+t.getFile().getName() + "\" " + NbBundle.getMessage(ImportThread.class, "MSG_ImportThread.import.stored"));
//                    } catch (StorageException ex) {
//                        // if something went wrong, mark all dependent track jobs
//                        io.getOut().println("\""+t.getFile().getName() + "\" " + NbBundle.getMessage(ImportThread.class, "MSG_ImportThread.import.failed") + "!");
//                        Logger.getLogger(ImportThread.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//
//            } catch (ParsingException ex) {
//                        // something went wrong
//                        io.getOut().println("\""+t.getFile().getName() + "\" " + NbBundle.getMessage(ImportThread.class, "MSG_ImportThread.import.failed") + "!");
//                        Logger.getLogger(ImportThread.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                } else {
//                    io.getOut().println("\""+t.getFile().getName()+" " + NbBundle.getMessage(ImportThread.class, "MSG_ImportThread.import.error.depend") + "!");
//                }
//            }
//        }
//    }

    /**
     * Processes track jobs (parsing and storing) and also handles sequence pair/paired end data parsing
     * and storing.
     */
    private void processTrackJobs() {
        if (!tracksJobs.isEmpty()) {
            io.getOut().println(NbBundle.getMessage(ImportThread.class, "MSG_ImportThread.import.start.track") + ":");

            for (Iterator<TrackJob> it = tracksJobs.iterator(); it.hasNext();) {
                TrackJob t = it.next();
                ph.progress(workunits++);

                //parsing track
                this.parseSingleTrack(t);
                
                it.remove();
            }
        }
    }
    
    
    private void processSeqPairJobs(){
                               
        if(!seqPairJobs.isEmpty()){
            
            //handle processing of sequence pair track jobs AFTER both belonging tracks are stored!
            //because now all mapping ids are set!
            
            io.getOut().println(NbBundle.getMessage(ImportThread.class, "MSG_ImportThread.import.start.seqPairs") + ":");

            for(Iterator<SeqPairJobContainer> it = seqPairJobs.iterator(); it.hasNext(); ){
                SeqPairJobContainer seqPairJobContainer = it.next();
                ph.progress(workunits++);
                
                int distance = seqPairJobContainer.getDistance();
                if (distance > 0) {
                    //parsing tracks
                    ParsedTrack track1 = this.parseSingleTrack(seqPairJobContainer.getTrackJob1());
                    ParsedTrack track2 = this.parseSingleTrack(seqPairJobContainer.getTrackJob2());

                    final ISeqPairClassifier seqPairClassifier = Lookup.getDefault().lookup(ISeqPairClassifier.class);
                    if (seqPairClassifier != null) {
                        seqPairClassifier.setData(track1, track2, distance, seqPairJobContainer.getDeviation(), seqPairJobContainer.getOrientation());
                        String description = seqPairJobContainer.getTrackJob1().getFile().getName() + " and " + seqPairJobContainer.getTrackJob2().getFile().getName();

                        try { //storing sequence pairs data
                            this.storeSeqPairs(seqPairClassifier.classifySeqPairs(), description);
                            io.getOut().println("\"" + description + " sequence pair data infos \" " + NbBundle.getMessage(ImportThread.class, "MSG_ImportThread.import.stored"));
                        } catch (StorageException ex) {
                            // something went wrong
                            io.getOut().println("\"" + description + " sequence pair data infos \" " + NbBundle.getMessage(ImportThread.class, "MSG_ImportThread.import.failed") + "!");
                            Logger.getLogger(ImportThread.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        track1.clear();
                        track2.clear();
                    }
                } else 
                if (distance <= 0){
                    this.showErrorMsg(NbBundle.getMessage(ImportThread.class, "MSG_ImportThread.import.error"));
                }

                it.remove();
                
            }
        }
    }
        

    /**
     * Parses a trackJob.
     * @param trackJob job to parse
     * @return returns the trackJob if everything went fine, otherwise <code>null</code>
     */
    private ParsedTrack parseSingleTrack(TrackJob trackJob) {

        // only import this track if no problems occured with dependencies
        try {

            //parsing track
            ParsedTrack track = this.parseTrack(trackJob);
            boolean seqPairs = false;
            if (trackJob.getParser() instanceof JokSeqPairParser){
                //TODO: implement seq pair support for all parsers!
                track.setReadnameToSeqIdMap(((JokSeqPairParser) trackJob.getParser()).getSeqIDToReadNameMap());
                ((JokSeqPairParser) trackJob.getParser()).resetSeqIdToReadnameMap();
                seqPairs = true;
            }
            io.getOut().println("\"" + trackJob.getFile().getName() + "\" " + NbBundle.getMessage(ImportThread.class, "MSG_ImportThread.import.parsed"));
            
            
            //storing track
            try {
                this.storeTrack(track, trackJob, seqPairs);
                io.getOut().println("\"" + trackJob.getFile().getName() + "\" " + NbBundle.getMessage(ImportThread.class, "MSG_ImportThread.import.stored"));
            } catch (StorageException ex) {
                // something went wrong
                io.getOut().println("\"" + trackJob.getFile().getName() + "\" " + NbBundle.getMessage(ImportThread.class, "MSG_ImportThread.import.failed") + "!");
                Logger.getLogger(ImportThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            return track;

            
        } catch (ParsingException ex) {
            // something went wrong
            io.getOut().println(ex.getMessage());
            io.getOut().println("\"" + trackJob.getFile().getName() + "\" " + NbBundle.getMessage(ImportThread.class, "MSG_ImportThread.import.failed") + "!");
        }
        return null;
    }
        
    
    @Override
    protected Object doInBackground() {
        CentralLookup.getDefault().add(this);
        try {
            io.getOut().reset();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        io.select();

        ph.start(workunits);
        workunits = 0;

        ph.progress(NbBundle.getMessage(ImportThread.class, "MSG_ImportThread.progress.ref") + "...", workunits);
        this.processRefGenomeJobs();

//        ph.progress(NbBundle.getMessage(ImportThread.class, "MSG_ImportThread.progress.readtrack") + "...", workunits);
//        processTrackRUNJobs();
        // track jobs have to be imported last, because they may depend upon previously imported genomes, runs
        ph.progress(NbBundle.getMessage(ImportThread.class, "MSG_ImportThread.progress.track") + "...", workunits);
        this.processTrackJobs();
        this.processSeqPairJobs();
        validTracksRun.clear();

        return null;
    }

    @Override
    protected void done(){
        super.done();
        ph.progress(workunits);
        io.getOut().println(NbBundle.getMessage(ImportThread.class, "MSG_ImportThread.import.finished"));
        io.getOut().close();
        ph.finish();

        CentralLookup.getDefault().remove(this);
    }

    @Override
    public void update(Object errorMsg) {
        if (errorMsg instanceof String){
            this.showErrorMsg((String) errorMsg);
        }
    }

    /**
     * If an error occured during the run of the parser, which does not interrupt
     * the parsing process, this method prints the error to the program console.
     * @param errorMsg
     */
    private void showErrorMsg(String errorMsg) {
        this.io.getOut().println("\""+errorMsg);
    }

    
    private void storeSeqPairs(ParsedSeqPairContainer seqPairContainer, String description) throws StorageException {
        
        Logger.getLogger(ImportThread.class.getName()).log(Level.INFO, "Start storing sequence pair data for track data from source \"{0}\"", description);
        ProjectConnector.getInstance().addSeqPairData(seqPairContainer);
        Logger.getLogger(ImportThread.class.getName()).log(Level.INFO, "Finished storing sequence pair data for track data from source \"{0}\"", description);
    
    }
}