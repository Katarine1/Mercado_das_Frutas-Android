package com.example.mercado;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.mercado.adapter.AdapterFrutasPedidos;
import com.example.mercado.configuracao.BancoControle;
import com.example.mercado.configuracao.ContextoBanco;
import com.example.mercado.model.Fruta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MeusPedidosActivity extends AppCompatActivity {

    public TextView textSubtotal, textFrete, textApagar, textTotal;

    List<Fruta> lista;
    RecyclerView recyclerPedidos;

    Cursor cursor;
    BancoControle conecta;

    private static DecimalFormat f = new DecimalFormat("#.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_pedidos);

        inicializador();
    }

    public void inicializador(){

        textSubtotal = findViewById(R.id.textSubtotal);
        textFrete = findViewById(R.id.textFrete);
        textApagar = findViewById(R.id.textApagar);
        textTotal = findViewById(R.id.textTotal);

        conecta = new BancoControle(getApplicationContext());

        recyclerPedidos = findViewById(R.id.recyclerPedidos);

        recyclerPedidos.setLayoutManager(new LinearLayoutManager(this));

        listarDados();
        AdapterFrutasPedidos adapter = new AdapterFrutasPedidos(lista);
        recyclerPedidos.setAdapter(adapter);
    }

    public List<Fruta> listarDados(){

        cursor = conecta.listagem();
        Fruta fruta = null;
        lista = new ArrayList<>();

        try{
            while (cursor.moveToNext()){

                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                String nome = cursor.getString(cursor.getColumnIndex("nome"));
                double preco = cursor.getDouble(cursor.getColumnIndex("preco"));
                int qtd = cursor.getInt(cursor.getColumnIndex("quantidade"));

                fruta = new Fruta(id, nome, preco, qtd);
                lista.add(fruta);
                double soma =0.0;
                for(int i = 0 ; i < lista.size() ; i++){
                    soma += lista.get(i).getPreco() * lista.get(i).getQuantidade();
                }

                textSubtotal.setText(String.valueOf(f.format(soma)));
                double vFrete = 15.00;
                textFrete.setText(String.valueOf(f.format(vFrete)));
                double valorPagar = (soma + vFrete);
                double vezes = valorPagar/3;
                textApagar.setText("3 X " + String.valueOf(f.format(vezes)));
                textTotal.setText(String.valueOf(f.format(valorPagar)));
            }
            cursor.close();

            return lista;
        }
        catch (Exception e){
            Log.i("CONSULTA", "listarDados: "+ e.getMessage());
        }

        return null;
    }

    public void onFrutasPedidos(View view){
        startActivity(new Intent(this, InicioActivity.class));
    }

    public void onMinhaListaPedidos(View view){
        startActivity(new Intent(this, MinhaListaActivity.class));
    }

    public void onMeusPedidosPedidos(View view){
        startActivity(new Intent(this, MeusPedidosActivity.class));
    }
}
