package com.example.mercado.configuracao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ContextoBanco extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "mercadoria.sqlite";
    private static final int VERSION = 1;

    public static final String TABELA = "frutas";
    public static final String ID = "_id";
    public static final String NOME = "nome";
    public static final String PRECO = "preco";
    public static final String QUANTIDADE = "quantidade";

    private final Context context;

    public ContextoBanco(@Nullable Context context) {
        super(context, NOME_BANCO, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+ TABELA + " ("
                + ID + " integer primary key autoincrement, "
                + NOME + " text, "
                + PRECO + " double, "
                + QUANTIDADE + " integer"
                + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA);
        onCreate(db);
    }
}
