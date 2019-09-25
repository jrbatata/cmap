package com.junior.cmap.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.junior.cmap.R;
import com.junior.cmap.model.Notificacao;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificacoesAdapter extends RecyclerView.Adapter<NotificacoesAdapter.MyViewHolder> {

    private List<Notificacao> notificacoes;
    private Context context;

    public NotificacoesAdapter(List<Notificacao> notificacoes, Context context) {
        this.context = context;
        this.notificacoes = notificacoes;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemNotificacao = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_notificacao, viewGroup,false);
        return new MyViewHolder(itemNotificacao);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        Notificacao notificacao = notificacoes.get(i);
        myViewHolder.textDepartamento.setText(notificacoes.get(i).getDepartamento());
        myViewHolder.textDescricao.setText(notificacoes.get(i).getDescricao());
        myViewHolder.imagemDepartamento.setImageResource(R.drawable.ic_daic);
        if(notificacao.isLida()){
            myViewHolder.textNaoLida.setVisibility(View.GONE);
        }else{
            myViewHolder.textNaoLida.setVisibility(View.VISIBLE);
        }
        myViewHolder.textData.setText(notificacao.getDataHora());
    }

    @Override
    public int getItemCount() {
        return notificacoes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        CircleImageView imagemDepartamento;
        TextView textDepartamento;
        TextView textDescricao;
        TextView textNaoLida;
        TextView textData;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            imagemDepartamento = itemView.findViewById(R.id.imagemDepartamento);
            textDepartamento = itemView.findViewById(R.id.textDepartamento);
            textDescricao = itemView.findViewById(R.id.textDescricao);
            textNaoLida = itemView.findViewById(R.id.textNaoLida);
            textData = itemView.findViewById(R.id.textData);

        }
    }
}
