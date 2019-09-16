package com.murilo.assembleia.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

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

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VotoServiceTest {
	
	@Autowired
	private VotoService votoService;
	
	@MockBean
	private VotoRepository votoRepository;
	
	@MockBean
	private PautaRepository pautaRepository;
	
	@MockBean
	private SessaoRepository sessaoRepository;
	
	@MockBean
	private CPFValidatorFeignClient validatorClient;
	
	@Test(expected = BusinessException.class)
	public void votoInvalido() {
		VotoDTO votoDTO = new VotoDTO();
		
		votoService.cadastrarVoto(1L, 1L, votoDTO);
	}
	
	@Test(expected = BusinessException.class)
	public void cpfNaoPodeVotar() {
		VotoDTO votoDTO = getVotoDTO();
		
		CPFValidatorDTO validator = getCPFValidator();
		
		validator.setStatus(StatusEnum.UNABLE_TO_VOTE);
		
		when(validatorClient.validateCPF(votoDTO.getCpf())).thenReturn(validator);
		
		votoService.cadastrarVoto(1L, 1L, votoDTO);
	}
	
	@Test(expected = BusinessException.class)
	public void pautaNaoCadastrada() {
		VotoDTO votoDTO = getVotoDTO();
		
		CPFValidatorDTO validator = getCPFValidator();
		
		when(validatorClient.validateCPF(votoDTO.getCpf())).thenReturn(validator);
		when(this.pautaRepository.findById(1L)).thenReturn(Optional.empty());
		
		votoService.cadastrarVoto(1L, 1L, votoDTO);
	}
	
	@Test(expected = BusinessException.class)
	public void sessaoNaoCadastrada() {
		VotoDTO votoDTO = getVotoDTO();
		
		CPFValidatorDTO validator = getCPFValidator();
		Optional<Pauta> pauta = getPauta();
		
		when(validatorClient.validateCPF(votoDTO.getCpf())).thenReturn(validator);
		when(this.pautaRepository.findById(1L)).thenReturn(pauta);
		when(this.sessaoRepository.findByIdAndPauta(1L, pauta.get())).thenReturn(Optional.empty());
		
		votoService.cadastrarVoto(1L, 1L, votoDTO);
	}
	
	@Test(expected = BusinessException.class)
	public void votoExistente() {
		VotoDTO votoDTO = getVotoDTO();
		Optional<Voto> voto = Optional.of(new Voto());
		
		CPFValidatorDTO validator = getCPFValidator();
		Optional<Pauta> pauta = getPauta();
		Optional<Sessao> sessao = getSessao();
		
		when(validatorClient.validateCPF(votoDTO.getCpf())).thenReturn(validator);
		when(this.pautaRepository.findById(1L)).thenReturn(pauta);
		when(this.sessaoRepository.findByIdAndPauta(1L, pauta.get())).thenReturn(sessao);
		when(this.votoRepository.findByCpfAndSessao(votoDTO.getCpf(), sessao.get())).thenReturn(voto);
		
		votoService.cadastrarVoto(1L, 1L, votoDTO);
	}
	
	@Test(expected = BusinessException.class)
	public void sessaoExpirada() {
		VotoDTO votoDTO = getVotoDTO();
		
		CPFValidatorDTO validator = getCPFValidator();
		Optional<Pauta> pauta = getPauta();
		Optional<Sessao> sessao = getSessao();
		
		sessao.get().setDataFim(LocalDateTime.now().minusHours(2));
		
		when(validatorClient.validateCPF(votoDTO.getCpf())).thenReturn(validator);
		when(this.pautaRepository.findById(1L)).thenReturn(pauta);
		when(this.sessaoRepository.findByIdAndPauta(1L, pauta.get())).thenReturn(sessao);
		when(this.votoRepository.findByCpfAndSessao(votoDTO.getCpf(), sessao.get())).thenReturn(Optional.empty());
		
		votoService.cadastrarVoto(1L, 1L, votoDTO);
	}
	
	@Test
	public void deveCadastrarVoto() {
		VotoDTO votoDTO = getVotoDTO();
		Voto voto = new Voto();
		
		CPFValidatorDTO validator = getCPFValidator();
		Optional<Pauta> pauta = getPauta();
		Optional<Sessao> sessao = getSessao();
		
		when(validatorClient.validateCPF(votoDTO.getCpf())).thenReturn(validator);
		when(this.pautaRepository.findById(1L)).thenReturn(pauta);
		when(this.sessaoRepository.findByIdAndPauta(1L, pauta.get())).thenReturn(sessao);
		when(this.votoRepository.findByCpfAndSessao(votoDTO.getCpf(), sessao.get())).thenReturn(Optional.empty());
		when(this.votoRepository.save(voto)).thenReturn(voto);
		
		votoService.cadastrarVoto(1L, 1L, votoDTO);
	}
	
	VotoDTO getVotoDTO() {
		VotoDTO votoDTO = new VotoDTO();
		
		votoDTO.setCpf("01396422005");
		votoDTO.setVoto(true);
		
		return votoDTO;
	}

	CPFValidatorDTO getCPFValidator() {
		CPFValidatorDTO validator = new CPFValidatorDTO();
		
		validator.setStatus(StatusEnum.ABLE_TO_VOTE);
		
		return validator;
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
