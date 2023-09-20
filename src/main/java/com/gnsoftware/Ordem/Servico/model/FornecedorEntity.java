package com.gnsoftware.Ordem.Servico.model;

import lombok.*;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "fornecedor")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FornecedorEntity {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String municipio;
    private String uf;
    @CNPJ
    private String cnpj;
}
