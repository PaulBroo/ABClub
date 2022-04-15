package com.example.uiteste.pagamentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.example.uiteste.R;
import com.example.uiteste.pagamentos.MenuPagamentos;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Quotas extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mbase;
    private DatabaseReference mDatabase;
    private String escalao;
    private Integer quotas;
    private HashMap<String, Long> map = new HashMap<>();
    private CheckBox caixajan,caixafev,caixamar,caixaabril,caixamaio,caixajun,
            caixajul,caixaago,caixaset,caixaout,caixanov,caixadez;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotas);
        initWidgets();

        mbase.child(FirebaseAuth.getInstance().getUid()).child("2022").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                    getquotas("2022");
                else
                    adicionarUser();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initWidgets() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance
                ("https://abclub-30a87-default-rtdb.europe-west1.firebasedatabase.app").getReference("Users");
        mbase = FirebaseDatabase.getInstance
                ("https://abclub-30a87-default-rtdb.europe-west1.firebasedatabase.app").getReference("Pagamentos");
    }

    public void adicionarUser() {
        mDatabase.child(FirebaseAuth.getInstance().getUid()).child("escalao").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                escalao = snapshot.getValue().toString();
                preencher();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void preencher(){
        if (escalao.equals("Seniores M") || escalao.equals("Seniores F")){
            CriarMeses(20);
        }
        else{
            CriarMeses(15);
        }
        getquotas("2022");
    }

    public void CriarMeses(int valor) {
        mbase.child(FirebaseAuth.getInstance().getUid()).child("2022").child("jan").setValue(valor);
        mbase.child(FirebaseAuth.getInstance().getUid()).child("2022").child("fev").setValue(valor);
        mbase.child(FirebaseAuth.getInstance().getUid()).child("2022").child("mar").setValue(valor);
        mbase.child(FirebaseAuth.getInstance().getUid()).child("2022").child("abril").setValue(valor);
        mbase.child(FirebaseAuth.getInstance().getUid()).child("2022").child("maio").setValue(valor);
        mbase.child(FirebaseAuth.getInstance().getUid()).child("2022").child("jun").setValue(valor);
        mbase.child(FirebaseAuth.getInstance().getUid()).child("2022").child("jul").setValue(valor);
        mbase.child(FirebaseAuth.getInstance().getUid()).child("2022").child("ago").setValue(valor);
        mbase.child(FirebaseAuth.getInstance().getUid()).child("2022").child("set").setValue(valor);
        mbase.child(FirebaseAuth.getInstance().getUid()).child("2022").child("out").setValue(valor);
        mbase.child(FirebaseAuth.getInstance().getUid()).child("2022").child("nov").setValue(valor);
        mbase.child(FirebaseAuth.getInstance().getUid()).child("2022").child("dez").setValue(valor);
    }

    public void getquotas(String ano) {

        mbase.child(mAuth.getUid()).child(ano).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                map.clear();
                quotas=0;
                for (DataSnapshot mes : snapshot.getChildren()) {
                    map.put(mes.getKey().toString(), (Long) mes.getValue());
                    quotas += ((Long) mes.getValue()).intValue();
                }
                AtualizarMes(ano);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void AtualizarMes(String ano) {
        caixajan = (CheckBox) findViewById(R.id.jan);
        if (!(map.get("jan") == 0)) {

            caixajan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(), MenuPagamentos.class);
                    intent.putExtra("mes","jan");
                    intent.putExtra("ano", ano);
                    intent.putExtra("quota",quotas.toString());
                    startActivity(intent);
                }
            });
        } else
            caixajan.setChecked(true);


        caixafev = (CheckBox) findViewById(R.id.fev);
        if (!(map.get("fev") == 0)) {

            caixafev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(), MenuPagamentos.class);
                    intent.putExtra("mes","fev");
                    intent.putExtra("ano", ano);
                    intent.putExtra("quota",quotas.toString());
                    startActivity(intent);
                }
            });
        } else
            caixafev.setChecked(true);


        caixamar = (CheckBox) findViewById(R.id.mar);
        if (!(map.get("mar") == 0)) {

            caixamar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(), MenuPagamentos.class);
                    intent.putExtra("mes","mar");
                    intent.putExtra("ano", ano);
                    intent.putExtra("quota",quotas.toString());
                    startActivity(intent);
                }
            });
        }else
            caixamar.setChecked(true);


        caixaabril = (CheckBox) findViewById(R.id.abril);
        if (!(map.get("abril") == 0)) {
            caixaabril.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(), MenuPagamentos.class);
                    intent.putExtra("mes","abril");
                    intent.putExtra("ano", ano);
                    intent.putExtra("quota",quotas.toString());
                    startActivity(intent);
                }
            });
        }else
            caixaabril.setChecked(true);

        caixamaio = (CheckBox) findViewById(R.id.maio);
        if (!(map.get("maio") == 0)) {
            caixamaio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(), MenuPagamentos.class);
                    intent.putExtra("mes","maio");
                    intent.putExtra("ano", ano);
                    intent.putExtra("quota",quotas.toString());
                    startActivity(intent);
                }

            });
        }else
            caixamaio.setChecked(true);


        caixajun = (CheckBox) findViewById(R.id.jun);
        if (!(map.get("jun") == 0)) {
            caixajun.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(), MenuPagamentos.class);
                    intent.putExtra("mes","jun");
                    intent.putExtra("ano", ano);
                    intent.putExtra("quota",quotas.toString());
                    startActivity(intent);
                }
            });
        }else
            caixajun.setChecked(true);

        caixajul = (CheckBox) findViewById(R.id.jul);
        if (!(map.get("jul") == 0)) {
            caixajul.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(), MenuPagamentos.class);
                    intent.putExtra("mes","jul");
                    intent.putExtra("ano", ano);
                    intent.putExtra("quota",quotas.toString());
                    startActivity(intent);
                }
            });
        }else
            caixajul.setChecked(true);

        caixaago = (CheckBox) findViewById(R.id.ago);
        if (!(map.get("ago") == 0)) {
            caixaago.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(), MenuPagamentos.class);
                    intent.putExtra("mes","ago");
                    intent.putExtra("ano", ano);
                    intent.putExtra("quota",quotas.toString());
                    startActivity(intent);
                }
            });
        }else
            caixaago.setChecked(true);

        caixaset = (CheckBox) findViewById(R.id.set);
        if (!(map.get("set") == 0)) {
            caixaset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(), MenuPagamentos.class);
                    intent.putExtra("mes","set");
                    intent.putExtra("ano", ano);
                    intent.putExtra("quota",quotas.toString());
                    startActivity(intent);
                }
            });
        }else
            caixaset.setChecked(true);


        caixaout = (CheckBox) findViewById(R.id.out);
        if (!(map.get("out") == 0)) {
            caixaout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(), MenuPagamentos.class);
                    intent.putExtra("mes","out");
                    intent.putExtra("ano", ano);
                    intent.putExtra("quota",quotas.toString());
                    startActivity(intent);
                }
            });
        }else
            caixaout.setChecked(true);

        caixanov = (CheckBox) findViewById(R.id.nov);
        if (!(map.get("nov") == 0)) {
            caixaout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(), MenuPagamentos.class);
                    intent.putExtra("mes","nov");
                    intent.putExtra("ano", ano);
                    intent.putExtra("quota",quotas.toString());
                    startActivity(intent);
                }
            });
        }else
            caixanov.setChecked(true);

        caixadez = (CheckBox) findViewById(R.id.dez);
        if (!(map.get("dez") == 0)) {
            caixadez.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(), MenuPagamentos.class);
                    intent.putExtra("mes","dez");
                    intent.putExtra("ano", ano);
                    intent.putExtra("quota",quotas.toString());
                    startActivity(intent);
                }
            });
        }else
            caixadez.setChecked(true);

    }
}
