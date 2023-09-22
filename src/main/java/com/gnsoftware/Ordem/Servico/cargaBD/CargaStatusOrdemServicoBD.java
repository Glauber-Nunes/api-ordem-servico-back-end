package com.gnsoftware.Ordem.Servico.cargaBD;

import com.gnsoftware.Ordem.Servico.model.StatusOrdemServicoEntity;
import com.gnsoftware.Ordem.Servico.repository.StatusOrdemServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class CargaStatusOrdemServicoBD implements CommandLineRunner {

    @Autowired
    StatusOrdemServicoRepository statusOrdemServico;

    @Override
    public void run(String... args) throws Exception {

        StatusOrdemServicoEntity ABERTA = new StatusOrdemServicoEntity(null, "ABERTA");
        StatusOrdemServicoEntity ENCERRADA = new StatusOrdemServicoEntity(null, "ENCERRADA");
        StatusOrdemServicoEntity ANDAMENTO = new StatusOrdemServicoEntity(null, "ANDAMENTO");
        statusOrdemServico.saveAll(Arrays.asList(ABERTA, ENCERRADA, ANDAMENTO));
    }
}
