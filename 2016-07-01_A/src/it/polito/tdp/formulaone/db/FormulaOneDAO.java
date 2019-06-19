package it.polito.tdp.formulaone.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.formulaone.model.Circuit;
import it.polito.tdp.formulaone.model.Connessione;
import it.polito.tdp.formulaone.model.Constructor;
import it.polito.tdp.formulaone.model.Driver;
import it.polito.tdp.formulaone.model.Season;


public class FormulaOneDAO {

	public List<Integer> getAllYearsOfRace() {
		
		String sql = "SELECT DISTINCT year FROM races ORDER BY year" ;
		
		try {
			Connection conn = ConnectDB.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet rs = st.executeQuery() ;
			
			List<Integer> list = new ArrayList<>() ;
			while(rs.next()) {
				list.add(rs.getInt("year"));
			}
			
			conn.close();
			return list ;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}
	
	public List<Season> getAllSeasons() {
		
		String sql = "SELECT year, url FROM seasons ORDER BY year" ;
		
		try {
			Connection conn = ConnectDB.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet rs = st.executeQuery() ;
			
			List<Season> list = new ArrayList<>() ;
			while(rs.next()) {
				list.add(new Season(Year.of(rs.getInt("year")), rs.getString("url"))) ;
			}
			
			conn.close();
			return list ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Circuit> getAllCircuits() {

		String sql = "SELECT circuitId, name FROM circuits ORDER BY name";

		try {
			Connection conn = ConnectDB.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			List<Circuit> list = new ArrayList<>();
			while (rs.next()) {
				list.add(new Circuit(rs.getInt("circuitId"), rs.getString("name")));
			}

			conn.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}
	
	public List<Constructor> getAllConstructors() {

		String sql = "SELECT constructorId, name FROM constructors ORDER BY name";

		try {
			Connection conn = ConnectDB.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			List<Constructor> constructors = new ArrayList<>();
			while (rs.next()) {
				constructors.add(new Constructor(rs.getInt("constructorId"), rs.getString("name")));
			}

			conn.close();
			return constructors;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}
	
	public List<Driver> trovaVertici(int anno, Map<Integer, Driver> map){
		String sql = "SELECT distinct re.driverId, d.driverRef, d.NUMBER, d.CODE, d.forename, d.surname, d.dob, d.nationality, d.url " + 
				"FROM races AS ra, results AS re, drivers AS d " + 
				"WHERE ra.YEAR=? AND ra.raceId=re.raceId AND re.POSITION IS NOT NULL " + 
				"AND re.driverId=d.driverId ";
				

		try {
			Connection conn = ConnectDB.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);

			ResultSet rs = st.executeQuery();

			List<Driver> drivers = new ArrayList<>();
			while (rs.next()) {
				Driver d= new Driver(rs.getInt("re.driverId"), rs.getString("d.driverRef"), rs.getInt("d.NUMBER"), rs.getString("d.CODE"), rs.getString("d.forename"), rs.getString("d.surname"),
						rs.getDate("d.dob").toLocalDate(), rs.getString( "d.nationality"), rs.getString("d.url"));
				drivers.add(d);
				map.put(d.getDriverId(), d);
			}

			conn.close();
			return drivers;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}

	
	public List<Connessione> trovaArchi(int anno, Map<Integer, Driver> map){
		String sql = "SELECT re1.driverId AS vincente, re2.driverId AS perdente, COUNT(*) AS cnt " + 
				"FROM races AS ra, results AS re1, results AS re2 " + 
				"WHERE ra.YEAR= ? AND ra.raceId= re1.raceId AND re1.POSITION IS NOT NULL AND re2.POSITION IS NOT NULL " + 
				"AND re1.raceId=re2.raceId AND re1.POSITION< re2.POSITION AND re1.driverId<>re2.driverId " + 
				"GROUP BY re1.driverId, re2.driverId";
				

		try {
			Connection conn = ConnectDB.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);

			ResultSet rs = st.executeQuery();

			List<Connessione> lis = new ArrayList<>();
			while (rs.next()) {
				Connessione c= new Connessione(map.get(rs.getInt("vincente")), map.get(rs.getInt("perdente")), rs.getInt("cnt"));
						
				lis.add(c);
			}

			conn.close();
			return lis;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}
}
	
