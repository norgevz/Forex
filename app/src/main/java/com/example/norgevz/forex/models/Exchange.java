package com.example.norgevz.forex.models;

public class Exchange {
    public Currency DestinationCurrency;
    public float rate;

    public Exchange(Currency destinationCurrency, float rate){
        this.DestinationCurrency = destinationCurrency;
        this.rate = rate;
    }
}
