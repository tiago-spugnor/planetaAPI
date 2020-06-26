package com.b2w.starwars.api.model;

import java.io.Serializable;
import java.util.List;

public class PlanetaRestResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer count;
	private List<PlanetaRestModel> results;
	
	public boolean isEmpty() {
		return count == 0;
	}
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public List<PlanetaRestModel> getResults() {
		return results;
	}
	public void setResults(List<PlanetaRestModel> results) {
		this.results = results;
	}
	
	
}
