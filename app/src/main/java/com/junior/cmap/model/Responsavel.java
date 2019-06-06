package com.junior.cmap.model;

import com.google.firebase.database.DatabaseReference;
import com.junior.cmap.config.ConfiguracaoFirebase;

public class Responsavel {
    private String nomeCompleto;
    private String cpf;
    private String dataNasc;
    private String grauParentesco;
    private String sexo;
    private String matAluno;
    private DatabaseReference reference = ConfiguracaoFirebase.getFirebase().child("responsavel");

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getGrauParentesco() {
        return grauParentesco;
    }

    public void setGrauParentesco(String grauParentesco) {
        this.grauParentesco = grauParentesco;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getMatAluno() {
        return matAluno;
    }

    public void setMatAluno(String matAluno) {
        this.matAluno = matAluno;
    }

    public DatabaseReference getReference() {
        return reference;
    }
}
