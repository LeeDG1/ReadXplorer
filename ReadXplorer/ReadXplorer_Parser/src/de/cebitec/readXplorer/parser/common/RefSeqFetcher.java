package de.cebitec.readXplorer.parser.common;

import de.cebitec.readXplorer.util.Observable;
import de.cebitec.readXplorer.util.Observer;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import net.sf.picard.PicardException;
import net.sf.picard.reference.IndexedFastaSequenceFile;

/**
 * A fetcher for any part of a referenc sequence stored in an indexed fasta 
 * file.
 *
 * @author Rolf Hilker <rhilker at mikrobio.med.uni-giessen.de>
 */
public class RefSeqFetcher implements Observable {
    
    private List<Observer> observers;
    private IndexedFastaSequenceFile refFile = null;

    /**
     * A fetcher for any part of a referenc sequence stored in an indexed fasta
     * file.
     * @param indexedFastaFile The indexed fasta file from which the data shall 
     * be read.
     * @param observer The observer for receiving error messages
     */
    public RefSeqFetcher(File indexedFastaFile, Observer observer) {
        this.observers = new ArrayList<>();
        this.observers.add(observer);
        try {
            refFile = new IndexedFastaSequenceFile(indexedFastaFile);
        } catch (FileNotFoundException ex) {
            this.notifyObservers("Fasta reference index file not found. Please make sure it exist.");
            this.notifyObservers(ex.getMessage());
        } catch (PicardException e) {
            String msg = "The following reference fasta file is missing! Please restore it in order to use this DB:\n" + indexedFastaFile.getAbsolutePath();
            JOptionPane.showMessageDialog(new JPanel(), msg, "Fasta missing error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Fetches the subsequence defined by start, stop and a reference name from
     * the reference file stored in this object.
     * @param refName name of the reference from which the sequence shall be 
     * retrieved
     * @param start start position of the interval of interest
     * @param stop stop position of the interval of interest
     * @return The subsequence defined by start, stop and a reference name from
     * the reference file stored in this object.
     */
    public String getSubSequence(String refName, int start, int stop) {
        return new String(refFile.getSubsequenceAt(refName, start, stop).getBases(), Charset.forName("UTF-8")).toUpperCase();
    }

    @Override
    public void registerObserver(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers(Object data) {
        for (Observer observer : this.observers) {
            observer.update(data);
        }
    }
    
}