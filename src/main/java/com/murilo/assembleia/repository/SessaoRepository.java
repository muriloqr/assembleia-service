package com.murilo.assembleia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.murilo.assembleia.entity.Sessao;

@Repository
public interface SessaoRepository extends CrudRepository<Sessao, Long> {

}
