package com.gnsoftware.Ordem.Servico.repository;

import com.gnsoftware.Ordem.Servico.model.compositekey.ProdutoOrdemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OsItemProdutoRepository extends JpaRepository<ProdutoOrdemEntity, Long> {
}