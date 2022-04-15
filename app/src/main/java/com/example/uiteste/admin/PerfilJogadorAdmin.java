package com.example.uiteste.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.uiteste.Data.User;
import com.example.uiteste.InterfacesPrincipais.PerfilJogador;
import com.example.uiteste.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

public class PerfilJogadorAdmin extends AppCompatActivity {
    private String idJogador;
    private TextView showNome, showEscalao, showData, showEmail;
    private ImageView ImagemPerfil;
    private User user;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_jogador_admin);
        initWidgets();
        getUser();
    }

    private void initWidgets() {
        Bundle extras = getIntent().getExtras();
        idJogador = extras.getString("idJogador");
        storageReference = FirebaseStorage.getInstance().getReference();

        showNome = (TextView) findViewById(R.id.NomeJogadorAdmin);
        showEscalao = (TextView) findViewById(R.id.EscalaoJogadorAdmin);
        showData = (TextView) findViewById(R.id.ShowDataNascAdmin);
        showEmail = (TextView) findViewById(R.id.EmailJogadorAdmin);
        ImagemPerfil = (ImageView) findViewById(R.id.ImagemPerfilAdmin);

        ImagemPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AbrirGaleria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(AbrirGaleria, 1020);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1020) {
            if (resultCode == Activity.RESULT_OK) {
                Uri imageUri = data.getData();
                ImagemPerfil.setImageURI(imageUri);
                String nomeFoto = idJogador;

                uploadImageFirebase(nomeFoto, imageUri);
            }
        }
    }

    private void uploadImageFirebase(String nome, Uri contentUri) {
        StorageReference imagem = storageReference.child("profileImages/" + nome);
        imagem.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagem.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("TAG", "URL: " + uri.toString());
                        SetDados();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PerfilJogadorAdmin.this, "Ocorreu um erro durante o upload da imagem!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getUser(){
        DatabaseReference refUsers = FirebaseDatabase.getInstance
                ("https://abclub-30a87-default-rtdb.europe-west1.firebasedatabase.app").getReference("Users");
        refUsers.child(idJogador).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                SetDados();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    private void SetDados() {
        showNome.setText(user.getPrimeiro_Nome() + " " + user.getUltimo_Nome());
        showEscalao.setText(user.getEscalao());
        showData.setText(user.getDia() + "/" + user.getMes() + "/" + user.getAno());
        showEmail.setText(user.getEmail());

        StorageReference imagem = storageReference.child("profileImages/" + idJogador);
        imagem.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(PerfilJogadorAdmin.this).load(uri.toString()).into(ImagemPerfil);
            }
        });
    }
}