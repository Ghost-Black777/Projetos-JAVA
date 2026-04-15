/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prjalugueldejogos.modelos;

/**
 *
 * @author felip
 */
public class JogoDigital extends Jogo{
    
    private String chaveAcesso;
    private int horasValidade;

    public String getChaveAcesso() {
        return chaveAcesso;
    }

    public void setChaveAcesso(String chaveAcesso) {
        this.chaveAcesso = chaveAcesso;
    }

    public int getHorasValidade() {
        return horasValidade;
    }

    public void setHorasValidade(int horasValidade) {
        this.horasValidade = horasValidade;
    }

    @Override
    public String toString() {
        return String.format("DIGITAL | Titulo: %s | Plataforma: %s | Aluguel: R$ %.2f | Key: %s | Validade: %d horas",
                getTitulo(), getPlataforma(), getPrecoAluguel(), getChaveAcesso(), getHorasValidade());
    }


}
