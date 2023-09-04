package com.gnsoftware.Ordem.Servico.dto;


import com.gnsoftware.Ordem.Servico.model.OsItemServicoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OsItemServicoDto  {

    private Long id;
    private Long ordemServico_id;

    private Long servico_id;
    private int quantidade;
    private double preco;
    private double subTotalServico = 0;

    public OsItemServicoDto(OsItemServicoEntity entity) {
        this.id = entity.getId();
        this.ordemServico_id = entity.getOrdemServico().getId();
        this.servico_id = entity.getServicoEntity().getId();
        this.quantidade = entity.getQuantidade();
        this.preco = entity.getPreco();
        this.subTotalServico = entity.subTotal();
    }

}
