/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cds.matchserver;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.TestInfo;

/**
 *
 * @author Chris
 */
public class TournamentTreeTest {

    static List<Player> worldPlayerPot;
    static int WORLD_PLAYERS = 32;

    public TournamentTreeTest(TestInfo testInfo) {
        worldPlayerPot = new ArrayList<>();

        for (int i = 0; i < WORLD_PLAYERS; i++) {
            worldPlayerPot.add(new Player());
        }
    }

    @BeforeEach
    void beforeEach(TestInfo testInfo) {
        String displayName = testInfo.getDisplayName();
        System.out.println("\n\nRUNNING " + displayName);
    }

//    /**
//     * Test of numberOfRoundsRequired method, of class TournamentTree.
//     */
//    @Test
//    public void testNumberOfRoundsRequired2() {
//        List<Player> pot = worldPlayerPot.subList(0, 4);
//        System.out.println("numberOfRoundsRequired " + pot.size());
//        TournamentTree instance = new TournamentTree(pot, "test");
//        int result = instance.numberOfRoundsRequired();
//        assertEquals(2, result);
//    }
//
//    @Test
//    public void testNumberOfRoundsRequired9() {
//        List<Player> pot = worldPlayerPot.subList(0, 9);
//        System.out.println("numberOfRoundsRequired " + pot.size());
//        TournamentTree instance = new TournamentTree(pot, "test");
//        int result = instance.numberOfRoundsRequired();
//        assertEquals(4, result);
//    }
//
//    @Test
//    public void testNumberOfFixturesRequired9() {
//        List<Player> pot = worldPlayerPot.subList(0, 9);
//        System.out.println("numberOfRoundsRequired " + pot.size());
//        TournamentTree instance = new TournamentTree(pot, "test");
//        int result = instance.numberOfRoundsRequired();
//        assertEquals(4, result);
//        assertEquals(8, instance.numberOfFixtures());
//    }
//
//    @Test
//    public void testNumberOfRoundsRequired16() {
//        List<Player> pot = worldPlayerPot.subList(0, 16);
//        System.out.println("numberOfRoundsRequired " + pot.size());
//        TournamentTree instance = new TournamentTree(pot, "test");
//        int result = instance.numberOfRoundsRequired();
//        assertEquals(4, result);
//        assertEquals(4, instance.getRounds().size());
//        assertEquals(15, instance.numberOfFixtures());
//
//    }
//
//    @Test
//    public void testNumberOfRoundsRequired17() {
//        List<Player> pot = worldPlayerPot.subList(0, 17);
//        TournamentTree instance = new TournamentTree(pot, "test");
//        int result = instance.numberOfRoundsRequired();
//        assertEquals(5, result);
//        assertEquals(5, instance.getRounds().size());
//        assertEquals(16, instance.numberOfFixtures(), "Should be 16 fixtures");
//    }
//    
//    @Test
//    public void testAddPlayers8() {
//        List<Player> pot = worldPlayerPot.subList(0, 8);
//        TournamentTree instance = new TournamentTree(pot, "test");
//        int result = instance.numberOfRoundsRequired();
//        assertEquals(3, result);
//        assertEquals(3, instance.getRounds().size());
//        assertEquals(7, instance.numberOfFixtures());
//        instance.addPlayersToRounds();
//    }
}
