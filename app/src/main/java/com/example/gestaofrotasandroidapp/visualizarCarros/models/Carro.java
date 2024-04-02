package com.example.gestaofrotasandroidapp.visualizarCarros.models;

public class Carro {
    private long id;
    private String nome;
    private String marca;
    private String placa;

    public Carro(long id, String nome, String marca, String placa) {
        this.id = id;
        this.nome = nome;
        this.marca = marca;
        this.placa = placa;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getMarca() {
        return marca;
    }

    public String getPlaca() {
        return placa;
    }
}
