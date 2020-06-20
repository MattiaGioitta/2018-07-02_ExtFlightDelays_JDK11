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

	private ExtFlightDelaysDAO dao;
	private Graph<Airport,DefaultWeightedEdge> graph;
	private Map<Integer,Airport> idMap;
	
	public Model() {
		this.dao = new ExtFlightDelaysDAO();
		this.idMap = new HashMap<>();
	}
	
	
	public void createGraph(Integer x) {
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.dao.loadAllAirports(this.idMap);
		List<Adiacenza> adiacenze = this.dao.getAdiacenze(x,idMap);
		for(Adiacenza a : adiacenze) {
			if(!this.graph.containsVertex(a.getA1())) {
				this.graph.addVertex(a.getA1());
			}
			if(!this.graph.containsVertex(a.getA2())) {
				this.graph.addVertex(a.getA2());
			}
			if(this.graph.getEdge(a.getA1(), a.getA2()) == null) {
				Graphs.addEdgeWithVertices(this.graph, a.getA1(), a.getA2(),a.getPeso());
			}
		}		
	}


	public Integer nVertici() {
		return this.graph.vertexSet().size();
	}


	public Integer nArchi() {
		return this.graph.edgeSet().size();
	}


	public List<Airport> getVertici() {
		List<Airport> lista = new ArrayList<>();
		for(Airport a : this.graph.vertexSet()) {
			lista.add(a);
		}
		Collections.sort(lista);
		return lista;
	}


	public List<Vicino> getVicini(Airport scelto) {
		List<Vicino> lista = new ArrayList<>();
		for(Airport a : Graphs.neighborListOf(this.graph, scelto)) {
			lista.add(new Vicino(a,this.graph.getEdgeWeight(this.graph.getEdge(scelto, a))));
		}
		Collections.sort(lista);
		return lista;
	}

}
