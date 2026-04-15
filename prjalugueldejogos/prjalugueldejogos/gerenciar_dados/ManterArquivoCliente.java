/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prjalugueldejogos.gerenciar_dados;

import com.mycompany.prjalugueldejogos.modelos.Cliente;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author felip
 */
public class ManterArquivoCliente {

    private final String NOME_ARQUIVO = "C:\\Users\\felip\\OneDrive\\Área de Trabalho\\teste_Cliente.txt";

    public void escreverArquivo(Cliente cliente) {
        String texto = "CPF:" + cliente.getCpf() + ";Nome:" + cliente.getNome();
        File f = new File(NOME_ARQUIVO);
        
        try (FileWriter fw = new FileWriter(f, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(texto);
        } catch (IOException ex) {
            System.err.println("Erro ao escrever no arquivo de Cliente: " + ex.getMessage());
        }
    }

    public List<Cliente> lerArquivo() {
        List<Cliente> clientes = new ArrayList<>();
        File f = new File(NOME_ARQUIVO);
        
        if (!f.exists()) return clientes;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.contains("CPF:")) {
                    String[] partes = linha.split(";");
                    String cpf = partes[0].split(":")[1].trim();
                    String nome = partes[1].split(":")[1].trim();
                    
                    Cliente c = new Cliente();
                    c.setCpf(cpf);
                    c.setNome(nome);
                    clientes.add(c);
                }
            }
        } catch (IOException ex) {
            System.err.println("Erro ao ler o arquivo de Cliente: " + ex.getMessage());
        }
        return clientes;
    }
    
    //Busca feita pelo CPF
    public boolean clienteExiste(String cpfBusca) {
        List<Cliente> todosClientes = lerArquivo();
        for (Cliente c : todosClientes) {
            if (c.getCpf().equals(cpfBusca)) {
                return true;
            }
        }
        return false;
    } 
}