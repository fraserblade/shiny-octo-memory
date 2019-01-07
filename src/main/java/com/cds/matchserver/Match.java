/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cds.matchserver;

import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chris
 */
public class Match {

    @Override
    public String toString() {
        return "Match " + matchNo + " {" + "competition=" + competition + ", p1=" + p1.getNameAndRank() + ", p2=" + p2.getNameAndRank() + ", setsToWin=" + setsToWin + ", p2Dice=" + p2Dice + ", p1Dice=" + p1Dice + ", setsP2=" + setsP2 + ", setsP1=" + setsP1 + '}';
    }

    private final String competition;
    private final int matchNo;

    public Match(Player _p1, Player _p2, int _numSets, String _competitionName, int _matchNo) {
        p1 = _p1;
        p2 = _p2;
        competition = _competitionName;
        matchNo = _matchNo;
        p1Dice = new Dice(p1.getCombined());
        p2Dice = new Dice(p2.getCombined());
        setsToWin = (_numSets / 2) + 1;
        System.out.println("It's " + p1.getName() + " and " + p2.getName() + " taking to the court...");
        if (p1.rank > p2.rank + 20) {
            //System.out.println("This should be an easy game for " + p2.getName());
        }
        if (p2.rank > p1.rank + 20) {
            //System.out.println("This should be an easy game for " + p1.getName());
        }
    }

    /**
     *
     * @return
     */
    Player playMatch() {
        return playMatch(true);
    }

    /**
     *
     * @param silent
     * @return
     */
    Player playMatch(boolean silent) {

        setsP1 = 0;
        setsP2 = 0;
        int gamesP1 = 0, gamesP2 = 0;

        List<SetScore> sets = new ArrayList<>();
        while (setsP1 < setsToWin && setsP2 < setsToWin) {
            while (continueSet(gamesP1, gamesP2)) {
                if (p1Dice.roll() > p2Dice.roll()) {
                    if (!silent) {
                        System.out.println("Game to " + p1.getName());
                    }
                    gamesP1++;
                }
                else {
                    if (!silent) {
                        System.out.println("Game to " + p2.getName());
                    }
                    gamesP2++;
                }
                if (!silent) {
                    _sleep(1);
                }
            }
            String setMsg = getMessageForCommentary();

            if (gamesP1 > gamesP2) {
                System.out.println(p1.getName() + " takes the " + setMsg + " set " + gamesP1 + ":" + gamesP2);
                setsP1++;
            } else {
                System.out.println(p2.getName() + " takes the " + setMsg + " set " + gamesP2 + ":" + gamesP1);
                setsP2++;
            }
            sets.add(new SetScore(gamesP1, gamesP2));
            //_sleep(5);
            gamesP1 = 0;
            gamesP2 = 0;
        }

        Result res = new Result(p1, setsP1, p2, setsP2, sets, competition);
        p1.addResult(res);
        p2.addResult(res);

        if (!silent) {
            System.out.println(res);
        }
        System.out.println(res.winner.getNameAndRank() + " wins");
        return res.winner;
    }

    /*
    
     */
    private Player playGame() {

        Player gameWinner = null;
        int p1Points = 0, p2Points = 0;

        while (continueGame(p1Points, p2Points)) {
            if (p1Dice.roll() > p2Dice.roll()) {

            }

        }
        return gameWinner;
    }

    private boolean continueGame(int p1Points, int p2Points) {

        boolean bContinue = (p1Points < 3 && p2Points < 3);

        if (!bContinue) {
            if (p1Points == 1) {
                System.out.println();
            }
        }
        return true;
    }

    static void _sleep(int t) {
        try {
            sleep(t * 1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Match.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getMessageForCommentary() {
        String setMsg = "first";
        if (setsP1 > 0 || setsP2 > 0) {
            setMsg = "next";
        }
        if (isFinalSet()) {
            setMsg = "final";
        }
        return setMsg;
    }

    private boolean isFinalSet() {
        return setsP1 + setsP2 == ((setsToWin - 1) * 2);
    }

    private boolean continueSet(int gamesP1, int gamesP2) {
        boolean continueSet = gamesP1 < 6 && gamesP2 < 6;

        if (!continueSet && (Math.abs(gamesP1 - gamesP2) < 2)) {
            continueSet = true;
        }
        if (((gamesP1 == 6 && gamesP2 == 7) || (gamesP2 == 6 && gamesP1 == 7)) && !isFinalSet()) {
            continueSet = false;
        }
        return continueSet;
    }

    private final Player p1;
    private final Player p2;
    private final int setsToWin;
    private final Dice p2Dice;
    private final Dice p1Dice;
    private int setsP2;
    private int setsP1;

}
