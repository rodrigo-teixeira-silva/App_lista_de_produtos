package com.example.controledeprodutos.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controledeprodutos.model.Produto;
import com.example.controledeprodutos.R;

import java.util.List;

public class AdapterProduto extends RecyclerView.Adapter<AdapterProduto.myViewHolder> {

    private List<Produto>produtoList;
    private OnClick onClick;

    public AdapterProduto(List<Produto> produtoList, OnClick onClick) {
        this.produtoList = produtoList;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produto,parent,false);
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
    Produto produto = produtoList .get(position);

    holder.textProduto.setText(produto.getNome());
    holder.textEstoque.setText("Estoque: " + produto.getEstoque());
    holder.textValor.setText(String.valueOf("R$ " + produto.getValor()));
    holder.itemView.setOnClickListener(View -> onClick.onClickListener(produto));
    }

    @Override
    public int getItemCount() {
        return produtoList.size();
    }

    public interface OnClick{
        Void onClickListener(Produto produto);
    }

    static class myViewHolder extends RecyclerView.ViewHolder{
        TextView textProduto, textEstoque, textValor;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            textProduto = itemView.findViewById(R.id.text_produto);
            textEstoque = itemView.findViewById(R.id.text_estoque);
            textValor = itemView.findViewById(R.id.text_valor);
            }
    }
}
