package com.gnsoftware.Ordem.Servico.services.impl;

import com.gnsoftware.Ordem.Servico.dto.FornecedorDto;
import com.gnsoftware.Ordem.Servico.model.FornecedorEntity;
import com.gnsoftware.Ordem.Servico.model.UfEntity;
import com.gnsoftware.Ordem.Servico.repository.FornecedorRepository;
import com.gnsoftware.Ordem.Servico.repository.UfRepository;
import com.gnsoftware.Ordem.Servico.services.FornecedorService;
import com.gnsoftware.Ordem.Servico.services.exceptions.DataIntegrityViolationException;
import com.gnsoftware.Ordem.Servico.services.exceptions.ModelNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FornecedorServiceImpl implements FornecedorService {

    @Autowired
    FornecedorRepository fornecedorRepository;
    @Autowired
    UfRepository ufRepository;

    @Override
    public FornecedorDto save(FornecedorDto dto) {

        this.existsByCnpj(dto);
        FornecedorEntity entity = new FornecedorEntity();

        return this.mapperObject(entity, dto);

    }


    @Override
    public FornecedorDto update(Long id, FornecedorDto dto) {

        Optional<FornecedorEntity> entityBanco = fornecedorRepository.findById(id);
        entityBanco.orElseThrow(() -> new ModelNotFound("Fornecedor Not Found"));

        return this.mapperObject(entityBanco.get(), dto);
    }

    @Override
    public void delete(Long id) {
        if (fornecedorRepository.existsById(id)) {
            fornecedorRepository.deleteById(id);
        } else {
            throw new ModelNotFound("Fornecedor Not Found");
        }

    }

    @Override
    public FornecedorDto findById(Long id) {
        Optional<FornecedorEntity> fornecedor = fornecedorRepository.findById(id);

        fornecedor.orElseThrow(() -> new ModelNotFound("Fornecedor Not Found"));

        return new FornecedorDto(fornecedor.get());
    }

    @Override
    public List<FornecedorDto> findAll() {
        List<FornecedorEntity> entities = fornecedorRepository.findAll();

        return entities.stream().map(entity -> new FornecedorDto(entity)).collect(Collectors.toList());
    }

    private FornecedorDto mapperObject(FornecedorEntity entity, FornecedorDto dto) {

        entity.setNome(dto.getNome());
        entity.setMunicipio(dto.getMunicipio());
        entity.setCnpj(dto.getCnpj());

        Optional<UfEntity> ufEntity = ufRepository.findById(dto.getUf().getId());
        ufEntity.orElseThrow(() -> new ModelNotFound("UF NOT FOUND"));
        entity.setUfEntity(ufEntity.get());

        fornecedorRepository.save(entity);

        return new FornecedorDto(entity);
    }

    private void existsByCnpj(FornecedorDto fornecedorDto) {
        if (fornecedorRepository.existsByCnpj(fornecedorDto.getCnpj())) {
            throw new DataIntegrityViolationException("CNPJ Ja Cadastrado na base de dados");
        }
    }
}
