package assegnamento;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
* 
* @author Giorgia Tedaldi
* @author Martina Frati
* @author Diletta Quarticelli
* 
**/

/**
* The {@code Server} class creates the ServerSocket and perform communications with clients.
* Server communicates with the database and takes and saves data from the database.
* 
**/
public class Server
{
	private static final int COREPOOL = 5;
	private static final int MAXPOOL = 100;
	private static final long IDLETIME = 5003;
	private static final int SPORT = 4692;
	private static MySystem mySystem = new MySystem();
	private ServerSocket socket;
	private ThreadPoolExecutor pool;
	private static final String DBURL = "jdbc:mysql://localhost:3306/winehouse?";
	private static final String ARGS = "createDatabaseIfNotExist=true&serverTimezone=UTC";
	private static final String EMAIL = "root";
	private static final String PASSWORD = "";
	private Connection connection;
	private Statement statement;

	/**
	 * 
	 * Class constructor.
	 * The server creates the socket and the database if it doesn't already exist.
	 *
	 **/
	public Server()
	{
		try
		{
			this.socket = new ServerSocket(SPORT);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		try 		
		{
			this.connection = DriverManager.getConnection(DBURL + ARGS, EMAIL, PASSWORD);
			this.statement = connection.createStatement();
			String createTableAdmin = "create table if not exists admins("
					+ "Name varchar(20) not null,"
					+ "Surname varchar(20) not null,"
					+ "Email varchar(20) primary key,"
					+ "Password varchar(20) not null)";
			statement.executeUpdate(createTableAdmin);
			String createTableEmployee = "create table if not exists employees("
					+ "Name varchar(20) not null,"
					+ "Surname varchar(20) not null,"
					+ "Email varchar(20) primary key,"
					+ "Password varchar(20) not null)";
			statement.executeUpdate(createTableEmployee);
			String createTableCustomer = "create table if not exists customers("
					+ "Name varchar(20) not null,"
					+ "Surname varchar(20) not null,"
					+ "Email varchar(20) primary key,"
					+ "Password varchar(20) not null)";
			statement.executeUpdate(createTableCustomer);
			String createTableWine = "create table if not exists wines("
					+ "Name varchar(20) not null,"
					+ "Winemaker varchar(20) not null,"
					+ "Year numeric(6) not null,"
					+ "Notes varchar(60) not null,"
					+ "Vine varchar(20) not null,"
					+ "Amount numeric(6) not null,"
					+ "unique(Name, Winemaker))";
			statement.executeUpdate(createTableWine);
			String createTableOrder = "create table if not exists orders("
					+ "Customer varchar(20) not null,"
					+ "Quantity numeric(6) not null,"
					+ "Name varchar(20) not null,"
					+ "Winemaker varchar(20) not null)";
			statement.executeUpdate(createTableOrder);
			String createTableNotificaton = "create table if not exists notifications("
					+ "Customer varchar(20) not null,"
					+ "WineName varchar(20) not null,"
					+ "Winemaker varchar(20) not null)";
			statement.executeUpdate(createTableNotificaton);
			String createTableNotificationRequest = "create table if not exists notificationrequest("
					+ "Customer varchar(20) not null,"
					+ "WineName varchar(20) not null,"
					+ "Winemaker varchar(20) not null,"
					+ "Quantity numeric(6) not null)";
			statement.executeUpdate(createTableNotificationRequest);
			getDatabase();
			Admin a1 = new Admin("Giorgia", "Tedaldi", "gt@gmail.com", "gt", mySystem);
			Admin a2 = new Admin("Diletta", "Quarticelli", "dq@gmail.com", "dq", mySystem);
			Admin a3 = new Admin("Martina", "Frati", "mf@gmail.com", "mf", mySystem); 
			mySystem.addAdmin(a1);
			mySystem.addAdmin(a2); 
			mySystem.addAdmin(a3); 
			Employee e1 = new Employee ("Mario", "Rossi", "mr@gmail.com", "mr", mySystem);
			Employee e2 = new Employee ("Cristina", "Neri", "cn@gmail.com", "cn", mySystem);
			a1.addEmployee(e1);
			a2.addEmployee(e2);
			Wine w1 = new Wine ("Marcello", "Ariola", 2018, "Red and sparkling", "Lambrusco maestri", 30);
			Wine w2 = new Wine ("Trento DOC", "Ferrari", 2002, "White and sparkling", "Chardonnay", 30);
			Wine w3 = new Wine ("Otello", "Ceci", 2013, "Red and sparkling", "Lambrusco maestri", 30);
			Wine w4 = new Wine ("Bolla", "Bardolino", 2019, "Rosè", "Corvina", 30);
			Wine w5 = new Wine ("Chianti", "Ruffino", 2019, "Red and still", "Sangiovese", 30);
			Wine w6 = new Wine ("Chainti", "Cecchi", 2017, "Red and still", "Sangiovese", 30);
			Wine w7 = new Wine ("Franciacorta", "Fratelli Berlucchi", 2008, "White and sparkling", "Chardonnay", 30);
			Wine w8 = new Wine ("Franciacorta", "Mirabella", 2017, "Rosè", "Chardonnay", 30);
			Wine w9 = new Wine ("Moscato giallo", "Colterenzio", 2016, "White", "Moscato giallo", 30);
			Wine w10 = new Wine ("Valdobbiadene", "Adami", 2018, "Prosecco", "Glera", 30);
			Wine w11 = new Wine ("Lugana DOC", "Famiglia Olivini", 2007, "White", "Trebbiano di Lugana", 30);
			Wine w12 = new Wine ("Decanta", "Ceci", 2015, "Red", "Barbera", 30);
			Wine w13 = new Wine ("Calabrone", "Bastianich", 2013, "Red", "Refosco dal peduncolo rosso", 30);
			Wine w14 = new Wine ("Mater terra", "Apollonio", 2007, "Dessert's wine", "Negroamaro", 30);
			Wine w15 = new Wine ("Podium", "Garofoli", 2018, "White", "Verdicchio", 30);
			e1.addWine(w1);
			e1.addWine(w2);
			e2.addWine(w3);
			e2.addWine(w4);
			e1.addWine(w5);
			e1.addWine(w6);
			e2.addWine(w7);
			e2.addWine(w8);
			e1.addWine(w9);
			e1.addWine(w10);
			e2.addWine(w11);
			e2.addWine(w12);
			e1.addWine(w13);
			e1.addWine(w14);
			e2.addWine(w15);
			Customer c1 = new Customer ("Pietro", "Bianchi", "pb@gmail.com", "pb", mySystem);
			c1.subscribeCustomer();
			setDatabase();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Sets all the tables in database.
	 * 
	 **/
	public void setDatabase()
	{
		setTableAdmin(connection);
		setTableEmployee(connection);
		setTableCustomer(connection);
		setTableWine(connection);
		setTableOrder(connection);
		setTableNotification(connection);
		setTableNotificationRequest(connection);
	}
	
	/**
	 * 
	 * Gets all database's data and stores them into every MySystem's list.
	 * 
	 **/
	public void getDatabase()
	{
		try 
		{
			ResultSet customersData = statement.executeQuery("select * from customers");
			getTableCustomer(customersData);
			ResultSet adminsData = statement.executeQuery("select * from admins");
			getTableAdmin(adminsData);
			ResultSet employeesData = statement.executeQuery("select * from employees");
			getTableEmployee(employeesData);
			ResultSet winesData = statement.executeQuery("select * from wines");
			getTableWine(winesData);
			ResultSet ordersData = statement.executeQuery("select * from orders");
			getTableOrder(ordersData);
			ResultSet notificationsData = statement.executeQuery("select * from notifications");
			getTableNotification(notificationsData);
			ResultSet requestNotificationData = statement.executeQuery("select * from notificationrequest");
			getTableNotificationRequest(requestNotificationData);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}	
	}

	/**
	 * 
	 * Runs the server code. The server waits for a {@code Request} and sends a {@code Response} through {@code ServerTherad}
	 * Until it is true socket accepts new connections.
	 * 
	 **/
	private void run()
	{
		this.pool = new ThreadPoolExecutor(COREPOOL, MAXPOOL, IDLETIME,
        TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
		while (true)
		{
			try
			{
				Socket s = this.socket.accept();
				this.pool.execute(new ServerThread(this, s, mySystem));
			}
			catch (Exception e)
			{
				break;
			}
		}
		this.pool.shutdown();
	}

	/**
	 * 
	 * Gets the server pool.
	 *
	 * @return the thread pool.
	 *
	 **/
	public ThreadPoolExecutor getPool()
	{
		return this.pool;
	}

	/**
	 * 
	 * Closes the socket and refreshes the DB.
	 * 
	 **/
	public void close()
	{
		try
		{
			refreshDatabase();
			this.socket.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Method to refresh all data from database. Before the server is closed all data are removed and then set the DB through the
	 * updated MySystem's lists.
	 * 
	 **/
	public void refreshDatabase()
	{
		try
		{
			statement.executeUpdate("delete from admins");
			statement.executeUpdate("delete from customers");
			statement.executeUpdate("delete from employees");
			statement.executeUpdate("delete from wines");
			statement.executeUpdate("delete from orders");
			statement.executeUpdate("delete from notifications");
			statement.executeUpdate("delete from notificationrequest");
			setDatabase();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Method to take all data from DB customers' table and adds them in customerList.
	 * 
	 * @param r ResulSet
	 * 
	 **/
	private static void getTableCustomer(ResultSet r)
	{
		mySystem.getCustomerList().clear();
		try 
		{
			while(r.next())
			{
				String name = r.getString(1);
				String surname = r.getString(2);
				String email = r.getString(3);
				String password = r.getString(4);
				Customer c = new Customer (name, surname, email, password, mySystem);
				mySystem.getCustomerList().add(c);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Method to take all data from DB admins' table and adds them in adminList.
	 * 
	 * @param r ResulSet
	 * 
	 **/
	private static void getTableAdmin(ResultSet r)
	{
		mySystem.getAdminList().clear();
		try 
		{
			while(r.next())
			{
				String name = r.getString(1);
				String surname = r.getString(2);
				String email = r.getString(3);
				String password = r.getString(4);
				Admin a = new Admin (name, surname, email, password, mySystem);
				mySystem.getAdminList().add(a);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Method to take all data from DB employees table and adds them in employeeList.
	 * 
	 * @param r ResulSet
	 * 
	 **/
	private static void getTableEmployee(ResultSet r)
	{
		mySystem.getEmployeeList().clear();
		try 
		{
			while(r.next())
			{
				String name = r.getString(1);
				String surname = r.getString(2);
				String email = r.getString(3);
				String password = r.getString(4);
				Employee e = new Employee (name, surname, email, password, mySystem);
				mySystem.getEmployeeList().add(e);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Method to take all data from DB wines' table and adds them in wineList.
	 * 
	 * @param r ResulSet
	 * 
	 **/
	private static void getTableWine(ResultSet r)
	{
		mySystem.getWineList().clear();
		try 
		{
			while(r.next())
			{
				String name = r.getString(1);
				String winemaker = r.getString(2);
				int year = Integer.parseInt(r.getString(3)); 
				String notes = r.getString(4);
				String vine = r.getString(5);
				int amount = Integer.parseInt(r.getString(6)); 
				Wine w = new Wine (name, winemaker, year, notes, vine, amount);
				mySystem.getWineList().add(w);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Method to take all data from DB orders' table and adds them in orderList.
	 * 
	 * @param r ResulSet
	 * 
	 **/
	private static void getTableOrder(ResultSet r)
	{
		mySystem.getOrderList().clear();
		try 
		{
			while(r.next())
			{
				String email = r.getString(1);
				int quantity = Integer.parseInt(r.getString(2));
				String wineName = r.getString(3);
				String winemaker = r.getString(4);
				Wine w = new Wine (wineName, winemaker, 0, "", "", 0);
				Order o = new Order (email, w, quantity, mySystem);
				mySystem.getOrderList().add(o);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Method to take all data from DB notifications table and adds them in notificationList.
	 * 
	 * @param r ResulSet
	 * 
	 **/
	private static void getTableNotification(ResultSet r)
	{
		mySystem.getNotificationList().clear();
		try 
		{
			while(r.next())
			{
				String email = r.getString(1);
				String wineName = r.getString(2);
				String winemaker = r.getString(3);
				String notification = email + "," + wineName + "," + winemaker;
				mySystem.getNotificationList().add(notification);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Method to take all data from DB notifications' request table and adds them in notificationRequestList.
	 * 
	 * @param r ResulSet
	 * 
	 **/
	private static void getTableNotificationRequest(ResultSet r)
	{
		mySystem.getNotificationRequestList().clear();
		try 
		{
			while(r.next())
			{
				String email = r.getString(1);
				String wineName = r.getString(2);
				String winemaker = r.getString(3);
				int quantity = r.getInt(4);
				String request = email + "," + wineName + "," + winemaker + "," + quantity;
				mySystem.getNotificationRequestList().add(request);
				for (Wine w: mySystem.getWineList())
				{
					if (w.getName().equals(wineName) && w.getWinemaker().equals(winemaker))
					{
						w.getNotification().put(email, quantity);
					}
				}
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Sets the admins' table. Takes admins from adminList and adds them to DB admins' table.
	 *
	 * @param c Connection
	 * 
	 **/
	private static void setTableAdmin(Connection c)
	{
		String insertValues = "insert ignore into admins values (?, ?, ?, ?)";
		try 
		{
			PreparedStatement preparedStatement = c.prepareStatement(insertValues);
			for (Admin a: mySystem.getAdminList())
			{
				preparedStatement.setString(1, a.getName());
				preparedStatement.setString(2, a.getSurname());
				preparedStatement.setString(3, a.getEmail());
				preparedStatement.setString(4, a.getPassword());
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Sets the customers' table. Takes customers from customerList and adds them to DB customers' table.
	 *
	 * @param c Connection
	 * 
	 **/
	private static void setTableCustomer(Connection c)
	{
		String insertValues = "insert ignore into customers values (?, ?, ?, ?)";
		try 
		{
			PreparedStatement preparedStatement = c.prepareStatement(insertValues);
			for (Customer x : mySystem.getCustomerList())
			{
				preparedStatement.setString(1, x.getName());
				preparedStatement.setString(2, x.getSurname());
				preparedStatement.setString(3, x.getEmail());
				preparedStatement.setString(4, x.getPassword());
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Sets the employees' table. Takes employees from employeeList and adds them to DB employees' table.
	 *
	 * @param c Connection
	 * 
	 **/
	private static void setTableEmployee(Connection c)
	{
		String insertValues = "insert ignore into employees values (?, ?, ?, ?)";
		try 
		{
			PreparedStatement preparedStatement = c.prepareStatement(insertValues);
			for (Employee e: mySystem.getEmployeeList())
			{
				preparedStatement.setString(1, e.getName());
				preparedStatement.setString(2, e.getSurname());
				preparedStatement.setString(3, e.getEmail());
				preparedStatement.setString(4, e.getPassword());
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Sets the wines' table. Takes wines from wineList and adds them to DB wines' table.
	 *
	 * @param c Connection
	 * 
	 **/
	private static void setTableWine(Connection c)
	{
		String insertValues = "insert ignore into wines values (?, ?, ?, ?, ?, ?)";
		try 
		{
			PreparedStatement preparedStatement = c.prepareStatement(insertValues);
			for (Wine w: mySystem.getWineList())
			{
				preparedStatement.setString(1, w.getName());
				preparedStatement.setString(2, w.getWinemaker());
				preparedStatement.setInt(3, w.getYear());
				preparedStatement.setString(4, w.getNotes());
				preparedStatement.setString(5, w.getVine());
				preparedStatement.setInt(6, w.getnWine());
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Sets the orders' table. Takes orders from orderList and adds them to DB orders' table.
	 *
	 * @param c Connection
	 * 
	 **/
	private static void setTableOrder(Connection c)
	{
		String insertValues = "insert ignore into orders values(?, ?, ?, ?)";
		try 
		{
			PreparedStatement preparedStatement = c.prepareStatement(insertValues);
			for (Order o: mySystem.getOrderList())
			{
				preparedStatement.setString(1, o.getCustomerMail());
				preparedStatement.setInt(2, o.getnWineOrder());
				preparedStatement.setString(3, o.getWine().getName());
				preparedStatement.setString(4, o.getWine().getWinemaker());
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Sets the notifications' table. Takes notifications from notificationList and adds them to DB notifications' table.
	 *
	 * @param c Connection
	 * 
	 **/
	private static void setTableNotification(Connection c)
	{
		String insertValues = "insert ignore into notifications values(?, ?, ?)";
		try 
		{
			PreparedStatement preparedStatement = c.prepareStatement(insertValues);
			for (String s: mySystem.getNotificationList())
			{
				String splitted [] = s.split(",");
				preparedStatement.setString(1, splitted[0]);
				preparedStatement.setString(2, splitted[1]);
				preparedStatement.setString(3, splitted[2]);
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Sets the notification's requests table. Takes notification's requests from notificationRequestList and adds them to DB 
	 * notification request's table.
	 *
	 * @param c Connection
	 * 
	 **/
	private static void setTableNotificationRequest(Connection c)
	{
		String insertValues = "insert ignore into notificationrequest values(?, ?, ?, ?)";
		try 
		{
			PreparedStatement preparedStatement = c.prepareStatement(insertValues);
			for (String s: mySystem.getNotificationRequestList())
			{
				String splitted [] = s.split(",");
				preparedStatement.setString(1, splitted[0]);
				preparedStatement.setString(2, splitted[1]);
				preparedStatement.setString(3, splitted[2]);
				preparedStatement.setString(4, splitted[3]);
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Starts the demo.
	 *
	 * @param args the method does not requires arguments.
	 *
	 * @throws IOException if the execution fails.
	 *
	**/
	public static void main(final String[] args) throws IOException
	{
		new Server().run();
	}
}