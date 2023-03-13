package assegnamento;

/**
*
* The {@code Order} class provides creation and storage of customers' orders.
* It includes the number of required wine's bottles, the wine's name and winemaker, and who makes the order.
* This class is used only to store and get information about orders.
* 
**/
public class Order 
{
	private String wineName;
	private String winemaker;
	private String customerEmail;
	private int quantity;
	
	/**
	 *
	 * Class constructor.
	 *
	 * @param wn wine's name
	 * @param wm winemaker
	 * @param e customer's email who makes the order
	 * @param q number of bottles required
	 *
	 **/
	public Order (final String wn, final String wm, final String e, final int q)
	{
		this.wineName = wn;
		this.winemaker = wm;
		this.customerEmail = e;
		this.quantity = q;	
	}

	/**
	 *
	 * Gets wine's name.
	 *
	 * @return wine's name
	 *
	 **/
	public String getWineName() 
	{
		return wineName;
	}
	
	/**
	 *
	 * Gets the winemaker.
	 *
	 * @return winemaker
	 *
	 **/
	public String getWinemaker() 
	{
		return winemaker;
	}
	
	/**
	 * 
	 * Gets customer's email.
	 * 
	 * @return customer's email
	 * 
	 **/
	public String getCustomerEmail() 
	{
		return customerEmail;
	}
	
	/**
	 * 
	 * Gets the number of wine's bottles ordered.
	 * 
	 * @return quantity of bottles
	 * 
	 **/
	public int getQuantity() 
	{
		return quantity;
	}

	/**
	 * 
	 * Gets all the attributes of order.
	 * 
	 * @return String of order's attributes
	 *
	 **/
	public String getAttributes()
	{
		return (wineName + "," + winemaker + "," + customerEmail + "," + Integer.toString(quantity));	
	}
}