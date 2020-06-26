package com.b2w.starwars.api.model;

import java.io.Serializable;

public class PlanetaRestModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String[] films;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getFilms() {
		return films;
	}
	public void setFilms(String[] films) {
		this.films = films;
	}
		
}
