package com.junior.cmap.config;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.junior.cmap.R;
import com.junior.cmap.model.Notificacao;
import com.squareup.picasso.Picasso;

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
        myViewHolder.textRemetente.setText(notificacoes.get(i).getTitulo());
        myViewHolder.textNotificacao.setText(notificacoes.get(i).getDescricao());
        myViewHolder.imageRemetente.setImageResource(R.drawable.ic_daic);
    }

    @Override
    public int getItemCount() {
        return notificacoes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        CircleImageView imageRemetente;
        TextView textRemetente;
        TextView textNotificacao;
        Button buttonRemetente;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            imageRemetente = itemView.findViewById(R.id.imageRemetente);
            textRemetente = itemView.findViewById(R.id.textRemetente);
            textNotificacao = itemView.findViewById(R.id.textNotificacao);

        }
    }
}
