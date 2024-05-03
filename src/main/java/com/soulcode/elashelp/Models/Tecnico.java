package com.soulcode.elashelp.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "Tecnicos")
@Entity

public class Tecnico {
    @Id

    private String cpf;

    private boolean admin;

    private String nome;

    private String email;

    @OneToOne
    private Setor setor;



}
