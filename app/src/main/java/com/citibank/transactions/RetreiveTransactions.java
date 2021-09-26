package com.citibank.transactions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.Transliterator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RetreiveTransactions extends AppCompatActivity {
    ListView transListView;
    List<Transactions> transactionsList;
    DatabaseReference transDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retreive_transactions);

        transListView = findViewById(R.id.transListView);
        transactionsList = new ArrayList<>();

        transDbRef = FirebaseDatabase.getInstance().getReference("Transactions");

        transDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                transactionsList.clear();
                for(DataSnapshot transactionDatasnap : snapshot.getChildren()){

                    Transactions transactions = transactionDatasnap.getValue(Transactions.class);
                    transactionsList.add(transactions);
                }
                ListAdapter adapter = new TrListAdapter(RetreiveTransactions.this,transactionsList);
                transListView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        //UPDATE TRANSACTION

        transListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Transactions transactions = transactionsList.get(position);
                showUpdateDialog(transactions.getId(),transactions.trAccNo);
                return false;
            }
        });
    }
    private void showUpdateDialog(String id,String benfAccNo){
        AlertDialog.Builder mDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View mDialogView = inflater.inflate(R.layout.update_transaction,null);
        mDialog.setView(mDialogView);

       //create views references
        EditText updateTrAccNo = mDialogView.findViewById(R.id.updateTrAccNo);
        EditText updateAmount = mDialogView.findViewById(R.id.updateAmount);
        EditText updateBenefAccno = mDialogView.findViewById(R.id.updateBenefAccno);
        Button idBtnUpdate = mDialogView.findViewById(R.id.idBtnUpdate);
        Button idBtnDelete = mDialogView.findViewById(R.id.idBtnDelete);

        mDialog.setTitle("Updating" +benfAccNo+ "Transaction");
        mDialog.show();

        idBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newTrAccNo = updateTrAccNo.getText().toString();
                String newAmount = updateAmount.getText().toString();
                String newBenefAcc = updateBenefAccno.getText().toString();
                updateData(id,newTrAccNo,newAmount,newBenefAcc);
                Toast.makeText(RetreiveTransactions.this, "Transaction Updated", Toast.LENGTH_SHORT).show();
            }
        });

        //TRANSACTION DELETE

        idBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteRecord(id);
            }
        });
    }
    private void showToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
    private void deleteRecord(String id){
        DatabaseReference DbRef = FirebaseDatabase.getInstance().getReference("Transactions").child(id);

        Task<Void> mTask = DbRef.removeValue();
        mTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                showToast("Record Deleted");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showToast("Record Deleting Failed");
            }
        });
    }

    
    private void updateData(String id, String trAccNo, String trAmount, String beAccNo) {
        DatabaseReference DbRef = FirebaseDatabase.getInstance().getReference("Transactions").child(id);
        Transactions transactions = new Transactions(id,trAccNo,trAmount,beAccNo);
        DbRef.setValue(transactions);
    }


}