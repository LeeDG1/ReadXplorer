/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cebitec.readxplorer.transcriptomeanalyses.featureTableExport;


/**
 * This Enum class represents a list of available qualifiers for feature keys
 * and their usage.
 *
 * @author jritter
 */
public enum Qualifier {

    NONE,
    //Qualifier       name of qualifier; qualifier requires a value if followed by an equal
    //                sign
    //Definition      definition of the qualifier
    //Value format    format of value, if required
    //Example         example of qualifier with value
    //Comment         comments, questions and clarifications
    /**
     * Qualifier /allele= Definition name of the allele for the given gene Value
     * format "text" Example /allele="adh1-1" Comment all gene-related features
     * (exon, CDS etc) for a given gene should share the same /allele qualifier
     * value; the /allele qualifier value must, by definition, be different from
     * the /gene qualifier value; when used with the variation feature key, the
     * allele qualifier value should be that of the variant.
     */
    ALLELE,
    //Qualifier       /altitude=
    //Definition      geographical altitude of the location from which the sample
    //                was collected
    //Value format    "text"
    //Example         /altitude="-256 m."
    //                /altitude="330.12 m."
    //Comment         Values indicate altitudes above or below nominal sea level
    //                provided in metres

    ALTITUDE,
    //Qualifier       /anticodon=
    //Definition      location of the anticodon of tRNA and the amino acid for which
    //                it codes
    //Value format    (pos:<location>,aa:<amino_acid>,seq:<text>) where location
    //                is the position of the anticodon and amino_acid is the abbreviation for the
    //                amino acid encoded and seq is the sequence of the anticodon
    //Example         /anticodon=(pos:34..36,aa:Phe,seq:aaa)
    //                /anticodon=(pos:join(5,495..496),aa:Leu,seq:taa)
    //                /anticodon=(pos:complement(4156..4158),aa:Gln,seq:ttg)

    ANTICODON,
    //Qualifier       /artificial_location
    //Definition      indicates that location of the CDS or mRNA is modified to adjust
    //                for the presence of a frameshift or internal stop codon and not
    //                because of biological processing between the regions.
    //Value format    "heterogeneous population sequenced", "low-quality sequence region"
    //Example         /artificial_location="heterogeneous population sequenced"
    //                /artificial_location="low-quality sequence region"
    //Comment         expected to be used only for genome-scale annotation.

    ARTIFICIAL_LOCATION,
    //Qualifier       /bio_material=
    //Definition      identifier for the biological material from which the nucleic
    //                acid sequenced was obtained, with optional institution code and
    //                collection code for the place where it is currently stored.
    //Value format    "[<institution-code>:[<collection-code>:]]<material_id>"
    //Example         /bio_material="CGC:CB3912"      <- Caenorhabditis stock centre
    //Comment         the bio_material qualifier should be used to annotate the
    //                identifiers of material in biological collections that are not
    //                appropriate to annotate as either /specimen_voucher or
    //                /culture_collection; these include zoos and aquaria, stock
    //                centres, seed banks, germplasm repositories and DNA banks;
    //                material_id is mandatory, institution_code and collection_code
    //                are optional; institution code is mandatory where collection
    //                code is present; institution code and collection code are taken
    //                from a controlled vocabulary maintained by the INSDC.

    BIO_MATERIAL,
    //Qualifier       /bound_moiety=
    //Definition      name of the molecule/complex that may bind to the
    //                given feature
    //Value format    "text"
    //Example         /bound_moiety="GAL4"
    //Comment         Multiple /bound_moiety qualifiers are legal on "promoter"
    //                and "enhancer" features. A single /bound_moiety qualifier
    //                is legal on the "misc_binding", "oriT" and "protein_bind"
    //                features.

    BOUND_MOIETY,
    //Qualifier       /cell_line=
    //Definition      cell line from which the sequence was obtained
    //Value format    "text"
    //Example         /cell_line="MCF7"

    CELL_LINE,
    //Qualifier       /cell_type=
    //Definition      cell type from which the sequence was obtained
    //Value format    "text"
    //Example         /cell_type="leukocyte"

    CELL_TYPE,
    //Qualifier       /chromosome=
    //Definition      chromosome (e.g. Chromosome number) from which
    //                the sequence was obtained
    //Value format    "text"
    //Example         /chromosome="1"

    CHROMOSOME,
    //Qualifier       /citation=
    //Definition      reference to a citation listed in the entry reference field
    //Value format    [integer-number] where integer-number is the number of the
    //                reference as enumerated in the reference field
    //Example         /citation=[3]
    //Comment         used to indicate the citation providing the claim of and/or
    //                evidence for a feature; brackets are used for conformity.

    CITATION,
    //Qualifier       /clone=
    //Definition      clone from which the sequence was obtained
    //Value format    "text"
    //Example         /clone="lambda-hIL7.3"
    //Comment         not more than one clone should be specified for a given source
    //                feature;  to indicate that the sequence was obtained from
    //                multiple clones, multiple source features should be given.

    CLONE,
    //Qualifier       /clone_lib=
    //Definition      clone library from which the sequence was obtained
    //Value format    "text"
    //Example         /clone_lib="lambda-hIL7"

    CLONE_LIB,
    //Qualifier       /codon_start=
    //Definition      indicates the offset at which the first complete codon of a
    //                coding feature can be found, relative to the first base of that
    //                feature.
    //Value format    1 or 2 or 3
    //Example         /codon_start=2

    CODON_START,
    //Qualifier       /collected_by=
    //Definition      name of persons or institute who collected the specimen
    //Value format    "text"
    //Example         /collected_by="Dan Janzen"

    COLLECTED_BY,
    //Qualifier       /collection_date=
    //
    //Definition      The date on which the specimen was collected.
    //                Date/time ranges are supported by providing two collection dates from among the supported value
    //                formats, delimited by a forward-slash character.
    //                Collection times are supported by adding "T", then the hour and minute, after the date.
    //                Collection times must be in Coordinated Universal Time (UTC), otherwise known as "Zulu Time" (Z).
    //
    //Value format    "DD-Mmm-YYYY", "Mmm-YYYY", "YYYY"
    //                "YYYY-MM-DDThh:mmZ", "YYYY-MM-DDThhZ", "YYYY-MM-DD", or "YYYY-MM" will be supported from 15 December 2013.
    //
    //Example         /collection_date="21-Oct-1952"
    //                /collection_date="Oct-1952"
    //                /collection_date="1952"
    //                /collection_date="1952-10-21T11:43Z"
    //                /collection_date="1952-10-21T11Z"
    //                /collection_date="1952-10-21"
    //                /collection_date="1952-10"
    //                /collection_date="21-Oct-1952/15-Feb-1953"
    //                /collection_date="Oct-1952/Feb-1953"
    //                /collection_date="1952/1953"
    //                /collection_date="1952-10-21/1953-02-15"
    //                /collection_date="1952-10/1953-02"
    //                /collection_date="1952-10-21T11:43Z/1952-10-21T17:43Z"
    //
    //Comment         'Mmm' represents a three-letter month abbreviation, and can be one of the following:
    //                Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec
    //
    //                'YYYY' is a four-digit value representing the year. 'MM' is a two-digit value representing
    //                the month. 'DD' is a two-digit value representing the day of the month.
    //
    //                'hh' is a two-digit value representing the hour of the day (00 to 23)
    //                'mm' is a two-digit value representing the minute of the hour (01 to 59)
    //
    //                Within a date range, value formats that make use of 'Mmm' (month abbreviations) cannot be
    //                combined with value formats that make use of 'MM' (two-digit month number)
    //
    //                Collection dates that are specified to at least the month, day, and year (DD-Mmm-YYYY or YYYY-MM-DD)
    //                are strongly encouraged. If the day and/or month of the collection date are not known,
    //                Mmm-YYYY or YYYY-MM or YYYY may be used.
    //
    //                Within a collection date range, the first date (possibly including time) must be
    //                prior to the second date (possibly including time).
    //
    //                Within a collection date range for which the day, month, and year are identical, the first time value
    //                must be prior to the second time value.
    //
    //                Collection dates in ISO format will be supported with effect of December 15, 2013.

