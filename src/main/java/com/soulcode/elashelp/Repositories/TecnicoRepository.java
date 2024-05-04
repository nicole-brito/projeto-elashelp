package com.soulcode.elashelp.Repositories;

import com.soulcode.elashelp.Models.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {
//    <T> ScopedValue<T> findByMatricula(Long matricula);
}
