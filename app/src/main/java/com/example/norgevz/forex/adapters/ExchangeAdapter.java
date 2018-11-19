package com.example.norgevz.forex.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.norgevz.forex.R;
import com.example.norgevz.forex.global.MyApplication;
import com.example.norgevz.forex.models.Currency;
import com.example.norgevz.forex.models.Exchange;

import java.util.List;

public class ExchangeAdapter  extends RecyclerView.Adapter<ExchangeAdapter.MyViewHolder> {
    private LayoutInflater inflater;

    public List<Exchange> data;

    public ExchangeAdapter(Context context, List<Exchange> _data){
        inflater = LayoutInflater.from(context);
        this.data = _data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = inflater.inflate(R.layout.custom_row, parent , false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    public void update(List<Exchange> datas){
        if(data != null){
            data.clear();
            data.addAll(datas);
            notifyDataSetChanged();
        }
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Exchange exchange = data.get(position);
        holder.destCurrNameTextView.setText(exchange.DestinationCurrency.name);
        Currency baseCurrency = MyApplication.getInstance().getBaseCurrency();
        holder.destRateTextView.setText("1" + baseCurrency.code + " : " + String.format("%.5f", exchange.rate) + " " + exchange.DestinationCurrency.code);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView destCurrNameTextView;
        TextView destRateTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.invalidate();
            destRateTextView = (TextView) itemView.findViewById(R.id.destRateTextView);
            destCurrNameTextView = (TextView) itemView.findViewById(R.id.destCurrNameTextView);
        }
    }
}
