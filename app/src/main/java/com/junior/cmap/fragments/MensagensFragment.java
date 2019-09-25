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
    private List<Notificacao> notificacoes;
    private Context context;
    private Usuario usuario;
    private DatabaseReference mensagensReference = ConfiguracaoFirebase.getFirebase().child("cmap/notificacoes");

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
        notificacoes = new ArrayList<>();
        recyclerMensagens = (RecyclerView) view.findViewById(R.id.recyclerMensagens);

        ConfiguracaoFirebase.getFirebase().child("cmap/usuarios/").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Usuario u = ds.getValue(Usuario.class);
                    if(u.getId().equals(ConfiguracaoFirebase.getFireBaseAuth().getUid())){
                        usuario = u;
                        Log.i("SOFROSOFRO","u auth: " + ConfiguracaoFirebase.getFireBaseAuth().getUid());
                        if(usuario.isAdm()){
                            mensagensAdm();
                        }else{
                            mensagensReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    List<Notificacao> not = new ArrayList<>();
                                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                                        Notificacao notificacao = ds.getValue(Notificacao.class);
                                        if(notificacao.getPrivacidade().equals("Público")){
                                            not.add(notificacao);
                                        }
                                    }

                                    setAdapter(not);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void mensagensAdm() {
        mensagensReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Notificacao> not = new ArrayList<>();
                Log.i("SOFROSOFRO","u dp: " + usuario.getId());
                Log.i("SOFROSOFRO","u dp: " + usuario.getNomeCompleto());
                Log.i("SOFROSOFRO","u dp: " + usuario.getDepartamento());
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Notificacao notificacao = ds.getValue(Notificacao.class);
                    if(notificacao.getDepartamento().equals(usuario.getDepartamento())){
                        not.add(notificacao);
                    }
                }
                setAdapter(not);
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
