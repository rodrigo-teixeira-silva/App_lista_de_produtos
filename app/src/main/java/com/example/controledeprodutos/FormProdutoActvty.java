package com.example.controledeprodutos;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FormProdutoActvty extends AppCompatActivity {

    private EditText edit_produto;
    private EditText edit_quantidade;
    private EditText edit_valor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_produto_actvty);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            edit_produto = findViewById(R.id.edit_produto);
            edit_quantidade = findViewById(R.id.edit_quantidade);
            edit_valor = findViewById(R.id.edit_valor);


            return insets;
        });
    }

    public void salvarProduto(View view) {

        String nome = edit_produto.getText().toString();
        String quantidade = edit_quantidade.getText().toString();
        String valor = edit_valor.getText().toString();

        if (!nome.isEmpty()) {
            if (!quantidade.isEmpty()) {

                int qnt = Integer.parseInt(quantidade);

                if (qnt >= 1) {

                    if (!valor.isEmpty()) {

                        double valorProduto = Double.parseDouble(valor);

                        if (valorProduto > 0) {
                            Toast.makeText(this, "Tudo certo!", Toast.LENGTH_SHORT).show();
                        } else {
                            edit_valor.requestFocus();
                            edit_valor.setError("Informe o valor maior que zero");
                        }

                    } else {
                        edit_valor.requestFocus();
                        edit_valor.setError("Informe o valor do produto");
                    }

                } else {
                    edit_quantidade.requestFocus();
                    edit_quantidade.setError("Informe um valor maior que zero");
                }

            } else {
                edit_quantidade.requestFocus();
                edit_quantidade.setError("Informe um valor valor");
            }
        } else {
            edit_produto.requestFocus();
            edit_produto.setError("Informe o nome do produto");
        }


    }
}