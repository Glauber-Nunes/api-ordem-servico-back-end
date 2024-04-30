package com.gnsoftware.Ordem.Servico.repository;

import com.gnsoftware.Ordem.Servico.model.OrdemServicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OSRepository extends JpaRepository<OrdemServicoEntity, Long> {

    @Query("SELECT COUNT(os) FROM OrdemServicoEntity os")
    Long countOrdemServico();
}