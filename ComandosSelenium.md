# **Comandos de Automação Selenium**

Este documento contém uma coleção de comandos úteis para automação de testes com Selenium. Ele está organizado com exemplos práticos e explicações detalhadas para fácil consulta e aprendizado.

---

## **📚 Recursos Utilizados**
- **Linguagem:** Java
- **Biblioteca de Automação:** Selenium WebDriver
- **Bibliotecas Adicionais:**
  - `org.openqa.selenium.*`: Para interações com o navegador e elementos.
  - `org.openqa.selenium.chrome.ChromeDriver`: Para o driver do Chrome.
  - `org.openqa.selenium.support.ui.Select`: Para manipular dropdowns.
  - `org.openqa.selenium.support.ui.ExpectedConditions`: Para esperas explícitas.
  - `org.openqa.selenium.support.ui.WebDriverWait`: Para controle de espera.
  - `org.apache.commons.io.FileUtils`: Para manipulação de arquivos, como capturar screenshots.
- **Tecnologias:**
  - Google Chrome e ChromeDriver.
  - Estruturas para manipulação de elementos DOM.
  - Execução de scripts JavaScript diretamente no navegador.

---

## **🚀 Configuração Inicial**
Antes de usar os comandos abaixo:

1. Configure o caminho do **ChromeDriver**:

   ```java
   // Define o caminho para o driver do Chrome
   System.setProperty("webdriver.chrome.driver", "CAMINHO_PARA_SEU_CHROMEDRIVER.EXE");


## Configuração inicial do Selenium:

**Define o caminho para o ChromeDriver**
- System.setProperty("webdriver.chrome.driver", "CAMINHO_PARA_SEU_CHROMEDRIVER.EXE");

**Inicializa o driver do Chrome**
- WebDriver driver = new ChromeDriver();

**Maximiza a janela do navegador**
-  Maximiza a janela do navegador

**Abre uma página específica**
- driver.get("https://exemplo.com");

## 🛠️ Localizando Elementos


**Define o caminho para o ChromeDriver**

# Exemplos de localização de elementos no Selenium:

// 1. Pelo atributo "id"
WebElement elementoPorId = driver.findElement(By.id("idDoElemento"));

// 2. Pelo atributo "name"
WebElement elementoPorNome = driver.findElement(By.name("nomeDoElemento"));

// 3. Pela classe CSS
WebElement elementoPorClasse = driver.findElement(By.className("nomeDaClasse"));

// 4. Usando seletor CSS
WebElement elementoPorCss = driver.findElement(By.cssSelector("seletorCSS"));

// 5. Usando XPath (mais flexível)
WebElement elementoPorXPath = driver.findElement(By.xpath("//tag[@atributo='valor']"));

// 6. Links (texto exato ou parte do texto)
WebElement linkPorTexto = driver.findElement(By.linkText("Texto do Link"));
WebElement linkPorParteTexto = driver.findElement(By.partialLinkText("Parte do Texto"));





