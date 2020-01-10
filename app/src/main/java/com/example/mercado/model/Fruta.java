package com.example.mercado.model;

import android.media.Image;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Fruta implements Serializable {

    private int id;
    private String nome;
    private double preco;
    private int quantidade;
    private int imagem;

    private DecimalFormat f = new DecimalFormat("#.##");

    public Fruta(){

    }

    public Fruta(int id, String nome, double preco, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public Fruta(int id) {
        this.id = id;
    }

    public Fruta(String nome, double preco, int quantidade, int imagem) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.imagem = imagem;
    }

    public Fruta(String nome, double preco, int quantidade) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }

}
