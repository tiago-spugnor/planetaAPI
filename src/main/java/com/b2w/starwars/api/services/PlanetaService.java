package com.b2w.starwars.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.b2w.starwars.api.model.Planeta;
import com.b2w.starwars.api.model.PlanetaRestResponse;
import com.b2w.starwars.api.repositories.PlanetaRepository;

@Service
public class PlanetaService {

	private static final String URI_STARWARS = "https://swapi.dev/api/planets/";
	
	@Autowired
	private PlanetaRepository repositorio;
	
	@Autowired
    private RestTemplate restTemplate;
	
	public List<Planeta> obterTodos() {
		return repositorio.findAll();
	}
	
	public Planeta obterPorId(String id) {
		Optional<Planeta> user = repositorio.findById(id);
		return user.orElseThrow( () -> new RuntimeException("Objeto não encontrado!"));
	}
	
	public List<Planeta> obterPorNome(String nome) {
		Optional<List<Planeta>> user = repositorio.findByNomeContaining(nome);
		return user.orElseThrow( () -> new RuntimeException("Objeto não encontrado!"));
	}
	
	public Planeta adicionar(Planeta obj) {
		
		PlanetaRestResponse response = restTemplate.getForObject(URI_STARWARS + "?search=" + obj.getNome() , PlanetaRestResponse.class);
		
		if(!response.isEmpty()) {
			obj.setNumeroOcorrencias(response.getResults().get(0).getFilms().length);
		} else {
			//ERRO: Planeta não encontrado			
		}		
		
		return repositorio.insert(obj); // PROVISÓRIO
	}
	
	public void deletar(String id) {
		this.obterPorId(id);
		repositorio.deleteById(id);
	}
}
