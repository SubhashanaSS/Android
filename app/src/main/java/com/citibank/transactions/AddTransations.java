package com.citibank.transactions;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTransations extends AppCompatActivity {
    EditText transAccNo, transAmount, benefAccNo;
    Button btnInsert;

    boolean isAllFieldsChecked = false;
    DatabaseReference transactionsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transations);
        transAccNo = findViewById(R.id.idTransAccNo);
        transAmount = findViewById(R.id.idTransAmount);
        benefAccNo = findViewById(R.id.idBenefAccNo);
        btnInsert = findViewById(R.id.idBtnInsert);

        transactionsDB = FirebaseDatabase.getInstance().getReference().child("Transactions");
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllFieldsChecked = CheckAllFields();
                if(isAllFieldsChecked) {
                    insertTransactionsData();
                }
            }
        });
    }

    private void insertTransactionsData(){
        String trAccNo = transAccNo.getText().toString();
        String trAmount = transAmount.getText().toString();
        String beAccNo = benefAccNo.getText().toString();
        String id = null;

        Transactions transactions = new Transactions(null, trAccNo,trAmount,beAccNo);
        transactionsDB.push().setValue(transactions);

        Toast.makeText(AddTransations.this, "Transaction Done Successfully", Toast.LENGTH_SHORT).show();
    }

    private boolean CheckAllFields(){
        if (transAccNo.length() == 0){
            transAccNo.setError("Transferee Account Number Is Required");
            return false;
        }

        if (transAmount.length() == 0){
            transAmount.setError("Amount Is Required");
            return false;
        }

        if (benefAccNo.length() == 0){
            benefAccNo.setError("Beneficiary Account number Is Required");
        }

        return true;
    }
}