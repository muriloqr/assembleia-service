package com.murilo.assembleia.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.murilo.assembleia.dto.SessaoDTO;
import com.murilo.assembleia.entity.Pauta;
import com.murilo.assembleia.entity.Sessao;
import com.murilo.assembleia.exception.BusinessException;
import com.murilo.assembleia.repository.PautaRepository;
import com.murilo.assembleia.repository.SessaoRepository;

@Service
public class SessaoService {
	
	private static final String PAUTA_NAO_ENCONTRADA = "Pauta não encontrada.";
	private static final String SESSAO_JA_EXISTENTE = "Já existe uma sessão para esta pauta.";
	
	@Autowired
	private SessaoRepository sessaoRepository;
	
	@Autowired
	private PautaRepository pautaRepository;
	
	public Sessao cadastrarSessao(SessaoDTO sessaoDTO, Long pautaId) {
		Optional<Pauta> pauta = pautaRepository.findById(pautaId);
		
		if (!pauta.isPresent()) {
			throw new BusinessException(PAUTA_NAO_ENCONTRADA);
		}
		
		Optional<Sessao> sessaoAntiga = sessaoRepository.findByPauta(pauta.get());
		
		if (sessaoAntiga.isPresent()) {
			throw new BusinessException(SESSAO_JA_EXISTENTE);
		}
		
		LocalDateTime dataAtual = LocalDateTime.now();
		Sessao sessao = new Sessao();
		Long minutosSessao = 1L;
		
		if (sessaoDTO != null && sessaoDTO.getMinutosSessao() != null) {
			minutosSessao = sessaoDTO.getMinutosSessao();
		}

		sessao.setDataInicio(dataAtual);
		sessao.setDataFim(dataAtual.plusMinutes(minutosSessao));
		sessao.setMinutosSessao(minutosSessao);
		sessao.setPauta(pauta.get());
		
		return sessaoRepository.save(sessao);
	}
}
