package com.springboot.springboot_automacao_hospital.service;

import com.springboot.springboot_automacao_hospital.model.Paciente;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IAService {

    private static final String API_KEY = "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";
    private static final String URL = "https://openrouter.ai/api/v1/chat/completions";

    public String obterDiagnostico(String sintomas, Paciente paciente) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);

        // Construindo a mensagem
        Map<String, Object> mensagemUsuario = new HashMap<>();
        mensagemUsuario.put("role", "user");
        mensagemUsuario.put("content", "Olá, sou o paciente " + paciente.getNomePaciente() +
                " e meus sintomas são: " + sintomas +
                ". Por favor, forneça um diagnóstico e recomende um especialista resumidamente por favor.");

        List<Map<String, Object>> mensagens = new ArrayList<>();
        mensagens.add(mensagemUsuario);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "openai/gpt-3.5-turbo"); // ou outro modelo compatível da OpenRouter
        body.put("messages", mensagens);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    URL,
                    HttpMethod.POST,
                    request,
                    Map.class
            );

            Map<String, Object> resposta = (Map<String, Object>) ((List<?>) response.getBody().get("choices")).get(0);
            Map<String, Object> message = (Map<String, Object>) resposta.get("message");

            return (String) message.get("content");

        } catch (Exception e) {
            return "Erro ao obter diagnóstico da IA: " + e.getMessage();
        }
    }
}
