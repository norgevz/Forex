package com.example.norgevz.forex.global;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.norgevz.forex.R;
import com.example.norgevz.forex.models.Currency;
import com.example.norgevz.forex.models.Exchange;
import com.example.norgevz.forex.settings.Settings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MyApplication extends Application {

    SharedPreferences sharedPref;

    SharedPreferences.Editor editor;

    private static MyApplication CurrentApplication;

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public SharedPreferences getSharedPref() {
        return sharedPref;
    }

    private Currency baseCurrency;

    private List<Currency> currencyList;

    private List<Exchange> exchangeList;

    public Currency getBaseCurrency() {

        if(baseCurrency == null){
            Gson gson = new Gson();

            try {

                if(Settings.containsSettings("baseCurrency")){
                    String json = Settings.getSettings("baseCurrency");
                    baseCurrency = gson.fromJson(json, Currency.class);
                }
                else{
                    baseCurrency = new Currency("EUR", "Euro");
                    setBaseCurrency(baseCurrency);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return baseCurrency;
    }

    public void setBaseCurrency(Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
        Gson gson = new Gson();
        String json = gson.toJson(baseCurrency);
        Settings.addSetting("baseCurrency", json);
    }

    public void setListOfCurrencies(List<Currency> currencies){
        currencyList = currencies;
        String value = new Gson().toJson(currencies);
        Settings.addSetting("listOfCurrencies", value);
    }

    public List<Currency> getListOfCurrencies(){

        if(currencyList != null ){
            return currencyList;
        }

        if(!Settings.containsSettings("listOfCurrencies")){
            return new ArrayList<>();
        }
        try {
            String value = Settings.getSettings("listOfCurrencies");
            List<Currency> currencies = new Gson().fromJson(value, new TypeToken<List<Currency>>(){}.getType());
            return currencies;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void setListOfExchages(List<Exchange> exchages){
        exchangeList = exchages;
        String value = new Gson().toJson(exchages);
        Settings.addSetting("exchangeList", value);
    }

    public List<Exchange> getLastExchanges(){

        if(exchangeList != null ){
            return exchangeList;
        }

        if(!Settings.containsSettings("exchangeList")){
            return new ArrayList<>();
        }
        try {
            String value = Settings.getSettings("exchangeList");
            List<Exchange> exchanges = new Gson().fromJson(value, new TypeToken<List<Exchange>>(){}.getType());
            return exchanges;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        sharedPref = getSharedPreferences(
                getString(R.string.api_key), Context.MODE_PRIVATE);

        editor = sharedPref.edit();

        CurrentApplication = this;
        String apiKey = getResources().getString(R.string.api_key);
        Settings.setApiKey(apiKey);
        String apiEntryPoint = getResources().getString(R.string.api_entry_point);
        Settings.setBaseEndpoint(apiEntryPoint);
    }

    public static MyApplication getInstance(){
        return CurrentApplication;
    }

    public static Context getAppContext(){
        return CurrentApplication.getApplicationContext();
    }
}
