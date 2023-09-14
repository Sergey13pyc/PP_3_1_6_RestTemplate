package ru.aretomov.myfirstcrudapp;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class App {


        public static void main(String[] args) {
            // Создаем объект RestTemplate
            RestTemplate restTemplate = new RestTemplate();

            // Шаг 1: Получение списка всех пользователей
            ResponseEntity<String> response1 = restTemplate.getForEntity("http://94.198.50.185:7081/api/users", String.class);
            String sessionId = response1.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
            String url1 = "http://94.198.50.185:7081/api/users";
            String url2 = "http://94.198.50.185:7081/api/users/3";


            // Шаг 3: Сохранение пользователя
            User user = new User(3L, "James", "Brown", (byte) 30); // Указать свой возраст
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.COOKIE, sessionId);
            HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);
            ResponseEntity<String> response3 = restTemplate.postForEntity(url1, requestEntity, String.class);

            // Шаг 4: Изменение пользователя
            user.setName("Thomas");
            user.setLastName("Shelby");
            ResponseEntity<String> response4 = restTemplate.exchange(url1, HttpMethod.PUT, requestEntity, String.class);

            // Шаг 5: Удаление пользователя
            ResponseEntity<String> response5 = restTemplate.exchange(url2, HttpMethod.DELETE, requestEntity, String.class);

            // Собираем код из частей
            String code = response3.getBody() + response4.getBody() + response5.getBody();
            System.out.println("Итоговый код: " + code);
        }
    }