    COLLECTION_DATE,
    //Qualifier       /compare=
    //Definition      Reference details of an existing public INSD entry
    //                to which a comparison is made
    //Value format    [accession-number.sequence-version]
    //Example         /compare=AJ634337.1
    //Comment         This qualifier may be used on the following features:
    //                misc_difference, unsure, old_sequence and variation.
    //                The feature "old_sequence" must have either a
    //                /citation or a /compare qualifier. Multiple /compare
    //                qualifiers with different contents are allowed within a
    //                single feature.
    //                This qualifier is not intended for large-scale annotation
    //                of variations, such as SNPs.

    COMPARE,
    //Qualifier       /country=
    //Definition      locality of isolation of the sequenced organism indicated in
    //                terms of political names for nations, oceans or seas, followed
    //                by regions and localities
    //Value format    "<country_value>[:<region>][, <locality>]" where
    //                country_value is any value from the controlled vocabulary at
    //                http://www.insdc.org/documents/country-qualifier-vocabulary
    //Example         /country="Canada:Vancouver"
    //                /country="France:Cote d'Azur, Antibes"
    //                /country="Atlantic Ocean:Charlie Gibbs Fracture Zone"
    //Comment         Intended to provide a reference to the site where the source
    //                organism was isolated or sampled. Regions and localities should
    //                be indicated where possible. Note that the physical geography of
    //                the isolation or sampling site should be represented in
    //                /isolation_source.

    COUNTRY,
    //Qualifier       /cultivar=
    //Definition      cultivar (cultivated variety) of plant from which sequence was
    //                obtained.
    //Value format    "text"
    //Example         /cultivar="Nipponbare"
    //                /cultivar="Tenuifolius"
    //                /cultivar="Candy Cane"
    //                /cultivar="IR36"
    //Comment         'cultivar' is applied solely to products of artificial
    //                selection;  use the variety qualifier for natural, named
    //                plant and fungal varieties;

    CULTIVAR,
    //Qualifier       /culture_collection=
    //Definition      institution code and identifier for the culture from which the
    //                nucleic acid sequenced was obtained, with optional collection
    //                code.
    //Value format    "<institution-code>:[<collection-code>:]<culture_id>"
    //Example         /culture_collection="ATCC:26370"
    //Comment         the /culture_collection qualifier should be used to annotate
    //                live microbial and viral cultures, and cell lines that have been
    //                deposited in curated culture collections; microbial cultures in
    //                personal or laboratory collections should be annotated in strain
    //                qualifiers;
    //                annotation with a culture_collection qualifier implies that the
    //                sequence was obtained from a sample retrieved (by the submitter
    //                or a collaborator) from the indicated culture collection, or
    //                that the sequence was obtained from a sample that was deposited
    //                (by the submitter or a collaborator) in the indicated culture
    //                collection; annotation with more than one culture_collection
    //                qualifier indicates that the sequence was obtained from a sample
    //                that was deposited (by the submitter or a collaborator) in more
    //                than one culture collection.
    //                culture_id and institution_code are mandatory, collection_code
    //                is optional; institution code and collection code are taken
    //                from a controlled vocabulary maintained by the INSDC.
    //                http://www.insdc.org/controlled-vocabulary-culturecollection-qualifier

    CULTURE_COLLECTION,
    //Qualifier       /db_xref=
    //Definition      database cross-reference: pointer to related information in
    //                another database.
    //Value format    "<database:identifier>" where database is
    //                the name of the database containing related information, and
    //                identifier is the internal identifier of the related information
    //                according to the naming conventions of the cross-referenced
    //                database.
    //Example         /db_xref="UniProtKB/Swiss-Prot:P28763"
    //Comment         the complete list of allowed database types is kept at
    //                http://www.insdc.org/db_xref.html

    DB_XREF,
    //Qualifier       /dev_stage=
    //Definition      if the sequence was obtained from an organism in a specific
    //                developmental stage, it is specified with this qualifier
    //Value format    "text"
    //Example         /dev_stage="fourth instar larva"

    DEV_STAGE,
    //Qualifier       /direction=
    //Definition      direction of DNA replication
    //Value format    left, right, or both where left indicates toward the 5' end of
    //                the entry sequence (as presented) and right indicates toward
    //                the 3' end
    //Example         /direction=LEFT

    DIRECTION,
    //Qualifier       /EC_number=
    //Definition      Enzyme Commission number for enzyme product of sequence
    //Value format    "text"
    //Example         /EC_number="1.1.2.4"
    //                /EC_number="1.1.2.-"
    //                /EC_number="1.1.2.n"
    //Comment         valid values for EC numbers are defined in the list prepared by the
    //                Nomenclature Committee of the International Union of Biochemistry and
    //                Molecular Biology (NC-IUBMB) (published in Enzyme Nomenclature 1992,
    //                Academic Press, San Diego, or a more recent revision thereof).
    //                The format represents a string of four numbers separated by full
    //                stops; up to three numbers starting from the end of the string can
    //                be replaced by dash "." to indicate uncertain assignment.
    //                Symbol "n" can be used in the last position instead of a number
    //                where the EC number is awaiting assignment. Please note that such
    //                incomplete EC numbers are not approved by NC-IUBMB.

    EC_NUMBER,
    //Qualifier       /ecotype=
    //Definition      a population within a given species displaying genetically
    //                based, phenotypic traits that reflect adaptation to a local habitat.
    //Value Format    "text"
    //Example         /ecotype="Columbia"
    //Comment         an example of such a population is one that has adapted hairier
    //                than normal leaves as a response to an especially sunny habitat.
    //                'Ecotype' is often applied to standard genetic stocks of
    //                Arabidopsis thaliana, but it can be applied to any sessile
    //                organism.

    ECOTYPE,
    //Qualifier       /environmental_sample
    //Definition      identifies sequences derived by direct molecular
    //                isolation from a bulk environmental DNA sample
    //                (by PCR with or without subsequent cloning of the
    //                product, DGGE, or other anonymous methods) with no
    //                reliable identification of the source organism.
    //                Environmental samples include clinical samples,
    //                gut contents, and other sequences from anonymous
    //                organisms that may be associated with a particular
    //                host. They do not include endosymbionts that can be
    //                reliably recovered from a particular host, organisms
    //                from a readily identifiable but uncultured field sample
    //                (e.g., many cyanobacteria), or phytoplasmas that can be
    //                reliably recovered from diseased plants (even though
    //                these cannot be grown in axenic culture).
    //Value format    none
    //Example         /environmental_sample
    //Comment         used only with the source feature key; source feature
    //                keys containing the /environmental_sample qualifier
    //                should also contain the /isolation_source qualifier.
    //                entries including /environmental_sample must not include
    //                the /strain qualifier

    ENVIRONMENTAL_SAMPLE,
    //Qualifier       /estimated_length=
    //Definition      estimated length of the gap in the sequence
    //Value format    unknown or <integer>
    //Example         /estimated_length=unknown
    //                /estimated_length=342

