package com.springboot.springboot_automacao_hospital.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomePaciente;

    @Column(nullable = false)
    private String cpfPaciente;

    @Column(nullable = false)
    private String sintomasPaciente;
}
