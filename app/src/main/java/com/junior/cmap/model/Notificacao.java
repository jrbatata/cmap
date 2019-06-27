package com.junior.cmap.model;

import com.google.firebase.database.DatabaseReference;
import com.junior.cmap.config.ConfiguracaoFirebase;

public class Notificacao {
    private String conteudo;
    private String privacidade;
    private String departamento;
    private DatabaseReference reference = ConfiguracaoFirebase.getFirebase().child("notificacao");

    public Notificacao() {
    }

    public DatabaseReference getReference() {
        return reference;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getPrivacidade() {
        return privacidade;
    }

    public void setPrivacidade(String privacidade) {
        this.privacidade = privacidade;
    }
}
