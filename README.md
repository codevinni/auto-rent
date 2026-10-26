# AutoRent 🚘

AutoRent é um sistema distribuído para gerenciamento e locação de veículos, composto por uma aplicação web voltada aos clientes e uma aplicação desktop voltada ao atendimento de balcão por funcionários. O sistema foi desenvolvido sobre a plataforma Jakarta EE utilizando Enterprise JavaBeans (EJB), persistência com JPA/Hibernate, Jakarta Faces (JSF) e Java Swing, operando sobre o servidor de aplicação WildFly integrado ao PostgreSQL.

## Sobre

O projeto foi desenvolvido para demonstrar a aplicação prática de sistemas corporativos distribuídos em ambiente Java EE, organizando a solução em módulos especializados: lógica de negócio remota desacoplada, interface web dinâmica e cliente desktop para operações administrativas.

A arquitetura utiliza Session Beans (Stateless, Stateful e Singleton) para isolar responsabilidades de negócio, controle conversacional de locações, autenticação de sessão e cache de métricas em memória.

## Módulos do Sistema

O projeto é dividido em cinco módulos:

- **AutoRent**: Módulo EJB responsável pela regra de negócio e persistência. Contém os Session Beans Stateless (operações CRUD e consultas especializadas), Stateful (processo de locação e autenticação) e Singleton (cache de estatísticas da frota).
- **AutoRentClient**: Biblioteca compartilhada que define o modelo de domínio (entidades JPA), interfaces remotas dos EJBs, enumerações e utilitários. É utilizada por todos os outros módulos.
- **AutoRentEAR**: Módulo corporativo (Enterprise Archive) responsável por empacotar o componente EJB (`AutoRent.jar`), a biblioteca de cliente (`AutoRentClient.jar`) e a aplicação web (`AutoRentWeb`) para implantação unificada no WildFly.
- **AutoRentWeb**: Aplicação web desenvolvida com Jakarta Faces (JSF) e CDI. Disponibiliza aos clientes o catálogo de veículos com filtros, fluxo de reserva/locação com seleção de acessórios e painel de histórico de locações.
- **AutoRentDesktop**: Aplicação desktop desenvolvida em Java Swing para funcionários no atendimento presencial de balcão. Conecta-se diretamente aos EJBs remotos via JNDI para gerenciar retiradas, devoluções, frota e clientes.

## Funcionalidades

### Aplicação Web (Clientes)

- Consulta ao catálogo de veículos com filtros por categoria, status e agência.
- Cadastro dos clientes com validação de dados e criptografia.
- Autenticação e controle de sessão.
- Fluxo de reserva e locação de veículos com cálculo de diárias e inclusão de acessórios.
- Dashboard do cliente com resumo da frota, locação em andamento e reservas ativas.
- Consulta ao histórico de locações realizadas.

### Aplicação Desktop (Funcionários)

- Autenticação restrita a usuários com perfil de funcionário.
- Consulta e cadastro de clientes.
- Consulta de inventário de veículos por agência.
- Alteração manual de status de veículos.
- Criação e confirmação de locações e reservas.
- Registro de retirada de veículo a partir de reserva existente.
- Registro de devolução com atualização de agência e liberação do veículo para a frota.
- Listagem de locações em andamento.

## Tecnologias Utilizadas

- **Java 17+**
- **Jakarta EE 10**
- **Enterprise JavaBeans (EJB)**: Stateless, Stateful e Singleton
- **JPA 3.2 / Hibernate 7**: Mapeamento objeto-relacional
- **Jakarta Faces 4 (JSF) e CDI**: Interface web
- **Java Swing**: Interface desktop
- **WildFly 27+**: Servidor de aplicação corporativo
- **PostgreSQL**: Sistema gerenciador de banco de dados
- **Eclipse IDE for Enterprise Java and Web Developers (2026-09)**: Ambiente de desenvolvimento

## Estrutura do Projeto

```
auto-rent/
├── AutoRent/                    # Módulo EJB (regras de negócio)
│   └── ejbModule/
│       ├── br/autorent/sessionbeans/
│       │   ├── singletons/     
│       │   ├── stateful/        
│       │   └── stateless/       
│       └── META-INF/            # persistence.xml
├── AutoRentClient/              # Interfaces remotas, entidades JPA e utilitários
│   └── src/main/java/br/autorent/
│       ├── enums/               
│       ├── model/              
│       ├── sessionbeans/       
│       └── utils/              
├── AutoRentDesktop/             # Aplicação desktop Swing (balcão)
│   ├── jar/                     # Dependências locais
│   └── src/br/autorent/
│       ├── gui/                 # Telas
│       └── services/            # Lookup JNDI dos EJBs remotos
├── AutoRentEAR/                 # Empacotador EAR
│   └── EarContent/              
├── AutoRentWeb/                 # Aplicação web Jakarta Faces (JSF)
│   ├── jar/                     # Dependências locais
│   └── src/main/
│       ├── java/br/autorent/managedbeans/
│       └── webapp/              # Páginas XHTML, CSS e assets
├── docs/                        # Documentação e relatórios
└── seed.sql                     # Script de população inicial do banco
```

