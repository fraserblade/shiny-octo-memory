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
public class PercentDice  {
    
    static Dice dice = new Dice(100);
    
    static int get() {
        return dice.roll();
    }
}
