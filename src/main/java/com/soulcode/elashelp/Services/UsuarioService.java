package com.soulcode.elashelp.Services;

import com.soulcode.elashelp.Models.Usuario;
import com.soulcode.elashelp.Repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UsuarioService {

    @Autowired
    private final UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario createUsuario(Usuario usuario) {
        if (usuarioRepository.existsById(usuario.getCpf())) {
            throw new RuntimeException("Já existe um usuário com esse CPF");
        }
        return usuarioRepository.save(usuario);
    }


    public Usuario updateUsuario(Usuario usuario) {
        this.usuarioRepository.findById(usuario.getCpf());
        return usuarioRepository.save(usuario);
    }

    public Usuario deleteById (String cpf) {
        this.usuarioRepository.findById(cpf);
        Usuario usuario = new Usuario();
        usuario.setCpf(cpf);
        return usuario;
    }
}
