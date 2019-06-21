package it.polito.tdp.formulaone.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.formulaone.db.FormulaOneDAO;

public class Model {
	FormulaOneDAO dao;
	SimpleDirectedWeightedGraph<Driver, DefaultWeightedEdge> grafo;
	Map<Integer, Driver> map;
	List<Driver> best;
	int sconfitteBest;
	

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
	public List<Driver> dreamTeam(int K) {
		best= new LinkedList<Driver>();
		sconfitteBest=Integer.MAX_VALUE;
		List<Driver> parziale= new LinkedList<Driver>();
		
		cercaBest(0, parziale, K);
		
		return best;
	}
	private int evaluate(List<Driver> parziale) {
		int sconfitteParziale= 0;
		
		Set<Driver> in = new HashSet<Driver>(parziale);
		Set<Driver> out = new HashSet<Driver>(grafo.vertexSet());
		out.removeAll(in);
		
		for (DefaultWeightedEdge e : grafo.edgeSet()) {
			if (out.contains(grafo.getEdgeSource(e)) && in.contains(grafo.getEdgeTarget(e))) {
				sconfitteParziale += grafo.getEdgeWeight(e);
			}
		}
		return sconfitteParziale;
	}

	private void cercaBest(int step, List<Driver> parziale, int K) {
		List<Driver> candidati= new LinkedList<Driver>(grafo.vertexSet());
		
		
		if(step==K && evaluate(parziale) < sconfitteBest ) {
			best= new LinkedList<Driver>(parziale);
			sconfitteBest= evaluate(parziale);
			return;
		}
		for(Driver d: candidati) {
			if(!parziale.contains(d)){
				parziale.add(d);
				this.cercaBest(step+1, parziale, K);
				parziale.remove(d);
			}
		}
		
		
	}


}
