package assegnamento;

/**
 * 
 * The {@code Customer} class defines a Customer instance. This class is used only to store and get 
 * information about customers.
 * 
 **/
public class Customer 
{
	protected String name;
	protected String surname;
	protected String email;
	protected String password;
	protected String loginFlag;
	
	/**
	 * 
	 * Class constructor.
	 * 
	 * @param n name
	 * @param s surname
	 * @param e email
	 * @param p password
	 * @param f "true" if customer is logged in
	 * 
	 **/
	public Customer (final String n, final String s, final String e, final String p, final String f)
	{
		this.name = n;
		this.surname = s;
		this.email = e;
		this.password = p;
		this.loginFlag = f;
	}

	/**
	 *
	 * Gets the name of customer.
	 * 
	 * @return customer's name
	 * 
	 **/
	public String getName() 
	{
		return name;
	}

	/**
	 *
	 * Gets the surname of customer.
	 * 
	 * @return customer's surname
	 * 
	 **/
	public String getSurname()
	{
		return surname;
	}

	/**
	 *
	 * Gets the email of customer.
	 * 
	 * @return customer's email
	 * 
	 **/
	public String getEmail() 
	{
		return email;
	}

	/**
	 *
	 * Gets the password of customer.
	 * 
	 * @return customer's password
	 * 
	 **/
	public String getPassword() 
	{
		return password;
	}

	/**
	 *
	 * Gets the loginFlag of customer.
	 * 
	 * @return customer's loginFlag
	 * 
	 **/
	public String getLoginFlag() 
	{
		return loginFlag;
	}
}