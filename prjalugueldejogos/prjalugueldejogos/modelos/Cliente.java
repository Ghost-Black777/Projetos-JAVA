/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prjalugueldejogos.modelos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author felip
 */
public class Cliente {
    private String nome;
    private String cpf;
    private List<Jogo> jogosA; // 1:n
    
    public Cliente() {
        this.jogosA = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Jogo> getJogos() {
        return jogosA;
    }

    public void setJogos(List<Jogo> jogos) {
        this.jogosA = jogosA;
    }  
    
}
