package com.cangaco.store.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.cangaco.store.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btCadastrarCliente = findViewById(R.id.btCadastrarCliente);
        Button btCadastrarItemVenda = findViewById(R.id.btCadastrarItemVenda);
        Button btCadastrarPedido = findViewById(R.id.btCadastrarPedido);

        btCadastrarCliente.setOnClickListener(view -> abrirActivity(CadastroClienteActivity.class));
        btCadastrarItemVenda.setOnClickListener(view -> abrirActivity(CadastroProdutoActivity.class));
        btCadastrarPedido.setOnClickListener(view -> abrirActivity(CadastroPedidoActivity.class));
    }

    private void abrirActivity(Class<?> activity) {
        Intent intent = new Intent(MainActivity.this, activity);

        startActivity(intent);
    }
}