package com.gnsoftware.Ordem.Servico.model.Pks;


import com.gnsoftware.Ordem.Servico.model.OrdemServicoEntity;
import com.gnsoftware.Ordem.Servico.model.ServicoEntity;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serial;
import java.io.Serializable;

@Embeddable
public class ServicoOrdemPK implements Serializable {
    @Serial
    private static final long serialVersionUID = 7133976691755621000L;
    @ManyToOne
    @JoinColumn(name = "ordem_servico_id")
    private OrdemServicoEntity ordemServico;
    @ManyToOne
    @JoinColumn(name = "servico_id")
    private ServicoEntity servicoEntity;

    public ServicoOrdemPK() {
    }

    public OrdemServicoEntity getOsEntity() {
        return ordemServico;
    }

    public void setOsEntity(OrdemServicoEntity ordemServicoEntity) {
        this.ordemServico = ordemServicoEntity;
    }

    public ServicoEntity getServicoEntity() {
        return servicoEntity;
    }

    public void setServicoEntity(ServicoEntity servicoEntity) {
        this.servicoEntity = servicoEntity;
    }
}
