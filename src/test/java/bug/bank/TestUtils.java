package bug.bank;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class TestUtils {

    public static WebDriver createWebDriver() {
        WebDriver driver;
        if(isWindows())
            System.setProperty("webdriver.chrome.driver", "drivers/chromedriver_win32/chromedriver.exe");
        else
            System.setProperty("webdriver.chrome.driver", "drivers/chromedriver_linux64/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(8000, TimeUnit.MILLISECONDS);
        driver.get("https://bugbank.netlify.app/");
        return driver;
    }

    private static Boolean isWindows() {
        String os = System.getProperty("os.name");
        return os.toUpperCase().contains("WINDOWS");
    }
}
