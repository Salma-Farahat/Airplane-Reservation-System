
public class Reservation
{
	String flightNum;
	String flightInfo;
	String seat;
	String seatType;
	boolean firstClass;
	String passengerName;
	String passengerPassport;


	
	public Reservation(String flightNum, String info)
	{
		this.flightNum = flightNum;
		this.flightInfo = info;
	}
	
	public Reservation(String flightNum, String passengerName, String passengerPassport)
	{
		this.flightNum = flightNum;
		this.passengerName = passengerName;
		this.passengerPassport = passengerPassport;
	}
	
	public Reservation(String flightNum, String info, String passengerName, String passengerPassport, String seat, String seatType)
	{
		this.flightNum = flightNum;
		this.flightInfo = info;
		this.passengerName = passengerName;
		this.passengerPassport = passengerPassport;
		this.seat = seat;
		this.seatType = seatType;
	}
	
	public String getFlightNum()
	{
		return flightNum;
	}
	
	public String getFlightInfo()
	{
		return flightInfo;
	}

	public void setFlightInfo(String flightInfo)
	{
		this.flightInfo = flightInfo;
	}
	/**
	 * checks to see if a passenger has 2 reservations on the same flight
	 */
	public boolean equals(Object other)
	{
		Reservation otherRes = (Reservation) other;
		return flightNum.equals(otherRes.flightNum)&&  passengerName.equals(otherRes.passengerName) && passengerPassport.equals(otherRes.passengerPassport); 
	}

	public void print()
	{
		System.out.println(flightInfo + " " + passengerName + " " + seat);
	}
}
