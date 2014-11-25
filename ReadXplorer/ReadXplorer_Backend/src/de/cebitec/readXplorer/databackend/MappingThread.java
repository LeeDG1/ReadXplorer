package de.cebitec.readXplorer.databackend;

import de.cebitec.readXplorer.databackend.connector.ProjectConnector;
import de.cebitec.readXplorer.databackend.connector.ReferenceConnector;
import de.cebitec.readXplorer.databackend.dataObjects.MappingResultPersistant;
import de.cebitec.readXplorer.databackend.dataObjects.PersistantDiff;
import de.cebitec.readXplorer.databackend.dataObjects.PersistantMapping;
import de.cebitec.readXplorer.databackend.dataObjects.PersistantReadPairGroup;
import de.cebitec.readXplorer.databackend.dataObjects.PersistantReference;
import de.cebitec.readXplorer.databackend.dataObjects.PersistantReferenceGap;
import de.cebitec.readXplorer.databackend.dataObjects.PersistantTrack;
import de.cebitec.readXplorer.databackend.dataObjects.ReadPairResultPersistant;
import de.cebitec.readXplorer.util.Properties;
import de.cebitec.readXplorer.util.SequenceUtils;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This mapping thread should be used for analyses, but not for visualizing
 * data. The thread carries out the database querries to receive the mappings
 * for a certain interval.
 *
 * @author -Rolf Hilker-
 */
public class MappingThread extends RequestThread {

    public static int FIXED_INTERVAL_LENGTH = 1000;
    private List<PersistantTrack> tracks;
    private int trackId;
    private Connection con;
    ConcurrentLinkedQueue<IntervalRequest> requestQueue;
    private List<PersistantMapping> currentMappings;
    private Collection<PersistantReadPairGroup> currentReadPairs;
    private boolean isDbUsed;
    private PersistantReference refGenome;

    /**
     * Creates a new mapping thread for carrying out mapping request either to a
     * database or a file.
     * @param tracks the track for which this mapping thread is created
     */
    public MappingThread(List<PersistantTrack> tracks) {
        super();
        // do general stuff
        this.requestQueue = new ConcurrentLinkedQueue<>();
        this.con = ProjectConnector.getInstance().getConnection();
        this.tracks = tracks;
        if (this.canQueryData()) {
            ReferenceConnector refConnector = ProjectConnector.getInstance().getRefGenomeConnector(tracks.get(0).getRefGenID());
            this.refGenome = refConnector.getRefGenome();
            for (PersistantTrack track : this.tracks) {
                if (track.isDbUsed()) {
                    this.isDbUsed = true;
                    break;
                }
            }
            this.trackId = this.tracks.get(0).getId();
        }
    }

    @Override
    public void addRequest(IntervalRequest request) {
        this.setLatestRequest(request);
        this.requestQueue.add(request);
    }
    
