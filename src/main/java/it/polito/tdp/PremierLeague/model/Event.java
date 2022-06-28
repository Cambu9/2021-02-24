package it.polito.tdp.PremierLeague.model;

public class Event implements Comparable<Event>{

	public enum EventType{
		GOAL,
		ESPULSIONE,
		INFORTUNIO
	}
	
	private EventType e;
	private int azioni;
	
	
	public Event(EventType e, int azioni) {
		super();
		this.e = e;
		this.azioni = azioni;
	}

	

	public EventType getE() {
		return e;
	}



	public void setE(EventType e) {
		this.e = e;
	}



	public int getAzioni() {
		return azioni;
	}



	public void setAzioni(int azioni) {
		this.azioni = azioni;
	}



	@Override
	public int compareTo(Event o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
