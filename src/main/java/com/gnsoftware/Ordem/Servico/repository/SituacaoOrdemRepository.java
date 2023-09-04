package com.gnsoftware.Ordem.Servico.repository;

import com.gnsoftware.Ordem.Servico.model.SituacaoOrdemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SituacaoOrdemRepository extends JpaRepository<SituacaoOrdemEntity, Long> {
}