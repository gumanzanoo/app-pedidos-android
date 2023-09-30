package com.cangaco.store.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cangaco.store.controllers.ClienteController;
import com.cangaco.store.controllers.ProdutoController;
import com.cangaco.store.controllers.PedidoController;
import com.cangaco.store.models.Cliente;
import com.cangaco.store.models.Produtos;
import com.cangaco.store.models.Pedido;

import com.cangaco.store.R;

import java.util.ArrayList;

public class CadastroPedidoActivity extends AppCompatActivity {
    private TextView tvPedidoCadastrado;
    private EditText edQtdProduto;
    private Spinner spProduto;
    private Spinner spCliente;
    private ArrayList<Produtos> listaProdutos;
    private ArrayList<Cliente> listaClientes;
    private int posicaoSelecionadaProduto = 0;
    private int posicaoSelecionadaCliente = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pedido);

        TextView tvListaProdutos = findViewById(R.id.tvListaProdutos);
        TextView tvListaClientes = findViewById(R.id.tvListaClientes);
        tvPedidoCadastrado = findViewById(R.id.tvPedidoCadastrado);

        RadioGroup rgPaymentType = findViewById(R.id.rgPaymentType);
        EditText edInstallmentCount = findViewById(R.id.edInstallmentCount);

        edQtdProduto = findViewById(R.id.edQtdProduto);

        spProduto = findViewById(R.id.spProduto);
        spCliente = findViewById(R.id.spCliente);

        Button btCadastrar = findViewById(R.id.btCadastrar);

        spProduto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView,
                                       View view, int posicao, long l) {
                if (posicao > 0) {
                    posicaoSelecionadaProduto = posicao;
                    tvListaProdutos.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spCliente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView,
                                       View view, int posicao, long l) {
                if (posicao > 0) {
                    posicaoSelecionadaCliente = posicao;
                    tvListaClientes.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        rgPaymentType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbCash) {
                edInstallmentCount.setVisibility(View.GONE);
            } else if (checkedId == R.id.rbInstallment) {
                edInstallmentCount.setVisibility(View.VISIBLE);
            }
        });

        carregaItems();
        carregaClientes();
        atualizarListaPedidos();
        btCadastrar.setOnClickListener(view -> cadastrarPedido());
    }

    private void cadastrarPedido() {
        int qtdProduto;

        if (edQtdProduto.getText().toString().isEmpty()) {
            edQtdProduto.setError("A quantidade deve ser informada!!");
            edQtdProduto.requestFocus();
            return;
        } else {
            qtdProduto = Integer.parseInt(edQtdProduto.getText().toString());
            if (qtdProduto <= 0) {
                edQtdProduto.setError("A quantidade deve ser maior que zero!!");
                edQtdProduto.requestFocus();
                return;
            }
        }

        Produtos produtos = listaProdutos.get(posicaoSelecionadaProduto - 1);
        Cliente cliente = listaClientes.get(posicaoSelecionadaCliente - 1);

        boolean isCash = ((RadioButton) findViewById(R.id.rbCash)).isChecked();

        double valorTotal = calcularValorTotal(qtdProduto, isCash, produtos.getValorUnitario());

        Pedido pedido = new Pedido();
        pedido.setItemPedido(produtos);
        pedido.setQuantidadeItens(qtdProduto);
        pedido.setValorTotal(valorTotal);
        pedido.setCliente(cliente);
        pedido.setIsCash(isCash);
        pedido.setQtdParcelas(isCash ? 1 : Integer.parseInt(((EditText) findViewById(R.id.edInstallmentCount)).getText().toString()));

        PedidoController.getInstance().salvarPedido(pedido);

        Toast.makeText(this,
                "Pedido cadastrado com sucesso!!",
                Toast.LENGTH_LONG).show();

        finish();
    }

    private double calcularValorTotal(int qtdProduto, boolean isCash, double valorUnitario) {
        double valorTotal = valorUnitario * qtdProduto;

        if (!isCash) {
            valorTotal *= 1.05;
        } else {
            valorTotal *= 0.95;
        }

        return valorTotal;
    }

    public double valParcelas(double valor, int parcelas) {
            return (valor / parcelas);
    }

    private void atualizarListaPedidos() {
        String texto = "";

        for (Pedido Pedido : PedidoController.getInstance().retornarPedidos()) {
            String pagamento = Pedido.isCash() ? "A vista" : "A prazo - " + Pedido.getQtdParcelas() + "/" + valParcelas(Pedido.getValorTotal(), Pedido.getQtdParcelas());
            texto += "Produto: " + Pedido.getItemPedido() + "\n" +
                     "Quantidade: " + Pedido.getQuantidadeItens() + "\n" +
                     "Cliente: " + Pedido.getCliente() + "\n" +
                     "Pagamento: " + pagamento + "\n" +
                     "Valor total: " + Pedido.getValorTotal() + "\n" + "\n";
        }

        tvPedidoCadastrado.setText(texto);
    }

    private void carregaItems() {
        listaProdutos = ProdutoController.getInstance().retornarItemsVenda();
        String[] vetProds = new String[listaProdutos.size() + 1];
        vetProds[0] = "Selecione o produto";
        for (int i = 0; i < listaProdutos.size(); i++) {
            Produtos produtos = listaProdutos.get(i);
            vetProds[i + 1] = produtos.getCod() + " - " + produtos.getDescricao() + " - " + produtos.getValorUnitario();
        }

        ArrayAdapter adapter = new ArrayAdapter(
                CadastroPedidoActivity.this,
                android.R.layout.simple_dropdown_item_1line,
                vetProds);

        spProduto.setAdapter(adapter);
    }

    private void carregaClientes() {
        listaClientes = ClienteController.getInstance().retornarClientes();
        String[] vetClientes = new String[listaProdutos.size() + 1];
        vetClientes[0] = "Selecione o cliente";
        for (int i = 0; i < listaClientes.size(); i++) {
            Cliente cliente = listaClientes.get(i);
            vetClientes[i + 1] = cliente.getCpf() + " - " + cliente.getNome();
        }

        ArrayAdapter adapter = new ArrayAdapter(
                CadastroPedidoActivity.this,
                android.R.layout.simple_dropdown_item_1line,
                vetClientes);

        spCliente.setAdapter(adapter);
    }
}
