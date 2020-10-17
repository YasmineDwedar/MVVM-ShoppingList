package com.example.myshoppinglist.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myshoppinglist.Adaptor.DataAdapter;
import com.example.myshoppinglist.Database.DataBase;
import com.example.myshoppinglist.Model.DataPojo;
import com.example.myshoppinglist.R;
import com.example.myshoppinglist.ViewModel.HomeViewModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Toolbar toolBar;
    private FloatingActionButton fab;
    private HomeViewModel homeViewModel;
    private DataBase database;
    private TextView totalSum;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String userId;

    public void firebaseSetup(){
        database = new DataBase(getApplicationContext());
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        userId = mUser.getUid();
        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Shopping List").child(userId);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fab = findViewById(R.id.id_floatingActionButton);
        toolBar = findViewById(R.id.id_home_toolBar);
        recyclerView = findViewById(R.id.id_recycler_home);
        totalSum = findViewById(R.id.id_total_amount);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle("Daily Shopping List");
        firebaseSetup();
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayCustomDialog();
            }
        });
        homeViewModel.getTotalAmount().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                totalSum.setText(s);

            }
        });

        homeViewModel.renderHomeView().observe(this, new Observer<List<DataPojo>>() {
            @Override
            public void onChanged(List<DataPojo> dataPojos) {
                DataAdapter adapter = new DataAdapter(HomeActivity.this, dataPojos);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }


//    public void setData(String val){
//        totalSum.setText(val);
//    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//
//
//        FirebaseRecyclerAdapter<DataPojo, MyViewHolder> adapter = new FirebaseRecyclerAdapter<DataPojo, MyViewHolder>(
//                DataPojo.class,
//                R.layout.item_row,
//                MyViewHolder.class,
//                database.mDatabaseReference
//        ) {
//
//            @Override
//            protected void populateViewHolder(MyViewHolder myViewHolder, final DataPojo dataPojo, final int i) {
//                myViewHolder.setDate(dataPojo.getDate());
//                myViewHolder.setAmmount(dataPojo.getAmount());
//                myViewHolder.setNote(dataPojo.getNote());
//                myViewHolder.setType(dataPojo.getType());
//                myViewHolder.myView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        post_key = getRef(i).getKey();
//                        xtype = dataPojo.getType();
//                        xamount = dataPojo.getAmount();
//                        xnote = dataPojo.getNote();
//                        displayCustomUpadateDialog();
//                    }
//                });
//            }
//        };
//        recyclerView.setAdapter(adapter);
//    }
//
//    public static class MyViewHolder extends RecyclerView.ViewHolder {
//        View myView;
//
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            myView = itemView;
//        }
//        public void setType(String type) {
//            TextView myType = itemView.findViewById(R.id.id_type);
//            myType.setText(type);
//        }
//
//        public void setNote(String note) {
//            TextView myNote = itemView.findViewById(R.id.id_note);
//            myNote.setText(note);
//        }
//
//        public void setDate(String date) {
//            TextView myDate = itemView.findViewById(R.id.id_date);
//            myDate.setText(date);
//        }
//
//        public void setAmmount(int ammount) {
//            TextView myAmmount = itemView.findViewById(R.id.id_amount);
//            myAmmount.setText(String.valueOf(ammount));
//        }
//    }

    //    public void displayCustomUpadateDialog() {
//        //Custom Upadte Dialog
//        AlertDialog.Builder myDialog = new AlertDialog.Builder(HomeActivity.this);
//        LayoutInflater inflater = LayoutInflater.from(HomeActivity.this);
//        View myView = inflater.inflate(R.layout.update_input_feild, null);
//        final AlertDialog dialog = myDialog.create();
//        dialog.setView(myView);
//        final EditText type = myView.findViewById(R.id.id_update_type);
//        final EditText amount = myView.findViewById(R.id.id_update_amount);
//        final EditText note = myView.findViewById(R.id.id_update_note);
//        Button update = myView.findViewById(R.id.id_save_updte_button);
//        Button delete = myView.findViewById(R.id.id_delete_updte_button);
//
//        type.setText(xtype);
//        type.setSelection(xtype.length());          //       setSelection set the EditText Cursor to the end of its text
//
//        amount.setText(String.valueOf(xamount));
//        amount.setSelection(String.valueOf(xamount).length());
//
//        note.setText(xnote);
//        note.setSelection(xnote.length());
//        dialog.show();
//        update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String myType = type.getText().toString().trim();
//                String myAmount = amount.getText().toString().trim();
//                String myNote = note.getText().toString().trim();
//
//                if (TextUtils.isEmpty(myType)) {
//                    type.setError("Required Feild...");
//
//                } else if (TextUtils.isEmpty(myAmount)) {
//                    amount.setError("Required Feild...");
//
//                } else if (TextUtils.isEmpty(myNote)) {
//                    note.setError("Required Feild...");
//                } else {
//                    int amountInt = Integer.parseInt(myAmount);
//                    String id = database.mDatabaseReference.push().getKey();
//                    String myDate = DateFormat.getDateInstance().format(new Date());
//                    DataPojo dataPojo = new DataPojo(id, myType, amountInt, myNote, myDate);
//                    database.mDatabaseReference.child(id).setValue(dataPojo);
//                    Toast.makeText(HomeActivity.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();
//                }
//            }
//        });
//
//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                database.mDatabaseReference.child(post_key).removeValue();
//                dialog.dismiss();
//
//            }
//        });
//
//    }
    private void displayCustomDialog() {
        //Custom Dialog
        AlertDialog.Builder myDialog = new AlertDialog.Builder(HomeActivity.this);
        LayoutInflater inflater = LayoutInflater.from(HomeActivity.this);
        View myView = inflater.inflate(R.layout.input_data_dialg, null);
        final AlertDialog dialog = myDialog.create();
        dialog.setView(myView);
        final EditText type = myView.findViewById(R.id.id_edit_type);
        final EditText amount = myView.findViewById(R.id.id_edit_amount);
        final EditText note = myView.findViewById(R.id.id_edit_note);
        Button save = myView.findViewById(R.id.id_save_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myType = type.getText().toString().trim();
                String myAmount = amount.getText().toString().trim();
                String myNote = note.getText().toString().trim();
                if (TextUtils.isEmpty(myType)) {
                    type.setError("Required Feild...");

                } else if (TextUtils.isEmpty(myAmount)) {
                    amount.setError("Required Feild...");

                } else if (TextUtils.isEmpty(myNote)) {
                    note.setError("Required Feild...");
                } else {
                    int amountInt = Integer.parseInt(myAmount);
                    String id = mDatabaseReference.push().getKey();
                    String myDate = DateFormat.getDateInstance().format(new Date());
                    DataPojo dataPojo = new DataPojo( id, myType, amountInt, myNote,myDate);
//                    database.mDatabaseReference.child(id).setValue(dataPojo);
                    homeViewModel.addToDataBase(dataPojo);
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out:
                mAuth.signOut();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}