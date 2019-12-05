package com.junior.cmap.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.junior.cmap.R;
import com.junior.cmap.model.Notificacao;
import com.junior.cmap.model.Usuario;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

public class EnviarNotificacaoActivity extends AppCompatActivity {

    private Usuario usuarioAtual;
    private TextView pesquisarEmail;
    private TextView selecionarCurso;
    private EditText textEmail;
    private EditText textTitulo;
    private EditText textMensagem;
    private RadioButton radioTodos;
    private RadioButton radioCurso;
    private RadioButton radioEspecificar;
    private Spinner spinCurso;
    private Button buttonEnviar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_notificacao);
        iniciar();
    }

    private void iniciar() {
        getSupportActionBar().setTitle("Enviar Notificação");
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("cmap").child("usuarios").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuarioAtual = dataSnapshot.getValue(Usuario.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        pesquisarEmail = findViewById(R.id.pesquisarEmail);
        selecionarCurso = findViewById(R.id.selecionarCurso);
        textEmail = findViewById(R.id.textEmail);
        textTitulo = findViewById(R.id.textTitulo);
        textMensagem = findViewById(R.id.textMensagem);
        radioTodos = findViewById(R.id.radioTodos);
        radioCurso = findViewById(R.id.radioCurso);
        radioEspecificar = findViewById(R.id.radioEspecificar);
        spinCurso = findViewById(R.id.spinCurso);
        buttonEnviar = findViewById(R.id.buttonEnviar);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.curso, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCurso.setAdapter(adapter);

        radioTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioSelecionado(0);
            }
        });

        radioCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioSelecionado(1);
            }
        });

        radioEspecificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioSelecionado(2);
            }
        });

        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("cmap");

                GregorianCalendar calendar = new GregorianCalendar();
                SimpleDateFormat formatador = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");

                final Notificacao notificacao = new Notificacao();
                notificacao.setDataHora(formatador.format(calendar.getTime()));
                notificacao.setId(notificacao.getDataHora() + "-" + UUID.randomUUID().toString());
                notificacao.setServidorId(usuarioAtual.getId());
                notificacao.setDepartamento(usuarioAtual.getDepartamento());
                notificacao.setLida(false);
                notificacao.setDescricao(textTitulo.getText().toString() + "|" + textMensagem.getText().toString());

                if(radioTodos.isChecked()){
                    ref.child("usuarios").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    Usuario u = ds.getValue(Usuario.class);
                                    if(!u.isAdm()){
                                        notificacao.setPrivacidade("Publico");
                                        ref.child("usuarios").child(u.getId()).child("notificacoes").child(notificacao.getId()).setValue(notificacao);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }else{
                    if(radioCurso.isChecked()){

                    }else{
                        if(radioEspecificar.isChecked()){

                        }
                    }
                }
            }
        });
    }

    private void radioSelecionado(int i) {
        switch (i){
            case 0:
                pesquisarEmail.setVisibility(View.GONE);
                textEmail.setVisibility(View.GONE);
                selecionarCurso.setVisibility(View.GONE);
                spinCurso.setVisibility(View.GONE);
                radioTodos.setChecked(true);
                radioCurso.setChecked(false);
                radioEspecificar.setChecked(false);
                break;
            case 1:
                pesquisarEmail.setVisibility(View.GONE);
                textEmail.setVisibility(View.GONE);
                selecionarCurso.setVisibility(View.VISIBLE);
                spinCurso.setVisibility(View.VISIBLE);
                radioCurso.setChecked(true);
                radioTodos.setChecked(false);
                radioEspecificar.setChecked(false);
                break;
            case 2:
                pesquisarEmail.setVisibility(View.VISIBLE);
                textEmail.setVisibility(View.VISIBLE);
                selecionarCurso.setVisibility(View.GONE);
                spinCurso.setVisibility(View.GONE);
                radioEspecificar.setChecked(true);
                radioCurso.setChecked(false);
                radioTodos.setChecked(false);
                break;
        }
    }


}
