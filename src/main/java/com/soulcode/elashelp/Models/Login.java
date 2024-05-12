package com.soulcode.elashelp.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "login")
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idLogin;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String senha;

    @Column(name = "reset_token")
    private String resetToken;

    @OneToOne
    @JoinColumn(name = "matricula", referencedColumnName = "matricula")
    private Tecnico tecnico;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn( name = "idUsuario", referencedColumnName = "idUsuario")
    private Usuario usuario;



}
