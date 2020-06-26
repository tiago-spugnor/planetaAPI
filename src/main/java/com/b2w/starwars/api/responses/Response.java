package com.b2w.starwars.api.responses;

public class Response<T> {

	private Integer count;
	private T results;

	public Response() {
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public T getReturns() {
		return results;
	}

	public void setReturns(T results) {
		this.results = results;
	}
	
}
