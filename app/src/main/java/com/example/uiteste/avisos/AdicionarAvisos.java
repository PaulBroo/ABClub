package com.example.uiteste.avisos;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.uiteste.Data.Aviso;
import com.example.uiteste.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class AdicionarAvisos extends AppCompatActivity {

    private FirebaseAuth fAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    private EditText title, description;
    private Button voltar;
    private Button adicionar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_avisos);


        adicionar = (Button) findViewById(R.id.btnAdicionar);

        adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdicionarAviso(); //VERIFICAR ISTO !!!
            }
        });

        voltar = (Button) findViewById(R.id.btnVoltar2);

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MenuAvisosTreinador.class));
                finish();
            }
        });

    }

    public void AdicionarAviso(){
        title = findViewById(R.id.editTextTitulo);
        description = findViewById(R.id.editTextDescricao);

        String Titulo = title.getText().toString().trim();
        String Descricao = description.getText().toString().trim();

        fAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseDatabase.getInstance("https://abclub-30a87-default-rtdb.europe-west1.firebasedatabase.app");
        reference = rootNode.getReference("Avisos");
        String id = reference.push().getKey();
        Aviso helperclass = new Aviso(id, Titulo, Descricao);

        reference.child(id).setValue(helperclass);
        Toast.makeText(this, "Aviso Criado!", Toast.LENGTH_SHORT).show();
        Intent intphto = new Intent(getApplicationContext(), MenuAvisosTreinador.class);
        startActivity(intphto);
    }
}