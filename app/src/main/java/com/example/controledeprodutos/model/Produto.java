package com.example.controledeprodutos.model;

import com.example.controledeprodutos.helper.FireBaseHelper;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

public class Produto implements Serializable {

    private String id;
    private String nome;
    private int estoque;
    private double valor;

    public Produto() {
        DatabaseReference reference = FireBaseHelper.getDatabaseReference();
        this.setId(reference.push().getKey());
    }

    public void deletaProduto() {
        DatabaseReference reference = FireBaseHelper.getDatabaseReference() // retorna o nó principal do banco de dados
                .child("produtos")
                .child(FireBaseHelper.getIdFirebase())
                .child(this.id);
        reference.removeValue();
    }


    public void salvaProduto() {
        DatabaseReference reference = FireBaseHelper.getDatabaseReference() // retorna o nó principal do banco de dados
                .child("produtos")
                .child(FireBaseHelper.getIdFirebase())
                .child(this.id);
        reference.setValue(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
