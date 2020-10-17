package com.example.myshoppinglist.Adaptor;


import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshoppinglist.Database.DataBase;
import com.example.myshoppinglist.Model.DataPojo;
import com.example.myshoppinglist.R;
import com.example.myshoppinglist.Ui.HomeActivity;
import com.example.myshoppinglist.ViewModel.HomeViewModel;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private String xtype, post_key, xnote;
    private int xamount;
    private Context context;
    private DataBase database;
    private List<DataPojo> list;

    public DataAdapter(Context context, List<DataPojo> pojos) {
        this.context = context;
        this.list = pojos;
        database = new DataBase(context);

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false);
        return new DataAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DataAdapter.ViewHolder holder, final int position) {
        holder.note.setText(list.get(position).getNote());
        holder.date.setText(list.get(position).getDate());
        holder.type.setText(list.get(position).getType());
        holder.amount.setText(String.valueOf(list.get(position).getAmount()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post_key = list.get(position).getId();
                xtype = list.get(position).getType();
                xamount = list.get(position).getAmount();
                xnote = list.get(position).getNote();
                Toast.makeText(context, list.get(position).getId(), Toast.LENGTH_SHORT).show();
                displayCustomUpadateDialog();
            }
        });

    }

    public void displayCustomUpadateDialog() {
        //Custom Upadte Dialog
        AlertDialog.Builder myDialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View myView = inflater.inflate(R.layout.update_input_feild, null);
        final AlertDialog dialog = myDialog.create();
        dialog.setView(myView);
        final EditText type = myView.findViewById(R.id.id_update_type);
        final EditText amount = myView.findViewById(R.id.id_update_amount);
        final EditText note = myView.findViewById(R.id.id_update_note);
        Button update = myView.findViewById(R.id.id_save_updte_button);
        Button delete = myView.findViewById(R.id.id_delete_updte_button);

        type.setText(xtype);
        type.setSelection(xtype.length());          //       setSelection set the EditText Cursor to the end of its text

        amount.setText(String.valueOf(xamount));
        amount.setSelection(String.valueOf(xamount).length());

        note.setText(xnote);
        note.setSelection(xnote.length());

        update.setOnClickListener(new View.OnClickListener() {
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
                    String myDate = DateFormat.getDateInstance().format(new Date());
                    DataPojo dataPojo = new DataPojo( post_key,myType, amountInt, myNote, myDate);
                    database.mDatabaseReference.child(post_key).setValue(dataPojo);
                    Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.mDatabaseReference.child(post_key).removeValue();
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView date, type, amount, note;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.id_date);
            type = itemView.findViewById(R.id.id_type);
            amount = itemView.findViewById(R.id.id_amount);
            note = itemView.findViewById(R.id.id_note);


        }
    }

}
