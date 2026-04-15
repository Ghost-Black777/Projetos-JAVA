package com.mycompany.prjalugueldejogos.menus;

import com.mycompany.prjalugueldejogos.gerenciar_dados.ManterArquivoCliente;
import com.mycompany.prjalugueldejogos.gerenciar_dados.ManterArquivoJogo;
import com.mycompany.prjalugueldejogos.modelos.Cliente;
import com.mycompany.prjalugueldejogos.modelos.Jogo;
import com.mycompany.prjalugueldejogos.modelos.JogoDigital;
import com.mycompany.prjalugueldejogos.modelos.JogoFisico;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/* * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
**/
/**
 *
 * @author felip
 */
public class Menu {
    
    List<Jogo> todosJogos = new ArrayList();
    private ManterArquivoCliente manterArquivoCliente = new ManterArquivoCliente();
    private ManterArquivoJogo manterArquivoJogo = new ManterArquivoJogo();
    private Scanner scanner = new Scanner(System.in);

    public void exibirMenu() {
        int opcao = 0;

 do {
            System.out.println("\n====== SISTEMA DE ALUGUEL DE JOGOS ======");
            System.out.println("1. Cadastrar Novo Cliente");
            System.out.println("2. Alugar novo jogo para Cliente existente");
            System.out.println("3. Consultar Jogos alugados por CPF");
            System.out.println("4. Listar todos jogos alugados");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opcao: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite um numero valido.");
                continue;
            }

            switch (opcao) {
                case 1:
                    cadastrarNovoCliente();
                    break;
                case 2:
                    alugarJogoClienteExistente();
                    break;
                case 3:
                    consultarJogos();
                    break;
                    
                case 4:
                    mostrarJogosAlugados();
                    break;
                case 5:
                    System.out.println("Encerrando o sistema... Ate logo!");
                    break;
                default:
                    System.out.println("Opcao invalida!");
            }
        } while (opcao != 5);

