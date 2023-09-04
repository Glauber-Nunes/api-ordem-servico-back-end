package com.gnsoftware.Ordem.Servico.repository;

import com.gnsoftware.Ordem.Servico.model.PermissaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissaoRepository extends JpaRepository<PermissaoEntity, Long> {
}