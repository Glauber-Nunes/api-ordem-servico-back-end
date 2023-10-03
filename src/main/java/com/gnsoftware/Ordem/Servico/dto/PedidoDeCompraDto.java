package com.gnsoftware.Ordem.Servico.dto;


import com.gnsoftware.Ordem.Servico.model.PedidoDeCompraEntity;
import com.gnsoftware.Ordem.Servico.model.compositekey.ItemPedidoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDeCompraDto {

    private Long id;

    private FornecedorDto fornecedor;

    private List<ItemPedidoDto> pedidoProdutos = new ArrayList<>();

    private Double totalPedido;

    public PedidoDeCompraDto(PedidoDeCompraEntity entity, List<ItemPedidoEntity> pedidoDeCompraEntities) {
        this.id = entity.getId();
        this.fornecedor = new FornecedorDto(entity.getFornecedor());

        for (ItemPedidoEntity pedidoEntity : pedidoDeCompraEntities) {
            this.pedidoProdutos.add(new ItemPedidoDto(pedidoEntity));
        }

        this.totalPedido = entity.totalPedido();
    }
}
