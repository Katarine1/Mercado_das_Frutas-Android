package com.example.mercado;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mercado.adapter.AdapterFrutas;
import com.example.mercado.configuracao.BancoControle;
import com.example.mercado.model.Fruta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class InicioActivity extends AppCompatActivity {

    private static DecimalFormat f = new DecimalFormat("0.00");

    LinearLayout linearLayoutDados;

    Fruta fruta;
    List<Fruta> lista;
    RecyclerView recyclerFrutas;

    TextView text_fruta, text_preco;
    ImageView imagemFruta;
    EditText editQtd;

    BancoControle conecta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        text_fruta = findViewById(R.id.text_fruta);
        text_preco = findViewById(R.id.text_preco);
        imagemFruta = findViewById(R.id.imagemFrutasInicio);
        editQtd = findViewById(R.id.editQtd);
        linearLayoutDados = findViewById(R.id.linearLayoutDados);

        linearLayoutDados.setVisibility(View.GONE);

        conecta = new BancoControle(getApplicationContext());

        lista = new ArrayList<>();

        recyclerFrutas = findViewById(R.id.reciclerFrutas);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerFrutas.setLayoutManager(layoutManager);

        linearFrutas();

        AdapterFrutas adapter = new AdapterFrutas(lista);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String fruta = lista.get(recyclerFrutas.getChildAdapterPosition(view)).getNome();
               String preco = String.valueOf(lista.get(recyclerFrutas.getChildAdapterPosition(view)).getPreco());
               int imagem = lista.get(recyclerFrutas.getChildAdapterPosition(view)).getImagem();
               text_fruta.setText(fruta);
               text_preco.setText(preco);
               imagemFruta.setImageResource(imagem);
               linearLayoutDados.setVisibility(View.VISIBLE);
            }
        });

        recyclerFrutas.setAdapter(adapter);
    }

    private void linearFrutas() {

        lista.add(new Fruta("Abacate", 1.50, 0, R.drawable.abacate));
        lista.add(new Fruta("Abacaxi", 2.20, 0, R.drawable.abacaxi));
        lista.add(new Fruta("Acerola", 0.55, 0, R.drawable.acerola));
        lista.add(new Fruta("Amora", 0.62, 0, R.drawable.amora));
        lista.add(new Fruta("Banana", 2.43, 0, R.drawable.banana));
        lista.add(new Fruta("Cajú", 0.78, 0, R.drawable.caju));
        lista.add(new Fruta("Caqui", 0.97, 0, R.drawable.caqui));
        lista.add(new Fruta("Cereja", 0.32, 0, R.drawable.cereja));
        lista.add(new Fruta("Coco", 3.26, 0, R.drawable.coco));
        lista.add(new Fruta("Figo", 0.86, 0, R.drawable.figo));
        lista.add(new Fruta("Framboesa", 3.29, 0, R.drawable.framboesa));
        lista.add(new Fruta("Pinha", 4.20, 0, R.drawable.fruta_de_conde_pinha));
        lista.add(new Fruta("Goiaba", 1.57, 0, R.drawable.goiaba));
        lista.add(new Fruta("Jaca", 6.58, 0, R.drawable.jaca));
        lista.add(new Fruta("Kiwi", 0.95, 0, R.drawable.kiwi));
        lista.add(new Fruta("Laranja Lima", 0.66, 0, R.drawable.laranja_lima));
        lista.add(new Fruta("Laranja Pera", 0.68, 0, R.drawable.laranja_pera));
        lista.add(new Fruta("Laranja Seleta", 0.57, 0, R.drawable.laranja_seleta));
        lista.add(new Fruta("Maça", 0.25, 0, R.drawable.maca));
        lista.add(new Fruta("Mamão", 1.53, 0, R.drawable.mamao));
        lista.add(new Fruta("Manga", 1.21, 0, R.drawable.manga));
        lista.add(new Fruta("Maracujá", 1.15, 0, R.drawable.maracuja));
        lista.add(new Fruta("Melancia", 5.54, 0, R.drawable.melancia));
        lista.add(new Fruta("Melão", 3.78, 0, R.drawable.melao));
        lista.add(new Fruta("Mexirica", 0.81, 0, R.drawable.mexirica));
        lista.add(new Fruta("Morango", 1.12, 0, R.drawable.morango));
        lista.add(new Fruta("Pêra", 1.54, 0, R.drawable.pera));
        lista.add(new Fruta("Pêssego", 1.86, 0, R.drawable.pessego));
        lista.add(new Fruta("Pitanga", 1.42, 0, R.drawable.pitanga));
        lista.add(new Fruta("Romã", 1.73, 0, R.drawable.roma));
        lista.add(new Fruta("Tangerina", 1.72, 0, R.drawable.tangirina));
        lista.add(new Fruta("Uva", 3.59, 0, R.drawable.uva));
    }

    public void onClickFrutasInicio(View view){
        startActivity(new Intent(this, InicioActivity.class));
    }

    public void onClickMinhaListaInicio(View view){
        startActivity(new Intent(this, MinhaListaActivity.class));
    }

    public void onClickPedidoInicio(View view){
        startActivity(new Intent(this, MeusPedidosActivity.class));
    }

    public void onCompra(View view){
        inserir();
    }

    private void inserir() {
        try {

            String nome = text_fruta.getText().toString();
            double preco = Double.parseDouble(text_preco.getText().toString());
            int qtd = Integer.parseInt(editQtd.getText().toString());

            fruta = new Fruta(nome, preco, qtd);
            conecta.inserir(fruta);

            limpaCampos();
            Toast.makeText(this, "Salvo com Sucesso!", Toast.LENGTH_SHORT).show();
            linearLayoutDados.setVisibility(View.GONE);
        }
        catch (Exception e){
            Toast.makeText(this, "Selecione uma Fruta e a Quantidade", Toast.LENGTH_SHORT).show();
        }
    }


    public void limpaCampos(){
        text_fruta.setText("");
        text_preco.setText("");
        editQtd.setText("");
    }
}
