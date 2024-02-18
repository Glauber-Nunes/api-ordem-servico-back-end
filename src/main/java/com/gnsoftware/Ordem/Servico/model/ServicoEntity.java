package com.gnsoftware.Ordem.Servico.model;


import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "servico")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ServicoEntity {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private Double preco;

    public Double subTotal() {
        return this.preco;
    }
}
