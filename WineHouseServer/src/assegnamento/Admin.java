package assegnamento;

/**
 * 
 * The {@code Admin} class defines an admin instance. It is a {@code Person} subclass.
 * An admin can add an employee to the employeeList, in {@code MySystem}, and he can edit or remove an employee.
 * 
 * @see MySystem
 *  
 **/
public class Admin extends Person
{
	/**
	 * 
	 * Class constructor.
	 * 
	 * @param n name
	 * @param s surname 
	 * @param e email
	 * @param p password
	 * @param sys MySystem
	 * 
	 **/
	public Admin (final String n, final String s, final String e, final String p, final MySystem sys)
	{
		super (n, s, e, p, sys);
	}
	
	/**
	 * 
	 * Method to edit an employee. If the old email exists on the system and the logingFlag is true, admin can't edit the employee.
	 * If the employee is not logged the new email has to be different from all the others. If  the new employee's email doesn't exist 
	 * admin can edit the employee.
	 * 
	 * @param s employee's old email
	 * @param e edited employee
	 * 
	 * @return "Employee logged" or a message of error
	 * 
	 **/
	public String editEmployee(String s, Employee e)
	{
		for (Employee toSearch: this.mySystem.getEmployeeList()) 
		{
			if (toSearch.getEmail().equals(s))
			{
				if (!toSearch.loginFlag)
				{
					this.mySystem.getEmployeeList().remove(this.mySystem.getEmployeeList().indexOf(toSearch));
				}
				else
				{
					return("Employee logged");
				}
				String toAdd = this.addEmployee(e);
				if (toAdd.equals("Already added") || toAdd.equals("Invalid email") || toAdd.equals("Invalid password"))
				{
					this.addEmployee(toSearch);
				}
				return(toAdd);
			}
		}
		return null;
	}
	
	/**
	 * 
	 * Method to add a new employee if it doesn't already exist in employeeList.
	 * 
	 * @param e employee to add
	 * 
	 * @return "Done" if the employee it's correctly added, "Already added" if the email is the same of
	 * another employee, or an error message
	 * 
	 **/
	public String addEmployee(Employee e)
	{
		if(!e.getEmail().matches("[0-9a-zA-Zאיטלעש_-]*.?[0-9a-zA-Zאיטלעש_-]*?@[0-9a-zA-Zאיטלעש]*.[0-9a-zA-Z]{2,}")||e.getEmail().length()>32) 
		{
			return ("Invalid email");
		}
		if(e.getPassword().length()>32) 
		{
			return ("Invalid password");
		}
		if(!e.getName().matches("[a-zA-Zאיטלעש]*")||e.getName().length()>32) 
		{
			return ("Invalid name");
		}
		if(!e.getSurname().matches("[a-zA-Zאיטלעש]*")||e.getSurname().length()>32) 
		{
			return ("Invalid surname");
		}
		for(Employee toSearch: this.mySystem.getEmployeeList())
		{
			if (toSearch.getEmail().equals(e.getEmail()))
			{
				return ("Already added");
			}
		}
		this.mySystem.getEmployeeList().add(e);
		return ("Done");
	}
	
	/**
	 * 
	 * Method to remove an employee if he exists in employeeList and he isn't logged in.
	 * 
	 * @param e employee to remove email
	 * 
	 * @return "removed" or "Not removed"
	 *  
	 **/
	public String removeEmployee(String e)
	{
		int index = 0;
		boolean removed = false;
		for (Employee toSearch: this.mySystem.getEmployeeList())
		{
			if (toSearch.getEmail().equals(e) && !toSearch.loginFlag)
			{
				index = this.mySystem.getEmployeeList().indexOf(toSearch);
				removed = true;	
			}
		}
		if (removed)
		{
			this.mySystem.getEmployeeList().remove(index);
			return ("Removed");
		}
		else
		{
			return ("Not removed");
		}
	}
}