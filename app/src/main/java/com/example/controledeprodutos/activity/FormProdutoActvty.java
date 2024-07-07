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

import com.example.controledeprodutos.R;
import com.example.controledeprodutos.model.Produto;

public class FormProdutoActvty extends AppCompatActivity {

    private EditText edit_produto;
    private EditText edit_quantidade;
    private EditText edit_valor;
    private Produto produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_produto_actvty);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

//            edit_produto = findViewById(R.id.edit_produto);
//
//            edit_produto.setText(produto.getNome());
//            edit_quantidade.setText(String.valueOf(produto.getEstoque()));
//            edit_valor.setText(String.valueOf(produto.getValor()));
//
//            Bundle bundle = getIntent().getExtras();
//            if (bundle != null) {
//                produto = (Produto) bundle.getSerializable("produto");
//
//                editProduto();
//            }

            return insets;
        });
    }

    private void editProduto() {
        edit_produto.setText(produto.getNome());
        edit_quantidade.setText(String.valueOf(produto.getEstoque()));
        edit_valor.setText(String.valueOf(produto.getValor()));
    }

    public void salvarProduto(View view) {
        try {
            String[] args = {String.valueOf(produto.getId())};

        }catch(Exception e){
            Log.e("error", "Erro ao deletar produto" + e.getMessage());
        }

        String nome = edit_produto.getText().toString();
        String quantidade = edit_quantidade.getText().toString();
        String valor = edit_valor.getText().toString();

        if (!nome.isEmpty()) {
            if (!quantidade.isEmpty()) {

                int qtd = Integer.parseInt(quantidade);

                if (qtd >= 1) {

                    if (!valor.isEmpty()) {

                        double valorProduto = Double.parseDouble(valor);

                        if (valorProduto > 0) {

                        }
                        if (produto == null) produto = new Produto();
                        produto.setNome(nome);
                        produto.setEstoque(qtd);
                        produto.setValor(valorProduto);

                        produto.salvaProduto();

                        finish();
                    } else {
                        edit_valor.requestFocus();
                        edit_valor.setError("Valor deve ser maior que zero");
                    }
                } else {
                    edit_quantidade.requestFocus();
                    edit_quantidade.setError("Quantidade deve ser maior que zero");
                }


            } else {
                edit_produto.requestFocus();
                edit_produto.setError("Nome do produto é obrigatório");
            }
        }
    }
}