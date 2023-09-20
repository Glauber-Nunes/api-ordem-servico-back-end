package com.gnsoftware.Ordem.Servico.model;

import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "status_os")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class StatusOrdemServicoEntity {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
}
