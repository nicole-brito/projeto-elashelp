package com.soulcode.elashelp.Services;


import com.soulcode.elashelp.Models.Login;
import com.soulcode.elashelp.Models.Role;
import com.soulcode.elashelp.Models.Tecnico;
import com.soulcode.elashelp.Models.Usuario;
import com.soulcode.elashelp.Repositories.LoginRepository;
import com.soulcode.elashelp.Repositories.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Autowired
    private LoginRepository loginRepository;

    public List<Tecnico> findAll() {
        return tecnicoRepository.findAll();
    }

    public Tecnico createTecnico(Tecnico tecnico) {
        if (tecnicoRepository.existsByEmail(tecnico.getEmail())) {
            throw new RuntimeException("Já existe um usuário com este email");
        }
//        }if(tecnicoRepository.existsByMatricula(tecnico.getMatricula())){
//            throw new RuntimeException("Já existe um usuário com este id");
//        }
        tecnico = tecnicoRepository.save(tecnico);

        Login login = new Login();
        login.setEmail(tecnico.getEmail());
        login.setSenha(tecnico.getSenha());
        login.setTecnico(tecnico);

        //TODO por hora fixo administrador ate receber isso do front
        login.setRole(Role.TECNICO);

        String encryptedPassword = new BCryptPasswordEncoder().encode(login.getPassword());
        login.setSenha(encryptedPassword);


        login = loginRepository.save(login);
        tecnico.setLogin(login);
        enviarEmailDeBoasVindas(login.getEmail());
        return tecnico;
    }
    private void enviarEmailDeBoasVindas(String email) {
        try {
            // Chama o método sendEmail do EmailService
            EmailService.sendEmail(email);
            System.out.println("E-mail de boas-vindas enviado para: " + email);
        } catch (Exception ex) {
            // Trata qualquer exceção de envio de e-mail
            System.err.println("Erro ao enviar e-mail de boas-vindas: " + ex.getMessage());
        }
    }
    public Tecnico updateTecnico(Tecnico tecnico) {
        this.tecnicoRepository.findById(tecnico.getIdTecnico());
        return tecnicoRepository.save(tecnico);
    }

    public Tecnico findById(Long idTecnico) {
        return tecnicoRepository.findById(idTecnico).orElse(null);
    }

    public String deleteById(Long idTecnico) {
        tecnicoRepository.deleteById(idTecnico);
        return "Técnico excluído com sucesso! Id Técnico: " + idTecnico;
    }
}
