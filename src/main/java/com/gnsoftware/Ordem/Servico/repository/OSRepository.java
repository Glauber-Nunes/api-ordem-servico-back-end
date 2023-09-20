package com.gnsoftware.Ordem.Servico.repository;

import com.gnsoftware.Ordem.Servico.model.OrdemServicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OSRepository extends JpaRepository<OrdemServicoEntity, Long> {
}