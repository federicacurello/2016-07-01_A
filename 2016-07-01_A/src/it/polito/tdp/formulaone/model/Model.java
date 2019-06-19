package it.polito.tdp.formulaone.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.formulaone.db.FormulaOneDAO;

public class Model {
	FormulaOneDAO dao;
	SimpleDirectedWeightedGraph<Driver, DefaultWeightedEdge> grafo;
	Map<Integer, Driver> map;
	

	public Model() {
		this.dao = new FormulaOneDAO();
		
		map= new HashMap<Integer, Driver>();
	}


	public List<Integer> anni(){
		return dao.getAllYearsOfRace();
	}


	public void creaGrafo(Integer anno) {
		grafo= new SimpleDirectedWeightedGraph<Driver, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, dao.trovaVertici(anno, map));
		for(Connessione c: dao.trovaArchi(anno, map)) {
			Graphs.addEdge(grafo, c.getVincente(), c.getPerdente(), c.getPeso());
		}
		System.out.println("Vertici: "+grafo.vertexSet().size());
		System.out.println("Archi: "+grafo.edgeSet().size());
	}
	public Driver trovaMigliore() {
		
		double max=0;
		Driver best=null;
		for( Driver d : grafo.vertexSet()) {
			double diff=0;
			for(Driver e: Graphs.successorListOf(grafo, d)) {
				diff=  (diff+ grafo.getEdgeWeight(grafo.getEdge(d, e))); 
			}
			for(Driver e: Graphs.predecessorListOf(grafo, d)) {
				diff=  (diff- grafo.getEdgeWeight(grafo.getEdge(e, d))); 
			}
			if(diff>max) {
				best=d;
				max=diff;
				best.setPunti(max);
			}
		}
		return best;
	}

}
