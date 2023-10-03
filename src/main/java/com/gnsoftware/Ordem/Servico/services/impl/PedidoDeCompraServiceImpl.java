package com.gnsoftware.Ordem.Servico.services.impl;

import com.gnsoftware.Ordem.Servico.dto.PedidoDeCompraDto;
import com.gnsoftware.Ordem.Servico.model.PedidoDeCompraEntity;
import com.gnsoftware.Ordem.Servico.repository.PedidoDeCompraRepository;
import com.gnsoftware.Ordem.Servico.services.PedidoDeCompraService;
import com.gnsoftware.Ordem.Servico.services.auxiliar.MapperObjectPedidoSave;
import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoDeCompraServiceImpl implements PedidoDeCompraService {

    @Autowired
    private MapperObjectPedidoSave mapperObjectPedidoSave;

    @Override
    public PedidoDeCompraDto save(PedidoDeCompraDto dto) {

        PedidoDeCompraEntity pedidoDeCompra = new PedidoDeCompraEntity();

        mapperObjectPedidoSave.mapperObjectSave(dto, pedidoDeCompra);

        return new PedidoDeCompraDto(pedidoDeCompra, pedidoDeCompra.getPedidoProdutos());
    }

    @Override
    public PedidoDeCompraDto update(Long id, PedidoDeCompraDto dto) {
        return null;
    }

    @Override
    public PedidoDeCompraDto findById(Long id) {
        return null;
    }

    @Override
    public List<PedidoDeCompraDto> findAll() {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
