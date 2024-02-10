package com.github.fadegor05.utils;

import com.github.fadegor05.models.Config;
import com.github.fadegor05.models.Instance;
import com.google.gson.Gson;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.http.HttpResponse;


public class CubeApiUtil {
    public static Instance getInstanceByAPI(String cubeApiUrl)  {
        System.out.println("Подключение к Cube-API...");
        String CONNECT_API_URL = String.format("%s/get_compiled_instance", cubeApiUrl);
        try {
            HttpResponse<String> response = Unirest.get(CONNECT_API_URL).asString();
            if (response.getStatus() < 400){
                System.out.println("Соединение с Cube-API было успешно установлено!\n");
            }
            Gson gson = new Gson();
            return gson.fromJson(String.valueOf(response.getBody()), Instance.class);
        }
        catch (UnirestException e) {
            System.out.println("Сервис Cube-API недоступен, попробуйте позже...");
            System.exit(0);
        }
        return null;
    }
}
