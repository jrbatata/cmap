package com.junior.cmap.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.junior.cmap.R;
import com.junior.cmap.adapter.AdapterPesquisa;
import com.junior.cmap.config.ConfiguracaoFirebase;
import com.junior.cmap.config.RecyclerItemClickListener;
import com.junior.cmap.model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class SelecionarAlunoActivity extends AppCompatActivity {

    private SearchView searchViewPesquisa;
    private RecyclerView recyclerViewAlunos;
    private List<Aluno> listaAlunos;
    private DatabaseReference alunosRef;
    private AdapterPesquisa adapterPesquisa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecionar_aluno);
        init();
    }

    public void init(){
        //Inicializa
        searchViewPesquisa = findViewById(R.id.searchViewPesquisa);
        recyclerViewAlunos = findViewById(R.id.recyclerViewAlunos);

        //Inicializa o List
        listaAlunos = new ArrayList<>();
        alunosRef = ConfiguracaoFirebase.getFirebase().child("sigaa/alunos");

        //Configura RecyclerView
        recyclerViewAlunos.setHasFixedSize(false);
        recyclerViewAlunos.setLayoutManager(new LinearLayoutManager(this));

        //Configura Adapter
        adapterPesquisa = new AdapterPesquisa(listaAlunos, this);
        recyclerViewAlunos.setAdapter(adapterPesquisa);
        recyclerViewAlunos.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        //Configura evento de clique
        recyclerViewAlunos.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerViewAlunos, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Aluno alunoSelecionado = listaAlunos.get(position);
                Intent intent = new Intent(SelecionarAlunoActivity.this, CadastroActivity.class);
                intent.putExtra("alunoSelecionado", alunoSelecionado);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));

        //Configura SearchView
        searchViewPesquisa.setQueryHint("Buscar pelo nome");
        searchViewPesquisa.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String textoDigitado = s.toUpperCase();
                pesquisarAlunos(textoDigitado);
                return true;
            }
        });
    }

    public void pesquisarAlunos(String texto){
        //Limpa a Lista
        listaAlunos.clear();

        //Pesquisa aluno caso tenha texto na pesquisa
        if(texto.length() > 0){
            Query query = alunosRef.orderByChild("nome")
                    .startAt(texto)
                    .endAt(texto + "\uf8ff");

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        listaAlunos.add(ds.getValue(Aluno.class));
                    }

                    adapterPesquisa.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
