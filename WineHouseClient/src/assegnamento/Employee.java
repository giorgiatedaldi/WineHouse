package assegnamento;

/**
 * 
 * The {@code Employee} class defines an employee instance. This class is used only store and get 
 * information about employees.
 *  
 **/
public class Employee 
{
	protected String name;
	protected String surname;
	protected String email;
	protected String password;
	protected String employeeLoginFlag;
	
	/**
	 * 
	 * Class constructor.
	 * 
	 * @param n name
	 * @param s surname
	 * @param e email
	 * @param p password
	 * @param f loginFlag
	 * 
	 **/
	public Employee (final String n, final String s, final String e, final String p, final String f)
	{
		this.name = n;
		this.surname = s;
		this.email = e;
		this.password = p;
		this.employeeLoginFlag = f;
	}

	/**
	 * 
	 * Gets employee's name.
	 * 
	 * @return employee's name
	 * 
	 **/
	public String getName() 
	{
		return name;
	}

	/**
	 * 
	 * Gets employee's surname.
	 * 
	 * @return employee's surname
	 * 
	 **/
	public String getSurname() 
	{
		return surname;
	}

	/**
	 * 
	 * Gets employee's email.
	 * 
	 * @return employee's email
	 * 
	 **/
	public String getEmail() 
	{
		return email;
	}
	
	/**
	 * 
	 * Gets employee's password.
	 * 
	 * @return employee's password
	 * 
	 **/
	public String getPassword() 
	{
		return password;
	}

	/**
	 * 
	 * Gets employee's loginFlag.
	 * 
	 * @return employee's loginFlag
	 * 
	 **/
	public String getEmployeeLoginFlag() 
	{
		return employeeLoginFlag;
	}
}