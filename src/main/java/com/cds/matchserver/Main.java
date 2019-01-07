/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cds.matchserver;

import java.io.IOException;
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
    private static final int WORLD_PLAYERS = 200;

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {

        final List<Player> worldPlayerPot = new ArrayList<>();

        for (int i = 0; i < WORLD_PLAYERS; i++) {
            worldPlayerPot.add(new Player());
        }

        List<Player> sList = worldPlayerPot.stream().sorted(Comparator.comparing(Player::getCombined).reversed()).collect(
                Collectors.toList());
        int j = 1;
        for (Player p : sList) {
            p.rank = j++;
        }

        Dice dAllPlayers = new Dice(worldPlayerPot.size());

        Player yourPlayer = worldPlayerPot.get(dAllPlayers.roll());

        for (int k = 0; k < 10; k++) {

            System.out.println("Your player is " + yourPlayer.toString());

            System.in.read();

            List<Tournament> tournaments = new ArrayList<>();

            Tournament tournament;

            for (int i = 0; i < 4; i++) {
                tournament = new Tournament(worldPlayerPot,
                        Name.generateTournamentName() + " " + (2019 + k));

                Fixture f = tournament.findPlayer(yourPlayer);

                p(" s(he) is playing in " + f + " " + f.player1.getNameAndRank() + " " + f.player2.getNameAndRank());

                Player winner;
                do {
                    winner = tournament.playTournamentByRound();
                }
                while (winner != null);

                yourPlayer.showResults();

                
            }

            Collections.sort(worldPlayerPot);

                System.out.println("Final ATP positions ");
                int pos = 1;
                for (Player p : worldPlayerPot) {
                    String msg = pos + ".\t" + p.getNameAndRank() + "\t\t\t" + p.atpPoints;
                    if(p.equals(yourPlayer)) {
                        msg += "**************";
                    }
                    System.out.println(msg);
                    p.setAge(p.getAge() + 1);
                    p.atpPoints = 0;    //   reset
                    pos++;
                }
                
            int improvement = new Dice(20).roll();
            p("Increasing stats by " + improvement);
            yourPlayer.improve(improvement);
        }
    }

    private static void simulateTournaments(List<Player> playerPot) {
        Tournament tournament;
        Player winner;

        for (int year = 2018; year < 2019; year++) {
            for (int i = 0; i < TOURNAMENTS; i++) {
                tournament = new Tournament(playerPot,
                        Name.generateTournamentName() + " " + year);
                winner = tournament.playWholeTournament();
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

    static void p(String s) {
        System.out.println(s);
    }
}
