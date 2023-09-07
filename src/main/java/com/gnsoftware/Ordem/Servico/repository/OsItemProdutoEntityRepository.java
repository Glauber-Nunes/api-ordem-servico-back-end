package com.gnsoftware.Ordem.Servico.repository;

import com.gnsoftware.Ordem.Servico.model.OsProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OsItemProdutoEntityRepository extends JpaRepository<OsProdutoEntity, Long> {
}