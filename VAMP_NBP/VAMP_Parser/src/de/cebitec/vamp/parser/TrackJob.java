package de.cebitec.vamp.parser;

import de.cebitec.vamp.parser.mappings.MappingParserI;
import java.io.File;
import java.sql.Timestamp;

/**
 * A track job is a container for all necessary data for a track to be parsed.
 *
 * @author jstraube
 */
public class TrackJob implements Job {

    private File file;
    private String description;
    private Timestamp timestamp;
    private MappingParserI parser;
    private Long trackID;
    private ReferenceJob refGen;

    /**
     * Creates a new track job along with its data.
     * @param trackID id of the track to create
     * @param file the file to be parsed as track
     * @param description the description of the track
     * @param refGen the ReferenceJob with all information about the reference
     * @param parser the parser to use for parsing
     * @param timestamp the timestamp when it was created
     */
    public TrackJob(Long trackID, File file, String description, ReferenceJob refGen, MappingParserI parser, Timestamp timestamp) {
        this.trackID = trackID;
        this.file = file;
        this.description = description;
        this.timestamp = timestamp;
        this.parser = parser;
        this.refGen = refGen;
    }

    public TrackJob(Long trackID, File file, String description, ReferenceJob refGen,
            MappingParserI parser, Timestamp timestamp, int distance, int deviation) {
        this(trackID, file, description, refGen, parser, timestamp);
    }

    public MappingParserI getParser() {
        return parser;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public ReferenceJob getRefGen() {
        return refGen;
    }

    @Override
    public File getFile() {
        return file;
    }

    public void setRefGen(ReferenceJob refGen) {
        this.refGen = refGen;
    }

    @Override
    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public Long getID() {
        return trackID;
    }

    @Override
    public String getName() {
        return getDescription();
    }
    
    @Override
    public String toString() {
        return description + ":" + timestamp;
    }

    public void setPersistant(Long trackID) {
        this.trackID = trackID;
    }
    
}