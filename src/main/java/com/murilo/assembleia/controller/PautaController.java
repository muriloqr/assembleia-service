package com.murilo.assembleia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.murilo.assembleia.dto.ResultadoVotacaoDTO;
import com.murilo.assembleia.entity.Pauta;
import com.murilo.assembleia.exception.BusinessException;
import com.murilo.assembleia.service.PautaService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1")
public class PautaController {
	
	private static final String NOME_INVALIDO = "Nome da pauta é inválido.";
	
	@Autowired
	private PautaService pautaService;
	
	@ApiOperation(value = "Cadastrar nova pauta")
	@PostMapping("/pautas")
    public ResponseEntity<Pauta> cadastrarPauta(@RequestBody Pauta pauta) {
		
		if (!StringUtils.hasText(pauta.getNome())) {
			throw new BusinessException(NOME_INVALIDO);
		}
		
		return ResponseEntity.ok().body(this.pautaService.cadastrarPauta(pauta));
    }
	
	@ApiOperation(value = "Buscar resultado da votação da pauta")
	@GetMapping("/pautas/{id}/resultado")
    public ResponseEntity<ResultadoVotacaoDTO> buscarResultado(@PathVariable Long id) {
		
		return ResponseEntity.ok().body(this.pautaService.buscarResultado(id));
    }

}
