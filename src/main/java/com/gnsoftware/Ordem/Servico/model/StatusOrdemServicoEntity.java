package com.gnsoftware.Ordem.Servico.model;

import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "status_os")
public class StatusOrdemServicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
}
