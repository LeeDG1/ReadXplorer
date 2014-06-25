package de.cebitec.readXplorer.transcriptomeAnalyses.main;

import de.cebitec.readXplorer.api.objects.AnalysisI;
import de.cebitec.readXplorer.databackend.dataObjects.PersistantChromosome;
import de.cebitec.readXplorer.databackend.dataObjects.PersistantFeature;
import de.cebitec.readXplorer.transcriptomeAnalyses.datastructures.TranscriptionStart;
import de.cebitec.readXplorer.transcriptomeAnalyses.enums.StartCodon;
import de.cebitec.readXplorer.util.FeatureType;
import de.cebitec.readXplorer.util.Observer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Provides a method for detecting and classification of Transcription start
 * sites.
 *
 * @author jritter
 */
public class TssDetection implements Observer, AnalysisI<List<TranscriptionStart>> {

    private final List<TranscriptionStart> detectedTSS;
    private final int trackid;
    private final HashMap<Integer, Boolean> fwdTss, revTss;
    /**
     * Key: Feature ID, Value: Locus
     */
    private final HashMap<Integer, String> fwdFeaturesIds, revFeaturesIds;
    private final List<Integer[]> fwdOffsets, revOffsets;

    /**
     * Provides a method for detecting and classification of Transcription start
     * sites.
     *
     * @param refGenome PersistantReference instance.
     * @param trackID for wich the Transcription start site detection shall
     * running.
     */
    public TssDetection(int trackID) {
        this.trackid = trackID;
        this.detectedTSS = new ArrayList<>();
        this.fwdOffsets = new ArrayList<>();
        this.revOffsets = new ArrayList<>();
        this.fwdTss = new HashMap<>();
        this.revTss = new HashMap<>();
        this.fwdFeaturesIds = new HashMap<>();
        this.revFeaturesIds = new HashMap<>();
    }

