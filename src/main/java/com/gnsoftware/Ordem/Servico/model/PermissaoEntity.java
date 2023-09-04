package com.gnsoftware.Ordem.Servico.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "permissao")
public class PermissaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    private String autorizacao;

    @JsonIgnore
    @ManyToMany(mappedBy = "permissoes")
    private List<UsuarioEntity> usuarios = new ArrayList<>();


}
