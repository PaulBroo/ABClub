package com.example.uiteste.InterfacesPrincipais;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.uiteste.R;
import com.example.uiteste.calendario.CalendarioMensal;
import com.example.uiteste.pagamentos.Quotas;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MenuJogador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_jogador);

        //Colocar a imagem de perfil
        StorageReference imagem = FirebaseStorage.getInstance().getReference
                ("profileImages/" + FirebaseAuth.getInstance().getUid());
        ImageView ImagemPerfil = findViewById(R.id.OpenPerfil);
        imagem.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(MenuJogador.this).load(uri.toString()).into(ImagemPerfil);
            }
        });
    }

    public void AbrirCalendario(View view) {
        startActivity(new Intent(getApplicationContext(), CalendarioMensal.class));
    }

    public void AbrirPerfil(View view) {
        startActivity(new Intent(getApplicationContext(), PerfilJogador.class));
    }

    public void AbrirAvisos(View view) {
    }

    public void AbrirFaltas(View view) {
    }

    public void AbrirPagamentos(View view) {
        startActivity(new Intent(getApplicationContext(), Quotas.class));
    }

    public void Logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }
}