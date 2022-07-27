package org.hisp.dhis.integration.rapidpro;

import org.hisp.dhis.integration.rapidpro.route.SelfSignedHttpClientConfigurer;
import org.junit.jupiter.api.Test;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

public class WebhookFunctionalTestCase extends AbstractFunctionalTestCase
{
    @Test
    public void testWebhook()
        throws IOException
    {
        camelContext.getRegistry().bind( "selfSignedHttpClientConfigurer", new SelfSignedHttpClientConfigurer() );
        camelContext.start();

        String webhookMessage = StreamUtils.copyToString(
            Thread.currentThread().getContextClassLoader().getResourceAsStream( "webhook.json" ),
            Charset.defaultCharset() );

        producerTemplate.sendBody(
            rapidProConnectorHttpEndpointUri + "/webhook?httpClientConfigurer=#selfSignedHttpClientConfigurer&httpMethod=POST",
            webhookMessage);
    }
}