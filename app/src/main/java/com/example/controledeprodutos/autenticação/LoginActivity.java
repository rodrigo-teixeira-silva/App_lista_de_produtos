package com.example.controledeprodutos.autenticação;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.controledeprodutos.R;
import com.example.controledeprodutos.activity.MainActivity;
import com.example.controledeprodutos.helper.FireBaseHelper;


public class LoginActivity extends AppCompatActivity {
    private TextView text_criar_conta;
    private EditText edit_email;
    private EditText edit_senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            iniciaComponentes();

            configCliques();

            return insets;
        });
    }

    public void Logar(View view) {
        String email = edit_email.getText().toString().trim();
        String senha = edit_senha.getText().toString();

        if (!email.isEmpty()) {
            if (!senha.isEmpty()) {
                validaLogin(email, senha);
            } else {
                edit_senha.requestFocus();
                edit_senha.setError("Informe a sua senha");
            }
        } else {
            edit_email.requestFocus();
            edit_email.setError("informe seu email");
        }
    }

    private void validaLogin(String email, String senha) {
        FireBaseHelper.getAuth().signInWithEmailAndPassword(
                email, senha
        ).addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        finish();
                        startActivity(new Intent(this, MainActivity.class));
                    } else {
                        String error = task.getException().getMessage();
                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void configCliques() {
        text_criar_conta.setOnClickListener(View -> startActivity(new Intent(this, Criar_contaActivity.class)));
    }

    private void iniciaComponentes() {
        text_criar_conta = findViewById(R.id.text_criar_conta);
        edit_email = findViewById(R.id.edit_email);
        edit_senha = findViewById(R.id.edit_senha);
    }
}