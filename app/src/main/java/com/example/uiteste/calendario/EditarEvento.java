package com.example.uiteste.calendario;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.uiteste.Data.Evento;
import com.example.uiteste.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class EditarEvento extends AppCompatActivity {
    private Spinner Equipas, Escaloes;
    private Button EscolherHora;
    private ToggleButton JogoToggle, TreinoToggle;
    private TextView data;
    private String DataParaDatabase, HoraEscolhida;
    private ArrayList<String> ArrayEscaloes, ArrayEquipas;
    private FirebaseDatabase rootNode;
    private DatabaseReference refRoot,refEquipas;
    private ArrayList<String> Pavilhoes;
    private String escalao, equipa, pavilhao;
    private int horas, minuto;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_evento);
        initWidgets();

        //Para o spinner de equipas
        ArrayEscaloes = new ArrayList<String>();
        PreencherEscaloes();
        PreencherPavilhoes();

        //Para o spinner de escalões
        ArrayEquipas = new ArrayList<String>();
        Escaloes = findViewById(R.id.EscolhaEscalao);
    }

    private void PreencherPavilhoes() {
        Pavilhoes = new ArrayList<>();
        DatabaseReference refPavilhoes = FirebaseDatabase.getInstance
                ("https://abclub-30a87-default-rtdb.europe-west1.firebasedatabase.app").getReference("Pavilhoes");
        refPavilhoes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot pavilhao : snapshot.getChildren()) {
                    Pavilhoes.add(pavilhao.getKey());
                }
                Spinner pavilhoes = findViewById(R.id.EscolherPavilhao);
                ArrayAdapter<String> AdapterPav = new ArrayAdapter<>
                        (EditarEvento.this, android.R.layout.simple_spinner_item, Pavilhoes);
                AdapterPav.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                pavilhoes.setAdapter(AdapterPav);
                pavilhao = pavilhoes.getItemAtPosition(0).toString();
                pavilhoes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                        pavilhao = pavilhoes.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void PreencherSpinnerEscaloes(ArrayList<String> arrayEquipas) {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>
                (EditarEvento.this, android.R.layout.simple_spinner_item, arrayEquipas);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Escaloes.setAdapter(spinnerAdapter);
        escalao = Escaloes.getItemAtPosition(0).toString();
        Escaloes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                escalao = adapterView.getItemAtPosition(position).toString();

                PreencherEquipas(ArrayEquipas);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void PreencherSpinnerEquipas(ArrayList<String> arrayEquipas) {
        ArrayAdapter<CharSequence> spinnerAdapter = new ArrayAdapter
                (this,android.R.layout.simple_spinner_item, arrayEquipas);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.notifyDataSetChanged();
        Equipas.setAdapter(spinnerAdapter);
        Equipas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                equipa = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initWidgets() {
        data = findViewById(R.id.ShowDataMarcacao);
        DataParaDatabase = CalendarUtils.formattedDate(CalendarUtils.selectedDate);
        data.setText(DataParaDatabase);
        rootNode = FirebaseDatabase.getInstance("https://abclub-30a87-default-rtdb.europe-west1.firebasedatabase.app");
        refRoot = rootNode.getReference();
        refEquipas = rootNode.getReference().child("Equipas");
        Equipas = findViewById(R.id.EscolhaEquipa);
        Escaloes = findViewById(R.id.EscolhaEscalao);
        EscolherHora = findViewById(R.id.EscolherHoraMarcacao);
        JogoToggle = findViewById(R.id.JogoToggle);
        TreinoToggle = findViewById(R.id.TreinoToggle);
        TreinoToggle.setChecked(true);
    }

     public void PreencherEscaloes() {
        refEquipas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayEscaloes.clear();

                for (DataSnapshot escalao : snapshot.getChildren()) {
                    ArrayEscaloes.add(escalao.getKey());
                }

                PreencherSpinnerEscaloes(ArrayEscaloes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditarEvento.this, "Erro a popular spinner de equipas!", Toast.LENGTH_SHORT).show();
            }
        });
        Log.d("tag", ArrayEscaloes.toString());
    }

    private void PreencherEquipas(ArrayList<String> array) {
        refEquipas.child(escalao).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                array.clear();

                for (DataSnapshot equipa : snapshot.getChildren()) {
                    array.add(equipa.getKey());
                }
                PreencherSpinnerEquipas(ArrayEquipas);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void EscolherHora(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hora, int minutos) {
                horas = hora;
                minuto = minutos;
                HoraEscolhida = String.format(Locale.getDefault(), "%02d:%02d", horas, minutos);
                EscolherHora.setText(HoraEscolhida);
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, horas, minuto, true);

        timePickerDialog.setTitle("Escolher Hora");
        timePickerDialog.show();
    }

    public void AbrirConvocatoria(View view) {
        Intent intent = new Intent(this, ConvocatoriaTreinador.class);
        Bundle extras = new Bundle();
        extras.putString("Escalao", escalao);
        extras.putString("Equipa", equipa);
        extras.putString("Data", DataParaDatabase);

        intent.putExtras(extras);
        startActivity(intent);
    }

    public void EscolherTipoTreino(View view) {
        JogoToggle.setChecked(false);
        TreinoToggle.setChecked(true);
    }

    public void EscolherTipoJogo(View view) {
        JogoToggle.setChecked(true);
        TreinoToggle.setChecked(false);
    }

    public void SalvarEvento(View view) {
        if (HoraEscolhida == null) {
            Toast.makeText(this, "Por favor selecione uma hora!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (CalendarUtils.idsConvocados.size() == 0) {
            Toast.makeText(this, "Convoque alguns jogadores primeiro!", Toast.LENGTH_SHORT).show();
            return;
        }
        String tipo;
        if (TreinoToggle.isChecked())
            tipo = "Treino";
        else
            tipo = "Jogo";
        Evento evento = new Evento(tipo, pavilhao, HoraEscolhida);
        refRoot.child("Calendario").child(DataParaDatabase).child(escalao).child("Info").setValue(evento);

        //Iterar a arrayList para colocar os convocados na database
        for (String id : CalendarUtils.idsConvocados){
            refRoot.child("Calendario").child(DataParaDatabase).child(escalao).child
                    ("Convocados").child(id).setValue(0);
        }
        finish();
    }
}

