package com.gnsoftware.Ordem.Servico.dto;

import com.gnsoftware.Ordem.Servico.model.OsItemProdutoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OsItemProdutoDto{

    private Long id;

    private Long ordemServico_id;

    private Long produto_id;
    private double quantidade;
    private double preco;
    private double subTotalProduto = 0;

    public OsItemProdutoDto(OsItemProdutoEntity entity) {
        this.id = entity.getId();
        this.ordemServico_id = entity.getOrdemServico().getId();
        this.produto_id = entity.getProdutoEntity().getId();
        this.quantidade = entity.getQuantidade();
        this.preco = entity.getPreco();
        this.subTotalProduto = entity.getSubTotalProduto();
    }



}
