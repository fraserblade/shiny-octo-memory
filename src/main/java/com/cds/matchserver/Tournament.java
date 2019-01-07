/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cds.matchserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Chris
 */
public class Tournament {

    private final List<Player> pot;
    private final String name;
    private final List<List<Fixture>> rounds;

    public Tournament(List<Player> tournamentPot, String _name) {
        name = _name;
        pot = new ArrayList<>(tournamentPot);
        Collections.shuffle(pot);   // randomise
        rounds = generateFixtures();
    }

    /**
     *
     * @return 
     */
    final protected List<List<Fixture>> generateFixtures() {

        List<List<Fixture>> r = new ArrayList<>();
        List<Fixture> fixtures = new ArrayList<>();

        Integer matchNo;

        for (matchNo = 1; matchNo <= pot.size() / 2; matchNo++) {
            fixtures.add(new Fixture(matchNo));
        }

        r.add(fixtures);

        while (fixtures.size() > 1) {
            fixtures = getNextRoundFixtures(fixtures, matchNo);
            r.add(fixtures);
            matchNo += fixtures.size();
        }

        return r;
    }

    /**
     *
     * @param fixtures
     * @param matchNo
     * @return
     */
    protected final List<Fixture> getNextRoundFixtures(List<Fixture> fixtures, Integer matchNo) {

        Fixture previous1 = null, next = null;

        List<Fixture> nextRoundFixtures = new ArrayList<>();
        for (Fixture fixture : fixtures) {
            if (previous1 == null) {
                previous1 = fixture;
            }
            else {
                Fixture nextRoundFixture = new Fixture(matchNo++, previous1, fixture, next);
                previous1.next = nextRoundFixture;
                fixture.next = nextRoundFixture;
                nextRoundFixtures.add(nextRoundFixture);
                previous1 = null;
            }
        }
        return nextRoundFixtures;
    }

    /**
     *
     * @return
     */
    Player playTournament() {

        System.out.println("\n\n\n______________________________________");
        System.out.println("Welcome to " + name + ", " + pot.size());
        System.out.println("______________________________________\n");

        addPlayersToFirstRoundFixtures();

        Player roundWinner = null;
        for (List<Fixture> round : rounds) {
            System.out.println("\n\n\n*******Playing round " + rounds.indexOf(round));
            roundWinner = playRound(round, rounds.indexOf(round) + 1);
        }
        return roundWinner;
    }

    /**
     *
     */
     private void addPlayersToFirstRoundFixtures() {
        List<Fixture> firstRound = rounds.get(0);
        int playerNo = 0;

        for (Fixture f : firstRound) {
            f.player1 = pot.get(playerNo++);
            f.player2 = pot.get(playerNo++);
        }
    }

    /**
     *
     * @param roundFixtures
     * @param roundNo
     * @return
     */
    private Player playRound(List<Fixture> roundFixtures, int roundNo) {

        int atpPoints = 25; //(round * round) * 25; TODO!!!!!!!!!
        Player roundWinner = null;

        //System.out.println(name + ": Playing " + roundDescription());
        //System.out.println("Adding " + atpPoints + " ATP points for the " + playersInRound.size() + " players in round");
        for (Fixture f : roundFixtures) {
            f.player1.atpPoints += atpPoints;
            f.player2.atpPoints += atpPoints;
        }

        for (Fixture f : roundFixtures) {
            Match match = new Match(f.player1, f.player2, 5, name + " " + roundDescription(roundFixtures.size()), roundNo);
            roundWinner = match.playMatch();
            if (f.next != null) {
                if (f.next.player1 == null) {
                    f.next.player1 = roundWinner;
                }
                else {
                    f.next.player2 = roundWinner;
                }
            }
        }
        return roundWinner;
    }

    private String roundDescription(int roundNo) {
        String desc = "Round x";

        if (roundNo == 1) {
            desc = "Final";
        }
        if (roundNo == 2) {
            desc = "Semi-final";
        }
        if (roundNo == 4) {
            desc = "Quarter-final";
        }
        if (roundNo == 8) {
            desc = "Round of 16";
        }

        return desc;
    }
}
