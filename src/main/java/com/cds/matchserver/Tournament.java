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

    FixtureTree fixtureTree;
    private final List<Player> pot;
    private final String name;
    private int currentRound = 1;
    private Player me;

    public Tournament(List<Player> tournamentPot, String _name) {
        name = _name;
        pot = new ArrayList<>(tournamentPot);
        Collections.shuffle(pot);   // randomise

        fixtureTree = new FixtureTree(tournamentPot);
        fixtureTree.populate(null);
        fixtureTree.addPlayersToTournament();
    }


    /**
     * 
     * @param p
     * @return 
     */
    public Fixture findPlayer(Player p) {

        List<Fixture> firstRound = fixtureTree.fixturesMappedByRound.get(1);
        Fixture fixture = null;

        for (Fixture f : firstRound) {
            if (f.player1.equals(p) || f.player2.equals(p)) {
                fixture = f;
                break;
            }
        }
        me = p;
        return fixture;
    }

    /**
     *
     * @return
     */
    Player playWholeTournament() {

        System.out.println("\n\n\n______________________________________");
        System.out.println("Welcome to " + name + ", " + pot.size());
        System.out.println("______________________________________\n");

        Player roundWinner = null;
        for (List<Fixture> round : fixtureTree.fixturesMappedByRound.values()) {
            System.out.println("\n\n*******Playing round " + round);
            roundWinner = playRound(round);
            currentRound++;
        }
        return roundWinner;
    }

    /**
     *
     * @return
     */
    Player playTournamentByRound() {

        Player roundWinner = null;
        List<Fixture> round = fixtureTree.fixturesMappedByRound.get(currentRound);

        if(round != null) {
            System.out.println("\n\n\n______________________________________");
            System.out.println("Welcome to " + roundDescription(currentRound) + " of the " + name + ", " + fixtureTree.fixturesMappedByRound.size());
            System.out.println("______________________________________\n");

            for (Fixture f : round) {
                System.out.println("Match " + f.i + " : " + f.player1.getNameAndRank() + " vs " + f.player2.getNameAndRank());
            }

             roundWinner = playRound(round);

            currentRound++;
        }
        return roundWinner;
    }

    /**
     *
     * @param roundFixtures
     * @param roundNo
     * @return
     */
    private Player playRound(List<Fixture> roundFixtures) {

        int atpPoints = 25; //(round * round) * 25; TODO!!!!!!!!!
        Player roundWinner = null;

        for (Fixture f : roundFixtures) {
            f.player1.atpPoints += atpPoints;
            f.player2.atpPoints += atpPoints;
        }

        for (Fixture f : roundFixtures) {
            Match match = new Match(f.player1, f.player2, 5, getMatchDescription());
            boolean silencePlayer = !f.player1.equals(me) && !f.player2.equals(me);
            
            roundWinner = match.playMatch(silencePlayer);
            addWinnerToNextFixture(f, roundWinner);
        }
        return roundWinner;
    }

    /**
     * 
     * @param f
     * @param roundWinner 
     */
    private void addWinnerToNextFixture(Fixture f, Player roundWinner) {
        if (f.nextFixture != null) {
            if (f.nextFixture.player1 == null) {
                f.nextFixture.player1 = roundWinner;
            }
            else {
                f.nextFixture.player2 = roundWinner;
            }
        }
        else {
            System.out.println("Tournament ends");
        }
    }

    /**
     * 
     * @return 
     */
    private String getMatchDescription() {
        return name + " at " + roundDescription(currentRound) + " of " + fixtureTree.fixturesMappedByRound.size();
    }

    /**
     * 
     * @param roundNo
     * @return 
     */
    private String roundDescription(int roundNo) {
        String desc = "Round " + roundNo;

        if (roundNo == fixtureTree.fixturesMappedByRound.size()) {
            desc = "Final";
        }
        if (roundNo == fixtureTree.fixturesMappedByRound.size() - 1) {
            desc = "Semi-final";
        }
        if (roundNo == fixtureTree.fixturesMappedByRound.size() - 2) {
            desc = "Quarter-final";
        }
        if (roundNo == fixtureTree.fixturesMappedByRound.size() - 3) {
            desc = "Round of 16";
        }
        return desc;
    }
}
