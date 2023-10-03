package com.gnsoftware.Ordem.Servico.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gnsoftware.Ordem.Servico.model.*;
import com.gnsoftware.Ordem.Servico.model.compositekey.ProdutoOrdemEntity;
import com.gnsoftware.Ordem.Servico.model.compositekey.ServicoOrdemEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdemServicoDto {

    private long id;

    private long atendente;

    private long situacaoOrdem;

    private long cliente;

    private String descricao;

    private long tecnico;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", locale = "pt-BR", timezone = "America/Sao_Paulo")
    private Date dataDoServico;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", locale = "pt-BR", timezone = "America/Sao_Paulo")
    private Date dataFechamento;

    private List<ProdutoOrdemDto> produtos = new ArrayList<>();

    private List<ServicoOrdemDto> servicos = new ArrayList<>();

    private long fornecedor;

    private String observacoes;

    private long statusOrdemServico;

    private Double valorTotalOrdem;

    public OrdemServicoDto(OrdemServicoEntity entity, List<ServicoOrdemEntity> itemServicos, List<ProdutoOrdemEntity> itemProdutos) {
        this.id = entity.getId();
        this.atendente = entity.getAtendenteEntity().getId();
        this.situacaoOrdem = entity.getSituacaoOrdemEntity().getId();
        this.cliente = entity.getClienteEntity().getId();
        this.descricao = entity.getDescricao();
        this.tecnico = entity.getTecnicoEntity().getId();
        dataDoServico = entity.getDataDoServico();
        dataFechamento = entity.getDataFechamento();
        this.fornecedor = entity.getFornecedorEntity().getId();
        this.observacoes = entity.getObservacoes();
        this.statusOrdemServico = entity.getStatusOrdemServicoEntity().getId();

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
        this.atendente = entity.getAtendenteEntity().getId();
        this.situacaoOrdem = entity.getSituacaoOrdemEntity().getId();
        this.cliente = entity.getClienteEntity().getId();
        this.descricao = entity.getDescricao();
        this.tecnico = entity.getTecnicoEntity().getId();
        dataDoServico = entity.getDataDoServico();
        dataFechamento = entity.getDataFechamento();
        this.fornecedor = entity.getFornecedorEntity().getId();
        this.observacoes = entity.getObservacoes();
        this.statusOrdemServico = entity.getStatusOrdemServicoEntity().getId();
    }

}
