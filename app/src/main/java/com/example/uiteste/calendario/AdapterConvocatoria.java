package com.example.uiteste.calendario;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.uiteste.Data.Jogador;
import com.example.uiteste.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AdapterConvocatoria extends RecyclerView.Adapter<AdapterConvocatoria.ViewHolder> {
    Context context;
    ArrayList<Jogador> jogadores;
    Boolean PermitirConvocacao;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView Nome, descricao;
        ImageView imagem;
        CheckBox checkbox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Nome = itemView.findViewById(R.id.NomeConvocatoria);
            descricao = itemView.findViewById(R.id.DescricaoConvocatoria);
            imagem = itemView.findViewById(R.id.MarquinhosConvocatoria);
            checkbox = itemView.findViewById(R.id.ConvocadoCheckBox);
        }
    }

    public AdapterConvocatoria(Context context, ArrayList Jogadores, Boolean PermitirConvocacao) {
        this.context = context;
        this.jogadores = Jogadores;
        this.PermitirConvocacao = PermitirConvocacao;
    }

    @NonNull
    @Override
    public AdapterConvocatoria.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_item_convocatoria, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterConvocatoria.ViewHolder holder, int position) {
        //Colocar nome, escalao, imagem e setup das checkboxes

        //Chamar o jogador daquela row
        Jogador jogador = jogadores.get(position);

        holder.Nome.setText(jogador.getNome());
        holder.descricao.setText(jogador.getEscalao());
        //Ir buscar a imagem através do ID
        StorageReference imagem = FirebaseStorage.getInstance().getReference().child
                ("profileImages/" + jogador.getId());
        imagem.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri.toString()).into(holder.imagem);
            }
        });

        //Se já foi convocado anteriorment checkar a checkbox automaticamente
        if(jogador.IsConvocado())
            holder.checkbox.setChecked(true);

        //CheckBox click listener
        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkbox = (CheckBox) view;
                if (checkbox.isChecked())
                    jogadores.get(holder.getAdapterPosition()).setConvocado(true);
                else
                    jogadores.get(holder.getAdapterPosition()).setConvocado(false);
            }
        });

        if (!PermitirConvocacao) {
            holder.checkbox.setVisibility(holder.itemView.GONE);
        }
    }
    public ArrayList<Jogador> getJogadores() {
        return jogadores;
    }

    @Override
    public int getItemCount() {
        return jogadores.size();
    }
}
