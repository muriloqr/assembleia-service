package com.murilo.assembleia.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Pauta {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAUTA_SEQ")
    @SequenceGenerator(sequenceName = "pauta_seq", allocationSize = 1, name = "PAUTA_SEQ")
    private Long id;
	
	@Column(name = "nome")
	private String nome;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