    ESTIMATED_LENGTH,
    //Qualifier       /exception=
    //Definition      indicates that the coding region cannot be translated using
    //                standard biological rules
    //Value format    "RNA editing", "reasons given in citation",
    //                "rearrangement required for product", "annotated by transcript
    //                or proteomic data"
    //Example         /exception="RNA editing"
    //                /exception="reasons given in citation"
    //                /exception="rearrangement required for product"
    //                /exception="annotated by transcript or proteomic data"
    //Comment         only to be used to describe biological mechanisms such
    //                as RNA editing;  where the exception cannot easily be described
    //                a published citation must be referred to; protein translation of
    //                /exception CDS will be different from the according conceptual
    //                translation;
    //                - An /inference qualifier should accompany any use of
    //                /exception="annotated by transcript or proteomic data", to
    //                provide support for the existence of the transcript/protein.
    //                - must not be used where transl_except would be adequate,
    //                  e.g. in case of stop codon completion use:
    //                /transl_except=(pos:6883,aa:TERM)
    //                /note="TAA stop codon is completed by addition of 3' A residues to
    //                mRNA".
    //                - must not be used for ribosomal slippage, instead use join operator,
    //                  e.g.: CDS   join(486..1784,1787..4810)
    //                              /note="ribosomal slip on tttt sequence at 1784..1787"

    EXCEPTION,
    //Qualifier       /experiment=
    //Definition      a brief description of the nature of the experimental
    //                evidence that supports the feature identification or assignment.
    //Value format    "[CATEGORY:]text"
    //                where CATEGORY is one of the following:
    //                "COORDINATES" support for the annotated coordinates
    //                "DESCRIPTION" support for a broad concept of function such as that
    //                based on phenotype, genetic approach, biochemical function, pathway
    //                information, etc.
    //                "EXISTENCE" support for the known or inferred existence of the product
    //                where text is free text (see examples)
    //Example         /experiment="5' RACE"
    //                /experiment="Northern blot [DOI: 12.3456/FT.789.1.234-567.2010]"
    //                /experiment="heterologous expression system of Xenopus laevis
    //                oocytes [PMID: 12345678, 10101010, 987654]"
    //                /experiment="COORDINATES: 5' and 3' RACE"
    //Comment         detailed experimental details should not be included, and would
    //                normally be found in the cited publications; PMID, DOI and any
    //                experimental database ID is allowed to be used in /experiment
    //                qualifier; value "experimental evidence, no additional details
    //                recorded" was used to  replace instances of /evidence=EXPERIMENTAL in
    //                December 2005

    EXPERIMETN,
    //Qualifier       /focus
    //Definition      identifies the source feature of primary biological
    //                interest for records that have multiple source features
    //                originating from different organisms and that are not
    //                transgenic.
    //Value format    none
    //Example         /focus
    //Comment         the source feature carrying the /focus qualifier
    //                identifies the main organism of the entry, this
    //                determines: a) the name displayed in the organism
    //                lines, b) if no translation table is specified, the
    //                translation table, c) the DDBJ/EMBL/GenBank taxonomic
    //                division in which the entry will appear; only one
    //                source feature with /focus is allowed in an entry; the
    //                /focus and /transgenic qualifiers are mutually exclusive
    //                in an entry.

    FOCUS,
    //Qualifier       /frequency=
    //Definition      frequency of the occurrence of a feature
    //Value format    text representing the proportion of a population carrying the
    //                feature expressed as a fraction
    //Example         /frequency="23/108"
    //                /frequency="1 in 12"
    //                /frequency=".85"

    FREQUENCY,
    //Qualifier       /function=
    //Definition      function attributed to a sequence
    //Value format    "text"
    //Example         function="essential for recognition of cofactor"
    //Comment         /function is used when the gene name and/or product name do not
    //                convey the function attributable to a sequence.

    FUNCTION,
    //Qualifier       /gap_type=
    //Definition      type of gap connecting components in records of a genome assembly,
    //                or the type of biological gap in a record that is part of a genome
    //                assembly;
    //Value format    "between scaffolds", "within scaffold", "telomere", "centromere",
    //                "short arm", "heterochromatin", "repeat within scaffold",
    //                "repeat between scaffolds", "unknown"
    //Example         /gap_type="between scaffolds"
    //                /gap_type="within scaffold"
    //Comment         This qualifier is used only for assembly_gap features and its values
    //                are controlled by the AGP Specification version 2.0:
    //                http://www.ncbi.nlm.nih.gov/projects/genome/assembly/agp/AGP_Specification.shtml
    //                Please also visit: http://www.insdc.org/controlled-vocabulary-gaptype-qualifier

    GAP_TYPE,
    //Qualifier       /gene=
    //Definition      symbol of the gene corresponding to a sequence region
    //Value format    "text"
    //Example         /gene="ilvE"

    GENE,
    //Qualifier       /gene_synonym=
    //Definition      synonymous, replaced, obsolete or former gene symbol
    //Value format    "text"
    //Example         /gene_synonym="Hox-3.3"
    //                in a feature where /gene="Hoxc6"
    //Comment         used where it is helpful to indicate a gene symbol
    //                synonym; when used, a primary gene symbol must always be
    //                indicated in /gene or a /locus_tag must be used.

    GENE_SYNONYM,
    //Qualifier       /germline
    //Definition      the sequence presented in the entry has not undergone somatic
    //                rearrangement as part of an adaptive immune response; it is the
    //                unrearranged sequence that was inherited from the parental
    //                germline
    //Value format    none
    //Example         /germline
    //Comment         /germline should not be used to indicate that the source of
    //                the sequence is a gamete or germ cell;
    //                /germline and /rearranged cannot be used in the same source
    //                feature;
    //                /germline and /rearranged should only be used for molecules that
    //                can undergo somatic rearrangements as part of an adaptive immune
    //                response; these are the T-cell receptor (TCR) and immunoglobulin
    //                loci in the jawed vertebrates, and the unrelated variable
    //                lymphocyte receptor (VLR) locus in the jawless fish (lampreys
    //                and hagfish);
    //                /germline and /rearranged should not be used outside of the
    //                Craniata (taxid=89593)

    GERMLINE,
    //Qualifier       /haplogroup=
    //Definition      name for a group of similar haplotypes that share some
    //                sequence variation. Haplogroups are often used to track
    //		migration of population groups
    //Value format    "text"
    //Example         /haplogroup="H*"

    HAPLOGROUP,
    //Qualifier       /haplotype=
    //Definition      name for a combination of alleles that are linked together
    //                on the same physical chromosome. In the absence of
    //                recombination, each haplotype is inherited as a unit, and may
    //                be used to track gene flow in populations.
    //Value format    "text"
    //Example         /haplotype="Dw3 B5 Cw1 A1"

    HAPLOTYPE,
    //Qualifier       /host=
    //Definition      natural (as opposed to laboratory) host to the organism from
    //                which sequenced molecule was obtained
    //Value format    "text"
    //Example         /host="Homo sapiens"
    //                /host="Homo sapiens 12 year old girl"
    //                /host="Rhizobium NGR234"

    HOST,
    //Qualifier       /identified_by=
    //Definition      name of the expert who identified the specimen taxonomically
    //Value format    "text"
    //Example         /identified_by="John Burns"

