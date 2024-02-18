package com.gnsoftware.Ordem.Servico.services.auxiliar;

import com.gnsoftware.Ordem.Servico.dto.*;
import com.gnsoftware.Ordem.Servico.model.*;
import com.gnsoftware.Ordem.Servico.model.compositekey.ProdutoOrdemEntity;
import com.gnsoftware.Ordem.Servico.model.compositekey.ServicoOrdemEntity;
import com.gnsoftware.Ordem.Servico.repository.*;
import com.gnsoftware.Ordem.Servico.services.StatusOrdemServicoService;
import com.gnsoftware.Ordem.Servico.services.exceptions.DataIntegrityViolationException;
import com.gnsoftware.Ordem.Servico.services.exceptions.ModelNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MapperObjectOsUpdate {
    @Autowired
    com.gnsoftware.Ordem.Servico.repository.OSRepository OSRepository;

    @Autowired
    StatusOrdemServicoService statusOrdemServicoService;

    @Autowired
    ServicoRepository servicoRepository;
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    AtendenteRepository atendenteRepository;
    @Autowired
    SituacaoOrdemRepository situacaoOrdemRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    TecnicoRepository tecnicoRepository;
    @Autowired
    FornecedorRepository fornecedorRepository;

    public void mapperObjectUpdate(OrdemServicoDto ordemServicoDto, OrdemServicoEntity osBancoUpdate) {

        this.atualizaCabecalhoOrdemServico(ordemServicoDto, osBancoUpdate); // chama metodo
        this.atualizaProdutoOrdemServico(ordemServicoDto, osBancoUpdate);// chama metodo
        this.atualizaServicoOrdemServico(ordemServicoDto, osBancoUpdate);// chama metodo
        this.salvarOsAtualizada(osBancoUpdate);// chama metodo
    }

    private void atualizaCabecalhoOrdemServico(OrdemServicoDto dto, OrdemServicoEntity osBancoUpdate) {

        Optional<AtendenteEntity> atendente = atendenteRepository.findById(dto.getAtendente().getId());
        atendente.orElseThrow(() -> new ModelNotFound("Atendente Not Found ID:"));

        Optional<SituacaoOrdemEntity> situacaoOrdem = situacaoOrdemRepository.findById(dto.getSituacaoOrdem().getId());
        situacaoOrdem.orElseThrow(() -> new ModelNotFound("Situação Not Found ID:"));

        Optional<ClienteEntity> cliente = clienteRepository.findById(dto.getCliente().getId());
        cliente.orElseThrow(() -> new ModelNotFound("Cliente Not Found ID:"));

        Optional<TecnicoEntity> tecnico = tecnicoRepository.findById(dto.getTecnico().getId());
        tecnico.orElseThrow(() -> new ModelNotFound("Tecnico Not Found ID:"));

        StatusOrdemServicoEntity statusOrdemServicoEntity = statusOrdemServicoService.findById(1L); // recebe automatico


        osBancoUpdate.setAtendenteEntity(atendente.get());
        osBancoUpdate.setSituacaoOrdemEntity(situacaoOrdem.get());
        osBancoUpdate.setClienteEntity(cliente.get());
        osBancoUpdate.setTecnicoEntity(tecnico.get());
        osBancoUpdate.setDataDoServico(osBancoUpdate.getDataDoServico());
        osBancoUpdate.setObservacoes(dto.getObservacoes());
        osBancoUpdate.setStatusOrdemServicoEntity(statusOrdemServicoEntity);
    }

    private void atualizaProdutoOrdemServico(OrdemServicoDto ordemServicoDto, OrdemServicoEntity osBancoUpdate) {

        for (ProdutoDto produtoDto : ordemServicoDto.getProdutos()) {

            Optional<ProdutoEntity> produto = produtoRepository.findById(produtoDto.getId());
            produto.orElseThrow(() -> new ModelNotFound("Produto Não Encontrado"));

            osBancoUpdate.getProdutos().add(produto.get());
        }


    }

    private void atualizaServicoOrdemServico (OrdemServicoDto ordemServicoDto, OrdemServicoEntity osBancoUpdate){

        for (ServicoDto servicoDto : ordemServicoDto.getServicos()) {
            Optional<ServicoEntity> servico = servicoRepository.findById(servicoDto.getId());
            servico.orElseThrow(() -> new ModelNotFound("Produto Not Found ID:"));
        }

    }

    private void salvarOsAtualizada (OrdemServicoEntity osBancoUpdate){
        osBancoUpdate.setValorTotalOrdem(osBancoUpdate.totalOs());
        OSRepository.save(osBancoUpdate);
    }
}