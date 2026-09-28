package main.java.com.zebra.vault;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class CardProviderClient {
    private final RestClient cardProviderClient;

    public CardProviderClient(@Value("${card_provider.base_url}") String baseUrl) {
        this.cardProviderClient = RestClient.builder().baseUrl(baseUrl).build();
    }
}
