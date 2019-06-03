package com.junior.cmap.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.junior.cmap.R;
import com.junior.cmap.config.ConfiguracaoFirebase;
import com.junior.cmap.model.ViewDialog;

public class LoginActivity extends AppCompatActivity {

    private EditText textEmail;
    private EditText textSenha;
    private FirebaseAuth auth = ConfiguracaoFirebase.getFireBaseAuth();
    private String strStatus = "";
    private ViewDialog viewDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        init();
    }

    public void init(){
        textEmail = (EditText) findViewById(R.id.textEmail);
        textSenha = (EditText) findViewById(R.id.textSenha);
        TextView textRegistrar = (TextView) findViewById(R.id.textRegistrar);
        TextView textEsqueciSenha = (TextView) findViewById(R.id.textEsqueciSenha);
        Button buttonEntrar = (Button) findViewById(R.id.buttonEntrar);
        viewDialog = new ViewDialog(this);

        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!textEmail.getText().toString().isEmpty() && !textSenha.getText().toString().isEmpty()){
                    viewDialog.showDialog("Autenticando","Por favor, aguarde enquanto validamos seus dados");
                    auth.signInWithEmailAndPassword(textEmail.getText().toString(), textSenha.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    viewDialog.hideDialog();
                                    if(task.isSuccessful()){
                                        strStatus = "Bem-vindo de volta!";
                                    }else{
                                        strStatus = "Erro: " + task.getException().getMessage();
                                    }
                                }
                            });
                }else{
                    strStatus = "Erro: Você precisa preencher todos os campos para registrar-se";
                }
                Toast.makeText(LoginActivity.this, strStatus, Toast.LENGTH_SHORT).show();
            }
        });


        textRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });

        textEsqueciSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //...
            }
        });
    }
}
