package com.example.uiteste.avisos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.uiteste.avisos.AdapterShowAviso;
import com.example.uiteste.Data.Aviso;
import com.example.uiteste.Data.Metodos;
import com.example.uiteste.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditarAvisos extends AppCompatActivity {

    private Button voltar;
    private Button editar;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AdapterShowAviso adapter;
    private EditText edTxtNovoTitulo;
    private EditText edTxtNovaDescricao;
    private ArrayList<Aviso> avisos = new ArrayList<>();
    private ArrayList<Aviso> avisosEditar = new ArrayList<>();

    DatabaseReference RootRef = FirebaseDatabase.getInstance("https://abclub-30a87-default-rtdb.europe-west1.firebasedatabase.app").getReference();
    DatabaseReference mAviso = RootRef.child("Avisos");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_avisos);

        edTxtNovoTitulo = findViewById(R.id.editTextNovoTitulo);
        edTxtNovaDescricao = findViewById(R.id.editTextNovaDescricao);

        PreencherLista();

        new Handler().postDelayed(() -> {
            CriarRecycleView();
        }, 200);


        editar = (Button) findViewById(R.id.btnEditar);

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ADICIONAR METODO EDITAR AVISO SELECIONADO
                avisosEditar = adapter.getAvisos();
                String novoTitulo = edTxtNovoTitulo.getText().toString().trim();
                String novaDescricao = edTxtNovaDescricao.getText().toString().trim();
                mAviso.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (Aviso a : avisos) {
                            if (a.getSelecionado()) {
                                String sameId = a.getId();
                                snapshot.getRef().child(a.getId()).removeValue();
                                Aviso editAviso = new Aviso(sameId, novoTitulo, novaDescricao);
                                mAviso.child(sameId).setValue(editAviso);
                                Toast.makeText(EditarAvisos.this, "Aviso Editado", Toast.LENGTH_SHORT).show();
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

        voltar = (Button) findViewById(R.id.btnVoltar4);

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MenuAvisosTreinador.class));
                finish();
            }
        });
    }

    private void CriarRecycleView() {
        recyclerView = findViewById(R.id.ListaAvisosEditar);
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
                Toast.makeText(EditarAvisos.this, "Erro a popular!", Toast.LENGTH_SHORT).show();
            }
        });
        //Preencher a lista com os avisos na lista de avisos que estão selecionados para serem editados
        mAviso.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot id: snapshot.getChildren()) {
                    if (!TemID(id.getKey())) {
                        Aviso novoAviso = Metodos.ProcurarAvisoPorId(id.getKey());
                        avisosEditar.add(novoAviso);
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