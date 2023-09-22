package com.gnsoftware.Ordem.Servico.repository;

import com.gnsoftware.Ordem.Servico.model.Pks.ProdutoOrdemPK;
import com.gnsoftware.Ordem.Servico.model.compositekey.ProdutoOrdemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoOrdemRepository extends JpaRepository<ProdutoOrdemEntity, ProdutoOrdemPK> {
}