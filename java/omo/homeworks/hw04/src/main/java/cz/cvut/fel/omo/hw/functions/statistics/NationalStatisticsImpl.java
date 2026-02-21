package cz.cvut.fel.omo.hw.functions.statistics;

import cz.cvut.fel.omo.hw.functions.data.model.*;
import cz.cvut.fel.omo.hw.functions.utils.CandidateUtils;
import cz.cvut.fel.omo.hw.functions.utils.CandidateUtilsImpl;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class NationalStatisticsImpl implements NationalStatistics {

    private final CompletableFuture<List<RegionResults>> regionResults;
    private final CandidateUtils candidateUtils;

    public NationalStatisticsImpl(CompletableFuture<List<RegionResults>> regionResults, CompletableFuture<Candidates> candidates) {
        this.regionResults = regionResults;
        this.candidateUtils = new CandidateUtilsImpl(candidates);
    }

    @Override
    public String getNameOfCityWithTheLowestVoterTurnout() {
        try {
            return regionResults.get().stream()
                    .flatMap(regionResults -> regionResults.getRegions().stream())
                    .flatMap(region -> region.getDistricts().stream())
                    .flatMap(district -> district.getCities().stream())
                    .min(Comparator.comparingDouble(city ->
                            city.getVoterTurnout().getNumberOfIssuedVotingEnvelopes() * 100.0 /
                                    city.getVoterTurnout().getNumberOfRegisteredVoters()
                    )).map(City::getName).orElse("");
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<String> getTop10CitiesWhereCandidateWonOrderedByNumberOfVotesDesc(int candidateId) {
        try {
            return regionResults.get().stream()
                    .flatMap(rr -> rr.getRegions().stream())
                    .flatMap(region -> region.getDistricts().stream())
                    .flatMap(district -> district.getCities().stream())
                    .filter(city -> city.getVotes().stream()
                            .filter(v -> v.getCandidateId() == candidateId)
                            .mapToInt(Vote::getVotes).sum() ==
                            city.getVotes().stream().mapToInt(Vote::getVotes).max().orElse(0) &&
                            city.getVotes().stream().filter(v -> v.getCandidateId() == candidateId)
                                    .mapToInt(Vote::getVotes).sum() > 0)
                    .map(city -> new AbstractMap.SimpleEntry<>(city.getName(),
                            city.getVotes().stream().filter(v -> v.getCandidateId() == candidateId)
                                    .mapToInt(Vote::getVotes).sum()))
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .limit(10)
                    .map(Map.Entry::getKey)
                    .toList();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Map<String, String> getRegionWinnerMap() {
        try {
            return regionResults.get().stream()
                    .flatMap(regionResults -> regionResults.getRegions().stream())
                    .collect(Collectors.toMap(
                            Region::getName,
                            region -> candidateUtils.getCandidateFullName(
                                    region.getDistricts().stream()
                                            .flatMap(district -> district.getCities().stream())
                                            .flatMap(city -> city.getVotes().stream())
                                            .collect(Collectors.groupingBy(Vote::getCandidateId, Collectors.summingInt(Vote::getVotes)))
                                            .entrySet().stream()
                                            .max(Map.Entry.comparingByValue())
                                            .map(Map.Entry::getKey)
                                            .orElse(0)
                            ).orElse("")
                    ));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

}
