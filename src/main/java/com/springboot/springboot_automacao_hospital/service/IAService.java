package com.springboot.springboot_automacao_hospital.service;

import com.springboot.springboot_automacao_hospital.model.Paciente;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IAService {

    private final String API_KEY = "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";
    private final String API_URL = "https://openrouter.ai/api/v1/chat/completions";

    public String obterDiagnostico(String sintomasPaciente, Paciente paciente) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);
        headers.add("HTTP-Referer", "http://localhost:8080"); // importante para autorização
        headers.add("X-Title", "automacao-hospital-java");

        // Montando o corpo da requisição como JSON
        JSONObject json = new JSONObject();
        json.put("model", "mistralai/mistral-7b-instruct");

        JSONArray messages = new JSONArray();
        messages.put(new JSONObject().put("role", "system").put("content", "Você é um médico que irá diagnosticar doenças baseado nos sintomas do paciente e indicar um médico para o paciente poder realizar a consulta pessoalmente."));
        messages.put(new JSONObject().put("role", "user").put("content", "Olá meu nome é: " + paciente.getNomePaciente() + " e meus sintomas são: " + sintomasPaciente));

        json.put("messages", messages);

        HttpEntity<String> request = new HttpEntity<>(json.toString(), headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    API_URL,
                    HttpMethod.POST,
                    request,
                    String.class
            );

            String responseBody = response.getBody();

            // Extraindo a resposta da IA
            JSONObject jsonResponse = new JSONObject(responseBody);
            JSONArray choices = jsonResponse.getJSONArray("choices");

            if (choices.length() > 0) {
                JSONObject message = choices.getJSONObject(0).getJSONObject("message");
                return message.getString("content");
            } else {
                return "A IA não retornou uma resposta válida.";
            }

        } catch (Exception e) {
            return "Erro ao obter diagnóstico da IA: " + e.getMessage();
        }
    }
}
