package com.example.uiteste.calendario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.uiteste.Data.Jogador;
import com.example.uiteste.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConvocatoriaTreinador extends AppCompatActivity {

    private String escalao, equipa, data;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AutoCompleteTextView ProcurarNomeJogador;
    private AdapterConvocatoria adapter;
    private ArrayList<Jogador> jogadoresParaALista = new ArrayList<>();
    private ArrayList<Jogador> jogadores = new ArrayList<>();
    private ArrayList<Jogador> jogadoresParaAdicionar;
    private Jogador JogadorAAdicionar;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convocatoria_treinador);
        initWidgets();
        PreencherListas();
        PreencherCaixaProcura();

    }



    private void PreencherCaixaProcura() {
        jogadoresParaAdicionar = new ArrayList<>();

        //Get all jogadores
        DatabaseReference refUsers = FirebaseDatabase.getInstance
                ("https://abclub-30a87-default-rtdb.europe-west1.firebasedatabase.app").getReference("Users");
        refUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot user: snapshot.getChildren()) {
                    if (user.child("role").getValue().equals("jogador")){
                        Jogador jogador = new Jogador(user.child("primeiro_Nome").getValue().toString() + " " +
                                user.child("ultimo_Nome").getValue().toString(), user.child("escalao").getValue().toString(),
                                user.child("equipa").getValue().toString(), user.getKey());
                        jogadoresParaAdicionar.add(jogador);
                    }
                }
                PreencherCaixaProcura2();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void PreencherCaixaProcura2(){
        ArrayAdapter<Jogador> adapterCaixaTexto = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, jogadoresParaAdicionar);
        ProcurarNomeJogador.setThreshold(1);
        ProcurarNomeJogador.setAdapter(adapterCaixaTexto);
        ProcurarNomeJogador.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
               JogadorAAdicionar = (Jogador) adapterView.getItemAtPosition(position);
           }
        });
    }

    private void PreencherListas() {
        // Preencher a lista com todos os jogadores do escalão escolhido
        ref.child("Equipas").child(escalao).child(equipa).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                jogadoresParaALista.clear();
                String Pnome, Unome;

                for (DataSnapshot jogador : snapshot.getChildren()) {
                    Pnome = jogador.child("primeiro_Nome").getValue().toString();
                    Unome = jogador.child("ultimo_Nome").getValue().toString();
                    jogadoresParaALista.add(new Jogador(Pnome + " " + Unome, escalao, equipa, jogador.getKey()));
                }
                PreencherListas2();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ConvocatoriaTreinador.this, "Erro a popular spinner de equipas!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void PreencherListas2() {
        //Preencher a lista com os jogadores que foram convocados numa iteração anterior
        for (String IdJogador : CalendarUtils.idsConvocados) {
            RemoverJogadorComId(IdJogador);
            ref.child("Users").child(IdJogador).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Jogador jogador = new Jogador(snapshot.child("primeiro_Nome").getValue() + " " +
                            snapshot.child("ultimo_Nome").getValue(), snapshot.child("escalao").getValue().toString(),
                            snapshot.child("equipa").getValue().toString(), snapshot.getKey());
                    jogador.setConvocado(true);
                    jogadoresParaALista.add(jogador);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        CriarRecycleView();
    }

    //Remover da lista o jogador com aquele id se o jogador com aquele id já está na lista de convocados
    private void RemoverJogadorComId(String id) {
        ArrayList<Jogador> JogadoresParaRemoverDaArrayList = new ArrayList();
        for (Jogador j : jogadoresParaALista) {
            if (j.getId().equals(id)){
                JogadoresParaRemoverDaArrayList.add(j);
            }
        }
        jogadoresParaALista.removeAll(JogadoresParaRemoverDaArrayList);
    }

    private void CriarRecycleView() {
        recyclerView = findViewById(R.id.ListaConvocatoria);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdapterConvocatoria(this, jogadoresParaALista, true);
        recyclerView.setAdapter(adapter);
    }

    private void initWidgets() {
        ref = FirebaseDatabase.getInstance
                ("https://abclub-30a87-default-rtdb.europe-west1.firebasedatabase.app").getReference();
        Bundle extras = getIntent().getExtras();
        escalao = extras.getString("Escalao");
        equipa = extras.getString("Equipa");
        data = extras.getString("Data");
        ProcurarNomeJogador = findViewById(R.id.NomeJogadorProcuradoConvocatoria);
    }

    public void RegistarConvocados(View view) {
        jogadores = adapter.getJogadores();
        CalendarUtils.idsConvocados.clear();
        for (Jogador j : jogadores) {
            if (j.IsConvocado())
                CalendarUtils.idsConvocados.add(j.getId());
        }
        finish();
    }

    public void AdicionarJogadorLista(View view) {
        if (JogadorAAdicionar != null) {
            jogadoresParaALista.add(JogadorAAdicionar);
            CriarRecycleView();
        }
        else
            Toast.makeText(this, "Escreva o nome do jogador que pretende adicionar!", Toast.LENGTH_SHORT).show();
    }
}