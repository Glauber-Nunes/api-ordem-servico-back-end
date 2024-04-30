package com.gnsoftware.Ordem.Servico.cargaBD;

import com.gnsoftware.Ordem.Servico.model.StatusFaturamentoOs;
import com.gnsoftware.Ordem.Servico.model.StatusOrdemServicoEntity;
import com.gnsoftware.Ordem.Servico.repository.StatusFaturamentoRepository;
import com.gnsoftware.Ordem.Servico.repository.StatusOrdemServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class CargaStatusOrdemServicoBD implements CommandLineRunner {

    @Autowired
    StatusOrdemServicoRepository statusOrdemServico;

    @Autowired
    StatusFaturamentoRepository statusFaturamentoRepository;

    @Override
    public void run(String... args) throws Exception {

        StatusOrdemServicoEntity ABERTA = new StatusOrdemServicoEntity(null, "ABERTA");
        StatusOrdemServicoEntity ENCERRADA = new StatusOrdemServicoEntity(null, "ENCERRADA");
        StatusOrdemServicoEntity ANDAMENTO = new StatusOrdemServicoEntity(null, "ANDAMENTO");
        statusOrdemServico.saveAll(Arrays.asList(ABERTA, ENCERRADA, ANDAMENTO));

        StatusFaturamentoOs FATURADO =  new StatusFaturamentoOs(null,"FATURADO");
        StatusFaturamentoOs PENDENTE =  new StatusFaturamentoOs(null,"PENDENTE");
        StatusFaturamentoOs AGUARDANDO =  new StatusFaturamentoOs(null,"AGUARDANDO");
        statusFaturamentoRepository.saveAll(Arrays.asList(FATURADO,PENDENTE,AGUARDANDO));

    }
}
