package com.b2w.starwars.api.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.b2w.starwars.api.model.Planeta;
import com.b2w.starwars.api.services.PlanetaService;

@RestController
@RequestMapping(value = "/planetas")
public class PlanetaResource {

	@Autowired
	private PlanetaService service;
	
	@GetMapping
	public ResponseEntity<List<Planeta>> findAll() {
		List<Planeta> list = service.obterTodosPlanetas();
		return ResponseEntity.ok().body(list);
	}
}
