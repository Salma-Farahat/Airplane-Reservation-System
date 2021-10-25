import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

public class Flight
{
	public enum Status {DELAYED, ONTIME, ARRIVED, INFLIGHT};
	public static enum Type {SHORTHAUL, MEDIUMHAUL, LONGHAUL};
	public static enum SeatType {ECONOMY, FIRSTCLASS, BUSINESS};

	private String flightNum;
	private String airline;
	private String origin, dest;
	private String departureTime;
	private Status status;
	private int flightDuration;
	protected Aircraft aircraft;
	protected int numPassengers;
	protected Type type;
	protected ArrayList<Passenger> manifest;
	protected TreeMap<String, Passenger> seatMap;
	protected Random random = new Random();
	
	private String errorMessage = "";
	  
	public String getErrorMessage()
	{
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	public Flight()
	{
		this.flightNum = "";
		this.airline = "";
		this.dest = "";
		this.origin = "Toronto";
		this.departureTime = "";
		this.flightDuration = 0;
		this.aircraft = null;
		numPassengers = 0;
		status = Status.ONTIME;
		type = Type.MEDIUMHAUL;
		manifest = new ArrayList<Passenger>();
		seatMap= new TreeMap<String, Passenger>();
	}
	
	public Flight(String flightNum)
	{
		this.flightNum = flightNum;
	}
	
	public Flight(String flightNum, String airline, String dest, String departure, int flightDuration, Aircraft aircraft)
	{
		this.flightNum = flightNum;
		this.airline = airline;
		this.dest = dest;
		this.origin = "Toronto";
		this.departureTime = departure;
		this.flightDuration = flightDuration;
		this.aircraft = aircraft;
		numPassengers = 0;
		status = Status.ONTIME;
		type = Type.MEDIUMHAUL;
		manifest = new ArrayList<Passenger>();
		seatMap= new TreeMap<String, Passenger>();

	}

	public Type getFlightType()
	{
		return type;
	}
	
	public String getFlightNum()
	{
		return flightNum;
	}
	public void setFlightNum(String flightNum)
	{
		this.flightNum = flightNum;
	}
	public String getAirline()
	{
		return airline;
	}
	public void setAirline(String airline)
	{
		this.airline = airline;
	}
	public String getOrigin()
	{
		return origin;
	}
	public void setOrigin(String origin)
	{
		this.origin = origin;
	}
	public String getDest()
	{
		return dest;
	}
	public void setDest(String dest)
	{
		this.dest = dest;
	}
	public String getDepartureTime()
	{
		return departureTime;
	}
	public void setDepartureTime(String departureTime)
	{
		this.departureTime = departureTime;
	}
	
	public Status getStatus()
	{
		return status;
	}
	public void setStatus(Status status)
	{
		this.status = status;
	}
	public int getFlightDuration()
	{
		return flightDuration;
	}
	public void setFlightDuration(int dur)
	{
		this.flightDuration = dur;
	}
	
	public int getNumPassengers()
	{
		return numPassengers;
	}
	public void setNumPassengers(int numPassengers)
	{
		this.numPassengers = numPassengers;
	}
	
	public void assignSeat(Passenger p)
	{
		int seat = random.nextInt(aircraft.numEconomySeats);
		p.setSeat("ECO"+ seat);
	}
	
	public String getLastAssignedSeat()
	{
		if (!manifest.isEmpty())
			return manifest.get(manifest.size()-1).getSeat();
		return "";
	}
	
	/**
	 * checks to see if there are seats available on the flight
	 * @param seatType
	 * @return boolean
	 */
	public boolean seatsAvailable(String seatType)
	{
		if (!seatType.equalsIgnoreCase("ECO")) return false;
		return numPassengers < aircraft.numEconomySeats;
	}
	
	/**
	 * Cancels a seat on the flight
	 * @param p
	 * @throws PassengerNotInManifestException
	 */

	public void cancelSeat(Passenger p) throws PassengerNotInManifestException
	{
		try{
		if (manifest.indexOf(p) == -1) 
		{
			throw new PassengerNotInManifestException();
		}
		
		manifest.remove(p);
		seatMap.values().remove(p);
		if (numPassengers > 0) numPassengers--;
		
	}
	catch(PassengerNotInManifestException e){
		System.out.println("Passenger " + p.getName() + " " + p.getPassport() + " Not Found");

	}
	}

	/**
	 * reserves a seat on the flight and increases the number of passengers by 1
	 * @param p
	 * @param seat
	 * @throws InvalidSeatTypeException
	 * @throws DuplicatePassengerException
	 * @throws FlightFullException
	 */
	public void reserveSeat(Passenger p, String seat) throws InvalidSeatTypeException, DuplicatePassengerException, FlightFullException
	{
		if (numPassengers >= aircraft.getNumSeats())
		{
			throw new FlightFullException();
		}

		if(seatMap.containsKey(seat)){
			throw new InvalidSeatTypeException();
		}
		// Check for duplicate passenger
	
		if (manifest.indexOf(p) >=  0)
		{
			throw new DuplicatePassengerException();
			
		}
		assignSeat(p);
		manifest.add(p);
		seatMap.put(seat, p);
		numPassengers++;
		


		
	}

	
	/**
	 * checks to see if 2 flights have the same flight number
	 */
	
	public boolean equals(Object other)
	{
		Flight otherFlight = (Flight) other;
		return this.flightNum.equals(otherFlight.flightNum);
	}
	
	public String toString()
	{
		 return airline + "\t Flight:  " + flightNum + "\t Dest: " + dest + "\t Departing: " + departureTime + "\t Duration: " + flightDuration + "\t Status: " + status;
	}

	/**
	 * Prints out all the seats on the airplane and XX if the seat is reserved
	 */
	public void printSeats(){

		if (aircraft.getModel().equals("Boeing 747")){		
		
			for (int row=0; row<this.aircraft.getRows(); row++){
			for (int col=0; col<this.aircraft.getColumns(); col++){
				if (seatMap.containsKey(this.aircraft.seatLayout[row][col]) ){
					System.out.print("XX ");
				}
				else{
				System.out.print(this.aircraft.seatLayout[row][col]+" ");
			}
			}
			if (row==1){
				System.out.println();
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("XX = Occupied   + = First Class");
	}



		else{
			
			
			for (int row=0; row<(this.aircraft.getRows()); row++){
				for (int col=0; col<(this.aircraft.getColumns()); col++){
					if (seatMap.containsKey(this.aircraft.seatLayout[row][col]) ){
						System.out.print("XX ");
					}
					else{
					System.out.print(this.aircraft.seatLayout[row][col]+" ");}
				}
				System.out.println();
			}
			System.out.println();
			System.out.println("XX = Occupied   + = First Class");}
		










	}
	/**
	 * populates the seat names on the airplane
	 */
	public void populateSeatLayout(){
		
		String letters="ABCD";
		int rownum=0;
		if (aircraft.getModel().equals("Boeing 747")){
		for (int row=0; row<4;row++){
			rownum=1;
			for (int col=0; col<(this.aircraft.getColumns()); col++){
				if (col<3){
				this.aircraft.seatLayout[row][col]=rownum+""+letters.charAt(row)+"+";
				rownum++;
			}
				else{
				this.aircraft.seatLayout[row][col]=rownum+""+letters.charAt(row);
				rownum++;
				}
			}

		}
	}



	else{
		for (int row=0; row<this.aircraft.getRows();row++){
			rownum=1;
			for (int col=0; col<(this.aircraft.getColumns()); col++){
				this.aircraft.seatLayout[row][col]=rownum+""+letters.charAt(row);
				rownum++;
			}

		}

	}
	}

	/**
	 * Prints out all the passengers in manifest Arraylist
	 */
	public void printPassengerManifest(){
		for (Passenger p: manifest){
			System.out.println(p);
		}
	}




}
class DuplicatePassengerException extends Exception{
	DuplicatePassengerException(){

	}
	DuplicatePassengerException(String message){
		super(message);
	}
}
class PassengerNotInManifestException extends Exception{
	PassengerNotInManifestException(){

	}
	PassengerNotInManifestException(String message){
		super(message);
	}
}

class InvalidSeatTypeException extends Exception{
	InvalidSeatTypeException(){

	}
	InvalidSeatTypeException(String message){
		super(message);
	}
}
class FlightFullException extends Exception{
	FlightFullException(){

	}
	FlightFullException(String message){
		super(message);
	}
}
class FlightNotFoundException extends Exception{
	FlightNotFoundException(){

	}
	FlightNotFoundException(String message){
		super(message);
	}
}