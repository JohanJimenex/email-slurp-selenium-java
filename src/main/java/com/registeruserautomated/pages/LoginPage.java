package com.registeruserautomated.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private final WebDriver driver;

    // === Selectores ===
    private final By usernameInput = By.id("username");  // Ajusta según tu HTML
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("loginButton");
    private final By errorMessage = By.id("loginError");

    // === Constructor ===
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // === Acciones ===
    public void enterUsername(String username) {
        driver.findElement(usernameInput).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public void loginAs(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    // === Validaciones ===
    public boolean isLoginErrorDisplayed() {
        return driver.findElement(errorMessage).isDisplayed();
    }

    public String getLoginErrorText() {
        return driver.findElement(errorMessage).getText();
    }
}
