package com.example.norgevz.forex.models;

public class Currency {
    public String code;
    public String name;

    public Currency(String code, String name){
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString(){
        return name + " (" + code + ")";
    }
}
