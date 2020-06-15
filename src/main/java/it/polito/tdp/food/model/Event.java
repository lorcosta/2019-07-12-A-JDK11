package it.polito.tdp.food.model;

public class Event implements Comparable<Event>{
	public enum Type{
		INIZIO_PREPARAZIONE,//viene assegnato un cibo ad una stazione
		FINE_PREPARAZIONE,//la stazione ha completato la preparazione di un cibo
		ASSEGNA_CIBI, 
	}
	private Double time;//tempo in minuti
	private Food food;
	private Stazione stazione;
	private Type event;
	/**
	 * @param time
	 * @param food
	 */
	public Event(Double time, Type event,Stazione stazione,Food food) {
		super();
		this.time = time;
		this.food = food;
		this.event=event;
		this.stazione=stazione;
	}
	public Double getTime() {
		return time;
	}
	
	public Food getFood() {
		return food;
	}
	
	public Type getEvent() {
		return event;
	}
	public Stazione getStazione() {
		return this.stazione;
	}
	@Override
	public int compareTo(Event other) {
		return this.time.compareTo(other.getTime());
	}
	
	
}

