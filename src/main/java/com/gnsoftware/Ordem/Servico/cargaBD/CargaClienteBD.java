package com.gnsoftware.Ordem.Servico.cargaBD;

import com.gnsoftware.Ordem.Servico.model.*;
import com.gnsoftware.Ordem.Servico.repository.*;
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
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ServicoRepository servicoRepository;
    @Autowired
    private TecnicoRepository tecnicoRepository;
    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Override
    public void run(String... args) throws Exception {

        ClienteEntity clienteEntity = new ClienteEntity(null, "felipe", "155.695.074-85", "7878877", "819189652", "felipe4@gmail.com", "rua 2");
        ClienteEntity clienteEntity1 = new ClienteEntity(null, "joao", "262.966.600-06", "4545454", "81565656", "joao@gmail.com", "rua 3");
        ClienteEntity clienteEntity2 = new ClienteEntity(null, "rafael", "319.386.750-02", "1545454", "81963548", "rafael@gmail.com", "rua 4");

       clienteRepository.saveAll(Arrays.asList(clienteEntity, clienteEntity1, clienteEntity2));

        AtendenteEntity atendenteEntity = new AtendenteEntity(null, "Felipe", "415.933.010-06");
        AtendenteEntity atendente2 = new AtendenteEntity(null, "rafaela", "215.430.700-00");
        atendenteRepository.saveAll(Arrays.asList(atendenteEntity,atendente2));

        ProdutoEntity produto1 = new ProdutoEntity(null,"DESCRIÇAO 1",120.0,"0212121","UN","UN",100.0);
        ProdutoEntity produto2 = new ProdutoEntity(null,"DESCRIÇAO",85.0,"2326262","UN","UN",70.0);

        produtoRepository.saveAll(Arrays.asList(produto1,produto2));

        ServicoEntity servico1 = new ServicoEntity(null,"Serviço 1" ,95.0);
        ServicoEntity servico2 = new ServicoEntity(null,"Serviço 2" ,75.0);

        servicoRepository.saveAll(Arrays.asList(servico1,servico2));

        TecnicoEntity tecnico = new TecnicoEntity(null,"João");
        TecnicoEntity tecnico2 = new TecnicoEntity(null,"Marcos");

        tecnicoRepository.saveAll(Arrays.asList(tecnico,tecnico2));

        AtendenteEntity atendente = new AtendenteEntity(null,"MARIA","15569507485");

        FornecedorEntity fornecedor = new FornecedorEntity(null,"Maraial TRANSPORTE","MARAIAL","PE","13.897.686/0001-07");

        fornecedorRepository.save(fornecedor);
    }
}
