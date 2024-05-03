package com.soulcode.elashelp.Services;

import com.soulcode.elashelp.Models.Login;
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
}
