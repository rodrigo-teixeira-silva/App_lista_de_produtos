package com.example.controledeprodutos.autenticação;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.controledeprodutos.R;
import com.example.controledeprodutos.activity.MainActivity;
import com.example.controledeprodutos.helper.FireBaseHelper;
import com.example.controledeprodutos.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class Criar_contaActivity extends AppCompatActivity {

    private EditText edit_nome;
    private EditText edit_email;
    private EditText edit_senha;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_criar_conta);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            iniciaComponentes();

            configCliques();

            return insets;
        });
    }

    private void configCliques() {
        findViewById(R.id.ib_voltar).setOnClickListener(View -> finish());
    }

    public void validaDados(View view) {
        String nome = edit_nome.getText().toString();
        String email = edit_email.getText().toString();
        String senha = edit_senha.getText().toString();

        if (!nome.isEmpty()) {
            if (!email.isEmpty()) {
                if (!senha.isEmpty()) {

                    progressBar.setVisibility(view.VISIBLE);

                    Usuario usuario = new Usuario();
                    usuario.setId(nome);
                    usuario.setEmail(email);
                    usuario.setSenha(senha);

                    salvarCadastro(usuario);

                    // Toast.makeText(this, "Tudo certo", Toast.LENGTH_SHORT).show();
                } else {
                    edit_senha.requestFocus();
                    edit_senha.setError("Informe sua senha");
                }

            } else {
                edit_email.requestFocus();
                edit_email.setError("Informe o email");
            }

        } else {
            edit_nome.requestFocus();
            edit_nome.setError("Informe o nome");
        }
    }

    private void salvarCadastro(Usuario usuario) {
        FireBaseHelper.getAuth().createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    String id = task.getResult().getUser().getUid();
                    usuario.setId(id);

                    finish();
//
                    startActivity(new Intent(Criar_contaActivity.this, MainActivity.class));

                }
            }
        });
    }

    private void iniciaComponentes() {
        edit_nome = findViewById(R.id.edit_nome);
        edit_email = findViewById(R.id.edit_email);
        edit_senha = findViewById(R.id.edit_senha);
        progressBar = findViewById(R.id.progressBar);

        TextView text_titulo = findViewById(R.id.text_titulo);
        text_titulo.setText("Criar conta");

    }
}