package com.example.controledeprodutos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.controledeprodutos.R;
import com.example.controledeprodutos.autenticação.LoginActivity;
import com.example.controledeprodutos.helper.FireBaseHelper;

public class SplashActvitytActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_actvityt);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            new Handler(Looper.getMainLooper()).postDelayed(this::verificaLogin,3000);

            return insets;
        });
    }

    private void verificaLogin() {
        if (FireBaseHelper.getAutenticado()){
            startActivity(new Intent(this, MainActivity.class));
        }else{
            startActivity(new Intent(this, LoginActivity.class));

        }
    }
}