package com.soulcode.elashelp.service;

import com.soulcode.elashelp.Models.Usuario;
import com.soulcode.elashelp.Repositories.UsuarioRepository;
import com.soulcode.elashelp.Services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class UsuarioServiceTest {

    @InjectMocks
    UsuarioService usuarioService;

    @Mock
    UsuarioRepository usuarioRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldFindUsuarioById() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1L);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Optional<Usuario> found = usuarioService.findUsuarioById(1L);

        assertEquals(usuario.getIdUsuario(), found.get().getIdUsuario());
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    public void shouldReturnNullWhenUsuarioNotFoundById() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Usuario> found = usuarioService.findUsuarioById(1L);

        assertEquals(Optional.empty(), found);
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    public void shouldFindUsuarioByEmail() {
        Usuario usuario = new Usuario();
        usuario.setEmail("test@test.com");
        when(usuarioRepository.findByEmail("test@test.com")).thenReturn(Optional.of(usuario));

        Usuario found = usuarioService.findByEmail("test@test.com");

        assertEquals(usuario.getEmail(), found.getEmail());
        verify(usuarioRepository, times(1)).findByEmail("test@test.com");
    }

    @Test
    public void shouldReturnNullWhenUsuarioNotFoundByEmail() {
        when(usuarioRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());

        Usuario found = usuarioService.findByEmail("test@test.com");

        assertEquals(null, found);
        verify(usuarioRepository, times(1)).findByEmail("test@test.com");
    }
}