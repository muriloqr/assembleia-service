package com.murilo.assembleia.service;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.murilo.assembleia.dto.ResultadoVotacaoDTO;
import com.murilo.assembleia.dto.VotoDTO;
import com.murilo.assembleia.entity.Pauta;
import com.murilo.assembleia.entity.Sessao;
import com.murilo.assembleia.exception.BusinessException;
import com.murilo.assembleia.repository.PautaRepository;
import com.murilo.assembleia.repository.SessaoRepository;
import com.murilo.assembleia.repository.VotoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PautaServiceTest {

	@Autowired
	private PautaService pautaService;
	
	@MockBean
	private PautaRepository pautaRepository;
	
	@MockBean
	private SessaoRepository sessaoRepository;
	
	@MockBean
	private VotoRepository votoRepository;
	
	@MockBean
	private KafkaTemplate<String, ResultadoVotacaoDTO> kafkaTemplate;
	
	@Test(expected = BusinessException.class)
	public void pautaNaoCadastrada() {
		when(this.pautaRepository.findById(1L)).thenReturn(Optional.empty());
		
		pautaService.buscarResultado(1L);
	}
	
	@Test(expected = BusinessException.class)
	public void sessaoNaoCadastrada() {
		Optional<Pauta> pauta = getPauta();
		
		when(this.pautaRepository.findById(1L)).thenReturn(pauta);
		when(this.sessaoRepository.findByIdAndPauta(1L, pauta.get())).thenReturn(Optional.empty());
		
		pautaService.buscarResultado(1L);
	}
	
	@Test(expected = BusinessException.class)
	public void sessaoEmAndamento() {
		Optional<Pauta> pauta = getPauta();
		Optional<Sessao> sessao = getSessao();
		
		when(this.pautaRepository.findById(1L)).thenReturn(pauta);
		when(this.sessaoRepository.findByIdAndPauta(1L, pauta.get())).thenReturn(sessao);
		
		pautaService.buscarResultado(1L);
	}
	
	@Test
	public void deveBuscarResultados() {
		
		Optional<Pauta> pauta = getPauta();
		Optional<Sessao> sessao = getSessao();
		
		sessao.get().setDataFim(LocalDateTime.now().minusHours(2));
		
		when(this.pautaRepository.findById(1L)).thenReturn(pauta);
		when(this.sessaoRepository.findByPauta(pauta.get())).thenReturn(sessao);
		
		pautaService.buscarResultado(1L);
	}
	
	VotoDTO getVotoDTO() {
		VotoDTO votoDTO = new VotoDTO();
		
		votoDTO.setCpf("01396422005");
		votoDTO.setVoto(true);
		
		return votoDTO;
	}

	Optional<Pauta> getPauta() {
		return Optional.of(new Pauta());
	}
	
	Optional<Sessao> getSessao() {
		Optional<Sessao> sessao = Optional.of(new Sessao());
		
		sessao.get().setDataFim(LocalDateTime.now().plusMinutes(59));
		
		return sessao;
	}
	
}