    public List<TranscriptionStart> postProcessing(PersistantChromosome chrom, List<TranscriptionStart> detectedTss, double mm, int chromLength,
            HashMap<Integer, List<Integer>> fwdFeatures, HashMap<Integer, List<Integer>> revFeatures,
            HashMap<Integer, PersistantFeature> allFeatures, ParameterSetFiveEnrichedAnalyses parameters) {
        List<TranscriptionStart> postProcessedTssList = new ArrayList<>();
        HashSet<FeatureType> fadeOutFeatureTypes = parameters.getExcludeFeatureTypes();
        // settings for checking CDS-shift 
        HashMap<String, StartCodon> validCodons = parameters.getValidStartCodons();
        double relPercentage = (parameters.getCdsShiftPercentage() / 100.0);

        int leaderlessRange = parameters.getLeaderlessLimit();
        Integer distanceForExcludingTss = parameters.getExclusionOfTSSDistance();

        boolean isExclusionOfAllIntragenicTss = parameters.isExclusionOfAllIntragenicTSS();
        boolean keepAllIntragenicTss = parameters.isKeepAllIntragenicTss();
        boolean keepOnlyAssignedIntragenicTss = parameters.isKeepOnlyAssignedIntragenicTss();

        int keepingInternalTssDistance = parameters.getKeepIntragenicTssDistanceLimit();

        // for determining first and last feature
        int lastFeatureId = determineBiggestId(allFeatures);
        int fstFeatureId = determineSmallestId(allFeatures);

        for (TranscriptionStart tss : detectedTss) {
            if (tss.isFwdStrand()) {
                int pos = tss.getStartPosition();
                int offset = 0;
                int end = 0;
                int dist2start = 0;
                int dist2stop = 0;
                boolean cdsShift = false;
                int offsetToNextDownstreamFeature = 0;

                // determining the offset to next downstream feature
                while (!fwdFeatures.containsKey(pos + offset - end)) {
                    if ((pos + offset) > chromLength) {
                        end = chromLength;
                    }
                    offset++;
                }

                double rel_count = tss.getReadStarts() / mm;
                tss.setRelCount(rel_count);
                PersistantFeature feature = getCorrespondingFeature(fwdFeatures, pos, offset, end, allFeatures, fadeOutFeatureTypes, chromLength);

                if (offset == 0) {
                    dist2start = pos - feature.getStart();
                    dist2stop = feature.getStop() - pos;
                    tss.setDist2start(dist2start);
                    tss.setDist2stop(dist2stop);

                    // check if feture is leaderless (downstream direction)
                    if (dist2start <= leaderlessRange) {
                        tss.setLeaderless(true);

                        // check if cis antisense
                        if (revFeatures.get(pos) != null) {
                            tss.setPutativeAntisense(true);
                        }

                        cdsShift = checkLeaderlessCdsShift(dist2start, chrom, tss.isFwdStrand(), pos, validCodons, cdsShift);

                        if (!cdsShift && dist2start > 0) {
                            cdsShift = checkCdsShift(chrom, feature.getStart(), feature.getStop(), pos, dist2start, tss.isFwdStrand(), relPercentage, validCodons);
                        }
                        tss.setCdsShift(cdsShift);

                        tss.setDetectedGene(feature);
                        postProcessedTssList.add(tss);
                        if (!cdsShift) {
                            fwdFeaturesIds.put(feature.getId(), feature.getLocus());
                        }
                    } else if (dist2start > leaderlessRange && isExclusionOfAllIntragenicTss) {
                        // do nothing
                    } else if (dist2start > leaderlessRange && keepAllIntragenicTss) {
                        int currentFeatureID = feature.getId();
                        // Getting next Feature
                        // PersistantFeature nextDownstreamFeature = getNextDownstreamFeature(fadeOutFeatureTypes, feature, offsetToNextDownstreamFeature, currentFeatureID, lastFeatureId, fstFeatureId, allFeatures, chromLength, pos);
                        PersistantFeature nextDownstreamFeature = null;
                        boolean flag = true;
                        if (flag) {
                            while (nextDownstreamFeature == null || nextDownstreamFeature.isFwdStrand() == false || fadeOutFeatureTypes.contains(nextDownstreamFeature.getType()) || feature.getLocus().equals(nextDownstreamFeature.getLocus())) {

                                if (currentFeatureID >= lastFeatureId) {
                                    currentFeatureID = fstFeatureId - 1;
                                    nextDownstreamFeature = allFeatures.get(++currentFeatureID);
                                    if (nextDownstreamFeature != null) {
                                        offsetToNextDownstreamFeature = chromLength - pos + nextDownstreamFeature.getStart();
                                    }
                                } else {
                                    nextDownstreamFeature = allFeatures.get(++currentFeatureID);
                                    if (nextDownstreamFeature != null) {
                                        if (pos > nextDownstreamFeature.getStart()) {
                                            offsetToNextDownstreamFeature = chromLength + nextDownstreamFeature.getStart() - pos;
                                        } else {
                                            offsetToNextDownstreamFeature = nextDownstreamFeature.getStart() - pos;
                                        }
                                    }
                                }
                            }
                        }
                        // check antisensness
                        if (revFeatures.get(pos) != null) {
                            tss.setPutativeAntisense(true);
                        }

                        if (offsetToNextDownstreamFeature < keepingInternalTssDistance) {
                            // the putative corresponding gene for TSS
                            tss.setIntragenicTSS(true);
                            if (!cdsShift && dist2start >= 3) {
                                cdsShift = checkCdsShift(chrom, feature.getStart(), feature.getStop(), pos, dist2start, tss.isFwdStrand(), relPercentage, validCodons);
                            }
                            tss.setNextGene(nextDownstreamFeature);
                            tss.setOffsetToNextDownstrFeature(offsetToNextDownstreamFeature);
                            tss.setCdsShift(cdsShift);
                            postProcessedTssList.add(tss);
                            fwdFeaturesIds.put(nextDownstreamFeature.getId(), nextDownstreamFeature.getLocus());

                        } else {
                            tss.setIntragenicTSS(true);

                            if (!cdsShift && dist2start > 0) {
                                cdsShift = checkCdsShift(chrom, feature.getStart(), feature.getStop(), pos, dist2start, tss.isFwdStrand(), relPercentage, validCodons);
                            }
                            tss.setCdsShift(cdsShift);
                            tss.setDetectedGene(feature);
                            tss.setOffset(offset);
                            tss.setOffsetToNextDownstrFeature(offsetToNextDownstreamFeature);
                            postProcessedTssList.add(tss);
                        }

                    } else if (dist2start > leaderlessRange && keepOnlyAssignedIntragenicTss) {
                        int currentFeatureID = feature.getId();
                        // Getting next Feature
                        // PersistantFeature nextDownstreamFeature = getNextDownstreamFeature(fadeOutFeatureTypes, feature, offsetToNextDownstreamFeature, currentFeatureID, lastFeatureId, fstFeatureId, allFeatures, chromLength, pos);
                        PersistantFeature nextDownstreamFeature = null;
                        boolean flag = true;
                        if (flag) {
                            while (nextDownstreamFeature == null || nextDownstreamFeature.isFwdStrand() == false || fadeOutFeatureTypes.contains(nextDownstreamFeature.getType()) || feature.getLocus().equals(nextDownstreamFeature.getLocus())) {

                                if (currentFeatureID >= lastFeatureId) {
                                    currentFeatureID = fstFeatureId - 1;
                                    nextDownstreamFeature = allFeatures.get(++currentFeatureID);
                                    if (nextDownstreamFeature != null) {
                                        offsetToNextDownstreamFeature = chromLength - pos + nextDownstreamFeature.getStart();
                                    }
                                } else {
                                    nextDownstreamFeature = allFeatures.get(++currentFeatureID);
                                    if (nextDownstreamFeature != null) {
                                        if (pos > nextDownstreamFeature.getStart()) {
                                            offsetToNextDownstreamFeature = chromLength + nextDownstreamFeature.getStart() - pos;
                                        } else {
                                            offsetToNextDownstreamFeature = nextDownstreamFeature.getStart() - pos;
                                        }
                                    }
                                }
                            }
                        }
                        // check antisensness
                        if (revFeatures.get(pos) != null) {
                            tss.setPutativeAntisense(true);
                        }

                        if (offsetToNextDownstreamFeature < keepingInternalTssDistance) {
                            // the putative corresponding gene for TSS
                            tss.setIntragenicTSS(true);
                            if (!cdsShift && dist2start >= 3) {
                                cdsShift = checkCdsShift(chrom, feature.getStart(), feature.getStop(), pos, dist2start, tss.isFwdStrand(), relPercentage, validCodons);
                            }
                            tss.setNextGene(nextDownstreamFeature);
                            tss.setOffsetToNextDownstrFeature(offsetToNextDownstreamFeature);
                            tss.setCdsShift(cdsShift);
                            postProcessedTssList.add(tss);
                            fwdFeaturesIds.put(nextDownstreamFeature.getId(), nextDownstreamFeature.getLocus());
                        }
                    }
                } else {
                    // leaderless in upstream direction, offset is != 0 but in leaderless range
                    if (offset <= leaderlessRange) {
                        tss.setLeaderless(true);
                        // check antisensness
                        if (revFeatures.get(pos) != null) {
                            tss.setPutativeAntisense(true);
                        }
                        // check for cdsShift, when offset > and offset+1 mod 3 == 0
                        if (offset > 0 && ((offset + 1) % 3) == 0) {
                            String startAtTSS = getSubSeq(chrom, tss.isFwdStrand(), pos - 1, pos + 2);
                            if (validCodons.containsKey(startAtTSS)) {
                                cdsShift = true;
                            }
                        }
                        tss.setDetectedGene(feature);
                        tss.setOffset(offset);
                        tss.setCdsShift(cdsShift);
                        postProcessedTssList.add(tss);
                        fwdFeaturesIds.put(feature.getId(), feature.getLocus());
                    } else {
                        // checking for "normal" TSS
                        if (offset < distanceForExcludingTss) {
                            // check antisensness
                            if (revFeatures.get(pos) != null) {
                                tss.setPutativeAntisense(true);
                            }

                            tss.setDetectedGene(feature);
                            tss.setOffset(offset);
                            postProcessedTssList.add(tss);
                            fwdOffsets.add(new Integer[]{pos, pos + offset});
                            fwdTss.put(pos, false);
                            fwdFeaturesIds.put(feature.getId(), feature.getLocus());
                        } else {
                            // TSS is too far away from next annotated feature
                            // check only for antisense
                            if (revFeatures.get(pos) != null) {
                                for (int id : revFeatures.get(pos)) {
                                    feature = allFeatures.get(id);
                                    if (!fadeOutFeatureTypes.contains(feature.getType())) {
                                        tss.setPutativeAntisense(true);
                                    }
                                }
                            }

                            if (tss.isPutativeAntisense()) {
                                tss.setDetectedGene(feature);
                                tss.setIntragenicAntisense(true);
                                postProcessedTssList.add(tss);
                            }

                            // check for 3'utr anisenseness
                            if (tss.isPutativeAntisense() == false) {
                                offset = 0;
                                end = 0;
                                boolean noRevFeatureFlag = true;
                                while (noRevFeatureFlag) {
                                    if (revFeatures.containsKey(pos + offset - end)) {
                                        for (int id : revFeatures.get(pos + offset - end)) {
                                            if (!fadeOutFeatureTypes.contains(allFeatures.get(id).getType())) {
                                                noRevFeatureFlag = false;
                                                feature = allFeatures.get(id);
                                            }
                                        }

                                    }
                                    if ((pos + offset) > chromLength) {
                                        end = chromLength;
                                    }
                                    offset++;
                                }
                                if (offset < parameters.getThreeUtrLimitAntisenseDetection()) {

                                    tss.setPutativeAntisense(true);
                                    tss.setDetectedGene(feature);
                                    tss.setIs3PrimeUtrAntisense(true);
                                    postProcessedTssList.add(tss);
                                }
                            }

                            // determining the offset to next downstream feature
                            // and checking the antisense site for annotated features
                            if (tss.isPutativeAntisense() == false) {
                                offset = 0;
                                boolean noRevFeatureFlag = true;
                                boolean noFwdFeatureFlag = true;
                                while (noRevFeatureFlag && noFwdFeatureFlag) {
                                    if (revFeatures.containsKey(pos + offset - end)) {
                                        for (int id : revFeatures.get(pos + offset - end)) {
//                                                    int id = reverseCDSs.get(i + offset - end).get(0);
                                            if (!fadeOutFeatureTypes.contains(allFeatures.get(id).getType())) {
                                                noRevFeatureFlag = false;
                                            }
                                        }
                                    }
                                    if (fwdFeatures.containsKey(pos + offset - end)) {
                                        for (Integer id : fwdFeatures.get(pos + offset - end)) {
                                            if (!fadeOutFeatureTypes.contains(allFeatures.get(id).getType())) {
                                                noFwdFeatureFlag = false;
                                            }
                                        }
                                    }
                                    if ((pos + offset) > chromLength) {
                                        end = chromLength;
                                    }
                                    offset++;
                                }

                                if (offset > parameters.getThreeUtrLimitAntisenseDetection()) {
                                    tss.setIntergenicTSS(true);
                                    postProcessedTssList.add(tss);
                                }
                            }
                        }
                    }

                }

            } else {
                int pos = tss.getStartPosition();
                int offset = 0;
                int end = 0;
                int dist2start = 0;
                int dist2stop = 0;
                boolean cdsShift = false;
                int offsetToNextDownstreamFeature = 0;

                // determining the offset to feature
                while (!revFeatures.containsKey(end + pos - offset)) {
                    if ((pos - offset) == 0) {
                        end = chromLength;
                    }
                    offset++;
                }

                double rel_count = tss.getReadStarts() / mm;

                PersistantFeature feature = null;

                boolean flag = true;
//                    // check for overlapping Features
                while (flag) {
                    if (revFeatures.containsKey(end + pos - offset)) {
                        for (int id : revFeatures.get(end + pos - offset)) {
                            feature = allFeatures.get(id);
                            if (flag && feature != null && !fadeOutFeatureTypes.contains(feature.getType())) {
                                flag = false;
                                break;
                            } else {
                                if ((pos - offset) == 0) {
                                    end = chromLength;
                                }
                                offset++;
                            }
                        }
                    } else {
                        if ((pos - offset) == 0) {
                            end = chromLength;
                        }
                        offset++;
                    }
                }

                if (offset == 0) {
                    dist2start = feature.getStop() - pos;
                    dist2stop = pos - feature.getStart();
                    tss.setDist2start(dist2start);
                    tss.setDist2stop(dist2stop);

                    // check if leaderless (downstream)
                    if (dist2start <= leaderlessRange) {
                        tss.setLeaderless(true);
                        // check antisensness
                        if (fwdFeatures.get(pos) != null) {
                            tss.setPutativeAntisense(true);
                        }

                        // check for cdsShift when offset is 0 and distance2Start > 0 and dist2Start mod 3 == 0
//                            cdsShift = checkCdsShift(chrom, feature.getStart(), feature.getStop(), i, dist2start, isFwd, relPercentage, validCodons);
                        if (dist2start > 0 && (dist2start % 3) == 0) {
                            String startAtTSSRev = getSubSeq(chrom, tss.isFwdStrand(), pos - 3, pos);
                            String complement = complement(startAtTSSRev);
                            if (validCodons.containsKey(complement)) {
                                cdsShift = true;
                            }
                        }

                        if (!cdsShift && dist2start > 0) {
                            cdsShift = checkCdsShift(chrom, feature.getStart(), feature.getStop(), pos, dist2start, tss.isFwdStrand(), relPercentage, validCodons);
                        }
                        tss.setCdsShift(cdsShift);
                        tss.setDetectedGene(feature);
                        tss.setOffset(offset);
                        postProcessedTssList.add(tss);
                        if (!cdsShift) {
                            revFeaturesIds.put(feature.getId(), feature.getLocus());
                        }
                    } else if (dist2start > leaderlessRange && keepAllIntragenicTss) {
                        int currentFeatureID = feature.getId();
                        PersistantFeature nextFeature = null;

                        flag = true;
                        if (flag) {
                            while (nextFeature == null || feature.getLocus().equals(nextFeature.getLocus()) || fadeOutFeatureTypes.contains(nextFeature.getType()) || nextFeature.isFwdStrand()) {

                                if (currentFeatureID <= fstFeatureId) {
                                    currentFeatureID = lastFeatureId + 1;
                                    nextFeature = allFeatures.get(--currentFeatureID);
                                    if (nextFeature != null) {
                                        offsetToNextDownstreamFeature = chromLength - nextFeature.getStop() + pos;
                                    }
                                } else {
                                    nextFeature = allFeatures.get(--currentFeatureID);
                                    if (nextFeature != null) {
                                        if (nextFeature.getStop() > pos) {
                                            offsetToNextDownstreamFeature = chromLength - nextFeature.getStop() + pos;
                                        } else {
                                            offsetToNextDownstreamFeature = pos - nextFeature.getStop();
                                        }
                                    }
                                }
                            }
                        }
                        // check antisensness
                        if (fwdFeatures.get(pos) != null) {
                            tss.setPutativeAntisense(true);
                        }

                        if (offsetToNextDownstreamFeature < keepingInternalTssDistance) {
                            tss.setIntragenicTSS(true);
                            if (!cdsShift && dist2start >= 3) {
                                cdsShift = checkCdsShift(chrom, feature.getStart(), feature.getStop(), pos, dist2start, tss.isFalsePositive(), relPercentage, validCodons);
                            }
                            // puttative nextgene
                            tss.setCdsShift(cdsShift);
                            tss.setNextGene(nextFeature);
                            tss.setOffsetToNextDownstrFeature(offsetToNextDownstreamFeature);
                            postProcessedTssList.add(tss);
                            revFeaturesIds.put(nextFeature.getId(), nextFeature.getLocus());
                        } else {
                            if (!cdsShift && dist2start > 0) {
                                cdsShift = checkCdsShift(chrom, feature.getStart(), feature.getStop(), pos, dist2start, tss.isFwdStrand(), relPercentage, validCodons);
                            }
                            tss.setIntragenicTSS(true);
                            tss.setCdsShift(cdsShift);
                            tss.setDetectedGene(feature);
                            tss.setOffset(offset);
                            postProcessedTssList.add(tss);
                        }
                    } else if (dist2start > leaderlessRange && keepOnlyAssignedIntragenicTss) {
                        int currentFeatureID = feature.getId();
                        PersistantFeature nextFeature = null;

                        flag = true;
                        if (flag) {
                            while (nextFeature == null || feature.getLocus().equals(nextFeature.getLocus()) || fadeOutFeatureTypes.contains(nextFeature.getType()) || nextFeature.isFwdStrand()) {

                                if (currentFeatureID <= fstFeatureId) {
                                    currentFeatureID = lastFeatureId + 1;
                                    nextFeature = allFeatures.get(--currentFeatureID);
                                    if (nextFeature != null) {
                                        offsetToNextDownstreamFeature = chromLength - nextFeature.getStop() + pos;
                                    }
                                } else {
                                    nextFeature = allFeatures.get(--currentFeatureID);
                                    if (nextFeature != null) {
                                        if (nextFeature.getStop() > pos) {
                                            offsetToNextDownstreamFeature = chromLength - nextFeature.getStop() + pos;
                                        } else {
                                            offsetToNextDownstreamFeature = pos - nextFeature.getStop();
                                        }
                                    }
                                }
                            }
                        }
                        // check antisensness
                        if (fwdFeatures.get(pos) != null) {
                            tss.setPutativeAntisense(true);
                        }

                        if (offsetToNextDownstreamFeature < keepingInternalTssDistance) {
                            tss.setIntragenicTSS(true);
                            if (!cdsShift && dist2start >= 3) {
                                cdsShift = checkCdsShift(chrom, feature.getStart(), feature.getStop(), pos, dist2start, tss.isFwdStrand(), relPercentage, validCodons);
                            }
                            // puttative nextgene
                            tss.setCdsShift(cdsShift);
                            tss.setNextGene(nextFeature);
                            tss.setOffsetToNextDownstrFeature(offsetToNextDownstreamFeature);
                            postProcessedTssList.add(tss);
                            revFeaturesIds.put(nextFeature.getId(), nextFeature.getLocus());
                        }
                    }
                } else {
                    if (offset <= leaderlessRange) {

                        // check antisensness
                        if (fwdFeatures.get(pos) != null) {
                            tss.setPutativeAntisense(true);
                        }
                        // Leaderless TSS upstream
                        tss.setLeaderless(true);
                        // check for cdsShift when offset is 0 and distance2Start > 0 and mod 3 == 0
                        // cdsShift = checkCdsShift(chrom, feature.getStart(), feature.getStop(), i, dist2start, isFwd, relPercentage, validCodons);
                        if (dist2start > 0 && (dist2start % 3) == 0) {
                            String startAtTSSRev = getSubSeq(chrom, tss.isFwdStrand(), pos - 3, pos);
                            String complement = complement(startAtTSSRev);
                            if (validCodons.containsKey(complement)) {
                                cdsShift = true;
                            }
                        }
                        tss.setCdsShift(cdsShift);
                        tss.setDetectedGene(feature);
                        tss.setOffset(offset);
                        postProcessedTssList.add(tss);
                        revFeaturesIds.put(feature.getId(), feature.getLocus());
                    } else {
                        // bigger Leaderless restriction
                        // "normal" TSS
                        if (offset < distanceForExcludingTss) {

                            // check antisensness
                            if (fwdFeatures.get(pos) != null) {
                                tss.setPutativeAntisense(true);
                            }
                            tss.setDetectedGene(feature);
                            tss.setOffset(offset);
                            revOffsets.add(new Integer[]{pos - offset, pos});
                            revTss.put(pos, false);
                            postProcessedTssList.add(tss);
                            revFeaturesIds.put(feature.getId(), feature.getLocus());
                        } else {
                            // check only for antisense
                            if (fwdFeatures.get(pos) != null) {
                                for (int id : fwdFeatures.get(pos)) {
                                    feature = allFeatures.get(id);
                                    if (!fadeOutFeatureTypes.contains(feature.getType())) {
                                        tss.setPutativeAntisense(true);
                                    }
                                }
                                if (tss.isPutativeAntisense()) {
                                    tss.setDetectedGene(feature);
                                    tss.setIntragenicAntisense(true);
                                    postProcessedTssList.add(tss);
                                }
                            }

                            // check for 3'utr anisenseness
                            if (!tss.isPutativeAntisense()) {
                                offset = 0;
                                end = 0;
                                boolean noFwdFeatureFlag = true;
                                while (noFwdFeatureFlag) {
                                    if (fwdFeatures.containsKey(end + pos - offset)) {
                                        for (Integer id : fwdFeatures.get(end + pos - offset)) {
                                            if (!fadeOutFeatureTypes.contains(allFeatures.get(id).getType())) {
                                                noFwdFeatureFlag = false;
                                                feature = allFeatures.get(id);
                                            }
                                        }
                                    }
                                    if ((pos - offset) == 0) {
                                        end = chromLength;
                                    }
                                    offset++;
                                }
                                if (offset < parameters.getThreeUtrLimitAntisenseDetection()) {
                                    tss.setPutativeAntisense(true);
                                    tss.setDetectedGene(feature);
                                    tss.setIs3PrimeUtrAntisense(true);
                                    postProcessedTssList.add(tss);
                                }
                            }
                            // determining the offset to next downstream feature
                            if (!tss.isPutativeAntisense()) {
                                offset = 0;

                                boolean noRevFeatureFlag = true;
                                boolean noFwdFeatureFlag = true;
                                while (noRevFeatureFlag && noFwdFeatureFlag) {
                                    if (revFeatures.containsKey(end + pos - offset)) {
                                        for (Integer id : revFeatures.get(end + pos - offset)) {
                                            if (!fadeOutFeatureTypes.contains(allFeatures.get(id).getType())) {
                                                noRevFeatureFlag = false;
                                            }
                                        }
                                    }
                                    if (fwdFeatures.containsKey(end + pos - offset)) {
                                        for (Integer id : fwdFeatures.get(end + pos - offset)) {
                                            if (!fadeOutFeatureTypes.contains(allFeatures.get(id).getType())) {
                                                noFwdFeatureFlag = false;
                                            }
                                        }
                                    }
                                    if ((pos - offset) == 0) {
                                        end = chromLength;
                                    }
                                    offset++;
                                }
                                if (offset > parameters.getThreeUtrLimitAntisenseDetection()) {
                                    tss.setIntergenicTSS(true);
                                    postProcessedTssList.add(tss);
                                }
                            }
                        }

                    }

                }
            }

        }

        // running additional 5'-UTR Antisense detection
        for (int i = 0; i < fwdOffsets.size(); i++) {
            Integer[] offset = fwdOffsets.get(i);
            int j = offset[0];
            int k = offset[1];
            for (; j < k; j++) {
                if (revTss.containsKey(j)) {
                    revTss.put(j, true);
                }
            }
        }

        for (int i = 0; i < revOffsets.size(); i++) {
            Integer[] offset = revOffsets.get(i);
            int j = offset[0];
            int k = offset[1];
            for (; j < k; j++) {
                if (fwdTss.containsKey(j)) {
                    fwdTss.put(j, true);
                }
            }
        }

        for (TranscriptionStart transcriptionStart : postProcessedTssList) {
            int start = transcriptionStart.getStartPosition();
            boolean isFwd = transcriptionStart.isFwdStrand();
            if (isFwd) {
                if (fwdTss.containsKey(start)) {
                    if (fwdTss.get(start) == true) {
                        transcriptionStart.setPutativeAntisense(true);
                        transcriptionStart.setIs5PrimeUtrAntisense(true);
                    }
                }
            } else {
                if (revTss.containsKey(start)) {
                    if (revTss.get(start) == true) {
                        transcriptionStart.setPutativeAntisense(true);
                        transcriptionStart.setIs5PrimeUtrAntisense(true);
                    }
                }
            }

            // Features with assigned stable RNA are flagged as assigned to stable RNA
            if (transcriptionStart.getAssignedFeature() != null) {
                if (transcriptionStart.getAssignedFeature().getType() == FeatureType.RRNA || transcriptionStart.getAssignedFeature().getType() == FeatureType.TRNA) {
                    transcriptionStart.setAssignedToStableRNA(true);
                    transcriptionStart.setAssignedFeatureType(transcriptionStart.getAssignedFeature().getType());
                }
            }

            /**
             * Running postprocessing of cds-shifts. All tss marked as putative
             * cds-shifts are only a valid putative cds-shift, if and only if
             * the feature has not alyready a TSS asigned.
             */
            if (transcriptionStart.isCdsShift()) {
                int featureID = transcriptionStart.getAssignedFeature().getId();

                if (fwdFeaturesIds.containsKey(featureID) || revFeaturesIds.containsKey(featureID)) {
                    transcriptionStart.setCdsShift(false);
                    transcriptionStart.setIntragenicTSS(true);
                }
            }
        }

        return postProcessedTssList;
    }

