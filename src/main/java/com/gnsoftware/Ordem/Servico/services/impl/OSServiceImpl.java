package com.gnsoftware.Ordem.Servico.services.impl;

import com.gnsoftware.Ordem.Servico.dto.OsDto;
import com.gnsoftware.Ordem.Servico.dto.OsProdutoDto;
import com.gnsoftware.Ordem.Servico.dto.OsServicoDto;
import com.gnsoftware.Ordem.Servico.model.*;
import com.gnsoftware.Ordem.Servico.repository.*;
import com.gnsoftware.Ordem.Servico.services.*;
import com.gnsoftware.Ordem.Servico.services.auxiliar.MapperObjectOsSave;
import com.gnsoftware.Ordem.Servico.services.auxiliar.MapperObjectOsUpdate;
import com.gnsoftware.Ordem.Servico.services.exceptions.DataIntegrityViolationException;
import com.gnsoftware.Ordem.Servico.services.exceptions.ModelNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    private OsItemProdutoEntityRepository osItemProdutoEntityRepository;

    @Autowired
    private OsItemServicoEntityRepository osItemServicoEntityRepository;

    @Autowired
    private MapperObjectOsUpdate mapperObjectOs;

    @Autowired
    private MapperObjectOsSave mapperObjectOsSave;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Override
    @Transactional
    public OsDto save(OsDto osDto) {

        OsEntity osEntity = new OsEntity();

        mapperObjectOsSave.mapperObjectSave(osDto, osEntity);
        //emailService.enviarEmailOSAberta(osEntity.getClienteEntity(), osDto); //envia email para o clienteEntity

        return new OsDto(osEntity, osEntity.getItemServicoOs(), osEntity.getItemProdutoOs());

    }

    @Override
    @Transactional
    public OsDto update(Long id_os, OsDto osDto) {

        Optional<OsEntity> osBancoUpdate = OSRepository.findById(id_os);
        osBancoUpdate.orElseThrow(() -> new ModelNotFound("Ordem Serviço Not Found"));

        for (OsProdutoDto osItem : osDto.getProdutos()) {
            Optional<OsProdutoEntity> itemProduto = osItemProdutoEntityRepository.findById(osItem.getId());
            itemProduto.orElseThrow(() -> new ModelNotFound("Not Found Item"));

            for (OsServicoDto osItemServico : osDto.getServicos()) {
                Optional<OsServicoEntity> itemServico = osItemServicoEntityRepository.findById(osItemServico.getId());
                itemServico.orElseThrow(() -> new ModelNotFound("Not Found Item Serviço"));

                mapperObjectOs.mapperObjectUpdate(osDto, osBancoUpdate.get(), itemProduto.get(), itemServico.get());

            }
        }

        return new OsDto(osBancoUpdate.get(), osBancoUpdate.get().getItemServicoOs(), osBancoUpdate.get().getItemProdutoOs());
    }

    @Override
    public OsDto findById(Long id) {
        Optional<OsEntity> ordemServico = OSRepository.findById(id);

        ordemServico.orElseThrow(() -> new ModelNotFound("Ordem De Serviço Não Encontrada"));

        return new OsDto(ordemServico.get(), ordemServico.get().getItemServicoOs(), ordemServico.get().getItemProdutoOs());
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
    public void finalizaOs(Long id_ordem_servico) {


        StatusOrdemServicoEntity statusENCERRADO = statusOrdemServicoService.findById(2L); //faz a busca automatica pelo status encerrado

        Optional<OsEntity> osEntityBanco = OSRepository.findById(id_ordem_servico);
        osEntityBanco.orElseThrow(() -> new ModelNotFound("Ordem De Serviço Não Encontrada"));

        if (osEntityBanco.get().getStatusOrdemServicoEntity() == statusENCERRADO) {
            throw new DataIntegrityViolationException("ORDEM DE SERVIÇO JA ESTA ENCERRADA");

        } else {
            osEntityBanco.get().setStatusOrdemServicoEntity(statusENCERRADO);
            osEntityBanco.get().setDataFechamento(new Date());
            this.descontaEstoqueProduto(osEntityBanco.get());
            OSRepository.save(osEntityBanco.get());
            // emailService.enviarEmailServicoFinalizado(osEntity); // envia email serviço finalizado
        }

    }

    //CONSULTAR SE O ID DO STATUS DA OrdemServicoEntity É IGUAL A ENCERRADO, CASO FOR NÃO DEIXA O USUARIO EDITAR A OrdemServicoEntity
    private void consultaStatusOs(OsDto osDto) {

        if (osDto.getStatusOrdemServico_id() == 2L) {
            throw new DataIntegrityViolationException("VOÇE NÃO PODE EDITAR UMA OS FINALIZADA");
        }
    }

    private void descontaEstoqueProduto(OsEntity os) {

        for (OsProdutoEntity produto : os.getItemProdutoOs()) {
            double quantidadeParaSubtrair = produto.getQuantidade(); // Obtém a quantidade a ser subtraída
            double estoqueAtual = produto.getProdutoEntity().getEstoque(); // Obtém o estoque atual

            produto.getProdutoEntity().setEstoque(estoqueAtual - quantidadeParaSubtrair);

            produtoRepository.save(produto.getProdutoEntity());
        }

    }
}
