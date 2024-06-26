package com.example.controledeprodutos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.controledeprodutos.adapter.AdapterProduto;
import com.example.controledeprodutos.model.Produto;
import com.example.controledeprodutos.ProdutoDAO;
import com.example.controledeprodutos.R;
import com.tsuryo.swipeablerv.SwipeLeftRightCallback;
import com.tsuryo.swipeablerv.SwipeableRecyclerView;

public class MainActivity extends AppCompatActivity implements AdapterProduto.OnClick {

    private AdapterProduto adapterProduto;
    private SwipeableRecyclerView rvProdutos;
    private ImageButton ibAdd;
    private ImageButton ibVerMais;
    private ProdutoDAO produtoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            ibAdd = findViewById(R.id.ib_add);
            ibVerMais = findViewById(R.id.ib_ver_mais);
            rvProdutos = findViewById(R.id.rvProdutos);
            produtoDAO = new ProdutoDAO(this);

            configRecycleView();

            ouvinteCliques();

            return insets;

        });
    }

    private void ouvinteCliques() {
        ibAdd.setOnClickListener(view -> {
            //Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, FormProdutoActvty.class));
        });

        ibVerMais.setOnClickListener(view -> {

            PopupMenu popupMenu = new PopupMenu(this, ibVerMais);
            popupMenu.getMenuInflater().inflate(R.menu.menu_toolbar, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(MenuItem -> {
                if (MenuItem.getItemId() == R.id.menu_sobre) {
                    Toast.makeText(this, "Sobre", Toast.LENGTH_SHORT).show();
                }
                return true;
            });
            popupMenu.show();

        });
    }

    //metodo
    private void configRecycleView() {
        rvProdutos.setLayoutManager(new LinearLayoutManager(this));
        rvProdutos.setHasFixedSize(true);
        adapterProduto = new AdapterProduto(produtoDAO.getlistProduto(), this);
        rvProdutos.setAdapter(adapterProduto);

        rvProdutos.setListener(new SwipeLeftRightCallback.Listener() {
            @Override
            public void onSwipedLeft(int position) {

            }

            @Override
            public void onSwipedRight(int position) {

                produtoDAO.getlistProduto().remove(position);
                adapterProduto.notifyItemRemoved(position);

            }
        });
    }

    @Override
    public Void onClickListener(Produto prooduto) {
        Toast.makeText(this, prooduto.getNome(), Toast.LENGTH_SHORT).show();

        return null;
    }
}