    /**
     *
     * @param fadeOutFeatureTypes
     * @param feature
     * @param offsetToNextDownstreamFeature
     * @param currentFeatureID
     * @param lastFeatureId
     * @param fstFeatureId
     * @param allFeatures
     * @param chromLength
     * @param pos
     * @return
     */
    private PersistantFeature getNextDownstreamFeature(HashSet<FeatureType> fadeOutFeatureTypes, PersistantFeature feature, int offsetToNextDownstreamFeature, int currentFeatureID, int lastFeatureId, int fstFeatureId, HashMap<Integer, PersistantFeature> allFeatures, int chromLength, int pos) {
        PersistantFeature nextDownstreamFeature = null;
        boolean flag = true;
        if (flag) {
            while (nextDownstreamFeature == null || nextDownstreamFeature.isFwdStrand() == false || fadeOutFeatureTypes.contains(nextDownstreamFeature.getType()) || feature.getLocus().equals(nextDownstreamFeature.getLocus())) {

                if (currentFeatureID >= lastFeatureId) {
                    currentFeatureID = fstFeatureId - 1;
                    nextDownstreamFeature = allFeatures.get(++currentFeatureID);
                    if (nextDownstreamFeature != null) {
                        offsetToNextDownstreamFeature = chromLength - pos + nextDownstreamFeature.getStart();
                    }
                } else {
                    nextDownstreamFeature = allFeatures.get(++currentFeatureID);
                    if (nextDownstreamFeature != null) {
                        if (pos > nextDownstreamFeature.getStart()) {
                            offsetToNextDownstreamFeature = chromLength + nextDownstreamFeature.getStart() - pos;
                        } else {
                            offsetToNextDownstreamFeature = nextDownstreamFeature.getStart() - pos;
                        }
                    }
                }
            }
        }

        return nextDownstreamFeature;
    }

