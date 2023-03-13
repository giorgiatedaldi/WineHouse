package assegnamento;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * The {@code MySystem} class manages the WineHouse.
 * It creates lists of {@code Wine}, {@code Order}, {@code Employee}, {@code Customer} and {@code Admin}.
 * An admin can add and edit new employee and display data of wines, orders, customers and employees.
 * A customer can subscribe to the system and, after being logged, buy any wine's bottles.
 * An employee can add new wines and restock them.
 * The system can provide a notification of a restocked wine if asked by the customer. There are two types of notifications'
 * lists: notificationRequestList stores all the requests of notification, so the ordered wine is not restocked yet,
 * notificationsList stores all the messages that have not been read yet by the customers but the wine is available.
 * 
 * @see Wine
 * @see Order
 * @see Customer
 * @see Employee
 * @see Admin
 * 
 **/
public class MySystem 
{
	private List<Wine> wineList;
	private List<Order> orderList;
	private List<Employee> employeeList;
	private List<Customer> customerList;
	private List<Admin> adminList;
	private List<String> notificationsList;
	private List<String> notificationRequestList;
	
	/**
	 * 
	 * Class constructor.
	 * 
	 **/
	public MySystem ()
	{
		this.wineList =  new ArrayList<Wine>();
		this.orderList = new ArrayList<Order>();
		this.employeeList = new ArrayList<Employee>();
		this.customerList = new ArrayList<Customer>();
		this.adminList = new ArrayList<Admin>();
		this.notificationsList = new ArrayList<String>();
		this.notificationRequestList = new ArrayList<String>();
	}
	
	/**
	 * 
	 * Adds employee to employeeList.
	 * 
	 * @param a Admin to add
	 * 
	 **/
	public void addAdmin(Admin a)
	{
		this.adminList.add(a);
	}

	/**
	 * 
	 * Adds customer to customerList.
	 * 
	 * @param c Customer to add
	 * 
	 **/
	public void addCustomer(Customer c)
	{
		this.customerList.add(c);
	}
	
	/**
	 * 
	 * Method to check the login of an employee, a customer or an admin. If login is successful it returns the class' name
	 * of who's logging in and sets the loginFlag to true, otherwise it sends a message of error if credentials are not correct, 
	 * if the user is not subscribed or is trying to log in with different privileges than it has.
	 * 
	 * @param email user's email
	 * @param password user's password
	 * @param c user's type
	 * 
	 * @return "Customer", "Admin" or "Employee" if log in is successful otherwise a message of error.
	 * 
	 **/
	public String updateLogin(String email, String password, String c)
	{
		if (c.equals("Customer"))
		{
			for (Customer toSearch : this.customerList)
			{
				if (email.equals(toSearch.getEmail()))
				{
					if (password.equals(toSearch.getPassword()))
					{
						toSearch.loginFlag = true;	
						return ("Customer");
					}
					else
					{
						return ("Email or password not correct");
					}
				}
			}
			return ("Not subscribed");
		}
		else if (c.equals("Employee"))		{
			for (Employee toSearch : this.employeeList)
			{
				if (email.equals(toSearch.getEmail()))
				{
					if (password.equals(toSearch.getPassword()))
					{
						toSearch.loginFlag = true;
						return ("Employee");
					}
					else
					{
						return ("Email or password not correct");
					}
				}
			}
			return ("Not an Employee");
		}
		else if (c.equals("Admin"))
		{
			for (Admin toSearch : this.adminList)
			{
				if (email.equals(toSearch.getEmail()))
				{
					if (password.equals(toSearch.getPassword()))
					{
						toSearch.loginFlag = true;
						return ("Admin");
					}
					else
					{
						return ("Email or password not correct");
					}
				}
			}
			return ("Not an Admin");	
		}
		return null;
	}
	
	/**
	 * 
	 * Method to logout a person. If person doesn't exist returns the string "Not Subscribed", otherwise returns a message of 
	 * successful logout.
	 * 
	 * @param user class of user who'a logging out
	 * @param email user's email
	 * 
	 * @return "Logout" or "Not Subscribed"
	 *
	 **/
	public String updateLogout(String user, String email)
	{
		if (user.equals("Customer"))
		{
			for (Customer toSearch : this.customerList)
			{
				if (toSearch.getEmail().equals(email))
				{
					toSearch.loginFlag = false;
					return ("Logout");
				}
			}
			return ("Not subscribed"); 
		}
		else if (user.equals("Employee"))
		{
			for (Employee toSearch : this.employeeList)
			{
				if (toSearch.getEmail().equals(email))
				{
					toSearch.loginFlag = false;
					return ("Logout");
				}
			}
			return ("Not subscribed"); 
		}
		else if (user.equals("Admin"))
		{
			for (Admin toSearch : this.adminList)
			{
				if (toSearch.getEmail().equals(email))
				{
					toSearch.loginFlag = false;
					return ("Logout");
				}
			}
			return ("Not subscribed"); 
		}
		return null;
	}

