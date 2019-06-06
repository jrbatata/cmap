package com.junior.cmap.model;

import com.google.firebase.database.DatabaseReference;
import com.junior.cmap.config.ConfiguracaoFirebase;

public class Aluno {
    private String cpfResponsavel;
    private String nomeCompleto;
    private String matricula;
    private String dataNasc;
    private String nivelEscolar;
    private String sexo;
    private DatabaseReference reference = ConfiguracaoFirebase.getFirebase().child("aluno");

    public Aluno(){
    }

    public DatabaseReference getReference() {
        return reference;
    }

    public Aluno(String nomeCompleto, String matricula, String dataNasc, String nivelEscolar, String sexo) {
        this.nomeCompleto = nomeCompleto;
        this.matricula = matricula;
        this.dataNasc = dataNasc;
        this.nivelEscolar = nivelEscolar;
        this.sexo = sexo;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getNivelEscolar() {
        return nivelEscolar;
    }

    public void setNivelEscolar(String nivelEscolar) {
        this.nivelEscolar = nivelEscolar;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCpfResponsavel() {
        return cpfResponsavel;
    }

    public void setCpfResponsavel(String cpfResponsavel) {
        this.cpfResponsavel = cpfResponsavel;
    }
}
