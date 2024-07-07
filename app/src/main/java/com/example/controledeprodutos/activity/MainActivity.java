package com.example.controledeprodutos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.controledeprodutos.R;
import com.example.controledeprodutos.adapter.AdapterProduto;
import com.example.controledeprodutos.autenticação.LoginActivity;
import com.example.controledeprodutos.helper.FireBaseHelper;
import com.example.controledeprodutos.model.Produto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tsuryo.swipeablerv.SwipeLeftRightCallback;
import com.tsuryo.swipeablerv.SwipeableRecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterProduto.OnClick {

    private AdapterProduto adapterProduto;
    private List<Produto> produtoList = new ArrayList<>();
    private SwipeableRecyclerView rvProdutos;
    private ImageButton ibAdd;
    private TextView text_info;
    private ImageButton ibVerMais;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            Produto produto = new Produto();

            iniciaComponentes();

            ouvinteCliques();

            configRecycleView();

            return insets;
        });
    }

    protected void onStart() {
        super.onStart();

        recuperaProdutos();

    }

    public void recuperaProdutos() {
        DatabaseReference produtosRef = FireBaseHelper.getDatabaseReference(); //nó principal
        produtosRef.child("produtos");
        produtosRef.child(FireBaseHelper.getIdFirebase());
        produtosRef.addListenerForSingleValueEvent((new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                produtoList.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Produto produto = snap.getValue(Produto.class);
                    produtoList.add(produto);
                }


                Collections.reverse(produtoList);

                //verificaQtdLista();

                adapterProduto.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }));

    }

    private void verificaQtdLista() {
        if (produtoList.size() == 0) {
            text_info.setText("Nenhum produto cadastrado");
            text_info.setVisibility(View.VISIBLE);
        } else {
            ibAdd.setVisibility(View.GONE);
            text_info.setVisibility(View.GONE);
        }
        progressBar.setVisibility(View.GONE);
    }

    private void ouvinteCliques() {
        ibAdd.setOnClickListener(view -> {
            startActivity(new Intent(this, FormProdutoActvty.class));
        });

        ibVerMais.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(this, ibVerMais);
            popupMenu.getMenuInflater().inflate(R.menu.menu_toolbar, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(MenuItem -> {
                if (MenuItem.getItemId() == R.id.menu_sobre) {
                    Toast.makeText(this, "Sobre", Toast.LENGTH_SHORT).show();
                } else if (MenuItem.getItemId() == R.id.menu_sair) {
                    FireBaseHelper.getAuth().signOut();
                    startActivity(new Intent(this, LoginActivity.class));
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
        adapterProduto = new AdapterProduto(produtoList, this);
        rvProdutos.setAdapter(adapterProduto);

        rvProdutos.setListener(new SwipeLeftRightCallback.Listener() {
            @Override
            public void onSwipedLeft(int position) {
            }

            @Override
            public void onSwipedRight(int position) {

                Produto produto = produtoList.get(position);

                produtoList.remove(produto);
                produto.deletaProduto();
               adapterProduto.notifyItemRemoved(position);

                verificaQtdLista();
            }
        });
    }

    @Override
    public Void onClickListener(Produto prooduto) {
        Intent intent = new Intent(this, FormProdutoActvty.class);
        intent.putExtra("produto", prooduto);
        startActivity(intent);
        return null;
    }

    private void iniciaComponentes() {
        ibAdd = findViewById(R.id.ib_add);
        ibVerMais = findViewById(R.id.ib_ver_mais);
        rvProdutos = findViewById(R.id.rvProdutos);


    }
}