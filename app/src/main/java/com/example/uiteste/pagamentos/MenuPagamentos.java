package com.example.uiteste.pagamentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.uiteste.InterfacesPrincipais.MenuJogador;
import com.example.uiteste.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuPagamentos extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mbase;
    private Button pagar;
    private TextView txtquota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pagamentos);

        Intent intent=getIntent();
        String mes= intent.getStringExtra("mes");
        String ano= intent.getStringExtra("ano");
        String quotas= intent.getStringExtra("quota");

        TextView txt = (TextView) findViewById((R.id.Txtquotas));
        Log.d("tag",quotas);
        txt.setText(quotas);

        mAuth = FirebaseAuth.getInstance();
        mbase = FirebaseDatabase.getInstance("https://abclub-30a87-default-rtdb.europe-west1.firebasedatabase.app").getReference("Pagamentos");

        pagar = (Button) findViewById(R.id.pagamento);
        pagar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mbase.child(FirebaseAuth.getInstance().getUid()).child(ano).child(mes).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        mbase.child(FirebaseAuth.getInstance().getUid()).child("2022").child(mes).setValue(0);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MenuJogador.class);
        startActivity(intent);
    }
}