package com.gnsoftware.Ordem.Servico.repository;

import com.gnsoftware.Ordem.Servico.model.Pks.ServicoOrdemPK;
import com.gnsoftware.Ordem.Servico.model.compositekey.ServicoOrdemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoOrdemRepository extends JpaRepository<ServicoOrdemEntity, ServicoOrdemPK> {
}