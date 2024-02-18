package com.gnsoftware.Ordem.Servico.services.impl;

import com.gnsoftware.Ordem.Servico.dto.ProdutoDto;
import com.gnsoftware.Ordem.Servico.model.ProdutoEntity;
import com.gnsoftware.Ordem.Servico.repository.ProdutoRepository;
import com.gnsoftware.Ordem.Servico.services.ProdutoService;
import com.gnsoftware.Ordem.Servico.services.exceptions.ModelNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrudutoServiceImpl implements ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;


    @PreAuthorize("hasAnyRole('ROLE_ADM')")
    @Override
    @Transactional
    public ProdutoDto save(ProdutoDto dto) {

        ProdutoEntity entity = new ProdutoEntity();

        return this.mapperObject(dto, entity);

    }

    @PreAuthorize("hasAnyRole('ROLE_ADM')")
    @Override
    @Transactional
    public ProdutoDto update(Long id, ProdutoDto dto) {

        Optional<ProdutoEntity> entityBanco = produtoRepository.findById(id);
        entityBanco.orElseThrow(() -> new ModelNotFound("Produto Not Found ID:" + id));

        return this.mapperObject(dto, entityBanco.get());
    }
    @PreAuthorize("hasAnyRole('ROLE_ADM')")
    @Override
    public ProdutoDto findById(Long id) {

        Optional<ProdutoEntity> produto = produtoRepository.findById(id);

        produto.orElseThrow(() -> new ModelNotFound("Produto Not Found ID:" + id));

        return new ProdutoDto(produto.get());
    }
    @PreAuthorize("hasAnyRole('ROLE_ADM')")
    @Override
    public List<ProdutoDto> findAll() {
        List<ProdutoEntity> entities = produtoRepository.findAll();

        return entities.stream().map(produto -> new ProdutoDto(produto)).collect(Collectors.toList());
    }
    @PreAuthorize("hasAnyRole('ROLE_ADM')")
    @Override
    public void delete(Long id) {

        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
        } else {
            throw new ModelNotFound("Produto Not Found ID:" + id);
        }

    }

    private ProdutoDto mapperObject(ProdutoDto dto, ProdutoEntity entity) {

        entity.setDescricao(dto.getDescricao());
        entity.setPreco(dto.getPreco());
        entity.setCodeBarras(dto.getCodeBarras());
        entity.setUnEntrada(dto.getUnEntrada());
        entity.setUnSaida(dto.getUnSaida());
        entity.setEstoque(dto.getEstoque());
        entity.setCodigoNcm(dto.getCodigoNcm());

        produtoRepository.save(entity);
        return new ProdutoDto(entity);
    }
}
