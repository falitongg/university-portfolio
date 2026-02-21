package cz.cvut.fel.omo.hw.functions.statistics;

import cz.cvut.fel.omo.hw.functions.data.DataLoader;
import cz.cvut.fel.omo.hw.functions.data.DatasetEnum;
import cz.cvut.fel.omo.hw.functions.data.OfflineDataLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

class AbroadStatisticsImplTest {

    private static AbroadStatistics abroadStatistics;

    @BeforeAll
    static void setup() {
        DataLoader dataLoader = new OfflineDataLoader();
        abroadStatistics = new AbroadStatisticsImpl(dataLoader.getAbroadResults(DatasetEnum.DATASET_2023_2ND_ROUND), dataLoader.getCandidates(DatasetEnum.DATASET_2023_2ND_ROUND));
        // test results source: https://www.volby.cz/pls/prez2023nss/pe2?xjazyk=CZ
    }

    @Test
    void getNameOfCountryWithTheHighestNonValidVotesRatio() {
        Assertions.assertEquals("Mongolsko", abroadStatistics.getNameOfCountryWithTheHighestNonValidVotesRatio());
    }

    @Test
    void getCandidateVictoryCountryMap() {
        Map<String, List<String>> candidateVictoryCountryMap = abroadStatistics.getCandidateVictoryCountryMap();
        Assertions.assertEquals(1, candidateVictoryCountryMap.get("Andrej Babiš").size());
        Assertions.assertEquals(92, candidateVictoryCountryMap.get("Petr Pavel").size());
    }

    @Test
    void equalVoteCountIsWinForBothCandidates() {
        Map<String, List<String>> candidateVictoryCountryMap = abroadStatistics.getCandidateVictoryCountryMap();
        Assertions.assertTrue(candidateVictoryCountryMap.get("Andrej Babiš").contains("Rusko"), "Both candidates have equal vote count so both should be winners");
        Assertions.assertTrue(candidateVictoryCountryMap.get("Petr Pavel").contains("Rusko"), "Both candidates have equal vote count so both should be winners");
    }

    @Test
    void getContinentRegisteredVoterCountMap() {
        Map<String, Integer> continentRegisteredVoterCountMap = abroadStatistics.getContinentRegisteredVoterCountMap();
        Assertions.assertEquals(2487, continentRegisteredVoterCountMap.get("Asie"));
        Assertions.assertEquals(21934, continentRegisteredVoterCountMap.get("Evropa"));
        Assertions.assertEquals(440, continentRegisteredVoterCountMap.get("Afrika"));
    }

    @Test
    void getNameOfCountryWithMostRegisteredVoters() {
        Assertions.assertEquals("Německo", abroadStatistics.getNameOfCountryWithMostRegisteredVoters());
    }
}