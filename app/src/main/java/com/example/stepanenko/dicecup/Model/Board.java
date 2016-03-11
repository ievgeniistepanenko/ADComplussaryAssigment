package com.example.stepanenko.dicecup.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Stepanenko on 02/03/2016.
 */
public class Board implements IObservable,Serializable {

    private ArrayList<IObserver> observers;
    private List<Dice> dices;
    private TreeMap<Date, int[]> history;

    private int currentNumberDices;

    public Board(int numberOfDices) {
        observers = new ArrayList<>();
        currentNumberDices = numberOfDices;
        history = new TreeMap<>();

        dices = new ArrayList<>();
        for (int i = 0; i<numberOfDices; i++){
            dices.add(new Dice());
        }
    }

    public void roll(){
        for (Dice d: dices) {
            d.roll();
        }
        saveHistory();
    }
    public  int getNumberOfDices(){
        return  currentNumberDices;
    }
    public void setNumberOfDices(int numberOfDices){
        if ( numberOfDices >0 && numberOfDices <=6  ){
            int difference = Math.abs(numberOfDices - currentNumberDices);
            if ( numberOfDices >= currentNumberDices){
                addDices( difference );
            }
            else{
                removeDices( difference );
            }
        }
    }
    public Dice getDice(int number){
        return dices.get( number );
    }

    private void saveHistory(){
        Date date = new Date();
        history.put(date, getCurrentValues());
    }

    private int[] getCurrentValues(){
        int[] values = new int[dices.size()];
        for (int i = 0; i < dices.size(); i++){
            values[i] = dices.get(i).getFaceValue();
        }
        return values;
    }


    private void removeDices(int numberOfDices){
        for (int i=0; i < numberOfDices; i++){
            dices.remove(dices.size()-1);
            currentNumberDices--;
        }
    }
    private void addDices(int numberOfDices){
        for (int i=0; i<numberOfDices;i++){
            dices.add(new Dice());
            currentNumberDices++;
        }
    }
    public TreeMap<Date, int[]> getHistory(){
        return history;
    }

    @Override
    public void registerObserver(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (IObserver o: observers)
        {
            o.update();
        }
    }

    public void clearHistory() {
        history.clear();
    }
}
