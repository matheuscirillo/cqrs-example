# CQRS + Event Sourcing

***Tecnologias***
- Java 8
- Apache Kafka
- Elasticsearch
- Redis

***Frameworks***
- Spring Boot - versão 2.3.4 RELEASE
- Spring MVC*
- Spring Security*
- Spring Data (JPA, Elasticsearch, Redis)*
- Spring Kafka*

**Versão gerenciada pelo parent project do Spring Boot*

## **Arquitetura**
![Arquitetura](https://i.imgur.com/tYJcX5b.png)

A UI interage com ambos os lados da aplicação - write e read. O lado que chamamos de 'write' recebe todas as interações com a intenção de escrita (create, update e delete), enquanto, o lado que chamamos de 'read' recebe as interações com inteção de leitura.

### **Authorization Server (não exibido no desenho)**
É a aplicação que efetua a criação do usuário, autenticação (emissão do token) e logout (revogação do token). Essa aplicação não segue padrões CQRS nem event-sourcing e não propaga evento algum. Ela armazena o estado da conta do usuário em uma base SQL.

### **Write side**
O ciclo de vida de uma requisição ao write-side é descrito abaixo:
1. Comando enviado pelo front
2. Filtro de segurança intercepta a requisição e verifica se o token recebido no HTTP header 'Authorization' é válido, fazendo uma requisição ao Redis (isso garante que o usuário ainda está logado)
3. Após a validação, o filtro delega a requisição para o Controller correto.
4. O Controller recebe o Comando e invoca o Command Handler correto
5. O Command Handler invoca o repositório para obter o agregado alvo da operação de escrita em questão
6. O repositório efetua uma busca no Elasticsearch (nossa stream de eventos)
7. A query retorna os eventos do agregado alvo
8. O repositório constrói o 'Domain-state' a partir dos eventos retornados da stream e retorna o agregado já construído ao Command Handler
9. O Command Handler envia comandos para o agregado em questão
10. Após a operação, o Command Handler deve propagar as alterações para a stream de eventos. Para isso, invoca o repositório, que 'appenda' as alterações à stream de eventos
11. Após as alterações estarem salvas na stream, é necessário sincronizar o 'read-side' da aplicação. Para isso, o Command Handler invoca o Event Publisher, que publica a mensagem no Apache Kafka (nosso event bus)
12. Ciclo de vida da requisição é encerrado devolvendo uma resposta (sucesso ou falha) à UI

### **Read side**
O ciclo de vida de uma requisição ao read-side é descrito abaixo:
1. Query enviada pelo front
2. Filtro de segurança intercepta a requisição e verifica se o token recebido no HTTP header 'Authorization' é válido, fazendo uma requisição ao Redis (isso garante que o usuário ainda está logado)
3. Após a validação, o filtro delega a requisição para o Controller correto.
4. O Controller recebe a Query e invoca o Query Handler correto
5. O Query Handler invoca invoca o repositório
6. O repositório efetua uma query no Elasticsearch (nossa read-optimized view)
7. A query retorna o objeto resultante da busca
8. Ciclo de vida da requisição é encerrado devolvendo uma resposta (objeto solicitado) à UI

### **Sync / Projection***
O passo-a-passo de como é feita a sincronização / projeção entre o write e read repositories é descrito abaixo
1. Existe um listener de eventos implementado no read-side da aplicação 
2. Após a publicação do evento no Kafka por parte do Command Handler *(passo 11 write-side)*, o event listener recebe os eventos.
3. Após receber os eventos, o Event Listener identifica o tipo de evento e delega para o Event Handler correto.
4. O Event Handler atualiza / cria a read-optimized view
5. Sincronização é encerrada

**Projection é o termo que significa que o write-side 'projeta' os eventos no read-side*

## Estrutura do projeto
O projeto é um Maven Project de 3 módulos:
- cqrs-example-authorization-server
- cqrs-example-write
- cqrs-example-read

Todos os projetos utilizam Spring Boot como seu configurador de dependência e também cada projeto é uma aplicação separada, o que significa que cada aplicação é deployada individualmente. Na configuação atual, o projeto é inicializado em um Embedded Tomcat.

## Iniciando o ecossistema
O projeto possui dependências de infra-estrutura para funcionar corretamente. Essas dependências são:
- Apache Kafka
- Redis
- Elasticsearch

É necessário subir cada um dos serviços acima, afim de que os módulos do projeto inicializem corretamente.

### **Apache Kafka**
Passo-a-passo de como iniciar o Apache Kafka:

#### **Passo 1**
Download [**aqui**](https://kafka.apache.org/downloads)

#### **Passo 2**
Após baixar, extrair o pacote e acessar `/kafka_2.13-2.7.0/bin` a pasta:

```
$ tar -xzf kafka_2.13-2.7.0.tgz
$ cd kafka_2.13-2.7.0/bin
```

#### **Passo 3**
Inicializar primeiro o Zookeeper:
```
$ ./zookeeper-server-start.sh config/zookeeper.properties
```

#### **Passo 4 (ir direto para o Passo 5 se for subir o Kafka na mesma máquina em que você irá rodar os módulos)**
Se for inicializar o Kafka em uma máquina da diferente de onde você irá rodar os módulos do projeto, você deverá configurar o Kafka para que o broker aceite conexões remotas, pois inicialmente o Kafka liga o listener na interface de loopback (localhost ou 127.0.0.1)

Para configurar o Kafka para que ele aceite conexões remotas, altere o arquivo `config/server.properties`

```
$ vi config/server.properties
```

Alterar o trecho

```
listeners=PLAINTEXT://:9092
```

Para (exemplo)

```
listeners=PLAINTEXT://192.168.0.12:9092 
```

#### **Passo 5**
Inicializar o Kafka:
```
$ ./kafka-server-start.sh config/server.properties
```

### **Elasticsearch**
Passo-a-passo de como iniciar o Elasticsearch

#### **Passo 1**
Download [**aqui**](https://www.elastic.co/pt/downloads/elasticsearch)

#### **Passo 2**
Extrair o arquivo:

```
$ tar -xvf elasticsearch-7.11.1-linux-x86_64.tar.gz
```

#### **Passo 3 (pular direto para o passo 4 se for subir o Elasticsearch na mesma máquina em que você irá rodar os módulos)**
Se for inicializar o Elasticsearch em uma máquina da diferente de onde você irá rodar os módulos do projeto, você deverá configurar o Elasticsearch para que o broker aceite conexões remotas, pois inicialmente o Elasticsearch liga o listener na interface de loopback (localhost ou 127.0.0.1)

Para configurar o Kafka para que ele aceite conexões remotas, altere o arquivo `config/elasticsearch.yml`
```
$ vi config/elasticsearch.yml
```

Alterar a propriedade network.host para (exemplo):
```
network.host: 192.168.0.12
```

#### **Passo 4**
Acessar a pasta `/bin` e inicializar o Elasticsearch

```
$ cd elasticsearch-7.11.1/bin
$ ./elasticsearch
```

### **Redis**
Passo-a-passo de como iniciar o Redis

#### **Passo 1**
Download [**aqui**](https://redis.io/download)

#### **Passo 2**
Extrair o arquivo e compilar o redis:

```
$ tar xvzf redis-stable.tar.gz
$ cd redis-stable
$ make
```

#### **Passo 3 (pular direto para o passo 4 se for subir o Redis na mesma máquina em que você irá rodar os módulos)**
Se for inicializar o Redis em uma máquina da diferente de onde você irá rodar os módulos do projeto, você deverá configurar o Redis para que o broker aceite conexões remotas, pois inicialmente o Redis liga o listener na interface de loopback (localhost ou 127.0.0.1)

Para configurar o Redis para que ele aceite conexões remotas e para que ele desative o protected mode, altere o arquivo `redis.conf`
```
$ vi redis.conf
```

Alterar a propriedade `bind` para (exemplo):
```
bind 192.168.0.12
```

Alterar a propriedade `protected-mode` para:
```
protected-mode no
```

#### **Passo 4**
Acessar a pasta `/src` e inicializar o Redis

```
$ cd src
$ ./redis-server ../redis.conf
```

### **Inicializando os módulos do projeto**

#### **Passo 1**

Clonar o projeto e buildar com o Maven:
```
$ git clone https://github.com/matheuscirillo/cqrs-example.git
$ cd cqrs-example
$ mvn clean install
```

#### **Passo 2**

Iniciar os projetos:

```
$ java -jar cqrs-example-authorization-server/target/cqrs-example-authorization-server-0.0.1-SNAPSHOT.jar
$ java -jar cqrs-example-read/target/cqrs-example-read-0.0.1-SNAPSHOT.jar
$ java -jar cqrs-example-write/target/cqrs-example-write-0.0.1-SNAPSHOT.jar
```

**Alterar o host do kafka, redis ou elasticsearch, e a porta de cada um dos módulos**:

Os módulos tentam se conectar ao Kafka via `localhost:9092`, ao Elasticsearch via `localhost:9200` e ao Redis via `localhost:6379`.

As portas em que os módulos sobem são:
- cqrs-example-authorization-server: **8080**
- cqrs-example-read: **8081**
- cqrs-example-write: **8082**


É possível alterar essas configurações ao inicializar os módulos via linha de comando. Por exemplo:

```
$ java -jar cqrs-example-authorization-server/target/cqrs-example-authorization-server-0.0.1-SNAPSHOT.jar --spring.redis.host=192.168.0.12 --server.port=9001
$ java -jar cqrs-example-read/target/cqrs-example-read-0.0.1-SNAPSHOT.jar --spring.redis.host=192.168.0.12 --spring.kafka.bootstrap-servers=192.168.0.12:9092 --server.port=9002
$ java -jar cqrs-example-write/target/cqrs-example-write-0.0.1-SNAPSHOT.jar --spring.redis.host=192.168.0.12 --spring.kafka.bootstrap-servers=192.168.0.12:9092 --server.port=9003
```

## Interagindo com a aplicação

### Authorization Server

**Criação de conta de usuário**

`POST - /registration`

Request body
```json
{
    "username": "matheuscirillo",
    "password": "123"
}
```
Response body
```
HTTP 201 Created
No body
```

**Autenticação**

`POST - /authentication`

Request body
```json
{
    "username": "matheuscirillo",
    "password": "123"
}
```
Response body
```json
{
    "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNjE0OTczMTcxfQ.VXT-EeEwO8doltFF7INMF4AP84agj6dbP1koeCTkLF1Arve4ZzvrTGopUNtnpjf0cGguUzEGYhr0zzgQ9iP5Vg",
    "expiresIn": 1614973171197
}
```

### Write Model

**Criação de conta bancária**

`POST - /bank-accounts`

Request headers
```
Authorization: Bearer <token obtido no Authorization Server>
```

Request body
```json
{
    "type": "<PF ou PJ>"
}
```
Response body
```
HTTP 201 OK
No body
```

Response headers
```
Location: http://localhost:8081/bank-accounts/{id}
```

**Criação de transação**

`POST - /bank-accounts/{id}/transactions`

Request body
```json
{
    "type": "<Withdraw ou Deposit>",
    "amount": 50
}
```
Response body
```
HTTP 200 OK
No body
```

### Read Model

**Consultar conta bancária**

`GET - /bank-accounts/{id}`

Request headers
```
Authorization: Bearer <token obtido no Authorization Server>
```

Response body

```json
{
    "id": 1,
    "balance": 140.0,
    "transactions": [
        {
            "id": "42f52474-a49b-4707-86e4-e983efb4ab31",
            "type": "Deposit",
            "amount": 100.0
        },
        {
            "id": "3f8396a3-d747-4a4c-8926-cdcedea6b5c3",
            "type": "Deposit",
            "amount": 50.0
        },
        {
            "id": "5693585d-6356-4d1a-8d7b-cac5d0dab39f",
            "type": "Withdraw",
            "amount": 10.0
        }
    ],
    "accountCreatedAt": 1614029062764
}
```

**Consultar transações de uma conta**

`POST - /bank-accounts/{id}/transactions`

Request headers
```
Authorization: Bearer <token obtido no Authorization Server>
```

Response body
```json
[
    {
        "id": "42f52474-a49b-4707-86e4-e983efb4ab31",
        "type": "Deposit",
        "amount": 100.0
    },
    {
        "id": "3f8396a3-d747-4a4c-8926-cdcedea6b5c3",
        "type": "Deposit",
        "amount": 50.0
    },
    {
        "id": "5693585d-6356-4d1a-8d7b-cac5d0dab39f",
        "type": "Withdraw",
        "amount": 10.0
    }
]
```
