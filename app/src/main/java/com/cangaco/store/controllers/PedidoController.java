package com.cangaco.store.controllers;

import com.cangaco.store.models.Pedido;

import java.util.ArrayList;

public class PedidoController {
    private static PedidoController instance;
    private static ArrayList<Pedido> listaPedidos;

    private PedidoController() {
        listaPedidos = new ArrayList<>();
    }

    public static PedidoController getInstance() {
        if (instance == null) {
            instance = new PedidoController();
        }
        return instance;
    }

    public void salvarPedido(Pedido pedido) {
        listaPedidos.add(pedido);
    }

    public ArrayList<Pedido> retornarPedidos() {
        return listaPedidos;
    }
}
