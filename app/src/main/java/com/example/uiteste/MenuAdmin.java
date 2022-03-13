package com.example.uiteste;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MenuAdmin extends AppCompatActivity {

    private Button AbrirCriar;
    private Button Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        AbrirCriar = (Button) findViewById(R.id.BtnRegistar);
        AbrirCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Criar();
            }
        });

        Logout = (Button)  findViewById(R.id.BtnLogoutAdmin);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });
    }

    private void Criar() {
        Intent intent = new Intent(this, CriarUtilizador.class);
        startActivity(intent);
    }
}