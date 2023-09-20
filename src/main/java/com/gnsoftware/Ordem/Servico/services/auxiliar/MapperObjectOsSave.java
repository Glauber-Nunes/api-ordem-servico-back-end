package com.gnsoftware.Ordem.Servico.services.auxiliar;

import com.gnsoftware.Ordem.Servico.dto.OrdemServicoDto;
import com.gnsoftware.Ordem.Servico.dto.ProdutoOrdemDto;
import com.gnsoftware.Ordem.Servico.dto.ServicoOrdemDto;
import com.gnsoftware.Ordem.Servico.model.*;
import com.gnsoftware.Ordem.Servico.model.compositekey.ProdutoOrdemEntity;
import com.gnsoftware.Ordem.Servico.model.compositekey.ServicoOrdemEntity;
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


    public void mapperObjectSave(OrdemServicoDto ordemServicoDto, OrdemServicoEntity ordemServicoEntity) {

        this.salvaCabecalhoOrdemServico(ordemServicoDto, ordemServicoEntity);
        this.salvaProdutoOrdemServico(ordemServicoDto, ordemServicoEntity);
        this.salvaServicoOrdemServico(ordemServicoDto, ordemServicoEntity);
        this.calculaValorTotalSalvaOrdemServico(ordemServicoEntity, ordemServicoDto);
    }

    private void salvaCabecalhoOrdemServico(OrdemServicoDto ordemServicoDto, OrdemServicoEntity ordemServicoEntity) {

        Optional<AtendenteEntity> atendente = atendenteRepository.findById(ordemServicoDto.getAtendente_id());
        atendente.orElseThrow(() -> new ModelNotFound("Atendente Not Found ID: " + ordemServicoDto.getAtendente_id()));

        Optional<SituacaoOrdemEntity> situacaoOrdem = situacaoOrdemRepository.findById(ordemServicoDto.getSituacaoOrdem_id());
        situacaoOrdem.orElseThrow(() -> new ModelNotFound("Situação Not Found ID: " + ordemServicoDto.getSituacaoOrdem_id()));

        Optional<ClienteEntity> cliente = clienteRepository.findById(ordemServicoDto.getCliente_id());
        cliente.orElseThrow(() -> new ModelNotFound("Cliente Not Found ID:" + ordemServicoDto.getCliente_id()));

        Optional<TecnicoEntity> tecnico = tecnicoRepository.findById(ordemServicoDto.getTecnico_id());
        tecnico.orElseThrow(() -> new ModelNotFound("Tecnico Not Found ID:" + ordemServicoDto.getTecnico_id()));

        Optional<FornecedorEntity> fornecedor = fornecedorRepository.findById(ordemServicoDto.getFornecedor_id());
        fornecedor.orElseThrow(() -> new ModelNotFound("Tecnico Not Found ID: " + ordemServicoDto.getFornecedor_id()));

        StatusOrdemServicoEntity statusOrdemServicoEntity = statusOrdemServicoService.findById(1L); // recebe automatico

        ordemServicoEntity.setAtendenteEntity(atendente.get());
        ordemServicoEntity.setSituacaoOrdemEntity(situacaoOrdem.get());
        ordemServicoEntity.setClienteEntity(cliente.get());
        ordemServicoEntity.setDescricao(ordemServicoDto.getDescricao());
        ordemServicoEntity.setTecnicoEntity(tecnico.get());
        ordemServicoEntity.setDataDoServico(new Date());
        ordemServicoEntity.setFornecedorEntity(fornecedor.get());
        ordemServicoEntity.setObservacoes(ordemServicoDto.getObservacoes());
        ordemServicoEntity.setStatusOrdemServicoEntity(statusOrdemServicoEntity);
    }

    private void salvaProdutoOrdemServico(OrdemServicoDto ordemServicoDto, OrdemServicoEntity ordemServicoEntity) {

        for (ProdutoOrdemDto produtoOrdemDto : ordemServicoDto.getProdutos()) {

            Optional<ProdutoEntity> produto = produtoRepository.findById(produtoOrdemDto.getProduto_id());
            produto.orElseThrow(() -> new ModelNotFound("Produto Not Found ID:" + produtoOrdemDto.getProduto_id()));

            ProdutoOrdemEntity osItemProduto = new ProdutoOrdemEntity(ordemServicoEntity, produto.get(), produtoOrdemDto.getQuantidade(), produto.get().getPreco());

            ordemServicoEntity.getItemProdutoOs().add(osItemProduto);
            this.estoqueProdutoDisponivel(ordemServicoEntity); // verifica se tem estoque no produto
        }
    }

    private void salvaServicoOrdemServico(OrdemServicoDto ordemServicoDto, OrdemServicoEntity ordemServicoEntity) {

        for (ServicoOrdemDto itemServico : ordemServicoDto.getServicos()) {
            Optional<ServicoEntity> servico = servicoRepository.findById(itemServico.getServico_id());
            servico.orElseThrow(() -> new ModelNotFound("Serviço Not Found ID:"));

            ServicoOrdemEntity servicoOrdemEntity = new ServicoOrdemEntity(ordemServicoEntity, servico.get(), itemServico.getQuantidade(), servico.get().getPreco());
            ordemServicoEntity.getItemServicoOs().add(servicoOrdemEntity);
        }
    }

    private void calculaValorTotalSalvaOrdemServico(OrdemServicoEntity ordemServicoEntity, OrdemServicoDto ordemServicoDto) {
        ordemServicoEntity.setValorTotalOrdem(ordemServicoEntity.totalOs());
        OSRepository.save(ordemServicoEntity);
        //emailService.enviarEmailOSAberta(osEntity.getClienteEntity(), osDto); //envia email para o clienteEntity
    }

    private void estoqueProdutoDisponivel(OrdemServicoEntity os) {

        for (ProdutoOrdemEntity itemProduto : os.getItemProdutoOs()) {

            if (itemProduto.getQuantidade() > itemProduto.getProdutoEntity().getEstoque()) {
                throw new DataIntegrityViolationException("Quantidade Em Estoque Insuficiente:" + " Produto " + itemProduto.getProdutoEntity().getDescricao() + ": Contem : " + itemProduto.getProdutoEntity().getEstoque() + " Em Estoque");
            }

        }

    }
}