## Configuração e Execução

### Requisitos

- Eclipse IDE for Enterprise Java and Web Developers (2026-09)
- WildFly 27 ou superior instalado
- PostgreSQL 14 ou superior em execução
- Plugin JBoss Tools / WildFly Server Adapter instalado no Eclipse

Todas as dependências `.jar` necessárias já estão inclusas nos respectivos diretórios `jar/` dos projetos, não sendo necessário gerenciador de dependências externo.

---

### 1. Configuração do Banco de Dados

1. No PostgreSQL, crie o banco de dados da aplicação:
   ```sql
   CREATE DATABASE autorent;
   ```

2. No WildFly, configure o driver JDBC do PostgreSQL e crie um DataSource com o nome JNDI `PostgresDS` (ou `java:/PostgresDS`) apontando para o banco `autorent`.

   Exemplo de configuração no arquivo `standalone/configuration/standalone.xml` do WildFly (dentro do subsistema `datasources`):
   ```xml
   <datasource jndi-name="java:/PostgresDS" pool-name="PostgresDS" enabled="true" use-java-context="true">
       <connection-url>jdbc:postgresql://localhost:5432/autorent</connection-url>
       <driver>postgresql</driver>
       <security>
           <user-name>postgres</user-name>
           <password>postgres</password>
       </security>
   </datasource>
   ```

---

### 2. Importação dos Projetos no Eclipse

1. Abra o Eclipse IDE for Enterprise Java and Web Developers (2026-09).
2. Vá em **File → Import → General → Existing Projects into Workspace**.
3. No campo **Select root directory**, selecione a pasta raiz `auto-rent`.
4. Certifique-se de marcar todos os 5 projetos listados:
   - `AutoRent`
   - `AutoRentClient`
   - `AutoRentDesktop`
   - `AutoRentEAR`
   - `AutoRentWeb`
5. Clique em **Finish**.

---

### 3. Execução da Aplicação Web (Deploy do EAR no WildFly)

1. Na aba **Servers** do Eclipse (caso não esteja visível, abra em **Window → Show View → Other... → Server → Servers**):
   - Se ainda não tiver o WildFly configurado, clique com o botão direito na área em branco da aba → **New → Server** → selecione **WildFly 27+**, aponte o diretório de instalação do servidor e conclua.
2. Clique com o botão direito no servidor WildFly configurado → **Add and Remove...**.
3. Selecione o projeto **AutoRentEAR** na coluna da esquerda e clique em **Add** para movê-lo para a coluna da direita.
4. Clique em **Finish**.
5. Inicie o servidor clicando no ícone verde de **Start** ou com o botão direito → **Start**.
6. Durante a inicialização, o Hibernate criará ou atualizará as tabelas e sequences automaticamente com base no `persistence.xml`.
7. Acesse a aplicação web pelo navegador:
   ```
   http://localhost:8080/AutoRentWeb
   ```

---

### 4. População Inicial dos Dados

Após a inicialização do WildFly (momento em que as tabelas e sequences já foram geradas no banco pelo Hibernate), execute o script de população `seed.sql` para cadastrar as agências, veículos, acessórios e usuários de teste:

```bash
psql -U postgres -d autorent -f seed.sql
```

Alternativamente, o conteúdo do arquivo `seed.sql` pode ser executado diretamente em qualquer cliente gráfico como pgAdmin ou DBeaver.

---

### 5. Execução da Aplicação Desktop (Balcão)

Com o servidor WildFly em execução:

1. No Eclipse, expanda o projeto **AutoRentDesktop**.
2. Navegue até o pacote `br.autorent.gui` dentro da pasta `src`.
3. Clique com o botão direito na classe **Main.java** → **Run As → Java Application**.
4. A janela de atendimento do balcão será exibida solicitando CPF e senha do funcionário.

---

## Credenciais para Testes

Os seguintes usuários de teste são cadastrados através do script `seed.sql`:

| Nome | CPF | Senha | Perfil | Acesso Permitido |
| :--- | :--- | :--- | :--- | :--- |
| Carlos Silva | 111.222.333-44 | senha123 | CUSTOMER | Web |
| Maria Oliveira | 222.333.444-55 | maria456 | CUSTOMER | Web |
| João Santos | 333.444.555-66 | joao789 | CUSTOMER | Web |
| Ana Costa | 444.555.666-77 | ana2024 | CUSTOMER | Web |
| Admin AutoRent | 999.888.777-66 | admin123 | EMPLOYEE | Desktop e Web |

*Nota: O acesso à aplicação desktop é restrito exclusivamente a usuários com papel `EMPLOYEE`.*