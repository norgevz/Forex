package com.example.norgevz.forex;

import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.norgevz.forex.adapters.ExchangeAdapter;
import com.example.norgevz.forex.fragments.CurrentCurrencyDialogFragment;
import com.example.norgevz.forex.global.MyApplication;
import com.example.norgevz.forex.models.Currency;
import com.example.norgevz.forex.models.Exchange;
import com.example.norgevz.forex.repositories.ExchangeRepository;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements CurrentCurrencyDialogFragment.OnListFragmentInteractionListener, ExchangeRepository.onExchangeListRequest {

    Button base_selector_button;
    RecyclerView recyclerView;
    private TextView emptyListTextView;
    List<Exchange> exchangesList;
    ExchangeAdapter exchangeAdapter;
    private ExchangeRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.exchangeList);
        recyclerView.setHasFixedSize(true);
        exchangeAdapter = new ExchangeAdapter(this, new ArrayList<Exchange>());
        recyclerView.setAdapter(exchangeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        base_selector_button = (Button) findViewById(R.id.base_selector_button);
        Currency base = MyApplication.getInstance().getBaseCurrency();
        base_selector_button.setText(base.code);
        emptyListTextView = (TextView) findViewById(R.id.empty_list_text_view);

        exchangesList = MyApplication.getInstance().getLastExchanges();

        this.repo = new ExchangeRepository();
        this.repo.setExchangeListListener(this);

        if(exchangesList.size() == 0) {
            emptyListTextView.setVisibility(View.VISIBLE);
            base_selector_button.setText("Select Base Currency");
        }
        else {
            exchangeAdapter.update(exchangesList);
        }
    }

    public void onCurrentCurrencyClicked(View view) throws JSONException {
        FragmentManager fm = getFragmentManager();
        CurrentCurrencyDialogFragment currenciesDialog = CurrentCurrencyDialogFragment.newInstance();
        currenciesDialog.show(fm, "Get Base Currency");
    }


    @Override
    public void onListFragmentInteraction(Currency item) {
        MyApplication.getInstance().setBaseCurrency(item);
        base_selector_button.setText(item.code);
        repo.getListOfExangeFromApi(item);
    }

    @Override
    public void onResponse(List<Exchange> exchanges) {
        exchangeAdapter.update(exchanges);
        MyApplication.getInstance().setListOfExchages(exchanges);
        if(exchanges.size() > 0){
            this.emptyListTextView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onFailResponse(String message) {
        List<Exchange> empty = new ArrayList<>();
        exchangeAdapter.update(empty);
        MyApplication.getInstance().setListOfExchages(empty);
        this.emptyListTextView.setVisibility(View.VISIBLE);
        this.emptyListTextView.setText(message);
    }
}
