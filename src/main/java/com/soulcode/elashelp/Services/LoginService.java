package com.soulcode.elashelp.Services;

import com.soulcode.elashelp.Models.Login;
import com.soulcode.elashelp.Models.Tecnico;
import com.soulcode.elashelp.Models.Usuario;
import com.soulcode.elashelp.Repositories.LoginRepository;
import com.soulcode.elashelp.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TecnicoService tecnicoService;

    @Autowired
    private LoginRepository loginRepository;

    public UserDetails getEmail(String email) {
        return loginRepository.findByEmail(email);
    }

    public String getUsuarioOuTecnico(String email) {
        Usuario usuario = usuarioService.findByEmail(email);
        if (usuario != null) {
            return usuario.getNome() + " " + usuario.getSobrenome();
        }

        Optional<Tecnico> tecnicoOptional = tecnicoService.findByEmail(email);
        if (tecnicoOptional.isPresent()) {
            Tecnico tecnico = tecnicoOptional.get();
            return tecnico.getNome() + " " + tecnico.getSobrenome();
        }

        return null;
    }
}
