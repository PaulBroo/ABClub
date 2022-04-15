package com.example.uiteste.avisos;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uiteste.Data.Aviso;
import com.example.uiteste.R;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class AdapterShowAviso extends RecyclerView.Adapter<AdapterShowAviso.ViewHolder> {
    Context context;
    ArrayList<Aviso> avisos;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView titulo, descricao;
        CheckBox checkbox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.TituloAviso);
            descricao = itemView.findViewById(R.id.DescricaoAviso);
            checkbox = itemView.findViewById(R.id.AvisoCheckBox);
        }
    }

    public AdapterShowAviso(Context context, ArrayList avisos) {
        this.context = context;
        this.avisos = avisos;
    }

    @NonNull
    @Override
    public AdapterShowAviso.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.aviso_single_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterShowAviso.ViewHolder holder, int position) {
        //Colocar nome e descrição
        holder.titulo.setText(avisos.get(position).getTitulo());
        holder.descricao.setText(avisos.get(position).getDescricao());

        //CheckBox click listener
        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkbox = (CheckBox) view;
                if (checkbox.isChecked()){
                    avisos.get(holder.getAdapterPosition()).setSelecionado(true);
                }
                else {
                    avisos.get(holder.getAdapterPosition()).setSelecionado(false);
                }
            }
        });
    }
    public ArrayList<Aviso> getAvisos() {
        return avisos;
    }

    @Override
    public int getItemCount() {
        return avisos.size();
    }
}
