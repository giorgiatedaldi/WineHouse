package assegnamento;

import java.util.Hashtable;

/**
*
* The {@code Wine} class provides all information about the wines that can be bought.
* A wine is identified by name, winemaker, year, technical notes and vine from which it derives.
* There is a an additional attribute: notification which stores the email and the amount of bottles of wine 
* required by the customer.
* 
**/
public class Wine 
{
	private String name;
	private String winemaker;
	private int year;
	private String notes;
	private String vine;
	private int nWine;
	private Hashtable<String, Integer> notification = new Hashtable<String, Integer> ();

	/**
	 *
	 * Class constructor.
	 *
	 * @param nm name
	 * @param w winemaker
	 * @param y year
	 * @param nt technical notes
	 * @param v vines
	 * @param nw number of wine's bottles in stock
	 *
	 **/
	public Wine(final String nm, final String w, final int y, final String nt, final String v, final int nw)
	{
		this.name = nm;
		this.winemaker = w;
		this.year = y;
		this.notes = nt;
		this.vine = v;
		this.nWine = nw;
	}
	
	/**
	 * 
	 * Method to return a string of all attributes.
	 * 
	 * @return string of attributes
	 *
	 **/
	public String getAttributes()
	{
		return (this.name + "," + this.winemaker + "," + Integer.toString(this.year) + "," + this.notes + "," + this.vine + "," + Integer.toString(this.nWine) );	
	}
	
	/**
	 *
	 * Gets wine's name.
	 *
	 * @return wine's name
	 *
	 **/
	public String getName() 
	{
		return name;
	}

	/**
	 *
	 * Gets wine's winemaker.
	 *
	 * @return wine's winemaker
	 *
	 **/
	public String getWinemaker() 
	{
		return winemaker;
	}

	/**
	 *
	 * Gets wine's year.
	 *
	 * @return wine's year
	 *
	 **/
	public int getYear() 
	{
		return year;
	}

	/**
	 *
	 * Gets wine's technical notes.
	 *
	 * @return wine's technical notes
	 *
	 **/
	public String getNotes() 
	{
		return notes;
	}

	/**
	 *
	 * Gets wine's vine.
	 *
	 * @return wine's vine
	 *
	 **/
	public String getVine() 
	{
		return vine;
	}

	/**
	 *
	 * Gets the number of wine's bottles in stock.
	 *
	 * @return the number of bottles in stock
	 *
	 **/
	public int getnWine() 
	{
		return nWine;
	}

	/**
	 *
	 * Sets the number of wine's bottles in stock.
	 *
	 * @param n new number of bottles in stock
	 *
	 **/
	public void setnWine(int n)
	{
		this.nWine = n;
	}


	/**
	 * 
	 * Gets the notifications' hashtable.
	 * 
	 * @return notifications' hashtable
	 * 
	 **/
	public Hashtable<String, Integer> getNotification()
	{
		return this.notification;
	}
}