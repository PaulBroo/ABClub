package com.example.uiteste.avisos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.uiteste.Data.Aviso;
import com.example.uiteste.InterfacesPrincipais.MenuTreinador;
import com.example.uiteste.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MenuAvisosTreinador extends AppCompatActivity {

    private static final String TAG = "MenuAvisosTreinador";

    private FirebaseAuth fAuth;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    private Aviso aviso;
    private ListView list;
    private Button adicionarAvisos;
    private Button editarAvisos;
    private Button eliminarAvisos;
    private Button voltar;
    private String showTitulo, showDescricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_avisos_treinador);

        adicionarAvisos = (Button) findViewById(R.id.BtnInserirAvisos);

        adicionarAvisos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdicionarAvisos.class));
                finish();
            }
        });

        editarAvisos = (Button) findViewById(R.id.btnEditarAvisos);

        editarAvisos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EditarAvisos.class));
                finish();
            }
        });

        eliminarAvisos = (Button) findViewById(R.id.btnEliminarAvisos);

        eliminarAvisos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EliminarAvisos.class));
                finish();
            }
        });

        voltar = (Button) findViewById(R.id.btnVoltar);

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MenuTreinador.class));
                finish();
            }
        });


    }

    protected void onStart(){
        super.onStart();
        setDados();
    }

    private void setDados() {
        DatabaseReference RootRef = FirebaseDatabase.getInstance("https://abclub-30a87-default-rtdb.europe-west1.firebasedatabase.app").getReference();
        DatabaseReference mAviso = RootRef.child("Avisos");

        list = (ListView) findViewById(R.id.ListViewAvisos);

        ArrayList<String> avisos = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, avisos);
        list.setAdapter(adapter);
        mAviso.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value = snapshot.getValue(Aviso.class).toString();
                avisos.add(value);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}