package com.gnsoftware.Ordem.Servico.repository;

import com.gnsoftware.Ordem.Servico.model.OsServicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OsItemServicoEntityRepository extends JpaRepository<OsServicoEntity, Long> {
}