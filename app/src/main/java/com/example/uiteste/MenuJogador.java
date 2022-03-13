package com.example.uiteste;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class MenuJogador extends AppCompatActivity {

    private ImageButton AbrirPerfil;
    private Button AbrirTreinos, AbrirPagamentos, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_jogador);

        AbrirPerfil = (ImageButton) findViewById(R.id.OpenPerfil);
        AbrirPerfil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PerfilJogador.class));
            }
        });

        AbrirTreinos = (Button) findViewById(R.id.BtnTreinos);
        AbrirTreinos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CalendarioPavilhoes.class));
            }
        });

        AbrirPagamentos = (Button) findViewById(R.id.BtnQuotas);
        AbrirPagamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MenuPagamentos.class));
                ;}
        });

        logout = findViewById(R.id.BtnLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });
    }
}