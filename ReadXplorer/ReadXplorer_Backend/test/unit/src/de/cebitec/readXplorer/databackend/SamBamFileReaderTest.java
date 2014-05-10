package de.cebitec.readXplorer.databackend;

import de.cebitec.readXplorer.databackend.dataObjects.PersistantCoverage;
import de.cebitec.readXplorer.databackend.dataObjects.PersistantReference;
import de.cebitec.readXplorer.util.Properties;
import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Rolf Hilker <rhilker at cebitec.uni-bielefeld.de>
 */
public class SamBamFileReaderTest {
    
    public SamBamFileReaderTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getMappingsFromBam method, of class SamBamFileReader.
     */
    @Test
    public void testGetMappingsFromBam() {
//        System.out.println("getMappingsFromBam");
//        PersistantReference refGenome = new PersistantReference(1, "PAO1", "PAO1", null, new File(""));
//        PersistantChromosome chrom = new PersistantChromosome(0, 1, 1, "gi|292657124|ref|NC_013968.1|", 7000000);
//        int from = 2000;
//        int to = 3000;
//        boolean diffsAndGapsNeeded = true;
//        byte trackNeeded = 0;
//        ParametersReadClasses parametersReadClasses = new ParametersReadClasses(true, true, false, false, new Byte("30"));
//        IntervalRequest request = new IntervalRequest(from, to, from, to, 1, null, diffsAndGapsNeeded, Properties.NORMAL, trackNeeded, parametersReadClasses);
//        SamBamFileReader samBamFileReader = new SamBamFileReader(new File(), 1, refGenome);

    }

    /**
     * Test of getCoverageFromBam method, of class SamBamFileReader.
     */
    @Test
    public void testGetCoverageFromBam() {
        System.out.println("getCoverageFromBam");
        PersistantReference refGenome = new PersistantReference(1, "PAO1", "PAO1", null, new File(""));
        int from = 10000;
        int to = 21100;
        boolean diffsAndGapsNeeded = false;
        byte trackNeeded = 0;
        IntervalRequest request = new IntervalRequest(from, to, from, to, 1, null, diffsAndGapsNeeded, Properties.NORMAL, trackNeeded, new ParametersReadClasses());
        SamBamFileReader samBamFileReader = new SamBamFileReader(new File("D:\\Programmieren & Studieren\\Pseudomonas aeruginosa Projekt\\SequenceData\\NG-5516_2_2_read_1-F469-with-PAO1.jok_sort.bam"), 1, refGenome);
        PersistantCoverage coverage = samBamFileReader.getCoverageFromBam(request).getCoverage();
        assertTrue(coverage.getPerfectFwdMult(10000) == 129);
        assertTrue(coverage.getPerfectFwdMult(15000) == 178);
        assertTrue(coverage.getPerfectFwdMult(15001) == 179);
        assertTrue(coverage.getPerfectFwdMult(15002) == 178);
        assertTrue(coverage.getPerfectFwdMult(15003) == 178);
        assertTrue(coverage.getPerfectFwdMult(21100) == 208);
        
        assertTrue(coverage.getPerfectRevMult(10000) == 152);
        assertTrue(coverage.getPerfectRevMult(15000) == 65);
        assertTrue(coverage.getPerfectRevMult(15001) == 65);
        assertTrue(coverage.getPerfectRevMult(15002) == 63);
        assertTrue(coverage.getPerfectRevMult(15003) == 59);
        assertTrue(coverage.getPerfectRevMult(21100) == 168);
    }
}
