package com.b2w.starwars.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.b2w.starwars.api.model.Planeta;
import com.b2w.starwars.api.model.PlanetaRestResponse;
import com.b2w.starwars.api.repositories.PlanetaRepository;
import com.b2w.starwars.api.services.exceptions.ObjetoJaCadastradoException;
import com.b2w.starwars.api.services.exceptions.ObjetoNaoEncontradoException;
import com.b2w.starwars.api.services.exceptions.ParametroObrigatorioException;

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
		Optional<Planeta> planeta = repositorio.findById(id);
		return planeta.orElseThrow( () -> new ObjetoNaoEncontradoException("Planeta com id = " + id + " não foi encontrado!"));
	}
	
	public Planeta obterPorNome(String nome) {
		Optional<Planeta> planeta = repositorio.findByNome(nome);		
		return planeta.orElseThrow( () -> new ObjetoNaoEncontradoException("Planeta com nome = '" + nome + "' não foi encontrado!"));
	}
	
	public Planeta adicionar(Planeta planeta) {
		
		if( planeta.getNome() == null ) {
			throw new ParametroObrigatorioException("O atributo 'Nome' é obrigatório!");
		}
		if( planeta.getClima() == null ) {
			throw new ParametroObrigatorioException("O atributo 'Clima' é obrigatório!");
		}
		if( planeta.getTerreno() == null ) {
			throw new ParametroObrigatorioException("O atributo 'Terreno' é obrigatório!");
		}
		
		if( repositorio.findByNome( planeta.getNome()).isPresent() ) {
			throw new ObjetoJaCadastradoException("Planeta com nome '" + planeta.getNome() + "' já está cadastrado!");
		}
		
		PlanetaRestResponse response = restTemplate.getForObject(URI_STARWARS + "?search=" + planeta.getNome() , PlanetaRestResponse.class);
		
		if(!response.isEmpty()) {
			
			if( !response.getResults().get(0).getName().equals(planeta.getNome()) ) {
				throw new ObjetoNaoEncontradoException("Planeta com nome '" + planeta.getNome() + "' não foi encontrado. Você quis dizer '" + response.getResults().get(0).getName() + "'?");
			}
			
			planeta.setNumeroOcorrencias(response.getResults().get(0).getFilms().length);
		} else {
			throw new ObjetoNaoEncontradoException("Planeta com nome '" + planeta.getNome() + "' não foi encontrado em um filme de Star Wars!");			
		}		
		
		return repositorio.insert(planeta); 
	}
	
	public void deletar(String id) {
		this.obterPorId(id);		
		repositorio.deleteById(id);
	}
	
	public void atualizar(Planeta planetaAtualizado) {
		Planeta planetaSalvo = this.obterPorId(planetaAtualizado.getId());
		updatePlaneta(planetaSalvo, planetaAtualizado);
		
		repositorio.save(planetaSalvo);
	}
	
	private void updatePlaneta(Planeta planetaSalvo, Planeta planetaAtualizado) {

		if( planetaAtualizado.getClima() != null) planetaSalvo.setClima(planetaAtualizado.getClima());
		if( planetaAtualizado.getTerreno() != null) planetaSalvo.setTerreno(planetaAtualizado.getTerreno());

	}
}
