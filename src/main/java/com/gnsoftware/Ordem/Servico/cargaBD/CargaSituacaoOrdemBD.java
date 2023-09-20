package com.gnsoftware.Ordem.Servico.cargaBD;

import com.gnsoftware.Ordem.Servico.model.SituacaoOrdemEntity;
import com.gnsoftware.Ordem.Servico.repository.SituacaoOrdemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class CargaSituacaoOrdemBD implements CommandLineRunner {

    @Autowired
    private SituacaoOrdemRepository situacaoOrdemRepository;

    @Override
    public void run(String... args) throws Exception {

        SituacaoOrdemEntity situacaoOrdemEntity1 = new SituacaoOrdemEntity(null, "AGUARDANDO");
        SituacaoOrdemEntity situacaoOrdemEntity2 = new SituacaoOrdemEntity(null, "AGUARDANDO PEÇA");
        SituacaoOrdemEntity situacaoOrdemEntity3 = new SituacaoOrdemEntity(null, "PRONTO");
        SituacaoOrdemEntity situacaoOrdemEntity4 = new SituacaoOrdemEntity(null, "EM CONSERTO");

        situacaoOrdemRepository.saveAll(Arrays.asList(situacaoOrdemEntity1, situacaoOrdemEntity2, situacaoOrdemEntity3, situacaoOrdemEntity4));
    }
}
