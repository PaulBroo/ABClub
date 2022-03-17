package com.example.uiteste;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.uiteste.avisos.MenuAvisosTreinador;
import com.google.firebase.auth.FirebaseAuth;

public class MenuTreinador extends AppCompatActivity {

    private Button Logout, Avisos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_treinador);

        Logout = (Button) findViewById(R.id.btnLogoutTreinador);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });

        Avisos = (Button) findViewById(R.id.btnAvisos);
        Avisos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MenuAvisosTreinador.class));
                finish();
            }
        });
    }

    public void AbrirTreinos(View view) {
            startActivity(new Intent(getApplicationContext(), TreinosTreinador.class));
    }
}