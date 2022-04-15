package com.example.uiteste.Data;

import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.uiteste.Data.Jogador;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class Metodos {

    //A partir de um id de User retorna um objeto Jogador
    public static Jogador ProcurarJogadorPorId(String id) {
        Jogador jogador = null;
        //inicizaliza uma DatabaseReference para o path 'Users'
        DatabaseReference ref = FirebaseDatabase.getInstance
                ("https://abclub-30a87-default-rtdb.europe-west1.firebasedatabase.app").getReference("Users");
        ref.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Cria um objeto jogador com todos os values dentro do node 'Users/id'
                Jogador jogador = new Jogador(snapshot.child("primeiro_Nome").getValue() + " " +
                        snapshot.child("ultimo_Nome").getValue(), snapshot.child("escalao").getValue().toString(),
                        snapshot.child("equipa").getValue().toString(), id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return jogador;
    }

    //A partir de um id de Aviso retorna um objeto Aviso
    public static Aviso ProcurarAvisoPorId(String id) {
        Aviso aviso = null;
        //inicizaliza uma DatabaseReference para o path 'Avisos'
        DatabaseReference ref = FirebaseDatabase.getInstance
                ("https://abclub-30a87-default-rtdb.europe-west1.firebasedatabase.app").getReference("Avisos");
        ref.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Cria um objeto aviso com todos os values dentro do node 'aviso/id'
                Aviso aviso = new Aviso(id, snapshot.child("titulo").getValue().toString(), snapshot.child("descricao").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return aviso;
    }

}
