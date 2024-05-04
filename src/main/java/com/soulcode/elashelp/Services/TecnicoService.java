package com.soulcode.elashelp.Services;


import com.soulcode.elashelp.Models.Tecnico;
import com.soulcode.elashelp.Models.Usuario;
import com.soulcode.elashelp.Repositories.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TecnicoService {
    @Autowired
    private TecnicoRepository tecnicoRepository;

    public List<Tecnico> findAll() {
        return tecnicoRepository.findAll();
    }

    public Tecnico createTecnico(Tecnico tecnico) {
        if (tecnicoRepository.existsById(tecnico.getMatricula())) {
            throw new RuntimeException("Já existe um Técnico com esse CPF");
        }
        return tecnicoRepository.save(tecnico);
    }

    public Tecnico updateTecnico(Tecnico tecnico) {
        this.tecnicoRepository.findById(tecnico.getMatricula());
        return tecnicoRepository.save(tecnico);
    }

    public Tecnico buscarTecnicoId(Long id) {
        return tecnicoRepository.getById(id);
    }

    public Tecnico deleteById(Long matricula) {
        this.tecnicoRepository.findById(matricula);
        Tecnico tecnico = new Tecnico();
        tecnico.setMatricula(matricula);
        Tecnico Tecnico;
        return tecnico;
    }

}
