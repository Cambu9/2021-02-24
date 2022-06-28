package it.polito.tdp.PremierLeague.db;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.PremierLeague.model.Adiacenza;

public class TestDao {

	public static void main(String[] args) {
		TestDao testDao = new TestDao();
		testDao.run();
	}
	
	public void run() {
		PremierLeagueDAO dao = new PremierLeagueDAO();
//		System.out.println("Players:");
//		System.out.println(dao.listAllPlayers());
//		System.out.println("Teams:");
//		System.out.println(dao.listAllTeams());
//		System.out.println("Actions:");
//		System.out.println(dao.listAllActions());
//		System.out.println("Matches:");
//		System.out.println(dao.listAllMatches());
		List<Adiacenza> adiacenza = new ArrayList<Adiacenza>();
		adiacenza = dao.adiacenza(dao.OneMatch(3));
		System.out.println(adiacenza);
	}

}