	/**
	 * 
	 * Classifies the type of request made by the customer. If the amount of wine's bottles in the system is enough 
	 * to completely fulfill the customer's order, the request is type 0. If the number of bottles is not enough to fulfill
	 * the customer's order, the request is type 1. If the required wine is out of stock, the request is type 2.
	 * 
	 * @param o Order
	 * 
	 * @return type of request
	 * 
	 **/
	public int requestType(Order o)
	{
		if (o.getWine().getnWine() >= o.getnWineOrder())
		{
			return 0;
		}
		else if (o.getWine().getnWine() < o.getnWineOrder() && o.getWine().getnWine() > 0)
		{
			return 1; 
		}
		else 
		{
			return 2;
		}
	}
	
	/**
	 * 
	 * Prepares the order made by the customer according to the request's type:
	 * 1. type 0: the system subtracts n from the number of wine available;
	 * 2. type 1: the system sets the number of wine to 0;
	 * 3. type 2: the required wine is out of stock.
	 * 
	 * @param o Order
	 * 
	 * @return string of bought wine or out of stock
	 * 
	 **/
	public String request(Order o)
	{
		 for (Order orderToSearch: this.getOrderList())
		 {
			 if (orderToSearch == o)
			 {
				 Wine w = orderToSearch.getWine();
				 
				 int type = this.requestType(orderToSearch);
				 if (type == 0)
				 { 
					 this.setWine(w, w.getnWine() - orderToSearch.getnWineOrder()); 
					 return (""+orderToSearch.getnWineOrder() + " bottles of " +  w.getName() + " bought");
				 }
				 else if (type == 1)
				 {
					 int nOrdered = w.getnWine();
					 this.setWine(orderToSearch.getWine(), 0);
					 orderToSearch.setnWine(nOrdered);
					 return (""+ nOrdered + " of " + orderToSearch.getnWineOrder() + " bottles of " + w.getName() + " bought");
				 }
				 else if (type == 2)
				 {
					 return (o.getWine().getName() + " out of stock");
				 }
			 }
		 }
		 return null;
	}

	/**
	 * The system notifies to the customer if required wine is now available.
	 * If the amount of bottles is enough for the customer the notification's requested is removed from notificationRequestList and
	 * the message is sent to the customer, so added into notificationList (list of notification that have to be read).
	 * 
	 * @param w Wine which has been restocked
	 * @param e customer to notify email
	 * 
	 * @return "Not enough" or a string with customer's email, ordered wine's name and winemaker.
	 * 
	 **/
	public String notifyCustomer(Wine w, String e) 
	{
		if (w.getnWine() >= w.getNotification().get(e).intValue())
		{	
			String toRemove = e + "," + w.getName() + "," + w.getWinemaker() + "," + w.getNotification().get(e).intValue();
			w.getNotification().remove(e);
			this.notificationRequestList.remove(this.notificationRequestList.indexOf(toRemove));
			this.notificationsList.add(e + "," + w.getName()+ "," + w.getWinemaker());
			return (e + "," + w.getName() + "," + w.getWinemaker());
		}
		return ("Not enough");
	}

	/**
	 * 
	 * Searches a specific wine object in wineList and allows to modify it.
	 * 
	 * @param w Wine
	 * @param n number of bottles to which to set the nWine attribute
	 * 
	 **/
	public void setWine(Wine w, int n) 
	{
		for (Wine wineToSearch: this.wineList)
		{
			if (wineToSearch == w)	
			{
				wineToSearch.setnWine(n);
			}
		}
	}

	/**
	 * 
	 * Gets the list of wines.
	 * 
	 * @return wines' list
	 * 
	 **/
	public List<Wine> getWineList() 
	{
		return wineList;
	}


	/**
	 * 
	 * Gets the list of orders.
	 * 
	 * @return orders' list
	 * 
	 **/
	public List<Order> getOrderList() 
	{
		return orderList;
	}

	/**
	 * 
	 * Gets the list employees.
	 * 
	 * @return employees' list
	 * 
	 **/
	public List<Employee> getEmployeeList() 
	{
		return employeeList;
	}

	/**
	 * 
	 * Gets the list of customers.
	 * 
	 * @return customers' list
	 * 
	 **/
	public List<Customer> getCustomerList() 
	{
		return customerList;
	}
	
	/**
	 * 
	 * Gets the list of admin.
	 * 
	 * @return admins' list
	 * 
	 **/
	public List<Admin> getAdminList() 
	{
		return adminList;
	}
	
	/**
	 * 
	 * Gets the list of notifications
	 * 
	 * @return notifications' list
	 * 
	 **/
	public List<String> getNotificationList() 
	{
		return notificationsList;
	}
	
	/**
	 * 
	 * Gets the list of notificationRequest.
	 * 
	 * @return notificationRequest list
	 * 
	 **/
	public List<String> getNotificationRequestList() 
	{
		return notificationRequestList;
	}
}