package it.polito.tdp.PremierLeague.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import it.polito.tdp.PremierLeague.model.Action;
import it.polito.tdp.PremierLeague.model.Adiacenza;
import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Player;
import it.polito.tdp.PremierLeague.model.Team;

public class PremierLeagueDAO {
	
	public List<Player> listAllPlayers(){
		String sql = "SELECT * FROM Players";
		List<Player> result = new ArrayList<Player>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));
				result.add(player);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Player> playerPerMatch(int m){
		
		String sql = "SELECT DISTINCT p.PlayerID, p.Name "
				+ "FROM players p, actions a "
				+ "WHERE a.PlayerID = p.PlayerID AND a.MatchID = ?";
		List<Player> result = new ArrayList<Player>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, m);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));
				result.add(player);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Team> listAllTeams(){
		String sql = "SELECT * FROM Teams";
		List<Team> result = new ArrayList<Team>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Team team = new Team(res.getInt("TeamID"), res.getString("Name"));
				result.add(team);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Action> listAllActions(){
		String sql = "SELECT * FROM Actions";
		List<Action> result = new ArrayList<Action>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				Action action = new Action(res.getInt("PlayerID"),res.getInt("MatchID"),res.getInt("TeamID"),res.getInt("Starts"),res.getInt("Goals"),
						res.getInt("TimePlayed"),res.getInt("RedCards"),res.getInt("YellowCards"),res.getInt("TotalSuccessfulPassesAll"),res.getInt("totalUnsuccessfulPassesAll"),
						res.getInt("Assists"),res.getInt("TotalFoulsConceded"),res.getInt("Offsides"));
				
				result.add(action);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Match OneMatch(int i){
		String sql = "SELECT m.MatchId, t1.Name, t2.Name "
				+ "FROM matches m, teams t1, teams t2 "
				+"WHERE m.TeamHomeID = t1.TeamID AND m.TeamAwayID = t2.TeamID AND m.MatchId = ?";
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, i);
			ResultSet res = st.executeQuery();
			res.next();
			Match match = new Match(res.getInt("m.MatchID"), res.getString("t1.Name"),res.getString("t2.Name"));
			conn.close();
			return match;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public List<Match> listAllMatches(){
		String sql = "SELECT m.MatchId, t1.Name, t2.Name "
				+ "FROM matches m, teams t1, teams t2 "
				+"WHERE m.TeamHomeID = t1.TeamID AND m.TeamAwayID = t2.TeamID ";
		List<Match> result = new ArrayList<Match>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
				Match match = new Match(res.getInt("m.MatchID"), res.getString("t1.Name"),res.getString("t2.Name"));
				
				
				result.add(match);

			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Adiacenza> adiacenza(Match m){
		String sql = "SELECT p1.PlayerID, p2.PlayerID, p1.Name, p2.Name, ((a1.TotalSuccessfulPassesAll + a1.Assists)/(a1.TimePlayed)) AS efficienza1, ((a2.TotalSuccessfulPassesAll + a2.Assists)/(a2.TimePlayed)) AS efficienza2, ((a1.TotalSuccessfulPassesAll + a1.Assists)/(a1.TimePlayed)) - ((a2.TotalSuccessfulPassesAll + a2.Assists)/(a2.TimePlayed)) AS peso "
				+ "FROM players p1, players p2, actions a1, actions a2 "
				+ "WHERE a1.MatchID = ? AND a1.MatchID = a2.MatchID AND p1.PlayerID = a1.PlayerID AND p2.PlayerID = a2.PlayerID AND p1.PlayerID != p2.PlayerID AND a1.TeamID > a2.TeamID";
		List<Adiacenza> result = new ArrayList<Adiacenza>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, m.getMatchID());
			ResultSet res = st.executeQuery();
			while (res.next()) {	
				Adiacenza a = new Adiacenza(new Player(res.getInt("p1.PlayerID"),res.getString("p1.Name")), 
											new Player(res.getInt("p2.PlayerID"),res.getString("p2.Name")), res.getDouble("peso"));	
				result.add(a);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public int idGiocatoreMigliore(int m) {
		String sql = "SELECT a.TeamID "
				+ "FROM actions a, players p "
				+ "WHERE p.PlayerID = a.PlayerID AND p.PlayerID = ? "
				+ "GROUP BY TeamID";
		int result = 0;
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, m);
			ResultSet res = st.executeQuery();
			res.next();	
			int t = res.getInt("TeamID");
			result = t;
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
