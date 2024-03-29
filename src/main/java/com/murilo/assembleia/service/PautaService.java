package com.murilo.assembleia.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.murilo.assembleia.dto.ResultadoVotacaoDTO;
import com.murilo.assembleia.entity.Pauta;
import com.murilo.assembleia.entity.Sessao;
import com.murilo.assembleia.exception.BusinessException;
import com.murilo.assembleia.repository.PautaRepository;
import com.murilo.assembleia.repository.SessaoRepository;
import com.murilo.assembleia.repository.VotoRepository;

@Service
public class PautaService {

	private static final String PAUTA_NAO_ENCONTRADA = "Pauta não encontrada.";
	private static final String SESSAO_NAO_ENCONTRADA = "Sessão não encontrada.";
	private static final String SESSAO_EM_ANDAMENTO = "A sessão de votação está em andamento.";
	private static final String TOPICO_KAFKA = "assembleia";
	
	@Autowired
	private PautaRepository pautaRepository;
	
	@Autowired
	private SessaoRepository sessaoRepository;
	
	@Autowired
	private VotoRepository votoRepository;
	
	@Autowired
	private KafkaTemplate<String, ResultadoVotacaoDTO> kafkaTemplate;
	 
	public void sendKafkaMessage(ResultadoVotacaoDTO resultado) {
	    kafkaTemplate.send(TOPICO_KAFKA, resultado);
	}
	
	public Pauta cadastrarPauta(Pauta pauta) {
		return this.pautaRepository.save(pauta);
	}
	
	public ResultadoVotacaoDTO buscarResultado(Long id) {
		Optional<Pauta> pauta = pautaRepository.findById(id);
		
		if (!pauta.isPresent()) {
			throw new BusinessException(PAUTA_NAO_ENCONTRADA);
		}
		
		Optional<Sessao> sessao = sessaoRepository.findByPauta(pauta.get());
		
		if (!sessao.isPresent()) {
			throw new BusinessException(SESSAO_NAO_ENCONTRADA);
		}
		
		if (LocalDateTime.now().isBefore(sessao.get().getDataFim())) {
			throw new BusinessException(SESSAO_EM_ANDAMENTO);
		}
		
		ResultadoVotacaoDTO resultado = new ResultadoVotacaoDTO();
		
		resultado.setVotosSim(votoRepository.countByVotoAndSessao(true, sessao.get()));
		resultado.setVotosNao(votoRepository.countByVotoAndSessao(false, sessao.get()));
		resultado.setTotalVotos(votoRepository.countBySessao(sessao.get()));
		resultado.setSessao(sessao.get());
		
		sendKafkaMessage(resultado);
		
		return resultado;
	}

}
