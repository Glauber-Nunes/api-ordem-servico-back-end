package com.gnsoftware.Ordem.Servico.repository;

import com.gnsoftware.Ordem.Servico.model.OsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OSRepository extends JpaRepository<OsEntity, Long> {
}