    /**
     * This method determines the offset to the current feature.
     *
     * @param fwdFeatures All persistant features in forward direction.
     * @param pos Transcription start position
     * @param offset length from transcription start site to translation start
     * site
     * @param end
     * @param allFeatures All persistant features in both directions
     * @param fadeOutFeatureTypes Feature types which have to be excluded from
     * analysis
     * @param chromLength Chromosome length.
     * @return the current persistant feature
     */
    private PersistantFeature getCorrespondingFeature(HashMap<Integer, List<Integer>> fwdFeatures, int pos, int offset, int end, HashMap<Integer, PersistantFeature> allFeatures, HashSet<FeatureType> fadeOutFeatureTypes, int chromLength) {
        // getting the PersistantFeature
        PersistantFeature feature = null;

        /**
         * check for overlapping Features and determining the corresponding
         * feature and offset
         */
        boolean flag = true;
        while (flag) {
            if (fwdFeatures.containsKey(pos + offset - end)) {
                for (int id : fwdFeatures.get(pos + offset - end)) {
                    feature = allFeatures.get(id);
                    if (feature != null && !fadeOutFeatureTypes.contains(feature.getType())) {
                        flag = false;
                        break;
                    } else {
                        if ((pos + offset) > chromLength) {
                            end = chromLength;
                        }
                        offset++;
                    }
                }
            } else {
                if ((pos + offset) > chromLength) {
                    end = chromLength;
                }
                offset++;
            }
        }
        return feature;
    }

    public List<TranscriptionStart> tssDetermination(StatisticsOnMappingData statistics, int ratio, int chromNo, int chromId, int chromosomLength) {

        List<TranscriptionStart> detectedTss = new ArrayList<>(); // List of detected transcription start sites
        int[][] forward = statistics.getForwardReadStarts(); // Array with startsite count information for forward mapping positions.
        int[][] reverse = statistics.getReverseReadStarts(); // Array with startsite count information for reverse mapping positions.
        double bg = statistics.getBgThreshold(); // Background cutoff
        int f_before; // fwd read stack one pos before 
        int r_before; // rev read stack one pos before
        boolean isFwd; // direction

        for (int i = 0; i < chromosomLength; i++) {
            
            if (forward[chromNo - 1][i] > bg) { // background cutoff is passed
                if (forward[chromNo - 1][i - 1] == 0) {
                    f_before = 1;
                } else {
                    f_before = forward[chromNo - 1][i - 1];
                }

                int f_ratio = (forward[chromNo - 1][i]) / f_before;

                if (f_ratio >= ratio) {
                    isFwd = true;
                    TranscriptionStart ts = new TranscriptionStart(i, isFwd, chromId, this.trackid);
                    ts.setReadStarts(forward[chromNo - 1][i]);
                    detectedTss.add(ts);
                }
            }
            
            if (reverse[chromNo - 1][i] > bg) {
                if (reverse[chromNo - 1][i + 1] == 0) {
                    r_before = 1;
                } else {
                    r_before = reverse[chromNo - 1][i + 1];
                }
                int r_ratio = (reverse[chromNo - 1][i]) / r_before;
                if (r_ratio >= ratio) {
                    isFwd = false;
                    TranscriptionStart ts = new TranscriptionStart(i, isFwd, chromId, this.trackid);
                    ts.setReadStarts(reverse[chromNo - 1][i]);
                    detectedTss.add(ts);
                }
            }
        }
        return detectedTss;
    }

