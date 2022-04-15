package com.example.uiteste.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.uiteste.Data.Jogador;
import com.example.uiteste.R;
import com.example.uiteste.calendario.AdapterConvocatoria;
import com.example.uiteste.calendario.EditarEvento;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListaDeJogadoresAdmin extends AppCompatActivity {
    private Spinner Escaloes;
    private String escalao;
    private ArrayList<Jogador> jogadoresParaALista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_jogadores_admin);
        initWidgets();
        PreencherSpinnerEscaloes();
    }

    private void initWidgets() {
        Escaloes = findViewById(R.id.SpinnerFiltroEscaloes);
    }

    private void PreencherSpinnerEscaloes() {
        ArrayList<String> arrayEscaloes = new ArrayList<>();
        arrayEscaloes.add("Todos");
        DatabaseReference refEquipas = FirebaseDatabase.getInstance
                ("https://abclub-30a87-default-rtdb.europe-west1.firebasedatabase.app").getReference("Equipas");

        refEquipas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot escalao: snapshot.getChildren()) {
                    arrayEscaloes.add(escalao.getKey());
                }
                PreencherSpinnerEscaloes2(arrayEscaloes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void PreencherSpinnerEscaloes2(ArrayList arrayEscaloes) {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>
                (ListaDeJogadoresAdmin.this, android.R.layout.simple_spinner_item, arrayEscaloes);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Escaloes.setAdapter(spinnerAdapter);
        escalao = Escaloes.getItemAtPosition(0).toString();
        Escaloes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                escalao = adapterView.getItemAtPosition(position).toString();
                PreencherListaJogadores();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
        PreencherListaJogadores();
    }

    private void PreencherListaJogadores() {
        jogadoresParaALista = new ArrayList<>();
        jogadoresParaALista.clear();

        DatabaseReference refEquipas = FirebaseDatabase.getInstance
                ("https://abclub-30a87-default-rtdb.europe-west1.firebasedatabase.app").getReference("Equipas");

        refEquipas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (escalao.equals("Todos")) {
                    for (DataSnapshot escalao : snapshot.getChildren()) {
                        for (DataSnapshot equipa : escalao.getChildren()) {
                            for (DataSnapshot jogador : equipa.getChildren()) {
                                Jogador player = new Jogador(jogador.child("primeiro_Nome").getValue() + " " +
                                        jogador.child("ultimo_Nome").getValue(), jogador.child("escalao").getValue().toString(),
                                        jogador.child("equipa").getValue().toString(), jogador.getKey());
                                jogadoresParaALista.add(player);
                                CriarRecycleView();
                            }
                        }
                    }
                } else {
                        for (DataSnapshot equipa : snapshot.child(escalao).getChildren()) {
                            for (DataSnapshot jogador : equipa.getChildren()) {
                                Jogador player = new Jogador(jogador.child("primeiro_Nome").getValue() + " " +
                                        jogador.child("ultimo_Nome").getValue(), jogador.child("escalao").getValue().toString(),
                                        jogador.child("equipa").getValue().toString(), jogador.getKey());
                                jogadoresParaALista.add(player);
                                CriarRecycleView();
                            }
                        }

                    }
                }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    private void CriarRecycleView() {
        RecyclerView recyclerView = findViewById(R.id.ListaJogadoresAdmin);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        AdapterListaJogadores adapter = new AdapterListaJogadores(ListaDeJogadoresAdmin.this, jogadoresParaALista);
        recyclerView.setAdapter(adapter);
    }
}