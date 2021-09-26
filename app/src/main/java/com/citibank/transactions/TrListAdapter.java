package com.citibank.transactions;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class TrListAdapter extends ArrayAdapter {

    private Activity mContext;
    List<Transactions> transactionsList;

    public TrListAdapter(Activity mContext, List<Transactions> transactionsList){
        super(mContext,R.layout.transaction_list,transactionsList);
        this.mContext = mContext;
        this.transactionsList = transactionsList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.transaction_list, null,true);

        TextView trAccno = listItemView.findViewById(R.id.idTransAccNo);
        TextView tAmount = listItemView.findViewById(R.id.idTransAmount);
        TextView benefAccno = listItemView.findViewById(R.id.idBenefAccNo);

        Transactions transactions = transactionsList.get(position);

        trAccno.setText(transactions.getTrAccNo());
        tAmount.setText(transactions.getTrAmount());
        benefAccno.setText(transactions.getBeAccNo());


        return listItemView;
    }
}
