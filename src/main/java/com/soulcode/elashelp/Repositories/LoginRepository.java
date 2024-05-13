package com.soulcode.elashelp.Repositories;

import com.soulcode.elashelp.Models.Login;
import com.soulcode.elashelp.Models.Tecnico;
import com.soulcode.elashelp.Models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {
//TODO o metodo findByEmail esta sendo utilizado pelo spruingSecurity pois o userNamedoLogin é o email
//    precisamos rever uma outra maneira de buscar os dados para envio de email utilizado no fingByEmail
//    Login findByEmail(String email);

    Login findByUsuario(Usuario id);
    Login findByTecnico(Tecnico id);

    Login findByResetToken(String resetToken);
//    Login findByEmail1(String email);
    UserDetails findByEmail(String email);
}
