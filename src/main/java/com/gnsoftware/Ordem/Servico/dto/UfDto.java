package com.gnsoftware.Ordem.Servico.dto;

import com.gnsoftware.Ordem.Servico.model.UfEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UfDto {

    private Long id;
    private String uf;

    public UfDto(UfEntity ufEntity){
        this.id = ufEntity.getId();
        this.uf = ufEntity.getUf();
    }
}
