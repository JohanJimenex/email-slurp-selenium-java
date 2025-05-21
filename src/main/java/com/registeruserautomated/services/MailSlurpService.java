package com.registeruserautomated.services;

import com.mailslurp.apis.InboxControllerApi;
import com.mailslurp.apis.WaitForControllerApi;
import com.mailslurp.clients.ApiClient;
import com.mailslurp.models.Email;
import com.mailslurp.models.InboxDto;
import com.registeruserautomated.config.TestConfig;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailSlurpService {

    private final ApiClient client;
    private final InboxControllerApi inboxControllerApi;
    private final WaitForControllerApi waitForControllerApi;
    private InboxDto inbox;

    public MailSlurpService() {
        this.client = new ApiClient();
        this.client.setApiKey(TestConfig.MAIL_SLURP_API_KEY);
        this.inboxControllerApi = new InboxControllerApi(client);
        this.waitForControllerApi = new WaitForControllerApi(client);
    }

    // Crea un nuevo inbox y lo guarda internamente
    public String createInboxAndGetEmail() throws Exception {
        this.inbox = inboxControllerApi.createInboxWithDefaults();
        return this.inbox.getEmailAddress();
    }

    // Espera el correo y extrae el código de verificación de 6 dígitos
    public String waitForVerificationCode() throws Exception {
        Email email = waitForControllerApi.waitForLatestEmail(
                inbox.getId(),
                60000L,
                true,
                null,
                null,
                TestConfig.MAIL_SLURP_API_KEY,
                null
        );

        String body = email.getBody();
        Matcher matcher = Pattern.compile("\\b\\d{6}\\b").matcher(body);

        if (matcher.find()) {
            return matcher.group();
        } else {
            throw new RuntimeException("No se encontró un código de verificación de 6 dígitos.");
        }
    }

    // Elimina el inbox
    public void deleteInbox() throws Exception {
        if (inbox != null) {
            inboxControllerApi.deleteInbox(inbox.getId());
        }
    }
}
