package com.example.interviewsentura.service;

import com.example.interviewsentura.dto.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private OkHttpClient client;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${weavy.api.url}")
    private String weavyApiUrl;

    @Value("${weavy.api.token}")
    private String weavyApiToken;
    public UserDTO saveUser(UserDTO userDTO) {
        String endpoint = weavyApiUrl + "/api/users";
        try {

            String jsonBody = objectMapper.writeValueAsString(userDTO);

            RequestBody body = okhttp3.RequestBody.create(
                    jsonBody,
                    MediaType.parse("application/json")
            );

            Request request = new Request.Builder()
                    .url(endpoint)
                    .post(body)
                    .addHeader("Authorization", "Bearer " + weavyApiToken)
                    .addHeader("Content-Type", "application/json")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    return objectMapper.readValue(responseBody, UserDTO.class);
                } else {
                    System.out.println("Failed to save user: " + response.message());
                    throw new RuntimeException("Failed to save user: " + response);
                }
            }
        } catch (JsonProcessingException e) {
            System.out.println("Error converting user to JSON");
            e.printStackTrace();
            throw new RuntimeException("Error converting user to JSON", e);
        } catch (IOException e) {
            System.out.println("Error making HTTP request to Weavy API");
            e.printStackTrace();
            throw new RuntimeException("Error making HTTP request to Weavy API", e);
        }
    }

    public UserDTO updateUser(String userId,UserDTO userDTO){
        String endpoint = weavyApiUrl + "/api/users/" + userId;

        try {
            String jsonBody = objectMapper.writeValueAsString(userDTO);
            RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));
            Request request = new Request.Builder()
                    .url(endpoint)
                    .put(body)
                    .addHeader("Authorization", "Bearer " + weavyApiToken)
                    .addHeader("Content-Type", "application/json")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    return objectMapper.readValue(responseBody, UserDTO.class);
                } else {
                    throw new RuntimeException("Failed to update user: " + response.message());
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting user to JSON", e);
        } catch (IOException e) {
            throw new RuntimeException("Error making HTTP request to Weavy API", e);
        }
    }
    public void deleteUser(String userId){
        String endpoint = weavyApiUrl + "/api/users/" + userId+"/trash";

        Request request = new Request.Builder()
                .url(endpoint)
                .post(RequestBody.create(new byte[0]))
                .addHeader("Authorization", "Bearer " + weavyApiToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Failed to delete user: " + response.message());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error making HTTP request to Weavy API", e);
        }
    }

    public Map<String, Object> listUsers(Map<String, String> queryParams) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(weavyApiUrl + "/api/users").newBuilder();
        queryParams.forEach(urlBuilder::addQueryParameter);

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .get()
                .addHeader("Authorization", "Bearer " + weavyApiToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                return objectMapper.readValue(responseBody, Map.class);
            } else {
                throw new RuntimeException("Failed to list users: " + response.message());
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting response to Map", e);
        } catch (IOException e) {
            throw new RuntimeException("Error making HTTP request to Weavy API", e);
        }
    }
}
