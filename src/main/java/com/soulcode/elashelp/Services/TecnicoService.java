package com.soulcode.elashelp.Services;


import com.soulcode.elashelp.Models.Login;
import com.soulcode.elashelp.Models.Tecnico;
import com.soulcode.elashelp.Models.Usuario;
import com.soulcode.elashelp.Repositories.LoginRepository;
import com.soulcode.elashelp.Repositories.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TecnicoService {
    @Autowired
    private TecnicoRepository tecnicoRepository;
    @Autowired
    private  LoginRepository loginRepository;

    public List<Tecnico> findAll() {
        return tecnicoRepository.findAll();
    }

    public Tecnico createTecnico(Tecnico tecnico) {
//        if (tecnicoRepository.existsById(tecnico.getMatricula())) {
//            throw new RuntimeException("Já existe um Técnico com esse cadastro");
//        }
       tecnico = tecnicoRepository.save(tecnico);

        Login login = new Login();
        login.setEmail(tecnico.getEmail());
        login.setSenha(tecnico.getSenha());
        login.setTecnico(tecnico);

        login = loginRepository.save(login);
        tecnico.setLogin(login);

        return tecnico;
    }

    public Tecnico updateTecnico(Tecnico tecnico) {
        this.tecnicoRepository.findById(tecnico.getMatricula());
        return tecnicoRepository.save(tecnico);
    }

    public Tecnico findById(Long matricula) {
        return tecnicoRepository.findById(matricula).orElse(null);
    }

    public String deleteById(Long matricula) {
        tecnicoRepository.deleteById(matricula);
        return "Técnico excluído com sucesso! Matrícula: " + matricula;
    }


}
