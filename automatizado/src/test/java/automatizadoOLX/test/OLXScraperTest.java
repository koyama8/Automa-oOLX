package automatizadoOLX.test;

// Observação: nesta parte do código, ajuste a URL, usuário e senha do MySQL
// de acordo com sua configuração local

import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;

public class OLXScraperTest {

    private final String CAMINHO_DRIVER = "C:/Users/koyam/OneDrive/Área de Trabalho/AutomatizaçãoOLX/automatizado/src/test/java/automatizadoOLX/resource/chromedriver.exe";

    private final String URL_OLX = "https://www.olx.com.br/autos-e-pecas/carros-vans-e-utilitarios/vw-volkswagen/fox";

    private static final String URL = "jdbc:mysql://<URL_DO_BANCO>/<NOME_DO_BANCO>";
    private static final String USER = "<USUARIO_DO_MYSQL>";
    private static final String PASSWORD = "<SENHA_DO_MYSQL>";

    @Test
    public void testCapturaEArmazenaAnuncios() {
        System.setProperty("webdriver.chrome.driver", CAMINHO_DRIVER);
        WebDriver driver = new ChromeDriver();

        try {
            driver.get(URL_OLX);

            WebDriverWait wait = new WebDriverWait(driver, 20);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,800);");
            Thread.sleep(3000);

            String[] paths = {
                    "/html/body/div[1]/div[2]/main/div[2]/div/main/div[7]/section[1]",
                    "/html/body/div[1]/div[2]/main/div[2]/div/main/div[7]/section[2]",
                    "/html/body/div[1]/div[2]/main/div[2]/div/main/div[7]/section[3]",
                    "/html/body/div[1]/div[2]/main/div[2]/div/main/div[7]/section[4]",
                    "/html/body/div[1]/div[2]/main/div[2]/div/main/div[7]/section[5]",
                    "/html/body/div[1]/div[2]/main/div[2]/div/main/div[7]/section[6]",
                    "/html/body/div[1]/div[2]/main/div[2]/div/main/div[7]/section[7]",
                    "/html/body/div[1]/div[2]/main/div[2]/div/main/div[7]/section[8]",
                    "/html/body/div[1]/div[2]/main/div[2]/div/main/div[7]/section[9]",
                    "/html/body/div[1]/div[2]/main/div[2]/div/main/div[7]/section[10]"
            };

            for (String path : paths) {
                try {
                    WebElement sectionElement = wait.until(
                            ExpectedConditions.presenceOfElementLocated(By.xpath(path)));

                    String conteudoSection = sectionElement.getText().trim();

                    if (!conteudoSection.isEmpty()) {
                        String anuncioFiltrado = extrairInformacoesEssenciais(driver, sectionElement);

                        if (!anuncioFiltrado.isEmpty()) {
                            inserirNoBanco(anuncioFiltrado);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("⚠️ Falha ao capturar section: " + path);
                }
            }

        } catch (Exception e) {
            System.out.println("❌ Erro ao capturar os detalhes dos sections: " + e.getMessage());
        } finally {
            driver.quit();
            System.out.println("✅ WebDriver fechado.");
        }
    }

    public void inserirNoBanco(String anuncio) {
        String[] linhas = anuncio.split("\n");

        String valorCarro = linhas[0].replace("Valor do Carro: ", "");
        String nomeCarro = linhas[1].replace("Nome do Veículo: ", "");
        String quilometragem = linhas[2].replace("Quilometragem: ", "");
        String cor = linhas[3].replace("Cor do carro: ", "");
        String aceitaTroca = linhas[4].replace("Aceita Troca: ", "");
        String cidade = linhas[5].replace("Cidade: ", "");

        String sql = "INSERT INTO anuncios (valor, nome_veiculo, quilometragem, cor, aceita_troca, cidade)"
                + " VALUES (?, ?, ?, ?, ?, ?)";

        try (
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, valorCarro);
            stmt.setString(2, nomeCarro);
            stmt.setString(3, quilometragem);
            stmt.setString(4, cor);
            stmt.setString(5, aceitaTroca);
            stmt.setString(6, cidade);

            stmt.executeUpdate();
            System.out.println("✅ Dados inseridos no banco com sucesso!");

        } catch (SQLException e) {
            System.out.println("❌ Erro ao inserir no banco: " + e.getMessage());
        }
    }

    public String extrairInformacoesEssenciais(WebDriver driver, WebElement sectionElement) {
        String[] linhas = sectionElement.getText().split("\n");

        String valorCarro = "N/A", nomeCarro = "", quilometragem = "", cor = "",
                aceitaTroca = "Não especificado", cidade = "Não especificada";

        Pattern kmPattern = Pattern.compile("\\d{1,3}(\\.\\d{3})* km");
        Pattern corPattern = Pattern.compile("\\b(Preto|Branco|Prata|Vermelho|Azul|Cinza|Verde|Amarelo|Laranja)\\b");
        Pattern trocaPattern = Pattern.compile("\\b(Aceita trocas|Não aceita trocas)\\b");

        for (String linha : linhas) {
            if (linha.contains("Patrocinado") || linha.toLowerCase().contains("impulsionado")) {
                continue;
            }

            if (nomeCarro.isEmpty() && linha.length() > 5) {
                nomeCarro = linha;
            }

            Matcher kmMatcher = kmPattern.matcher(linha);
            Matcher corMatcher = corPattern.matcher(linha);
            Matcher trocaMatcher = trocaPattern.matcher(linha);

            if (kmMatcher.find()) {
                quilometragem = kmMatcher.group();
            }
            if (corMatcher.find()) {
                cor = corMatcher.group();
            }
            if (trocaMatcher.find()) {
                aceitaTroca = trocaMatcher.group();
            }
        }

        try {
            WebElement precoElemento = sectionElement.findElement(By.xpath(".//div[2]/div[1]/div[2]/h3"));
            valorCarro = precoElemento.getText().trim();
        } catch (Exception e) {
            System.out.println("⚠️ Não foi possível capturar o valor do carro dentro do section.");
        }

        try {
            WebElement cidadeElemento = sectionElement.findElement(By.xpath(".//div[2]/div[2]/div/div/div/p"));
            cidade = cidadeElemento.getText().trim();
        } catch (Exception e) {
            System.out.println("⚠️ Não foi possível capturar a cidade dentro do section.");
        }

        return "Valor do Carro: " + valorCarro
                + "\nNome do Veículo: " + nomeCarro
                + "\nQuilometragem: " + quilometragem
                + "\nCor do carro: " + cor
                + "\nAceita Troca: " + aceitaTroca
                + "\nCidade: " + cidade;
    }
}
