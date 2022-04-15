package com.example.uiteste.admin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.uiteste.Data.Jogador;
import com.example.uiteste.R;
import com.example.uiteste.calendario.AdapterConvocatoria;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AdapterListaJogadores extends RecyclerView.Adapter<AdapterListaJogadores.ViewHolder> {
    Context context;
    ArrayList<Jogador> jogadores;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView Nome, descricao;
        ImageView imagem;
        Button AbrirPerfil;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Nome = itemView.findViewById(R.id.NomeListaJogadores);
            descricao = itemView.findViewById(R.id.DescricaoListaJogadores);
            imagem = itemView.findViewById(R.id.FotoJogadorLista);
            AbrirPerfil = itemView.findViewById(R.id.AbrirPerfilJogadorLista);
        }
    }

    public AdapterListaJogadores(Context context, ArrayList<Jogador> Jogadores) {
        this.context = context;
        this.jogadores = Jogadores;
    }

    @NonNull
    @Override
    public AdapterListaJogadores.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_item_lista_jogadores_admin, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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

            holder.AbrirPerfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PerfilJogadorAdmin.class);
                    Bundle extras = new Bundle();
                    extras.putString("idJogador", jogador.getId());
                    intent.putExtras(extras);
                    context.startActivity(intent);
                }
            });
    }

    public ArrayList<Jogador> getJogadores() {
        return jogadores;
    }

    @Override
    public int getItemCount() {
        return jogadores.size();
    }
}
