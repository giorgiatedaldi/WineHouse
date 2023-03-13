package assegnamento;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * The {@code Customer} class defines a Customer instance. It is a {@code Person} subclass.
 * A customer can subscribe to the system, search a specific type of wine by its name or production's year and,
 * after being logged, buy any wine's bottles.
 * If the required wine is out of stock, the customer can send a notification request to {@code MySystem}.
 * The system will send a notification when the wine is restocked.
 * 
 * @see MySystem
 * @see Person
 * 
 **/
public class Customer extends Person
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
	public Customer (final String n, final String s, final String e, final String p, final MySystem sys)
	{
		super (n, s, e, p, sys);
	}
	
	/**
	 * 
	 * Subscribes a customer to the system. A customer can only register once.
	 * Inserts customer in CustomerList and sets the attribute loginFlag to true.
	 * 
	 * @return "Already subscribed" if the email is the same of another customer, "Subscribed" or
	 * a error message 
	 * 
	 **/
	public String subscribeCustomer()
	{
		for (Customer toSearch: this.mySystem.getCustomerList()) 
		{
			if (toSearch.getEmail().equals(this.email))
			{
				return ("Already subscribed");
			}
		}
		if(this.getEmail().matches("[0-9a-zA-Zאיטלעש_-]*.?[0-9a-zA-Zאיטלעש_-]*?@[0-9a-zA-Z]*.[0-9a-zA-Z]{2,}")&&this.getEmail().length()<=32&&this.getPassword().length()<=32&&this.getName().matches("[a-zA-Z]*")&&this.getName().length()<=32&&this.getSurname().matches("[a-zA-Z]*")&&this.getSurname().length()<=32)
		{
			this.mySystem.addCustomer(this);
			this.loginFlag = true;
			return ("Subscribed");
		} 
		else if (this.getEmail().length()>32 || !this.getEmail().matches("[0-9a-zA-Zאיטלעש_-]*.?[0-9a-zA-Zאיטלעש_-]*?@[0-9a-zA-Z]*.[0-9a-zA-Z]{2,}"))
		{ 
			return ("Invalid email");
		}
		else if (this.getPassword().length()>32)
		{
			return ("Invalid password");
		}
		else if (this.getName().length()>32 || !this.getName().matches("[a-zA-Zאיטלעש]*"))
		{
			return ("Invalid name");
		}
		else if (this.getSurname().length()>32 || !this.getSurname().matches("[a-zA-Zאיטלעש]*"))
		{
			return ("Invalid surname");
		}
		return ("");
	}
	
	/**
	 * 
	 * Searches wine according to the name, in wineList.
	 * It returns the list of wines that match that name.
	 * 
	 * @param n wine's name
	 * 
	 * @return list of wines with the name n
	 * 
	 **/
	public List<String> searchName(String n)
	{
		List<String> nameWines = new ArrayList<String>();
		boolean found = false;
		for (Wine wineToSearch: this.mySystem.getWineList())
		{
			if (n.toLowerCase().equals(wineToSearch.getName().toLowerCase()))
			{
				found = true;
				nameWines.add(wineToSearch.getAttributes());
			}	
		}
		if (!found)
		{
			nameWines.add("null");
		}
		return nameWines;
	}
	
	/**
	 * 
	 * Searches wine according to the year of production in wineList.
	 * It returns the list of wines that match that year.
	 * 
	 * @param y wine's year
	 * 
	 * @return list of wines with the year y
	 * 
	 **/
	public List<String> searchYear(String y)
	{
		List<String> yearWines = new ArrayList<String>();
		boolean found = false;
		for (Wine wineToSearch: this.mySystem.getWineList())
		{
			if (y.equals(Integer.toString(wineToSearch.getYear())))
			{
				found = true;
				yearWines.add(wineToSearch.getAttributes());
			}	
		}
		if (!found)
		{
			yearWines.add("null");
		}
		return yearWines;
	}
	
	/**
	 * 
	 * Buys wine: if the customer is logged into the system, he creates a new order and a request is sent to the system.
	 * 
	 * @param w Wine
	 * @param n Number
	 * 
	 * @return type of request made by the customer
	 * 
	 **/
	public String buy(Wine w, int n)
	{
		Order o = new Order(this.email, w, n, this.mySystem);
		o.newOrder();
		return (this.mySystem.request(o));
	}
	
	/**
	 * 
	 * If there isn't the requested wine in the system, the customer can place an order. 
	 * The method inserts the couple key (customer's email who request the notification) and value (quantity of bottles) in
	 * the wine's hashtable. The customer will receive a notification as soon as an employee restocks the wine.
	 * 
	 * @param w Wine requested
	 * @param n bottles required
	 * 
	 * @return string which confirms the notification
	 * 
	 **/
	public String receiveNotification(Wine w, int n)
	{
		for (Wine wineToSearch: this.mySystem.getWineList())
		{
			if (wineToSearch.getName().equals(w.getName()) && wineToSearch.getWinemaker().equals(w.getWinemaker()))
			{
				wineToSearch.getNotification().put(this.email, n);
				this.mySystem.getNotificationRequestList().add(email+ "," + w.getName() + "," + w.getWinemaker() + "," + n);
			}
		}
		return ("You will receive a notification when " +  w.getName() +" is restocked");
	}
}