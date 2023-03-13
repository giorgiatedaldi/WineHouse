package assegnamento;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * The class {@code Client} defines a client that sends a {@code Request} to a server and receives 
 * its {@code Response}.
 * 
 * @see Request
 * @see Response
 *
**/
public class Client
{
	private static final int SPORT = 4700;
	private static final String SHOST = "localhost";
	private ObjectOutputStream os;
	private ObjectInputStream  is;
	private Response response;
	private Socket client;
  
	/**
	 * 
	 * Runs the client code.
	 *
	 **/
	public Client()
	{
		run();
	}
  
	/**
	 * 
	 * Closes the client and the input/output streams.
	 * 
	 **/
	public void close()
	{
		try 
		{
			this.client.close();
			System.exit(0);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Creates the socket to communicate with server, and the input/output streams.
	 * 
	 **/
	public void run()
	{	
		try 
		{
			client = new Socket(SHOST, SPORT);
			os = new ObjectOutputStream(client.getOutputStream());
			is = new ObjectInputStream((client.getInputStream()));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}	
  }
  
	/**
	 * 
	 * Sends a request to the server.
	 * 
	 * @param r Request to send 
	 * @throws IOException error in sending request
	 * 
	 **/
	public void sendRequest(Request r) throws IOException
	{
		try 
		{
			os.writeObject(r);
		} 
		catch (IOException e) 
		{
			this.close();
			e.printStackTrace();	
		}			  
	}
  
	/**
	 * 
	 * Waits for the server's response.
	 * 
	 * @return server's response for the specific request.
	 * 
	 **/
	public Response receiveResponse() 
	{
		Object i = null;
		try 
		{
			i = is.readObject();
		} 
		catch (ClassNotFoundException | IOException e) 
		{
			this.close();
			e.printStackTrace();
		}
		if (i instanceof Response)
		{
			Response rs = (Response) i;
			this.response = rs;
		}
		return this.response;
  } 
}