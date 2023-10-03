package com.gnsoftware.Ordem.Servico.repository;

import com.gnsoftware.Ordem.Servico.model.Pks.ItemPedidoPK;
import com.gnsoftware.Ordem.Servico.model.compositekey.ItemPedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedidoEntity, ItemPedidoPK> {
}