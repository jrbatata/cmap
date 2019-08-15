package com.junior.cmap.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.junior.cmap.config.NotificacoesAdapter;
import com.junior.cmap.model.Notificacao;

import java.util.ArrayList;
import java.util.List;

public class MensagensFragment extends Fragment {

    private RecyclerView recyclerMensagens;
    private NotificacoesAdapter adapter;
    private List<Notificacao> notificacoes;
    private Context context;
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

        Notificacao notificacao = new Notificacao();
        mensagensReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Notificacao> not = new ArrayList<>();

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Notificacao notificacao = ds.getValue(Notificacao.class);
                    not.add(notificacao);

                }

                adapter = new NotificacoesAdapter(not, context);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                recyclerMensagens.setLayoutManager(layoutManager);
                recyclerMensagens.setHasFixedSize(true);
                recyclerMensagens.addItemDecoration(new DividerItemDecoration(context, LinearLayout.VERTICAL));
                recyclerMensagens.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
