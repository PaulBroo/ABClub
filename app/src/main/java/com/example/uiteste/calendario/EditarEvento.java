package com.example.uiteste.calendario;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
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

import com.example.uiteste.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class EditarEvento extends AppCompatActivity {
    private Spinner Equipas, Escaloes;
    private Button EscolherHora;
    private TextView data;
    private LocalTime hora;
    private ArrayList<String> ArrayEscaloes, ArrayEquipas;
    private FirebaseDatabase rootNode;
    private DatabaseReference refEscaloes,refEquipas;
    private String escalao, equipa;
    private int horas, minuto;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_evento);
        initWidgets();
        data.setText(CalendarUtils.formattedDate(CalendarUtils.selectedDate));


        //Para o spinner de equipas
        ArrayEscaloes = new ArrayList<String>();
        PreencherEscaloes();

        new Handler().postDelayed(() -> {
            PreencherSpinnerEscaloes(ArrayEscaloes);
        }, 100);

        //Para o spinner de escalões
        ArrayEquipas = new ArrayList<String>();
        Escaloes = findViewById(R.id.EscolhaEscalao);
    }


    private void PreencherSpinnerEscaloes(ArrayList<String> arrayEquipas) {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>
                (EditarEvento.this, android.R.layout.simple_spinner_item, arrayEquipas);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Escaloes.setAdapter(spinnerAdapter);
        Escaloes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                escalao = adapterView.getItemAtPosition(position).toString();

                new Handler().postDelayed(() -> {
                    PreencherEquipas(ArrayEquipas);
                    PreencherSpinnerEquipas(ArrayEquipas);
                }, 100);
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
        rootNode = FirebaseDatabase.getInstance("https://abclub-30a87-default-rtdb.europe-west1.firebasedatabase.app");
        refEscaloes = rootNode.getReference();
        refEquipas = rootNode.getReference().child("Equipas");
        Equipas = findViewById(R.id.EscolhaEquipa);
        Escaloes = findViewById(R.id.EscolhaEscalao);
        EscolherHora = findViewById(R.id.EscolherHoraMarcacao);
    }

     public void PreencherEscaloes() {
        refEscaloes.child("Equipas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayEscaloes.clear();

                for (DataSnapshot escalao : snapshot.getChildren()) {
                    ArrayEscaloes.add(escalao.getKey());
                }
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void saveEventAction(View view) {
        //String eventName = eventNameET.getText().toString();
        //Event newEvent = new Event(eventName, CalendarUtils.selectedDate, time);
        //Event.eventsList.add(newEvent);
        finish();
    }

    public void EscolherHora(View view) {


        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hora, int minutos) {
                horas = hora;
                minuto = minutos;
                EscolherHora.setText(String.format(Locale.getDefault(), "%02d:%02d", horas, minutos));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, horas, minuto, true);

        timePickerDialog.setTitle("Escolher Hora");
        timePickerDialog.show();
    }
}

