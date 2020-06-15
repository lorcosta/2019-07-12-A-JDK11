package it.polito.tdp.food.model;

public class Stazione {
	private Boolean libera;
	private Food food;
	/**
	 * @param libera
	 * @param food
	 */
	public Stazione(Boolean libera, Food food) {
		super();
		this.libera = libera;
		this.food = food;
	}
	public Boolean getLibera() {
		return libera;
	}
	public void setLibera(Boolean libera) {
		this.libera = libera;
	}
	public Food getFood() {
		return food;
	}
	public void setFood(Food food) {
		this.food = food;
	}
}
