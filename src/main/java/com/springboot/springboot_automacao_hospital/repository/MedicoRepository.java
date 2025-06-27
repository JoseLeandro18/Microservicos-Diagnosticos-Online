package com.springboot.springboot_automacao_hospital.repository;

import com.springboot.springboot_automacao_hospital.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

}
