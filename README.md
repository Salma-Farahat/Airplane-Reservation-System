# Flight Reservation System

## Overview
The Flight Reservation System is a Java program that allows users to interact with a flight management system, where they can list flights, make reservations, cancel reservations, and view seat availability. This system handles both short-haul and long-haul flights and provides the ability to reserve economy and first-class seats.

## Code Structure
The Flight Reservation System is organized into several classes, each with specific responsibilities:

- `Aircraft.java`: Represents an aircraft and its attributes, including the number of seats, model, and seat layout. Implements the `Comparable` interface for sorting.

- `Flight.java`: Represents a flight with details such as flight number, airline, destination, departure time, status, and a reference to the aircraft it uses. Passengers can reserve and cancel seats on the flight. It includes methods for managing seat availability, manifest, and printing seat layouts and passenger manifests. Various exceptions are defined for error handling.

- `FlightManager.java`: Manages the list of flights, flight data, and reservations. It provides methods for listing flights, reserving seats, and canceling reservations.

- `FlightReservationSystem.java`: The main class that interacts with the user. It provides a command-line interface for users to perform actions such as listing flights, making reservations, canceling reservations, and viewing passenger manifests.

- `LongHaulFlight.java`: Extends the `Flight` class to handle long-haul flights with first-class and economy seats. It overrides and extends some of the methods to accommodate the different seat types.

- `Passenger.java`: Represents a passenger with attributes like name, passport, and seat information. Provides methods for equality comparison and string representation.

- `Reservation.java`: Represents a reservation with flight and passenger information. Provides methods for getting flight details and printing the reservation.

- `flights.txt`: Sample data file containing flight information, including airline, destination, departure time, and capacity.

## How to Run
1. Compile all Java files in the command line using the `javac` command:

   ```bash
   javac Aircraft.java Flight.java FlightManager.java FlightReservationSystem.java LongHaulFlight.java Passenger.java Reservation.java
   ```

2. Run the program with the following command:

   ```bash
   java FlightReservationSystem
   ```

3. You can now interact with the Flight Reservation System using the provided command-line interface.

## Usage
The Flight Reservation System supports various commands:

- `LIST`: List all available flights.
- `RES <flightNum> <name> <passport> <seatType>`: Reserve a seat on a flight.
- `CANCEL <flightNum> <name> <passport>`: Cancel a reservation.
- `SEATS <flightNum>`: View seat availability for a specific flight.
- `MYRES`: List your reservations.
- `PASMAN <flightNum>`: View the passenger manifest for a flight.
- `QUIT` or `Q`: Exit the program.

## Data Input
Flight data is loaded from the `flights.txt` file, which contains flight information in the following format:

```
<Airline> <Destination> <DepartureTime> <Capacity>
```

## Error Handling
The system handles various exceptions, including `DuplicatePassengerException`, `InvalidSeatTypeException`, `FlightFullException`, `PassengerNotInManifestException`, and `FlightNotFoundException`.

## Note
The system is designed as a command-line interface for demonstration purposes. It can be extended to incorporate a user-friendly graphical interface if needed.
