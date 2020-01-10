package com.example.mercado.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mercado.R;
import com.example.mercado.model.Fruta;
import com.example.mercado.util.CalculoPrecoQtd;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterFrutasPedidos extends RecyclerView.Adapter<AdapterFrutasPedidos.ViewHolderFrutas>
        implements View.OnClickListener {

    private static DecimalFormat f = new DecimalFormat("0.00");

    List<Fruta> lista;
    private Context context;
    private View.OnClickListener listener;

    public AdapterFrutasPedidos(Context context) {
        this.context = context;
    }

    public AdapterFrutasPedidos(List<Fruta> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolderFrutas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_pedidos, null, false);

        view.setOnClickListener(this);

        return new AdapterFrutasPedidos.ViewHolderFrutas(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderFrutas holder, final int position) {

        final int id = lista.get(position).getId();
        final double preco = lista.get(position).getPreco();
        final int qtd = lista.get(position).getQuantidade();
        final String nome = lista.get(position).getNome();

        CalculoPrecoQtd cal = new CalculoPrecoQtd();
        final double calculo = cal.precoQtd(preco, qtd);

        holder.textId.setText(String.valueOf(id));
        holder.textNomeP.setText(nome);
        holder.textPrecoP.setText(String.valueOf(f.format(calculo)));
        holder.textQtdsP.setText(String.valueOf(qtd));
    }

    @Override
    public int getItemCount() {

        return lista.size();
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolderFrutas extends RecyclerView.ViewHolder {

        TextView textId, textNomeP, textPrecoP, textQtdsP;

        public ViewHolderFrutas(@NonNull View itemView) {
            super(itemView);

            textId = itemView.findViewById(R.id.textId);
            textNomeP = itemView.findViewById(R.id.textFrutaPedido);
            textPrecoP = itemView.findViewById(R.id.textPrecoPedido);
            textQtdsP = itemView.findViewById(R.id.textQtdss);
        }
    }
}
