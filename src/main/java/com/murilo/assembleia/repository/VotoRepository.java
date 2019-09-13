package com.murilo.assembleia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.murilo.assembleia.entity.Voto;

@Repository
public interface VotoRepository extends CrudRepository<Voto, Long>{

}
