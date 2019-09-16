package com.murilo.assembleia.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.murilo.assembleia.dto.CPFValidatorDTO;
import com.murilo.assembleia.dto.VotoDTO;
import com.murilo.assembleia.entity.Pauta;
import com.murilo.assembleia.entity.Sessao;
import com.murilo.assembleia.entity.Voto;
import com.murilo.assembleia.enums.StatusEnum;
import com.murilo.assembleia.exception.BusinessException;
import com.murilo.assembleia.feign.CPFValidatorFeignClient;
import com.murilo.assembleia.repository.PautaRepository;
import com.murilo.assembleia.repository.SessaoRepository;
import com.murilo.assembleia.repository.VotoRepository;

@Service
public class VotoService {
	
	private static final String PAUTA_NAO_ENCONTRADA = "Pauta não encontrada.";
	private static final String SESSAO_NAO_ENCONTRADA = "Sessão não encontrada.";
	private static final String VOTO_INVALIDO = "Voto inválido.";
	private static final String VOTO_CONTABILIZADO = "Associado já votou.";
	private static final String SESSAO_EXPIRADA = "Sessão de votação expirada.";
	private static final String CPF_INVALIDO = "CPF inválido.";
	private static final String CPF_NAO_PERMITIDO = "CPF não permitido para votar.";

	@Autowired
	private VotoRepository votoRepository;
	
	@Autowired
	private SessaoRepository sessaoRepository;
	
	@Autowired
	private PautaRepository pautaRepository;
	
	@Autowired
	private CPFValidatorFeignClient validatorClient;
	
	public Voto cadastrarVoto(Long pautaId, Long sessaoId, VotoDTO votoDTO) {
		if (votoDTO == null || votoDTO.getVoto() == null || votoDTO.getCpf() == null) {
			throw new BusinessException(VOTO_INVALIDO);
		}
		
		try {
			CPFValidatorDTO validator = validatorClient.validateCPF(votoDTO.getCpf());
			
			if (validator.getStatus().equals(StatusEnum.UNABLE_TO_VOTE)) {
				throw new BusinessException(CPF_NAO_PERMITIDO);
			}
		} catch (Exception e) {
			
			throw new BusinessException(CPF_INVALIDO);
		}
		
		Optional<Pauta> pauta = pautaRepository.findById(pautaId);
		
		if (!pauta.isPresent()) {
			throw new BusinessException(PAUTA_NAO_ENCONTRADA);
		}
		
		Optional<Sessao> sessao = sessaoRepository.findByIdAndPauta(sessaoId, pauta.get());
		
		if (!sessao.isPresent()) {
			throw new BusinessException(SESSAO_NAO_ENCONTRADA);
		}
		
		Optional<Voto> votoSessao = votoRepository.findByCpfAndSessao(votoDTO.getCpf(), sessao.get());
		
		if (votoSessao.isPresent()) {
			throw new BusinessException(VOTO_CONTABILIZADO);
		}
		
		if (LocalDateTime.now().isAfter(sessao.get().getDataFim())) {
			throw new BusinessException(SESSAO_EXPIRADA);
		}
		
		Voto voto = new Voto();
		
		voto.setCpf(votoDTO.getCpf());
		voto.setVoto(votoDTO.getVoto());
		voto.setSessao(sessao.get());
		
		return this.votoRepository.save(voto);
	}
}