    IDENTIFIED_BY,
    //Qualifier       /inference=
    //Definition      a structured description of non-experimental evidence that supports
    //                the feature identification or assignment.
    //
    //Value format    "[CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]"
    //
    //                where CATEGORY is one of the following:
    //                "COORDINATES" support for the annotated coordinates
    //                "DESCRIPTION" support for a broad concept of function such as that
    //                based on phenotype, genetic approach, biochemical function, pathway
    //                information, etc.
    //                "EXISTENCE" support for the known or inferred existence of the product
    //
    //                where TYPE is one of the following:
    //                "non-experimental evidence, no additional details recorded"
    //                   "similar to sequence"
    //                      "similar to AA sequence"
    //                      "similar to DNA sequence"
    //                      "similar to RNA sequence"
    //                      "similar to RNA sequence, mRNA"
    //                      "similar to RNA sequence, EST"
    //                      "similar to RNA sequence, other RNA"
    //                   "profile"
    //                      "nucleotide motif"
    //                      "protein motif"
    //                      "ab initio prediction"
    //                   "alignment"
    //
    //                where the optional text "(same species)" is included when the
    //                inference comes from the same species as the entry.
    //
    //                where the optional "EVIDENCE_BASIS" is either a reference to a
    //                database entry (including accession and version) or an algorithm
    //                (including version) , eg 'INSD:AACN010222672.1', 'InterPro:IPR001900',
    //                'ProDom:PD000600', 'Genscan:2.0', etc. and is structured
    //                "[ALGORITHM][:EVIDENCE_DBREF[,EVIDENCE_DBREF]*[,...]]"
    //
    //Example         /inference="COORDINATES:profile:tRNAscan:2.1"
    //                /inference="similar to DNA sequence:INSD:AY411252.1"
    //                /inference="similar to RNA sequence, mRNA:RefSeq:NM_000041.2"
    //                /inference="similar to DNA sequence (same
    //                species):INSD:AACN010222672.1"
    //                /inference="protein motif:InterPro:IPR001900"
    //                /inference="ab initio prediction:Genscan:2.0"
    //                /inference="alignment:Splign:1.0"
    //                /inference="alignment:Splign:1.26p:RefSeq:NM_000041.2,INSD:BC003557.1"
    //
    //Comment         /inference="non-experimental evidence, no additional details
    //                recorded" was used to replace instances of
    //                /evidence=NOT_EXPERIMENTAL in December 2005; any database ID can be
    //                used in /inference= qualifier; recommentations for choice of resource
    //                acronym for[EVIDENCE_BASIS] are provided in the /inference qualifier
    //                vocabulary recommendation document (http://www.insdc.org/inference.html);

    INFERENCE,
    //Qualifier       /isolate=
    //Definition      individual isolate from which the sequence was obtained
    //Value format    "text"
    //Example         /isolate="Patient #152"
    //                /isolate="DGGE band PSBAC-13"

    ISOLATE,
    //Qualifier       /isolation_source=
    //Definition      describes the physical, environmental and/or local
    //                geographical source of the biological sample from which
    //                the sequence was derived
    //Value format    "text"
    //Examples        /isolation_source="rumen isolates from standard
    //                Pelleted ration-fed steer #67"
    //                /isolation_source="permanent Antarctic sea ice"
    //                /isolation_source="denitrifying activated sludge from
    //                carbon_limited continuous reactor"
    //Comment         used only with the source feature key;
    //                source feature keys containing an /environmental_sample
    //                qualifier should also contain an /isolation_source
    //                qualifier; the /country qualifier should be used to
    //                describe the country and major geographical sub-region.

    ISOLATE_SOURCE,
    //Qualifier       /lab_host=
    //Definition      scientific name of the laboratory host used to propagate the
    //                source organism from which the sequenced molecule was obtained
    //Value format    "text"
    //Example         /lab_host="Gallus gallus"
    //                /lab_host="Gallus gallus embryo"
    //                /lab_host="Escherichia coli strain DH5 alpha"
    //                /lab_host="Homo sapiens HeLa cells"
    //Comment         the full binomial scientific name of the host organism should
    //                be used when known; extra conditional information relating to
    //                the host may also be included

    LAB_HOST,
    //Qualifier       /lat_lon=
    //Definition      geographical coordinates of the location where the specimen was
    //                collected
    //Value format    "text"
    //Example         /lat_lon="47.94 N 28.12 W"
    //                /lat_lon="45.0123 S 4.1234 E"
    //Comment         degrees latitude and longitude in format "d[d.dddd] N|S d[dd.dddd] W|E"
    //                (see the examples)

    LAT_LON,
    //Qualifier       /linkage_evidence=
    //Definition      type of evidence establishing linkage across an
    //		assembly_gap. Only allowed to be used with assembly_gap features that
    //                have a /gap_type value of "within scaffold"or "repeat within scaffold";
    //Value format    "pcr", "paired-ends", "align genus", "align xgenus", "align trnscpt", "within clone",
    //                "clone contig", "map", "strobe", "unspecified"
    //Example         /linkage_evidence="paired-ends"
    //		/linkage_evidence="within clone"
    //Comment         This qualifier is used only for assembly_gap features and its values are
    //                controlled by the AGP Specification version 2.0:
    //                http://www.ncbi.nlm.nih.gov/projects/genome/assembly/agp/AGP_Specification.shtml
    //                Please also visit: http://www.insdc.org/controlled-vocabulary-linkageevidence-qualifier

    LINKAGE_EVIDENCE,
    //Qualifier       /locus_tag=
    //Definition      a submitter-supplied, systematic, stable identifier for a gene
    //                and its associated features, used for tracking purposes
    //Value Format    "text"(single token)
    //                but not "<1-5 letters><5-9 digit integer>[.<integer>]"
    //Example         /locus_tag="ABC_0022"
    //                /locus_tag="A1C_00001"
    //Comment         /locus_tag can be used with any feature that /gene can be used with;
    //                identical /locus_tag values may be used within an entry/record,
    //                but only if the identical /locus_tag values are associated
    //                with the same gene; in all other circumstances the /locus_tag
    //                value must be unique within that entry/record. Multiple /locus_tag
    //                values are not allowed within one feature for entries created
    //                after 15-OCT-2004.
    //                If a /locus_tag needs to be re-assigned the /old_locus_tag qualifier
    //                should be used to store the old value. The /locus_tag value should
    //                not be in a format which resembles INSD accession numbers,
    //                accession.version, or /protein_id identifiers.

    LOCUS_TAG,
    //Qualifier       /macronuclear
    //Definition      if the sequence shown is DNA and from an organism which
    //                undergoes chromosomal differentiation between macronuclear and
    //                micronuclear stages, this qualifier is used to denote that the
    //                sequence is from macronuclear DNA.
    //Value format    none
    //Example         /macronuclear

    MACRONUCLEAR,
    //Qualifier       /map=
    //Definition      genomic map position of feature
    //Value format    "text"
    //Example         /map="8q12-q13"

    MAP,
    //Qualifier       /mating_type=
    //Definition      mating type of the organism from which the sequence was
    //                obtained; mating type is used for prokaryotes, and for
    //                eukaryotes that undergo meiosis without sexually dimorphic
    //                gametes
    //Value format    "text"
    //Examples        /mating_type="MAT-1"
    //                /mating_type="plus"
    //                /mating_type="-"
    //                /mating_type="odd"
    //                /mating_type="even"
    //Comment         /mating_type="male" and /mating_type="female" are
    //                valid in the prokaryotes, but not in the eukaryotes;
    //                for more information, see the entry for /sex.

