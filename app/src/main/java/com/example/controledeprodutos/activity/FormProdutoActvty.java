package com.example.controledeprodutos.activity;

import static com.gun0912.tedpermission.normal.TedPermission.create;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.controledeprodutos.R;
import com.example.controledeprodutos.model.Produto;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.IOException;
import java.util.List;

public class FormProdutoActvty extends AppCompatActivity {

    private static final int REQUEST_GALERIA = 100;
    private ImageView imagem_produto;
    private String caminho_imagem;
    private Bitmap imagem;
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

            iniciaComponentes();

            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                produto = (Produto) bundle.getSerializable("produto");

                editProduto();
            }
            return insets;
        });
    }

    private void showDialogPermissao(PermissionListener permissionListener, String[] permissoes) {
        create()

                .setPermissionListener(permissionListener)
                .setDeniedTitle("Permissão negada")
                .setDeniedMessage("Você negou a permissão para acessar a galeria, deseja permitir?")
                .setDeniedCloseButtonText("Não")
                .setGotoSettingButtonText("Sim")
                .setPermissions(permissoes)
                .setDeniedMessage("Você precisa dar permissaõ para acesso a imagens da galeria do dispositivo")
                .check();

    }


    public void verificarPermissaoGaleria(View view) {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {

                abrirGaleria();

            }
            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(FormProdutoActvty.this, "Permissão negada", Toast.LENGTH_SHORT).show();
            }
        };

        showDialogPermissao(permissionListener, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,});

        showDialogPermissao(
                permissionListener,
                new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                "Se você não aceitar a permissão, você não poderá acessar a galeria");
    }

    private void showDialogPermissao(PermissionListener permissionListener, String[] permissoes, String mensagem) {

        TedPermission.create()
                .setPermissionListener(permissionListener)
                .setDeniedMessage("Você negou a permissão para acessar a galeria, deseja permitir?")
                .setPermissions(Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION)
                .setDeniedCloseButtonText("Não")
                .setGotoSettingButtonText("Sim")
                .setPermissions(permissoes) // Make sure 'permissoes' is initialized

                 .setDeniedTitle("Permissões")
//

                .check();

    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_GALERIA);
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

    public void iniciaComponentes() {
        edit_produto = findViewById(R.id.edit_produto);
        edit_quantidade = findViewById(R.id.edit_quantidade);
        edit_valor = findViewById(R.id.edit_valor);
        imagem_produto = findViewById(R.id.imagem_produto);
    }


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_GALERIA) {

                Uri localImagemSelecionada = data.getData();
                caminho_imagem = localImagemSelecionada.toString();

                if (Build.VERSION.SDK_INT < 28) {
                    try {
                        imagem = MediaStore.Images.Media.getBitmap(getBaseContext().getContentResolver(), localImagemSelecionada);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    ImageDecoder.Source source = ImageDecoder.createSource(getBaseContext().getContentResolver(), localImagemSelecionada);
                    try {
                        imagem = ImageDecoder.decodeBitmap(source);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("INFOTESTE", "onActivityResult: " + caminho_imagem);
            }
        }
    }
}