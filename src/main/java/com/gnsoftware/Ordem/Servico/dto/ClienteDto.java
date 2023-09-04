package com.gnsoftware.Ordem.Servico.dto;

import com.gnsoftware.Ordem.Servico.model.ClienteEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteDto {

    private Long id;
    @NotEmpty(message = "NOME REQUERIDO")
    private String nome;
    private String cpf;
    private String rg;
    private String telefone;
    private String email;
    private String endereco;

    public ClienteDto(ClienteEntity entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.cpf = entity.getCpf();
        this.rg = entity.getRg();
        this.telefone = entity.getTelefone();
        this.email = entity.getEmail();
        this.endereco = entity.getEndereco();
    }

    public ClienteDto(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

}
