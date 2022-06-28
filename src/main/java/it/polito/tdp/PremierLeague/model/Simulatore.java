package it.polito.tdp.PremierLeague.model;

import java.util.PriorityQueue;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;
import it.polito.tdp.PremierLeague.model.Event.EventType;

public class Simulatore {

	
	PremierLeagueDAO dao = new PremierLeagueDAO();
	Model m = new Model();
	
	//input
	private int n;
	private Match match;
	private int idBestPlayerTeam;
	
	//dati in uscita
	private int goalTeam1;
	private int goalTeam2;
	private int espTeam1;
	private int espTeam2;
	
	//stato mondo
	private int nGiocatori1;
	private int nGiocatori2;
	
	
	//coda
	PriorityQueue<Event> queue;
	
	public Simulatore(int id) {
		this.idBestPlayerTeam = dao.idGiocatoreMigliore(id);	
	}
	private void init(int n, Match m) {
		this.n = n;
		this.match = m;
		this.nGiocatori1 = 11;
		this.nGiocatori2 = 11;
		this.goalTeam1 = 0;
		this.goalTeam2 = 0;
		this.espTeam1 = 0;
		this.espTeam2 = 0;
		this.queue = new PriorityQueue<Event>();
		
		double prob = Math.random();
		
		if(prob<=0.2) {
			this.queue.add(new Event(EventType.INFORTUNIO, n));
		}else if(prob<=50) {
			this.queue.add(new Event(EventType.ESPULSIONE, n));
		}else {
			this.queue.add(new Event(EventType.GOAL, n));
		}
		
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			processEvent(e);
	}
}

	private void processEvent(Event e) {
		int azioni = e.getAzioni();
		EventType type = e.getE();
		
		
		
		switch(type) {
		case GOAL:
			break;
		case ESPULSIONE:
			
			break;
			
		case INFORTUNIO:
			if(Math.random()<=0.5) {
				//this.n+2;
			}else {
				//this.n+3;
			}
			break;
		}
	}
	
	
	
	
	
	
}