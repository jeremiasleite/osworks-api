package com.jeremiasleite.osworks.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeremiasleite.osworks.domain.model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long>{

}
