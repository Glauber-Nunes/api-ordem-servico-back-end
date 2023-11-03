package com.gnsoftware.Ordem.Servico.model;


import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gnsoftware.Ordem.Servico.model.compositekey.ProdutoOrdemEntity;
import com.gnsoftware.Ordem.Servico.model.compositekey.ServicoOrdemEntity;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
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
    private String descricao;
    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private TecnicoEntity tecnicoEntity;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", locale = "pt-BR", timezone = "America/Sao_Paulo")
    private Date DataDoServico;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", locale = "pt-BR", timezone = "America/Sao_Paulo")
    private Date DataFechamento;

    @OneToMany(mappedBy = "id.ordemServico", cascade = CascadeType.ALL)
    @JsonIgnore
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<ServicoOrdemEntity> itemServicoOs = new ArrayList<>();

    @OneToMany(mappedBy = "id.ordemServico", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProdutoOrdemEntity> itemProdutoOs = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private FornecedorEntity fornecedorEntity;

    @Column(columnDefinition = "TEXT")
    private String observacoes;
    @ManyToOne
    @JoinColumn(name = "status_id")
    private StatusOrdemServicoEntity statusOrdemServicoEntity;

    private Double valorTotalOrdem;




    public Double totalOs() {
        double sumProdutos = 0;
        double sumServicos = 0;

        for (ProdutoOrdemEntity itemServico : itemProdutoOs) {
            sumProdutos = sumProdutos + itemServico.subTotal();
        }

        for (ServicoOrdemEntity itemProduto : itemServicoOs) {
            sumServicos = sumServicos + itemProduto.subTotal();
        }

        return sumProdutos + sumServicos;
    }
}
