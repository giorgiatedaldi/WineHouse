package assegnamento;

/**
 * 
 * The {@code Employee} class defines an employee instance. It is a {@code Person} subclass.
 * An employee subscribes to {@code MySystem}, he prepares the order, sends the wine to the customer and supplies 
 * the warehouse.
 * 
 * @see Person
 * @see MySystem
 *  
 **/
public class Employee  extends Person
{
	/**
	 * 
	 * Class constructor.
	 * 
	 * @param n Name
	 * @param s Surname
	 * @param e Email
	 * @param p Password
	 * @param sys MySystem
	 * 
	 **/
	public Employee (final String n, final String s, final String e, final String p, final MySystem sys)
	{
		super (n, s, e, p, sys);
	}

	/**
	 * 
	 * Method to add Wine in wineList. If the wine does not exist it adds a new wine otherwise it returns the string already added.
	 * 
	 * @param w Wine to add
	 *
	 * @return "Added" or "Already added"
	 * 
	 **/
	public String addWine(Wine w)
	{
		for (Wine toSearch: this.mySystem.getWineList())
		{
			if (toSearch.getName().equals(w.getName()) && toSearch.getWinemaker().equals(w.getWinemaker()))
			{
				return ("Already added");
			}
		}
		this.mySystem.getWineList().add(w);
		return ("Added");
	}
	
	/**
	 * 
	 * Method to restock wine, it sets the number of wine and sends a notification to the 
	 * customer, if the wine is enough, through the method notifyCustomer. 
	 * 
	 * @param n name of wine
	 * @param w winemaker
	 * @param a number of bottles to restock
	 * 
	 **/
	public void restockWine(String n, String w, int a)
	{
		Wine found = findWine(n, w);
		found.setnWine(found.getnWine()+a);
		Wine updateFound = findWine(n, w);
		for (int i = 0; i < updateFound.getNotification().keySet().size(); i++)	
		{
			String toNotify = this.mySystem.notifyCustomer(updateFound, (String) updateFound.getNotification().keySet().toArray()[i]);
			if (!toNotify.equals("Not enough"))
			{
				i--;
			}
		}		
	}
	
	/**
	 * 
	 * Sets employee's password.
	 * 
	 * @param p password
	 *
	 **/
	public void setPassword(String p) 
	{
		this.password = p; 
	}
	
	/**
	 * 
	 * Sets employee's attributes.
	 * 
	 * @param n name
	 * @param s surname
	 * @param e email
	 * @param p password 
	 * 
	 **/
	public void setEmployee(String n, String s, String e, String p)
	{
		this.name = n;
		this.surname = s;
		this.email = e;
		this.password = p;
	}
}