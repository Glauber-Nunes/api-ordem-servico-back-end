package com.gnsoftware.Ordem.Servico.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gnsoftware.Ordem.Servico.model.*;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.security.SecureRandom;
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

    private TecnicoDto tecnico;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", locale = "pt-BR", timezone = "America/Sao_Paulo")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_do_servico")
    private Date dataDoServico;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", locale = "pt-BR", timezone = "America/Sao_Paulo")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_fechamento")
    private Date dataFechamento;

    private List<ProdutoDto> produtos = new ArrayList<>();

    private List<ServicoDto> servicos = new ArrayList<>();

    private String observacoes;

    private StatusOrdemServicoDto statusOrdemServico;

    private Double valorTotalOrdem;

    private Double SubTotalServico;
    private Double SubTotalProduto;
    private String protocolo;

    public OrdemServicoDto(OrdemServicoEntity ordemServico, List<ServicoEntity> servicos, List<ProdutoEntity> produtos) {
        this.id = ordemServico.getId();
        this.atendente = new AtendenteDto(ordemServico.getAtendenteEntity());
        this.situacaoOrdem = new SituacaoOrdemDto(ordemServico.getSituacaoOrdemEntity());
        this.cliente = new ClienteDto(ordemServico.getClienteEntity());
        this.tecnico = new TecnicoDto(ordemServico.getTecnicoEntity());
        dataDoServico = ordemServico.getDataDoServico();
        dataFechamento = ordemServico.getDataFechamento();
        this.observacoes = ordemServico.getObservacoes();
        this.statusOrdemServico = new StatusOrdemServicoDto(ordemServico.getStatusOrdemServicoEntity());

        for (ProdutoEntity produto : produtos) {
            this.produtos.add(new ProdutoDto(produto));
        }

        for (ServicoEntity servico : servicos) {
            this.servicos.add(new ServicoDto(servico));
        }

        this.valorTotalOrdem = ordemServico.getValorTotalOrdem();
        this.SubTotalServico = ordemServico.getSubTotalServico();
        this.SubTotalProduto = ordemServico.getSubTotalProduto();
        this.protocolo = ordemServico.getProtocolo();
    }

    public OrdemServicoDto(OrdemServicoEntity entity) {
        this.id = entity.getId();
        this.atendente = new AtendenteDto(entity.getAtendenteEntity());
        this.situacaoOrdem = new SituacaoOrdemDto(entity.getSituacaoOrdemEntity());
        this.cliente = new ClienteDto(entity.getClienteEntity());
        this.tecnico = new TecnicoDto(entity.getTecnicoEntity());
        dataDoServico = entity.getDataDoServico();
        dataFechamento = entity.getDataFechamento();
        this.observacoes = entity.getObservacoes();
        this.statusOrdemServico = new StatusOrdemServicoDto(entity.getStatusOrdemServicoEntity());
    }


}
