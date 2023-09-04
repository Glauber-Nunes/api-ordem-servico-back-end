package com.gnsoftware.Ordem.Servico.repository;

import com.gnsoftware.Ordem.Servico.model.StatusOrdemServicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusOrdemServicoRepository extends JpaRepository<StatusOrdemServicoEntity, Long> {
}