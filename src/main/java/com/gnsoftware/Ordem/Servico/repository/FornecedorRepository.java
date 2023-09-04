package com.gnsoftware.Ordem.Servico.repository;

import com.gnsoftware.Ordem.Servico.model.FornecedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FornecedorRepository extends JpaRepository<FornecedorEntity, Long> {

    boolean existsByCnpj(String cnpj);
}