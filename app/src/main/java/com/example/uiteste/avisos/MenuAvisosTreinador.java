package com.example.uiteste.avisos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.uiteste.MenuTreinador;
import com.example.uiteste.R;

public class MenuAvisosTreinador extends AppCompatActivity {

    private Button adicionarAvisos;
    private Button editarAvisos;
    private Button eliminarAvisos;
    private Button voltar;

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
}