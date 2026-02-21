package cz.cvut.fel.omo.hw.functions.statistics;

import java.util.List;
import java.util.Map;

public interface AbroadStatistics {

    /**
     * Constructs a map with name of the candidate as a key and list of countries where the candidate won as a value.
     *
     * @return a map with candidate name -> list of won countries, for example "Andrej Babiš" => {}.
     */
    Map<String, List<String>> getCandidateVictoryCountryMap();

    /**
     * Constructs a map with name of the continent as a key and number of registered voters.
     * This statistics shows number of Czech citizens registered abroad for each continent.
     *
     * @return a map with continent -> number of registered voters
     */
    Map<String, Integer> getContinentRegisteredVoterCountMap();

    /**
     * Finds the country in the world where is the highest ratio of non-valid votes to total votes cast.
     * Note: number of invalid votes = submitted envelopes - valid votes
     *
     * @return the name of the country with the highest ratio of invalid votes
     */
    String getNameOfCountryWithTheHighestNonValidVotesRatio();

    /**
     * Finds the country with the most registered voters.
     * This statistics shows where are the most Czech citizens registered abroad.
     *
     * @return the name of the country with the most registered voters
     */
    String getNameOfCountryWithMostRegisteredVoters();
}
