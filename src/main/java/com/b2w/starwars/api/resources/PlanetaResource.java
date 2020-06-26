package com.b2w.starwars.api.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.b2w.starwars.api.model.Planeta;
import com.b2w.starwars.api.responses.Response;
import com.b2w.starwars.api.services.PlanetaService;

@RestController
@RequestMapping(value = "/planetas")
public class PlanetaResource {

	@Autowired
	private PlanetaService service;
	
	@GetMapping
	public ResponseEntity<Response<List<Planeta>>> findAll() {
		List<Planeta> list = service.obterTodos();
		
		Response<List<Planeta>> response = new Response<List<Planeta>>();
		response.setCount(list.size());
		response.setReturns(list);
		
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping(value = "id/{id}")
	public ResponseEntity<Planeta> findById(@PathVariable String id) {
		Planeta obj = service.obterPorId(id);
		
		return ResponseEntity.ok().body(obj);
	}
	
	@GetMapping(value = "nome/{nome}")
	public ResponseEntity<Planeta> findByNome(@PathVariable String nome) {
		Planeta obj = service.obterPorNome(nome);
		
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody Planeta obj) {
		obj = service.adicionar(obj);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
											.path("/{id}")
											.buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable String id) {
		service.deletar(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@RequestBody Planeta planetaAtualizado, @PathVariable String id) {
		planetaAtualizado.setId(id);
		service.atualizar(planetaAtualizado);
		
		return ResponseEntity.noContent().build();
	}
}
