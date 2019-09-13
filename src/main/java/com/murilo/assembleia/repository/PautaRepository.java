package com.murilo.assembleia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.murilo.assembleia.entity.Pauta;

@Repository
public interface PautaRepository extends CrudRepository<Pauta, Long> {

}
