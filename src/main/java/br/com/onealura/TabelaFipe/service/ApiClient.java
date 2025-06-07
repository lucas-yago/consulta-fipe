package br.com.onealura.TabelaFipe.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ApiClient {

    private final HttpClient client;

    public ApiClient() {
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public String fetchData(String url){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(10))
                .build();
        HttpResponse<String> reponse = null;
        
        try {
            reponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            
        }catch (IOException | InterruptedException e){
            throw new RuntimeException(e);
        }
        return reponse.body();
    };
}
