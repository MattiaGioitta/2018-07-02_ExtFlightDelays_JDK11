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
	
	private Graph<Airport, DefaultWeightedEdge> graph;
	private ExtFlightDelaysDAO dao;
	private Map<Integer,Airport> idMap;
	private List<Airport> bestPath;
	private double bestMiglia;
	
	public Model() {
		this.dao = new ExtFlightDelaysDAO();
		this.idMap = new HashMap<>();
	}

	public void createGraph(Integer miglia) {
		this.dao.loadAllAirports(idMap);
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		List<Adiacenza> adiacenze = this.dao.getAdiacenze(miglia,idMap);
		//popolo il grafo
		for(Adiacenza a : adiacenze) {
			if(!this.graph.containsVertex(a.getA1())) {
				this.graph.addVertex(a.getA1());
			}
			if(!this.graph.containsVertex(a.getA2())) {
				this.graph.addVertex(a.getA2());
			}
			if(this.graph.getEdge(a.getA1(), a.getA2()) == null) {
				Graphs.addEdgeWithVertices(this.graph, a.getA1(), a.getA2(),a.getDistanza());
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

	public List<Airport> vertici() {
		List<Airport> list = new ArrayList<>();
		for(Airport a: this.graph.vertexSet()) {
			list.add(a);
		}
		return list;
	}

	public List<Vicino> vicini(Airport a) {
		List<Vicino> vicini = new ArrayList<>();
		for(Airport ar : Graphs.neighborListOf(this.graph, a)) {
			vicini.add(new Vicino(ar,this.graph.getEdgeWeight(this.graph.getEdge(a, ar))));
		}
		Collections.sort(vicini);
		return vicini;
	}

	public void cercaCammino(Airport source, Integer migliaDisponibili) {
		this.bestPath = new ArrayList<>();
		this.bestMiglia = 0.0;
		List<Airport> parziale = new ArrayList<>();
		cerca(parziale, source, migliaDisponibili);
	}

	private void cerca(List<Airport> parziale, Airport source, Integer migliaDisponibili) {
		//condizione terminale
		Double distanza = calcolaDistanza(parziale);
		if(distanza>migliaDisponibili) {
			return;
		}
		if(parziale.size()>this.bestPath.size() && distanza<=migliaDisponibili) {
			this.bestMiglia = distanza;
			this.bestPath = new ArrayList<>(parziale);
			return;
		}
		
		for(Airport a : Graphs.neighborListOf(this.graph, source)) {
			if(!parziale.contains(a)) {
				parziale.add(a);
				cerca(parziale,a,migliaDisponibili);
				parziale.remove(parziale.size()-1);
			}
		}
		
	}

	private Double calcolaDistanza(List<Airport> parziale) {
		Double distanza = 0.0;
		for(int i = 1;i<parziale.size();i++) {
			if(this.graph.getEdge(parziale.get(i-1), parziale.get(i)) != null) {
				distanza+=this.graph.getEdgeWeight(this.graph.getEdge(parziale.get(i-1), parziale.get(i)));
			}
		}
		return distanza;
	}

	public List<Airport> getCammino() {
		// TODO Auto-generated method stub
		return this.bestPath;
	}

	public Double getMigliaPercorsi() {
		// TODO Auto-generated method stub
		return this.bestMiglia;
	}

}
