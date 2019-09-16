package com.murilo.assembleia.service;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.murilo.assembleia.dto.SessaoDTO;
import com.murilo.assembleia.dto.VotoDTO;
import com.murilo.assembleia.entity.Pauta;
import com.murilo.assembleia.entity.Sessao;
import com.murilo.assembleia.exception.BusinessException;
import com.murilo.assembleia.repository.PautaRepository;
import com.murilo.assembleia.repository.SessaoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SessaoServiceTest {

	@Autowired
	private SessaoService sessaoService;
	
	@MockBean
	private SessaoRepository sessaoRepository;
	
	@MockBean
	private PautaRepository pautaRepository;
	
	@Test(expected = BusinessException.class)
	public void pautaNaoCadastrada() {
		SessaoDTO sessaoDTO = new SessaoDTO();
		when(this.pautaRepository.findById(1L)).thenReturn(Optional.empty());
		
		sessaoService.cadastrarSessao(sessaoDTO, 1L);
	}
	
	@Test(expected = BusinessException.class)
	public void sessaoJaCadastrada() {
		SessaoDTO sessaoDTO = new SessaoDTO();
		Optional<Pauta> pauta = getPauta();
		Optional<Sessao> sessao = getSessao();
		
		when(this.pautaRepository.findById(1L)).thenReturn(pauta);
		when(sessaoRepository.findByPauta(pauta.get())).thenReturn(sessao);
		
		sessaoService.cadastrarSessao(sessaoDTO, 1L);
	}
	
	@Test
	public void deveCadastrarSessao() {
		Optional<Pauta> pauta = getPauta();
		Optional<Sessao> sessao = getSessao();
		SessaoDTO sessaoDTO = new SessaoDTO();
		
		sessaoDTO.setMinutosSessao(1L);
		
		when(pautaRepository.findById(1L)).thenReturn(pauta);
		when(sessaoRepository.findByPauta(pauta.get())).thenReturn(Optional.empty());
		when(sessaoRepository.save(sessao.get())).thenReturn(sessao.get());
		
		sessaoService.cadastrarSessao(sessaoDTO, 1L);
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
