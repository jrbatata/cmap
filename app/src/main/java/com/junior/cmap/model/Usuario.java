package com.junior.cmap.model;

import com.google.firebase.database.DatabaseReference;
import com.junior.cmap.config.ConfiguracaoFirebase;

public class Usuario {
    private String id;
    private String email;
    private String senha;
    private DatabaseReference reference = ConfiguracaoFirebase.getFirebase().child("usuario");

    public Usuario() {
    }

    public Usuario(String email, String senha, String id) {
        this.email = email;
        this.senha = senha;
        this.id = id;
    }

    public DatabaseReference getReference() {
        return reference;
    }

    public Usuario(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
