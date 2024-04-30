package com.gnsoftware.Ordem.Servico.model;


import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ordem_servico")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrdemServicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "atendente_id")
    private AtendenteEntity atendenteEntity;
    @ManyToOne
    @JoinColumn(name = "situacaoOrdem_id")
    private SituacaoOrdemEntity situacaoOrdemEntity;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteEntity clienteEntity;

    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private TecnicoEntity tecnicoEntity;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", locale = "pt-BR", timezone = "America/Sao_Paulo")
    private Date DataDoServico;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", locale = "pt-BR", timezone = "America/Sao_Paulo")
    private Date DataFechamento;

    @ManyToMany
    @JoinTable(
            name = "os_servico",
            joinColumns = @JoinColumn(name = "Ordem_Servico_ID"),
            inverseJoinColumns = @JoinColumn(name = "Servico_id"))
    @JsonIgnore
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<ServicoEntity> servicos = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "os_produto",
            joinColumns = @JoinColumn(name = "Ordem_Servico_ID"),
            inverseJoinColumns = @JoinColumn(name = "Produto_ID"))
    @JsonIgnore
    private List<ProdutoEntity> produtos = new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    private String observacoes;
    @ManyToOne
    @JoinColumn(name = "status_id")
    private StatusOrdemServicoEntity statusOrdemServicoEntity;

    private Double valorTotalOrdem;

    private Double SubTotalServico;
    private Double SubTotalProduto;

    private String protocolo;

    public Double subTotalProduto(){
        this.SubTotalProduto = 0.0;

        for (ProdutoEntity produto : this.produtos) {
            SubTotalProduto = SubTotalProduto + produto.subTotal();
        }

        return SubTotalProduto;
    }

    public Double subTotalServico(){

        this.SubTotalServico = 0.0;

        for (ServicoEntity servico : this.servicos) {
            SubTotalServico = SubTotalServico + servico.subTotal();
        }

        return SubTotalServico;
    }

    public Double totalOs() {
        return subTotalProduto() + subTotalServico();
    }

}