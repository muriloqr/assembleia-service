package com.murilo.assembleia.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.murilo.assembleia.entity.Pauta;
import com.murilo.assembleia.entity.Sessao;

@Repository
public interface SessaoRepository extends CrudRepository<Sessao, Long> {
	
	public Optional<Sessao> findByPauta(Pauta pauta);
	
	public Optional<Sessao> findByIdAndPauta(Long id, Pauta pauta);
}