    MATING_TYPE,
    //Qualifier       /mobile_element_type=
    //Definition      type and name or identifier of the mobile element which is
    //                described by the parent feature
    //Value format    "<mobile_element_type>[:<mobile_element_name>]" where
    //                mobile_element_type is one of the following:
    //                "transposon", "retrotransposon", "integron",
    //                "insertion sequence", "non-LTR retrotransposon",
    //                "SINE", "MITE", "LINE", "other".
    //Example         /mobile_element_type="transposon:Tnp9"
    //Comment         /mobile_element_type is legal on mobile_element feature key only.
    //                Mobile element should be used to represent both elements which
    //                are currently mobile, and those which were mobile in the past.
    //                Value "other" requires a mobile_element_name.

    MOBILE_ELEMENT_TYPE,
    //Qualifier       /mod_base=
    //Definition      abbreviation for a modified nucleotide base
    //Value format    modified_base
    //Example         /mod_base=m5c
    //Comment         modified nucleotides not found in the restricted vocabulary
    //                list can be annotated by entering '/mod_base=OTHER' with
    //                '/note="name of modified base"'

    MOD_BASE,
    //Qualifier       /mol_type=
    //Definition      in vivo molecule type of sequence
    //Value format    "genomic DNA", "genomic RNA", "mRNA", "tRNA", "rRNA", "other
    //                RNA", "other DNA", "transcribed RNA", "viral cRNA", "unassigned
    //                DNA", "unassigned RNA"
    //Example         /mol_type="genomic DNA"
    //Comment         all values refer to the in vivo or synthetic molecule for
    //                primary entries and the hypothetical molecule in Third Party
    //                Annotation entries; the value "genomic DNA" does not imply that
    //                the molecule is nuclear (e.g. organelle and plasmid DNA should
    //                be described using "genomic DNA"); ribosomal RNA genes should be
    //                described using "genomic DNA"; "rRNA" should only be used if the
    //                ribosomal RNA molecule itself has been sequenced; /mol_type is
    //                mandatory on every source feature key; all /mol_type values
    //                within one entry/record must be the same; values "other RNA" and
    //                "other DNA" should be applied to synthetic molecules, values
    //                "unassigned DNA", "unassigned RNA" should be applied where in
    //                vivo molecule is unknown
    //                Please also visit:
    //                http://www.insdc.org/controlled-vocabulary-moltype-qualifier

    MOL_TYPE,
    //Qualifier       /ncRNA_class=
    //Definition      a structured description of the classification of the
    //                non-coding RNA described by the ncRNA parent key
    //Value format   "TYPE"
    //Example         /ncRNA_class="miRNA"
    //                /ncRNA_class="siRNA"
    //                /ncRNA_class="scRNA"
    //Comment         TYPE is a term taken from the INSDC controlled vocabulary for ncRNA
    //                classes (http://www.insdc.org/rna_vocab.html); on
    //                15-Oct-2013, the following terms were valid:
    //
    //                      "antisense_RNA"
    //                      "autocatalytically_spliced_intron"
    //                      "ribozyme"
    //                      "hammerhead_ribozyme"
    //                      "lncRNA"
    //                      "RNase_P_RNA"
    //                      "RNase_MRP_RNA"
    //                      "telomerase_RNA"
    //                      "guide_RNA"
    //                      "rasiRNA"
    //                      "scRNA"
    //                      "siRNA"
    //                      "miRNA"
    //                      "piRNA"
    //                      "snoRNA"
    //                      "snRNA"
    //                      "SRP_RNA"
    //                      "vault_RNA"
    //                      "Y_RNA"
    //                      "other"
    //
    //                ncRNA classes not yet in the INSDC /ncRNA_class controlled
    //                vocabulary can be annotated by entering
    //                '/ncRNA_class="other"' with '/note="[brief explanation of
    //                novel ncRNA_class]"';

    NC_RNA,
    //Qualifier       /note=
    //Definition      any comment or additional information
    //Value format    "text"
    //Example         /note="This qualifier is equivalent to a comment."

    NOTE,
    //Qualifier       /number=
    //Definition      a number to indicate the order of genetic elements (e.g.,
    //                exons or introns) in the 5' to 3' direction
    //Value format    unquoted text (single token)
    //Example         /number=4
    //                /number=6B
    //Comment         text limited to integers, letters or combination of integers and/or
    //                letters represented as an unquoted single token (e.g. 5a, XIIb);
    //                any additional terms should be included in /standard_name.
    //                Example:  /number=2A
    //                          /standard_name="long"

    NUMBER,
    //Qualifier       /old_locus_tag=
    //Definition      feature tag assigned for tracking purposes
    //Value Format    "text" (single token)
    //Example         /old_locus_tag="RSc0382"
    //                /locus_tag="YPO0002"
    //Comment         /old_locus_tag can be used with any feature where /gene is valid and
    //                where a /locus_tag qualifier is present.
    //                Identical /old_locus_tag values may be used within an entry/record,
    //                but only if the identical /old_locus_tag values are associated
    //                with the same gene; in all other circumstances the /old_locus_tag
    //                value must be unique within that entry/record.
    //                Multiple/old_locus_tag qualifiers with distinct values are
    //                allowed within a single feature; /old_locus_tag and /locus_tag
    //                values must not be identical within a single feature.
    OLD_LOCUS_TAG,
    //Qualifier       /operon=
    //Definition      name of the group of contiguous genes transcribed into a
    //                single transcript to which that feature belongs.
    //Value format    "text"
    //Example         /operon="lac"
    //Comment         currently valid only on Prokaryota-specific features

    OPERON,
    //Qualifier       /organelle=
    //Definition      type of membrane-bound intracellular structure from which the
    //                sequence was obtained
    //Value format    chromatophore, hydrogenosome, mitochondrion, nucleomorph, plastid,
    //                mitochondrion:kinetoplast, plastid:chloroplast, plastid:apicoplast,
    //                plastid:chromoplast, plastid:cyanelle, plastid:leucoplast, plastid:proplastid
    //Examples        /organelle="chromatophore"
    //                /organelle="hydrogenosome"
    //                /organelle="mitochondrion"
    //                /organelle="nucleomorph"
    //                /organelle="plastid"
    //                /organelle="mitochondrion:kinetoplast"
    //                /organelle="plastid:chloroplast"
    //                /organelle="plastid:apicoplast"
    //                /organelle="plastid:chromoplast"
    //                /organelle="plastid:cyanelle"
    //                /organelle="plastid:leucoplast"
    //                /organelle="plastid:proplastid"
    //Comments        modifier text limited to values from controlled list
    //                Please also visit: http://www.insdc.org/controlled-vocabulary-organelle-qualifier

    ORGANELLE,
    //Qualifier       /organism=
    //Definition      scientific name of the organism that provided the
    //                sequenced genetic material.
    //Value format    "text"
    //Example         /organism="Homo sapiens"
    //Comment         the organism name which appears on the OS or ORGANISM line
    //                will match the value of the /organism qualifier of the
    //                source key in the simplest case of a one-source sequence.

    ORGANISM,
    //Qualifier       /partial
    //Definition      differentiates between complete regions and partial ones
    //Value format    none
    //Example         /partial
    //Comment         not to be used for new entries from 15-DEC-2001;
    //                use '<' and '>' signs in the location descriptors to
    //                indicate that the sequence is partial.

    PARTIAL,
    //Qualifier       /PCR_conditions=
    //Definition      description of reaction conditions and components for PCR
    //Value format    "text"
    //Example         /PCR_conditions="Initial denaturation:94degC,1.5min"
    //Comment         used with primer_bind key

