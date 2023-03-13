package assegnamento;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * 
 * The {@code ActionController} class manages the customer's interface, where it's possible to search
 * a specific type of wine, and, if the customer is logged, buy it. If the wine is out 
 * of stock or not enough for the request, the customer can choose to be notified when it's restocked.
 * Every event is handled through a new {@code Request} sent to the server and by its {@code Response}.
 * 
 * @see Request
 * @see Response
 *
 **/
public class ActionController
{
    @FXML
    private Button actionLoginButton;  
    @FXML
    private Button mailButton;  
    @FXML
    private Button actionSearchButton;  
    @FXML
    private Button loginBackArrow;    
    @FXML
    public Button buyButton;   
    @FXML
    private Button buyLogoutButton;    
    @FXML
    private RadioButton actionNameRadioButton;
    @FXML
    private RadioButton actionYearRadioButton;
    @FXML
    private TextField actionSearchWine;   
    @FXML
    private TextField buyAmount;   
    @FXML
    private Text buyAmountWrong;   
    @FXML
    private Label notificationNumber;
    @FXML
    private TableView<Wine> actionWineTableView;
    @FXML
    private TableColumn<Wine, String> nameColumnTableView;
    @FXML
    private TableColumn<Wine, String> winemakerColumnTableView;
    @FXML
    private TableColumn<Wine, Integer> yearColumnTableView;
    @FXML
    private TableColumn<Wine, String> notesColumnTableView;
    @FXML
    private TableColumn<Wine, String> vineColumnTableView;
    @FXML
    private TableColumn<Wine, Integer> amountColumnTableView;   
    @FXML
    private ToggleGroup actionSearch;   
    private String emailLogged = null;   
    public int count = 0;   
    public int updateCount = 0;    
    private ObservableList<Wine> wines = FXCollections.observableArrayList();    
	private ObservableList<Wine> filteredWines = FXCollections.observableArrayList();
    
