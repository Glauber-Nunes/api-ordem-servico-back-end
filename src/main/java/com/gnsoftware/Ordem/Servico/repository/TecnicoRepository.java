package com.gnsoftware.Ordem.Servico.repository;

import com.gnsoftware.Ordem.Servico.model.TecnicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TecnicoRepository extends JpaRepository<TecnicoEntity, Long> {
}