    PCR_CONDITIONS,
    //Qualifier       /PCR_primers=
    //Definition      PCR primers that were used to amplify the sequence.
    //                A single /PCR_primers qualifier should contain all the primers used
    //                for a single PCR reaction. If multiple forward or reverse primers are
    //                present in a  single PCR reaction, multiple sets of fwd_name/fwd_seq
    //                or rev_name/rev_seq values will be  present.
    //Value format    /PCR_primers="[fwd_name: XXX1, ]fwd_seq: xxxxx1,[fwd_name: XXX2,]
    //                fwd_seq: xxxxx2, [rev_name: YYY1, ]rev_seq: yyyyy1,
    //                [rev_name: YYY2, ]rev_seq: yyyyy2"
    //
    //Example         /PCR_primers="fwd_name: CO1P1, fwd_seq: ttgattttttggtcayccwgaagt,
    //                rev_name: CO1R4, rev_seq: ccwvytardcctarraartgttg"
    //                /PCR_primers=" fwd_name: hoge1, fwd_seq: cgkgtgtatcttact,
    //                rev_name: hoge2, rev_seq: cg<i>gtgtatcttact"
    //                /PCR_primers="fwd_name: CO1P1, fwd_seq: ttgattttttggtcayccwgaagt,
    //                fwd_name: CO1P2, fwd_seq: gatacacaggtcayccwgaagt, rev_name: CO1R4,
    //                rev_seq: ccwvytardcctarraartgttg"
    //
    //Comment         fwd_seq and rev_seq are both mandatory; fwd_name and rev_name are
    //                both optional. Both sequences should be presented in 5'>3' order.
    //                The sequences should be given in the IUPAC degenerate-base alphabet,
    //                except for the modified bases; those must be enclosed within angle
    //                brackets <>

    PCR_PRIMERS,
    //Qualifier       /phenotype=
    //Definition      phenotype conferred by the feature, where phenotype is defined as a
    //                physical, biochemical or behavioural characteristic or set of
    //                characteristics
    //Value format    "text"
    //Example         /phenotype="erythromycin resistance"

    PHENOTYPE,
    //Qualifier       /plasmid=
    //Definition      name of naturally occurring plasmid from which the sequence was
    //                obtained, where plasmid is defined as an independently replicating
    //                genetic unit that cannot be described by /chromosome or /segment
    //Value format    "text"
    //Example         /plasmid="C-589"

    PLASMID,
    //Qualifier       /pop_variant=
    //Definition      name of subpopulation or phenotype of the sample from which the sequence
    //                was derived
    //Value format    "text"
    //Example         /pop_variant="pop1"
    //                /pop_variant="Bear Paw"

    POP_VARIANT,
    //Qualifier       /product=
    //Definition      name of the product associated with the feature, e.g. the mRNA of an
    //                mRNA feature, the polypeptide of a CDS, the mature peptide of a
    //                mat_peptide, etc.
    //Value format    "text"
    //Example         /product="trypsinogen" (when qualifier appears in CDS feature)
    //                /product="trypsin" (when qualifier appears in mat_peptide feature)
    //                /product="XYZ neural-specific transcript" (when qualifier appears in
    //                mRNA feature)

    PRODUCT,
    //Qualifier       /protein_id=
    //Definition      protein identifier, issued by International collaborators.
    //                this qualifier consists of a stable ID portion (3+5 format
    //                with 3 position letters and 5 numbers) plus a version number
    //                after the decimal point.
    //Value format    <identifier>
    //Example         /protein_id="AAA12345.1"
    //Comment         when the protein sequence encoded by the CDS changes, only
    //                the version number of the /protein_id value is incremented;
    //                the stable part of the /protein_id remains unchanged and as a
    //                result will permanently be associated with a given protein;
    //                this qualifier is valid only on CDS features which translate
    //                into a valid protein.

    PROTEIN_ID,
    //Qualifier       /proviral
    //Definition      this qualifier is used to flag sequence obtained from a virus or
    //                phage that is integrated into the genome of another organism
    //Value format    none
    //Example         /proviral

    PROVIRAL,
    //Qualifier       /pseudo
    //Definition      indicates that this feature is a non-functional version of the
    //                element named by the feature key
    //Value format    none
    //Example         /pseudo
    //Comment         The qualifier /pseudo should be used to describe non-functional
    //                genes that are not formally described as pseudogenes, e.g. CDS
    //                has no translation due to other reasons than pseudogenisation events.
    //                Other reasons may include sequencing or assembly errors.
    //                In order to annotate pseudogenes the qualifier /pseudogene= must be
    //                used indicating the TYPE which can be taken from the INSDC controlled vocabulary
    //                for pseudogenes.

    PSEUDO,
    //Qualifier       /pseudogene=
    //Definition      indicates that this feature is a pseudogene of the element named
    //                by the feature key
    //Value format    "TYPE"
    //                where TYPE is one of the following:
    //                processed, unprocessed, unitary, allelic, unknown
    //
    //Example         /pseudogene="processed"
    //                /pseudogene="unprocessed"
    //                /pseudogene="unitary"
    //                /pseudogene="allelic"
    //                /pseudogene="unknown"
    //
    //Comment         TYPE is a term taken from the INSDC controlled vocabulary for pseudogenes
    //                (http://www.insdc.org/documents/pseudogene-qualifier-vocabulary):
    //
    //                processed: the pseudogene has arisen by reverse transcription of a
    //                mRNA into cDNA, followed by reintegration into the genome. Therefore,
    //                it has lost any intron/exon structure, and it might have a pseudo-polyA-tail.
    //
    //                unprocessed: the pseudogene has arisen from a copy of the parent gene by duplication
    //                followed by accumulation of random mutations. The changes, compared to their
    //                functional homolog, include insertions, deletions, premature stop codons, frameshifts
    //                and a higher proportion of non-synonymous versus synonymous substitutions.
    //
    //                unitary: the pseudogene has no parent. It is the original gene, which is
    //                functional is some species but disrupted in some way (indels, mutation,
    //                recombination) in another species or strain.
    //
    //                allelic: a (unitary) pseudogene that is stable in the population but
    //                importantly it has a functional alternative allele also in the population. i.e.,
    //                one strain may have the gene, another strain may have the pseudogene.
    //                MHC haplotypes have allelic pseudogenes.
    //
    //                unknown: the submitter does not know the method of pseudogenisation.

    PSEUDOGENE,
    //Qualifier       /rearranged
    //Definition      the sequence presented in the entry has undergone somatic
    //                rearrangement as part of an adaptive immune response; it is not
    //                the unrearranged sequence that was inherited from the parental
    //                germline
    //Value format    none
    //Example         /rearranged
    //Comment         /rearranged should not be used to annotate chromosome
    //                rearrangements that are not involved in an adaptive immune
    //                response;
    //                /germline and /rearranged cannot be used in the same source
    //                feature;
    //                /germline and /rearranged should only be used for molecules that
    //                can undergo somatic rearrangements as part of an adaptive immune
    //                response; these are the T-cell receptor (TCR) and immunoglobulin
    //                loci in the jawed vertebrates, and the unrelated variable
    //                lymphocyte receptor (VLR) locus in the jawless fish (lampreys
    //                and hagfish);
    //                /germline and /rearranged should not be used outside of the
    //                Craniata (taxid=89593)

    REARRANGED,
    //Qualifier       /replace=
    //Definition      indicates that the sequence identified a feature's intervals is
    //                replaced by the sequence shown in "text"; if no sequence is
    //                contained within the qualifier, this indicates a deletion.
    //Value format    "text"
    //Example         /replace="a"
    //                /replace=""

