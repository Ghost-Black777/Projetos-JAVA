/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prjalugueldejogos.gerenciar_dados;

import com.mycompany.prjalugueldejogos.modelos.Jogo;
import com.mycompany.prjalugueldejogos.modelos.JogoDigital;
import com.mycompany.prjalugueldejogos.modelos.JogoFisico;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author felip
 */
public class ManterArquivoJogo {
    
       private final String NOME_ARQUIVO = "C:\\Users\\felip\\OneDrive\\Área de Trabalho\\teste_Jogo.txt";

 // recebe o cpf do cliente para vincular o jogo
    public void escreverArquivo(Jogo j, String cpfCliente) {
        String texto = obj2Str(j, cpfCliente);
        File f = new File(NOME_ARQUIVO);
        try (FileWriter fw = new FileWriter(f, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(texto);
        } catch (IOException ex) {
            System.err.println("Erro ao escrever no arquivo: " + ex.getMessage());
        }
    }

    public List<Jogo> lerArquivo() {
        List<Jogo> jogos = new ArrayList<>();
        File f = new File(NOME_ARQUIVO);
        if (!f.exists()) return jogos;

        try (FileReader fr = new FileReader(f);
             BufferedReader br = new BufferedReader(fr)) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    jogos.add(str2Obj(linha));
                }
            }
        } catch (IOException ex) {
            System.err.println("Erro ao ler o arquivo: " + ex.getMessage());
        }
        return jogos;
    }

    // Busca os jogos pelo CPF
    public List<Jogo> buscarJogosPorCliente(String cpfCliente) {
        List<Jogo> jogosEncontrados = new ArrayList<>();
        File f = new File(NOME_ARQUIVO);
        if (!f.exists()) return jogosEncontrados;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.contains("ClienteCPF:" + cpfCliente + ";")) {
                    jogosEncontrados.add(str2Obj(linha));
                }
            }
        } catch (IOException ex) {
            System.err.println("Erro ao buscar jogos: " + ex.getMessage());
        }
        return jogosEncontrados;
    }
    
    // Busca lista de todos os jogos alugados
        public List<Jogo> buscarJogosAlugados () {
        List<Jogo> jogosEncontrados = new ArrayList<>();
        File f = new File(NOME_ARQUIVO);
        if (!f.exists()) return jogosEncontrados;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.contains("ClienteCPF:")) {
                    jogosEncontrados.add(str2Obj(linha));
                }
            }
        } catch (IOException ex) {
            System.err.println("Erro ao buscar jogos: " + ex.getMessage());
        }
        return jogosEncontrados;
    }
    

    private String obj2Str(Jogo j, String cpfCliente) {
        StringBuilder sb = new StringBuilder();
        sb.append("ClienteCPF:").append(cpfCliente).append(";");
        sb.append("Titulo:").append(j.getTitulo()).append(";");
        sb.append("Plataforma:").append(j.getPlataforma()).append(";");
        sb.append("Preco:").append(j.getPrecoAluguel()).append(";");

        if (j instanceof JogoFisico jf) {
            sb.append("Tipo:Fisico;");
            sb.append("Dias:").append(jf.getDiasAluguel()).append(";");
            sb.append("Retirada:").append(jf.isRetiradaNaLoja()).append(";");
            sb.append("Taxa:").append(jf.getTaxaEntrega());
        } else if (j instanceof JogoDigital jd) {
            sb.append("Tipo:Digital;");
            sb.append("Key:").append(jd.getChaveAcesso()).append(";");
            sb.append("Horas:").append(jd.getHorasValidade());
        }
        return sb.toString();
    }

    private Jogo str2Obj(String texto) {
        Map<String, String> atributos = extraiAtributos(texto);

        String titulo = atributos.get("Titulo");
        String plataforma = atributos.get("Plataforma");
        double preco = Double.parseDouble(atributos.get("Preco"));
        String tipo = atributos.get("Tipo");

        if ("Fisico".equalsIgnoreCase(tipo)) {
            JogoFisico jf = new JogoFisico();
            jf.setTitulo(titulo);
            jf.setPlataforma(plataforma);
            jf.setPrecoAluguel(preco);
            jf.setDiasAluguel(Integer.parseInt(atributos.get("Dias")));
            jf.setRetiradaNaLoja(Boolean.parseBoolean(atributos.get("Retirada")));
            jf.setTaxaEntrega(Double.parseDouble(atributos.get("Taxa")));
            return jf;
            
        } else if ("Digital".equalsIgnoreCase(tipo)) {
            JogoDigital jd = new JogoDigital();
            jd.setTitulo(titulo);
            jd.setPlataforma(plataforma);
            jd.setPrecoAluguel(preco);
            jd.setChaveAcesso(atributos.get("Key"));
            jd.setHorasValidade(Integer.parseInt(atributos.get("Horas")));
            return jd;
        }
        return null;
    }

    private Map<String, String> extraiAtributos(String texto) {
        Map<String, String> mapa = new HashMap<>();
        String[] partes = texto.split(";");
        for (String parte : partes) {
            if (parte.contains(":")) {
                String[] chaveValor = parte.split(":", 2);
                mapa.put(chaveValor[0].trim(), chaveValor[1].trim());
            }
        }
        return mapa;
    }
}