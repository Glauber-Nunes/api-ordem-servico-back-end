package com.gnsoftware.Ordem.Servico.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermissaoDto {

    private Long id;
    private String autorizacao;
    private List<UsuarioDto> usuarios = new ArrayList<>();
}

