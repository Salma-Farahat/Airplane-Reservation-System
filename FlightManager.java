import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class FlightManager {
	ArrayList<Flight> flights = new ArrayList<Flight>();

	String[] cities = { "Dallas", "New York", "London", "Paris", "Tokyo" };
	final int DALLAS = 0;
	final int NEWYORK = 1;
	final int LONDON = 2;
	final int PARIS = 3;
	final int TOKYO = 4;

	int[] flightTimes = { 3, // Dallas
			1, // New York
			7, // London
			8, // Paris
			16,// Tokyo
	};

	ArrayList<Aircraft> airplanes = new ArrayList<Aircraft>();
	ArrayList<String> flightNumbers = new ArrayList<String>();
	Map<String, Flight> flighMap = new TreeMap<String, Flight>();

	String errMsg = null;
	Random random = new Random();

	public FlightManager() {
		// Create some aircraft types with max seat capacities
		airplanes.add(new Aircraft(4, "Bombardier 5000"));
		airplanes.add(new Aircraft(20, "Boeing 737"));
		airplanes.add(new Aircraft(40, "Dash-8 100"));
		airplanes.add(new Aircraft(80, "Airbus 320"));
		airplanes.add(new Aircraft(120, 12, "Boeing 747"));




		try {

			File inputfile = new File("flights.txt");
			Scanner s = new Scanner(inputfile);

			while (s.hasNextLine()) {
				String airline = s.next();
				String city = s.next();
				String departure = s.next();
				String capacity = s.next();
				int seats = Integer.parseInt(capacity);
				while (seats % 4 != 0) {
					seats--;
				}

				airline = airline.replace("_", " ");
				String flightNum = generateFlightNumber(airline);
				String cityInCaps = city.toUpperCase();
				Aircraft aircraft = null;

				outer: for (int i = 0; i < airplanes.size(); i++) {
					Aircraft a = airplanes.get(i);
					if (city.equals("Tokyo")) {
						aircraft = airplanes.get(4);
					} else if (seats <= a.numEconomySeats + a.numFirstClassSeats) {
						aircraft = airplanes.get(i);
						break outer;
					}

				}
				int duration = 0;
				if (city.equals("Dallas")) {
					duration = flightTimes[DALLAS];
				} else if (city.equals("New_York")) {
					city = city.replace("_", " ");
					duration = flightTimes[NEWYORK];
				} else if (city.equals("London")) {
					duration = flightTimes[LONDON];
				} else if (city.equals("Paris")) {
					duration = flightTimes[PARIS];
				} else if (city.equals("Tokyo")) {
					duration = flightTimes[TOKYO];
				}
				if (city.equals("Tokyo")) {
					LongHaulFlight flight = new LongHaulFlight(flightNum, airline, city, departure, duration, aircraft);
					flight.populateSeatLayout();
					flights.add(flight);
				} else {
					Flight flight = new Flight(flightNum, airline, city, departure, duration, aircraft);
					flight.populateSeatLayout();
					flights.add(flight);
				}

				for (Flight f : flights) {
					flighMap.put(f.getFlightNum(), f);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * gernerates a flight number 
	 * @param airline
	 * @return String
	 */
	private String generateFlightNumber(String airline) {
		String word1, word2;
		Scanner scanner = new Scanner(airline);
		word1 = scanner.next();
		word2 = scanner.next();
		String letter1 = word1.substring(0, 1);
		String letter2 = word2.substring(0, 1);
		letter1.toUpperCase();
		letter2.toUpperCase();

		// Generate random number between 101 and 300
		boolean duplicate = false;
		int flight = random.nextInt(200) + 101;
		String flightNum = letter1 + letter2 + flight;
		return flightNum;
	}

	public String getErrorMessage() {
		return errMsg;
	}
	
	/**
	 * Prints all the flights in the flight map
	 */
	public void printAllFlights() {
		Set<String> keyset = flighMap.keySet();
		for (String k : keyset) {
			Flight value = flighMap.get(k);
			System.out.println(value);
		}

	}

	/**
	 * Checks to see if there is a seat available on the flight
	 * 
	 * @param flightNum
	 * @param seatType
	 * @return true if seat is available and false otherwise
	 */
	public boolean seatsAvailable(String flightNum, String seatType) {


		if (flighMap.containsKey(flightNum)) {
			try {
				flighMap.get(flightNum).seatsAvailable(seatType);
				return flighMap.get(flightNum).seatsAvailable(seatType);
			} catch (Exception e) {
				System.out.println(e);
			}
		}

		return false;
	}

	/**
	 * reserves a seat on the flight if it is not full.
	 * 
	 * @param flightNum
	 * @param name
	 * @param passport
	 * @param seatType
	 * @return reservation object
	 */

	public Reservation reserveSeatOnFlight(String flightNum, String name, String passport, String seatType)
			throws FlightFullException, FlightNotFoundException, DuplicatePassengerException, InvalidSeatTypeException {
		// int index = flights.indexOf(new Flight(flightNum));

		String type = "ECO";
		if (seatType.contains("+")) {
			type = "FCL";
		}
		Flight flight = flighMap.get(flightNum);

		Passenger p = new Passenger(name, passport, seatType, type);
		if (flight instanceof LongHaulFlight) {
			LongHaulFlight f = (LongHaulFlight) flight;
			f.reserveSeat(p, seatType);
			p.setSeat(seatType);
		} else {
			flight.reserveSeat(p, seatType);
			p.setSeat(seatType);
		}
		return new Reservation(flightNum, flight.toString(), p.getName(), p.getPassport(), p.getSeat(), type);

	}

	/**
	 * Given a reservation object, cancel a seat on the flight
	 * 
	 * @param res
	 * @return
	 */

	public boolean cancelReservation(Reservation res, Passenger p) throws PassengerNotInManifestException 
	{

		if (flighMap.containsKey(res.getFlightNum())) {
			try {
				flighMap.get(res.getFlightNum()).cancelSeat(p);
			} catch (Exception e) {
				System.out.println(e);
			}
		}

		return true;
	}

}
