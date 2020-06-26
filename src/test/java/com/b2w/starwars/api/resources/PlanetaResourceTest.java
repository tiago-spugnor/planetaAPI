package com.b2w.starwars.api.resources;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.b2w.starwars.api.model.Planeta;
import com.b2w.starwars.api.repositories.PlanetaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class PlanetaResourceTest {

	@Autowired
	private PlanetaRepository repository;
	
    @Autowired
    private MockMvc mvc;
    
	@BeforeEach
	void eraseDatabase() {
		repository.deleteAll();
		repository.insert(new Planeta("5ef538217189f8051adc4e1d", "Tatooine", "Árido", "Deserto", 5));
	}
	
	@Test
	public void getTodosPlanetas() throws Exception {

		mvc.perform(get("/planetas")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$.count", is(1)))
			      .andExpect(jsonPath("$.returns[0].nome", is("Tatooine")));
	}
	
	@Test
	public void getTodosPlanetasComSearch() throws Exception {

		mvc.perform(get("/planetas?search=" + "Tat")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$.count", is(1)))
			      .andExpect(jsonPath("$.returns[0].nome", is("Tatooine")));
	}
	
	@Test
	public void getPlanetaPorId() throws Exception {

		mvc.perform(get("/planetas/id/" + "5ef538217189f8051adc4e1d")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$.id", is("5ef538217189f8051adc4e1d")))
			      .andExpect(jsonPath("$.nome", is("Tatooine")));
	}
	
	@Test
	public void getPlanetaPorNome() throws Exception {

		mvc.perform(get("/planetas/nome/" + "Tatooine")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$.id", is("5ef538217189f8051adc4e1d")))
			      .andExpect(jsonPath("$.nome", is("Tatooine")));
	}
	
	@Test
	public void deletePlaneta() throws Exception {

		mvc.perform(delete("/planetas/" + "5ef538217189f8051adc4e1d")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isNoContent());
	}
	
	@Test
	public void postPlaneta() throws Exception {

		repository.deleteAll();
		
		mvc.perform(post("/planetas")
			      .contentType(MediaType.APPLICATION_JSON)
			      .content(asJsonString(new Planeta(null, "Tatooine", "Árido", "Deserto") )))
			      .andExpect(status().isCreated());
	}
	
	@Test
	public void putPlaneta() throws Exception {
		
		mvc.perform(put("/planetas/" + "5ef538217189f8051adc4e1d" )
			      .contentType(MediaType.APPLICATION_JSON)
			      .content(asJsonString(new Planeta(null, "Tatooine", "Árido", "Deserto Demais") )))
			      .andExpect(status().isNoContent());
		
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

}
