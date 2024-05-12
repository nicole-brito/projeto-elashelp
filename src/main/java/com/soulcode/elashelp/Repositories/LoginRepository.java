package com.soulcode.elashelp.Repositories;

import com.soulcode.elashelp.Models.Login;
import com.soulcode.elashelp.Models.Tecnico;
import com.soulcode.elashelp.Models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {
    Login findByEmail(String email);
    Login findByUsuario(Usuario id);
    Login findByTecnico(Tecnico id);

    Login findByResetToken(String resetToken);
}
