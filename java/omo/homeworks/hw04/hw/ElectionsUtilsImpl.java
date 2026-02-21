package cz.cvut.fel.omo.hw.functions.utils;

import cz.cvut.fel.omo.hw.functions.data.model.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ElectionsUtilsImpl implements ElectionsUtils {

    private final CompletableFuture<List<RegionResults>> regionResultsFuture;
    private final CompletableFuture<AbroadResults> abroadResultsFuture;

    @Override
    public List<Vote> getAllVotes() {
        try {
            return Stream.concat(
                    regionResultsFuture.get().stream()
                            .flatMap(rr -> rr.getRegions().stream())
                            .flatMap(region -> region.getDistricts().stream())
                            .flatMap(district -> district.getCities().stream())
                            .flatMap(city -> city.getVotes().stream()),
                    abroadResultsFuture.get().getAbroad().getContinents().stream()
                            .flatMap(continent -> continent.getCountries().stream())
                            .flatMap(country -> country.getVotes().stream())
            ).collect(Collectors.toList());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }


    @Override
    public List<VoterTurnout> getAllVoterTurnouts() {
        try {
            return Stream.concat(
                    regionResultsFuture.get().stream()
                            .flatMap(rr -> rr.getRegions().stream())
                            .flatMap(region -> region.getDistricts().stream())
                            .flatMap(district -> district.getCities().stream())
                            .map(City::getVoterTurnout),
                    abroadResultsFuture.get().getAbroad().getContinents().stream()
                            .flatMap(continent -> continent.getCountries().stream())
                            .map(Country::getVoterTurnout)
            ).collect(Collectors.toList());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

}
