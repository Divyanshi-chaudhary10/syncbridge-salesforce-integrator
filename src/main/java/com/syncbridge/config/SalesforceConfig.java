package com.syncbridge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.context.annotation.Bean;

@Configuration
public class SalesforceConfig {

    @Value("${salesforce.client-id:mock-client-id}")
    private String clientId;

    @Value("${salesforce.client-secret:mock-client-secret}")
    private String clientSecret;

    @Value("${salesforce.authorization-uri:https://login.salesforce.com/services/oauth2/authorize}")
    private String authorizationUri;

    @Value("${salesforce.token-uri:https://login.salesforce.com/services/oauth2/token}")
    private String tokenUri;

    @Value("${salesforce.user-info-uri:https://login.salesforce.com/services/oauth2/userinfo}")
    private String userInfoUri;

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration salesforceRegistration = ClientRegistration
            .withRegistrationId("salesforce")
            .clientId(clientId)
            .clientSecret(clientSecret)
            .authorizationGrantType(org.springframework.security.oauth2.core.AuthorizationGrantType.AUTHORIZATION_CODE)
            .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
            .authorizationUri(authorizationUri)
            .tokenUri(tokenUri)
            .userInfoUri(userInfoUri)
            .userNameAttributeName("preferred_username")
            .scope("api", "refresh_token")
            .clientName("Salesforce")
            .build();

        return new InMemoryClientRegistrationRepository(salesforceRegistration);
    }
}