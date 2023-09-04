package com.gnsoftware.Ordem.Servico.model;


import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gnsoftware.Ordem.Servico.dto.OsItemProdutoDto;
import com.gnsoftware.Ordem.Servico.dto.OsItemServicoDto;
import lombok.*;

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
public class OsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", locale = "pt-BR", timezone = "America/Sao_Paulo")
    private Date DataDoServico;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", locale = "pt-BR", timezone = "America/Sao_Paulo")
    private Date DataFechamento;

    @OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OsItemServicoEntity> itemServicoOs = new ArrayList<>();

    @OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OsItemProdutoEntity> itemProdutoOs = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private FornecedorEntity fornecedorEntity;

    @Column(columnDefinition = "TEXT")
    private String observacoes;
    @ManyToOne
    @JoinColumn(name = "status_id")
    private StatusOrdemServicoEntity statusOrdemServicoEntity;

    private Double valorTotalOrdem ;

    public Double totalOs() {
        double sumProdutos = 0;
        double sumServicos = 0;

        for (OsItemProdutoEntity itemServico : itemProdutoOs) {
            sumProdutos = sumProdutos + itemServico.subTotal();
        }

        for (OsItemServicoEntity itemProduto : itemServicoOs) {
            sumServicos = sumServicos + itemProduto.subTotal();
        }

        return  sumProdutos + sumServicos;
    }

}
