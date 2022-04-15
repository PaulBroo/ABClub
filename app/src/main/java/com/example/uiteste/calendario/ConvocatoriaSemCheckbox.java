package com.example.uiteste.calendario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.uiteste.Data.Jogador;
import com.example.uiteste.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConvocatoriaSemCheckbox extends AppCompatActivity {
    private DatabaseReference ref;
    private String escalao, equipa, data;
    private ArrayList<Jogador> jogadoresParaALista;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AdapterConvocatoria adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convocatoria_sem_checkbox);
        initWidgets();
        PreencherLista();
    }

    private void initWidgets() {
        ref = FirebaseDatabase.getInstance
                ("https://abclub-30a87-default-rtdb.europe-west1.firebasedatabase.app").getReference();
        Bundle extras = getIntent().getExtras();
        escalao = extras.getString("Escalao");
        equipa = extras.getString("Equipa");
        data = extras.getString("Data");
    }

    private void PreencherLista() {
        jogadoresParaALista = new ArrayList<>();
        //Preencher a lista com os jogadores que foram convocados
        ref.child("Calendario").child(data).child(escalao).child("Convocados").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                jogadoresParaALista.clear();
                for (DataSnapshot IdJogador : snapshot.getChildren()) {
                    ref.child("Users").child(IdJogador.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Jogador jogador = new Jogador(snapshot.child("primeiro_Nome").getValue() + " " +
                                    snapshot.child("ultimo_Nome").getValue(), snapshot.child("escalao").getValue().toString(),
                                    snapshot.child("equipa").getValue().toString(), snapshot.getKey());
                            jogador.setConvocado(true);
                            jogadoresParaALista.add(jogador);
                            Log.d("tag", jogador.getNome());
                            CriarRecycleView();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void CriarRecycleView() {
        recyclerView = findViewById(R.id.ListaConvocatoriaSemCheckBox);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdapterConvocatoria(this, jogadoresParaALista, false);
        recyclerView.setAdapter(adapter);
    }
}