# Explorando Padrões de Projeto com Java e Spring

Projeto desenvolvido como parte do laboratório de Design Patterns da Digital Innovation One (DIO). A aplicação demonstra como padrões de projeto podem ser utilizados em uma API REST para cadastro e gerenciamento de clientes.

## Padrões utilizados

* **Singleton:** os componentes gerenciados pelo Spring possuem uma única instância no contexto da aplicação.
* **Strategy/Repository:** a interface `ClienteService` define as operações, enquanto `ClienteServiceImpl` implementa as regras do serviço.
* **Facade:** o serviço centraliza o acesso aos repositórios e a integração com a API ViaCEP, oferecendo uma interface simplificada para o cadastro de clientes.

## Funcionalidades

* Cadastrar clientes;
* Buscar todos os clientes;
* Buscar cliente por ID;
* Atualizar cliente;
* Excluir cliente;
* Consultar endereço automaticamente pelo CEP;
* Validar nome e CEP antes de cadastrar ou atualizar.

## Minha contribuição

Como evolução do projeto-base, implementei:

* Validação de nome obrigatório;
* Validação da existência do endereço;
* Validação de CEP com exatamente oito números;
* Exceção personalizada `ClienteInvalidoException`;
* Retorno HTTP `400 Bad Request` com mensagem explicativa;
* Correção da atualização para preservar o ID do cliente;
* Documentação dos testes realizados.

## Tecnologias

* Java;
* Spring Boot 2.5.4;
* Spring Web;
* Spring Data JPA;
* Spring Cloud OpenFeign;
* H2 Database;
* ViaCEP;
* Springdoc OpenAPI/Swagger;
* Maven.

O projeto está configurado para Java 11 e também foi executado e testado localmente com JDK 21.

## Como executar

Clone o repositório:

```bash
git clone https://github.com/devdanielcorrea/lab-padroes-projeto-spring.git
```

Entre na pasta:

```bash
cd lab-padroes-projeto-spring
```

Execute com o Maven Wrapper:

```bash
./mvnw spring-boot:run
```

No Windows também é possível utilizar:

```powershell
mvnw.cmd spring-boot:run
```

## Documentação da API

Com a aplicação em execução, acesse:

```text
http://localhost:8080/swagger-ui.html
```

## Exemplo de cadastro válido

Requisição para `POST /clientes`:

```json
{
  "nome": "Daniel",
  "endereco": {
    "cep": "38401410"
  }
}
```

## Exemplos de validação

### Nome vazio

```json
{
  "nome": "",
  "endereco": {
    "cep": "38401410"
  }
}
```

Resposta esperada:

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "O nome do cliente é obrigatório.",
  "path": "/clientes"
}
```

### CEP inválido

```json
{
  "nome": "Daniel",
  "endereco": {
    "cep": "123"
  }
}
```

Mensagem esperada:

```text
O CEP deve conter exatamente 8 números.
```

## Créditos

Projeto-base desenvolvido por [falvojr](https://github.com/falvojr) para o laboratório de Design Patterns da Digital Innovation One.

Repositório original:

https://github.com/digitalinnovationone/lab-padroes-projeto-spring

Evolução, validações, testes e documentação realizados por [Daniel Corrêa](https://github.com/devdanielcorrea).
