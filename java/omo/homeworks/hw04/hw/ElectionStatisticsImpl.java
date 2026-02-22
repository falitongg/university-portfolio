package cz.cvut.fel.omo.hw.functions.statistics;

import cz.cvut.fel.omo.hw.functions.data.model.*;
import cz.cvut.fel.omo.hw.functions.utils.CandidateUtils;
import cz.cvut.fel.omo.hw.functions.utils.CandidateUtilsImpl;
import cz.cvut.fel.omo.hw.functions.utils.ElectionsUtils;
import cz.cvut.fel.omo.hw.functions.utils.ElectionsUtilsImpl;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ElectionStatisticsImpl implements ElectionStatistics {
    private final ElectionsUtils electionsUtils;
    private final CandidateUtils candidateUtils;

    public ElectionStatisticsImpl(CompletableFuture<List<RegionResults>> regionResultsFuture,
                                  CompletableFuture<AbroadResults> abroadResultsFuture,
                                  CompletableFuture<Candidates> candidatesFuture) {
        this.candidateUtils = new CandidateUtilsImpl(candidatesFuture);
        this.electionsUtils = new ElectionsUtilsImpl(regionResultsFuture, abroadResultsFuture);
    }

    @Override
    public int getTotalValidVotes() {
        try {
            return electionsUtils.getAllVotes().stream().mapToInt(Vote::getVotes).sum();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public int getTotalInvalidVotes() {
        try {
            return electionsUtils.getAllVoterTurnouts().stream()
                    .mapToInt(t -> t.getNumberOfSubmittedVotingEnvelopes() - t.getNumberOfValidVotes()).sum();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public int getTotalVoterCount() {
        try {
            return electionsUtils.getAllVoterTurnouts().stream()
                    .mapToInt(VoterTurnout::getNumberOfRegisteredVoters).sum();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public int getTotalIssuedEnvelopes() {
        try {
            return electionsUtils.getAllVoterTurnouts().stream()
                    .mapToInt(VoterTurnout::getNumberOfIssuedVotingEnvelopes).sum();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public double getTotalVoterTurnout() {
        try {
            return getTotalIssuedEnvelopes() * 100.0 / getTotalVoterCount();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Map<String, Integer> getCandidateVotesMap() {
        try {
            return electionsUtils.getAllVotes().stream()
                    .collect(Collectors.groupingBy(
                            v -> candidateUtils.getCandidateFullName(v.getCandidateId()).orElse(""),
                            Collectors.summingInt(Vote::getVotes)));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Map<String, Double> getCandidateVotesPercentMap() {
        try {
            int totalVotes = getTotalValidVotes();
            return getCandidateVotesMap().entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            e -> e.getValue() * 100.0 / totalVotes));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String getCandidatesByVotesDesc() {
        Map<String, Double> percentMap = getCandidateVotesPercentMap();
        return getCandidateVotesMap().entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(e -> String.format("%s (%.2f%%)", e.getKey(), percentMap.get(e.getKey())))
                .collect(Collectors.joining(", "));
    }
}
