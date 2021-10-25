import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

// Flight System for one single day at YYZ (Print this in title) Departing flights!!


public class FlightReservationSystem
{
	public static void main(String[] args)
	{
		FlightManager manager = new FlightManager();

		ArrayList<Reservation> myReservations = new ArrayList<Reservation>();	// my flight reservations


		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		while (scanner.hasNextLine())
		{
			String inputLine = scanner.nextLine();
			if (inputLine == null || inputLine.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}

			Scanner commandLine = new Scanner(inputLine);
			String action = commandLine.next();

			if (action == null || action.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}

			else if (action.equals("Q") || action.equals("QUIT"))
				return;

			else if (action.equalsIgnoreCase("LIST"))
			{
				manager.printAllFlights(); 
			}
			else if (action.equalsIgnoreCase("RES"))
			{
				String flightNum = null;
				String passengerName = "";
				String passport = "";
				String seatType = "";

				if (commandLine.hasNext())
				{
					flightNum = commandLine.next();
				}
				if (commandLine.hasNext())
				{
					passengerName = commandLine.next();
				}
				if (commandLine.hasNext())
				{
					passport = commandLine.next();
				}
				if (commandLine.hasNext())
				{
					seatType = commandLine.next();

					try{

					Reservation res = manager.reserveSeatOnFlight(flightNum, passengerName, passport, seatType);
					if (res != null)
					{
						myReservations.add(res);
						res.print();
					}
					

				}
				catch(DuplicatePassengerException e){
					System.out.println("Duplicate Passenger " + passengerName + " " + passport);
				}
				catch(InvalidSeatTypeException e){
					System.out.println("Seat "+ seatType+" is Occupied");
				}
				catch (FlightFullException e){
					System.out.println("Flight " + flightNum + " Full");
				}
				catch(Exception e){
					System.out.println(e);

				}
			}
		}
			else if (action.equalsIgnoreCase("CANCEL"))
			{
				Reservation res = null;
				String flightNum = null;
				String passengerName = "";
				String passport = "";


				if (commandLine.hasNext())
				{
					flightNum = commandLine.next();
				}
				if (commandLine.hasNext())
				{
					passengerName = commandLine.next();
				}
				if (commandLine.hasNext())
				{
					passport = commandLine.next();
					try{
					int index = myReservations.indexOf(new Reservation(flightNum, passengerName, passport));
					if (index >= 0)
					{
						Passenger p = new Passenger(passengerName, passport);
						manager.cancelReservation(myReservations.get(index), p);
						myReservations.remove(index);
					}
				
					}
					catch(PassengerNotInManifestException e){
						System.out.println("Passenger " + passengerName + " " + passport + " Not Found");
				
					}
				}

			}
			else if (action.equalsIgnoreCase("SEATS"))
			{
				String flightNum = "";

				if (commandLine.hasNext())
				{
					
					flightNum = commandLine.next();


						boolean found =false;
						Set<String> keyset = manager.flighMap.keySet();
						for (String k : keyset){
							if (k.equals(flightNum)){
								Flight f= manager.flighMap.get(k);
								found = true;
								f.printSeats();
							}
							
						}
						if (found== false){
							System.out.println("Flight not Found");
						}
						
				
				}
			}
			else if (action.equalsIgnoreCase("MYRES"))
			{
				for (Reservation myres : myReservations)
					myres.print();
			}
			else if (action.equalsIgnoreCase("PASMAN"))
			{

				String flightNum = "";

				if (commandLine.hasNext())
				{
					flightNum = commandLine.next();
					manager.flighMap.get(flightNum).printPassengerManifest();
				}
			}
			
			System.out.print("\n>");
		}
	}


}

