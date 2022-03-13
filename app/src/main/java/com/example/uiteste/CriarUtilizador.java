package com.example.uiteste;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uiteste.Data.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class CriarUtilizador extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private TextView showData;
    private EditText FirstName, LastName, Password, Email;
    private Spinner EscalaoSpinner, RoleSpinner;
    private Button criar;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private FirebaseAuth fAuth;
    private int anoNasc, mesNasc, diaNasc;
    private String escalao, role;

    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_utilizador);

        //Para o calendario
        showData = findViewById(R.id.MostrarData);
        findViewById(R.id.EscolherData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        //Para o spinner de escalões
        EscalaoSpinner = findViewById(R.id.EscaloesLista);
        ArrayAdapter<CharSequence> adapterE = ArrayAdapter.createFromResource(this,R.array.escalões, android.R.layout.simple_spinner_item);
        adapterE.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        EscalaoSpinner.setAdapter(adapterE);
        EscalaoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String text = adapterView.getItemAtPosition(position).toString();
                escalao = text;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Para o spinner de roles
        RoleSpinner = findViewById(R.id.RolesLista);
        ArrayAdapter<CharSequence> adapterR = ArrayAdapter.createFromResource(this,R.array.roles, android.R.layout.simple_spinner_item);
        adapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        RoleSpinner.setAdapter(adapterR);
        RoleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String text = adapterView.getItemAtPosition(position).toString();
                role = text;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Para o botão criar
        findViewById(R.id.BtnConcluir).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConcluirCriacao();
            }
        });

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        String date = "DATA --> " + day + "/" + (month + 1) + "/" + year;
        anoNasc = year;
        mesNasc = month + 1;
        diaNasc = day;
        showData.setText(date);
    }

    private void showDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }




    public void ConcluirCriacao() {
        FirstName = findViewById(R.id.PrimeiroNome);
        LastName = findViewById(R.id.UltimoNome);
        Password = findViewById(R.id.CriarPassword);
        Email = findViewById(R.id.EmailShowText);

        String PrimeiroNome = FirstName.getText().toString().trim();
        String UltimoNome = LastName.getText().toString().trim();
        String password = Password.getText().toString().trim();
        String email = Email.getText().toString().trim();

        fAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseDatabase.getInstance("https://abclub-30a87-default-rtdb.europe-west1.firebasedatabase.app");
        reference = rootNode.getReference("Users");
        User helperclass = new User(PrimeiroNome, UltimoNome, escalao, role,email , diaNasc, mesNasc, anoNasc);

        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    reference.child(task.getResult().getUser().getUid()).setValue(helperclass);
                    Toast.makeText(CriarUtilizador.this, "Utilizador Criado!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(CriarUtilizador.this, "Error ! " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}