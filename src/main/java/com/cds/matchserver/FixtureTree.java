/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cds.matchserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Chris
 */
public class FixtureTree {

    public final Map<Integer, List<Fixture>> fixturesMappedByRound = new HashMap<>();
    
    final int numRounds;
    int mapCount = 0;
    int depth = 0;
    List<Player> pot;
    final Map<Integer, Fixture> allFixturesByNumber = new HashMap<>();

    /**
     * 
     * @param _pot 
     */
    public FixtureTree(List<Player> _pot) {
        pot = _pot;
        numRounds = (int) (Math.log(pot.size()) / Math.log(2));
    }

    /**
     * 
     * @param parent
     * @return 
     */
    final Fixture populate(Fixture parent) {

        Fixture newFixture = dropFixtureIntoCollections(parent);

        depth++;
        if (depth < numRounds) {
            newFixture.previous1 = populate(newFixture);
            newFixture.previous2 = populate(newFixture);
        }
        depth--;
        return newFixture;
    }

    /**
     *
     * @param parent
     * @return
     */
    private Fixture dropFixtureIntoCollections(Fixture parent) {
        int roundNo = (numRounds - depth);
        Fixture newFixture = new Fixture(mapCount);
        newFixture.nextFixture = parent;
        newFixture.round = roundNo;

        // Map into "all fixtures"
        //
        allFixturesByNumber.put(mapCount++, newFixture);

        // Map into by-round list
        //
        List<Fixture> fixtures = fixturesMappedByRound.get(roundNo);
        if (fixtures == null) {
            fixtures = new ArrayList<>();
            fixturesMappedByRound.put(roundNo, fixtures);
        }
        fixtures.add(newFixture);
        return newFixture;
    }

    /**
     * 
     */
    void addPlayersToTournament() {
        List<Fixture> round1Fixtures = fixturesMappedByRound.get(1);

        int fixture = 0;
        for (Player p : pot.subList(0, pot.size() / 2)) {
            Fixture f = round1Fixtures.get(fixture++);
            f.player1 = p;
        }
        fixture = 0;
        for (Player p : pot.subList(pot.size() / 2, pot.size())) {
            Fixture f = round1Fixtures.get(fixture++);
            f.player2 = p;
        }
    }
}
