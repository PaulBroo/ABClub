package com.example.uiteste.calendario;

import static com.example.uiteste.calendario.CalendarUtils.selectedDate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uiteste.Data.Evento;
import com.example.uiteste.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterEventos extends RecyclerView.Adapter<AdapterEventos.ViewHolder> {
    Context context;
    HashMap<String, Evento> eventos = new HashMap<>();
    ArrayList<String> escaloesPorOrdem = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView escalao, equipa, pavilhao, hora, tipo;
        Button AbrirConvocatoria;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            escalao = itemView.findViewById(R.id.EscalaoListaEventos);
            equipa = itemView.findViewById(R.id.EquipaListaEvento);
            pavilhao = itemView.findViewById(R.id.PavilhaoListaEvento);
            hora = itemView.findViewById(R.id.HoraListaEvento);
            tipo = itemView.findViewById(R.id.TipoListaEvento);
            AbrirConvocatoria = itemView.findViewById(R.id.AbrirConvocatoriaEventos);
        }
    }

    public AdapterEventos(Context context, HashMap<String, Evento> eventos, ArrayList<String> escaloesPorOrdem ) {
        this.context = context;
        this.eventos = eventos;
        this.escaloesPorOrdem = escaloesPorOrdem;

    }
    @NonNull
    @Override
    public AdapterEventos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_item_eventos, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterEventos.ViewHolder holder, int position) {
        String EscalaoAtual = escaloesPorOrdem.get(position);
        Evento evento = eventos.get(EscalaoAtual);

        holder.escalao.setText(EscalaoAtual);
        holder.pavilhao.setText(evento.getPavilhao());
        holder.hora.setText(evento.getHora());
        holder.equipa.setText("Equipa");
        holder.tipo.setText(evento.getTipo());

        holder.AbrirConvocatoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ConvocatoriaSemCheckbox.class);
                Bundle extras = new Bundle();
                extras.putString("Escalao", EscalaoAtual);
                //extras.putString("Equipa", evento.get);
                extras.putString("Data", CalendarUtils.formattedDate(selectedDate));

                intent.putExtras(extras);
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                DatabaseReference database = FirebaseDatabase.getInstance
                        ("https://abclub-30a87-default-rtdb.europe-west1.firebasedatabase.app").getReference();
                database.child("Calendario").child(CalendarUtils.formattedDate(selectedDate)).child(EscalaoAtual).removeValue();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return escaloesPorOrdem.size();
    }
}
