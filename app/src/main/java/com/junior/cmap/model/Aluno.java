package com.junior.cmap.model;

import com.google.firebase.database.DatabaseReference;
import com.junior.cmap.config.ConfiguracaoFirebase;

import java.io.Serializable;

public class Aluno implements Serializable {
    private String cpfResponsavel;
    private String nome;
    private String matricula;
    private String dataNasc;
    private String nivelEscolar;
    private String sexo;

    public Aluno(){
    }

    public Aluno(String nomeCompleto, String matricula, String dataNasc, String nivelEscolar, String sexo) {
        this.nome = nomeCompleto;
        this.matricula = matricula;
        this.dataNasc = dataNasc;
        this.nivelEscolar = nivelEscolar;
        this.sexo = sexo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nomeCompleto) {
        this.nome = nomeCompleto;
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
