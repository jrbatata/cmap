package com.junior.cmap.model;

public class Aluno {

    private String cpfResponsavel;
    private String curso;
    private String dataNasc;
    private String matricula;
    private String nivelEscolar;
    private String nome;
    private String sexo;

    public Aluno(String cpfResponsavel,
                 String curso,
                 String dataNasc,
                 String matricula,
                 String nivelEscolar,
                 String nome,
                 String sexo){
        this.cpfResponsavel = cpfResponsavel;
        this.curso = curso;
        this.dataNasc = dataNasc;
        this.matricula = matricula;
        this.nivelEscolar = nivelEscolar;
        this.nome = nome;
        this.sexo = sexo;
    }

    public Aluno(){

    }

    public String getCpfResponsavel() {
        return cpfResponsavel;
    }

    public void setCpfResponsavel(String cpfResponsavel) {
        this.cpfResponsavel = cpfResponsavel;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNivelEscolar() {
        return nivelEscolar;
    }

    public void setNivelEscolar(String nivelEscolar) {
        this.nivelEscolar = nivelEscolar;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}
