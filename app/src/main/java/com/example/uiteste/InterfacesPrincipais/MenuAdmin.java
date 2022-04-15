package com.example.uiteste.InterfacesPrincipais;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.uiteste.R;
import com.example.uiteste.admin.CriarUtilizador;
import com.example.uiteste.admin.ListaDeJogadoresAdmin;
import com.google.firebase.auth.FirebaseAuth;

public class MenuAdmin extends AppCompatActivity {

    private Button AbrirCriar;
    private Button Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

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

    public void AbrirListaDeJogadores(View view) {
        Intent intent = new Intent(this, ListaDeJogadoresAdmin.class);
        startActivity(intent);
    }

    public void AbrirCriarUtilizador(View view) {
        Intent intent = new Intent(this, CriarUtilizador.class);
        startActivity(intent);
    }
}