/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prjalugueldejogos.modelos;

/**
 *
 * @author felip
 */
public class JogoFisico extends Jogo {

    private double taxaEntrega;
    private boolean retiradaNaLoja;
    private int diasAluguel;

    public double getTaxaEntrega() {
        return taxaEntrega;
    }

    public void setTaxaEntrega(double taxaEntrega) {
        this.taxaEntrega = taxaEntrega;
    }

    public int getDiasAluguel() {
        return diasAluguel;
    }

    public void setDiasAluguel(int diasAluguel) {
        this.diasAluguel = diasAluguel;
    }

    public boolean isRetiradaNaLoja() {
        return retiradaNaLoja;
    }

    public void setRetiradaNaLoja(boolean retiradaNaLoja) {
        this.retiradaNaLoja = retiradaNaLoja;
    }

    @Override
    public String toString() {
        String infoEntrega = retiradaNaLoja ? "Retirada na Loja(Gratis)" : String.format("Taxa de Entrega: R$ %.2f", taxaEntrega);
        return String.format("FISICO | Titulo: %s | Plataforma: %s | Aluguel: R$: %.2f | Tempo: %d dias | %s",
                getTitulo(), getPlataforma(), getPrecoAluguel(), diasAluguel, infoEntrega);

    }
}
