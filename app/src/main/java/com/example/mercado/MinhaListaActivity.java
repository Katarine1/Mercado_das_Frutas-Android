package com.example.mercado;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mercado.adapter.AdapterFrutasPedidos;
import com.example.mercado.configuracao.BancoControle;
import com.example.mercado.configuracao.ContextoBanco;
import com.example.mercado.model.Fruta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MinhaListaActivity extends AppCompatActivity {

    private static DecimalFormat f = new DecimalFormat("0.00");

    AlertDialog alert;
    String id, nome, preco, qtd;

    List<Fruta> lista;
    RecyclerView recyclerMinhasCompras;

    private AlertDialog alerta;

    Cursor cursor;
    BancoControle conecta;
    AdapterFrutasPedidos adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minha_lista);

        conecta = new BancoControle(getApplicationContext());

        recyclerMinhasCompras = findViewById(R.id.recyclerMinhaLista);

        recyclerMinhasCompras.setLayoutManager(new LinearLayoutManager(this));

        listarDados();
        adapter = new AdapterFrutasPedidos(lista);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getLista(v);
                viewLayoutLista();
            }
        });

        recyclerMinhasCompras.setAdapter(adapter);
    }

    public void getLista(View v){
        id = String.valueOf(lista.get(recyclerMinhasCompras.getChildAdapterPosition(v)).getId());
        nome = lista.get(recyclerMinhasCompras.getChildAdapterPosition(v)).getNome();
        preco = String.valueOf(f.format(lista.get(recyclerMinhasCompras.getChildAdapterPosition(v)).getPreco()));
        qtd = String.valueOf(lista.get(recyclerMinhasCompras.getChildAdapterPosition(v)).getQuantidade());
    }

    public void viewLayoutLista(){
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.lista_pedidos, null, false);
        final TextView tId, tNome, tPreco, tQtd;

        tId = view.findViewById(R.id.textId);
        tNome = view.findViewById(R.id.textFrutaPedido);
        tPreco = view.findViewById(R.id.textPrecoPedido);
        tQtd = view.findViewById(R.id.textQtdss);

        tId.setText(id);
        tNome.setText(nome);
        tPreco.setText(preco);
        tQtd.setText(qtd);

        LayoutInflater li = getLayoutInflater();

        View alertView = li.inflate(R.layout.layout_dialog, null);

        TextView msg = alertView.findViewById(R.id.t_msg);
        Button btn_cancelar = alertView.findViewById(R.id.btn_cancelar);
        Button btn_ok = alertView.findViewById(R.id.btn_ok);

        msg.setText("Deseja excluir o item "+tNome.getText().toString()+"?");

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MinhaListaActivity.class));
                alert.dismiss();
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Busca os dados por Id
                    cursor = conecta.listarPorId(tId.getText().toString());

                    while(cursor.moveToNext()){
                        tNome.setText(cursor.getString(0));
                        tPreco.setText(String.valueOf(cursor.getDouble(1)));
                        tQtd.setText(String.valueOf(cursor.getInt(2)));
                    }
                    cursor.close();

                    // Remove Dados
                    remove(tId.getText().toString());

                    alert.dismiss();

                } catch (Exception e) {
                    Log.i("EXCLUIR", "excluir: " + e.getMessage());
                }
                startActivity(new Intent(getApplicationContext(), MinhaListaActivity.class));
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(alertView);

        alert = builder.create();
        alert.show();
    }

    private void remove(String id) {
        conecta.remove(id);
        listarDados();
        Toast.makeText(getApplicationContext(), "Excluído com Sucesso!", Toast.LENGTH_SHORT).show();
    }

    public List<Fruta> listarDados() {

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
            }

            cursor.close();

            return lista;
        }
        catch (Exception e){
            Log.i("CONSULTA", "listarDados: "+ e.getMessage());
        }
        return null;
    }

    public void onFrutasCompras(View view) {
        startActivity(new Intent(this, InicioActivity.class));
    }

    public void onMinhaListaCompras(View view) {
        startActivity(new Intent(this, MinhaListaActivity.class));
    }

    public void onMeusPedidosCompras(View view) {
        startActivity(new Intent(this, MeusPedidosActivity.class));
    }
}
