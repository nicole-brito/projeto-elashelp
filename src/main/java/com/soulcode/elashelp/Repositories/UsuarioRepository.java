package com.soulcode.elashelp.Repositories;

import com.soulcode.elashelp.Models.Login;
import com.soulcode.elashelp.Models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Boolean existsByEmail(String email);

    Boolean existsByCpf(String cpf);

    Optional<Usuario> findByEmail(String email);

//    Usuario findByEmail(String email);

}
