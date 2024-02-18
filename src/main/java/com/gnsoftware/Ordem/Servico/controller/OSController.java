package com.gnsoftware.Ordem.Servico.controller;

import com.gnsoftware.Ordem.Servico.dto.OrdemServicoDto;
import com.gnsoftware.Ordem.Servico.model.OrdemServicoEntity;
import com.gnsoftware.Ordem.Servico.services.OSService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/ordem_servicos")
public class OSController {

    @Autowired
    private OSService OSService;

    @PreAuthorize("hasAnyRole('ROLE_ADM')")
    @GetMapping("/{id}")
    public ResponseEntity<OrdemServicoDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(OSService.findById(id));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADM')")
    @PostMapping
    public ResponseEntity<OrdemServicoDto> save(@RequestBody OrdemServicoDto OrdemServicoDto) {
        System.out.println("Recebendo Ordem de Serviço no servidor: " + OrdemServicoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(OSService.save(OrdemServicoDto));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADM')")
    @PutMapping("/{id}")
    public ResponseEntity<OrdemServicoDto> update(@PathVariable Long id, @RequestBody OrdemServicoDto ordemServicoDto) {
        return ResponseEntity.status(HttpStatus.OK).body(OSService.update(id, ordemServicoDto));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADM')")
    @GetMapping
    public ResponseEntity<List<OrdemServicoDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(OSService.findAll());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADM')")
    @PutMapping("/finalizar-servico/{id_os}")
    public ResponseEntity<String> finalizaOs(@PathVariable Long id_os, @RequestBody OrdemServicoEntity ordemServico) {

        OSService.finalizaOs(id_os, ordemServico);
        JSONObject response = new JSONObject();
        response.put("message", "FINALIZDO COM SUCESSO");
        return ResponseEntity.status(HttpStatus.OK).body(response.toString());


    }

    @PreAuthorize("hasAnyRole('ROLE_ADM')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        OSService.delete(id);

        JSONObject response = new JSONObject();
        response.put("message", "EXCLUIDO COM SUCESSO");

        return ResponseEntity.status(HttpStatus.OK).body(response.toString());
    }

}
