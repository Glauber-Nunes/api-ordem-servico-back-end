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
    private String uf;
    @CNPJ
    private String cnpj;

    public FornecedorDto(FornecedorEntity entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.municipio = entity.getMunicipio();
        this.uf = entity.getUf();
        this.cnpj = entity.getCnpj();
    }
}
