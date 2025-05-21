package com.registeruserautomated.tests;

import com.registeruserautomated.services.MailSlurpService;

public class RegisterTestDesacoplado {

    public static void main(String[] args) throws Exception {

        MailSlurpService mailService = new MailSlurpService();

        // 1. Obtener correo temporal
        String email = mailService.createInboxAndGetEmail();

        /*
        ===========================
            AQUI VA EL CÓDIGO DE AUTOMATIZACIÓN DE REGISTRO EN LA PÁGINA DE CLARO
            Ejemplo:
            WebDriver driver = new ChromeDriver();

            driver.get("https://www.claro.com.co/");

            WebElement registerButton = driver.findElement(By.id("register"));

            registerButton.click();

            WebElement emailField = driver.findElement(By.id("email"));

            emailField.sendKeys(email);

            WebElement submitButton = driver.findElement(By.id("submit"));
            submitButton.click();
        ===========================
         */
        // 2. Obtener el código de verificación
        String codigo = mailService.waitForVerificationCode();

        /*
        ===========================
            AQUI VA EL CÓDIGO DE AUTOMATIZACIÓN PARA INGRESAR EL CÓDIGO DE VERIFICACIÓN
            Ejemplo:
            WebElement verificationField = driver.findElement(By.id("verificationCode"));
            verificationField.sendKeys(codigo);

            WebElement confirmButton = driver.findElement(By.id("confirm"));
            confirmButton.click();
        ===========================
         */
        // 3. Limpiar recursos
        mailService.deleteInbox();
    }
}
