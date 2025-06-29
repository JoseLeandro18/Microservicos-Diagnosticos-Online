package com.springboot.springboot_automacao_hospital.controller;

import com.springboot.springboot_automacao_hospital.model.Paciente;
import com.springboot.springboot_automacao_hospital.repository.PacienteRepository;
import com.springboot.springboot_automacao_hospital.service.IAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private IAService iaService;

    @GetMapping("/cadastro-paciente")
    public String mostrarFormularioCadastro() {
        return "cadastro-paciente";
    }

    @PostMapping("/cadastro-paciente") // <- Esse caminho é ativado quando o formulário envia para /pacientes com POST
    public String salvarPaciente(Paciente paciente, Model model) {
        pacienteRepository.save(paciente); // <- salva o paciente no banco

        model.addAttribute("idPaciente", paciente.getId()); // envia o id para a página

        return "idPaciente";
    }

    @GetMapping("/login-paciente")
    public String mostrarFormularioConsulta() {
        return "login-paciente";
    }

    @PostMapping("/consulta-paciente")
    public String consultarPaciente(@RequestParam String cpfPaciente,
                                    @RequestParam Long idPaciente,
                                    Model model) {

        Optional<Paciente> optionalPaciente = pacienteRepository.findById(idPaciente);

        if (optionalPaciente.isPresent()) {
            Paciente paciente = optionalPaciente.get();

            // Verifica se nome e CPF batem
            if (paciente.getCpfPaciente().equals(cpfPaciente.trim())) {
                String diagnostico = iaService.obterDiagnostico(paciente.getSintomasPaciente(), paciente);
                model.addAttribute("paciente", paciente);
                model.addAttribute("diagnostico", diagnostico);
                return "consulta-paciente";
            }
        }

        model.addAttribute("erro", "Paciente não encontrado. Verifique os dados.");
        return "login-paciente";
    }


}
