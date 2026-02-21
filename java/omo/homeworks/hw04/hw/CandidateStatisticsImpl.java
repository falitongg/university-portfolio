package cz.cvut.fel.omo.hw.functions.statistics;

import cz.cvut.fel.omo.hw.functions.data.model.Candidate;
import cz.cvut.fel.omo.hw.functions.data.model.Candidates;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CandidateStatisticsImpl implements CandidateStatistics {

    private final CompletableFuture<Candidates> candidates;

    @Override
    public double getAverageAge() {
        try {
            return candidates.get().getCandidatesList().stream().mapToInt(Candidate::getAge).average().orElse(0.0);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String getOldestCandidateName() {
        try{
            return candidates.get().getCandidatesList().stream().max(Comparator.comparingInt(Candidate::getAge)).map(Candidate::getFullName).orElse("");
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Map<String, Integer> getCandidateAgeMap() {
        try{
            return candidates.get().getCandidatesList().stream().collect(Collectors.toMap(Candidate::getFullName, Candidate::getAge));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
