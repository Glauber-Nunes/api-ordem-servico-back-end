package com.gnsoftware.Ordem.Servico.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gnsoftware.Ordem.Servico.model.compositekey.ProdutoOrdemEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProdutoOrdemDto {

    private Long ordemServico_id;

    private ProdutoDto produto;

    private Double quantidade;
    private Double preco;
    private double subTotalProduto = 0;

    public ProdutoOrdemDto(ProdutoOrdemEntity entity) {
        this.ordemServico_id = entity.getOsEntity().getId();
        this.produto = new ProdutoDto(entity.getProdutoEntity());
        this.quantidade = entity.getQuantidade();
        this.preco = entity.getPreco();
        this.subTotalProduto = entity.subTotal();
    }
}
