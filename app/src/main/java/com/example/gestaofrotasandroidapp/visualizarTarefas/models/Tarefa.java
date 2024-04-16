package com.example.gestaofrotasandroidapp.visualizarTarefas.models;

public class Tarefa {
    private long id;
    private String descricao;
    private int prioridade;
    private int status;

    public Tarefa(long id, String descricao, int prioridade, int status) {
        this.id = id;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public int getStatus() {
        return status;
    }
}
