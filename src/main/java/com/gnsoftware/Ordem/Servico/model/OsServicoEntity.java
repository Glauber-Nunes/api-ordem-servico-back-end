package com.gnsoftware.Ordem.Servico.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "os_servico")
public class OsServicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "ordem_servico_id")
    private OsEntity ordemServico;

    @ManyToOne
    @JoinColumn(name = "servico_id")
    private ServicoEntity servicoEntity;

    private Double quantidade;
    private Double preco;
    private double subTotalServico = 0;

    public OsServicoEntity(OsEntity ordemServico, ServicoEntity servicoEntity, Double quantidade, Double preco) {
        this.ordemServico = ordemServico;
        this.servicoEntity = servicoEntity;
        this.quantidade = quantidade;
        this.preco = preco;
        this.subTotalServico += subTotal();
    }

    @PrePersist
    @PreUpdate
    private void calcularSubTotalServico() {
        this.subTotalServico = quantidade * preco;
    }
    public Double subTotal() {
        return quantidade * preco;
    }
}
