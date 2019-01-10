/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cds.matchserver;

/**
 *
 * @author Chris
 */
public class Fixture {

    int i;
    Fixture previous1;
    Fixture previous2;
    Fixture nextFixture;
    int round;
    Player player1;
    Player player2;

    Fixture(Integer matchNo) {
        this(matchNo, null, null, null);
    }

    @Override
    public String toString() {
        return "Fixture{" + "i=" + i + ", previous1=" + previous1 + ", previous2=" + previous2 + ", next=" + (nextFixture != null ? nextFixture.i : "not set") +'}';
    }


    Fixture(int i, Fixture previous1, Fixture previous2, Fixture next) {
        this.i = i;
        this.previous1 = previous1;
        this.previous2 = previous2;
        this.nextFixture = next;
    }

}
