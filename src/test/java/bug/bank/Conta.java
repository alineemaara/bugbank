package bug.bank;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

public class Conta {
    private static final String BTN_CONFIRMAR_CONTA = "(//button[@type=\'submit\'])[2]";

    private final String nome;
    private final String email;
    private final String senha;
    private String numeroConta;
    private String digitoConta;

    Conta(String nome, String email, String senha) {
        this.email = email;
        this.senha = senha;
        this.nome = nome;
    }

    public String getNumeroConta() {
        return this.numeroConta;
    }

    public String getDigitoConta() {
        return this.digitoConta;
    }

    public void criarConta(WebDriver driver) {
        WebDriverWait w = new WebDriverWait(driver, 3);

        driver.findElement(By.xpath("//*[contains(text(),'Registrar')]")).click();
        driver.findElement(By.xpath("(//input[@name=\'email\'])[2]")).clear();
        driver.findElement(By.xpath("(//input[@name=\'email\'])[2]")).sendKeys(email);
        driver.findElement(By.name("name")).clear();
        driver.findElement(By.name("name")).sendKeys(nome);
        driver.findElement(By.xpath("(//input[@name=\'password\'])[2]")).clear();
        driver.findElement(By.xpath("(//input[@name=\'password\'])[2]")).sendKeys(senha);
        driver.findElement(By.xpath("//input[@name=\'passwordConfirmation\']")).clear();
        driver.findElement(By.xpath("//input[@name=\'passwordConfirmation\']")).sendKeys(senha);
        driver.findElement(By.id("toggleAddBalance")).click();

        driver.findElement(By.xpath(BTN_CONFIRMAR_CONTA)).click();
        w.until(ExpectedConditions.elementToBeClickable(By.id("btnCloseModal")));
        driver.findElement(By.id("btnCloseModal")).click();

        acessarConta(driver);
        setNumeroEDigitoConta(driver);
        sairConta(driver);

    }

    public void acessarConta(WebDriver driver) {
        WebDriverWait w = new WebDriverWait(driver, 3);
        w.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys(senha);
        driver.findElement(By.xpath("(//button[contains(.,'Acessar')])")).click();
    }

    private void setNumeroEDigitoConta(WebDriver driver) {
        var contaCompleta = driver.findElement(By.id("textAccountNumber")).findElement(By.tagName("span")).getText();
        String[] conta = contaCompleta.split("-");
        this.numeroConta = conta[0];
        this.digitoConta = conta[1];
    }

    public void sairConta(WebDriver driver) {
        driver.findElement(By.id("btnExit")).click();
    }

    public Double getSaldo(WebDriver driver) {
        var saldoConta = driver.findElement(By.id("textBalance")).findElement(By.tagName("span")).getText();
        saldoConta= saldoConta.replace("R$ ", "").replace(".", "").replace(",", ".");
        return Double.valueOf(saldoConta);
    }

    public void transferir(Conta contaDestino, Double valorTransferencia, WebDriver driver) {
        WebDriverWait w = new WebDriverWait(driver, 3);
        w.until(ExpectedConditions.elementToBeClickable(By.xpath(new String("//a[@id='btn-TRANSFERÊNCIA']/span/img".getBytes(), StandardCharsets.UTF_8))));
        driver.findElement(By.xpath(new String("//a[@id='btn-TRANSFERÊNCIA']/span/img".getBytes(), StandardCharsets.UTF_8))).click();
        driver.findElement(By.name("accountNumber")).sendKeys(contaDestino.getNumeroConta());
        driver.findElement(By.name("digit")).sendKeys(contaDestino.getDigitoConta());
        driver.findElement(By.name("transferValue")).sendKeys(valorTransferencia.toString());
        driver.findElement(By.name("description")).sendKeys("Transferir");
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        w.until(ExpectedConditions.elementToBeClickable(By.id("btnCloseModal")));
        driver.findElement(By.id("btnCloseModal")).click();
        driver.findElement(By.id("btnBack")).click();
    }

}
