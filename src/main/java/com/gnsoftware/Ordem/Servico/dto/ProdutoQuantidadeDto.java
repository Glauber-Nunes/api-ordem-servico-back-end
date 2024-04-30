package com.gnsoftware.Ordem.Servico.dto;

import com.gnsoftware.Ordem.Servico.model.ProdutoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoQuantidadeDto {

    private ProdutoDto produto;
    private int quantidade;
    public ProdutoQuantidadeDto(ProdutoEntity produto, int quantidade) {
        this.produto = new ProdutoDto(produto);
        this.quantidade = quantidade;
    }
}
