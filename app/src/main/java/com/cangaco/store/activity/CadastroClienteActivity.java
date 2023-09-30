package com.cangaco.store.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cangaco.store.R;
import com.cangaco.store.controllers.ClienteController;
import com.cangaco.store.models.Cliente;

public class CadastroClienteActivity extends AppCompatActivity {
    private EditText edCpfCliente;
    private EditText edNomeCliente;
    private TextView tvClientesCadastrados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);

        Button btCadastrarCliente = findViewById(R.id.btCadastrarCliente);

        edCpfCliente = findViewById(R.id.edCpfCliente);
        edNomeCliente = findViewById(R.id.edNomeCliente);

        tvClientesCadastrados = findViewById(R.id.tvClientesCadastrados);

        atualizarListaClientes();

        btCadastrarCliente.setOnClickListener(view -> salvarCliente());
    }

    private void salvarCliente() {
        if (edCpfCliente.getText().toString().isEmpty()) {
            edCpfCliente.setError("Informe o CPF!");
            return;
        }

        if (edNomeCliente.getText().toString().isEmpty()) {
            edNomeCliente.setError("Informe o Nome!");
            return;
        }

        Cliente cliente = new Cliente();
        cliente.setCpf(edCpfCliente.getText().toString());
        cliente.setNome(edNomeCliente.getText().toString());

        ClienteController.getInstance().salvarCliente(cliente);

        Toast.makeText(CadastroClienteActivity.this,
                "Cliente cadastrado com sucesso!!",
                Toast.LENGTH_LONG).show();

        this.finish();
    }

    private void atualizarListaClientes() {
        String texto = "";
        for (Cliente cliente : ClienteController.getInstance().retornarClientes()) {
            texto += "CPF: " + cliente.getCpf() + "\n" +
                    "Nome: " + cliente.getNome() + "\n" + "\n";
        }

        tvClientesCadastrados.setText(texto);
    }
}
