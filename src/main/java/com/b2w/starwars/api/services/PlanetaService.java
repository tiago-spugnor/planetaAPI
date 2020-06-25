package com.b2w.starwars.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.b2w.starwars.api.model.Planeta;
import com.b2w.starwars.api.repositories.PlanetaRepository;

@Service
public class PlanetaService {

	@Autowired
	private PlanetaRepository repositorio;
	
	public List<Planeta> obterTodosPlanetas() {
		return repositorio.findAll();
	}
}
