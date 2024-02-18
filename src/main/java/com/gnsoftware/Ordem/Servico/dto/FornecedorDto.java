package com.gnsoftware.Ordem.Servico.dto;

import com.gnsoftware.Ordem.Servico.model.FornecedorEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FornecedorDto {

    private Long id;
    @NotBlank(message = "NOME REQUERIDO")
    private String nome;
    private String municipio;
    @CNPJ
    private String cnpj;

    private UfDto uf;

    public FornecedorDto(FornecedorEntity entity) {

        if (entity != null) {
            this.id = entity.getId();
            this.nome = entity.getNome();
            this.municipio = entity.getMunicipio();
            this.cnpj = entity.getCnpj();
            this.uf = new UfDto(entity.getUfEntity());
        }

    }
}
