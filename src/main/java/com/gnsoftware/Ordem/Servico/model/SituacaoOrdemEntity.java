package com.gnsoftware.Ordem.Servico.model;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "situacao_os")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SituacaoOrdemEntity {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
}