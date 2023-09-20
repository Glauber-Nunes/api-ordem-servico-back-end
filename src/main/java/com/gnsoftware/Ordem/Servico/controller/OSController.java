package com.gnsoftware.Ordem.Servico.controller;

import com.gnsoftware.Ordem.Servico.dto.OrdemServicoDto;
import com.gnsoftware.Ordem.Servico.services.OSService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/ordem_servicos")
public class OSController {

    @Autowired
    private OSService OSService;

    @GetMapping("/{id}")
    public ResponseEntity<OrdemServicoDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(OSService.findById(id));
    }

    @PostMapping
    public ResponseEntity<OrdemServicoDto> save(@RequestBody OrdemServicoDto OrdemServicoDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(OSService.save(OrdemServicoDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdemServicoDto> update(@PathVariable Long id, @RequestBody OrdemServicoDto ordemServicoDto) {
        return ResponseEntity.status(HttpStatus.OK).body(OSService.update(id, ordemServicoDto));
    }

    @GetMapping
    public ResponseEntity<List<OrdemServicoDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(OSService.findAll());
    }

    @PostMapping("/finalizar-servico/{id}")
    public ResponseEntity<String> finalizaOs(@PathVariable Long id) {
        OSService.finalizaOs(id);

        JSONObject response = new JSONObject();
        response.put("message", "FINALIZADO COM SUCESSO");

        return ResponseEntity.status(HttpStatus.OK).body(response.toString());
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> delete(@PathVariable Long id) {
        OSService.delete(id);

        JSONObject response = new JSONObject();
        response.put("message", "EXCLUIDO COM SUCESSO");

        return ResponseEntity.status(HttpStatus.OK).body(response.toString());
    }
}
