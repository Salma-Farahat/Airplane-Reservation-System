
public class Aircraft implements Comparable<Aircraft>
{
  int numEconomySeats;
  int numFirstClassSeats;
  String[][] seatLayout;
  String model;
  
  public Aircraft(int seats, String model)
  {
  	this.numEconomySeats = seats;
  	this.numFirstClassSeats = 0;
  	this.model = model;
	this.seatLayout= new String [getRows()][getColumns()];
  }

  public Aircraft(int economy, int firstClass, String model)
  {
  	this.numEconomySeats = economy;
  	this.numFirstClassSeats = firstClass;
  	this.model = model;
	this.seatLayout= new String [getRows()][getColumns()];

  }
  
	public int getNumSeats()
	{
		return numEconomySeats;
	}
	
	public int getTotalSeats()
	{
		return numEconomySeats + numFirstClassSeats;
	}
	
	public int getNumFirstClassSeats()
	{

		return numFirstClassSeats;
	}

	public String getModel()
	{
		return model;
	}

	public void setModel(String model)
	{
		this.model = model;
	}
	
	public void print()
	{
		System.out.println("Model: " + model + "\t Economy Seats: " + numEconomySeats + "\t First Class Seats: " + numFirstClassSeats);
	}
	/**
	 * Get the number of columns in the airplane
	 * @return integer
	 */
	public int getColumns(){
		if (model.equals("Bombardier 5000")){
			return 2;
		}
		return getTotalSeats()/4;
	}

	/**
	 * Get the number of first class columns in the airplane
	 * @return integer
	 */
	public int getFirstClassColumns(){

		return getNumFirstClassSeats()/4;
	}

	/**
	 * Get the number of rows in the airplane
	 * @return integer
	 */
	public int getRows(){
		if (model.equals("Bombardier 5000")){
			return 2;
		}
		return 4;
	}




  public int compareTo(Aircraft other)
  {
  	if (this.numEconomySeats == other.numEconomySeats)
  		return this.numFirstClassSeats - other.numFirstClassSeats;
  	
  	return this.numEconomySeats - other.numEconomySeats; 
  }
}
