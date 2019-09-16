package com.murilo.assembleia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.murilo.assembleia.dto.VotoDTO;
import com.murilo.assembleia.entity.Voto;
import com.murilo.assembleia.service.VotoService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1")
public class VotoController {
	
	@Autowired
	private VotoService votoService;
	
	@ApiOperation(value = "Cadastrar voto em uma sessão")
	@PostMapping("/pautas/{pautaId}/sessoes/{sessaoId}/votos")
	public ResponseEntity<Voto> createVoto(@RequestBody VotoDTO votoDTO,
										   @PathVariable Long pautaId, 
										   @PathVariable Long sessaoId) {
		
		return ResponseEntity.ok().body(this.votoService.cadastrarVoto(pautaId, sessaoId, votoDTO));
	}
}
