package com.gnsoftware.Ordem.Servico.dto;


import com.gnsoftware.Ordem.Servico.model.OsServicoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OsServicoDto {

    private Long id;
    private Long ordemServico_id;

    private Long servico_id;
    private Double quantidade;
    private Double preco;
    private double subTotalServico = 0;

    public OsServicoDto(OsServicoEntity entity) {
        this.id = entity.getId();
        this.ordemServico_id = entity.getOrdemServico().getId();
        this.servico_id = entity.getServicoEntity().getId();
        this.quantidade = entity.getQuantidade();
        this.preco = entity.getPreco();
        this.subTotalServico = entity.subTotal();
    }

}
