# Trabalho Final A3 – Registro de Dados Contratuais

**Disciplina:** Programação de Soluções Computacionais  
**Professor:** Alexandre de Oliveira (Montanha)  
**Grupo:**   
Bernardo de Paula Dias RA: 125111385813
Pedro Henrique Pedrotti Kendziescki RA: 125111403524
Bernardo Pereira Laia Mendes RA: 125111409567

---

## Descrição do Projeto

O projeto é referente a um controle de dados de um contrato.Ele permite realizar operações de CRUD (Criar, Listar, Buscar, Editar e Remover registros), com validações e persistência dos dados em arquivo `.txt`.

---

##  Funcionalidades

A aplicação permite:
- ✅ Cadastrar um novo contrato
- ✅ Listar todos os contratos cadastrados
- ✅ Buscar um contrato pelo ID de registro
- ✅ Editar qualquer campo de um contrato
- ✅ Remover um contrato
- ✅ Salvar tudo automaticamente em arquivo

 private int id;
    private String ;
    private String ;
    private String ;
    private String ;
    private double ;
    private LocalDate ;
    private LocalDate ;
    private LocalDate ;
    private String ;

### Atributos:
- `contratante`: String
- `contratado`: String
- `numeroContrato`: String
- `descricao`: String
- `celebracao`: String
- `inicio`: String
- `termino`: String
- `cpf`: String
- `valor`: double

---

##  Validações Implementadas

- O CPF precisa ser valido, conforme regras de validação.
- As datas precisam ser coerente em questão de tempo, ou seja, um contrato não pode ter inicio um dia depois da data de vencimento.

---

##  Persistência

- Os dados são armazenados no arquivo `contrato.txt`
- Os dados são carregados na inicialização e salvos automaticamente após qualquer modificação

---


#
