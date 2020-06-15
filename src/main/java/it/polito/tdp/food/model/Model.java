package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	private Graph<Food,DefaultWeightedEdge> graph;
	private FoodDao dao=new FoodDao();
	private List<Food> cibi=null;
	
	public void creaGrafo(Integer porzioni) {
		this.graph=new SimpleWeightedGraph<Food, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		cibi=dao.listFoodVertices(porzioni);
		Graphs.addAllVertices(this.graph, cibi);
		
		
		for(Food f1:cibi) {
			for(Food f2:cibi) {
				if(!f1.equals(f2) && f1.getFood_code()<f2.getFood_code()) {
					Double peso=dao.calcolaCalorieCongiunte(f1, f2);
					if(peso!=null) {
						Graphs.addEdgeWithVertices(this.graph, f1, f2, peso);
					}
				}
			}
		}
	}
	
	public List<Food> getCibi(){
		return cibi;
	}

	public Integer getNumVertici() {
		return this.graph.vertexSet().size();
	}
	public Integer getNumArchi() {
		return this.graph.edgeSet().size();
	}
	/**
	 * Ritorna una lista ordinata in ordine crescente di peso dei vicini di un cibo
	 * @param source
	 * @return lista ordinata crescente di peso dei vicini di source
	 */
	public List<FoodCalories> analisiCalorie(Food source){
		List<FoodCalories> list=new ArrayList<>();
		List<Food> vicini= new ArrayList<>(Graphs.neighborListOf(this.graph, source));
		for(Food vicino:vicini) {
			Double peso=this.graph.getEdgeWeight(this.graph.getEdge(source, vicino));
			list.add(new FoodCalories(vicino,peso));
		}
		Collections.sort(list);
		return list;
	}

	public String simula(Integer k,Food source) {
		Simulator sim=new Simulator(this.graph, this);
		sim.init(k,source);
		sim.run();
		String messaggio=String.format("Preparati %d cibi in %f minuti", sim.getCibiPreparati(),sim.getTempoPreparazione());
		return messaggio;
	}
}
