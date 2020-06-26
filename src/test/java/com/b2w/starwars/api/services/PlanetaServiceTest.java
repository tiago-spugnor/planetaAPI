package com.b2w.starwars.api.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.b2w.starwars.api.model.Planeta;
import com.b2w.starwars.api.repositories.PlanetaRepository;
import com.b2w.starwars.api.services.exceptions.ObjetoJaCadastradoException;
import com.b2w.starwars.api.services.exceptions.ObjetoNaoEncontradoException;
import com.b2w.starwars.api.services.exceptions.ParametroObrigatorioException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PlanetaServiceTest {

	@Autowired
	private PlanetaRepository repository;
	
	@Autowired
	private PlanetaService service;
	
	
	@BeforeEach
	void eraseDatabase() {
		repository.deleteAll();
		repository.insert(new Planeta("5ef538217189f8051adc4e1d", "Tatooine", "Árido", "Deserto", 5));
	}
	
	@Test
	void adicionarPlaneta() {
		Planeta plnt = new Planeta(null, "Utapau", "Árido", "Savana");
		service.adicionar(plnt);
		
		assertNotNull(plnt.getId()); 		
		
		assertThrows(ObjetoNaoEncontradoException.class, () -> { service.adicionar(new Planeta(null, "Planeta Inexistente", "", "") ); });

		assertThrows(ParametroObrigatorioException.class, () -> { service.adicionar(new Planeta(null, null, "", "") ); });

		assertThrows(ParametroObrigatorioException.class, () -> { service.adicionar(new Planeta(null, "", null, "") ); });
		
		assertThrows(ParametroObrigatorioException.class, () -> { service.adicionar(new Planeta(null, "", "", null) ); });
		
		assertThrows(ObjetoNaoEncontradoException.class, () -> { service.adicionar(new Planeta(null, "Tatooi", "Árido", "Deserto") ); });

		assertThrows(ObjetoJaCadastradoException.class, () -> { service.adicionar(new Planeta(null, "Tatooine", "Árido", "Deserto") ); });
	}
	
	@Test
	void obterTodosPlanetas() {

		List<Planeta> plnts = service.obterTodos();
		
		assertNotNull(plnts.get(0)); 		
		assertEquals(plnts.get(0).getNome(), "Tatooine"); 		
		
	}
	
	
	
	@Test
	void obterPlanetaPorId() {
		Planeta target = new Planeta("5ef538217189f8051adc4e1d", "Tatooine", "Árido", "Deserto", 5);
		Planeta wrongTarget = new Planeta("xxx", "Tatooine", "Árido", "Deserto", 5);

		Planeta plnt = service.obterPorId("5ef538217189f8051adc4e1d");
		
		assertNotNull(plnt); 	
		assertEquals(plnt, target);
		assertEquals(plnt.hashCode(), target.hashCode());
		assertNotEquals(plnt, wrongTarget);
		
		assertThrows(ObjetoNaoEncontradoException.class, () -> { service.obterPorId("xxx"); });
	}
	
	@Test
	void obterPlanetaPorNome() {

		Planeta plnt = service.obterPorNome("Tatooine");
		
		assertNotNull(plnt);
		
		repository.deleteAll();
		
		assertThrows(ObjetoNaoEncontradoException.class, () -> { service.obterPorNome("Tatooine"); });
	}
	
	@Test
	void deletarPlaneta() {
		service.deletar("5ef538217189f8051adc4e1d");		
	}
	
	@Test
	void atualizarPlaneta() {
		Planeta novoPlaneta = new Planeta("5ef538217189f8051adc4e1d", "Tatooine", "Árido", "Deserto Demais", 5);
		
		service.atualizar(novoPlaneta);	
		
		Planeta planetaSalvo = repository.findById("5ef538217189f8051adc4e1d").get();
		
		assertEquals(novoPlaneta, planetaSalvo);
		assertEquals(novoPlaneta.getTerreno(), planetaSalvo.getTerreno());
	}
}
