package com.springboot.springboot_automacao_hospital.service;

import com.springboot.springboot_automacao_hospital.model.Paciente;
import com.springboot.springboot_automacao_hospital.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    // Procura Todos
    public List<Paciente> findAllPacientes() {
        return pacienteRepository.findAll();
    }

    // Procura pelo ID
    public Paciente findPacienteById(Long id) {
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        return paciente.get();
    }

    // Remove Todos
    public void removeAllPacientes() {
        pacienteRepository.deleteAll();
    }

    // Remove pelo ID
    public void removePacienteById(Long id) {
        pacienteRepository.deleteById(id);
    }
}
