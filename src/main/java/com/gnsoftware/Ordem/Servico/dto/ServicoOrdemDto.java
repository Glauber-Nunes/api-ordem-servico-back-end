package com.gnsoftware.Ordem.Servico.dto;

import com.gnsoftware.Ordem.Servico.model.compositekey.ServicoOrdemEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServicoOrdemDto {

    private Long ordemServico_id;

    private Long servico_id;

    private Double quantidade;
    private Double preco;
    private double subTotalServico = 0;

    public ServicoOrdemDto(ServicoOrdemEntity entity) {
        this.ordemServico_id = entity.getOsEntity().getId();
        this.servico_id = entity.getServicoEntity().getId();
        this.quantidade = entity.getQuantidade();
        this.preco = entity.getPreco();
        this.subTotalServico = entity.subTotal();
    }

}
