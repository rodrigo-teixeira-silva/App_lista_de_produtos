package com.example.controledeprodutos.autenticação;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.controledeprodutos.R;
import com.example.controledeprodutos.helper.FireBaseHelper;

public class recuperarContaActivity extends AppCompatActivity {

    private TextView edit_email;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recuperar_conta);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            iniciaComponentes();

            return insets;
        });
    }

    public void recuperarSenha(View view) {
        String email = edit_email.getText().toString().trim();

        if (!email.isEmpty()) {

            progressBar.setVisibility(view.VISIBLE);

            enviaEmail(email);

        } else {
            edit_email.requestFocus();
            edit_email.setError("Informe seu email");
        }
    }

    private void enviaEmail(String email) {
        FireBaseHelper.getAuth().sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Toast.makeText(this, "Email enviado com sucesso", Toast.LENGTH_LONG).show();
            } else {
                String error = task.getException().getMessage();
                Toast.makeText(this, "Erro: " + error, Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void iniciaComponentes() {
        edit_email = findViewById(R.id.edit_email);
        progressBar = findViewById(R.id.progressBar);


        TextView text_titulo = findViewById(R.id.text_titulo);
        text_titulo.setText("Recuperar Conta");
    }


}