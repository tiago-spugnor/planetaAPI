package com.b2w.starwars.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.b2w.starwars.api.model.Planeta;

@Repository
public interface PlanetaRepository extends MongoRepository<Planeta, String>{

	Optional<List<Planeta>> findByNomeContaining(String nome);
}
