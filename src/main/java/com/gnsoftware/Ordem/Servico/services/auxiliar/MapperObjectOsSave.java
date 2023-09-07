package com.gnsoftware.Ordem.Servico.services.auxiliar;

import com.gnsoftware.Ordem.Servico.dto.OsDto;
import com.gnsoftware.Ordem.Servico.dto.OsProdutoDto;
import com.gnsoftware.Ordem.Servico.dto.OsServicoDto;
import com.gnsoftware.Ordem.Servico.model.*;
import com.gnsoftware.Ordem.Servico.repository.*;
import com.gnsoftware.Ordem.Servico.services.EmailService;
import com.gnsoftware.Ordem.Servico.services.StatusOrdemServicoService;
import com.gnsoftware.Ordem.Servico.services.exceptions.DataIntegrityViolationException;
import com.gnsoftware.Ordem.Servico.services.exceptions.ModelNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class MapperObjectOsSave {
    @Autowired
    private com.gnsoftware.Ordem.Servico.repository.OSRepository OSRepository;

    @Autowired
    private StatusOrdemServicoService statusOrdemServicoService;

    @Autowired
    private EmailService emailService;
    @Autowired
    private ServicoRepository servicoRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private AtendenteRepository atendenteRepository;
    @Autowired
    private SituacaoOrdemRepository situacaoOrdemRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private TecnicoRepository tecnicoRepository;
    @Autowired
    private FornecedorRepository fornecedorRepository;
    @Autowired
    private OsItemProdutoEntityRepository osItemProdutoEntityRepository;

    public void mapperObjectSave(OsDto osDto, OsEntity osEntity) {

        Optional<AtendenteEntity> atendente = atendenteRepository.findById(osDto.getAtendente_id());
        atendente.orElseThrow(() -> new ModelNotFound("Atendente Not Found"));
        Optional<SituacaoOrdemEntity> situacaoOrdem = situacaoOrdemRepository.findById(osDto.getSituacaoOrdem_id());
        situacaoOrdem.orElseThrow(() -> new ModelNotFound("Situaçâo Not Found"));
        Optional<ClienteEntity> cliente = clienteRepository.findById(osDto.getCliente_id());
        cliente.orElseThrow(() -> new ModelNotFound("Cliente Not Found"));
        Optional<TecnicoEntity> tecnico = tecnicoRepository.findById(osDto.getTecnico_id());
        tecnico.orElseThrow(() -> new ModelNotFound("Tecnico Not Found"));
        Optional<FornecedorEntity> fornecedor = fornecedorRepository.findById(osDto.getFornecedor_id());
        fornecedor.orElseThrow(() -> new ModelNotFound("Fornecedor Not Found"));
        StatusOrdemServicoEntity statusOrdemServicoEntity = statusOrdemServicoService.findById(1L);

        osEntity.setAtendenteEntity(atendente.get());
        osEntity.setSituacaoOrdemEntity(situacaoOrdem.get());
        osEntity.setClienteEntity(cliente.get());
        osEntity.setDescricao(osDto.getDescricao());
        osEntity.setTecnicoEntity(tecnico.get());
        osEntity.setDataDoServico(new Date());
        osEntity.setFornecedorEntity(fornecedor.get());
        osEntity.setObservacoes(osDto.getObservacoes());
        osEntity.setStatusOrdemServicoEntity(statusOrdemServicoEntity);


        for (OsProdutoDto osProdutoDto : osDto.getProdutos()) {
            Optional<ProdutoEntity> produtoEntity = produtoRepository.findById(osProdutoDto.getProduto_id());
            produtoEntity.orElseThrow(() -> new ModelNotFound("Produto Not Found ID:" + osProdutoDto.getProduto_id()));

            OsProdutoEntity osItemProduto = new OsProdutoEntity(osEntity, produtoEntity.get(), osProdutoDto.getQuantidade(), osProdutoDto.getPreco());

            osEntity.getItemProdutoOs().add(osItemProduto);
            this.estoqueProdutoDisponivel(osEntity); // verifica se tem estoque no produto
        }

        for (OsServicoDto itemServico : osDto.getServicos()) {
            Optional<ServicoEntity> servicoEntity = servicoRepository.findById(itemServico.getServico_id());
            ServicoEntity servico = servicoEntity.orElseThrow(() -> new ModelNotFound("Serviço Not Found ID:" + itemServico.getServico_id()));

            OsServicoEntity osServicoEntity = new OsServicoEntity(osEntity, servico, itemServico.getQuantidade(), itemServico.getPreco());
            osEntity.getItemServicoOs().add(osServicoEntity);
        }

        osEntity.setValorTotalOrdem(osEntity.totalOs());
        OSRepository.save(osEntity);
    }


    private void estoqueProdutoDisponivel(OsEntity os) {

        for (OsProdutoEntity itemProduto : os.getItemProdutoOs()) {

            if (itemProduto.getQuantidade() > itemProduto.getProdutoEntity().getEstoque()) {
                throw new DataIntegrityViolationException("Quantidade Em Estoque Insuficiente:" + " Produto " + itemProduto.getProdutoEntity().getDescricao() + ": Contem : " + itemProduto.getProdutoEntity().getEstoque() + " Em Estoque");
            }

        }

    }
}
