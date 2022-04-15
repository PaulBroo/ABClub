package com.example.uiteste.InterfacesPrincipais;

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

public class PerfilJogador extends AppCompatActivity {
    private User user;
    private TextView showNome, showEscalao, showData, showEmail;
    private ImageView ImagemPerfil;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_jogador);
    }

    protected void onStart(){
        super.onStart();
        DatabaseReference RootRef = FirebaseDatabase.getInstance("https://abclub-30a87-default-rtdb.europe-west1.firebasedatabase.app").getReference();
        DatabaseReference mUser = RootRef.child("Users").child(FirebaseAuth.getInstance().getUid());
        storageReference = FirebaseStorage.getInstance().getReference();
        initWidgets();

        mUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            User CurrentUser = snapshot.getValue(User.class);
            user = CurrentUser;
            SetDados();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PerfilJogador.this, "Erro: " , Toast.LENGTH_SHORT).show();
            }
        });

        ImagemPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AbrirGaleria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(AbrirGaleria, 1000);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                Uri imageUri = data.getData();
                ImagemPerfil.setImageURI(imageUri);
                String nomeFoto = FirebaseAuth.getInstance().getUid().toString();
                
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
                Toast.makeText(PerfilJogador.this, "Ocorreu um erro durante o upload da imagem!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initWidgets() {
        showNome = (TextView) findViewById(R.id.NomeJogador);
        showEscalao = (TextView) findViewById(R.id.ShowEscalao);
        showData = (TextView) findViewById(R.id.ShowDataNasc);
        showEmail = (TextView) findViewById(R.id.EmailJogador);
        ImagemPerfil = (ImageView) findViewById(R.id.ImagemPerfil);
    }

    public void SetDados() {
        showNome.setText(user.getPrimeiro_Nome() + " " + user.getUltimo_Nome());
        showEscalao.setText(user.getEscalao());
        showData.setText(user.getDia() + "/" + user.getMes() + "/" + user.getAno());
        showEmail.setText(user.getEmail());

        StorageReference imagem = storageReference.child("profileImages/" + FirebaseAuth.getInstance().getUid());
        imagem.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(PerfilJogador.this).load(uri.toString()).into(ImagemPerfil);
            }
        });
    }
}