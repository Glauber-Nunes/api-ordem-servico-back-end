package com.gnsoftware.Ordem.Servico.dto;


import com.gnsoftware.Ordem.Servico.model.CodigoNcm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CodigoNcmDto {

    private Long id;
    private String numero;
    private String descricao;

    public CodigoNcmDto(CodigoNcm entity) {
        this.id = entity.getId();
        this.numero = entity.getNumero();
        this.descricao = entity.getDescricao();
    }
}
