package com.gnsoftware.Ordem.Servico.dto;

import com.gnsoftware.Ordem.Servico.model.SituacaoOrdemEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SituacaoOrdemDto {

    private Long id;
    private String nome;

    public SituacaoOrdemDto(SituacaoOrdemEntity entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
    }
}
