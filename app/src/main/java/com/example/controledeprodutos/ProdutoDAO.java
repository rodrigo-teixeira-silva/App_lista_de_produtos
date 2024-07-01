package com.example.controledeprodutos;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.controledeprodutos.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    // DAO Data Access Object
    private final SQLiteDatabase write;
    private final SQLiteDatabase read;

    public ProdutoDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        this.write = dbHelper.getWritableDatabase();
        this.read = dbHelper.getReadableDatabase();
    }

    public void salvarProduto(Produto produto) {

        ContentValues cv = new ContentValues(); // classe utilizado para persistencia de dados espacifica chave e tipo de inf
        cv.put("nome", produto.getNome());
        cv.put("estoque", produto.getEstoque());
        cv.put("valor", produto.getValor());

        try {
            write.insert(DBHelper.TB_PRODUTO, null, cv);
            //write.close();
        } catch (Exception e) {
            Log.i("ERROR", "Erro ao salvar Produto:" + e.getMessage());
        }
    }

    public List<Produto> getlistProduto() {

        List<Produto> produtoList = new ArrayList<>();

        String sql = " SELECT * FROM " + DBHelper.TB_PRODUTO + ";";
        Cursor c = read.rawQuery(sql, null);

        while (c.moveToNext()) {

            @SuppressLint("Range") int id = c.getInt(c.getColumnIndex("id"));
            @SuppressLint("Range") String nome = c.getString(c.getColumnIndex("nome"));
            @SuppressLint("Range") int estoque = c.getInt(c.getColumnIndex("estoque"));
            @SuppressLint("Range") double valor = c.getDouble(c.getColumnIndex("valor"));

            Produto produto = new Produto();
            produto.setId(id);
            produto.setNome(nome);
            produto.setEstoque(estoque);
            produto.setValor(valor);

            produtoList.add(produto);

        }
        return produtoList;
    }

    public void atualizaProduto(Produto produto) {

        ContentValues cv = new ContentValues();

        cv.put("nome", produto.getNome());
        cv.put("estoque", produto.getEstoque());
        cv.put("valor", produto.getValor());

        String where = "id=?";
        String[] args = {String.valueOf(produto.getId())};

        try {
            write.update(DBHelper.TB_PRODUTO, cv, where, args);
            //write.close();
        } catch (Exception e) {
            Log.i("ERROR", "Erro ao atualizar produto" + e.getMessage());
        }
    }

    public void deleteProduto(Produto produto) {
        try {
            String[] args = {String.valueOf(produto.getId())};
            String where = "id=?";
            write.delete(DBHelper.TB_PRODUTO, "id=?", args);
        } catch (Exception e) {
            Log.i("error", "Erro ao deletar produto" + e.getMessage());
        }
    }
}
