
import java.util.Scanner;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Appcontrato {
    private static final List<contrato> contratos = new ArrayList<>();
    private static int proximoId = 1;
    private static final Scanner scanner = new Scanner(System.in);
    private static final String ARQUIVO_DADOS = "contratos.txt";
    private static final DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        try (scanner) {
            carregarDados();
            
            int opcao;
            do {
                exibirMenu();
                opcao = lerInteiro("Escolha uma opção: ");
                scanner.nextLine(); // Limpar buffer
                
                switch (opcao) {
                    case 1 -> criarContrato();
                    case 2 -> listarContratos();
                    case 3 -> buscarContrato();
                    case 4 -> editarContrato();
                    case 5 -> excluirContrato();
                    case 6 -> System.out.println("Saindo do sistema...");
                    default -> System.out.println("Opção inválida! Tente novamente.");
                }
            } while (opcao != 6);
        }
    }
    
    private static void exibirMenu() {
        System.out.println("\n=== SISTEMA DE GERENCIAMENTO DE CONTRATOS ===");
        System.out.println("1. Criar novo contrato");
        System.out.println("2. Listar todos os contratos");
        System.out.println("3. Buscar contrato (por ID)");
        System.out.println("4. Editar contrato");
        System.out.println("5. Excluir contrato");
        System.out.println("6. Sair");
    }
    
    // Métodos auxiliares para validação
    private static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número inteiro válido.");
            }
        }
    }
    
    private static double lerDouble(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
            }
        }
    }
    
    private static String lerStringValidada(String mensagem, String campo, boolean permitirNumeros) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            
            if (entrada.isEmpty()) {
                System.out.println(campo + " não pode ser vazio!");
                continue;
            }
            
            if (!permitirNumeros && !entrada.matches("[a-zA-ZÀ-ú\\s]+")) {
                System.out.println(campo + " deve conter apenas letras e espaços!");
                continue;
            }
            
            return entrada;
        }
    }
    
    private static String lerCPF(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String cpf = scanner.nextLine().trim();
            
            if (ValidaCPF.validarCPF(cpf)) {
                return ValidaCPF.imprimeCPF(cpf);
            }
            System.out.println("CPF inválido! Digite novamente.");
        }
    }
    
    private static LocalDate lerData(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return LocalDate.parse(scanner.nextLine(), formatoData);
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida! Use o formato DD/MM/AAAA.");
            }
        }
    }
    
    private static double lerValorContrato(String mensagem) {
        while (true) {
            double valor = lerDouble(mensagem);
            if (valor > 0) {
                return valor;
            }
            System.out.println("O valor do contrato deve ser maior que zero!");
        }
    }
    
    // Métodos CRUD
    private static void criarContrato() {
        System.out.println("\n--- NOVO CONTRATO ---");
        
        String contratante = lerStringValidada("Contratante: ", "Contratante", false);
        String contratado = lerStringValidada("Contratado: ", "Contratado", false);
        String numeroContrato = lerStringValidada("Número do contrato: ", "Número do contrato", true);
        String descricao = lerStringValidada("Descrição: ", "Descrição", true);
        String cpf = lerCPF("CPF do contratante: ");
        
        LocalDate celebracao, inicio, termino;
        do {
            celebracao = lerData("Data de celebração (DD/MM/AAAA): ");
            inicio = lerData("Data de início (DD/MM/AAAA): ");
            termino = lerData("Data de término (DD/MM/AAAA): ");
            
            if (celebracao.isAfter(termino)) {
                System.out.println("Data de celebração não pode ser posterior à data de término!");
            } else if (inicio.isAfter(termino)) {
                System.out.println("Data de início não pode ser posterior à data de término!");
            } else if (termino.isBefore(celebracao) || termino.isBefore(inicio)) {
                System.out.println("Data de término não pode ser anterior às outras datas!");
            } else {
                break;
            }
            System.out.println("Por favor, insira as datas novamente.\n");
        } while (true);
        
        double valor = lerValorContrato("Valor do contrato: R$");
        
        contrato novoContrato = new contrato(
            proximoId++, contratante, contratado, numeroContrato, 
            descricao, valor, celebracao, inicio, termino, cpf
        );
        
        contratos.add(novoContrato);
        salvarDados();
        System.out.println("\nContrato cadastrado com sucesso! ID: " + novoContrato.getId());
    }
    
    private static void listarContratos() {
        System.out.println("\n--- LISTA DE CONTRATOS ---");
        
        if (contratos.isEmpty()) {
            System.out.println("Nenhum contrato cadastrado.");
        } else {
            for (contrato contrato : contratos) {
                System.out.println(contrato);
            }
        }
    }
    
    private static void buscarContrato() {
        System.out.println("\n--- BUSCAR CONTRATO ---");
        
        int id = lerInteiro("Digite o ID do contrato: ");
        
        for (contrato contrato : contratos) {
            if (contrato.getId() == id) {
                System.out.println("Contrato encontrado:");
                System.out.println(contrato);
                return;
            }
        }
        
        System.out.println("Contrato com ID " + id + " não encontrado.");
    }
    
    private static void editarContrato() {
        System.out.println("\n--- EDITAR CONTRATO ---");
        
        int id = lerInteiro("Digite o ID do contrato que deseja editar: ");
        contrato contrato = contratos.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
        
        if (contrato == null) {
            System.out.println("Contrato com ID " + id + " não encontrado.");
            return;
        }
        
        System.out.println("Editando contrato:");
        System.out.println(contrato);
        System.out.println("\nDigite os novos dados (deixe em branco para manter o valor atual):");
        
        // Editar campos
        System.out.print("Contratante (" + contrato.getContratante() + "): ");
        String contratante = scanner.nextLine();
        if (!contratante.isEmpty()) {
            contrato.setContratante(contratante);
        }
        
        System.out.print("Contratado (" + contrato.getContratado() + "): ");
        String contratado = scanner.nextLine();
        if (!contratado.isEmpty()) {
            contrato.setContratado(contratado);
        }
        
        System.out.print("Número do contrato (" + contrato.getNumeroContrato() + "): ");
        String numeroContrato = scanner.nextLine();
        if (!numeroContrato.isEmpty()) {
            contrato.setNumeroContrato(numeroContrato);
        }
        
        System.out.print("Descrição (" + contrato.getDescricao() + "): ");
        String descricao = scanner.nextLine();
        if (!descricao.isEmpty()) {
            contrato.setDescricao(descricao);
        }
        
        System.out.print("CPF (" + contrato.getCpf() + "): ");
        String cpf = scanner.nextLine();
        if (!cpf.isEmpty()) {
            if (ValidaCPF.validarCPF(cpf)) {
                contrato.setCpf(ValidaCPF.imprimeCPF(cpf));
            } else {
                System.out.println("CPF inválido! Não foi alterado.");
            }
        }
        
        // Editar datas
        System.out.println("Datas (deixe em branco para manter as atuais):");
        System.out.print("Data de celebração (" + contrato.getCelebracaoFormatada() + "): ");
        String celebracaoStr = scanner.nextLine();
        if (!celebracaoStr.isEmpty()) {
            try {
                LocalDate celebracao = LocalDate.parse(celebracaoStr, formatoData);
                contrato.setCelebracao(celebracao);
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida! Não foi alterada.");
            }
        }
        
        System.out.print("Data de início (" + contrato.getInicioFormatado() + "): ");
        String inicioStr = scanner.nextLine();
        if (!inicioStr.isEmpty()) {
            try {
                LocalDate inicio = LocalDate.parse(inicioStr, formatoData);
                contrato.setInicio(inicio);
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida! Não foi alterada.");
            }
        }
        
        System.out.print("Data de término (" + contrato.getTerminoFormatado() + "): ");
        String terminoStr = scanner.nextLine();
        if (!terminoStr.isEmpty()) {
            try {
                LocalDate termino = LocalDate.parse(terminoStr, formatoData);
                contrato.setTermino(termino);
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida! Não foi alterada.");
            }
        }
        
        // Validar consistência das datas
        if (!contrato.validarDatas()) {
            System.out.println("Atenção: As datas não estão consistentes! Verifique:");
            System.out.println("- Data de celebração não pode ser posterior à data de término");
            System.out.println("- Data de início não pode ser posterior à data de término");
            System.out.println("- Data de término não pode ser anterior às outras datas");
        }
        
        System.out.print("Valor (" + contrato.getValorFormatado() + "): ");
        String valorStr = scanner.nextLine();
        if (!valorStr.isEmpty()) {
            try {
                double valor = Double.parseDouble(valorStr);
                if (valor > 0) {
                    contrato.setValor(valor);
                } else {
                    System.out.println("Valor inválido! Não foi alterado.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido! Não foi alterado.");
            }
        }
        
        salvarDados();
        System.out.println("Contrato atualizado com sucesso!");
    }
    
    private static void excluirContrato() {
        System.out.println("\n--- EXCLUIR CONTRATO ---");
        
        int id = lerInteiro("Digite o ID do contrato que deseja excluir: ");
        contrato contrato = contratos.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
        
        if (contrato == null) {
            System.out.println("Contrato com ID " + id + " não encontrado.");
            return;
        }
        
        System.out.println("Tem certeza que deseja excluir o contrato abaixo? (S/N)");
        System.out.println(contrato);
        String confirmacao = scanner.nextLine();
        
        if (confirmacao.equalsIgnoreCase("S")) {
            contratos.remove(contrato);
            salvarDados();
            System.out.println("Contrato excluído com sucesso!");
        } else {
            System.out.println("Operação cancelada.");
        }
    }
    
    // Persistência em arquivo
    private static void carregarDados() {
        File arquivo = new File(ARQUIVO_DADOS);
        if (!arquivo.exists()) {
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_DADOS))) {
            String linha;
            contratos.clear();
            int maxId = 0;
            
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split("\\|");
                if (dados.length >= 9) {
                    int id = Integer.parseInt(dados[0].trim());
                    String contratante = dados[1].trim();
                    String contratado = dados[2].trim();
                    String cpf = dados[3].trim();
                    String numeroContrato = dados[4].trim();
                    
                    // Remover símbolos de moeda e converter valor
                    String valorStr = dados[5].trim().replaceAll("[^\\d,]", "").replace(",", ".");
                    double valor = Double.parseDouble(valorStr);
                    
                    LocalDate celebracao = LocalDate.parse(dados[6].trim(), formatoData);
                    LocalDate inicio = LocalDate.parse(dados[7].trim(), formatoData);
                    LocalDate termino = LocalDate.parse(dados[8].trim(), formatoData);
                    
                    String descricao = dados.length > 9 ? dados[9].trim() : "";
                    
                    contratos.add(new contrato(
                        id, contratante, contratado, numeroContrato, 
                        descricao, valor, celebracao, inicio, termino, cpf
                    ));
                    
                    if (id > maxId) {
                        maxId = id;
                    }
                }
            }
            
            proximoId = maxId + 1;
        } catch (IOException | NumberFormatException | DateTimeParseException e) {
            System.out.println("Erro ao carregar dados: " + e.getMessage());
        }
    }
    
    private static void salvarDados() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_DADOS))) {
            for (contrato contrato : contratos) {
                writer.write(contrato.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }
}