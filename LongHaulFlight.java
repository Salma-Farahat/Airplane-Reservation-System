
/*
 * A Long Haul Flight is a flight that travels a long distance and has two types of seats (First Class and Economy)
 */

public class LongHaulFlight extends Flight
{
	int firstClassPassengers;
		
	public LongHaulFlight(String flightNum, String airline, String dest, String departure, int flightDuration, Aircraft aircraft)
	{
		super(flightNum, airline, dest, departure, flightDuration, aircraft);
		type = Flight.Type.LONGHAUL;
	}

	public LongHaulFlight()
	{
		firstClassPassengers = 0;
		type = Flight.Type.LONGHAUL;
	}
	
	public void assignSeat(Passenger p)
	{
		int seat = random.nextInt(aircraft.getNumFirstClassSeats());
		p.setSeat("FCL"+ seat);
	}
	

	/**
	 * cancels the seat and decreases the number of passengers
	 */
	public void cancelSeat(Passenger p) throws PassengerNotInManifestException{
		
		super.cancelSeat(p);
	}

	/**
	 * reserves a first class seat if the seat value has "+"
	 */
	public void reserveSeat(Passenger p, String seat) throws InvalidSeatTypeException, DuplicatePassengerException, FlightFullException{
		type=  Flight.Type.LONGHAUL;
		if (seat.contains("+")){
			type=  Flight.Type.LONGHAUL;
				if (firstClassPassengers >= aircraft.getNumFirstClassSeats())
				{
					//errorMessage = "Flight " + flightNum + " Full";
					throw new FlightFullException();
				}
				if(seatMap.containsKey(seat)){
					throw new InvalidSeatTypeException();
				}
				// Check for duplicate passenger
			
				if (manifest.indexOf(p) >=  0)
				{
					//errorMessage = "Duplicate Passenger " + p.getName() + " " + p.getPassport();
					throw new DuplicatePassengerException();
					
				}
				assignSeat(p);
				manifest.add(p);
				seatMap.put(seat, p);
				firstClassPassengers++;
		}
		else{
		super.reserveSeat(p, seat);}
	}

	
	public int getPassengerCount()
	{
		return getNumPassengers() +  firstClassPassengers;
	}
	
	/**
	 * checks to see if there are seats available on the flight
	 */
	public boolean seatsAvailable(String seatType)
	{
		if (seatType.equals("FCL"))
			return firstClassPassengers < aircraft.getNumFirstClassSeats();
		else
			return super.seatsAvailable(seatType);
	}
	
	public String toString()
	{
		 return super.toString() + "\t LongHaul";
	}
	public Type getFlightType()
	{
		return super.getFlightType().LONGHAUL;
	}
}
