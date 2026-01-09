# Backend Project Documentation

Esta aplicação é criada usando Spring, sendo dessa forma uma 

-----

## Estrutura do projeto

## Configs

***SecurityConfig***
É a configuração do SecurityFilterChain. Antes de chegar ao controller, sua requisição passa por uma série de filtros. Nessa classe definimos os filtros de Autorização, controlando a segurança da aplicação.

- **csrf().disable():** Devido ao use de JWT no lugar de cookies, podemos desabilitar o CRFS.
- **SessionCreationPolicy.STATELESS:** Isso economiza muita Memória RAM no servidor, pois você não precisa manter um HttpSession aberto para milhares de usuários.
- **requestMatchers(...).permitAll():** É a white list da aplicação.

## Repository

Aqui ficam as interfaces que ligam as classes de dados ao banco de dados.

## Service

***UserDetailsService***
Responsável por converter os dados do banco de dados (UserRepository) para o formato que o motor de segurança do Spring entende.

A interface UserDetailsService foi criada para aplicar o Princípio da Responsabilidade Única (SOLID). O Spring Security não quer saber como ou onde seus usuários estão guardados (se é num banco SQL, num arquivo de texto ou numa API externa); ele só precisa de um objeto que prometa entregar um UserDetails

## Database

Inicialmente, usa-se o H2 Database. Ao rodar a aplicação, ele executa o schema.sql e depois o data.sql, criando uma simulação de base de dados em memória.

# Refference and Docummentation used:

- [H2 Database Config](https://www.baeldung.com/spring-boot-h2-database)