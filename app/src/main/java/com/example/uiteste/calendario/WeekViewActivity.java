package com.example.uiteste.calendario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Carousel;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.uiteste.calendario.CalendarUtils.daysInWeekArray;
import static com.example.uiteste.calendario.CalendarUtils.monthYearFromDate;

import com.example.uiteste.Data.Evento;
import com.example.uiteste.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WeekViewActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView MesSelecionado;
    private RecyclerView CalendarioTreinador;
    private RecyclerView ListaEventos;
    private CheckBox CheckBoxFiltro;
    private HashMap<String, Evento> eventos = new HashMap<>();
    private ArrayList<String> escaloesPorOrdem = new ArrayList<>();
    private ArrayList<String> DiasComTreinos = new ArrayList<>();
    private ArrayList<String> DiasComJogos = new ArrayList<>();
    private String dia;
    private Boolean Filtrado;
    private DatabaseReference ref = FirebaseDatabase.getInstance
            ("https://abclub-30a87-default-rtdb.europe-west1.firebasedatabase.app").getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        initWidgets();
        VerificarRole();
        PreencherLista();
    }

    public void PreencherLista(){
        if (Filtrado)
            getDiasComEventosPessoais();
        else {
            getDiasComEventos();
        }
    }

    private void VerificarRole() {
        Button CriarMarcacao = findViewById(R.id.NovaMarcacao);

        ref.child("Users").child(FirebaseAuth.getInstance().getUid()).child("role").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()) {
                    String role = String.valueOf(task.getResult().getValue());

                    if (role.equals("jogador"))
                        CriarMarcacao.setVisibility(View.GONE);
                }
            }
        });
    }

    private void getDiasComEventosPessoais() {
        String id = FirebaseAuth.getInstance().getUid();
        DatabaseReference refCalendario = FirebaseDatabase.getInstance
                ("https://abclub-30a87-default-rtdb.europe-west1.firebasedatabase.app").getReference().
                child("Calendario");
        refCalendario.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DiasComTreinos.clear();
                DiasComJogos.clear();
                for (DataSnapshot dia : snapshot.getChildren()) {
                    for (DataSnapshot evento : dia.getChildren()) {
                        if (evento.child("Convocados").hasChild(id)) {
                            if (evento.child("Info").child("tipo").getValue().equals("Jogo"))
                                DiasComJogos.add(dia.getKey());
                            else
                                DiasComTreinos.add(dia.getKey());
                        }
                        setWeekView();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getDiasComEventos() {
        DatabaseReference refCalendario = FirebaseDatabase.getInstance
                ("https://abclub-30a87-default-rtdb.europe-west1.firebasedatabase.app").getReference().
                child("Calendario");
        refCalendario.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DiasComTreinos.clear();
                DiasComJogos.clear();
                for (DataSnapshot dia : snapshot.getChildren()) {
                    for (DataSnapshot evento : dia.getChildren()) {
                        if (evento.child("Info").child("tipo").getValue().equals("Jogo"))
                            DiasComJogos.add(dia.getKey());
                        else
                            DiasComTreinos.add(dia.getKey());
                    }
                }
                setWeekView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void initWidgets()
    {
        CalendarioTreinador = findViewById(R.id.calendarRecyclerView);
        MesSelecionado = findViewById(R.id.monthYearTV);
        ListaEventos = findViewById(R.id.EventosDoDia);
        CheckBoxFiltro = findViewById(R.id.CheckBoxFiltroCalendario);

        Bundle extras = getIntent().getExtras();
        Filtrado = extras.getBoolean("Filtrado");
    }

    private void setListaEventos() {
        escaloesPorOrdem.clear();
        eventos.clear();
        dia = CalendarUtils.formattedDate(CalendarUtils.selectedDate);


        //Popular hashMap a partir da database
        DatabaseReference refEventos = FirebaseDatabase.getInstance
                ("https://abclub-30a87-default-rtdb.europe-west1.firebasedatabase.app").getReference().
                child("Calendario").child(dia);
        refEventos.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                escaloesPorOrdem.clear();
                eventos.clear();
                for (DataSnapshot Evento : snapshot.getChildren()){
                        String escalao = Evento.getKey();
                        com.example.uiteste.Data.Evento evento = new Evento(Evento.child("Info").child("tipo").getValue().toString(),
                                Evento.child("Info").child("pavilhao").getValue().toString(),
                                Evento.child("Info").child("hora").getValue().toString());
                        eventos.put(escalao, evento);
                        escaloesPorOrdem.add(escalao);
                }
                setEventAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setListaEventosPessoais() {
        dia = CalendarUtils.formattedDate(CalendarUtils.selectedDate);
        String id = FirebaseAuth.getInstance().getUid();

        //Popular hashMap a partir da database
        DatabaseReference refEventos = FirebaseDatabase.getInstance
                ("https://abclub-30a87-default-rtdb.europe-west1.firebasedatabase.app").getReference().
                child("Calendario").child(dia);
        refEventos.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                escaloesPorOrdem.clear();
                eventos.clear();
                for (DataSnapshot Evento : snapshot.getChildren()){
                    if (Evento.child("Convocados").hasChild(id)) {
                        String escalao = Evento.getKey();
                        com.example.uiteste.Data.Evento evento = new Evento(Evento.child("Info").child("tipo").getValue().toString(),
                                Evento.child("Info").child("pavilhao").getValue().toString(),
                                Evento.child("Info").child("hora").getValue().toString());
                        eventos.put(escalao, evento);
                        escaloesPorOrdem.add(escalao);
                    }
                    setEventAdapter();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setEventAdapter() {
        AdapterEventos adapterEventos = new AdapterEventos(this,
                eventos, escaloesPorOrdem);
        RecyclerView.LayoutManager layoutManagerEventos = new LinearLayoutManager(this);
        ListaEventos.setLayoutManager(layoutManagerEventos);
        ListaEventos.setAdapter(adapterEventos);
    }

    private void setWeekView()
    {
        MesSelecionado.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this, DiasComTreinos, DiasComJogos);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        CalendarioTreinador.setLayoutManager(layoutManager);
        CalendarioTreinador.setAdapter(calendarAdapter);
        if (Filtrado)
            setListaEventosPessoais();
        else
            setListaEventos();
    }


    public void previousWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        CalendarUtils.selectedDate = date;
        PreencherLista();
    }

    public void newEventAction(View view)
    {
        CalendarUtils.idsConvocados.clear();
        startActivity(new Intent(this, EditarEvento.class));
    }
}

