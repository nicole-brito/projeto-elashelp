package com.soulcode.elashelp.Services;


import com.soulcode.elashelp.Models.Login;
import com.soulcode.elashelp.Models.Role;
import com.soulcode.elashelp.Models.Tecnico;
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

        tecnico = tecnicoRepository.save(tecnico);

        Login login = new Login();
        login.setEmail(tecnico.getEmail());
        login.setSenha(tecnico.getSenha());
        login.setTecnico(tecnico);

        //TODO por hora fixo  ate receber isso do front]
        //        lembrar de mudar aqui para cadastrar tecnico ou usuario
        login.setRole(Role.TECNICO);
        //login.setRole(Role.ADMINISTRADOR);

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

    public void deleteById(Long idTecnico) {
        tecnicoRepository.deleteById(idTecnico);
    }


    public Optional<Tecnico> findByEmail(String email) {
        Optional<Tecnico> tecnicos = tecnicoRepository.findByEmail(email);
        if (tecnicos.isEmpty()) {
            return Optional.empty();
        } else {
            tecnicos.stream().count();
            return Optional.of(tecnicos.get());
        }
    }


//    public Tecnico findTecnicoByEmail(String email) {
//        return tecnicoRepository.findByEmail(email);
//    }

}
