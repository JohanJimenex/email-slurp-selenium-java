package com.registeruserautomated.tests;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.registeruserautomated.pages.LoginPage;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginTest {

    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeAll
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\chromedriver.exe"); // o usa WebDriverManager
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        loginPage = new LoginPage(driver);
    }

    @BeforeEach
    public void goToLoginPage() {
        driver.get("https://tusitio.com/login");  // cambia por tu URL real
    }

    @Test
    public void testLoginConCredencialesValidas() {
        loginPage.loginAs("usuario_valido", "clave_valida");
        Assertions.assertFalse(loginPage.isLoginErrorDisplayed(), "No debería mostrar error");
        // También puedes verificar si redirige al dashboard
    }

    @Test
    public void testLoginConCredencialesInvalidas() {
        loginPage.loginAs("usuario_fake", "clave_mala");
        Assertions.assertTrue(loginPage.isLoginErrorDisplayed(), "Debería mostrar mensaje de error");
        Assertions.assertEquals("Credenciales incorrectas", loginPage.getLoginErrorText());
    }

    @AfterAll
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
