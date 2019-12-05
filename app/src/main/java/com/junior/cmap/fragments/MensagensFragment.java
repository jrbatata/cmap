package com.junior.cmap.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.junior.cmap.R;
import com.junior.cmap.config.ConfiguracaoFirebase;
import com.junior.cmap.adapter.NotificacoesAdapter;
import com.junior.cmap.model.Notificacao;
import com.junior.cmap.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class MensagensFragment extends Fragment {

    private RecyclerView recyclerMensagens;
    private NotificacoesAdapter adapter;
    private Context context;
    private Usuario usuario;

    public MensagensFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mensagens, container, false);
        init(view);
        return view;
    }

    public void init(View view) {
        context = getContext();
        usuario = new Usuario();
        recyclerMensagens = (RecyclerView) view.findViewById(R.id.recyclerMensagens);
        final String uid = ConfiguracaoFirebase.getFireBaseAuth().getUid();

        ConfiguracaoFirebase.getFirebase().child("cmap/usuarios/" + uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuario = dataSnapshot.getValue(Usuario.class);
                carregarNotificacoes(usuario);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void carregarNotificacoes(Usuario usuario){
        if(usuario != null){
            if(usuario.isAdm()){
                mensagensAdm();
            }else{
                ConfiguracaoFirebase.getFirebase().child("cmap/usuarios/" + usuario.getId() + "/notificacoes").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<Notificacao> notificacoes = new ArrayList<>();
                        List<Notificacao> ordenados = new ArrayList<>();

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Notificacao notificacao = ds.getValue(Notificacao.class);
                            notificacoes.add(notificacao);
                        }

                        //ordena pelos mais recentes
                        for(int i = notificacoes.size()-1; i > 0; i--){
                            ordenados.add(notificacoes.get(i));
                        }
                        setAdapter(ordenados);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }else{
        }
    }

    private void mensagensAdm() {
        ConfiguracaoFirebase.getFirebase().child("cmap/usuarios/" + usuario.getId() +"/notificacoes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Notificacao> not = new ArrayList<>();
                List<Notificacao> ordenados = new ArrayList<>();

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Notificacao notificacao = ds.getValue(Notificacao.class);
                    not.add(notificacao);

                }

                //ordena pelos mais recentes
                for(int i = not.size()-1; i > 0; i--){
                    ordenados.add(not.get(i));
                }

                setAdapter(ordenados);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setAdapter(List<Notificacao> n){
        adapter = new NotificacoesAdapter(n, context);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerMensagens.setLayoutManager(layoutManager);
        recyclerMensagens.setHasFixedSize(true);
        recyclerMensagens.addItemDecoration(new DividerItemDecoration(context, LinearLayout.VERTICAL));
        recyclerMensagens.setAdapter(adapter);
    }
}
