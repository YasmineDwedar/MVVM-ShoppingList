package com.example.myshoppinglist.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myshoppinglist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private TextView signUp;
    private Button btnLogIn;
    private ProgressDialog mDialog;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.id_login_email);
        password = findViewById(R.id.id_login_password);
        btnLogIn = findViewById(R.id.id_login_btn);
        signUp = findViewById(R.id.id_signup_text);
        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);
        if (mAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));

            }
        });
       btnLogIn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String mEmail = email.getText().toString().trim();
               String mPassowrd = password.getText().toString().trim();
               if (TextUtils.isEmpty(mEmail)) {
                   email.setError("Required Field..");
               } else if (TextUtils.isEmpty(mPassowrd)) {
                   password.setError("Required Field..");
               }
               mDialog.setMessage("Processing..");
               mDialog.show();
               mAuth.signInWithEmailAndPassword(mEmail,mPassowrd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()) {
                           startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                           Toast.makeText(getApplicationContext(), "Successful !", Toast.LENGTH_SHORT).show();
                           mDialog.dismiss();
                       } else {
                           Toast.makeText(getApplicationContext(), "Login Failed..", Toast.LENGTH_SHORT).show();
                           mDialog.dismiss();
                       }
                   }
               });
           }
       });
    }
}