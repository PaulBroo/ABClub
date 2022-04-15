package com.example.uiteste.calendario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uiteste.InterfacesPrincipais.Login;
import com.example.uiteste.InterfacesPrincipais.MenuAdmin;
import com.example.uiteste.InterfacesPrincipais.MenuJogador;
import com.example.uiteste.InterfacesPrincipais.MenuTreinador;
import com.example.uiteste.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.Reference;
import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarioMensal extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private CheckBox checkboxFiltro;
    private TextView TextViewJogador;
    private ArrayList<String> DiasComTreinos = new ArrayList();
    private ArrayList<String> DiasComJogos = new ArrayList();
    private DatabaseReference DatabaseUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendario_mensal);
        initWidgets();
        verificarRole();
        CalendarUtils.selectedDate = LocalDate.now();
        PreencherCalendario();
    }

    private void verificarRole() {
        DatabaseUsers = FirebaseDatabase.getInstance
                ("https://abclub-30a87-default-rtdb.europe-west1.firebasedatabase.app").getReference("Users");
        DatabaseUsers.child(FirebaseAuth.getInstance().getUid()).child("role").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()) {
                    String role = String.valueOf(task.getResult().getValue());

                    if (role.equals("admin")) {
                        checkboxFiltro.setVisibility(View.GONE);
                        TextViewJogador.setVisibility(View.GONE);
                    }
                    if (role.equals("jogador")) {
                        checkboxFiltro.setVisibility(View.VISIBLE);
                        TextViewJogador.setVisibility(View.VISIBLE);
                    }
                    if (role.equals("treinador")) {
                        checkboxFiltro.setVisibility(View.GONE);
                        TextViewJogador.setVisibility(View.GONE);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        checkboxFiltro = findViewById(R.id.CheckBoxFiltroCalendario);
        TextViewJogador = findViewById(R.id.TextViewJogadorCalendario);

        checkboxFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreencherCalendario();
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
                        setMonthView();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getDiasComEventos() { ;
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
                setMonthView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void PreencherCalendario(){
        if (checkboxFiltro.isChecked())
            getDiasComEventosPessoais();
        else
            getDiasComEventos();
    }

    private void setMonthView()
    {
        monthYearText.setText(CalendarUtils.monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = CalendarUtils.daysInMonthArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this, DiasComTreinos, DiasComJogos);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    public void previousMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        PreencherCalendario();
    }

    public void nextMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        PreencherCalendario();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        if(date != null)
        {
            CalendarUtils.selectedDate = date;
            PreencherCalendario();
        }
    }

    public void weeklyAction(View view)
    {
        Intent intent = new Intent(this, com.example.uiteste.calendario.WeekViewActivity.class);
        Bundle extras = new Bundle();
        if (checkboxFiltro.isChecked())
            extras.putBoolean("Filtrado", true);
        else
            extras.putBoolean("Filtrado", false);
        intent.putExtras(extras);
        startActivity(intent);
    }
}








