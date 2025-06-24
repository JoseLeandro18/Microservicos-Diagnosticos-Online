package com.springboot.springboot_automacao_hospital.repository;

import com.springboot.springboot_automacao_hospital.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Paciente findByNomePacienteAndCpfPaciente(String nomePaciente, String cpfPaciente);
}
