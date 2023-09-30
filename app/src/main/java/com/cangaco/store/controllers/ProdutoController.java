package com.cangaco.store.controllers;

import com.cangaco.store.models.Produtos;
import java.util.ArrayList;

public class ProdutoController {
    private static ProdutoController instance;
    private static ArrayList<Produtos> listaItemsVenda;

    private ProdutoController() {
        listaItemsVenda = new ArrayList<>();
    }
    public static ProdutoController getInstance() {
        if (instance == null) {
            instance = new ProdutoController();
        }
        return instance;
    }

    public void salvarItemVenda(Produtos Produtos) {
        listaItemsVenda.add(Produtos);
    }

    public ArrayList<Produtos> retornarItemsVenda() {
        return listaItemsVenda;
    }
}
