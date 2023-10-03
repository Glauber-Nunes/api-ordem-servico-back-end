package com.gnsoftware.Ordem.Servico.services;

import com.gnsoftware.Ordem.Servico.dto.PedidoDeCompraDto;

import java.util.List;

public interface PedidoDeCompraService {

    PedidoDeCompraDto save(PedidoDeCompraDto dto);

    PedidoDeCompraDto update(Long id,PedidoDeCompraDto dto);

    PedidoDeCompraDto findById(Long id);

    List<PedidoDeCompraDto> findAll();

    void delete (Long id);
}
