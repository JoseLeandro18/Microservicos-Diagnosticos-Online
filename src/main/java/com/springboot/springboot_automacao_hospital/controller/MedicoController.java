package com.springboot.springboot_automacao_hospital.controller;

import com.springboot.springboot_automacao_hospital.model.Medico;
import com.springboot.springboot_automacao_hospital.model.Paciente;
import com.springboot.springboot_automacao_hospital.repository.MedicoRepository;
import com.springboot.springboot_automacao_hospital.repository.PacienteRepository;
import com.springboot.springboot_automacao_hospital.service.IAService;
import com.springboot.springboot_automacao_hospital.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


@Controller
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private IAService iaService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private PacienteRepository pacienteRepository;

    @GetMapping("/cadastro-medico")
    public String mostrarFormularioCadastro() {
        return "cadastro-medico";
    }

    @PostMapping("/cadastro-medico")
    public String salvarMedico(Medico medico, Model model) {
        medicoRepository.save(medico);

        model.addAttribute("idMedico", medico.getCodigoMedico());
        return "idMedico";
    }

    @GetMapping("/login-medico")
    public String mostrarFormularioLoginMedico() {
        return "login-medico";
    }

    @PostMapping("/consulta-medico")
    public String consultarPaciente(@RequestParam String nomeMedico,
                                    @RequestParam String cpfMedico,
                                    @RequestParam Long idMedico,
                                    @RequestParam Long idPaciente,
                                    Model model) {

        Optional<Paciente> optionalPaciente = pacienteRepository.findById(idPaciente);
        Optional<Medico> optionalMedico = medicoRepository.findById(idMedico);

        if (optionalPaciente.isPresent() && optionalMedico.isPresent()) {
            Medico medico = optionalMedico.get();
            Paciente paciente = optionalPaciente.get();

            if (medico.getNomeMedico().equalsIgnoreCase(nomeMedico.trim()) &&
                    medico.getCpfMedico().equals(cpfMedico.trim())) {

                String diagnosticoPaciente = iaService.obterDiagnostico(paciente.getSintomasPaciente(), paciente);
                model.addAttribute("idPaciente", paciente.getId());
                model.addAttribute("sintomasPaciente", paciente.getSintomasPaciente());
                model.addAttribute("diagnosticoPaciente", diagnosticoPaciente);

                return "consulta-medico";
            }
        }

        model.addAttribute("erro", "Medico ou paciente não encontrado. Verifique os dados.");
        return "login-medico";
    }

}
