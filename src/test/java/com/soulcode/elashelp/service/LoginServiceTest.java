package com.soulcode.elashelp.service;

import com.soulcode.elashelp.Models.Login;
import com.soulcode.elashelp.Models.Tecnico;
import com.soulcode.elashelp.Models.Usuario;
import com.soulcode.elashelp.Repositories.LoginRepository;
import com.soulcode.elashelp.Services.LoginService;
import com.soulcode.elashelp.Services.TecnicoService;
import com.soulcode.elashelp.Services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LoginServiceTest {

    @InjectMocks
    LoginService loginService;

    @Mock
    UsuarioService usuarioService;

    @Mock
    TecnicoService tecnicoService;

    @Mock
    LoginRepository loginRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetEmail() {
        UserDetails userDetails = mock(UserDetails.class);
        when(loginRepository.findByEmail("test@test.com")).thenReturn(userDetails);

        UserDetails found = loginService.getEmail("test@test.com");

        assertEquals(userDetails, found);
        verify(loginRepository, times(1)).findByEmail("test@test.com");
    }

    @Test
    public void shouldGetUsuarioOuTecnicoForUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNome("Test");
        usuario.setSobrenome("User");
        when(usuarioService.findByEmail("test@test.com")).thenReturn(usuario);

        String name = loginService.getUsuarioOuTecnico("test@test.com");

        assertEquals("Test User", name);
        verify(usuarioService, times(1)).findByEmail("test@test.com");
    }

    @Test
    public void shouldGetUsuarioOuTecnicoForTecnico() {
        when(usuarioService.findByEmail("test@test.com")).thenReturn(null);

        Tecnico tecnico = new Tecnico();
        tecnico.setNome("Test");
        tecnico.setSobrenome("Tech");
        when(tecnicoService.findByEmail("test@test.com")).thenReturn(Optional.of(tecnico));

        String name = loginService.getUsuarioOuTecnico("test@test.com");

        assertEquals("Test Tech", name);
        verify(usuarioService, times(1)).findByEmail("test@test.com");
        verify(tecnicoService, times(1)).findByEmail("test@test.com");
    }

    @Test
    public void shouldReturnNullWhenNoUsuarioOrTecnicoFound() {
        when(usuarioService.findByEmail("test@test.com")).thenReturn(null);
        when(tecnicoService.findByEmail("test@test.com")).thenReturn(Optional.empty());

        String name = loginService.getUsuarioOuTecnico("test@test.com");

        assertEquals(null, name);
        verify(usuarioService, times(1)).findByEmail("test@test.com");
        verify(tecnicoService, times(1)).findByEmail("test@test.com");
    }
}