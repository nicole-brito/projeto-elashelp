package com.soulcode.elashelp.Repositories;

import com.soulcode.elashelp.Models.Login;
import com.soulcode.elashelp.Models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Boolean existsByEmail(String email);

    Boolean existsByCpf(String cpf);



}
