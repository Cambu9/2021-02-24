package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	PremierLeagueDAO dao = new PremierLeagueDAO();
	private Graph<Player, DefaultWeightedEdge> grafo;
	double max = 0.0;
	
	public List<Match> getAllMatches() {
		List<Match> match = new ArrayList<Match>();
		match = dao.listAllMatches();
		Collections.sort(match);
		return match;
	}
	
	public String creaGrafo(Match m) {
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		//aggiungo i vertici
		Graphs.addAllVertices(this.grafo, this.dao.playerPerMatch(m.getMatchID()));
		
		List<Adiacenza> adiacenze = new ArrayList<Adiacenza>();
		adiacenze = dao.adiacenza(m);
		//aggiungo archi
		for(Adiacenza a: adiacenze) {
			if (a.getPeso()<0) {
				if(grafo.containsVertex(a.getP1()) && grafo.containsVertex(a.getP2())) {
					Graphs.addEdgeWithVertices(this.grafo, a.getP2(),a.getP1(),(-1)* a.getPeso());
				}
			}else if(a.getPeso()>=0) {
				if(grafo.containsVertex(a.getP1()) && grafo.containsVertex(a.getP2())) {
					Graphs.addEdgeWithVertices(this.grafo, a.getP1(), a.getP2(), a.getPeso());
				}
			}
		}
		return "Grafo creato correttamente con " +this.grafo.vertexSet().size()+ " vertici e " +this.grafo.edgeSet().size()+" archi.\n";
	}
	
	public Player calcoloGiocatoreMigliore() {
	
		if(this.grafo == null) {
			return null;
		}
		double bestPlayerWeight = 0.0;
		Player pBest = null;
		for(Player p: this.grafo.vertexSet()) {
			double outgoing = 0.0;
			double incoming = 0.0;
			double tot = 0.0;
			for(DefaultWeightedEdge e: this.grafo.outgoingEdgesOf(p)) {
				outgoing += this.grafo.getEdgeWeight(e);
			}
			for(DefaultWeightedEdge e: this.grafo.incomingEdgesOf(p)) {
				incoming += this.grafo.getEdgeWeight(e);
			}
			tot = outgoing - incoming;
			if(tot > bestPlayerWeight) {
				bestPlayerWeight = tot;
				pBest = p;
			}
		}
		double temp = Math.pow(10, 3);
		max =Math.round(bestPlayerWeight*temp)/temp;
		return pBest;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}
	
}
