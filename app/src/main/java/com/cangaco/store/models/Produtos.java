package com.cangaco.store.models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Produtos {
    private String cod;
    private String descricao;
    private double valorUnitario;

    public Produtos() {
    }

    public Produtos(String cod, String descricao, double valorUnitario) {
        this.cod = cod;
        this.descricao = descricao;
        this.valorUnitario = valorUnitario;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public static String generateCodeFromDescription(String description) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] descriptionBytes = description.getBytes();

            byte[] hashBytes = md.digest(descriptionBytes);

            StringBuilder cod = new StringBuilder();
            for (byte b : hashBytes) {
                cod.append(String.format("%02x", b));
            }

            return cod.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {

    }
}