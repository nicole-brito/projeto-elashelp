package com.soulcode.elashelp.service;

import com.soulcode.elashelp.Models.Tecnico;
import com.soulcode.elashelp.Repositories.TecnicoRepository;
import com.soulcode.elashelp.Services.TecnicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TecnicoServiceTest {

    @InjectMocks
    TecnicoService tecnicoService;

    @Mock
    TecnicoRepository tecnicoRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldFindAllTecnicos() {
        Tecnico tecnico1 = new Tecnico();
        Tecnico tecnico2 = new Tecnico();
        when(tecnicoRepository.findAll()).thenReturn(Arrays.asList(tecnico1, tecnico2));

        List<Tecnico> tecnicos = tecnicoService.findAll();

        assertEquals(2, tecnicos.size());
        verify(tecnicoRepository, times(1)).findAll();
    }

    @Test
    public void shouldFindTecnicoById() {
        Tecnico tecnico = new Tecnico();
        tecnico.setIdTecnico(1L);
        when(tecnicoRepository.findById(1L)).thenReturn(Optional.of(tecnico));

        Tecnico found = tecnicoService.findById(1L);

        assertEquals(tecnico.getIdTecnico(), found.getIdTecnico());
        verify(tecnicoRepository, times(1)).findById(1L);
    }

    @Test
    public void shouldReturnNullWhenTecnicoNotFoundById() {
        when(tecnicoRepository.findById(1L)).thenReturn(Optional.empty());

        Tecnico found = tecnicoService.findById(1L);

        assertEquals(null, found);
        verify(tecnicoRepository, times(1)).findById(1L);
    }

    @Test
    public void shouldFindTecnicoByEmail() {
        Tecnico tecnico = new Tecnico();
        tecnico.setEmail("test@test.com");
        when(tecnicoRepository.findByEmail("test@test.com")).thenReturn(Optional.of(tecnico));

        Optional<Tecnico> found = tecnicoService.findByEmail("test@test.com");

        assertEquals(tecnico.getEmail(), found.get().getEmail());
        verify(tecnicoRepository, times(1)).findByEmail("test@test.com");
    }

    @Test
    public void shouldReturnEmptyWhenTecnicoNotFoundByEmail() {
        when(tecnicoRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());

        Optional<Tecnico> found = tecnicoService.findByEmail("test@test.com");

        assertEquals(Optional.empty(), found);
        verify(tecnicoRepository, times(1)).findByEmail("test@test.com");
    }

    @Test
    public void shouldCreateTecnico() {
        Tecnico tecnico = new Tecnico();
        when(tecnicoRepository.save(tecnico)).thenReturn(tecnico);

        Tecnico created = tecnicoService.createTecnico(tecnico);

        assertEquals(tecnico, created);
        verify(tecnicoRepository, times(1)).save(tecnico);
    }

    @Test
    public void shouldUpdateTecnico() {
        Tecnico tecnico = new Tecnico();
        tecnico.setIdTecnico(1L);
        when(tecnicoRepository.findById(1L)).thenReturn(Optional.of(tecnico));
        when(tecnicoRepository.save(tecnico)).thenReturn(tecnico);

        Tecnico updated = tecnicoService.updateTecnico(tecnico);

        assertEquals(tecnico, updated);
        verify(tecnicoRepository, times(1)).findById(1L);
        verify(tecnicoRepository, times(1)).save(tecnico);
    }

    @Test
    public void shouldDeleteTecnico() {
        doNothing().when(tecnicoRepository).deleteById(1L);

        tecnicoService.deleteById(1L);

        verify(tecnicoRepository, times(1)).deleteById(1L);
    }
}