package com.junior.cmap.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.junior.cmap.R;
import com.junior.cmap.config.ConfiguracaoFirebase;
import com.junior.cmap.model.Usuario;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final FirebaseUser usuarioAtual = ConfiguracaoFirebase.getFireBaseAuth().getCurrentUser();
        if (usuarioAtual != null){
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            reference.child("cmap/usuarios/").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        if(ds.getKey().equals(usuarioAtual.getUid())) {
                            Usuario u = ds.getValue(Usuario.class);
                            if (u.isAdm()) {
                                signIn(AdministradorActivity.class);
                            } else {
                                signIn(PrincipalActivity.class);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else {
            setContentView(R.layout.activity_main);
            init();
        }
    }

    public void init(){
        getSupportActionBar().hide();
        TextView textCadastro = (TextView) findViewById(R.id.textRegistrar);
        Button buttonLogin = (Button) findViewById(R.id.buttonLogin);
        final EditText txtEmail = findViewById(R.id.textEmail);
        final EditText txtSenha = findViewById(R.id.textSenha);

        textCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://sig.ifam.edu.br/sigaa/public/cadastro/familiares.jsf"));
                startActivity(intent);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signInWithEmailAndPassword(txtEmail.getText().toString(), txtSenha.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                                    reference.child("cmap/usuarios").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for(DataSnapshot ds : dataSnapshot.getChildren()){
                                                if(ds.getKey().equals(ConfiguracaoFirebase.getFireBaseAuth().getCurrentUser().getUid())) {
                                                    Usuario u = ds.getValue(Usuario.class);
                                                    if (u.isAdm()) {
                                                        signIn(AdministradorActivity.class);
                                                    } else {
                                                        signIn(PrincipalActivity.class);
                                                    }
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }else{
                                    Toast.makeText(MainActivity.this, "Erro: nenhum usuário encontrado", Toast.LENGTH_SHORT);
                                }
                            }
                        });
            }
        });
    }

    public void signIn(Class tela){
        Intent intent = new Intent(this, tela);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
