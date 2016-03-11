package com.example.stepanenko.dicecup.Model;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by Stepanenko on 02/03/2016.
 */
public class Dice implements Serializable {
    private int faceValue;
    private Random random;

    public Dice(int faceValue) {
        this.faceValue = faceValue;
    }

    public Dice() {
        roll();
    }

    public int getFaceValue() {
        return faceValue;
    }
    public void roll(){
        random = new Random();
        faceValue = random.nextInt(6)+1;
    }
}
