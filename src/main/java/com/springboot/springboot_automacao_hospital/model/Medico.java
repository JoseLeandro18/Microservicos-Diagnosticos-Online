package com.springboot.springboot_automacao_hospital.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "medicos")
@AllArgsConstructor
@NoArgsConstructor
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeMedico;

    @Column(nullable = false)
    private String cpfMedico;

    @Column(nullable = false)
    private Long codigoMedico;
}
