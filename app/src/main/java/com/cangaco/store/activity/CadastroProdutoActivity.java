package com.cangaco.store.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cangaco.store.R;
import com.cangaco.store.controllers.ProdutoController;
import com.cangaco.store.models.Produtos;

public class CadastroProdutoActivity extends AppCompatActivity {
    private EditText edDescricaoItemVenda;
    private EditText edValorItemVenda;
    private TextView tvItemsVendaCadastrados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_itemvenda);

        Button btCadastrarItemVenda = findViewById(R.id.btCadastrarItemVenda);

        edDescricaoItemVenda = findViewById(R.id.edDescricaoItemVenda);
        edValorItemVenda = findViewById(R.id.edValorItemVenda);

        tvItemsVendaCadastrados = findViewById(R.id.tvItemsVendaCadastrados);

        atualizarListaItemsVenda();

        btCadastrarItemVenda.setOnClickListener(view -> salvarItemVenda());
    }

    private void salvarItemVenda() {
        if (edDescricaoItemVenda.getText().toString().isEmpty()) {
            edDescricaoItemVenda.setError("Informe uma descrição!");
            return;
        }

        if (edValorItemVenda.getText().toString().isEmpty()) {
            edValorItemVenda.setError("Informe o valor unitário!");
            return;
        }

        double valorUn;
        if (edValorItemVenda.getText().toString().isEmpty()) {
            edValorItemVenda.setError("O valor unitário deve ser informado!!");
            edValorItemVenda.requestFocus();
            return;
        } else {
            valorUn = Double.parseDouble(edValorItemVenda.getText().toString());
            if (valorUn <= 0) {
                edValorItemVenda.setError("O valor deve ser maior que zero!!");
                edValorItemVenda.requestFocus();
                return;
            }
        }

        Produtos produtos = new Produtos();
        produtos.setDescricao(edDescricaoItemVenda.getText().toString());
        produtos.setCod(Produtos.generateCodeFromDescription(produtos.getDescricao()));
        produtos.setValorUnitario(valorUn);

        ProdutoController.getInstance().salvarItemVenda(produtos);

        Toast.makeText(CadastroProdutoActivity.this,
                "Item cadastrado com sucesso!!",
                Toast.LENGTH_LONG).show();

        this.finish();
    }

    private void atualizarListaItemsVenda() {
        String texto = "";
        for (Produtos produtos : ProdutoController.getInstance().retornarItemsVenda()) {
            texto += "COD: " + produtos.getCod() + "\n" +
                    "Desc: " + produtos.getDescricao() + "\n" +
                    "Val. Un: " + produtos.getValorUnitario() + "\n";
        }

        tvItemsVendaCadastrados.setText(texto);
    }
}
