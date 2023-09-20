package com.gnsoftware.Ordem.Servico.dto;

import com.gnsoftware.Ordem.Servico.model.TelefoneEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TelefoneDto {

    private Long id;
    private String dd;
    private String numero;

    public TelefoneDto(TelefoneEntity entity) {
        this.id = entity.getId();
        this.dd = entity.getDd();
        this.numero = entity.getNumero();
    }
}
