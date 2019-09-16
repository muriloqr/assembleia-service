package com.murilo.assembleia.dto;

import com.murilo.assembleia.entity.Sessao;

public class ResultadoVotacaoDTO {
	
	private Long votosSim;	
	private Long votosNao;
	private Long totalVotos;
	private Sessao sessao;
	
	public Long getVotosSim() {
		return votosSim;
	}
	
	public void setVotosSim(Long votosSim) {
		this.votosSim = votosSim;
	}
	
	public Long getVotosNao() {
		return votosNao;
	}
	
	public void setVotosNao(Long votosNao) {
		this.votosNao = votosNao;
	}
	
	public Long getTotalVotos() {
		return totalVotos;
	}
	
	public void setTotalVotos(Long totalVotos) {
		this.totalVotos = totalVotos;
	}

	public Sessao getSessao() {
		return sessao;
	}

	public void setSessao(Sessao sessao) {
		this.sessao = sessao;
	}
}
