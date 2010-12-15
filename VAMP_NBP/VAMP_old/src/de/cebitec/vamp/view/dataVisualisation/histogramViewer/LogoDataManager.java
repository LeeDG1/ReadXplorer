package de.cebitec.vamp.view.dataVisualisation.histogramViewer;

import de.cebitec.vamp.databackend.dataObjects.PersistantDiff;
import de.cebitec.vamp.databackend.dataObjects.PersistantReferenceGap;
import de.cebitec.vamp.view.dataVisualisation.GenomeGapManager;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ddoppmeier
 */
public class LogoDataManager {

    private static int NUM_OF_BASE_TYPES = 7;
    private static int HEIGHT = 14;
    private static int MATCH = 0;
    private static int A = 1;
    private static int C = 2;
    private static int G = 3;
    private static int T = 4;
    private static int N = 5;
    private static int READGAP = 6;

    private Integer[][] counts;
    private int absStart;
    private int stop;
    private int width;
    private int upperCutoff;
    private int maxFoundCoverage;

    public LogoDataManager(int absStart, int width){
        this.absStart = absStart;
        this.stop = absStart + width - 1;
        this.width = width;

        if(this.width < 1){
            this.width = 1;
            this.stop = absStart;
        }
        this.upperCutoff = this.stop;
        this.maxFoundCoverage = 0;

        counts = new Integer[this.width][HEIGHT];
        for(int i = 0; i < width; i++){
            for(int j = 0; j<HEIGHT; j++){
                counts[i][j] = 0;
            }
        }

    }

    public int getNumOfMatchesAt(int position, boolean forwardStrand){
        int row = MATCH;
        if(!forwardStrand){
            row += NUM_OF_BASE_TYPES;
        }
        position -= absStart;
        return counts[position][row];
    }

    public int getNumOfAAt(int position, boolean forwardStrand){
        int row = A;
        if(!forwardStrand){
            row += NUM_OF_BASE_TYPES;
        }
        position -= absStart;
        return counts[position][row];
    }

    public int getNumOfCAt(int position, boolean forwardStrand){
        int row = C;
        if(!forwardStrand){
            row += NUM_OF_BASE_TYPES;
        }
        position -= absStart;
        return counts[position][row];
    }

    public int getNumOfGAt(int position, boolean forwardStrand){
        int row = G;
        if(!forwardStrand){
            row += NUM_OF_BASE_TYPES;
        }
        position -= absStart;
        return counts[position][row];
    }

    public int getNumOfTAt(int position, boolean forwardStrand){
        int row = T;
        if(!forwardStrand){
            row += NUM_OF_BASE_TYPES;
        }
        position -= absStart;
        return counts[position][row];
    }
    
    public int getNumOfNAt(int position, boolean forwardStrand){
        int row = N;
        if(!forwardStrand){
            row += NUM_OF_BASE_TYPES;
        }
        position -= absStart;
        return counts[position][row];
    }

    public int getNumOfReadGapsAt(int position, boolean forwardStrand) {
        int row = READGAP;
        if(!forwardStrand){
            row += NUM_OF_BASE_TYPES;
        }
        position -= absStart;
        return counts[position][row];
    }

    //sets the coverage value for matching base to the refernce genome
    public void setCoverageAt(int position, int value, boolean forwardStrand){
        int row = MATCH;
        if(!forwardStrand){
            row += NUM_OF_BASE_TYPES;
        }
        position -= absStart;
        maxFoundCoverage = (value > maxFoundCoverage ? value : maxFoundCoverage);
        counts[position][row] = value;
    }

    //if a base differs from the reference genome
    /**
     * 
     * @param diff
     * @param position
     */
    public void addExtendedPersistantDiff(PersistantDiff diff, int position){
        char base = diff.getBase();
        int row = 0;
        if(base == 'A'){
            row = A;
        } else if(base == 'C'){
            row = C;
        } else if(base == 'G'){
            row = G;
        } else if(base == 'T'){
            row = T;
        } else if(base == 'N'){
            row = N;
        } else if(base == '_'){
            row = READGAP;
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "found unknown base {0}", base);
        }

        int column = MATCH;
        if(!diff.isIsForwardStrand()){
            row += NUM_OF_BASE_TYPES;
            column += NUM_OF_BASE_TYPES;
        }

        int count = diff.getCount();
        position -= absStart;

        // increase counts for current base
        counts[position][row] = counts[position][row] + count;

        // decrease match coverage
        counts[position][column] = counts[position][column] - count;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("width: ").append(width).append(", height: ").append(HEIGHT).append("\n");

        for(int i = 0; i < width; i++){
            int pos = absStart + i;
            sb.append("pos: ").append(pos).append("\t");
            for(int j = 0; j < HEIGHT; j++){
                sb.append(counts[i][j]).append("\t");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    void addGaps(Collection<PersistantReferenceGap> gaps, GenomeGapManager gapManager) {
        for(Iterator<PersistantReferenceGap> it = gaps.iterator(); it.hasNext(); ){
            PersistantReferenceGap gap = it.next();
            char base = gap.getBase();
            int origPos = gap.getPosition();
            int count = gap.getCount();
            int shiftedPos = origPos + gapManager.getNumOfGapsSmaller(origPos);
            shiftedPos += gap.getOrder();

            // gaps have been loaded with original bounds
            // so, some of them may be outside of the intervall, this LogoDataManager
            // manages. Skip those gaps
            if(shiftedPos > upperCutoff){
                continue;
            }
            shiftedPos -= absStart;

            int row = 0;
            if(base == 'A'){
                row = A;
            } else if(base == 'C'){
                row = C;
            } else if(base == 'G'){
                row = G;
            } else if(base == 'T'){
                row = T;
            } else if(base == 'N'){
                row = N;
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "found unknown base {0}", base);
            }

            if(!gap.isForwardStrand()){
                row += NUM_OF_BASE_TYPES;
            }

            counts[shiftedPos][row] = counts[shiftedPos][row] + count;
        }
    }

    public int getMaxFoundCoverage() {
        return maxFoundCoverage;
    }

}