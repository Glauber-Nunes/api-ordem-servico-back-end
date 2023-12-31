package com.gnsoftware.Ordem.Servico.cargaBD;

import com.gnsoftware.Ordem.Servico.model.*;
import com.gnsoftware.Ordem.Servico.model.enums.Perfil;
import com.gnsoftware.Ordem.Servico.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


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

    @Autowired
    private TelefoneRepository telefoneRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CodigoNcmRepository ncmRepository;
    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Override
    public void run(String... args) throws Exception {

        CodigoNcm codigoNcm1 = new CodigoNcm(null,"84133020","Bomba injetora motor diesel");
        CodigoNcm codigoNcm2 = new CodigoNcm(null,"87089990","Borracha descarga/silencioso");
        ncmRepository.saveAll(Arrays.asList(codigoNcm1,codigoNcm2));

        TelefoneEntity telefone1 = new TelefoneEntity(null, "81", "8199458445");
        TelefoneEntity telefone2 = new TelefoneEntity(null, "85", "81344245");
        TelefoneEntity telefone3 = new TelefoneEntity(null, "11", "119458445");

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome("ADMINISTRADOR");
        usuario.setEmail("adm@gmail.com");
        usuario.setPerfils(Arrays.asList(Perfil.ROLE_ADM));
        usuario.setSenha(passwordEncoder.encode("adm2023"));
        
        usuarioRepository.save(usuario);

       // EnderecoEntity endereco = new EnderecoEntity(null, "rua", "102", "complemneto", "bairro", "cidade");
        //    "estado", "55405-000", "Brasil");
        //    enderecoRepository.save(endereco);
        //  telefoneRepository.saveAll(Arrays.asList(telefone1, telefone2, telefone3));

        ClienteEntity clientePadrao = new ClienteEntity(null, "Gsistemas", "155.695.074-85", "7878877", "felipe4@gmail.com", telefone1, new EnderecoEntity(null,"rua 2","88","p","centro","mara","estado","888","brasil"),null);
        // ClienteEntity clienteEntity1 = new ClienteEntity(null, "joao", "262.966.600-06", "4545454", "joao@gmail.com", "rua 3", telefone2);
        // ClienteEntity clienteEntity2 = new ClienteEntity(null, "rafael", "319.386.750-02", "1545454", "rafael@gmail.com", "rua 4", telefone3);
        clienteRepository.save(clientePadrao);


        AtendenteEntity atendenteEntity = new AtendenteEntity(null, "Felipe", "415.933.010-06");
        AtendenteEntity atendente2 = new AtendenteEntity(null, "rafaela", "215.430.700-00");
        atendenteRepository.saveAll(Arrays.asList(atendenteEntity, atendente2));

        ProdutoEntity produto1 = new ProdutoEntity(null, "DESCRIÇAO 1", 120.0, "0212121", "UN", "UN", 100.0,codigoNcm1);
        ProdutoEntity produto2 = new ProdutoEntity(null, "DESCRIÇAO", 85.0, "2326262", "UN", "UN", 70.0,codigoNcm2);

        produtoRepository.saveAll(Arrays.asList(produto1, produto2));

        ServicoEntity servico1 = new ServicoEntity(null, "Serviço 1", 95.0);
        ServicoEntity servico2 = new ServicoEntity(null, "Serviço 2", 75.0);

        servicoRepository.saveAll(Arrays.asList(servico1, servico2));

        TecnicoEntity tecnico = new TecnicoEntity(null, "João");
        TecnicoEntity tecnico2 = new TecnicoEntity(null, "Marcos");

        tecnicoRepository.saveAll(Arrays.asList(tecnico, tecnico2));

        AtendenteEntity atendente = new AtendenteEntity(null, "MARIA", "15569507485");

        FornecedorEntity fornecedor = new FornecedorEntity(null, "Maraial TRANSPORTE", "MARAIAL", "PE", "13.897.686/0001-07");

        fornecedorRepository.save(fornecedor);

        PagamentoEntity pagamento1 = new PagamentoEntity(null,"Dinheiro");
        PagamentoEntity pagamento2 = new PagamentoEntity(null,"Cartão de crédito");
        PagamentoEntity pagamento3 = new PagamentoEntity(null,"Cartão de débito");
        PagamentoEntity pagamento4= new PagamentoEntity(null,"PIX");
        PagamentoEntity pagamento5= new PagamentoEntity(null,"Cheque");
        PagamentoEntity pagamento6= new PagamentoEntity(null,"A Prazo Da Loja");
        pagamentoRepository.saveAll(Arrays.asList(pagamento1,pagamento2,pagamento3,pagamento4,pagamento5,pagamento6));


    }
}
