# 🔹 Passos para Armazenar os Dados da OLX Corretamente

Os dados dos anúncios na OLX mudam constantemente, então é essencial criar um fluxo estruturado para capturar, armazenar e gerenciar essas informações de forma eficiente.

## 1️⃣ Definir o Site e os XPaths para Captura

- **Site OLX:** [https://www.olx.com.br/brasil?q=ford](https://www.olx.com.br/brasil?q=ford)
- **Paths completos dos `<section>` que contêm os anúncios:**
  ```
  /html/body/div[1]/div[2]/main/div[3]/div/main/div[7]/section[1]
  /html/body/div[1]/div[2]/main/div[3]/div/main/div[7]/section[2]
  ```
  ⚠️ **Atenção:** Sempre que o site da OLX for atualizado ou recarregado, os anúncios podem mudar de posição. É fundamental verificar se os XPaths continuam válidos ao longo do tempo.

## 2️⃣ Capturar os Dados Dinamicamente com Selenium

Para extrair os dados, utilizamos um Web Scraper em **Java com Selenium**, seguindo os seguintes passos:

- Acessar a página da OLX (observando que os anúncios podem mudar a cada atualização da página).
- Capturar dinamicamente os `<section>` que contêm os anúncios.
- Armazenar os dados coletados em uma estrutura adequada para facilitar consultas e análises futuras.

💡 **Dica:** Como os anúncios variam a cada acesso, é recomendável capturar e armazenar os dados em um banco de dados ou arquivo local para evitar perdas e garantir um histórico das ofertas.
