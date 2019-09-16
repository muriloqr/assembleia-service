package com.murilo.assembleia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.murilo.assembleia.service.SessaoService;
import com.murilo.assembleia.dto.SessaoDTO;
import com.murilo.assembleia.entity.Sessao;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1")
public class SessaoController {
	
	@Autowired
	private SessaoService sessaoService;
	
	@ApiOperation(value = "Criar uma sessão de votação em uma pauta")
	@PostMapping("/pautas/{id}/sessoes")
	public ResponseEntity<Sessao> cadastrarSessao(@RequestBody(required = false) SessaoDTO sessaoDTO,
								@PathVariable Long id) {
		
		return ResponseEntity.ok().body(this.sessaoService.cadastrarSessao(sessaoDTO, id));
	}

}
