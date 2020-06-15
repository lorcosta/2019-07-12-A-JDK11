package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.food.model.Event.Type;
import it.polito.tdp.food.model.Food.StatoPreparazione;

public class Simulator {
	//Modello del mondo
	private List<Stazione> stazioni;
	private List<Food> cibi;
	private Graph<Food,DefaultWeightedEdge> grafo;
	private Model model;
	//Parametri di simulazione
	private Integer k=1;
	//Risultati calcolati
	private Double tempoPreparazione;
	private Integer cibiPreparati;
	//Coda degli eventi
	private PriorityQueue<Event> queue;
	
	
	
	
	public Simulator(Graph<Food,DefaultWeightedEdge> grafo, Model model) {
		this.grafo=grafo;
		this.model=model;
	}
	/**
	 * Inizializza la simulazione passando i parametri necessari
	 * @param k
	 * @param source
	 * @param cibi
	 */
	public void init(Integer k,Food source) {
		this.cibi=new ArrayList<>(this.grafo.vertexSet());
		this.stazioni=new ArrayList<>();
		this.k=k;
		for(Food cibo :cibi) {
			cibo.setPreparazione(StatoPreparazione.DA_PREPARARE);
		}
		for(int i=0;i<this.k;i++) {
			this.stazioni.add(new Stazione(true,null));
		}
		this.tempoPreparazione=0.0;
		this.cibiPreparati=0;
		this.queue= new PriorityQueue<Event>();
		//Inserisco gli eventi iniziali 
		List<FoodCalories> vicini=model.analisiCalorie(source);
		for(int i=0; i<vicini.size() && i<this.k;i++) {
			this.stazioni.get(i).setLibera(false);
			this.stazioni.get(i).setFood(vicini.get(i).getFood());
			this.queue.add(new Event(vicini.get(i).getCalories(),Type.FINE_PREPARAZIONE,
										this.stazioni.get(i),vicini.get(i).getFood()));
		}
		
	}
	public void run() {
		while(!this.queue.isEmpty()) {
			Event e=this.queue.poll();
			processEvent(e);
		}
	}

	private void processEvent(Event e) {
		switch (e.getEvent()){
		case ASSEGNA_CIBI:
			break;
		case FINE_PREPARAZIONE:
			this.cibiPreparati++;
			this.tempoPreparazione=e.getTime();
			e.getStazione().setLibera(true);
			e.getFood().setPreparazione(StatoPreparazione.PREPARATO);
			Event e2=new Event(e.getTime(),Type.INIZIO_PREPARAZIONE,e.getStazione(),e.getFood());
			this.queue.add(e2);
			break;
		case INIZIO_PREPARAZIONE:
			List<FoodCalories> vicini=model.analisiCalorie(e.getFood());
			FoodCalories prossimo=null;
			for(FoodCalories vicino:vicini) {
				if(vicino.getFood().getPreparazione()==StatoPreparazione.DA_PREPARARE) {
					prossimo=vicino;
					break;//non proseguire nel ciclo
				}
			}
			if(prossimo!=null) {
				prossimo.getFood().setPreparazione(StatoPreparazione.IN_CORSO);
				e.getStazione().setLibera(false);
				e.getStazione().setFood(prossimo.getFood());
				e2=new Event(e.getTime()+prossimo.getCalories(),Type.FINE_PREPARAZIONE,
						e.getStazione(),prossimo.getFood());
				this.queue.add(e2);
			}
			break;
		default:
			break;
		}
		
	}
	public Double getTempoPreparazione() {
		return tempoPreparazione;
	}
	public Integer getCibiPreparati() {
		return this.cibiPreparati;
	}
}
