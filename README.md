
---

## Descrição

Aplicação desenvolvida em **Spring Boot** para gerenciar solicitações de seguro, com validação antifraude, persistência em MongoDB e mensageria assíncrona via RabbitMQ.

---

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.x
- Spring Web, Data MongoDB, AMQP
- RabbitMQ
- MongoDB
- Feign Client
- MapStruct + Lombok
- Swagger / OpenAPI (springdoc)
- Docker & Docker Compose
- JUnit 5 + Mockito + MockMvc
- Spring Boot Actuator
- WireMock (mock de APIs externas)

---

## Como Executar Localmente

### 1. Subir os containers
📄 [docker-compose.yml](./docker-compose.yml)
```bash
docker-compose up -d
```

Os serviços sobem nas portas:
- MongoDB: `localhost:27017`
- RabbitMQ: `localhost:5672` (`http://localhost:15672` para painel)
- WireMock: `http://localhost:8081`

### 2. Rodar a aplicação

```bash
./mvnw spring-boot:run
```

ou executar a classe [InsuranceApplication.java](src/main/java/com/insurance/InsuranceApplication.java)



---

## Rodar os Testes

```bash
./mvnw test
```

- Testes unitários: Cobrem regras de negócio (serviços, validadores, factory)
- Testes de integração: Validam controladores, banco de dados mongodb e consumidores RabbitMQ
- Utiliza `spring-rabbit-test` e `MockMvc`

---

## Documentação da API

Acesse via Swagger:

```
http://localhost:8080/swagger-ui.html
```

---

## Observabilidade

Actuator habilitado para os endpoints:

```
http://localhost:8080/actuator/health
http://localhost:8080/actuator/info
http://localhost:8080/actuator/metrics
```

---

## Arquitetura

Padrão baseado em **Clean Architecture**, com separação de camadas:

```
com.insurance
├── domain              # Entidades, enums, lógica de negócio, validadores
├── application         # Implemetações de entrada da aplicação
│   ├── controller          # Endpoints REST
│   ├── consumer            # Consumidores Mensageria
│   ├── dto                 # Classes de request e response
│   ├── mapper              # Mappers para Utilização do MapStruct
│   └── exceptions          # Handlers globais e exceções customizadas
├── infrastructure      # MongoDB, RabbitMQ, Feign Clients
└── config              # RabbitMQ, Swagger
```

---

## Decisões de Projeto

- **RabbitMQ (Topic Exchange)**: para comunicação desacoplada entre serviços
- **MongoDB**: flexível para armazenar estruturas de seguro
- **Feign Client**: abstração para comunicação com API externa de fraude
- **MapStruct**: mapeamento limpo entre entidade e DTO
- **Swagger + Actuator**: documentação e observabilidade

---

## Estrutura esperada

```
.
├── docker-compose.yml
├── wiremock/
│   ├── mappings/
│   └── __files/
└── src/main/java/com/insurance/...
```

## Observações

Mocks criados para facilitar a comunicação com o serviço de fraude;

- Id de cliente de alto risco: adc56d77-348c-4bf0-908f-22d402ee715c;
- Id de cliente Sem Informação: 692bfe41-e92a-4e04-ba26-ee77669d2c7b;
- Id de cliente Preferencial: 1a23921b-9514-470a-925c-518846414c31;
- ID de cliente Regular: 57dd33af-dff9-4025-bf43-6e7f0c436a7a;

Em alguns pontos eu decidi add algumas coisas que não estavam definidas na documentação

- Adicionei 2 Status a ordem para facilitar o processamento da notificação do pagamento e subscrição
- Não estava definido os status dos serviços de subscription e payment, então eu criei uns status que fazem sentido no contexto nos enums [SubscriptionStatusEnum.java](src/main/java/com/insurance/domain/enums/SubscriptionStatusEnum.java) e [PaymentProcessStatusEnum.java](src/main/java/com/insurance/domain/enums/PaymentProcessStatusEnum.java)
