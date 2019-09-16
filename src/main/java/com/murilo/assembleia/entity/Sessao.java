package com.murilo.assembleia.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Sessao {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SESSAO_SEQ")
    @SequenceGenerator(sequenceName = "sessao_seq", allocationSize = 1, name = "SESSAO_SEQ")
    private Long id;
	
	@Column(name = "data_inicio")
	private LocalDateTime dataInicio;
	
	@Column(name = "data_fim")
	private LocalDateTime dataFim;

	@Column(name = "minutos_sessao")
    private Long minutosSessao;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pauta_id", referencedColumnName = "id")
	private Pauta pauta;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDateTime dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDateTime getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDateTime dataFim) {
		this.dataFim = dataFim;
	}

	public Long getMinutosSessao() {
		return minutosSessao;
	}

	public void setMinutosSessao(Long minutosSessao) {
		this.minutosSessao = minutosSessao;
	}

	public Pauta getPauta() {
		return pauta;
	}

	public void setPauta(Pauta pauta) {
		this.pauta = pauta;
	}
}
