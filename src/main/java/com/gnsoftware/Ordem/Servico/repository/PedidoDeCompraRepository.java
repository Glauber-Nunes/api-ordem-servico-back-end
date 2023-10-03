package com.gnsoftware.Ordem.Servico.repository;

import com.gnsoftware.Ordem.Servico.model.PedidoDeCompraEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoDeCompraRepository extends JpaRepository<PedidoDeCompraEntity, Long> {
}