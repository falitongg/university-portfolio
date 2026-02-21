package cz.cvut.fel.omo.hw.functions.statistics;

import cz.cvut.fel.omo.hw.functions.data.model.*;
import cz.cvut.fel.omo.hw.functions.utils.CandidateUtils;
import cz.cvut.fel.omo.hw.functions.utils.CandidateUtilsImpl;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class AbroadStatisticsImpl implements AbroadStatistics {
    private final CompletableFuture<AbroadResults> abroadResults;
    private final CandidateUtils candidateUtils;

    public AbroadStatisticsImpl(CompletableFuture<AbroadResults> abroadResults,
                                CompletableFuture<Candidates> candidates) {
        this.abroadResults = abroadResults;
        this.candidateUtils = new CandidateUtilsImpl(candidates);
    }

    @Override
    public String getNameOfCountryWithTheHighestNonValidVotesRatio() {
        try {
            return abroadResults.get().getAbroad().getContinents().stream()
                    .flatMap(continent -> continent.getCountries().stream())
                    .max(Comparator.comparingDouble(c ->
                            c.getVoterTurnout().getNumberOfSubmittedVotingEnvelopes() > 0
                                    ? (c.getVoterTurnout().getNumberOfSubmittedVotingEnvelopes() -
                                    c.getVoterTurnout().getNumberOfValidVotes()) * 100.0 /
                                    c.getVoterTurnout().getNumberOfSubmittedVotingEnvelopes()
                                    : 0.0
                    )).map(Country::getName).orElse("");
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Map<String, List<String>> getCandidateVictoryCountryMap() {
        try {
            return abroadResults.get().getAbroad().getContinents().stream()
                    .flatMap(continent -> continent.getCountries().stream())
                    .flatMap(country -> {
                        int maxVotes = country.getVotes().stream()
                                .mapToInt(Vote::getVotes).max().orElse(0);
                        return country.getVotes().stream()
                                .filter(v -> v.getVotes() == maxVotes)
                                .map(Vote::getCandidateId)
                                .map(id -> candidateUtils.getCandidateFullName(id).orElse("Unknown"))
                                .map(name -> new AbstractMap.SimpleEntry<>(name, country.getName()));
                    })
                    .collect(Collectors.groupingBy(
                            Map.Entry::getKey,
                            Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                    ));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Map<String, Integer> getContinentRegisteredVoterCountMap() {
        try {
            return abroadResults.get().getAbroad().getContinents().stream()
                    .collect(Collectors.toMap(Continent::getName,
                            continent -> continent.getCountries().stream()
                                    .mapToInt(c -> c.getVoterTurnout().getNumberOfRegisteredVoters()).sum()
                    ));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String getNameOfCountryWithMostRegisteredVoters() {
        try {
            return abroadResults.get().getAbroad().getContinents().stream()
                    .flatMap(continent -> continent.getCountries().stream())
                    .max(Comparator.comparingInt(c -> c.getVoterTurnout().getNumberOfRegisteredVoters()))
                    .map(Country::getName).orElse("");
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