    /**
     * Collects all mappings of the associated tracks for the interval described
     * by the request parameters. Mappings for DB tracks can only be obtained 
     * for one track.
     * @param request the interval request containing the requested reference
     * interval
     * @return the collection of mappings for the given interval
     */
    List<PersistantMapping> loadMappings(IntervalRequest request) {
        List<PersistantMapping> mappingList = new ArrayList<>();
        if (request.getFrom() < request.getTo() && request.getFrom() > 0 && request.getTo() > 0) {
            if (this.isDbUsed) {
                if (request.isDiffsAndGapsNeeded()) {
                    mappingList = this.loadMappingsWithDiffsFromDB(request);
                } else {
                    mappingList = this.loadMappingsWithoutDiffsFromDB(request);
                }
            }

            Date currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "{0}: Reading mapping data from file...", currentTimestamp);

            PersistantTrack track;
            for (int i = 0; i < this.tracks.size(); ++i) {
                track = tracks.get(i);
                if (!track.isDbUsed()) {
                    SamBamFileReader externalDataReader = new SamBamFileReader(new File(track.getFilePath()), track.getId(), refGenome);
                    Collection<PersistantMapping> intermedRes = externalDataReader.getMappingsFromBam(request);
                    externalDataReader.close();
                    mappingList.addAll(intermedRes);
                }
            }
            if (tracks.size() > 1) {
                Collections.sort(mappingList);
            }

            currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "{0}: Done reading mapping data from file...", currentTimestamp);

        }
        return mappingList;
    }

    /**
     * Loads all mappings (without diffs) from the DB with start positions
     * within the given interval of the reference genome.
     * @param request the genome request containing the requested genome
     * interval
     * @return the list of mappings belonging to the given interval
     */
    List<PersistantMapping> loadMappingsWithoutDiffsFromDB(IntervalRequest request) {

        Date currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());

        List<PersistantMapping> mappings = new ArrayList<>();
        int from = request.getFrom();
        int to = request.getTo();
        ParametersReadClasses readClassParams = request.getReadClassParams();
        
        //Note: uniqueness of mappings cannot be querried from db!!!
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "{0}: Reading mapping data from database...", currentTimestamp);
        try (PreparedStatement fetch = con.prepareStatement(SQLStatements.FETCH_MAPPINGS_WITHOUT_DIFFS)) {
            fetch.setLong(1, from);
            fetch.setLong(2, to);
            fetch.setLong(3, trackId);

            ResultSet rs = fetch.executeQuery();
            //  int counter = 0;
            while (rs.next()) {
                int currentTrackId = rs.getInt(FieldNames.MAPPING_TRACK);
                if (currentTrackId == trackId) {

                    int mismatches = rs.getInt(FieldNames.MAPPING_NUM_OF_ERRORS);
                    boolean isBestMapping = rs.getBoolean(FieldNames.MAPPING_IS_BEST_MAPPING);

                    //only add desired mappings to mappings
                    if (readClassParams.isPerfectMatchUsed() && mismatches == 0
                            || readClassParams.isBestMatchUsed() && isBestMapping
                            || readClassParams.isCommonMatchUsed() && !isBestMapping) {

                        int id = rs.getInt(FieldNames.MAPPING_ID);
                        int start = rs.getInt(FieldNames.MAPPING_START);
                        int stop = rs.getInt(FieldNames.MAPPING_STOP);
                        boolean isFwdStrand = rs.getByte(FieldNames.MAPPING_DIRECTION) == SequenceUtils.STRAND_FWD;
                        int count = rs.getInt(FieldNames.MAPPING_NUM_OF_REPLICATES);
                        int seqId = rs.getInt(FieldNames.MAPPING_SEQUENCE_ID);

                        PersistantMapping mapping = new PersistantMapping(id, start, stop, trackId,
                                isFwdStrand, count, mismatches, seqId, isBestMapping);
                        mappings.add(mapping);
                    }
                }
            }
            rs.close();

            currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "{0}: Done reading mapping data from database...", currentTimestamp);

        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return mappings;
    }

    /**
     * Collects all mappings of the associated track from the DB for the 
     * interval described by the request parameters. Mappings can only be
     * obtained for one track currently. Diffs and gaps are included.
     * @param request the interval request containing the requested reference
     * interval
     * @return the collection of DB mappings for the given interval
     */
    List<PersistantMapping> loadMappingsWithDiffsFromDB(IntervalRequest request) {
        HashMap<Long, PersistantMapping> mappings = new HashMap<>();

        int from = request.getFrom();
        int to = request.getTo();
        ParametersReadClasses readClassParams = request.getReadClassParams();
        Collection<PersistantMapping> mappingList = new ArrayList<>();

        if (from < to && from > 0 && to > 0) {
            try (PreparedStatement fetch = con.prepareStatement(SQLStatements.FETCH_MAPPINGS_FROM_INTERVAL_FOR_TRACK)) {

                //determine readlength
                //TODO: ensure this is only calculated when track id or db changed!
                //TODO: fetch largest read length from db
//                    PreparedStatement fetchReadlength = con.prepareStatement(SQLStatements.GET_CURRENT_READLENGTH);
//                    fetchReadlength.setLong(1, trackID);
//                    ResultSet rsReadlength = fetchReadlength.executeQuery();
//
//                    int readlength = 1000;
//                    final int spacer = 10;
//                    if (rsReadlength.next()) {
//                        int start = rsReadlength.getInt(FieldNames.MAPPING_START);
//                        int stop = rsReadlength.getInt(FieldNames.MAPPING_STOP);
//                        readlength = stop - start + spacer;
//                    }
//                    fetchReadlength.close();


                //mapping processing

                fetch.setLong(1, from - FIXED_INTERVAL_LENGTH);
                fetch.setLong(2, to);
                fetch.setLong(3, from);
                fetch.setLong(4, to + FIXED_INTERVAL_LENGTH);
                fetch.setLong(5, trackId);
                fetch.setLong(6, from);
                fetch.setLong(7, to);

                ResultSet rs = fetch.executeQuery();
                while (rs.next()) {
                    // mapping data
                    int mismatches = rs.getInt(FieldNames.MAPPING_NUM_OF_ERRORS);
                    boolean isBestMapping = rs.getBoolean(FieldNames.MAPPING_IS_BEST_MAPPING);

                    //only add desired mappings to mappings
                    if (readClassParams.isPerfectMatchUsed() && mismatches == 0
                            || readClassParams.isBestMatchUsed() && isBestMapping
                            || readClassParams.isCommonMatchUsed() && !isBestMapping) {

                        int mappingID = rs.getInt(FieldNames.MAPPING_ID);
                        int sequenceID = rs.getInt(FieldNames.MAPPING_SEQUENCE_ID);
                        int mappingTrack = rs.getInt(FieldNames.MAPPING_TRACK);
                        int start = rs.getInt(FieldNames.MAPPING_START);
                        int stop = rs.getInt(FieldNames.MAPPING_STOP);
                        byte direction = rs.getByte(FieldNames.MAPPING_DIRECTION);
                        boolean isForwardStrand = (direction == SequenceUtils.STRAND_FWD);
                        int count = rs.getInt(FieldNames.MAPPING_NUM_OF_REPLICATES);
                        PersistantMapping m = new PersistantMapping(mappingID, start, stop, mappingTrack, isForwardStrand, count, mismatches, sequenceID, isBestMapping);

                        // add new mapping if not exists
                        if (!mappings.containsKey(m.getId())) {
                            mappings.put(m.getId(), m);
                        }

                        // diff data
                        String baseString = rs.getString(FieldNames.DIFF_BASE);
                        int position = rs.getInt(FieldNames.DIFF_POSITION);
                        int type = rs.getInt(FieldNames.DIFF_TYPE);
                        int gapOrder = rs.getInt(FieldNames.DIFF_GAP_ORDER);

                        // diff data may be null, if mapping has no diffs
                        if (baseString != null) {
                            char base = baseString.charAt(0);
                            if (type == 1) {
                                PersistantDiff d = new PersistantDiff(position, base, isForwardStrand, count);
                                mappings.get(m.getId()).addDiff(d);
                            } else if (type == 0) {
                                PersistantReferenceGap g = new PersistantReferenceGap(position, base, gapOrder, isForwardStrand, count);
                                mappings.get(m.getId()).addGenomeGap(g);
                            } else {
                                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "found unknown type for diff in database {0}", type);
                            }
                        }
                    }
                }
                rs.close();

            } catch (SQLException ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex.getStackTrace());
            }

            mappingList = mappings.values();
        }
        
        return new ArrayList<>(mappingList);
    }

    /**
     * Loads all mappings (without diffs) from the DB with ids within the given
     * interval of the reference genome.
     * @param request the genome request containing the requested mapping id
     * interval
     * @return the list of mappings belonging to the given mapping id interval
     * sorted by mapping start
     */
    List<PersistantMapping> loadMappingsById(IntervalRequest request) {

        Date currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "{0}: Reading mapping data from database...", currentTimestamp);

        List<PersistantMapping> mappings = new ArrayList<>();
        int from = request.getFrom();
        int to = request.getTo();
        ParametersReadClasses readClassParams = request.getReadClassParams();

        try (PreparedStatement fetch = con.prepareStatement(SQLStatements.FETCH_MAPPINGS_BY_ID_WITHOUT_DIFFS)) {
            fetch.setLong(1, from);
            fetch.setLong(2, to);

            ResultSet rs = fetch.executeQuery();
            //  int counter = 0;
            while (rs.next()) {
                int currentTrackId = rs.getInt(FieldNames.MAPPING_TRACK);
                if (currentTrackId == this.trackId) {

                    int mismatches = rs.getInt(FieldNames.MAPPING_NUM_OF_ERRORS);
                    boolean isBestMapping = rs.getBoolean(FieldNames.MAPPING_IS_BEST_MAPPING);
                    
                    //only add desired mappings to mappings
                    if (readClassParams.isPerfectMatchUsed() && mismatches == 0
                            || readClassParams.isBestMatchUsed() && isBestMapping
                            || readClassParams.isCommonMatchUsed() && !isBestMapping) {
                        
                        int id = rs.getInt(FieldNames.MAPPING_ID);
                        int start = rs.getInt(FieldNames.MAPPING_START);
                        int stop = rs.getInt(FieldNames.MAPPING_STOP);
                        boolean isFwdStrand = rs.getByte(FieldNames.MAPPING_DIRECTION) == SequenceUtils.STRAND_FWD;
                        int count = rs.getInt(FieldNames.MAPPING_NUM_OF_REPLICATES);
                        int seqId = rs.getInt(FieldNames.MAPPING_SEQUENCE_ID);

                        PersistantMapping mapping = new PersistantMapping(id, start, stop, this.trackId,
                                isFwdStrand, count, mismatches, seqId, isBestMapping);
                        mappings.add(mapping);
                    }
                }


            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

        currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "{0}: Done reading mapping data from database...", currentTimestamp);

        return mappings;
    }

    /**
     * Receives all reduced mappings belonging to the associated tracks. In 
     * order to save memory only Start, Stop and Direction are received by this
     * method. Diffs and gaps are never included.
     * @param request the request to carry out
     * @return list of reduced mappings. Diffs and gaps are never included.
     */
    public List<PersistantMapping> loadReducedMappings(IntervalRequest request) {

        Connection connection = ProjectConnector.getInstance().getConnection();
        Date currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());

        List<PersistantMapping> mappings = new ArrayList<>();
        ParametersReadClasses readClassParams = request.getReadClassParams();
        if (this.isDbUsed) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "{0}: Reading mapping data from database...", currentTimestamp);
            try (PreparedStatement fetch = connection.prepareStatement(SQLStatements.FETCH_REDUCED_MAPPINGS_BY_TRACK_ID_AND_MAP_ID_INTERVAL)) {
                fetch.setLong(1, trackId);
                fetch.setLong(2, request.getFrom());
                fetch.setLong(3, request.getTo());

                ResultSet rs = fetch.executeQuery();
                while (rs.next()) {
                    
                    int mismatches = rs.getInt(FieldNames.MAPPING_NUM_OF_ERRORS);
                    boolean isBestMapping = rs.getBoolean(FieldNames.MAPPING_IS_BEST_MAPPING);

                    //only add desired mappings to mappings
                    if (readClassParams.isPerfectMatchUsed() && mismatches == 0
                            || readClassParams.isBestMatchUsed() && isBestMapping
                            || readClassParams.isCommonMatchUsed() && !isBestMapping) {
                        
                        int start = rs.getInt(FieldNames.MAPPING_START);
                        int stop = rs.getInt(FieldNames.MAPPING_STOP);
                        boolean isFwdStrand = rs.getByte(FieldNames.MAPPING_DIRECTION) == SequenceUtils.STRAND_FWD;
                        int numReplicates = rs.getInt(FieldNames.MAPPING_NUM_OF_REPLICATES);

                        PersistantMapping mapping = new PersistantMapping(start, stop, isFwdStrand, numReplicates);
                        mappings.add(mapping);
                    }
                }
                rs.close();
                
                currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "{0}: Done reading mapping data from database...", currentTimestamp);

            } catch (SQLException ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            }
        } else { //handle retrieving of data from other source than a DB
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "{0}: Reading mapping data from file...", currentTimestamp);
            
            for (int i = 0; i < this.tracks.size(); ++i) {
                if (!this.tracks.get(i).isDbUsed()) {
                    SamBamFileReader externalDataReader = new SamBamFileReader(new File(tracks.get(i).getFilePath()), tracks.get(i).getId(), refGenome);
                    Collection<PersistantMapping> intermedRes = externalDataReader.getReducedMappingsFromBam(request);
                    externalDataReader.close();
                    mappings.addAll(intermedRes);
                }
            }
            if (tracks.size() > 1) {
                Collections.sort(mappings);
            }
            
            currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "{0}: Done reading mapping data from file...", currentTimestamp);
        }

        return mappings;
    }
    
    /**
     * Fetches all read pair mappings for the given interval and typeFlag.
     * @param request The request for which the data shall be gathered
     * @return the collection of read pair mappings for the given interval
     * and typeFlag
     */
    public Collection<PersistantReadPairGroup> getReadPairMappings(IntervalRequest request) {
        Collection<PersistantReadPairGroup> readPairs = new ArrayList<>();
        int from = request.getFrom();
        int to = request.getTo();
        if (from > 0 && to > 0 && from < to) {

            if (this.isDbUsed) {
                JOptionPane.showMessageDialog(new JPanel(), "Read Pair Viewer not available anymore for DB-tracks. Please reimport the data sets!", 
                        "Function not available!", JOptionPane.ERROR_MESSAGE);
            } else {
                for (int i = 0; i < this.tracks.size(); ++i) {
                    if (!this.tracks.get(i).isDbUsed()) {
                        SamBamFileReader reader = new SamBamFileReader(new File(tracks.get(i).getFilePath()), tracks.get(i).getId(), refGenome);
                        Collection<PersistantReadPairGroup> intermedRes = reader.getReadPairMappingsFromBam(request);
                        readPairs.addAll(intermedRes);
                    }
                }
            }
        }
        return readPairs;
    }

    @Override
    public void run() {

        while (!interrupted()) {

            IntervalRequest request = requestQueue.poll();
            if (request != null) {
                if (doesNotMatchLatestRequestBounds(request)) {
                    if (request.getDesiredData() == Properties.READ_PAIRS) {
                        this.currentReadPairs = this.getReadPairMappings(request);
                    } else if (request.getDesiredData() == Properties.MAPPINGS_DB_BY_ID) {
                        currentMappings = this.loadMappingsById(request);
                    } else if (request.getDesiredData() == Properties.REDUCED_MAPPINGS) {
                        currentMappings = this.loadReducedMappings(request);
                    } else {
                        currentMappings = this.loadMappings(request);
                    }
                    //switch between ordinary mappings and read pairs
                    if (request.getDesiredData() != Properties.READ_PAIRS) {
                        request.getSender().receiveData(new MappingResultPersistant(currentMappings, request));
                    } else {
                        request.getSender().receiveData(new ReadPairResultPersistant(currentReadPairs, request));
                    }
                }

            } else {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CoverageThreadAnalyses.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    /**
     * @return true, if the tracklist is not null or empty, false otherwise
     */
    protected boolean canQueryData() {
        return this.tracks != null && !this.tracks.isEmpty();
    }   
}