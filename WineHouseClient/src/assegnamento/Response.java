package assegnamento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
* 
* The {@code Response} class stores a response generated from {@code ServerThread}, sent to a client.
* It includes a list of parameters.
*
**/
public class Response implements Serializable
{
	private static final long serialVersionUID = 1L;
	private List<String> parameters = new ArrayList<String>();
  
	/**
	 *  
	 * Class constructor.
	 * 
	 * @param p list of parameters
	 * 
	 **/
	public Response(List<String> p)
	{
		this.parameters = p;
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