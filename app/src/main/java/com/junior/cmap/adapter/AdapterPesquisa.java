package com.junior.cmap.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.junior.cmap.R;
import com.junior.cmap.model.Aluno;

import java.util.List;

public class AdapterPesquisa extends RecyclerView.Adapter<AdapterPesquisa.MyViewHolder> {

    private List<Aluno> listaAlunos;
    private Context context;

    public AdapterPesquisa(List<Aluno> listaAlunos, Context context) {
        this.listaAlunos = listaAlunos;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemLista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_pesquisa_alunos, viewGroup, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Aluno aluno = listaAlunos.get(i);
        myViewHolder.textNome.setText(aluno.getNome());
        myViewHolder.textMatricula.setText(aluno.getMatricula());
    }

    @Override
    public int getItemCount() {
        return listaAlunos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textNome;
        TextView textMatricula;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textNome = itemView.findViewById(R.id.textNome);
            textMatricula = itemView.findViewById(R.id.textMatricula);

        }
    }
}