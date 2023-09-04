package com.gnsoftware.Ordem.Servico.services.impl;

import com.gnsoftware.Ordem.Servico.dto.OsItemProdutoDto;
import com.gnsoftware.Ordem.Servico.dto.OsItemServicoDto;
import com.gnsoftware.Ordem.Servico.dto.OsDto;
import com.gnsoftware.Ordem.Servico.model.*;
import com.gnsoftware.Ordem.Servico.repository.*;
import com.gnsoftware.Ordem.Servico.services.*;
import com.gnsoftware.Ordem.Servico.services.exceptions.DataIntegrityViolationException;
import com.gnsoftware.Ordem.Servico.services.exceptions.ModelNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class OSServiceImpl implements OSService {

    @Autowired
    private OSRepository OSRepository;

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

    @Override
    @Transactional
    public OsDto save(OsDto osDto) {

        OsEntity osEntity = new OsEntity();
        this.mapperObject(osDto, osEntity);

        //emailService.enviarEmailOSAberta(osEntity.getClienteEntity(), osDto); //envia email para o clienteEntity

        return new OsDto(osEntity, osEntity.getItemServicoOs(), osEntity.getItemProdutoOs());

    }

    @Override
    public OsDto update(Long id, OsDto osDto) {

        Optional<OsEntity> entityBanco = OSRepository.findById(id);
        entityBanco.orElseThrow(() -> new ModelNotFound("Ordem Serviço Not Found"));

        this.mapperObject(osDto, entityBanco.get());

        return new OsDto(entityBanco.get(), entityBanco.get().getItemServicoOs(), entityBanco.get().getItemProdutoOs());

    }

    @Override
    public OsDto findById(Long id) {
        Optional<OsEntity> ordemServico = OSRepository.findById(id);

        ordemServico.orElseThrow(() -> new ModelNotFound("Ordem De Serviço Não Encontrada"));

        return new OsDto(ordemServico.get());
    }

    @Override
    public void delete(Long id) {
        Optional<OsEntity> ordemServico = OSRepository.findById(id);
        ordemServico.orElseThrow(() -> new ModelNotFound("Ordem De Serviço Não Encontrada"));

        OSRepository.delete(ordemServico.get());
    }

    @Override
    public List<OsDto> findAll() {
        List<OsEntity> entities = OSRepository.findAll();

        return entities.stream().map(osEntity -> new OsDto(osEntity)).collect(Collectors.toList());
    }

    @Override
    public void finalizaOs(Long id_ordem_servico, OsEntity osEntity) {

        StatusOrdemServicoEntity statusENCERRADO = statusOrdemServicoService.findById(2L); //faz a busca automatica pelo status encerrado

        Optional<OsEntity> osEntityBanco = OSRepository.findById(id_ordem_servico);
        osEntityBanco.orElseThrow(() -> new ModelNotFound("Ordem De Serviço Não Encontrada"));

        if (osEntityBanco.get().getStatusOrdemServicoEntity() == statusENCERRADO) {
            throw new DataIntegrityViolationException("ORDEM DE SERVIÇO JA ESTA ENCERRADA");

        } else {
            osEntityBanco.get().setStatusOrdemServicoEntity(statusENCERRADO);
            osEntityBanco.get().setDataFechamento(new Date());// coloca a data do fechamento da OrdemServicoEntity assim que a ordemServicoEntity for finalizada pelo usuario
            OSRepository.saveAndFlush(osEntityBanco.get());
            emailService.enviarEmailServicoFinalizado(osEntity); // envia email serviço finalizado
        }


    }


    //CONSULTAR SE O ID DO STATUS DA OrdemServicoEntity É IGUAL A ENCERRADO, CASO FOR NÃO DEIXA O USUARIO EDITAR A OrdemServicoEntity
    private void consultaStatusOs(OsDto osDto) {

        if (osDto.getStatusOrdemServico_id() == 2L) {
            throw new DataIntegrityViolationException("VOÇE NÃO PODE EDITAR UMA OS FINALIZADA");
        }
    }

    private void mapperObject(OsDto osDto, OsEntity osEntity) {

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

        osEntity.getItemServicoOs().clear();
        osEntity.getItemProdutoOs().clear();

        for (OsItemProdutoDto itemProduto : osDto.getProdutos()) {
            Optional<ProdutoEntity> produto = produtoRepository.findById(itemProduto.getProduto_id());
            produto.orElseThrow(() -> new ModelNotFound("Produto Not Found"));

            osEntity.getItemProdutoOs().add(new OsItemProdutoEntity(osEntity, produto.get(), itemProduto.getQuantidade(), produto.get().getPreco()));
        }

        for (OsItemServicoDto itemServico : osDto.getServicos()) {
            Optional<ServicoEntity> servico = servicoRepository.findById(itemServico.getServico_id());
            servico.orElseThrow(() -> new ModelNotFound("Serviço Not Found"));

            osEntity.getItemServicoOs().add(new OsItemServicoEntity(osEntity, servico.get(), itemServico.getQuantidade(), servico.get().getPreco()));
        }

        osEntity.setValorTotalOrdem(osEntity.totalOs());
        OSRepository.save(osEntity);

    }
}
