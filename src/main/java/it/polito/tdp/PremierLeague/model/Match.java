package it.polito.tdp.PremierLeague.model;

import java.time.LocalDateTime;

public class Match implements Comparable<Match>{
	Integer matchID;
	String Team1;
	String Team2;
	public Match(Integer matchID, String team1, String team2) {
		super();
		this.matchID = matchID;
		Team1 = team1;
		Team2 = team2;
	}
	public Integer getMatchID() {
		return matchID;
	}
	public void setMatchID(Integer matchID) {
		this.matchID = matchID;
	}
	public String getTeam1() {
		return Team1;
	}
	public void setTeam1(String team1) {
		Team1 = team1;
	}
	public String getTeam2() {
		return Team2;
	}
	public void setTeam2(String team2) {
		Team2 = team2;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Team1 == null) ? 0 : Team1.hashCode());
		result = prime * result + ((Team2 == null) ? 0 : Team2.hashCode());
		result = prime * result + ((matchID == null) ? 0 : matchID.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Match other = (Match) obj;
		if (Team1 == null) {
			if (other.Team1 != null)
				return false;
		} else if (!Team1.equals(other.Team1))
			return false;
		if (Team2 == null) {
			if (other.Team2 != null)
				return false;
		} else if (!Team2.equals(other.Team2))
			return false;
		if (matchID == null) {
			if (other.matchID != null)
				return false;
		} else if (!matchID.equals(other.matchID))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "[" + matchID + "] " + Team1 + " Vs. " + Team2 + "\n";
	}
	
	@Override
	public int compareTo(Match o) {
		// TODO Auto-generated method stub
		return this.matchID-o.matchID;
	}
	
}