package com.gnsoftware.Ordem.Servico.repository;

import com.gnsoftware.Ordem.Servico.model.AtendenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtendenteRepository extends JpaRepository<AtendenteEntity, Long> {

    boolean existsByCpf(String cpf);
}