package com.gnsoftware.Ordem.Servico.services.impl;

import com.gnsoftware.Ordem.Servico.dto.ClienteDto;
import com.gnsoftware.Ordem.Servico.model.ClienteEntity;
import com.gnsoftware.Ordem.Servico.model.enums.Perfil;
import com.gnsoftware.Ordem.Servico.repository.ClienteRepository;
import com.gnsoftware.Ordem.Servico.services.ClienteService;
import com.gnsoftware.Ordem.Servico.services.exceptions.DataIntegrityViolationException;
import com.gnsoftware.Ordem.Servico.services.exceptions.ModelNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    @Transactional
    public ClienteDto save(ClienteDto dto) {

        this.findByExistsCPF(dto); // verifica se o cpf ja esta cadastrado
        this.findByExistsEmail(dto); // verifica se o email ja esta cadastrado

        ClienteEntity entity = new ClienteEntity();

        return this.mapperObject(entity, dto);

    }

    @Override
    @Transactional
    public ClienteDto update(Long id, ClienteDto dto) {

        Optional<ClienteEntity> entityBanco = clienteRepository.findById(id);
        entityBanco.orElseThrow(() -> new ModelNotFound("Cliente Not Found"));

        return this.mapperObject(entityBanco.get(), dto);

    }

    @Override
    public ClienteDto findById(Long id) {

        Optional<ClienteEntity> cliente = clienteRepository.findById(id);

        cliente.orElseThrow(() -> new ModelNotFound("CLIENTE NÃO ENCONTRADO"));

        return new ClienteDto(cliente.get());
    }

    @Override
    public List<ClienteDto> findAll() {
        List<ClienteEntity> entities = clienteRepository.findAll();

        return entities.stream().map(entity -> new ClienteDto(entity)).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {

        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
        } else {
            throw new ModelNotFound("CLIENTE Not Found");
        }
    }

    private ClienteDto mapperObject(ClienteEntity entity, ClienteDto dto) {

        entity.setNome(dto.getNome());
        entity.setCpf(dto.getCpf());
        entity.setRg(dto.getRg());
        entity.setTelefone(dto.getTelefone());
        entity.setEmail(dto.getEmail());
        entity.setEndereco(dto.getEndereco());
        entity.setPerfil(Perfil.CLIENTE);
        clienteRepository.save(entity);

        return new ClienteDto(entity);
    }

    private void findByExistsCPF(ClienteDto clienteDto) {
        if (clienteRepository.existsByCpf(clienteDto.getCpf())) {
            throw new DataIntegrityViolationException("CPF Ja Cadastrado na base de dados");
        }
    }

    private void findByExistsEmail(ClienteDto clienteDto) {
        if (clienteRepository.existsByemail(clienteDto.getEmail())) {
            throw new DataIntegrityViolationException("Email Ja Cadastrado na base de dados");
        }
    }
}
