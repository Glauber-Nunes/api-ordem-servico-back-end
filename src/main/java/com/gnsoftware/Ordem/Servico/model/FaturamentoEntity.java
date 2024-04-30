package com.gnsoftware.Ordem.Servico.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FaturamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double valor;

    private String protocoloOs;

    @ManyToOne
    @JoinColumn(name = "status_faturamento_id")
    private StatusFaturamentoOs faturamentoOs;
}
