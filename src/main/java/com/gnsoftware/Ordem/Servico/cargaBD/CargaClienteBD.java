package com.gnsoftware.Ordem.Servico.cargaBD;

import com.gnsoftware.Ordem.Servico.model.ClienteEntity;
import com.gnsoftware.Ordem.Servico.repository.AtendenteRepository;
import com.gnsoftware.Ordem.Servico.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class CargaClienteBD implements CommandLineRunner {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private AtendenteRepository atendenteRepository;

    @Override
    public void run(String... args) throws Exception {

        ClienteEntity clienteEntity = new ClienteEntity(null, "felipe", "155.695.074-85", "7878877", "819189652", "felipe4@gmail.com", "rua 2");
        ClienteEntity clienteEntity1 = new ClienteEntity(null, "joao", "262.966.600-06", "4545454", "81565656", "joao@gmail.com", "rua 3");
        ClienteEntity clienteEntity2 = new ClienteEntity(null, "rafael", "319.386.750-02", "1545454", "81963548", "rafael@gmail.com", "rua 4");

       clienteRepository.saveAll(Arrays.asList(clienteEntity, clienteEntity1, clienteEntity2));

        //AtendenteEntity atendenteEntity = new AtendenteEntity(null, "Felipe", "415.933.010-06");
        //AtendenteEntity atendente2 = new AtendenteEntity(null, "rafaela", "215.430.700-00");
        //atendenteRepository.save(atendenteEntity);
        //atendenteRepository.save(atendente2);
    }
}
