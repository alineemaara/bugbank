package bug.bank;

import org.openqa.selenium.WebDriver;

public class ContaFactory {

    public static Conta criarConta(String nome, String email, String senha, WebDriver driver) {
        Conta conta = new Conta(nome, email, senha);
        conta.criarConta(driver);
        return conta;
    }
}
