/*
 * Copyright (C) 2015 Institute for Bioinformatics and Systems Biology, University Giessen, Germany
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package bio.comp.jlu.readxplorer.cli.imports;


import bio.comp.jlu.readxplorer.cli.imports.ImportReferenceCallable.ImportReferenceResult;
import de.cebitec.readxplorer.parser.ReferenceJob;
import de.cebitec.readxplorer.parser.common.ParsedReference;
import de.cebitec.readxplorer.parser.common.ParsingException;
import de.cebitec.readxplorer.parser.reference.BioJavaGff2Parser;
import de.cebitec.readxplorer.parser.reference.BioJavaGff3Parser;
import de.cebitec.readxplorer.parser.reference.BioJavaParser;
import de.cebitec.readxplorer.parser.reference.FastaReferenceParser;
import de.cebitec.readxplorer.parser.reference.ReferenceParserI;
import de.cebitec.readxplorer.parser.reference.filter.FeatureFilter;
import de.cebitec.readxplorer.parser.reference.filter.FilterRuleSource;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.api.sendopts.CommandException;


/**
 * The <code>ImportReferenceCallable</code> class is responsible for the import
 * of a reference genome in the cli version.
 * <p>
 * The following options are available:
 * <p>
 * Mandatory:
 * <li>
 * <lu>-r / --ref-import</lu>
 * <lu>-d / --db: file to H2 database</lu>
 * <lu>-t / --file-type: sequence file type</lu>
 * <lu>-f / --files: reference genome files</lu>
 * <lu>-n / --names: reference genome names</lu>
 * <lu>-d / --descriptions: reference genome descriptions</lu>
 * </li>
 *
 * Optional: -v / --verbose: print information during import process
 * <p>
 * @author Oliver Schwengers <oschweng@cebitec.uni-bielefeld.de>
 */
public final class ImportReferenceCallable implements Callable<ImportReferenceResult> {

    private static final Logger LOG = Logger.getLogger( ImportReferenceCallable.class.getName() );

    private final File referenceFile;


    public ImportReferenceCallable( File referenceFile ) {

        this.referenceFile = referenceFile;

    }


    @Override
    public ImportReferenceResult call() throws CommandException {

        try {

            // create necessary (mockup) objects
            final ReferenceParserI refParser = selectParser( referenceFile.getName().substring( referenceFile.getName().lastIndexOf( '.' ) + 1 ) );
            final ReferenceJob referenceJob = new ReferenceJob( 0, referenceFile, refParser,
                                                                "", referenceFile.getName(), new Timestamp( System.currentTimeMillis() ) );
            final ImportReferenceResult result = new ImportReferenceResult();
            result.setReferenceJob( referenceJob );

            // parse reference genome
            LOG.log( Level.FINE, "parsing reference file: {0}...", referenceFile.getName() );
            FeatureFilter filter = new FeatureFilter();
            filter.addBlacklistRule( new FilterRuleSource() );
            ParsedReference parsedRefGenome = refParser.parseReference( referenceJob, filter );
            result.setParsedReference( parsedRefGenome );
            LOG.log( Level.FINE, "parsed reference file: {0}", referenceFile.getName() );
            result.addOutput( "parsed reference file " + referenceFile.getName() );

            return result;

        } catch( ParsingException ex ) {
            LOG.log( Level.SEVERE, null, ex );
            CommandException ce = new CommandException( 1, "import failed!" );
            ce.initCause( ex );
            throw ce;
        } catch( OutOfMemoryError ex ) {
            LOG.log( Level.SEVERE, null, ex );
            CommandException ce = new CommandException( 1, "out of memory!" );
            ce.initCause( ex );
            throw ce;
        }

    }


    private static ReferenceParserI selectParser( String fileTypeArg ) {

        switch( fileTypeArg.toLowerCase() ) {
            case "ebl":
            case "embl":
                return new BioJavaParser( BioJavaParser.EMBL );
            case "gb":
            case "gbk":
            case "genbank":
                return new BioJavaParser( BioJavaParser.GENBANK );
            case "gff3":
                return new BioJavaGff3Parser();
            case "gff2":
            case "gtf":
                return new BioJavaGff2Parser();
            case "fasta":
            default:
                return new FastaReferenceParser();
        }

    }


    public class ImportReferenceResult {

        private final List<String> output;
        private ReferenceJob rj;
        private ParsedReference pr;


        ImportReferenceResult() {
            this.output = new ArrayList<>( 10 );
        }


        void addOutput( String msg ) {
            output.add( msg );
        }


        public List<String> getOutput() {
            return Collections.unmodifiableList( output );
        }


        void setReferenceJob( ReferenceJob rj ) {
            this.rj = rj;
        }


        public ReferenceJob getReferenceJob() {
            return rj;
        }


        void setParsedReference( ParsedReference pr ) {
            this.pr = pr;
        }


        public ParsedReference getParsedReference() {
            return pr;
        }


    }

}
