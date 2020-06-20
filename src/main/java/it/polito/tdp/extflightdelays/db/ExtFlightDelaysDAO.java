package it.polito.tdp.extflightdelays.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.extflightdelays.model.Adiacenza;
import it.polito.tdp.extflightdelays.model.Airline;
import it.polito.tdp.extflightdelays.model.Airport;
import it.polito.tdp.extflightdelays.model.Flight;

public class ExtFlightDelaysDAO {

	public List<Airline> loadAllAirlines() {
		String sql = "SELECT * from airlines";
		List<Airline> result = new ArrayList<Airline>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Airline(rs.getInt("ID"), rs.getString("IATA_CODE"), rs.getString("AIRLINE")));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public void loadAllAirports(Map<Integer, Airport> idMap) {
		String sql = "SELECT * FROM airports";
		

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Airport airport = new Airport(rs.getInt("ID"), rs.getString("IATA_CODE"), rs.getString("AIRPORT"),
						rs.getString("CITY"), rs.getString("STATE"), rs.getString("COUNTRY"), rs.getDouble("LATITUDE"),
						rs.getDouble("LONGITUDE"), rs.getDouble("TIMEZONE_OFFSET"));
				idMap.put(airport.getId(), airport);
			}

			conn.close();
			

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Flight> loadAllFlights() {
		String sql = "SELECT * FROM flights";
		List<Flight> result = new LinkedList<Flight>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Flight flight = new Flight(rs.getInt("ID"), rs.getInt("AIRLINE_ID"), rs.getInt("FLIGHT_NUMBER"),
						rs.getString("TAIL_NUMBER"), rs.getInt("ORIGIN_AIRPORT_ID"),
						rs.getInt("DESTINATION_AIRPORT_ID"),
						rs.getTimestamp("SCHEDULED_DEPARTURE_DATE").toLocalDateTime(), rs.getDouble("DEPARTURE_DELAY"),
						rs.getDouble("ELAPSED_TIME"), rs.getInt("DISTANCE"),
						rs.getTimestamp("ARRIVAL_DATE").toLocalDateTime(), rs.getDouble("ARRIVAL_DELAY"));
				result.add(flight);
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Adiacenza> getAdiacenze(Integer x, Map<Integer, Airport> idMap) {
		final String sql = "SELECT f.ORIGIN_AIRPORT_ID AS a1,f.DESTINATION_AIRPORT_ID AS a2,AVG(f.ELAPSED_TIME) AS peso " + 
				"FROM flights AS f " + 
				"WHERE f.ORIGIN_AIRPORT_ID IN(SELECT a.ID " + 
				"FROM airports AS a,flights AS f " + 
				"WHERE a.ID=f.ORIGIN_AIRPORT_ID " + 
				"OR a.ID=f.DESTINATION_AIRPORT_ID " + 
				"GROUP BY a.ID " + 
				"HAVING SUM(a.ID)>=?) " + 
				"AND f.DESTINATION_AIRPORT_ID IN (SELECT a.ID " + 
				"FROM airports AS a,flights AS f " + 
				"WHERE a.ID=f.ORIGIN_AIRPORT_ID " + 
				"OR a.ID=f.DESTINATION_AIRPORT_ID " + 
				"GROUP BY a.ID " + 
				"HAVING SUM(a.ID)>=?) " + 
				"GROUP BY f.ORIGIN_AIRPORT_ID,f.DESTINATION_AIRPORT_ID";
		List<Adiacenza> lista = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, x);
			st.setInt(2, x);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Adiacenza a = new Adiacenza(idMap.get(rs.getInt("a1")),idMap.get(rs.getInt("a2")),rs.getDouble("peso"));
				lista.add(a);
			}
			
			conn.close();
			return lista;
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
	}
}

