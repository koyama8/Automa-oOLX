# OLX Scraper & MySQL Inserter 
[![Java](https://img.shields.io/badge/Java-1.8%2B-blue.svg)](https://www.oracle.com/br/java/technologies/javase-downloads.html)
[![Selenium](https://img.shields.io/badge/Selenium-3.141.59-brightgreen.svg)](https://www.selenium.dev/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-orange.svg)](https://www.mysql.com/)

> Projeto Java para extrair dados de anúncios da OLX e inserir diretamente em um banco de dados MySQL.

## ✨ Visão Geral 
Este repositório contém um **script de automação** que abre a página da OLX (com busca para *Volkswagen Fox*), **extrai informações** dos anúncios (como valor, quilometragem, cor, cidade etc.) e **armazena** tudo em uma tabela **MySQL**.

### **Principais recursos**:
- **Selenium WebDriver** para navegar na OLX e coletar dados.
- **Expressões Regulares** (Regex) para extrair padrões de texto, como `<número>.km`, cores, etc.
- **Conexão JDBC** com MySQL para inserir os dados diretamente no banco de dados.

---

## 🏗️ Estrutura do Projeto
```
.
├── pom.xml                         # Configuração Maven (dependências)
├── src
│   └── test
│       └── java
│           └── automatizadoOLX
│               └── test
│                   └── OLXScraperTest.java  # Arquivo principal do scraper
├── README.md                       # Você está aqui!
└── ...
```
- **`pom.xml`**: arquivo Maven para gerenciamento de dependências (Selenium, MySQL Connector, etc.).
- **`OLXScraperTest.java`**: código de raspagem e inserção no banco MySQL.
- **`chromedriver.exe`** (não listado): executável do ChromeDriver (versão compatível com o seu Chrome).

---

## 🎯 Pré-Requisitos
| Tecnologia      | Versão  | Observações                                       |
|-----------------|---------|---------------------------------------------------|
| **Java**        | 1.8 ou +| Verifique se o `JAVA_HOME` está configurado.      |
| **Maven**       | 3.x     | Para baixar e gerenciar dependências do projeto.  |
| **MySQL**       | 8.0 ou +| Verifique se o serviço está rodando localmente.    |
| **ChromeDriver**| Compatível com sua versão do Chrome | Ajustar caminho em `OLXScraperTest.java`. |

---

## ⚙️ Configurações Iniciais
1. **Clonar este repositório**:
   ```bash
   git clone https://github.com/seu-usuario/olx-scraper-mysql.git
   ```
2. **Importar** no IntelliJ, Eclipse ou VS Code como um projeto **Maven**.
3. **No `pom.xml`**, confirme se as dependências de Selenium e MySQL estão listadas:
   ```xml
   <dependency>
       <groupId>org.seleniumhq.selenium</groupId>
       <artifactId>selenium-chrome-driver</artifactId>
       <version>3.141.59</version>
   </dependency>
   <dependency>
       <groupId>mysql</groupId>
       <artifactId>mysql-connector-java</artifactId>
       <version>8.0.23</version>
   </dependency>
   ```
4. **Executar**:
   ```bash
   mvn clean install
   ```

---

## 🛠️ Ajustando para seu MySQL
Abra o arquivo [`OLXScraperTest.java`](src/test/java/automatizadoOLX/test/OLXScraperTest.java) e altere as variáveis:
```java
private static final String URL = "jdbc:mysql://<URL_DO_BANCO>/<NOME_DO_BANCO>";
private static final String USER = "<USUARIO_DO_MYSQL>";
private static final String PASSWORD = "<SENHA_DO_MYSQL>";
```
Em seguida, certifique-se de que há uma tabela **`anuncios`** no seu banco. Se ainda não existir:
```sql
USE <NOME_DO_BANCO>;

CREATE TABLE anuncios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    valor VARCHAR(50),
    nome_veiculo VARCHAR(255),
    quilometragem VARCHAR(50),
    cor VARCHAR(50),
    aceita_troca VARCHAR(50),
    cidade VARCHAR(100),
    data_insercao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

---

## 🚀 Como Executar
1. **Abra o terminal** na raiz do projeto.
2. Rode:
   ```bash
   mvn test
   ```
3. Se tudo correr bem, o Chrome será aberto, coletará os dados e **inserirá no MySQL**.
4. Confirme no MySQL:
   ```sql
   SELECT * FROM anuncios;
   ```

---

## 📦 Exemplo de Saída
No console, pode aparecer algo como:
```
✅ Dados inseridos no banco com sucesso!
Valor do Carro: R$ 35.900
Nome do Veículo: Fox Comfortline 1.6 ...
...
```
Ao consultar no MySQL:
```sql
SELECT * FROM anuncios;
```
Você verá colunas como **valor, nome_veiculo, quilometragem, cor, aceita_troca, cidade**, etc.

---

## 💡 Possíveis Erros e Soluções
- **`NoSuchElementException`**: O XPath dos anúncios pode ter mudado; revise o código.
- **`java.sql.SQLException`**: Confirme se o MySQL está rodando, se o usuário/senha estão corretos e se a tabela existe.
- **Versão do ChromeDriver**: Caso seja incompatível, baixe outro **ChromeDriver** compatível com seu Google Chrome.

---

## 👩‍💻 Contribuindo
Fique à vontade para abrir **Issues** ou dar **Pull Requests** com melhorias! Algumas ideias:
1. **Refinar logs** (mais detalhes na execução).
2. **Extração de mais campos** (ano, modelo, etc.).
3. **Container Docker** para rodar o Selenium e MySQL de forma unificada.

---

## 📝 Licença
Esse projeto não possui licença formal. Use e modifique conforme sua necessidade.  
Se tiver dúvidas, abra uma **Issue** ou entre em contato.

--- 

Aproveite e, se o projeto foi útil, deixe uma ⭐ no repositório!
