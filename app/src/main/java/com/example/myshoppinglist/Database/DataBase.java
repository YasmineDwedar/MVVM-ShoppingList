package com.example.myshoppinglist.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.myshoppinglist.Model.DataPojo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataBase {
    //Firebase
    private Context mContext;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String userId;
    public DatabaseReference mDatabaseReference;
    private static final String TAG = "Database";

    public DataBase() {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        userId = mUser.getUid();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Shopping List").child(userId);
        mDatabaseReference.keepSynced(true);
    }

    public DataBase(Context context) {
        this.mContext = context;
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        userId = mUser.getUid();                                          //Node                  //UserId
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Shopping List").child(userId);
        mDatabaseReference.keepSynced(true);

    }

    public MutableLiveData<String> getTotalAmount() {

        final MutableLiveData<String> amountString = new MutableLiveData<>();

        mDatabaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totalAmount = 0;
                for (DataSnapshot item : snapshot.getChildren()) {
                    DataPojo dataPojo = item.getValue(DataPojo.class);
                    if (dataPojo != null) {
                        totalAmount += dataPojo.getAmount();
                    }
                    amountString.setValue(String.valueOf(totalAmount));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return amountString;
    }

    public MutableLiveData<List<DataPojo>> renderHomeView() {
        final MutableLiveData<List<DataPojo>> ListMutableLiveData = new MutableLiveData<>();
        final ArrayList<DataPojo> list = new ArrayList<>();
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot item : snapshot.getChildren()) {
                    DataPojo dataPojo = item.getValue(DataPojo.class);
                    if (dataPojo != null) {
                        list.add(dataPojo);
                    }
                }
                ListMutableLiveData.setValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return ListMutableLiveData;
    }

    public void addToDataBase(DataPojo dataPojo) {
        mDatabaseReference.child(dataPojo.getId()).setValue(dataPojo);

    }


//    public void getTotalAmnt() {
//        mDatabaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                int totalAmount = 0;
//                for (DataSnapshot item : snapshot.getChildren()) {
//                    DataPojo dataPojo = item.getValue(DataPojo.class);
//                    totalAmount += dataPojo.getAmount();
//                    String amountString = String.valueOf(totalAmount);
//                    HomeActivity homeActivity = new HomeActivity();
//                    homeActivity.setData(amountString);
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//
//    }
}
