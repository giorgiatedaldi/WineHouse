package assegnamento;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * The {@code ServerThread} class manages the interaction with a client of the server.
 * It process {@code Request} from clients and sends {@code Response}.
 * 
 * @see Server
 * @see Request
 * @see Response
 * 
 **/
public class ServerThread implements Runnable 
{
	private static final long SLEEPTIME = 200;
	private Server server;
	private Socket socket;
	private MySystem mySystem;
	private ObjectOutputStream os;
	private ObjectInputStream is;
	private boolean connected = true;

	/**
	 * 
	 * Class constructor.
	 * 
	 * @param s Server
	 * @param c Socket
	 * @param sys MySystem
	 * 
	 **/ 
	public ServerThread(final Server s, final Socket c, MySystem sys) 
	{
		this.server = s;
		this.socket = c;
		this.mySystem = sys;
	}

	/**
	 * 
	 * Method to send a response, writes the response on the outputStream.
	 * 
	 * @param r Response
	 * 
	 **/
	public void sendResponse(Response r)
	{
		try 
		{
			os.writeObject(r);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Creates input/output stream.
	 * The method receives a request that has a value that represents its type and a list of parameters.
	 * According to request, it sends a new response with a list of string.
	 * Once the attribute "connected" is not true anymore the server, the socket and input/output streams 
	 * are closed.
	 * 
	 **/
	@Override
	public void run() 
	{
		is = null;
		os = null;
		try
		{
			is = new ObjectInputStream(this.socket.getInputStream());
			os = new ObjectOutputStream(this.socket.getOutputStream());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return;
		}
		String.valueOf(this.hashCode());
		while (connected) 
		{
			try
			{
				Object i = is.readObject();
				List<String> listRs = new ArrayList<String>();
				if (i instanceof Request) 
				{
					Request rq = (Request) i;
					switch (rq.getValue()) 
					{
						//Sends wines' list.
						case "WINES":
							for (Wine w : this.mySystem.getWineList()) 
							{
								listRs.add(w.getAttributes());
							}
							break;
						//Sends orders' list.
						case "ORDERS":
							for (Order o : this.mySystem.getOrderList()) 
							{
								listRs.add(o.getAttributes());
							}
							break;
						//Sends customers' list.	
						case "CUSTOMERS": 
							for (Customer c : this.mySystem.getCustomerList())
							{
								listRs.add(c.getAttributes());
							}
							break;
						//Sends employees' list.
						case "EMPLOYEES":
							for (Employee e : this.mySystem.getEmployeeList()) 
							{
								listRs.add(e.getAttributes());
							}
							break;
						//Tries to log in the person and sends the feedback.
						case "LOGIN":
							if (rq.getParameters().size() == 3) 
							{
								if (rq.getParameters().get(0).equals("Customer")) 
								{
									Customer c = new Customer("", "", rq.getParameters().get(1), rq.getParameters().get(2), this.mySystem);
									listRs.add(c.login());
								}
								if (rq.getParameters().get(0).equals("Employee"))
								{
									Employee e = new Employee("", "", rq.getParameters().get(1), rq.getParameters().get(2), this.mySystem);
									listRs.add(e.login());
								}
								if (rq.getParameters().get(0).equals("Admin"))
								{
									Admin a = new Admin("", "", rq.getParameters().get(1), rq.getParameters().get(2), this.mySystem);
									listRs.add(a.login());
								}
							}
							break;
						//Tries to sign up the customer and sends the feedback.
						case "SIGNUP":
							if (rq.getParameters().size() == 4) 
							{
								Customer c = new Customer(rq.getParameters().get(0), rq.getParameters().get(1), rq.getParameters().get(2), rq.getParameters().get(3), this.mySystem);
								listRs.add(c.subscribeCustomer());
							}
							break;
						//Tries to log out the person and sends the feedback.
						case "LOGOUT":
							if (rq.getParameters().size() == 2) 
							{
								if (rq.getParameters().get(0).equals("Customer")) 
								{
									Customer c = new Customer("", "", rq.getParameters().get(1), "", this.mySystem);
									listRs.add(c.logout());
								}
								if (rq.getParameters().get(0).equals("Employee"))
								{
									Employee e = new Employee("", "", rq.getParameters().get(1), "", this.mySystem);
									listRs.add(e.logout());
								}
								if (rq.getParameters().get(0).equals("Admin")) 
								{
									Admin a = new Admin("", "", rq.getParameters().get(1), "", this.mySystem);
									listRs.add(a.logout());
								}
							}
							break;
						//Sends the wines' list that match the research according to name and year.
						case "SEARCH":
							if (rq.getParameters().size() == 2)
							{
								List<String> foundWines = new ArrayList<String>();
								Customer c = new Customer("", "", "", "", this.mySystem);
								if (rq.getParameters().get(0).equals("NAME")) 
								{
									foundWines = c.searchName(rq.getParameters().get(1));
									for (String s : foundWines) 
									{
										listRs.add(s);
									}
	
								} 
								else if (rq.getParameters().get(0).equals("YEAR")) 
								{									
									foundWines = c.searchYear(rq.getParameters().get(1));
									for (String s : foundWines)
									{
										listRs.add(s);
									}
								}
							}
							break;
						//Buys the wine and sends the feedback of the buy action.
						case "BUY":
							if (rq.getParameters().size() == 4) 
							{
								List<String> param = rq.getParameters();
								Customer c = new Customer("", "", param.get(0), "", this.mySystem);
								Wine wFound = c.findWine(param.get(2), param.get(3));
								String bought = c.buy(wFound, Integer.parseInt(param.get(1)));
								listRs.add(bought);
							}
							break;
						//Requests a notification and sends the feedback.
						case "NOTIFY":
							if (rq.getParameters().size() == 4) 
							{
								List<String> param = rq.getParameters();
								Customer c = new Customer("", "", param.get(0), "", this.mySystem);
								Wine wFound = c.findWine(param.get(1), param.get(2));
								String notification = c.receiveNotification(wFound, Integer.parseInt(param.get(3)));
								listRs.add(notification);
							}
							break;
						//Tries to add an employee and sends the feedback.
						case "ADDEMPLOYEE":
							if (rq.getParameters().size() == 4) 
							{
								List<String> param = rq.getParameters();
								Admin a = new Admin("", "", "", "", this.mySystem);
								Employee e = new Employee(param.get(0), param.get(1), param.get(2), param.get(3), this.mySystem);
								listRs.add(a.addEmployee(e));
							}
							break;
						//Tries to edit an employee's parameter and sends the feedback.
						case "SETEMPLOYEE":
							if (rq.getParameters().size() == 5) 
							{
								List<String> param = rq.getParameters();
								Employee e = new Employee(param.get(1), param.get(2), param.get(3), param.get(4), this.mySystem);
								Admin a = new Admin("", "", "", "", this.mySystem);
								listRs.add(a.editEmployee(param.get(0), e));
							}
							break;
						//Tries to remove an employee and sends the feedback.
						case "REMOVEEMPLOYEE":
							if (rq.getParameters().size() == 1) {
								List<String> param = rq.getParameters();
								Admin a = new Admin("", "", "", "", this.mySystem);
								listRs.add(a.removeEmployee(param.get(0)));
							}
							break;
						//Tries to add a wine and sends the feedback.
						case "ADDWINE":
							if (rq.getParameters().size() == 6)
							{
								List<String> param = rq.getParameters();
								Wine w = new Wine(param.get(0), param.get(1), Integer.parseInt(param.get(2)), param.get(3), param.get(4), Integer.parseInt(param.get(5)));
								Employee e = new Employee("", "", "", "", this.mySystem);
								listRs.add(e.addWine(w));
							}
							break;
						//Restocks wine and sends the list of all the notification's requests satisfied after the restock.
						case "RESTOCKWINE":
							if (rq.getParameters().size() == 3) 
							{
								List <String> param = rq.getParameters();
								Employee e = new Employee("", "", "", "", this.mySystem);
								e.restockWine(param.get(0), param.get(1), Integer.parseInt(param.get(2)));
								listRs.add("Restocked");
							}
							break;
						//Sends the notifications' list to read.
						case "DISPLAYNOTIFICATION":
							for (String s : this.mySystem.getNotificationList()) 
							{
								listRs.add(s);
							}
							break;
						//The notifications of a specific customer have been read so are removed from the list.
						case "NOTIFICATIONREAD":
							for (int x=0; x<mySystem.getNotificationList().size(); x++)
							{
								String splitted[] = mySystem.getNotificationList().get(x).split(",");
								if (splitted[0].equals(rq.getParameters().get(0)))
								{
									mySystem.getNotificationList().remove(mySystem.getNotificationList().get(x));
									x--;
								}
							}
							listRs.add("Removed");
							break;
						//Closes the server.
						case "CLOSESERVER":
							if (rq.getParameters().size()== 0)
							{
								listRs.add("Close server");
								this.connected = false;
								break;
							}
						}
						Thread.sleep(SLEEPTIME);
						Response response = new Response(listRs);
						os.writeObject(response);
						os.flush();
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				this.connected = false;
			}
		}
		try 
		{
			socket.close();
			is.close();
			os.close();
			server.close();
			System.exit(0);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}