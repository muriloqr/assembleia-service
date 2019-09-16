package com.murilo.assembleia.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Voto {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VOTO_SEQ")
    @SequenceGenerator(sequenceName = "voto_seq", allocationSize = 1, name = "VOTO_SEQ")
    private Long id;
	
	@Column(name = "cpf")
	private String cpf;
	
	@Column(name = "voto")
	private Boolean voto;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sessao_id")
    private Sessao sessao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Boolean getVoto() {
		return voto;
	}

	public void setVoto(Boolean voto) {
		this.voto = voto;
	}

	public Sessao getSessao() {
		return sessao;
	}

	public void setSessao(Sessao sessao) {
		this.sessao = sessao;
	}

}
