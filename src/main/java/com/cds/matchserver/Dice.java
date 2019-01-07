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
/**
 *
 * @author neurometric
 */
public class Dice {
    
    private final int sides;

    public int getSides() {
        return sides;
    }
    
    public Dice(int _sides) {
        sides = _sides;
    }
    
    public int roll() {
        int zeroBasedRoll =  (int) ( Math.random() * sides );
        //System.out.println(" rolled " + (zeroBasedRoll+1));
        return zeroBasedRoll + 1;
    }    
}