	/**
	 * 
	 * Handles the event of pressing the button login/sign up. Displays a new login stage.
	 * 
	 **/
    public void handleLoginSignUpAction()  
    {
    	Stage loginStage = (Stage) buyButton.getScene().getWindow();
    	Parent loginRoot = null;
		try 
		{
			loginRoot = FXMLLoader.load(getClass().getResource("loginScene.fxml"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
        loginStage.setScene(new Scene(loginRoot));
        loginStage.show();
    }
    
    /**
     * 
     * Handles the event of pressing the arrow button. Updates the table of wines after a research, by
     * setting it to the wines' observable list.
     * 
     **/
    public void handleArrowButton()
    {
    	filteredWines.clear();
    	actionSearchWine.setText("");
    	actionWineTableView.setItems(wines);
    }
    
    /**
     * 
     * Handles the event of pressing the mail button. Creates a new mail stage and displays the 
     * new notifications about restocked wines. The response from the server contains a 
     * list of all the notifications requested by every customer. In every open action stage it's 
     * checked if the list contains messages for the customer logged, and counts them. When the mail stage
     * is closed the notifications read are removed.
     * 
     **/
    public void handleMailButton() 
    {
    	List<String> parameters = new ArrayList<String>();
    	Request rq = new Request("DISPLAYNOTIFICATION", parameters);
    	Response rs = null;
    	try 
    	{
    		Main.c.sendRequest(rq);
		} 
    	catch (IOException e) 
    	{
			e.printStackTrace();
		}
    	rs = Main.c.receiveResponse();
		Stage mailStage = new Stage();
		FXMLLoader mailLoader =  new FXMLLoader(getClass().getResource("mailScene.fxml"));
    	Parent mailRoot = null;
		try 
		{
			mailRoot = (Parent) mailLoader.load();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		for (String s: rs.getParameters())
		{
			String splitted[] = s.split(",");
			if (this.emailLogged.equals(splitted[0]))
			{
				((MailController)mailLoader.getController()).getDisplayNotifications().add(splitted[1] + " produced by " + splitted[2] +" is now available");
			}
		}
		Collections.reverse(((MailController)mailLoader.getController()).getDisplayNotifications());
		count = 0;
		notificationNumber.setVisible(false);
    	mailStage.setScene(new Scene(mailRoot));
    	mailStage.showAndWait();
    	List<String> param = new ArrayList <String>();
    	param.add(this.emailLogged);
    	Request notificationRead = new Request("NOTIFICATIONREAD", param);
    	try 
    	{
			Main.c.sendRequest(notificationRead);
		}
    	catch (IOException e) 
    	{
			e.printStackTrace();
		}
    	Main.c.receiveResponse();
    }

    /**
     * 
     *  Handles the event of pressing the search button. Uploads the displayed wines to the ones
     *  that match the research, setting the table to the filtered wines' observable list.
     * 
     **/
    public void handleSearchAction() 
    {
    	this.refresh();
    	String searchFilter = null;
    	if (actionNameRadioButton.isSelected())
    	{
    		searchFilter = "NAME";
    	}
    	else if (actionYearRadioButton.isSelected())
    	{
    		searchFilter = "YEAR";
    	}
    	List<String> parameters = new ArrayList<String>();
    	String toSearch = actionSearchWine.getText();   	
    	if (!toSearch.isEmpty())
    	{
    		parameters.add(searchFilter);
        	parameters.add(toSearch);
        	Request rq = new Request("SEARCH", parameters);
        	try 
        	{
				Main.c.sendRequest(rq);
			} 
        	catch (IOException e) 
        	{
				e.printStackTrace();
			} 
        	Response rs = Main.c.receiveResponse();
    		filteredWines.clear();
        	if (rs.getParameters().get(0).equals("null"))
        	{
        		actionWineTableView.setItems(filteredWines);	
        	}
        	else
        	{
            	for (int x = 0; x < rs.getParameters().size(); x++)
            	{
            		String[] attributes = rs.getParameters().get(x).split(",");
            		Wine w = new Wine(attributes[0], attributes[1], Integer.parseInt(attributes[2]), attributes[3], attributes[4], Integer.parseInt(attributes[5]));
            		filteredWines.add(w);
            	}
            	actionWineTableView.setItems(filteredWines);
        	}
    	}
    	else
    	{
        	actionWineTableView.setItems(wines);
    	}
    }
  
    /**
     * 
     * Handles the event of pressing the logout button by sending the email of the customer
     * who's logging out to server through the function receiveData. Displays a new action stage. 
     * 
     **/
    public void handleLogoutAction() 
    {
    	buyAmount.setText("");
    	List<String> parameters = new ArrayList<String>();
    	parameters.add("Customer");
    	parameters.add(this.emailLogged);
    	Request rq = new Request ("LOGOUT", parameters);
    	try
    	{
			Main.c.sendRequest(rq);
		} 
    	catch (IOException e) 
    	{
			e.printStackTrace();
		}
    	Response rs = Main.c.receiveResponse();
    	if (rs.getParameters().get(0).equals("Logout"))
    	{
    		this.receiveData(true);
    	}
    	notificationNumber.setVisible(false);
    }
    
    /**
     * 
     * Handles the event of pressing the buy button. Creates a new pop up stage, where the customer
     * can confirm the purchase or not. Before the confirmation the wines' observable list is updated to
     * display a correct message about its purchase. 
     * If the wine is not enough or out of stock, the customer can choose to be notified when it's restocked.
     * Only once the pop up is closed the purchase and the eventual notification request are effective.
     * 
     **/
    public void handleBuyButton()
    {
    	List<String> parameters = new ArrayList<String>();
    	Wine w= actionWineTableView.getSelectionModel().getSelectedItem();
		String n= buyAmount.getText();
		buyAmountWrong.setText("");
		this.refresh();
    	if (w!=null && !n.isEmpty() && n.matches("^[1-9][0-9]{0,3}") && Integer.parseInt(n)!=0)
    	{	
    		Stage popUpStage = new Stage();
    		FXMLLoader popUpLoader =  new FXMLLoader(getClass().getResource("popUpScene.fxml"));
        	Parent popUpRoot = null;
			try 
			{
				popUpRoot = (Parent) popUpLoader.load();
			} 
			catch (IOException e1) 
			{
				e1.printStackTrace();
			}
        	parameters.add(this.emailLogged);
        	parameters.add(n);
        	parameters.add(w.getName());
        	parameters.add(w.getWinemaker());
        	Wine update = this.findWine(w.getName(), w.getWinemaker());
        	if (update.getAmount() >= Integer.parseInt(n))
        	{
        		((PopUpController)popUpLoader.getController()).popUpText.setText(n + " bottles of " + update.getName() + " selected. Do you want to proceed?");
        		((PopUpController)popUpLoader.getController()).popUpNotificationButton.setDisable(true);
        		((PopUpController)popUpLoader.getController()).getCloseWindow(true);
        	}
        	else if (update.getAmount() < Integer.parseInt(n) && update.getAmount() > 0)
        	{
        		((PopUpController)popUpLoader.getController()).popUpText.setText("Only "+ update.getAmount() + " bottles of " + n + " of "+ w.getName() + " selected. Do you want to proceed?");
        		((PopUpController)popUpLoader.getController()).popUpNotificationButton.setDisable(true);
        		List<String> notifyParameters = new ArrayList<String>();
        		notifyParameters.add(this.emailLogged);
        		notifyParameters.add(w.getName());
        		notifyParameters.add(w.getWinemaker());
        		int toNotifyAmount = Integer.parseInt(n) - update.getAmount();
        		notifyParameters.add(Integer.toString(toNotifyAmount));
        		((PopUpController)popUpLoader.getController()).notifyParameters(notifyParameters);
        	}
        	else
        	{
        		((PopUpController)popUpLoader.getController()).popUpText.setText(w.getName() + " out of stock");
        		((PopUpController)popUpLoader.getController()).popUpBuyButton.setDisable(true);
        		List<String> notifyParameters = new ArrayList<String>();
        		notifyParameters.add(this.emailLogged);
        		notifyParameters.add(w.getName());
        		notifyParameters.add(w.getWinemaker());
        		notifyParameters.add(n);
        		((PopUpController)popUpLoader.getController()).notifyParameters(notifyParameters);
        	}	
        	popUpStage.setScene(new Scene(popUpRoot));
        	popUpStage.showAndWait();
        	if (((PopUpController)popUpLoader.getController()).getCheck())
        	{
        		Request rq = new Request("BUY", parameters);
            	try 
            	{
    				Main.c.sendRequest(rq);
    			} 
            	catch (IOException e) 
            	{
    				e.printStackTrace();
    			}
            	Response rs = Main.c.receiveResponse();
            	this.refresh();
            	((PopUpController)popUpLoader.getController()).popUpText.setText((rs.getParameters().get(0)));
        	}
    	}
    	else if (w==null)
    	{
    		buyAmountWrong.setText("Choose Wine");
    	}
    	else
    	{
    		buyAmountWrong.setText("Insert valid number");
    	}
    }

    /**
     * 
     * Finds a specific wine by its key: name and winemaker.
     * 
     * @param name wine's name
     * @param winemaker wine's producer
     * 
     * @return the wine searched if exists
     * 
     **/
    public Wine findWine(String name, String winemaker)
    {
		  for (Wine w: wines)
		  {
			  if (w.getName().equals(name) && w.getWinemaker().equals(winemaker))
			  {
				  return w;
			  }
		  }
		  return null;
    }
    
    /**
     * 
     * Sets the action stage's buttons according to whether the customer is logged in or not.
     * 
     * @param b false if customer is logged 
     * 
     **/
    public void receiveData(boolean b)
    {
		buyButton.setDisable(b);
    	buyAmount.setDisable(b);
    	actionLoginButton.setDisable(!b);
    	actionLoginButton.setVisible(b);
    	buyLogoutButton.setDisable(b);
    	buyLogoutButton.setVisible(!b);
    	mailButton.setDisable(b);
    	mailButton.setVisible(!b);
    }
    
    /**
     * 
     * Stores the data of the logged customer, sent by the login stage.
     * 
     * @param email logged customer's email
     * 
     **/
    public void receiveLoggedUser(String email)
    {
    	this.emailLogged = email;
    }
    
    /**
     * 
     * Refreshes the table of wines updating wines's observable list, and the number of notifications
     * to read if the customer is logged.
     * 
     **/
    public void refresh()
    {
    	actionWineTableView.getItems().clear();
		List<String> list = new ArrayList<String>();
    	Request rqAddEmployee = new Request("WINES", list);
    	Response rs = null;
    	try 
    	{
			Main.c.sendRequest(rqAddEmployee);
			rs = Main.c.receiveResponse();
		} 
    	catch (IOException e) 
    	{
			e.printStackTrace();
		}
    	wines.clear();
    	for (int x = 0; x<rs.getParameters().size(); x++)
    	{
    		String[] attributes = rs.getParameters().get(x).split(",");
    		Wine y= new Wine(attributes[0], attributes[1], Integer.parseInt(attributes[2]), attributes[3], attributes[4], Integer.parseInt(attributes[5]));
    		wines.add(y);
       	}
    	actionWineTableView.setItems(wines);
    	buyAmountWrong.setText("");
    	if (this.emailLogged != null)
    	{
    		List<String> parameters = new ArrayList<String>();
        	Request rq = new Request("DISPLAYNOTIFICATION", parameters);
        	Response rsNotification= null;
        	try 
        	{
        		Main.c.sendRequest(rq);
    		} 
        	catch (IOException e) 
        	{
    			e.printStackTrace();
    		}
        	rsNotification = Main.c.receiveResponse();
        	updateCount = 0;
    		for (String s: rsNotification.getParameters())
    		{
    			String splitted[] = s.split(",");
    			if (this.emailLogged.equals(splitted[0]))
    			{
    				++updateCount;
    			}
    		}
    		if (updateCount > count)
    		{
    			notificationNumber.setText(Integer.toString(updateCount-count));
    			notificationNumber.setVisible(true);
    		}
    	}
    }
    
    /**
     * 
     * Initializes the action stage through the wines' list read from the server's response.
     * Wines' observable list contains all the wines. Filtered wines' observable list contains
     * a copy of all wines and will be modified according to the customer's research.
     * 
     **/
    public void initialize()
    {
    	
    	List<String> list = new ArrayList<String>();
    	Request rqAddWine = new Request("WINES", list);
    	Response rs = null;
    	try 
    	{
    		Main.c.sendRequest(rqAddWine);
			rs=Main.c.receiveResponse();
		} 
    	catch (IOException e) 
    	{
			e.printStackTrace();
		}
    	for (int x = 0; x<rs.getParameters().size(); x++)
    	{
    		String[] attributes = rs.getParameters().get(x).split(",");
    		Wine w = new Wine(attributes[0], attributes[1], Integer.parseInt(attributes[2]), attributes[3], attributes[4], Integer.parseInt(attributes[5]));
    		wines.add(w);
    		filteredWines.add(w);
       	}
    	nameColumnTableView.setCellValueFactory(new PropertyValueFactory<Wine, String>("Name"));
    	winemakerColumnTableView.setCellValueFactory(new PropertyValueFactory<Wine, String>("Winemaker"));
    	yearColumnTableView.setCellValueFactory(new PropertyValueFactory<Wine, Integer>("Year"));
    	notesColumnTableView.setCellValueFactory(new PropertyValueFactory<Wine, String>("Notes"));
    	vineColumnTableView.setCellValueFactory(new PropertyValueFactory<Wine, String>("Vine"));
    	amountColumnTableView.setCellValueFactory(new PropertyValueFactory<Wine, Integer>("Amount"));
    	actionWineTableView.setItems(filteredWines);
    	actionNameRadioButton.setSelected(true);
    }
}