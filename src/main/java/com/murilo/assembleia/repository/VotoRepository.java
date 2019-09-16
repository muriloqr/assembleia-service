package com.murilo.assembleia.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.murilo.assembleia.entity.Sessao;
import com.murilo.assembleia.entity.Voto;

@Repository
public interface VotoRepository extends CrudRepository<Voto, Long>{
	
	public Optional<Voto> findByCpfAndSessao(String cpf, Sessao sessao);
	
	public Long countByVoto(Boolean voto);
	
	public Long countBySessao(Sessao sessao);
}
