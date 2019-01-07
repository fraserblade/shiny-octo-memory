/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cds.matchserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Chris
 */
public class Main {

    private static final int TOURNAMENTS = 10;
    private static final int PLAYERS = 1000;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        List<Player> playerPot = new ArrayList<>();

        for (int i = 0; i < PLAYERS; i++) {
            playerPot.add(new Player());
        }

        List<Player> sList = playerPot.stream().sorted(Comparator.comparing(Player::getCombined).reversed()).collect(Collectors.toList());
        int j = 1;
        for (Player p : sList) {
            p.rank = j++;
        }

        Tournament tournament;
        Player winner;

        for (int year = 2018; year < 2019; year++) {
            for (int i = 0; i < TOURNAMENTS; i++) {
                tournament = new Tournament(playerPot, Name.generateTournamentName() + " " + year);
                winner = tournament.playTournament();
                winner.atpPoints += 1100;
                System.out.println("Overall winner " + winner.getNameAndRank() + " age " + winner.getAge());
            }

            Collections.sort(playerPot);

            Dice d6 = new Dice(6);

            for (Iterator<Player> iterator = playerPot.iterator(); iterator.hasNext();) {
                Player p = iterator.next();
                if (p.getAge() - d6.roll() > 35) {
                    System.out.println(p.getName() + " retires ");
                    iterator.remove();
                }
            }

            System.out.println("Final ATP positions " + year);
            int pos = 1;
            for (Player p : playerPot) {
                System.out.println(pos + ".\t" + p.getNameAndRank() + "\t\t\t" + p.atpPoints);
                p.setAge(p.getAge() + 1);
                pos++;
            }
        }
        playerPot.get(0).showResults();
    }

    private static void fun(List<Player> playerPot) {
        Player lowestRanking = playerPot.stream()
                .min(Comparator.comparing(player -> player.getCombined()))
                .get();
        System.out.println("LOWEST " + lowestRanking);

        Player hiRanking = playerPot.stream()
                .max(Comparator.comparing(player -> player.getCombined()))
                .get();
        System.out.println("HIGHEST " + hiRanking);
    }
}