    REPLACE,
    //Qualifier       /ribosomal_slippage
    //Definition      during protein translation, certain sequences can program
    //                ribosomes to change to an alternative reading frame by a
    //                mechanism known as ribosomal slippage
    //Value format    none
    //Example         /ribosomal_slippage
    //Comment         a join operator,e.g.: [join(486..1784,1787..4810)] should be used
    //                in the CDS spans to indicate the location of ribosomal_slippage

    RIBOSOMAL_SLIPPAGE,
    //Qualifier       /rpt_family=
    //Definition      type of repeated sequence; "Alu" or "Kpn", for example
    //Value format    "text"
    //Example         /rpt_family="Alu"

    RPT_FAMILY,
    //Qualifier       /rpt_type=
    //Definition      organization of repeated sequence
    //Value format    tandem, inverted, flanking, terminal, direct, dispersed, and other
    //Example         /rpt_type=INVERTED
    //Comment         the values are case-insensitive, i.e. both "INVERTED" and "inverted"
    //                are valid;
    //                Definitions of the values:
    //                tandem, a repeat that exists adjacent to another in the same
    //                orientation;
    //                inverted, a repeat which occurs as part of as set (normally a part)
    //                organized in the reverse orientation;
    //                flanking, a repeat lying outside the sequence for which it has
    //                functional significance (eg. transposon insertion target sites);
    //                terminal, a repeat at the ends of and within the sequence for which
    //                it has functional significance (eg. transposon LTRs);
    //                direct, a repeat that exists not always adjacent but is in the same
    //                orientation;
    //                dispersed, a repeat that is found dispersed throughout the genome;
    //                other, a repeat exhibiting important attributes that cannot be
    //                described by other values. Please also visit:
    //                http://www.insdc.org/controlled-vocabulary-rpttype-qualifier

    RPT_TYPE,
    //Qualifier       /rpt_unit_range=
    //Definition      identity of a repeat range
    //Value format    <base_range>
    //Example         /rpt_unit_range=202..245
    //Comment         used to indicate the base range of the sequence that constitutes
    //                a repeated sequence specified by the feature keys oriT and
    //                repeat_region; qualifiers /rpt_unit_range and /rpt_unit_seq
    //                replaced qualifier /rpt_unit in December 2005
    RPT_UNIT_RANGE,
    //Qualifier       /rpt_unit_seq=
    //Definition      identity of a repeat sequence
    //Value format    "text"
    //Example         /rpt_unit_seq="aagggc"
    //                /rpt_unit_seq="ag(5)tg(8)"
    //                /rpt_unit_seq="(AAAGA)6(AAAA)1(AAAGA)12"
    //Comment         used to indicate the literal sequence that constitutes a
    //                repeated sequence specified by the feature keys oriT and
    //                repeat_region; qualifiers /rpt_unit_range and /rpt_unit_seq
    //                replaced qualifier /rpt_unit in December 2005

    RPT_UNIT_SEQ,
    //Qualifier       /satellite=
    //Definition      identifier for a satellite DNA marker, compose of many tandem
    //                repeats (identical or related) of a short basic repeated unit;
    //Value format    "<satellite_type>[:<class>][ <identifier>]"
    //                where satellite_type is one of the following
    //                    "satellite", "microsatellite", "minisatellite"
    //Example         /satellite="satellite: S1a"
    //                /satellite="satellite: alpha"
    //                /satellite="satellite: gamma III"
    //                /satellite="microsatellite: DC130"
    //Comment         many satellites have base composition or other properties
    //                that differ from those of the rest of the genome that allows
    //                them to be identified.
    //                Please also visit: http://www.insdc.org/controlled-vocabulary-satellite-qualifier

    SATELLITE,
    //Qualifier       /segment=
    //Definition      name of viral or phage segment sequenced
    //Value format    "text"
    //Example         /segment="6"

    SEGMENT,
    //Qualifier       /serotype=
    //Definition      serological variety of a species characterized by its
    //                antigenic properties
    //Value format    "text"
    //Example         /serotype="B1"
    //Comment         used only with the source feature key;
    //                the Bacteriological Code recommends the use of the
    //                term 'serovar' instead of 'serotype' for the
    //                prokaryotes; see the International Code of Nomenclature
    //                of Bacteria (1990 Revision) Appendix 10.B "Infraspecific
    //                Terms".

    SEROTYPE,
    //Qualifier       /serovar=
    //Definition      serological variety of a species (usually a prokaryote)
    //                characterized by its antigenic properties
    //Value format    "text"
    //Example         /serovar="O157:H7"
    //Comment         used only with the source feature key;
    //                the Bacteriological Code recommends the use of the
    //                term 'serovar' instead of 'serotype' for prokaryotes;
    //                see the International Code of Nomenclature of Bacteria
    //                (1990 Revision) Appendix 10.B "Infraspecific Terms".

    SEROVAR,
    //Qualifier       /sex=
    //Definition      sex of the organism from which the sequence was obtained;
    //                sex is used for eukaryotic organisms that undergo meiosis
    //                and have sexually dimorphic gametes
    //Value format    "text"
    //Examples        /sex="female"
    //                /sex="male"
    //                /sex="hermaphrodite"
    //                /sex="unisexual"
    //                /sex="bisexual"
    //                /sex="asexual"
    //                /sex="monoecious" [or monecious]
    //                /sex="dioecious" [or diecious]
    //Comment         /sex should be used (instead of /mating_type)
    //                in the Metazoa, Embryophyta, Rhodophyta & Phaeophyceae;
    //                /mating_type should be used (instead of /sex)
    //                in the Bacteria, Archaea & Fungi;
    //                neither /sex nor /mating_type should be used
    //                in the viruses;
    //                outside of the taxa listed above, /mating_type
    //                should be used unless the value of the qualifier
    //                is taken from the vocabulary given in the examples
    //                above

    SEX,
    //Qualifier       /specimen_voucher=
    //Definition      identifier for the specimen from which the nucleic acid
    //                sequenced was obtained
    //Value format    /specimen_voucher="[<institution-code>:[<collection-code>:]]<specimen_id>"
    //Example         /specimen_voucher="UAM:Mamm:52179"
    //                /specimen_voucher="AMCC:101706"
    //                /specimen_voucher="USNM:field series 8798"
    //                /specimen_voucher="personal:Dan Janzen:99-SRNP-2003"
    //                /specimen_voucher="99-SRNP-2003"
    //Comment         the /specimen_voucher qualifier is intended to annotate a
    //                reference to the physical specimen that remains after the
    //                sequence has been obtained;
    //                if the specimen was destroyed in the process of sequencing,
    //                electronic images (e-vouchers) are an adequate substitute for a
    //                physical voucher specimen; ideally the specimens will be
    //                deposited in a curated museum, herbarium, or frozen tissue
    //                collection, but often they will remain in a personal or
    //                laboratory collection for some time before they are deposited in
    //                a curated collection;
    //                there are three forms of specimen_voucher qualifiers; if the
    //                text of the qualifier includes one or more colons it is a
    //                'structured voucher'; structured vouchers include
    //                institution-codes (and optional collection-codes) taken from a
    //                controlled vocabulary maintained by the INSDC that denotes the
    //                museum or herbarium collection where the specimen resides;
    //                Please also visit: http://www.insdc.org/controlled-vocabulary-specimenvoucher-qualifier

