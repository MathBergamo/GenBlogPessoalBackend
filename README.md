# Blog Pessoal
Este README descreve o projeto "Blog Pessoal", desenvolvido durante o curso da Generation de Desenvolvedor Fullstack Java. O projeto utiliza a plataforma Spring Boot e consiste em uma aplicação para criação, leitura, atualização e exclusão de postagens em um blog pessoal. Além disso, o sistema permite o cadastro de temas e usuários, bem como a autenticação de usuários por meio de tokens JWT.

## Sumário

- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Funcionalidades](#funcionalidades)
- [Instruções de Uso](#instruções-de-uso)
- [Endpoints da API](#endpoints-da-api)
- [Configuração](#configuração)

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.0.10
- Spring Data JPA
- Spring Security
- MySQL
- JWT (JSON Web Token)

## Estrutura do Projeto

O projeto está organizado em pacotes de acordo com suas funcionalidades:

- **Controller**: Contém os controladores que definem os endpoints da API.
- **Models**: Contém as classes de modelo que representam as entidades do sistema.
- **Repository**: Contém as interfaces de repositório para acessar o banco de dados.
- **Security**: Contém as classes relacionadas à segurança, como autenticação e autorização.
- **Service**: Contém os serviços que implementam a lógica de negócios.
- **Config**: Contém as configurações do Spring, como segurança e autenticação.

## Funcionalidades

O Blog Pessoal oferece as seguintes funcionalidades:

- Cadastro, leitura, atualização e exclusão de postagens.
- Cadastro, leitura, atualização e exclusão de temas.
- Cadastro, leitura e atualização de usuários.
- Autenticação de usuários por meio de tokens JWT.

## Instruções de Uso

1. Clone o repositório para sua máquina local:


2. Importe o projeto em sua IDE de preferência (por exemplo, Spring Tool Suite ou IntelliJ IDEA).

3. Configure o banco de dados MySQL com as credenciais apropriadas no arquivo `application.properties`.

4. Execute a aplicação Spring Boot.

5. Acesse a API através dos endpoints especificados na próxima seção.

## Endpoints da API

A API oferece os seguintes endpoints:

### Postagens

- **GET** `/postagem`: Retorna todas as postagens.
- **GET** `/postagem/{id}`: Retorna uma postagem específica pelo ID.
- **GET** `/postagem/titulo/{titulo}`: Retorna postagens por título.
- **POST** `/postagem`: Cria uma nova postagem.
- **PUT** `/postagem`: Atualiza uma postagem existente.
- **DELETE** `/postagem/{id}`: Exclui uma postagem pelo ID.

### Temas

- **GET** `/tema`: Retorna todos os temas.
- **GET** `/tema/{id}`: Retorna um tema específico pelo ID.
- **GET** `/tema/descricao/{descricao}`: Retorna temas por descrição.
- **POST** `/tema`: Cria um novo tema.
- **PUT** `/tema`: Atualiza um tema existente.
- **DELETE** `/tema/{id}`: Exclui um tema pelo ID.

### Usuários

- **GET** `/usuarios/all`: Retorna todos os usuários.
- **GET** `/usuarios/{id}`: Retorna um usuário específico pelo ID.
- **POST** `/usuarios/cadastrar`: Cadastra um novo usuário.
- **POST** `/usuarios/logar`: Realiza a autenticação de um usuário e gera um token JWT.
- **PUT** `/usuarios/atualizar`: Atualiza os dados de um usuário existente.

## Configuração

O projeto utiliza o banco de dados MySQL. Certifique-se de configurar as credenciais corretas no arquivo `application.properties` para que a aplicação possa se conectar ao banco de dados.

Além disso, a segurança da API é implementada com o uso de tokens JWT. A classe `JwtService` é responsável por gerar e validar esses tokens. Certifique-se de configurar uma chave secreta adequada no arquivo `JwtService.java` para garantir a segurança dos tokens.
