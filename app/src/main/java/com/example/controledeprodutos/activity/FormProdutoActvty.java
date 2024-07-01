package com.example.controledeprodutos.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.controledeprodutos.model.Produto;
import com.example.controledeprodutos.ProdutoDAO;
import com.example.controledeprodutos.R;
import com.google.firebase.Firebase;

public class FormProdutoActvty extends AppCompatActivity {

    private EditText edit_produto;
    private EditText edit_quantidade;
    private EditText edit_valor;
    private ProdutoDAO produtoDAO;
    private Produto produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_produto_actvty);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            edit_produto = findViewById(R.id.edit_produto);

            produtoDAO = new ProdutoDAO(this);

            edit_produto.setText(produto.getNome());
            edit_quantidade.setText(String.valueOf(produto.getEstoque()));
            edit_valor.setText(String.valueOf(produto.getValor()));

            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                produto = (Produto) bundle.getSerializable("produto");

                editProduto();
            }

            edit_produto = findViewById(R.id.edit_produto);
            edit_quantidade = findViewById(R.id.edit_quantidade);
            edit_valor = findViewById(R.id.edit_valor);

            return insets;
        });
    }

    private void editProduto() {
        edit_produto.setText(produto.getNome());
        edit_quantidade.setText(String.valueOf(produto.getEstoque()));
        edit_valor.setText(String.valueOf(produto.getValor()));
    }

    public void salvarProduto(Produto produto){
        try {
            String[] args = {String.valueOf(produto.getId())};

        }catch(Exception e){
            Log.e("error", "Erro ao deletar produto" + e.getMessage());
        }


    }
}