    SPECIMEN_VOUCHER,
    //Qualifier       /standard_name=
    //Definition      accepted standard name for this feature
    //Value format    "text"
    //Example         /standard_name="dotted"
    //Comment         use /standard_name to give full gene name, but use /gene to
    //                give gene symbol (in the above example /gene="Dt").

    STANDARD_NAME,
    //Qualifier       /strain=
    //Definition      strain from which sequence was obtained
    //Value format    "text"
    //Example         /strain="BALB/c"
    //Comment         entries including /strain must not include
    //                the /environmental_sample qualifier

    STRAIN,
    //Qualifier       /sub_clone=
    //Definition      sub-clone from which sequence was obtained
    //Value format    "text"
    //Example         /sub_clone="lambda-hIL7.20g"
    //Comment         the comments on /clone apply to /sub_clone

    SUB_CLONE,
    //Qualifier       /sub_species=
    //Definition      name of sub-species of organism from which sequence was
    //                obtained
    //Value format    "text"
    //Example         /sub_species="lactis"

    SUB_SPECIES,
    //Qualifier       /sub_strain=
    //Definition      name or identifier of a genetically or otherwise modified
    //                strain from which sequence was obtained, derived from a
    //                parental strain (which should be annotated in the /strain
    //                qualifier).sub_strain from which sequence was obtained
    //Value format    "text"
    //Example         /sub_strain="abis"
    //Comment         If the parental strain is not given, this should
    //                be annotated in the strain qualifier instead of sub_strain.
    //                Either:
    //                /strain="K-12"
    //                /sub_strain="MG1655"
    //                or:
    //                /strain="MG1655"

    SUB_STREAIN,
    //Qualifier       /tag_peptide=
    //Definition      base location encoding the polypeptide for proteolysis tag of
    //                tmRNA and its termination codon;
    //Value format    <base_range>
    //Example         /tag_peptide=90..122
    //Comment         it is recommended that the amino acid sequence corresponding
    //                to the /tag_peptide be annotated by describing a 5' partial
    //                CDS feature; e.g. CDS    <90..122;

    TAG_PEPTIDE,
    //Qualifier       /tissue_lib=
    //Definition      tissue library from which sequence was obtained
    //Value format    "text"
    //Example         /tissue_lib="tissue library 772"

    TISSUE_LIB,
    //Qualifier       /tissue_type=
    //Definition      tissue type from which the sequence was obtained
    //Value format    "text"
    //Example         /tissue_type="liver"

    TISSUE_TYPE,
    //Qualifier       /transgenic
    //Definition      identifies the source feature of the organism which was
    //                the recipient of transgenic DNA.
    //Value format    none
    //Example         /transgenic
    //Comment         transgenic sequences must have at least two source feature keys;
    //                the source feature key having the /transgenic qualifier must
    //                span the whole sequence; the source feature carrying the
    //                /transgenic qualifier identifies the main organism of the entry,
    //                this determines: a) the name displayed in the organism lines,
    //                b) if no translation table is specified, the translation table;
    //                only one source feature with /transgenic is allowed in an entry;
    //                the /focus and /transgenic qualifiers are mutually exclusive in
    //                an entry.

    TRANSGENIC,
    //Qualifier       /translation=
    //Definition      automatically generated one-letter abbreviated amino acid
    //                sequence derived from either the universal genetic code or the
    //                table as specified in /transl_table and as determined by an
    //                exception in the /transl_except qualifier
    //Value format    IUPAC one-letter amino acid abbreviation, "X" is to be used
    //                for AA exceptions.
    //Example         /translation="MASTFPPWYRGCASTPSLKGLIMCTW"
    //Comment         to be used with CDS feature only; this is a mandatory qualifier
    //                in the CDS feature key except where /pseudogene="TYPE" or /pseudo
    //                is shown; see /transl_table for definition and location of genetic
    //                code tables.

    TRANSLATION,
    //Qualifier       /transl_except=
    //Definition      translational exception: single codon the translation of which
    //                does not conform to genetic code defined by /organism or
    //                /transl_table.
    //Value format    (pos:location,aa:<amino_acid>) where amino_acid is the
    //                amino acid coded by the codon at the base_range position
    //Example         /transl_except=(pos:213..215,aa:Trp)
    //                /transl_except=(pos:1017,aa:TERM)
    //                /transl_except=(pos:2000..2001,aa:TERM)
    //                /transl_except=(pos:X22222:15..17,aa:Ala)
    //Comment         if the amino acid is not on the restricted vocabulary list use
    //                e.g., '/transl_except=(pos:213..215,aa:OTHER)' with
    //                '/note="name of unusual amino acid"';
    //                for modified amino-acid selenocysteine use three letter code
    //                'Sec'  (one letter code 'U' in amino-acid sequence)
    //                /transl_except=(pos:1002..1004,aa:Sec);
    //                for partial termination codons where TAA stop codon is
    //                completed by the addition of 3' A residues to the mRNA
    //                either a single base_position or a base_range is used, e.g.
    //                if partial stop codon is a single base:
    //                /transl_except=(pos:1017,aa:TERM)
    //                if partial stop codon consists of two bases:
    //                /transl_except=(pos:2000..2001,aa:TERM) with
    //                '/note='stop codon completed by the addition of 3' A residues
    //                to the mRNA'.

    TRANSL_EXCEPT,
    //Qualifier       /transl_table=
    //Definition      definition of genetic code table used if other than universal
    //                genetic code table. Tables used are described in appendix IV.
    //Value format    <integer; 1=universal table 1;2=non-universal table 2;...
    //Example         /transl_table=4
    //Comment         genetic code exceptions outside range of specified tables are
    //                reported in /transl_except qualifier.

    TRANSL_TABLE,
    //Qualifier       /trans_splicing
    //Definition      indicates that exons from two RNA molecules are ligated in
    //                intermolecular reaction to form mature RNA
    //Value format    none
    //Example         /trans_splicing
    //Comment         should be used on features such as CDS, mRNA and other features
    //                that are produced as a result of a trans-splicing event. This
    //                qualifier should be used only when the splice event is indicated in
    //                the "join" operator, eg join(complement(69611..69724),139856..140087)

    TRANSL_SPLICING,
    //Qualifier       /type_material=
    //Definition      indicates that the organism from which this sequence was obtained is
    //                a nomenclatural type of the species (or subspecies) corresponding with
    //                the /organism identified in the sequence entry
    //Value format    "<type-of-type> of <organism name>"
    //                where type-of-type is one of the following:
    //                type strain, neotype strain, holotype, paratype, neotype, allotype, hapanotype,
    //                syntype, lectotype, paralectotype, isotype, epitype, isosyntype, ex-type,
    //                reference strain, type material;
    //Example         /type_material="type strain of Escherichia coli"
    //                /type_material="holotype of Cercopitheus lomamiensis"
    //                /type_material="paratype of Cercopitheus lomamiensis"
    //Comment         <type-of-type> is taken from a controlled vocabularly, listed above.
    //                <organism name> should be listed as the scientific name
    //                (or as a synonym) at the species (or subsopecies) node in the taxonomy database.
    //                Usage of /type_material will start in the second half of 2014.
    TYPE_MATERIAL,
    //Qualifier       /variety=
    //Definition      variety (= varietas, a formal Linnaean rank) of organism
    //                from which sequence was derived.
    //Value format    "text"
    //Example         /variety="insularis"
    //Comment         use the cultivar qualifier for cultivated plant
    //                varieties, i.e., products of artificial selection;
    //                varieties other than plant and fungal variatas should be
    //                annotated via /note, e.g. /note="breed:Cukorova"
    VARIETY, PUBMED,

}
