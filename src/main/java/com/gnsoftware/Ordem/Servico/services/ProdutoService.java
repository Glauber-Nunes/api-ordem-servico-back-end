package com.gnsoftware.Ordem.Servico.services;

import com.gnsoftware.Ordem.Servico.dto.ProdutoDto;
import com.gnsoftware.Ordem.Servico.model.ProdutoEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProdutoService {

    ProdutoDto save(ProdutoDto produtoDto, MultipartFile imagem);

    ProdutoDto update(Long id, ProdutoDto produtoDto,MultipartFile imagem);

    ProdutoDto findById(Long id);

    List<ProdutoDto> findAll();

    void delete(Long id);

    Long countProduto();

}
