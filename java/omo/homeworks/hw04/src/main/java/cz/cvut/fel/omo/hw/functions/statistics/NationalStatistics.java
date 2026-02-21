package cz.cvut.fel.omo.hw.functions.statistics;

import java.util.List;
import java.util.Map;

public interface NationalStatistics {

    /**
     * Returns a mapping of region names to the names of the winning candidates in those regions.
     * Each entry represents the winner for a specific region.
     *
     * @return Map where the key is the region name and the value is the winner's name
     */
    Map<String, String> getRegionWinnerMap();

    /**
     * Returns a list of the top 10 cities where the specified candidate won, ordered by the number of votes in descending order.
     * Only cities where the candidate received the most votes are included.
     *
     * @param candidateId the unique identifier of the candidate
     * @return a list of city names ordered by the number of votes the candidate received, descending
     */
    List<String> getTop10CitiesWhereCandidateWonOrderedByNumberOfVotesDesc(int candidateId);

    /**
     * Returns the name of the city with the lowest voter turnout.
     * Note: voter turnout percent = issued envelopes / number of registered voters
     *
     * @return the name of the city with the lowest voter turnout
     */
    String getNameOfCityWithTheLowestVoterTurnout();
}
