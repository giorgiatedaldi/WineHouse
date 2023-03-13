package assegnamento;

/**
*
* The {@code Wine} class provides all information about the wines that can be bought.
* A wine is identified by name, winemaker, year, technical notes and vine from which it derives.
* This class is used only to store and get information about wines.
* 
**/
public class Wine 
{
	private String name;
	private String winemaker;
	private int year;
	private String notes;
	private String vine;
	private int amount;
	
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
		this.amount = nw;
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
	public int getAmount() 
	{
		return amount;
	}
}