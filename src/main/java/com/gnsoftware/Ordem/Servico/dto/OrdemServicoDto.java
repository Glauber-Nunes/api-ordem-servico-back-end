package com.gnsoftware.Ordem.Servico.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gnsoftware.Ordem.Servico.model.*;
import com.gnsoftware.Ordem.Servico.model.compositekey.ProdutoOrdemEntity;
import com.gnsoftware.Ordem.Servico.model.compositekey.ServicoOrdemEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdemServicoDto {

    private Long id;

    private AtendenteDto atendente;

    private SituacaoOrdemDto situacaoOrdem;

    private ClienteDto cliente;

    private String descricao;

    private TecnicoDto tecnico;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", locale = "pt-BR", timezone = "America/Sao_Paulo")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_do_servico")
    private Date dataDoServico;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", locale = "pt-BR", timezone = "America/Sao_Paulo")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_fechamento")
    private Date dataFechamento;

    private List<ProdutoOrdemDto> produtos = new ArrayList<>();

    private List<ServicoOrdemDto> servicos = new ArrayList<>();

    private FornecedorDto fornecedor;

    private String observacoes;

    private StatusOrdemServicoDto statusOrdemServico;

    private Double valorTotalOrdem;

    public OrdemServicoDto(OrdemServicoEntity entity, List<ServicoOrdemEntity> itemServicos, List<ProdutoOrdemEntity> itemProdutos) {
        this.id = entity.getId();
        this.atendente = new AtendenteDto(entity.getAtendenteEntity());
        this.situacaoOrdem = new SituacaoOrdemDto(entity.getSituacaoOrdemEntity());
        this.cliente = new ClienteDto(entity.getClienteEntity());
        this.descricao = entity.getDescricao();
        this.tecnico = new TecnicoDto(entity.getTecnicoEntity());
        dataDoServico = entity.getDataDoServico();
        dataFechamento = entity.getDataFechamento();
        this.fornecedor = new FornecedorDto(entity.getFornecedorEntity());
        this.observacoes = entity.getObservacoes();
        this.statusOrdemServico = new StatusOrdemServicoDto(entity.getStatusOrdemServicoEntity());

        for (ProdutoOrdemEntity produto : itemProdutos) {
            this.produtos.add(new ProdutoOrdemDto(produto));
        }

        for (ServicoOrdemEntity servico : itemServicos) {
            this.servicos.add(new ServicoOrdemDto(servico));
        }

        this.valorTotalOrdem = entity.getValorTotalOrdem();

    }

    public OrdemServicoDto(OrdemServicoEntity entity) {
        this.id = entity.getId();
        this.atendente = new AtendenteDto(entity.getAtendenteEntity());
        this.situacaoOrdem = new SituacaoOrdemDto(entity.getSituacaoOrdemEntity());
        this.cliente = new ClienteDto(entity.getClienteEntity());
        this.descricao = entity.getDescricao();
        this.tecnico = new TecnicoDto(entity.getTecnicoEntity());
        dataDoServico = entity.getDataDoServico();
        dataFechamento = entity.getDataFechamento();
        this.fornecedor = new FornecedorDto(entity.getFornecedorEntity());
        this.observacoes = entity.getObservacoes();
        this.statusOrdemServico = new StatusOrdemServicoDto(entity.getStatusOrdemServicoEntity());
    }

}
