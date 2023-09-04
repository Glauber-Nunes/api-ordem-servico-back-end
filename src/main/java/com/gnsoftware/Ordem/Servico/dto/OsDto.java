package com.gnsoftware.Ordem.Servico.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gnsoftware.Ordem.Servico.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OsDto {

    private Long atendente_id;

    private Long situacaoOrdem_id;

    private Long cliente_id;

    private String descricao;

    private Long tecnico_id;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", locale = "pt-BR", timezone = "America/Sao_Paulo")
    private Date DataDoServico;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", locale = "pt-BR", timezone = "America/Sao_Paulo")
    private Date DataFechamento;

    private List<OsItemProdutoDto> produtos = new ArrayList<>();

    private List<OsItemServicoDto> servicos = new ArrayList<>();

    private Long fornecedor_id;

    private String observacoes;

    private Long statusOrdemServico_id;

    private Double valorTotalOrdem ;


    public OsDto(OsEntity entity, List<OsItemServicoEntity> itemServicos, List<OsItemProdutoEntity> itemProdutos) {
        this.atendente_id = entity.getAtendenteEntity().getId();
        this.situacaoOrdem_id = entity.getSituacaoOrdemEntity().getId();
        this.cliente_id = entity.getClienteEntity().getId();
        this.descricao = entity.getDescricao();
        this.tecnico_id = entity.getTecnicoEntity().getId();
        DataDoServico = entity.getDataDoServico();
        DataFechamento = entity.getDataFechamento();
        this.fornecedor_id = entity.getFornecedorEntity().getId();
        this.observacoes = entity.getObservacoes();
        this.statusOrdemServico_id = entity.getStatusOrdemServicoEntity().getId();

        for (OsItemProdutoEntity produto : itemProdutos) {
            this.produtos.add(new OsItemProdutoDto(produto));
        }

        for (OsItemServicoEntity servico : itemServicos) {
            this.servicos.add(new OsItemServicoDto(servico));
        }

        this.valorTotalOrdem = entity.getValorTotalOrdem();

    }

    public OsDto(OsEntity entity) {
        this.atendente_id = entity.getAtendenteEntity().getId();
        this.situacaoOrdem_id = entity.getSituacaoOrdemEntity().getId();
        this.cliente_id = entity.getClienteEntity().getId();
        this.descricao = entity.getDescricao();
        this.tecnico_id = entity.getTecnicoEntity().getId();
        DataDoServico = entity.getDataDoServico();
        DataFechamento = entity.getDataFechamento();
        this.fornecedor_id = entity.getFornecedorEntity().getId();
        this.observacoes = entity.getObservacoes();
        this.statusOrdemServico_id = entity.getStatusOrdemServicoEntity().getId();
        this.valorTotalOrdem = entity.getValorTotalOrdem();
    }

}
