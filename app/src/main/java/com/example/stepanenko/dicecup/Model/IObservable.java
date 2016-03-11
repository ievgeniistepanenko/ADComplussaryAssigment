package com.example.stepanenko.dicecup.Model;

/**
 * Created by Stepanenko on 02/03/2016.
 */
public interface IObservable {
    void registerObserver(IObserver observer);
    void removeObserver(IObserver observer);
    void notifyObservers();
}
