package com.example.uiteste;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    private Button Logar;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance("https://abclub-30a87-default-rtdb.europe-west1.firebasedatabase.app").getReference("Users");

        Logar = (Button) findViewById(R.id.BtnLogin);
        Logar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {login();}
        });
    }

    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            VerificarRole();
        }
    }

    private void VerificarRole() {
        mDatabase.child(FirebaseAuth.getInstance().getUid()).child("role").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()) {
                    role = String.valueOf(task.getResult().getValue());

                    if (role.equals("admin")) {
                       startActivity(new Intent(getApplicationContext(), MenuAdmin.class));
                       finish();
                   }
                   if (role.equals("jogador")) {
                       startActivity(new Intent(getApplicationContext(), MenuJogador.class));
                       finish();
                   }
                   if (role.equals("treinador")) {
                       startActivity(new Intent(getApplicationContext(), MenuTreinador.class));
                       finish();
                   }
                }
                else {
                    Toast.makeText(Login.this, "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void login() {

        EditText CaixaNome = (EditText) findViewById(R.id.editTextTextPersonName);
        EditText CaixaPass = (EditText) findViewById((R.id.editTextTextPassword));

        String username = (String) CaixaNome.getText().toString();
        String pass = (String) CaixaPass.getText().toString();

        if (username.isEmpty()) {
            Toast.makeText(this, "Insira um username!", Toast.LENGTH_SHORT).show();
            CaixaPass.requestFocus();
            return;
        }

        if (pass.isEmpty()) {
            Toast.makeText(this, "Insira uma password!", Toast.LENGTH_SHORT).show();
            CaixaNome.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(username, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
              VerificarRole();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}