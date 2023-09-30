package com.cangaco.store.controllers;

import com.cangaco.store.models.Cliente;

import java.util.ArrayList;

public class ClienteController {
    private static ClienteController instance;
    private static ArrayList<Cliente> listaClientes;

    private ClienteController() {
        listaClientes = new ArrayList<>();
    }

    public static ClienteController getInstance() {
        if (instance == null) {
            instance = new ClienteController();
        }
        return instance;
    }

    public void salvarCliente(Cliente cliente) {
        listaClientes.add(cliente);
    }

    public ArrayList<Cliente> retornarClientes() {
        return listaClientes;
    }
}
