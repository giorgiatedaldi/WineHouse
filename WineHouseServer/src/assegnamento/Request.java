package assegnamento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
* 
* The {@code Request} class stores a request sent to {@code Server} handled by {@code ServerThread}. 
* It includes a string that represents the request and a list of parameters.
* 
* @see Server
* @see ServerThread 
*
**/
public class Request implements Serializable
{
	private static final long serialVersionUID = 1L;
	private final String value;
	private  List<String> parameters = new ArrayList<String>();

	/**
	 * 
	 * Class constructor.
	 * 
	 * @param v value of request
	 * @param p list of parameters
	 *   
	 **/
	public Request(final String v, List<String> p)
	{
		this.value = v;
		this.parameters=p;
	}

	/**
	   * 
	   * Gets the request's value.
	   * 
	   * @return request's name
	   * 
	   **/
	public String getValue()
	{
		return this.value;
	}
  
	 /**
	   * 
	   * Get the parameters' list.
	   * 
	   * @return parameters' list
	   * 
	   **/
	public List<String> getParameters()
	{
		return this.parameters;
	}
}