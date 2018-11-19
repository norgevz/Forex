package com.example.norgevz.forex.repositories;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.norgevz.forex.global.MyApplication;
import com.example.norgevz.forex.models.Currency;
import com.example.norgevz.forex.models.Exchange;
import com.example.norgevz.forex.network.VolleySingleton;
import com.example.norgevz.forex.settings.Settings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ExchangeRepository {

    private onListOfCurrenciesRequest listOfCurrenciesListener;
    private onExchangeListRequest onExchangeListListener;

    public void setListOfCurrenciesListener(onListOfCurrenciesRequest listener){
        this.listOfCurrenciesListener = listener;
    }

    public void setExchangeListListener(onExchangeListRequest listener){
        this.onExchangeListListener = listener;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) MyApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void getListOfCurrencies() {
        if(isNetworkAvailable()){
            getListOfCurrenciesFromApi();
        }else
            this.listOfCurrenciesListener.onFailResponse("No Internet");
    }

    private void getListOfCurrenciesFromApi()  {
        String URL = Settings.getBaseEndpoint() + "symbols?access_key=" + Settings.getApiKey();
        RequestQueue queue = VolleySingleton.getInstance().getRequestQueue();

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, URL, null , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try{
                    String success = response.get("success").toString();
                    if(success.equals("true")){
                        List<Currency> currencies = new ArrayList<>();
                        JSONObject symbols =  response.getJSONObject("symbols");
                        JSONArray keys =  symbols.names();
                        for(int i = 0; i < keys.length(); i++) {
                            String key = keys.getString(i);
                            currencies.add(new Currency(key, symbols.getString(key)));
                        }
                        listOfCurrenciesListener.onResponse(currencies);
                    }
                    else{
                        listOfCurrenciesListener.onFailResponse(response.getJSONObject("error").getString("info"));
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listOfCurrenciesListener.onFailResponse(error.getMessage());
                }
            }
        );
        queue.add(objectRequest);
    }

    public Currency getCurrencyNamebyCode(String code){
        final List<Currency> currencies = MyApplication.getInstance().getListOfCurrencies();
        for (Currency c : currencies){
            if(c.code.equals(code)){
                return c;
            }
        }
        return null;
    }


    public void getListOfExangeFromApi(Currency baseCurrency)  {
        String URL = Settings.getBaseEndpoint() + "latest?access_key=" + Settings.getApiKey();
        URL += "&base=" + baseCurrency.code;
        RequestQueue queue = VolleySingleton.getInstance().getRequestQueue();


        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, URL, null , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try{
                    String success = response.get("success").toString();
                    if(success.equals("true")){
                        List<Exchange> exchanges = new ArrayList<>();
                        JSONObject rates =  response.getJSONObject("rates");
                        JSONArray keys =  rates.names();
                        for(int i = 0; i < keys.length(); i++) {
                            String key = keys.getString(i);

                            exchanges.add(new Exchange(getCurrencyNamebyCode(key), Float.valueOf(rates.getString(key))));
                        }
                        onExchangeListListener.onResponse(exchanges);
                    }
                    else{
                        onExchangeListListener.onFailResponse(response.getJSONObject("error").getString("type"));
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onExchangeListListener.onFailResponse(error.getMessage());
            }
        }
        );
        queue.add(objectRequest);
    }

    public interface onListOfCurrenciesRequest{
        void onResponse(List<Currency> currencies);
        void onFailResponse(String message);
    }

    public interface onExchangeListRequest{
        void onResponse(List<Exchange> exchanges);
        void onFailResponse(String message);
    }

}
