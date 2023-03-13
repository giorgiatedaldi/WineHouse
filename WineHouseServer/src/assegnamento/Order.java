package assegnamento;

/**
*
* The {@code Order} class provides creation and storage of customers' orders.
* It includes the number of required wine's bottles, the object {@code Wine} and customer's email to specify
* who and what is ordered, and an object {@code MySystem} which is the system that manages the sales operations.
*
* @see Customer
* @see Wine
* @see MySystem
*
**/
public class Order 
{
	private int nOrderWine;
	private Wine wine;
	private MySystem mySystem;
	private String customerEmail;

	/**
	 *
	 * Class constructor.
	 *
	 * @param e customer's email
	 * @param w wine ordered
	 * @param n ordered wine's bottles
	 * @param sys MySystem
	 *
	 **/
	public Order (final String e, final Wine w, final int n, final MySystem sys)
	{
		this.customerEmail = e;
		this.wine = w;
		this.nOrderWine = n;
		this.mySystem = sys;
	}
	
	/**
	 *
	 * Creates a new order and appends it to the orderList.
	 *
	 **/
	public void newOrder()
	{
		this.mySystem.getOrderList().add(this);
	}

	/**
	 *
	 * Gets the object wine which is ordered.
	 *
	 * @return Ordered wine
	 *
	 **/
	public Wine getWine() 
	{
		return wine;
	}

	/**
	 *
	 * Sets the type of wine which has to be put in the order.
	 *
	 * @param wine Ordered wine
	 *
	 **/
	public void setWine(Wine wine) 
	{
		this.wine = wine;
	}

	/**
	 *
	 * Gets the number of wine's bottles which have been ordered.
	 *
	 * @return The number of ordered wine's bottles
	 *
	 **/
	public int getnWineOrder() 
	{
		return nOrderWine;
	}

	/**
	 *
	 * Sets the number of wine's bottles in the order.
	 *
	 * @param nWine Number of wine's bottles
	 *
	 **/
	public void setnWine(int nWine) 
	{
		this.nOrderWine = nWine;
	}

	/**
	 *
	 * Gets the system which has to handle the sales operations.
	 *
	 * @return The system which operates
	 *
	 **/
	public MySystem getMySystem() 
	{
		return mySystem;
	}
	
	/**
	 *
	 * Gets the customer's email who orders the wine.
	 *
	 * @return the customer's email involved in the order
	 *
	 **/
	public String getCustomerMail()
	{
		return this.customerEmail;
	}
	
	/**
	 * 
	 * Gets all the attributes of order.
	 * 
	 * @return wine's attributes
	 *
	 **/
	public String getAttributes()
	{
		return (this.wine.getName() + "," + this.wine.getWinemaker() + "," + this.customerEmail + "," + Integer.toString(this.nOrderWine));	
	}
}