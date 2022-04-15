package com.example.uiteste.InterfacesPrincipais;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.uiteste.R;
import com.example.uiteste.avisos.MenuAvisosTreinador;
import com.example.uiteste.calendario.CalendarioMensal;
import com.google.firebase.auth.FirebaseAuth;

public class MenuTreinador extends AppCompatActivity {

    private Button Logout, Avisos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_treinador);
    }

    public void AbrirTreinos(View view) {
        startActivity(new Intent(getApplicationContext(), CalendarioMensal.class));
    }

    public void Logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

    public void AbrirAvisos(View view) {
        startActivity(new Intent(getApplicationContext(), MenuAvisosTreinador.class));
    }

    public void AbrirRegistoFaltas(View view) {
    }
}