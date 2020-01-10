package com.example.mercado.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mercado.R;
import com.example.mercado.configuracao.BancoControle;
import com.example.mercado.model.Fruta;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterFrutas extends RecyclerView.Adapter<AdapterFrutas.ViewHolderFrutas>
        implements View.OnClickListener{

    List<Fruta> lista;

    private View.OnClickListener listener;

    private BancoControle banco;

    private Context context;

    private static DecimalFormat f = new DecimalFormat("0.00");

    public AdapterFrutas(List<Fruta> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolderFrutas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_frutas, null, false);

        view.setOnClickListener(this);

        return new ViewHolderFrutas(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFrutas holder, int position) {

         holder.textNome.setText(lista.get(position).getNome());
         holder.textPreco.setText(String.valueOf(f.format(lista.get(position).getPreco())));
         holder.imagemFrutas.setImageResource(lista.get(position).getImagem());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

    public class ViewHolderFrutas extends RecyclerView.ViewHolder {

        TextView textNome, textPreco;
        ImageView imagemFrutas;

        public ViewHolderFrutas(@NonNull View itemView) {
            super(itemView);

            textNome = itemView.findViewById(R.id.textFruta);
            textPreco = itemView.findViewById(R.id.textPreco);
            imagemFrutas = itemView.findViewById(R.id.imagemFrutas);

        }
    }
}
