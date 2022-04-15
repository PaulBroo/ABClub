package com.example.uiteste.avisos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.uiteste.avisos.AdapterShowAviso;
import com.example.uiteste.Data.Aviso;
import com.example.uiteste.Data.Jogador;
import com.example.uiteste.Data.Metodos;
import com.example.uiteste.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EliminarAvisos extends AppCompatActivity {

    private Button voltar;
    private Button eliminar;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AdapterShowAviso adapter;
    private ArrayList<Aviso> avisos = new ArrayList<>();
    private ArrayList<Aviso> avisosRemover = new ArrayList<>();

    DatabaseReference RootRef = FirebaseDatabase.getInstance("https://abclub-30a87-default-rtdb.europe-west1.firebasedatabase.app").getReference();
    DatabaseReference mAviso = RootRef.child("Avisos");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_avisos);

        PreencherLista();

        new Handler().postDelayed(() -> {
            CriarRecycleView();
        }, 200);


        eliminar = (Button) findViewById(R.id.btnDelete);

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avisosRemover = adapter.getAvisos();
                mAviso.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (Aviso a : avisos) {
                            if (a.getSelecionado()) {
                                snapshot.getRef().child(a.getId()).removeValue();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Intent intphto = new Intent(getApplicationContext(), MenuAvisosTreinador.class);
                startActivity(intphto);
            }
        });

        voltar = (Button) findViewById(R.id.btnVoltar3);

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MenuAvisosTreinador.class));
                finish();
            }
        });
    }

    private void CriarRecycleView() {
        recyclerView = findViewById(R.id.ListaAvisosEliminar);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdapterShowAviso(this, avisos);
        recyclerView.setAdapter(adapter);
    }

    private void PreencherLista() {
        // Preencher a lista com todos os avisos
        mAviso.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                avisos.clear();

                for (DataSnapshot aviso : snapshot.getChildren()) {
                    Aviso value = aviso.getValue(Aviso.class);
                    avisos.add(value);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EliminarAvisos.this, "Erro a popular!", Toast.LENGTH_SHORT).show();
            }
        });
        //Preencher a lista com os avisos na lista de avisos que estão selecionados para serem eliminados
        mAviso.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot id: snapshot.getChildren()) {
                    if (!TemID(id.getKey())) {
                        Aviso novoAviso = Metodos.ProcurarAvisoPorId(id.getKey());
                        avisosRemover.add(novoAviso);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private boolean TemID(String id) {
        for (Aviso a : avisos) {
            if (a.getId().equals(id)){
                return true;
            }
        }
        return false;
    }
}