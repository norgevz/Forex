package com.example.norgevz.forex.fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.norgevz.forex.R;
import com.example.norgevz.forex.global.MyApplication;
import com.example.norgevz.forex.models.Currency;
import com.example.norgevz.forex.repositories.ExchangeRepository;
import com.example.norgevz.forex.settings.Settings;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class CurrentCurrencyDialogFragment extends DialogFragment implements ExchangeRepository.onListOfCurrenciesRequest {


    private OnListFragmentInteractionListener mListener;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    private List<Currency> currencies;

    private ExchangeRepository repo;
    ListView theListVew;
    private TextView emptyListTextView;
    ProgressBar progressBar;
    ArrayAdapter theAdapter;

    public CurrentCurrencyDialogFragment() {

    }

    public static CurrentCurrencyDialogFragment newInstance() throws JSONException {
        CurrentCurrencyDialogFragment fragment = new CurrentCurrencyDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.repo = new ExchangeRepository();
        this.repo.setListOfCurrenciesListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_currentcurrency, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        currencies = MyApplication.getInstance().getListOfCurrencies();

        theAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, getCurrenciesNames());

        theListVew = (ListView)view.findViewById(R.id.theListView);
        emptyListTextView = (TextView) view.findViewById(R.id.empty_list_text_view);
        emptyListTextView.setVisibility(View.VISIBLE);

        theListVew.setAdapter(theAdapter);

        theListVew.setOnItemClickListener( new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.onListFragmentInteraction(currencies.get(position));
                dismiss();
            }
        });
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        if(currencies.size() == 0) {
            progressBar.setVisibility(View.VISIBLE);
        }
        this.repo.getListOfCurrencies();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public List<String> getCurrenciesNames(){
        List<String> result = new ArrayList<>();

        for(Currency c : this.currencies){
            result.add(c.toString());
        }
        return result;
    }

    @Override
    public void onResponse(List<Currency> currencies) {
        this.currencies = currencies;
        List<String> currenciesNames = getCurrenciesNames();
        progressBar.setVisibility(View.INVISIBLE);

        if(currenciesNames.size() > 0){
            emptyListTextView.setText("Status: Online");
            theAdapter.clear();
            theAdapter.addAll(currenciesNames);
            theAdapter.notifyDataSetChanged();
            MyApplication.getInstance().setListOfCurrencies(this.currencies);
        }
    }

    @Override
    public void onFailResponse(String message) {
        System.err.print(message);
        progressBar.setVisibility(View.INVISIBLE);
        emptyListTextView.setText("Status: " + message);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Currency item);
    }
}
