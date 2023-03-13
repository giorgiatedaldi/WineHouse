package assegnamento;

/**
 * 
 * The {@code Person} class defines a person instance: name, surname, email and password.
 * It has three subclasses: {@code Customer}, {@code Employee} and {@code Admin}. A customer can 
 * search/buy wine's bottles. If the required wine is not available he can also request a notification 
 * from the system when it's restocked.
 * An employee manages customers' requests and can acquire new wine's bottles.
 * An admin can add a new employee or change credentials of an existing one, he can also view information
 * of customers, orders, wines and employees.
 * 
 * @see Employee
 * @see Customer
 * @see Admin
 *
 **/
public class Person 
{
	protected String name;
	protected String surname;
	protected String email;
	protected String password;
	protected MySystem mySystem;
	protected boolean loginFlag = false;

	/**
	 * 
	 * Class constructor.
	 * 
	 * @param n name
	 * @param s surname
	 * @param e email
	 * @param p password
	 * @param sys mySystem
	 * 
	 **/
	public Person (final String n, final String s, final String e, final String p, final MySystem sys)
	{
		this.name = n;
		this.surname = s;
		this.email = e;
		this.password = p;
		this.mySystem = sys;
	}
	
	/**
	 * 
	 * Gets person's name.
	 * 
	 * @return person's name
	 * 
	 **/
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * 
	 * Gets person's surname.
	 * 
	 * @return person's surname
	 * 
	 **/
	public String getSurname()
	{
		return this.surname;
	}
	
	/**
	 * 
	 * Gets person's email.
	 * 
	 * @return person's email
	 * 
	 **/
	public String getEmail()
	{
		return this.email;
	}
	
	/**
	 * 
	 * Gets person's password.
	 * 
	 * @return person's password
	 * 
	 **/
	public String getPassword()
	{
		return this.password;
	}
	
	/**
	 * 
	 * Method to find wine in wineList by its key (name and winemaker), if wine is founded return w otherwise it return null.
	 * 
	 * @param name of wine
	 * @param winemaker wine's producer
	 * 
	 * @return found wine or null
	 *
	 **/
	public Wine findWine(String name, String winemaker)
	{
		for (Wine w: this.mySystem.getWineList())
		{
			if (w.getName().equals(name) && w.getWinemaker().equals(winemaker))
			{
				return w;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * Method to log in a person trough email and password.
	 * 
	 * @return feedback of login
	 * 
	 **/
	public String login()
	{
		return (this.mySystem.updateLogin(this.email, this.password, this.getClass().getSimpleName()));
	}

	/**
	 * 
	 * Method to logout a person trough email.
	 * 
	 * @return feedback of logout
	 * 
	 **/
	public String logout()
	{
		return (this.mySystem.updateLogout(this.getClass().getSimpleName(), this.email));
	}
	
	/**
	 * 
	 * Gets all the attributes of person. 
	 * 
	 * @return String of attributes
	 * 
	 **/
	public String getAttributes()
	{
		return(this.name + "," + this.surname + ","+ this.email + "," + password + "," + Boolean.toString(this.loginFlag));
	}
}