package com.gnsoftware.Ordem.Servico.dto;

import com.gnsoftware.Ordem.Servico.model.compositekey.ItemPedidoEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemPedidoDto {

    private Long Pedido_id;
    private Long produto_id;
    private Double quantidade;
    private Double preco;
    private String localEntrega;
    private String infoComplementar;
    private double subTotalPedido = 0;

    public ItemPedidoDto(ItemPedidoEntity entity) {
        this.Pedido_id = entity.getPedidoDeCompraEntity().getId();
        this.produto_id = entity.getProdutoEntity().getId_produto();
        this.quantidade = entity.getQuantidade();
        this.preco = entity.getPreco();
        this.localEntrega = entity.getLocalEntrega();
        this.infoComplementar = entity.getInfoComplementar();
        this.subTotalPedido = entity.subTotal();
    }
}
