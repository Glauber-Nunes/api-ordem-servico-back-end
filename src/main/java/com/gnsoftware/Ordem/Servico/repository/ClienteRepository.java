package com.gnsoftware.Ordem.Servico.repository;

import com.gnsoftware.Ordem.Servico.model.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

    ClienteEntity findByCpf(@Param("cpf") String cpf);
    boolean existsByCpf(String cpf);
    boolean existsByemail(String email);
}