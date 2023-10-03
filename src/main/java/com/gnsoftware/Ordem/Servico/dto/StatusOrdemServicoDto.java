package com.gnsoftware.Ordem.Servico.dto;

import com.gnsoftware.Ordem.Servico.model.StatusOrdemServicoEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StatusOrdemServicoDto {

    private Long id;
    private String nome;

    public StatusOrdemServicoDto(StatusOrdemServicoEntity entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
    }
}
