
H2 Database (Fase Inicial): É um banco de dados em memória. Ele não requer instalação; você apenas adiciona a dependência no pom.xml.

Comportamento na Memória: O H2 armazena as tabelas e índices diretamente na JVM Heap. Como os dados residem em estruturas de dados como B-Trees em memória, o acesso é extremamente veloz, mas tudo é perdido quando o servidor reinicia. É perfeito para testar o login sem se preocupar com infraestrutura.

PostgreSQL (Fase de Portfólio): Quando o sistema estiver estável, mude para o Postgres via Docker. Ele é o padrão da indústria para sistemas relacionais robustos, suportando complexidade de transações ACID que um marketplace exige.


config/ -> Aqui é onde você coloca o SecurityConfig.java

controller/ -> Classes @RestController (Ex: AuthController.java)

model/ -> Entidades JPA (Ex: User.java)

repository/ -> Interfaces de acesso ao banco (Spring Data JPA)

service/ -> Lógica de negócio e regras de validação



# Login

A lógica será:

O Mobile envia login/senha.

O Spring valida no banco de dados.

O Spring gera uma String assinada (JWT) e devolve ao Mobile.

O Mobile guarda esse Token e envia no cabeçalho Authorization em todas as próximas requisições (como ver produtos ou adicionar ao carrinho).

**Quando o seu app React Native enviar uma tentativa de login, veja o que acontece na memória:**

Injeção de Dependência: O Spring, ao subir, já instanciou o seu UserRepository (o Proxy) e o injetou no CustomUserDetailsService. Ambos residem na Heap.

Busca e Alocação: Ao chamar loadUserByUsername:

O Hibernate gera o SQL.

Os dados brutos retornados pelo banco (via JDBC) são transformados em uma instância da sua classe User.

Estrutura de Dados: Esse objeto User é colocado temporariamente no Persistence Context (L1 Cache) do Hibernate, que é um Map interno para evitar buscas duplicadas na mesma transação.

Propagation: O objeto User é passado para o AuthenticationProvider. Na memória, o Spring mantém uma referência a este objeto durante o processo de comparação de senhas. Se a senha bater, o objeto é encapsulado em um Authentication e colocado no SecurityContextHolder (naquele "armário" ThreadLocal que mencionamos antes)