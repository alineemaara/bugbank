package bug.bank;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.math.BigDecimal;

public class TranferenciaTests {

    private static WebDriver driver;
    private static Conta conta1;
    private static Conta conta2;
    public static final String NOME_PRIMEIRO_USUARIO = "usuario1";
    public static final String EMAIL_PRIMEIRO_USUARIO = "usuario1@teste.com.br";
    public static final String SENHA_PRIMEIRO_USUARIO = "usuario1senha";

    public static final String NOME_SEGUNDO_USUARIO = "usuario2";
    public static final String EMAIL_SEGUNDO_USUARIO = "usuario2@teste.com.br";
    public static final String SENHA_SEGUNDO_USUARIO = "usuario2senha";

    @BeforeClass
    public static void setUp() {
        driver = TestUtils.createWebDriver();
        conta1 = ContaFactory.criarConta(NOME_PRIMEIRO_USUARIO, EMAIL_PRIMEIRO_USUARIO, SENHA_PRIMEIRO_USUARIO, driver);
        conta2 = ContaFactory.criarConta(NOME_SEGUNDO_USUARIO, EMAIL_SEGUNDO_USUARIO, SENHA_SEGUNDO_USUARIO, driver);
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }

    @Test
    public void testTransferenciaEntreContas(){
//        ARRANGE
        Double valorTranferencia = 100.00;
        conta1.acessarConta(driver);
        var saldoInicialContaDestino = conta1.getSaldo(driver);
        conta1.sairConta(driver);

        conta2.acessarConta(driver);
        var saldoInicialContaOrigem= conta2.getSaldo(driver);

//        ACT
        conta2.transferir(conta1, valorTranferencia, driver);

//        ASSERT
        var saldoAtualEsperado = (saldoInicialContaOrigem - valorTranferencia);
        Assert.assertEquals(BigDecimal.valueOf(saldoAtualEsperado), BigDecimal.valueOf(conta2.getSaldo(driver)));
        conta2.sairConta(driver);

        conta1.acessarConta(driver);
        var saldoContaDestinoAtualEsperado = (saldoInicialContaDestino + valorTranferencia);
        Assert.assertEquals(BigDecimal.valueOf(saldoContaDestinoAtualEsperado), BigDecimal.valueOf(conta1.getSaldo(driver)));
        conta1.sairConta(driver);
    }

}
