package com.gnsoftware.Ordem.Servico.model.Pks;

import com.gnsoftware.Ordem.Servico.model.OrdemServicoEntity;
import com.gnsoftware.Ordem.Servico.model.ProdutoEntity;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serial;
import java.io.Serializable;

@Embeddable
public class ProdutoOrdemPK implements Serializable {
    @Serial
    private static final long serialVersionUID = 7133976691755621000L;
    @ManyToOne
    @JoinColumn(name = "ordem_servico_id")
    private OrdemServicoEntity ordemServico;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private ProdutoEntity produtoEntity;

    public ProdutoOrdemPK() {
    }

    public OrdemServicoEntity getOsEntity() {
        return ordemServico;
    }

    public void setOsEntity(OrdemServicoEntity ordemServicoEntity) {
        this.ordemServico = ordemServicoEntity;
    }

    public ProdutoEntity getProdutoEntity() {
        return produtoEntity;
    }

    public void setProdutoEntity(ProdutoEntity produtoEntity) {
        this.produtoEntity = produtoEntity;
    }
}
