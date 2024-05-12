package com.soulcode.elashelp.Services;

import com.soulcode.elashelp.Models.Login;
import com.soulcode.elashelp.Models.Tecnico;
import com.soulcode.elashelp.Models.Usuario;
import com.soulcode.elashelp.Repositories.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private LoginRepository loginRepository;

    public boolean verificarLogin(String email, String senha) {

        Login login = loginRepository.findByEmail(email);

        if (login != null) {
            // Verifica se a senha inserida corresponde à senha armazenada no banco de dados

            return senha.equals(login.getSenha());
        }
        return false;
    }

    public boolean acessoPorId(Usuario idUsuario) {
        // Verifica se o ID do usuário não é nulo
        if (idUsuario != null) {
            // Tenta encontrar o Login associado ao usuário pelo ID
            Login loginUsuario = loginRepository.findByUsuario(idUsuario);

            // Verifica se o Login foi encontrado e se o Usuario associado ao Login corresponde ao idUsuario fornecido
            return loginUsuario != null && loginUsuario.getUsuario() != null && loginUsuario.getUsuario().getLogin().equals(idUsuario);
        }

        // Caso contrário, retorna falso
        return false;
    }
}