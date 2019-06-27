package com.junior.cmap.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseUser;
import com.junior.cmap.R;
import com.junior.cmap.config.ConfiguracaoFirebase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init(){
        getSupportActionBar().hide();
        Button cadastro = (Button) findViewById(R.id.buttonRegister);
        Button login = (Button) findViewById(R.id.buttonLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTela(LoginActivity.class);
            }
        });
        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                abrirTela(CadastroActivity.class);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser usuarioAtual = ConfiguracaoFirebase.getFireBaseAuth().getCurrentUser();
        if (usuarioAtual != null){
            abrirTela(PrincipalActivity.class);
        }
    }

    public void abrirTela(Class tela){
        Intent intent = new Intent(this, tela);
        startActivity(intent);
    }
}
