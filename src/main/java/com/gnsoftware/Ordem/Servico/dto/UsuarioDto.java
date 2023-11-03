package com.gnsoftware.Ordem.Servico.dto;


import com.gnsoftware.Ordem.Servico.model.UsuarioEntity;

import com.gnsoftware.Ordem.Servico.model.enums.Perfil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {

    private Long id;
    @Size(min = 5, max = 20)
    private String nome;
    @Column(unique = true)
    private String email;
    private String senha;

    private Set<Perfil> perfils = new HashSet<>();

    public UsuarioDto(UsuarioEntity entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.email = entity.getEmail();
        //this.senha = entity.getSenha();

    }


}