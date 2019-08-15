package com.junior.cmap.model;

import com.google.firebase.database.DatabaseReference;
import com.junior.cmap.config.ConfiguracaoFirebase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Notificacao {
    private String id;
    private String titulo;
    private String descricao;
    private String servidorId;
    private boolean publico;
    private ArrayList<String> listDestinatarios;
    private String horario;
    private String data;

    public Notificacao() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getServidorId() {
        return servidorId;
    }

    public void setServidorId(String servidorId) {
        this.servidorId = servidorId;
    }

    public boolean isPublico() {
        return publico;
    }

    public void setPublico(boolean publico) {
        this.publico = publico;
    }

    public ArrayList<String> getListDestinatarios() {
        return listDestinatarios;
    }

    public void setListDestinatarios(ArrayList<String> listDestinatarios) {
        this.listDestinatarios = listDestinatarios;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
