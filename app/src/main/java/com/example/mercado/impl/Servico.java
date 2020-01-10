package com.example.mercado.impl;

import android.database.Cursor;

import com.example.mercado.model.Fruta;

public interface Servico {

    String inserir(Fruta fruta);
    Cursor listagem();
    Cursor listarPorId(String id);
    void remove(String id);
}