        scanner.close();
    }

    private void cadastrarNovoCliente() {
        System.out.println("\n--- Cadastro de Novo Cliente ---");
        System.out.print("Nome do Cliente: ");
        String nome = scanner.nextLine();
        System.out.print("CPF do Cliente: ");
        String cpf = scanner.nextLine();

        Cliente cli = new Cliente();
        cli.setNome(nome);
        cli.setCpf(cpf);
        
        manterArquivoCliente.escreverArquivo(cli);

        System.out.println("Cliente '" + nome + "' cadastrado com sucesso!");
        
        // CPF vincula aos jogos
        cadastrarJogos(cpf);
    }

    private void alugarJogoClienteExistente() {
        System.out.println("\n--- Alugar Jogo para Cliente Existente ---");
        System.out.print("Digite o CPF do Cliente: ");
        String cpf = scanner.nextLine();

        if (manterArquivoCliente.clienteExiste(cpf)) {
            System.out.println("Cliente encontrado! Vamos adicionar os jogos.");
            cadastrarJogos(cpf);
        } else {
            System.out.println("Erro: Nenhum cliente encontrado com o CPF '" + cpf + "'.");
        }
    }

    private void cadastrarJogos(String cpfCliente) {
        String resposta;

        do {
            System.out.print("\nDeseja alugar um jogo para este cliente? (S/N): ");
            resposta = scanner.nextLine().toUpperCase();

            if (resposta.equals("S")) {
                System.out.print("O jogo eh Fisico (1) ou Digital (2)? ");
                String tipo = scanner.nextLine();

                System.out.print("Titulo do jogo: ");
                String titulo = scanner.nextLine();
                System.out.print("Plataforma: ");
                String plataforma = scanner.nextLine();

                if (tipo.equals("1")) {
                    // --- LOGICA DO JOGO FISICO ---
                    System.out.print("Tempo de aluguel (em dias): ");
                    int dias = Integer.parseInt(scanner.nextLine());
                    
                    double preco = dias * 8.99;
                    
                    System.out.print("O cliente vai retirar na loja? (S/N): ");
                    boolean retirada = scanner.nextLine().trim().toUpperCase().equals("S");
                    
                    double taxa = 0.0;
                    if (!retirada) {
                        taxa = 5.00;
                        System.out.println("Taxa de entrega fixada em R$ 5,00.");
                    }
                    
                    JogoFisico jf = new JogoFisico();
                    jf.setTitulo(titulo);
                    jf.setPlataforma(plataforma);
                    jf.setPrecoAluguel(preco);
                    jf.setDiasAluguel(dias);
                    jf.setRetiradaNaLoja(retirada);
                    jf.setTaxaEntrega(taxa);
                    
                    manterArquivoJogo.escreverArquivo(jf, cpfCliente);
                    System.out.println("Jogo fisico salvo com sucesso!");
                    System.out.println("-> Valor total do aluguel calculado: R$ " + String.format("%.2f", preco));
                    todosJogos.add(jf);
                    
                } else if (tipo.equals("2")) {
                    // --- LOGICA DO JOGO DIGITAL ---
                    System.out.print("Tempo de assinatura (em horas): ");
                    int horas = Integer.parseInt(scanner.nextLine());
                    
                    double preco = horas * 1.50;
                    
                    // GERAÇÃO AUTOMÁTICA DA KEY:
                    // Pega 8 caracteres aleatórios gerados pelo Java e transforma em Maiúsculo
                    String rawKey = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
                    // Coloca um tracinho no meio pra ficar bonitinho (ex: A1B2-C3D4)
                    String keyGerada = rawKey.substring(0, 4) + "-" + rawKey.substring(4, 8);
                    
                    JogoDigital jd = new JogoDigital();
                    jd.setTitulo(titulo);
                    jd.setPlataforma(plataforma);
                    jd.setPrecoAluguel(preco);
                    jd.setChaveAcesso(keyGerada); // Passa a Key que o sistema acabou de criar
                    jd.setHorasValidade(horas);
                    
                    manterArquivoJogo.escreverArquivo(jd, cpfCliente);
                    System.out.println("Jogo digital salvo com sucesso!");
                    
                    // Exibe a Key gerada para o atendente passar para o cliente
                    System.out.println("-> KEY GERADA: " + keyGerada); 
                    System.out.println("-> Valor total da assinatura calculado: R$ " + String.format("%.2f", preco));
                    todosJogos.add(jd);
                } else {
                    System.out.println("Tipo invalido. Jogo nao cadastrado.");
                }
            }
        } while (resposta.equals("S"));
    }

    private void consultarJogos() {
        System.out.println("\n--- Consultar Jogos Alugados ---");
        System.out.print("Digite o CPF do Cliente para buscar os jogos: ");
        String cpf = scanner.nextLine();

        List<Jogo> jogos = manterArquivoJogo.buscarJogosPorCliente(cpf);
        
        if (jogos.isEmpty()) {
            System.out.println("Nenhum jogo encontrado para este CPF.");
        } else {
            System.out.println("\n--- Jogos Encontrados (" + jogos.size() + ") ---");
            for (Jogo j : jogos) {
                System.out.println(j.toString());
            }
            System.out.println("--------------------------------");
        }
    }
    
    private void mostrarJogosAlugados(){
        
        if (todosJogos == null || todosJogos.isEmpty()) {
            System.out.println("Nenhum jogo encontrado.");
            return;
        }

        System.out.println("------ Jogos encontrados ------");
        
        for (Jogo jogo : todosJogos) {
            System.out.println("Titulo: " + jogo.getTitulo());
            System.out.println("Plataforma: " + jogo.getPlataforma());
            System.out.println("Preco: " + jogo.getPrecoAluguel());
            
            if (jogo instanceof JogoFisico) {
                JogoFisico jf = (JogoFisico) jogo;
                System.out.println("Taxa de entrega: " + jf.getTaxaEntrega());
                System.out.println("Dias de aluguel: " + jf.getDiasAluguel());
                
            } else if (jogo instanceof JogoDigital) {
                JogoDigital jd = (JogoDigital) jogo;
                System.out.println("ChaveAcesso: " + jd.getChaveAcesso());
                System.out.println("HorasValidade: " + jd.getHorasValidade());
            }
            System.out.println("-------------------------------"); 
        }
    }
}