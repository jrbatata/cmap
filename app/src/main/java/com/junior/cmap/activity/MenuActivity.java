package com.junior.cmap.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.junior.cmap.R;
import com.junior.cmap.config.ConfiguracaoFirebase;
import com.junior.cmap.model.Aluno;
import com.junior.cmap.model.Responsavel;
import com.junior.cmap.model.Usuario;

public class MenuActivity extends AppCompatActivity {

    private Responsavel responsavel;
    private Aluno aluno;
    private TextView descricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();
        init();
    }

    public void init(){
        responsavel = new Responsavel();
        responsavel.getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Responsavel rs = ds.getValue(Responsavel.class);
                    responsavel.setNomeCompleto(rs.getNomeCompleto());
                    responsavel.setGrauParentesco(rs.getGrauParentesco());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        aluno = new Aluno();
        aluno.getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Aluno al = ds.getValue(Aluno.class);
                    aluno.setMatricula(al.getMatricula());
                    aluno.setNomeCompleto(al.getNomeCompleto());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
