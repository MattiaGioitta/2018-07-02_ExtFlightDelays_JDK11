package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	
	private Graph<Airport,DefaultWeightedEdge> graph;
	private ExtFlightDelaysDAO dao;
	private Map<Integer,Airport> idMap;
	private List<Airport> bestPath;
	private double bestWeight;
	public Model() {
		this.dao = new ExtFlightDelaysDAO();
		this.idMap = new HashMap<>();
	}

	public void createGraph(Integer x) {
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.dao.loadAllAirports(idMap);
		List<Adiacenza> adiacenze = this.dao.getAdiacenze(x,idMap);
		for(Adiacenza  a : adiacenze) {
			if(!this.graph.containsVertex(a.getA1())) {
				this.graph.addVertex(a.getA1());
			}
			if(!this.graph.containsVertex(a.getA2())) {
				this.graph.addVertex(a.getA2());
			}
			if(this.graph.getEdge(a.getA1(),a.getA2())==null) {
				Graphs.addEdgeWithVertices(this.graph, a.getA1(), a.getA2(),a.getPeso());
			}
		}
		
	}

	public Integer nVertici() {
		// TODO Auto-generated method stub
		return this.graph.vertexSet().size();
	}

	public Integer nArchi() {
		// TODO Auto-generated method stub
		return this.graph.edgeSet().size();
	}

	public List<Airport> getVertici() {
		List<Airport> l = new ArrayList<>(this.graph.vertexSet());
		Collections.sort(l);
		return l;
	}

	public List<Vicino> vicini(Airport scelto) {
		List<Vicino> l = new ArrayList<>();
		for(Airport a : Graphs.neighborListOf(this.graph, scelto)) {
			l.add(new Vicino(a,this.graph.getEdgeWeight(this.graph.getEdge(scelto, a))));
		}
		Collections.sort(l);
		return l;
	}

	public void findPath(Airport source, Airport destination, Integer t) {
		List<Airport> parziale = new ArrayList<>();
		this.bestPath = new ArrayList<>();
		this.bestWeight = 0.0;
		parziale.add(source);
		recursive(parziale,destination,t);
		
	}

	private void recursive(List<Airport> parziale, Airport destination, Integer t) {
		//terminal condition
		if(calcolaVoli(parziale)>this.bestWeight && numeroTratte(parziale)<t 
				&& parziale.get(parziale.size()-1).equals(destination)) {
			this.bestPath = new ArrayList<>(parziale);
			this.bestWeight = calcolaVoli(parziale);
			return;
		}
		//recursive method
		for(Airport a : Graphs.neighborListOf(this.graph, parziale.get(parziale.size()-1))) {
			if(!parziale.contains(a)) {
				parziale.add(a);
				recursive(parziale,destination,t);
				parziale.remove(parziale.size()-1);
			}
			
		}
		return;
		
		
	}

	private Integer numeroTratte(List<Airport> parziale) {
		return parziale.size()-1;
	}

	private double calcolaVoli(List<Airport> parziale) {
		double peso = 0.0;
		for(int i = 1;i<parziale.size();i++) {
			peso+=this.graph.getEdgeWeight(this.graph.getEdge(parziale.get(i-1), parziale.get(i)));
		}
		return peso;
	}

	public List<Airport> getPath() {
		// TODO Auto-generated method stub
		return this.bestPath;
	}

	public Double getPeso() {
		// TODO Auto-generated method stub
		return this.bestWeight;
	}

}
