package com.registeruserautomated.tests;

import com.mailslurp.apis.InboxControllerApi;
import com.mailslurp.apis.WaitForControllerApi;
import com.mailslurp.clients.ApiClient;
import com.mailslurp.models.Email;
import com.mailslurp.models.InboxDto;
import com.registeruserautomated.config.TestConfig;

public class RegisterTest {

    public static void main(String[] args) throws Exception {

        // Obtenermos el apikey desde el archivo de configuracion
        String apiKey = TestConfig.MAIL_SLURP_API_KEY;

        // Configuramos el cliente de mailslurp
        ApiClient client = new ApiClient();
        client.setApiKey(apiKey);

        // create an inbox
        InboxControllerApi inboxControllerApi = new InboxControllerApi(client);
        InboxDto inbox = inboxControllerApi.createInboxWithDefaults();

        //Dirección de Correo creado:
        String emailAddress = inbox.getEmailAddress();

        /*
        ===========================
            AQUI VA EL CÓDIGO DE AUTOMATIZACIÓN DE REGISTRO EN LA PÁGINA DE CLARO
        ===========================
         */
        
        // Esperar y obtener el último email recibido en el inbox
        WaitForControllerApi waitForControllerApi = new WaitForControllerApi(client);
        //Espera hasta 60 segundos para recibir un email
        Email email = waitForControllerApi.waitForLatestEmail(inbox.getId(), 60000L, true, null, null, apiKey, null);

        /* Este es un ejemplo de cómo podrías obtener un email específico por su ID */
        // UUID inboxId = UUID.fromString("72584029-e127-48c9-a2fa-06c6b5e4e12d"); // Reemplaza por tu ID especifico si lo necesitas
        // Email email = waitForControllerApi.waitForLatestEmail(inboxId, 60000L, true, null, null, "DESC", null);
        
        
        // Extraer el primer número de 6 dígitos del cuerpo del email
        String body = email.getBody();
        String codigo = null;
       
        // Usamos una expresión regular para encontrar un código de 6 dígitos, cambia el valor 6 si es necesario
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("\\b\\d{6}\\b").matcher(body);

        if (matcher.find()) {
            codigo = matcher.group();
            System.out.println("Código de verificación extraído: " + codigo);
        } else {
            System.out.println("No se encontró un código de verificación de 6 dígitos.");
        }

        //Elimina el inbox después de usarlo
        inboxControllerApi.deleteInbox(inbox.getId());

    }
}
