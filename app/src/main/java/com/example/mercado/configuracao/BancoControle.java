package com.example.mercado.configuracao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mercado.impl.Servico;
import com.example.mercado.model.Fruta;

import java.util.ArrayList;
import java.util.List;

public class BancoControle implements Servico {

    private SQLiteDatabase db;
    private ContextoBanco banco;
    private ContentValues valores;
    Cursor cursor;

    private List<Fruta> lista = new ArrayList<>();

    public BancoControle(Context context) {
        banco = new ContextoBanco(context);
    }

    @Override
    public String inserir(Fruta fruta) {//Salva os dados

        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(banco.NOME, fruta.getNome());
        valores.put(banco.PRECO, fruta.getPreco());
        valores.put(banco.QUANTIDADE, fruta.getQuantidade());

        resultado = db.insert(banco.TABELA, null, valores);
        db.close();

        if(resultado == -1){
            return "Erro ao inserir os dados!";
        }else{
            return "Salvo com Sucesso!";
        }
    }

    @Override
    public Cursor listagem() { // Lista os dados
        String[] campos = {banco.ID, banco.NOME, banco.PRECO, banco.QUANTIDADE};
        db = banco.getReadableDatabase();

        cursor = db.query(banco.TABELA, campos, null, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        db.close();
        return cursor;
    }

    @Override
    public Cursor listarPorId(String id) { //Lista por Id

        db = banco.getReadableDatabase();
        String[] parametros = {id};
        String[] campos = {banco.NOME, banco.PRECO, banco.QUANTIDADE};
        Cursor cursor = db.query(banco.TABELA, campos, banco.ID + " = ?", parametros, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        db.close();
        return cursor;
    }

    @Override
    public void remove(String id) {

        db = banco.getWritableDatabase();
        String[] parametro = {id};
        db.delete(banco.TABELA, banco.ID + " = ?", parametro);

    }
}
