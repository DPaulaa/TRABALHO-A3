# Classe `contrato` - Diagrama UML

## 📌 Descrição

A classe `contrato` representa um contrato com informações básicas como contratante, contratado, numero do contrato, descrição, valor, cpf, data de celebração, inicio e termino de contrato.


## 🧩 Atributos

| Nome             | Tipo      | Visibilidade | Descrição                          |
|------------------|-----------|--------------|------------------------------------|
| id               | int       | privada (-)  | Numero de identificação            |
| contratante      | String    | privada (-)  | Nome do contratante                |
| contratado       | String    | privada (-)  | Nome do contratado                 |
| numeroContrato   | String    | privada (-)  | Numero do contrato                 |
| descricao        | String    | privada (-)  | Descrição do serviço               |
| valor            | Double    | privada (-)  | Valor do contrato                  |
| celebracao       | LocalDate | privada (-)  | Data de celebração do contrato     |
| inicio           | LocalDate | privada (-)  | Data de inicio do contrato         |
| termino          | LocalDate | privada (-)  | Data de termino do contrato        |
| cpf              | String    | privada (-)  | Registro de CPF                    |

---

## 🏗️ Construtor

```java
public contrato(int id, String contratante, String contratado, String numeroContrato, String descricao, double valor, LocalDate celebracao, LocalDate inicio, LocalDate termino, String cpf)

