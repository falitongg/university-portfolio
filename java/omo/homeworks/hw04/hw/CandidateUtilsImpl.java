package cz.cvut.fel.omo.hw.functions.utils;

import cz.cvut.fel.omo.hw.functions.data.model.Candidate;
import cz.cvut.fel.omo.hw.functions.data.model.Candidates;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor
public class CandidateUtilsImpl implements CandidateUtils {

    private final CompletableFuture<Candidates> candidates;

    @Override
    public Optional<String> getCandidateFullName(int id) {
        try{
            return candidates.get().getCandidatesList().stream().filter(candidate -> candidate.getId() == id).map(Candidate::getFullName).findFirst();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<Integer> getCandidateAge(int id) {
        try{
            return candidates.get().getCandidatesList().stream().filter(candidate -> candidate.getId() == id).map(Candidate::getAge).findFirst();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public <T> Optional<T> getCandidateAttribute(int id, Function<Candidate, T> mappingFunction) {
        try{
            return candidates.get().getCandidatesList().stream().filter(candidate -> candidate.getId() == id).map(mappingFunction).findFirst();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<Candidate> getCandidate(int id) {
        try{
            return candidates.get().getCandidatesList().stream().filter(candidate -> candidate.getId() == id).findFirst();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
