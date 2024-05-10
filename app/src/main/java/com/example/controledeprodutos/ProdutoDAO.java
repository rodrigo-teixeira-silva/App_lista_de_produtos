package com.example.controledeprodutos;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.core.graphics.drawable.IconCompat;

public class ProdutoDAO {

    private final SQLiteDatabase write;
    private final SQLiteDatabase read;

    public ProdutoDAO(Context context, SQLiteDatabase write, SQLiteDatabase read) {
        DBHelper dbHelper = new DBHelper(context);

        this.write = write;
        this.read = read;
    }

    public void salvarProduto(Produto produto) {

        ContentValues cv = new ContentValues();
        cv.put("nome", produto.getNome());
        cv.put("estoque", produto.getEstoque());
        cv.put("valor", produto.getValor());



    }
}
