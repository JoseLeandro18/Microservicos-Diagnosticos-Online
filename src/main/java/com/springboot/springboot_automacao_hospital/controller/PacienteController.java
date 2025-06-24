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

    @GetMapping("/cadastro")
    public String mostrarFormularioCadastro() {
        return "cadastro";
    }

    @PostMapping("/cadastro") // <- Esse caminho é ativado quando o formulário envia para /pacientes com POST
    public String salvarPaciente(Paciente paciente, Model model) {
        pacienteRepository.save(paciente); // <- salva o paciente no banco

        model.addAttribute("idPaciente", paciente.getId()); // envia o id para a página

        return "idForm";
    }

    @GetMapping("/consulta")
    public String mostrarFormularioConsulta() {
        return "login";
    }

    @PostMapping("/consulta")
    public String consultarPaciente(@RequestParam String nomePaciente,
                                    @RequestParam String cpfPaciente,
                                    @RequestParam Long idPaciente,
                                    Model model) {

        Optional<Paciente> optionalPaciente = pacienteRepository.findById(idPaciente);

        if (optionalPaciente.isPresent()) {
            Paciente paciente = optionalPaciente.get();

            // Verifica se nome e CPF batem
            if (paciente.getNomePaciente().equals(nomePaciente) && paciente.getCpfPaciente().equals(cpfPaciente)) {
                String diagnostico = iaService.obterDiagnostico(paciente.getSintomasPaciente(), paciente);
                model.addAttribute("paciente", paciente);
                model.addAttribute("diagnostico", diagnostico);
                return "consulta";
            }
        }

        model.addAttribute("erro", "Paciente não encontrado. Verifique os dados.");
        return "login";
    }


}
