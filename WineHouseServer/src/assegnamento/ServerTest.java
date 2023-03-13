package assegnamento;

import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * 
 * The {@code ServerTest} class manages the tests on every steps that a subscribed customer has to do
 * to buy wines' bottles. It's created a specific {@code MySystem} object to test the methods.
 * 
 * @see MySystem
 *
 **/
public class ServerTest 
{	
	public static MySystem mySys = new MySystem();
	public static Customer c = new Customer("Pietro", "Bianchi", "pb@gmail.com", "pb", mySys);
	public static Wine w1 = new Wine ("Franciacorta", "Fratelli Berlucchi", 2008, "White and sparkling", "Chardonnay", 30);
	public static Wine w2 = new Wine ("Franciacorta", "Mirabella", 2017, "Rosè", "Chardonnay", 30);
	public static Wine w3 = new Wine ("Moscato giallo", "Colterenzio", 2016, "White", "Moscato giallo", 0);
	
	/**
	 * 
	 * This method sets up the MySystem object by adding the instances of admin, employee and wines needed
	 * to perform the tests.
	 * 
	 **/
	@BeforeAll
	public static void setUp()
	{
		Admin a1 = new Admin("Giorgia", "Tedaldi", "gt@gmail.com", "gt", mySys);
		mySys.addAdmin(a1);
		Employee e1 = new Employee ("Mario", "Rossi", "mr@gmail.com", "mr", mySys);
		a1.addEmployee(e1);
		c.subscribeCustomer();
		e1.addWine(w1);
		e1.addWine(w2);
		e1.addWine(w3);
	}
	
	/**
	 * 
	 * Performs the tests for login method. In the first case the customer is not subscribed, in the
	 * second one it's subscribed but the password is not correct, in the last one the customer's login
	 * was successful.
	 * 
	 **/
	@Test
	public void loginTest()
	{
		Customer c1 = new Customer("Caterina", "Neri", "cn@gmail.com", "cn", mySys);
		Customer c2 = new Customer("Pietro", "Bianchi", "pb@gmail.com", "pl", mySys);
 		assertTrue(c1.login().equals("Not subscribed"));
		assertTrue(c2.login().equals("Email or password not correct"));
		assertTrue(c.login().equals("Customer"));
	}
	
	/**
	 * 
	 * Performs the tests for the method to search wines by name. In the first case the wine searched
	 * is not found. The method "searchName" returns a list of wines that matches the research, in this
	 * case, every element of the list is tested to control that the name is the one expected.
	 * 
	 **/
	@Test
	public void searchNameTest()
	{
		List<String> notFound = c.searchName("Ranciacorta");
		assertTrue(notFound.get(0).equals("null"));
		List<String> found = c.searchName("Franciacorta");
		for(int i = 0; i < found.size(); i++)
		{
			String splitted[] = found.get(i).split(",");
			assertTrue(splitted[0].equals("Franciacorta"));
		}
	}
	
	/**
	 * 
	 * Performs the tests for the method to search wines by year. In the first case the wine searched
	 * is not found. The method "searchYear" returns a list of wines that matches the research, in this
	 * case, every element of the list is tested to control that the year is the one expected.
	 * 
	 **/
	@Test
	public void searchYearTest()
	{
		List<String> notFound = c.searchYear("2009");
		assertTrue(notFound.get(0).equals("null"));
		List<String> found = c.searchYear("2017");
		for(int i = 0; i < found.size(); i++)
		{
			String splitted[] = found.get(i).split(",");
			assertTrue(splitted[2].equals("2017"));
		}
	}
	
	/**
	 * 
	 * Performs the tests for the buy method. In the first case the customer is not subscribed, in the
	 * second one it's subscribed but not logged in. In the other cases (when the customer is logged in)
	 * there are three types of possible outputs. The customer can buy less/more bottles than those in the 
	 * store or the wine chosen is out of stock.
	 * 
	 **/
	@Test
	public void buyTest()
	{
		Customer c1 = new Customer("Caterina", "Neri", "cn@gmail.com", "cn", mySys);
		assertTrue(c1.buy(w1, 5).equals("Not subscribed"));
		c.logout();
		assertTrue(c.buy(w1, 5).equals("Not logged in"));
		c.login();
		String typeZero = "4 bottles of Franciacorta bought";
		String typeOne = "30 of 35 bottles of Franciacorta bought";
		String typeTwo = "Moscato giallo out of stock";
		assertTrue(c.buy(w1, 4).equals(typeZero));
		assertTrue(c.buy(w2, 35).equals(typeOne));
		assertTrue(c.buy(w3, 10).equals(typeTwo));
	}
}