package com.cangaco.store.models;

public class Pedido {
    private Produtos itemPedido;
    private Cliente cliente;
    private int quantidadeItens;
    private double valorTotal;
    private boolean isCash;
    private int qtdParcelas;

    public Pedido() {}

    public Pedido(Produtos itemPedido, Cliente cliente, int quantidadeItens, double valorTotal, boolean isCash, int qtdParcelas) {
        this.itemPedido = itemPedido;
        this.cliente = cliente;
        this.quantidadeItens = quantidadeItens;
        this.valorTotal = valorTotal;
        this.isCash = isCash;
        this.qtdParcelas = qtdParcelas;
    }

    public String getItemPedido() {
        return itemPedido.getDescricao();
    }

    public void setItemPedido(Produtos itemPedido) {
        this.itemPedido = itemPedido;
    }

    public String getCliente() {
        return cliente.getNome();
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getQuantidadeItens() {
        return quantidadeItens;
    }

    public void setQuantidadeItens(int quantidadeItens) {
        this.quantidadeItens = quantidadeItens;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public boolean isCash() {
        return isCash;
    }

    public void setIsCash(boolean bool) {
        isCash = bool;
    }

    public int getQtdParcelas() {
        return qtdParcelas;
    }

    public void setQtdParcelas(int qtdParcelas) {
        this.qtdParcelas = qtdParcelas;
    }
}
