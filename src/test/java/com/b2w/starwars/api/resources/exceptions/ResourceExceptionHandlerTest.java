package com.b2w.starwars.api.resources.exceptions;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.b2w.starwars.api.model.Planeta;
import com.b2w.starwars.api.repositories.PlanetaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ResourceExceptionHandlerTest {

	@Autowired
	private PlanetaRepository repository;
	
    @Autowired
    private MockMvc mvc;
    
    @Test
	public void objectoNaoEncontrado() throws Exception {

		mvc.perform(get("/planetas/nome/xxx"))
			      .andExpect(status().isNotFound())
			      .andExpect(jsonPath("$.error", is("OBJETO NÃO ENCONTRADO!")));
	}
    
    @Test
	public void parametroObrigatorio() throws Exception {

    	mvc.perform(post("/planetas")
			      .contentType(MediaType.APPLICATION_JSON)
			      .content(asJsonString(new Planeta(null, null, null, null) )))
			      .andExpect(status().isBadRequest());
	}

    @Test
    public void objetoJaCadastrado() throws Exception {
    	
    	repository.deleteAll();
		repository.insert(new Planeta("5ef538217189f8051adc4e1d", "Tatooine", "Árido", "Deserto", 5));
    	
    	mvc.perform(post("/planetas")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(asJsonString(new Planeta("5ef538217189f8051adc4e1d", "Tatooine", "Árido", "Deserto"))))
    	.andExpect(status().isConflict());
    }
    
    public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