    /**
     * Running the transcription start site detection and classification.
     *
     * @param length Length of the reference genome.
     * @param fwdFeatures CDS information for forward regions in genome.
     * @param revFeatures CDS information for reverse regions in genome.
     * @param allFeatures HashMap with all featureIDs and associated features.
     * @param ratio User given ratio for minimum increase of start counts from
     * pos to pos + 1.
     * @param isLeaderlessDetection true for performing leaderless detection.
     * @param leaderlessRestirction Restriction of bases upstream and
     * downstream.
     * @param isExclusionOfInternalTss true for excluding internal TSS.
     * @param distanceForExcludingTss number restricting the distance between
     * TSS and detected gene.
     */
    public void runningTSSDetection(PersistantChromosome chrom, HashMap<Integer, List<Integer>> fwdFeatures, HashMap<Integer, List<Integer>> revFeatures,
            HashMap<Integer, PersistantFeature> allFeatures, StatisticsOnMappingData statistics, int chromId, int chromNo, int chromosomLength, ParameterSetFiveEnrichedAnalyses parameters) {

        int ratio = parameters.getRatio();
        HashSet<FeatureType> fadeOutFeatureTypes = parameters.getExcludeFeatureTypes();
        int leaderlessRange = parameters.getLeaderlessLimit();
        Integer distanceForExcludingTss = parameters.getExclusionOfTSSDistance();

        boolean isExclusionOfAllIntragenicTss = parameters.isExclusionOfAllIntragenicTSS();
        boolean keepAllIntragenicTss = parameters.isKeepAllIntragenicTss();
        boolean keepOnlyAssignedIntragenicTss = parameters.isKeepOnlyAssignedIntragenicTss();

        int keepingInternalTssDistance = parameters.getKeepIntragenicTssDistanceLimit();

        int[][] forward = statistics.getForwardReadStarts(); // Array with startsite count information for forward mapping positions.
        int[][] reverse = statistics.getReverseReadStarts(); // Array with startsite count information for reverse mapping positions.

        double mm = statistics.getMappingsPerMillion(); // Mappings per Million.
        double bg = statistics.getBgThreshold(); // Background cutoff

        // for determining first and last feature
        int lastFeatureId = determineBiggestId(allFeatures);
        int fstFeatureId = determineSmallestId(allFeatures);

        // settings for checking CDS-shift 
        HashMap<String, StartCodon> validCodons = parameters.getValidStartCodons();
        double relPercentage = (parameters.getCdsShiftPercentage() / 100.0);

        int f_before;
        int r_before;

        for (int i = 0; i < chromosomLength; i++) {
            if ((forward[chromNo - 1][i] > bg) || (reverse[chromNo - 1][i] > bg)) { // background cutoff is passed
                int dist2start = 0;
                int dist2stop = 0;
                boolean leaderless = false;
                boolean cdsShift = false;
                boolean isIntragenic = false;
                boolean isPutAntisense = false;
                int offsetToNextDownstreamFeature = 0;

                if (forward[chromNo - 1][i - 1] == 0) {
                    f_before = 1;
                } else {
                    f_before = forward[chromNo - 1][i - 1];
                }

                if (reverse[chromNo - 1][i + 1] == 0) {
                    r_before = 1;
                } else {
                    r_before = reverse[chromNo - 1][i + 1];
                }

                int f_ratio = (forward[chromNo - 1][i]) / f_before;
                int r_ratio = (reverse[chromNo - 1][i]) / r_before;

                if (f_ratio >= ratio) {
                    boolean isFwd = true;

                    int[] offsetAndArray = determineOffsetInFwdDirection(chromosomLength, fwdFeatures, i);
                    int offset = offsetAndArray[0];
                    int end = offsetAndArray[1];
                    double rel_count = forward[chromNo - 1][i] / mm;

                    // getting the PersistantFeature 
                    PersistantFeature feature = null;

                    boolean flag = true;
//                    // check for overlapping Features
                    while (flag) {
                        if (fwdFeatures.containsKey(i + offset - end)) {
                            for (int id : fwdFeatures.get(i + offset - end)) {
                                feature = allFeatures.get(id);
                                if (feature != null && !fadeOutFeatureTypes.contains(feature.getType())) {
                                    flag = false;
                                    break;
                                } else {
                                    if ((i + offset) > chromosomLength) {
                                        end = chromosomLength;
                                    }
                                    offset++;
                                }
                            }
                        } else {
                            if ((i + offset) > chromosomLength) {
                                end = chromosomLength;
                            }
                            offset++;
                        }
                    }

                    // Case 1: offset is 0
                    // => TSS is whether intragenic, leaderless or represents
                    // a CDS-Shift and can also be located antisense to an annotated feature
                    if (offset == 0) {
                        dist2start = i - feature.getStart();
                        dist2stop = feature.getStop() - i;

                        // check if feture is leaderless (downstream direction)
                        if (dist2start <= leaderlessRange) {
                            leaderless = true;

                            // check if cis antisense
                            if (revFeatures.get(i) != null) {
                                isPutAntisense = true;
                            }

                            cdsShift = checkLeaderlessCdsShift(dist2start, chrom, isFwd, i, validCodons, cdsShift);

                            if (!cdsShift && dist2start > 0) {
                                cdsShift = checkCdsShift(chrom, feature.getStart(), feature.getStop(), i, dist2start, isFwd, relPercentage, validCodons);
                            }

                            TranscriptionStart tss = new TranscriptionStart(i, isFwd, forward[chromNo - 1][i], rel_count,
                                    feature, offset, dist2start, dist2stop, null, offsetToNextDownstreamFeature,
                                    leaderless, cdsShift,
                                    isIntragenic, isPutAntisense, chromId, this.trackid);
                            detectedTSS.add(tss);
                            if (!cdsShift) {
                                fwdFeaturesIds.put(feature.getId(), feature.getLocus());
                            }
                        } else if (dist2start > leaderlessRange && isExclusionOfAllIntragenicTss == false) {
                            // Case 2: Intragenic TSS if exclusion of intragenic TSS is not set to true
                            // here we want to find the next downstream feature 
                            // because the start site is intragenic

                            /**
                             * Getting next Feature
                             */
                            int currentFeatureID = feature.getId();
                            PersistantFeature nextDownstreamFeature = null;
                            flag = true;

                            if (flag) {
                                while (nextDownstreamFeature == null || nextDownstreamFeature.isFwdStrand() == false || fadeOutFeatureTypes.contains(nextDownstreamFeature.getType()) || feature.getLocus().equals(nextDownstreamFeature.getLocus())) {

                                    if (currentFeatureID >= lastFeatureId) {
                                        currentFeatureID = fstFeatureId - 1;
                                        nextDownstreamFeature = allFeatures.get(++currentFeatureID);
                                        if (nextDownstreamFeature != null) {
                                            offsetToNextDownstreamFeature = chromosomLength - i + nextDownstreamFeature.getStart();
                                        }
                                    } else {
                                        nextDownstreamFeature = allFeatures.get(++currentFeatureID);
                                        if (nextDownstreamFeature != null) {
                                            if (i > nextDownstreamFeature.getStart()) {
                                                offsetToNextDownstreamFeature = chromosomLength + nextDownstreamFeature.getStart() - i;
                                            } else {
                                                offsetToNextDownstreamFeature = nextDownstreamFeature.getStart() - i;
                                            }
                                        }
                                    }
                                }
                            }
//                            }
                            // check antisensness
                            if (revFeatures.get(i) != null) {
                                isPutAntisense = true;
                            }

                            if (offsetToNextDownstreamFeature < keepingInternalTssDistance) {
                                // putative the corresponding gene for TSS
                                isIntragenic = true;
                                if (!cdsShift && dist2start >= 3) {
                                    cdsShift = checkCdsShift(chrom, feature.getStart(), feature.getStop(), i, dist2start, isFwd, relPercentage, validCodons);
                                }
                                TranscriptionStart tss = new TranscriptionStart(i, isFwd, forward[chromNo - 1][i], rel_count,
                                        null, offset, dist2start, dist2stop, nextDownstreamFeature, offsetToNextDownstreamFeature,
                                        leaderless, cdsShift,
                                        isIntragenic, isPutAntisense, chromId, this.trackid);
                                detectedTSS.add(tss);
                                fwdFeaturesIds.put(nextDownstreamFeature.getId(), nextDownstreamFeature.getLocus());
                            } else if (keepAllIntragenicTss) {
                                isIntragenic = true;
                                if (!cdsShift && dist2start > 0) {
                                    cdsShift = checkCdsShift(chrom, feature.getStart(), feature.getStop(), i, dist2start, isFwd, relPercentage, validCodons);
                                }
                                TranscriptionStart tss = new TranscriptionStart(i, isFwd, forward[chromNo - 1][i], rel_count,
                                        feature, offset, dist2start, dist2stop, null, offsetToNextDownstreamFeature,
                                        leaderless, cdsShift,
                                        isIntragenic, isPutAntisense, chromId, this.trackid); //TODO: check if it is internal
                                detectedTSS.add(tss);
                            }
                        } else if (dist2start > leaderlessRange && isExclusionOfAllIntragenicTss == true) {
                            // at least test whether CDS-Shift occur or not
                            isIntragenic = true;

                            // check antisensness
                            if (revFeatures.get(i) != null) {
                                isPutAntisense = true;
                            }
                            if (!cdsShift && dist2start > 0) {
                                cdsShift = checkCdsShift(chrom, feature.getStart(), feature.getStop(), i, dist2start, isFwd, relPercentage, validCodons);
                            }
                            if (cdsShift) {
//                                if (validFeatureTypes.contains(feature.getType())) {
                                TranscriptionStart tss = new TranscriptionStart(i, isFwd, forward[chromNo - 1][i], rel_count,
                                        feature, offset, dist2start, dist2stop, null, offsetToNextDownstreamFeature,
                                        leaderless, cdsShift,
                                        isIntragenic, isPutAntisense, chromId, this.trackid); //TODO: check if it is internal
                                detectedTSS.add(tss);
                            }
                        }

                    } else {
                        // leaderless in upstream direction, offset is != 0 but in leaderless range
                        if (offset <= leaderlessRange) {
                            leaderless = true;
                            // check antisensness
                            if (revFeatures.get(i) != null) {
                                isPutAntisense = true;
                            }
                            // check for cdsShift, when offset > and offset+1 mod 3 == 0
                            if (offset > 0 && ((offset + 1) % 3) == 0) {
                                String startAtTSS = getSubSeq(chrom, isFwd, i - 1, i + 2);
                                if (validCodons.containsKey(startAtTSS)) {
                                    cdsShift = true;
                                }
                            }

                            TranscriptionStart tss = new TranscriptionStart(i, isFwd, forward[chromNo - 1][i], rel_count, feature,
                                    offset, dist2start, dist2stop, null, offsetToNextDownstreamFeature, leaderless,
                                    cdsShift, isIntragenic, isPutAntisense, chromId, this.trackid);//TODO: check if it is internal
                            detectedTSS.add(tss);
                            fwdFeaturesIds.put(feature.getId(), feature.getLocus());
                        } else {
                            // checking for "normal" TSS
                            if (offset < distanceForExcludingTss) {
                                // check antisensness
                                if (revFeatures.get(i) != null) {
                                    isPutAntisense = true;
                                }

                                TranscriptionStart tss = new TranscriptionStart(i, isFwd, forward[chromNo - 1][i], rel_count, feature, offset, dist2start, dist2stop, null, offsetToNextDownstreamFeature, leaderless, cdsShift, isIntragenic, isPutAntisense, chromId, this.trackid);
                                detectedTSS.add(tss);
                                fwdOffsets.add(new Integer[]{i, i + offset});
                                fwdTss.put(i, false);
                                fwdFeaturesIds.put(feature.getId(), feature.getLocus());
                            } else {
                                // TSS is too far away from next annotated feature
                                // check only for antisense
                                if (revFeatures.get(i) != null) {
                                    for (int id : revFeatures.get(i)) {
                                        feature = allFeatures.get(id);
                                        if (!fadeOutFeatureTypes.contains(feature.getType())) {
                                            isPutAntisense = true;
                                        }
                                    }
                                }
                                if (isPutAntisense) {
                                    TranscriptionStart tss = new TranscriptionStart(i, isFwd, forward[chromNo - 1][i], rel_count, feature, 0, 0, 0, null, 0,
                                            leaderless, cdsShift, isIntragenic, isPutAntisense, chromId, this.trackid);
                                    tss.setIntragenicAntisense(true);
                                    detectedTSS.add(tss);
                                }

                                // check for 3'utr anisenseness
                                if (isPutAntisense == false) {
                                    offset = 0;
                                    end = 0;
                                    boolean noRevFeatureFlag = true;
                                    while (noRevFeatureFlag) {
                                        if (revFeatures.containsKey(i + offset - end)) {
                                            for (int id : revFeatures.get(i + offset - end)) {
                                                if (!fadeOutFeatureTypes.contains(allFeatures.get(id).getType())) {
                                                    noRevFeatureFlag = false;
                                                    feature = allFeatures.get(id);
                                                }
                                            }

                                        }
                                        if ((i + offset) > chromosomLength) {
                                            end = chromosomLength;
                                        }
                                        offset++;
                                    }
                                    if (offset < parameters.getThreeUtrLimitAntisenseDetection()) {

                                        isPutAntisense = true;
                                        TranscriptionStart tss = new TranscriptionStart(
                                                i, isFwd, forward[chromNo - 1][i], rel_count, feature, 0, 0, 0, null, 0,
                                                leaderless, cdsShift, isIntragenic, isPutAntisense, chromId, this.trackid);
                                        tss.setIs3PrimeUtrAntisense(isPutAntisense);
                                        detectedTSS.add(tss);
                                    }
                                }

                                // determining the offset to next downstream feature
                                // and checking the antisense site for annotated features
                                if (isPutAntisense == false) {
                                    offset = 0;
                                    boolean noRevFeatureFlag = true;
                                    boolean noFwdFeatureFlag = true;
                                    while (noRevFeatureFlag && noFwdFeatureFlag) {
                                        if (revFeatures.containsKey(i + offset - end)) {
                                            for (int id : revFeatures.get(i + offset - end)) {
//                                                    int id = reverseCDSs.get(i + offset - end).get(0);
                                                if (!fadeOutFeatureTypes.contains(allFeatures.get(id).getType())) {
                                                    noRevFeatureFlag = false;
                                                }
                                            }
                                        }
                                        if (fwdFeatures.containsKey(i + offset - end)) {
                                            for (Integer id : fwdFeatures.get(i + offset - end)) {
//                                                int id = forwardCDSs.get(i + offset - end).get(0);
                                                if (!fadeOutFeatureTypes.contains(allFeatures.get(id).getType())) {
                                                    noFwdFeatureFlag = false;
                                                }
                                            }
                                        }
                                        if ((i + offset) > chromosomLength) {
                                            end = chromosomLength;
                                        }
                                        offset++;
                                    }

                                    if (offset > parameters.getThreeUtrLimitAntisenseDetection()) {
                                        TranscriptionStart tss = new TranscriptionStart(
                                                i, isFwd, forward[chromNo - 1][i], rel_count, null, 0, 0, 0, null, 0,
                                                leaderless, cdsShift, isIntragenic, isPutAntisense, chromId, this.trackid);
                                        tss.setIntergenicTSS(true);
                                        detectedTSS.add(tss);
                                    }
                                }
                            }
                        }
                    }
                }

                if (r_ratio >= ratio) {
                    boolean isFwd = false;
                    int offset = 0;
                    int end = 0;

                    // determining the offset to feature
                    while (!revFeatures.containsKey(end + i - offset)) {
                        if ((i - offset) == 0) {
                            end = chromosomLength;
                        }
                        offset++;
                    }

                    double rel_count = reverse[chromNo - 1][i] / mm;

                    PersistantFeature feature = null;

                    boolean flag = true;
//                    // check for overlapping Features
                    while (flag) {
                        if (revFeatures.containsKey(end + i - offset)) {
                            for (int id : revFeatures.get(end + i - offset)) {
                                feature = allFeatures.get(id);
                                if (flag && feature != null && !fadeOutFeatureTypes.contains(feature.getType())) {
                                    flag = false;
                                    break;
                                } else {
                                    if ((i - offset) == 0) {
                                        end = chromosomLength;
                                    }
                                    offset++;
                                }
                            }
                        } else {
                            if ((i - offset) == 0) {
                                end = chromosomLength;
                            }
                            offset++;
                        }
                    }

                    if (offset == 0) {
                        dist2start = feature.getStop() - i;
                        dist2stop = i - feature.getStart();

                        // check if leaderless (downstream)
                        if (dist2start <= leaderlessRange) {
                            leaderless = true;
                            // check antisensness
                            if (fwdFeatures.get(i) != null) {
                                isPutAntisense = true;
                            }

                            // check for cdsShift when offset is 0 and distance2Start > 0 and dist2Start mod 3 == 0
//                            cdsShift = checkCdsShift(chrom, feature.getStart(), feature.getStop(), i, dist2start, isFwd, relPercentage, validCodons);
                            if (dist2start > 0 && (dist2start % 3) == 0) {
                                String startAtTSSRev = getSubSeq(chrom, isFwd, i - 3, i);
                                String complement = complement(startAtTSSRev);
                                if (validCodons.containsKey(complement)) {
                                    cdsShift = true;
                                }
                            }

                            if (!cdsShift && dist2start > 0) {
                                cdsShift = checkCdsShift(chrom, feature.getStart(), feature.getStop(), i, dist2start, isFwd, relPercentage, validCodons);
                            }

                            TranscriptionStart tss = new TranscriptionStart(i, isFwd, reverse[chromNo - 1][i], rel_count, feature, offset, dist2start, dist2stop, null, offsetToNextDownstreamFeature,
                                    leaderless, cdsShift, isIntragenic, isPutAntisense, chromId, this.trackid);
                            detectedTSS.add(tss);
                            if (!cdsShift) {
                                revFeaturesIds.put(feature.getId(), feature.getLocus());
                            }
                        }

                        if (dist2start > leaderlessRange && isExclusionOfAllIntragenicTss == false) {
                            int currentFeatureID = feature.getId();
                            PersistantFeature nextFeature = null;

                            flag = true;

                            if (flag) {
                                while (nextFeature == null || feature.getLocus().equals(nextFeature.getLocus()) || fadeOutFeatureTypes.contains(nextFeature.getType()) || nextFeature.isFwdStrand() == true) {

                                    if (currentFeatureID <= fstFeatureId) {
                                        currentFeatureID = lastFeatureId + 1;
                                        nextFeature = allFeatures.get(--currentFeatureID);
                                        if (nextFeature != null) {
                                            offsetToNextDownstreamFeature = chromosomLength - nextFeature.getStop() + i;
                                        }
                                    } else {
                                        nextFeature = allFeatures.get(--currentFeatureID);
                                        if (nextFeature != null) {
                                            if (nextFeature.getStop() > i) {
                                                offsetToNextDownstreamFeature = chromosomLength - nextFeature.getStop() + i;
                                            } else {
                                                offsetToNextDownstreamFeature = i - nextFeature.getStop();
                                            }
                                        }
                                    }
                                }
                            }
                            // check antisensness
                            if (fwdFeatures.get(i) != null) {
                                isPutAntisense = true;
                            }

                            if (offsetToNextDownstreamFeature < keepingInternalTssDistance) {
                                isIntragenic = true;
                                if (!cdsShift && dist2start >= 3) {
                                    cdsShift = checkCdsShift(chrom, feature.getStart(), feature.getStop(), i, dist2start, isFwd, relPercentage, validCodons);
                                }
                                // puttative nextgene
                                TranscriptionStart tss = new TranscriptionStart(i, isFwd, reverse[chromNo - 1][i], rel_count,
                                        null, offset, dist2start, dist2stop, nextFeature, offsetToNextDownstreamFeature, leaderless, cdsShift,
                                        isIntragenic, isPutAntisense, chromId, this.trackid); //TODO: check if it is internal
                                detectedTSS.add(tss);
                                revFeaturesIds.put(nextFeature.getId(), nextFeature.getLocus());
                            } else if (keepAllIntragenicTss) {
                                if (!cdsShift && dist2start > 0) {
                                    cdsShift = checkCdsShift(chrom, feature.getStart(), feature.getStop(), i, dist2start, isFwd, relPercentage, validCodons);
                                }
                                isIntragenic = true;
                                TranscriptionStart tss = new TranscriptionStart(i, isFwd, reverse[chromNo - 1][i], rel_count,
                                        feature, offset, dist2start, dist2stop, null, offsetToNextDownstreamFeature, leaderless, cdsShift,
                                        isIntragenic, isPutAntisense, chromId, this.trackid);
                                detectedTSS.add(tss);
                            }
                        } else if (dist2start > leaderlessRange && isExclusionOfAllIntragenicTss == true) {
                            // at least test if CDS-Shift occur or not
                            isIntragenic = true;
                            // check antisensness
                            if (fwdFeatures.get(i) != null) {
                                isPutAntisense = true;
                            }
                            if (!cdsShift && dist2start > 0) {
                                cdsShift = checkCdsShift(chrom, feature.getStart(), feature.getStop(), i, dist2start, isFwd, relPercentage, validCodons);
                            }
                            if (cdsShift) {
                                TranscriptionStart tss = new TranscriptionStart(i, isFwd, reverse[chromNo - 1][i], rel_count,
                                        feature, offset, dist2start, dist2stop, null, offsetToNextDownstreamFeature, leaderless, cdsShift,
                                        isIntragenic, isPutAntisense, chromId, this.trackid);
                                detectedTSS.add(tss);
                            }
                        }
                    } else {
                        if (offset <= leaderlessRange) {

                            // check antisensness
                            if (fwdFeatures.get(i) != null) {
                                isPutAntisense = true;
                            }
                            // Leaderless TSS upstream
                            leaderless = true;
                            // check for cdsShift when offset is 0 and distance2Start > 0 and mod 3 == 0
//                            cdsShift = checkCdsShift(chrom, feature.getStart(), feature.getStop(), i, dist2start, isFwd, relPercentage, validCodons);
                            if (dist2start > 0 && (dist2start % 3) == 0) {
                                String startAtTSSRev = getSubSeq(chrom, isFwd, i - 3, i);
                                String complement = complement(startAtTSSRev);
                                if (validCodons.containsKey(complement)) {
                                    cdsShift = true;
                                }
                            }

                            TranscriptionStart tss = new TranscriptionStart(i, isFwd, reverse[chromNo - 1][i], rel_count, feature, offset, dist2start, dist2stop, null, offsetToNextDownstreamFeature,
                                    leaderless, cdsShift, isIntragenic, isPutAntisense, chromId, this.trackid);
                            detectedTSS.add(tss);
                            revFeaturesIds.put(feature.getId(), feature.getLocus());
                        } else {
                            // bigger Leaderless restriction
                            // "normal" TSS
                            if (offset < distanceForExcludingTss) {

                                // check antisensness
                                if (fwdFeatures.get(i) != null) {
                                    isPutAntisense = true;
                                }
                                TranscriptionStart tss = new TranscriptionStart(i, isFwd, reverse[chromNo - 1][i], rel_count,
                                        feature, offset, dist2start, dist2stop, null, offsetToNextDownstreamFeature, leaderless, cdsShift,
                                        isIntragenic, isPutAntisense, chromId, this.trackid);
                                revOffsets.add(new Integer[]{i - offset, i});
                                revTss.put(i, false);
                                detectedTSS.add(tss);
                                revFeaturesIds.put(feature.getId(), feature.getLocus());
                            } else {
                                // check only for antisense
                                if (fwdFeatures.get(i) != null) {
                                    for (int id : fwdFeatures.get(i)) {
                                        feature = allFeatures.get(id);
                                        if (!fadeOutFeatureTypes.contains(feature.getType())) {
                                            isPutAntisense = true;
                                        }
                                    }
                                    if (isPutAntisense) {
                                        TranscriptionStart tss = new TranscriptionStart(i, isFwd, reverse[chromNo - 1][i], rel_count, feature, 0, 0, 0, null, 0,
                                                leaderless, cdsShift, isIntragenic, isPutAntisense, chromId, this.trackid);
                                        tss.setIntragenicAntisense(true);
                                        detectedTSS.add(tss);
                                    }
                                }

                                // check for 3'utr anisenseness
                                if (isPutAntisense == false) {
                                    offset = 0;
                                    end = 0;
                                    boolean noFwdFeatureFlag = true;
                                    while (noFwdFeatureFlag) {
                                        if (fwdFeatures.containsKey(end + i - offset)) {
                                            for (Integer id : fwdFeatures.get(end + i - offset)) {
//                                                int id = forwardCDSs.get(end + i - offset).get(0);
                                                if (!fadeOutFeatureTypes.contains(allFeatures.get(id).getType())) {
                                                    noFwdFeatureFlag = false;
                                                    feature = allFeatures.get(id);
                                                }
                                            }
                                        }
                                        if ((i - offset) == 0) {
                                            end = chromosomLength;
                                        }
                                        offset++;
                                    }
                                    if (offset < parameters.getThreeUtrLimitAntisenseDetection()) {
//                                        if (validFeatureTypes.contains(feature.getType())) {
                                        isPutAntisense = true;
                                        TranscriptionStart tss = new TranscriptionStart(i, isFwd, reverse[chromNo - 1][i], rel_count, feature, 0, 0, 0, null, 0,
                                                leaderless, cdsShift, isIntragenic, isPutAntisense, chromId, this.trackid);
                                        tss.setIs3PrimeUtrAntisense(isPutAntisense);
                                        detectedTSS.add(tss);
                                    }
                                }
                                // determining the offset to next downstream feature
                                if (isPutAntisense == false) {
                                    offset = 0;

                                    boolean noRevFeatureFlag = true;
                                    boolean noFwdFeatureFlag = true;
                                    while (noRevFeatureFlag && noFwdFeatureFlag) {
                                        if (revFeatures.containsKey(end + i - offset)) {
                                            for (Integer id : revFeatures.get(end + i - offset)) {
                                                if (!fadeOutFeatureTypes.contains(allFeatures.get(id).getType())) {
                                                    noRevFeatureFlag = false;
                                                }
                                            }
                                        }
                                        if (fwdFeatures.containsKey(end + i - offset)) {
                                            for (Integer id : fwdFeatures.get(end + i - offset)) {
                                                if (!fadeOutFeatureTypes.contains(allFeatures.get(id).getType())) {
                                                    noFwdFeatureFlag = false;
                                                }
                                            }
                                        }
                                        if ((i - offset) == 0) {
                                            end = chromosomLength;
                                        }
                                        offset++;
                                    }
                                    if (offset > parameters.getThreeUtrLimitAntisenseDetection()) {
                                        TranscriptionStart tss = new TranscriptionStart(i, isFwd, reverse[chromNo - 1][i], rel_count, null, 0, 0, 0, null, 0,
                                                leaderless, cdsShift, isIntragenic, isPutAntisense, chromId, this.trackid);
                                        tss.setIntergenicTSS(true);
                                        detectedTSS.add(tss);
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }

        List<TranscriptionStart> removeList = new ArrayList<>();
        // running additional 5'-UTR Antisense detection
        for (int i = 0; i < fwdOffsets.size(); i++) {
            Integer[] offset = fwdOffsets.get(i);
            int j = offset[0];
            int k = offset[1];
            for (; j < k; j++) {
                if (revTss.containsKey(j)) {
                    revTss.put(j, true);
                }
            }
        }

        for (int i = 0; i < revOffsets.size(); i++) {
            Integer[] offset = revOffsets.get(i);
            int j = offset[0];
            int k = offset[1];
            for (; j < k; j++) {
                if (fwdTss.containsKey(j)) {
                    fwdTss.put(j, true);
                }
            }
        }

        for (TranscriptionStart transcriptionStart : detectedTSS) {
            int start = transcriptionStart.getStartPosition();
            boolean isFwd = transcriptionStart.isFwdStrand();
            if (isFwd) {
                if (fwdTss.containsKey(start)) {
                    if (fwdTss.get(start) == true) {
                        transcriptionStart.setPutativeAntisense(true);
                        transcriptionStart.setIs5PrimeUtrAntisense(true);
                    }
                }
            } else {
                if (revTss.containsKey(start)) {
                    if (revTss.get(start) == true) {
                        transcriptionStart.setPutativeAntisense(true);
                        transcriptionStart.setIs5PrimeUtrAntisense(true);
                    }
                }
            }

            // Features with assigned stable RNA are flagged as assigned to stable RNA
            if (transcriptionStart.getAssignedFeature() != null) {
                if (transcriptionStart.getAssignedFeature().getType() == FeatureType.RRNA || transcriptionStart.getAssignedFeature().getType() == FeatureType.TRNA) {
                    transcriptionStart.setAssignedToStableRNA(true);
                    transcriptionStart.setAssignedFeatureType(transcriptionStart.getAssignedFeature().getType());
                }
            }

            /**
             * Running postprocessing of cds-shifts. All tss marked as putative
             * cds-shifts are only a valid putative cds-shift, if and only if
             * the feature has not alyready a TSS asigned.
             */
//        for (TranscriptionStart ts : detectedTSS) {
//            if (ts.isCdsShift() && !ts.isLeaderless()) {
            if (transcriptionStart.isCdsShift()) {
                int featureID = transcriptionStart.getAssignedFeature().getId();

                if (fwdFeaturesIds.containsKey(featureID) || revFeaturesIds.containsKey(featureID)) {
                    transcriptionStart.setCdsShift(false);
                    transcriptionStart.setIntragenicTSS(true);
                }
            }
//        }
        }

    }

    private boolean checkLeaderlessCdsShift(int dist2start, PersistantChromosome chrom, boolean isFwd, int i, HashMap<String, StartCodon> validCodons, boolean cdsShift) {
        // check for cdsShift, when offset > and offset+1 mod 3 == 0
        if (dist2start > 0 && (dist2start % 3) == 0) {
            String startAtTSS = getSubSeq(chrom, isFwd, i - 1, i + 2);
            if (validCodons.containsKey(startAtTSS)) {
                cdsShift = true;
            }
        }
        return cdsShift;
    }

    /**
     * Method for checking a potential CDS-Shift.
     *
     * @param chrom Current processing PersistantChromosome.
     * @param featureStart Start position of PersistantFeature.
     * @param featureStop Stop position of PersistantFeature.
     * @param tssStart Start position of transcription start site.
     * @param dist2start Distance from transcription start site to start
     * position of PersistantFeature.
     * @param isFwd true if is forward direction.
     * @param relPercentage
     * @param validStartCodons Map of Codons, which has to be validated.
     *
     * @return true, if putative CDS-Shift occur.
     */
    private boolean checkCdsShift(PersistantChromosome chrom, int featureStart,
            int featureStop, int tssStart, int dist2start, boolean isFwd,
            double relPercentage, HashMap<String, StartCodon> validStartCodons) {
        double length = featureStop - featureStart;
        double partOfFeature = dist2start / length;
        int a = 1;
        int b = 2;
        int c = 3;
        int startFwd = featureStart - a + 3;
        int stopFwd = featureStart + b + 3;
        int startRev = featureStop - c - 3;
        int stopRev = featureStop - 3;

        if (partOfFeature <= relPercentage) {
            //1. is tss in the range of X % of the feature

            if (isFwd) {
                while (partOfFeature <= relPercentage) {
                    String startAtTSS = getSubSeq(chrom, isFwd, startFwd, stopFwd);
                    if (validStartCodons.containsKey(startAtTSS) || startFwd >= tssStart) {
                        return true;
                    }
                    dist2start = startFwd - featureStart;
                    partOfFeature = dist2start / length;
                    startFwd += 3;
                    stopFwd += 3;
                }
            } else {
                while (partOfFeature <= relPercentage) {
                    String startAtTSSRev = getSubSeq(chrom, isFwd, startRev, stopRev);
                    String complement = complement(startAtTSSRev);
                    if (validStartCodons.containsKey(complement) && startRev <= tssStart) {
                        return true;

                    }

                    dist2start = featureStop - startRev;
                    partOfFeature = dist2start / length;
                    startRev -= 3;
                    stopRev -= 3;
                }
            }
        }

        return false;
    }

    /**
     * Gets a DNA String and complements it. A to T, T to A, G to C and C to G.
     *
     * @param seq is DNA String consisting of A,C,G and T.
     * @return the compliment of seq.
     */
    private String complement(String seq) {
        char BASE_A = 'A';
        char BASE_C = 'C';
        char BASE_G = 'G';
        char BASE_T = 'T';
        String a = "A";
        String c = "C";
        String g = "G";
        String t = "T";
        String compliment = "";

        for (int i = 0; i < seq.length(); i++) {
            if (BASE_A == seq.charAt(i)) {
                compliment = compliment.concat(t);
            } else if (BASE_C == (seq.charAt(i))) {
                compliment = compliment.concat(g);

            } else if (BASE_G == seq.charAt(i)) {
                compliment = compliment.concat(c);

            } else if (BASE_T == seq.charAt(i)) {
                compliment = compliment.concat(a);
            }
        }
        return compliment;
    }

    /**
     * If the direction is reverse, the subsequence will be inverted.
     *
     * @param isFwd direction of sequence.
     * @param start start of subsequence.
     * @param stop stop of subsequence.
     * @return the subsequence.
     */
    private String getSubSeq(PersistantChromosome chrom, boolean isFwd, int start, int stop) {

        String seq = "";
        if (start > 0 && stop < chrom.getLength()) {
            seq = refGenome.getChromSequence(chrom.getId(), start, stop);
        }
        if (isFwd) {
            return seq;
        } else {
            String reversedSeq = new StringBuffer(seq).reverse().toString();
            return reversedSeq;
        }
    }

    @Override
    public void update(Object args) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TranscriptionStart> getResults() {
        return detectedTSS;
    }

    /**
     * Determines the biggest Feature id in a map of PersistantFeatures, whereby
     * the Key is the featureID.
     *
     * @param features HashMap: Key => FeatureID, Value => PersistantFeatures.
     * @return biggest FeatureID.
     */
    private int determineBiggestId(HashMap<Integer, PersistantFeature> features) {
        int result = 0;

        for (PersistantFeature persistantFeature : features.values()) {
            if (persistantFeature.getId() > result) {
                result = persistantFeature.getId();
            }
        }

        return result;
    }
                "Gesamtanzahl von TSS:\t" + this.totalCntOfTss
    /**
     * Determines the smallest FeatureID from a Map with PersistantFeatures.
     *
     * @param features Map: Key => FeatureID, Value => PersistantFeature.
     * @return smallest FeatureID.
     */
    private int determineSmallestId(HashMap<Integer, PersistantFeature> features) {
        int result = 0;
        for (PersistantFeature persistantFeature : features.values()) {
            result = persistantFeature.getId();
            break;
        }

        for (PersistantFeature persistantFeature : features.values()) {
            if (persistantFeature.getId() < result) {
                result = persistantFeature.getId();
            }
        }

        return result;
    }

    /**
     *
     * @param chromLength length of the current chromosome.
     * @param fwdFeatures all forwatd features.
     * @param currentPos current position in chromosome.
     * @return the offset to next upstream feature.
     */
    private int[] determineOffsetInFwdDirection(int chromLength, HashMap<Integer, List<Integer>> fwdFeatures, int currentPos) {
        int offset = 0;
        int end = 0;
        int[] result = new int[2];

        // determining the offset to next downstream feature
        while (!fwdFeatures.containsKey(currentPos + offset - end)) {
            if ((currentPos + offset) > chromLength) {
                end = chromLength;
            }
            offset++;
        }

        result[0] = offset;
        result[1] = end;
        return result;
    }

    private int determineOffsetInRevDirection(int chromLength, HashMap<Integer, List<Integer>> revFeatures, int currentPos) {
        int offset = 0;
        int end = 0;

        return offset;
    }

}
