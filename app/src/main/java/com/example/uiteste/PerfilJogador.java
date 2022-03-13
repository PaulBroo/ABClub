package com.example.uiteste;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uiteste.Data.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class PerfilJogador extends AppCompatActivity {
    private User user;
    private TextView showNome, showEscalao, showData, showEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_jogador);
    }

    protected void onStart(){
        super.onStart();
        DatabaseReference RootRef = FirebaseDatabase.getInstance("https://abclub-30a87-default-rtdb.europe-west1.firebasedatabase.app").getReference();
        DatabaseReference mUser = RootRef.child("Users").child(FirebaseAuth.getInstance().getUid());
        showNome = (TextView) findViewById(R.id.NomeJogador);
        showEscalao = (TextView) findViewById(R.id.ShowEscalao);
        showData = (TextView) findViewById(R.id.ShowDataNasc);
        showEmail = (TextView) findViewById(R.id.EmailJogador);

        mUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            User CurrentUser = snapshot.getValue(User.class);
            user = CurrentUser;
            SetDados();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PerfilJogador.this, "Erro: " , Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void SetDados() {
        showNome.setText(user.getPrimeiro_Nome() + user.getUltimo_Nome());
        showEscalao.setText(user.getEscalao());
        showData.setText(user.getDia() + "/" + user.getMes() + "/" + user.getAno());
        showEmail.setText(user.getEmail());
    }

}