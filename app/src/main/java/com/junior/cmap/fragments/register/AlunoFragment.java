package com.junior.cmap.fragments.register;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.junior.cmap.R;
import com.junior.cmap.config.ConfiguracaoFirebase;
import com.junior.cmap.model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class AlunoFragment extends Fragment {

    private SearchView searchViewPesquisa;
    private RecyclerView recyclerViewAlunos;
    private List<Aluno> listaAlunos;
    private DatabaseReference alunosRef;

    public AlunoFragment() {
        // Required empty public constructor
    }

    public static AlunoFragment newInstance(){
        return new AlunoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_aluno, container, false);
        init(view);
        return view;
    }

    public void init(View view){
        //Inicializa
        searchViewPesquisa = view.findViewById(R.id.searchViewPesquisa);
        recyclerViewAlunos = view.findViewById(R.id.recyclerViewAlunos);

        //Inicializa o List
        listaAlunos = new ArrayList<>();
        alunosRef = ConfiguracaoFirebase.getFirebase().child("sigaa/alunos");

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

    public void trocaTela(Fragment fragment) {
        //Declaração e inicialização da transação
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        //Substitui o fragment colocado dentro do container
        transaction.replace(R.id.container, fragment);
        //Adiciona a transação na pilha
        transaction.addToBackStack(null);
        //Fecha a transação
        transaction.commit();
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

                    /* int total = listaAlunos.size();
                    Log.d("listaAlunos", "total: " + total);*/
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

}
