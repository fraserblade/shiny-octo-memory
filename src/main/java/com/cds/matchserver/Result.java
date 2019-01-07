/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cds.matchserver;

import java.util.List;

/**
 *
 * @author Chris
 */
public class Result {

    private final String competition;


    @Override
    public String toString() {
        String msg = "match : " + competition + "\n\t" + winner.getNameAndRank() + " bt " + loser.getNameAndRank() + " " + winnerSets + ":" + loserSets + "\n";
        for (SetScore s : setScores) {
            msg += "\t";
            msg += (reverse ? s.b + ":" + s.a : s.a + ":" + s.b);
            msg += "\n";
        }
        return msg;
    }

    Player winner, loser;
    int winnerSets, loserSets;
    boolean reverse = false;
    List<SetScore> setScores;

    Result(Player p1, int setsP1, Player p2, int setsP2, List<SetScore> sets, String competition) {
        this.competition = competition;
        
        this.setScores = sets;

        if (setsP1 > setsP2) {
            winner = p1;
            winnerSets = setsP1;
            loser = p2;
            loserSets = setsP2;

        }
        else {
            winner = p2;
            winnerSets = setsP2;
            loser = p1;
            loserSets = setsP1;
            reverse = true;

        }
    }
}
