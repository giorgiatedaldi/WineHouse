package assegnamento;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
*
* The {@code AdminController} class manages all the operations that an admin can do.
* The admin can display all information about customers, employees, wines and orders.
* He can also add new employees, modify their credentials and remove them from the system.
* Every event is handled through a new {@code Request} sent to the server and by its {@code Response}.
*
* @see Request
* @see Response
*
**/
public class AdminController 
{
    @FXML
    private Button adminLogoutButton;    
    @FXML
    private Button editEmployeeButton;   
    @FXML
    private Button addEmployeeButton;
    @FXML
    private Button removeEmployeeButton;    
    @FXML
    private Button closeServerButton;   
    @FXML
    private Button refreshButton;    
    @FXML
    public Text textWrong;    
    @FXML
    private ChoiceBox<String> adminChoiceBox;
    @FXML
    private TableView<Customer> customerTableView;
    @FXML
    private TableColumn<Customer, String> nameCustomerColumn;
    @FXML
    private TableColumn<Customer, String> surnameCustomerColumn;
    @FXML
    private TableColumn<Customer, String> emailCustomerColumn;
    @FXML
    private TableColumn<Customer, String> passwordCustomerColumn;
    @FXML
    private TableColumn<Customer, String> loggedCustomerColumn;
    @FXML
    private TableView<Employee> employeeTableView;
    @FXML
    private TableColumn<Employee, String> nameEmployeeColumn;
    @FXML
    private TableColumn<Employee, String> surnameEmployeeColumn;
    @FXML
    private TableColumn<Employee, String> emailEmployeeColumn;
    @FXML
    private TableColumn<Employee, String> passwordEmployeeColumn;
    @FXML
    private TableColumn<Employee, String> loggedEmployeeColumn;
    @FXML
    private TableView<Wine> wineTableView;
    @FXML
    private TableColumn<Wine, String> nameWineColumn;
    @FXML
    private TableColumn<Wine, String> winemakerWineColumn;
    @FXML
    private TableColumn<Wine, Integer> yearWineColumn;
    @FXML
    private TableColumn<Wine, String> notesWineColumn;
    @FXML
    private TableColumn<Wine, String> vineWineColumn;
    @FXML
    private TableColumn<Wine, Integer> amountWineColumn;
    @FXML
    private TableView<Order> orderTableView;
    @FXML
    private TableColumn<Order, String> wineNameOrderColumn;
    @FXML
    private TableColumn<Order, String> winemakerOrderColumn;
    @FXML
    private TableColumn<Order, String> costumerOrderColumn;
    @FXML
    private TableColumn<Order, Integer> quantityOrderColumn;      
    private String emailAdmin = null;    
    private ObservableList<String> menuItem = FXCollections.observableArrayList("Customers", "Employees", "Wines", "Orders");	
    private ObservableList<Wine> wineList = FXCollections.observableArrayList();
    private ObservableList<Order> orderList = FXCollections.observableArrayList();
    private ObservableList<Customer> customerList = FXCollections.observableArrayList();	
    private ObservableList<Employee> employeeList = FXCollections.observableArrayList();
 
    /**
    *
    * Handles the closing of the page and the server too.
    * A pop up appears to make sure that the admin really wants to do this action.
    *
    **/
	public void handleCloseServerButton()
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
    	PopUpController popUpController = popUpLoader.getController();
    	popUpController.popUpText.setText("Are you sure?");
    	popUpController.confirmCloseButton.setVisible(true);
    	popUpController.confirmCloseButton.setDisable(false);
    	popUpController.confirmRemoveButton.setVisible(false);
    	popUpController.confirmRemoveButton.setDisable(true);
    	popUpController.popUpBuyButton.setVisible(false);
    	popUpController.popUpBuyButton.setDisable(true);
    	popUpController.popUpNotificationButton.setVisible(false);
    	popUpController.popUpNotificationButton.setDisable(true);
    	popUpStage.setScene(new Scene(popUpRoot));
    	popUpStage.show();	
	}

	/**
	 *
	 * Handles the event of pressing the addEmployee button which opens a stage that
	 * allows the insertion of all the details about the employee to add.
	 *
	 **/
    public void handleAddButton()
    {
    	Stage addStage = new Stage();
		FXMLLoader addloader =  new FXMLLoader(getClass().getResource("editScene.fxml"));
    	Parent addRoot = null;
		try 
		{
			addRoot = (Parent) addloader.load();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
    	EditController editController = addloader.getController();
    	textWrong.setText("");
    	editController.addConfirmButton.setVisible(true);
    	editController.addConfirmButton.setDisable(false);
		editController.addConfirmButton.setDefaultButton(true);
    	addStage.setScene(new Scene(addRoot));
    	addStage.showAndWait();
		this.setTableViewed();
    }
  
    /**
    *
    * Handles the event of pressing the removeEmployee button which enables the deletion of an employee from the system.
    * A pop up appears to make sure that the admin really wants to do this action.
    *
    **/
    public void handleRemoveButton()
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
    	PopUpController popUpController = popUpLoader.getController();
    	popUpController.popUpText.setText("Are you sure?");
    	popUpController.confirmRemoveButton.setVisible(true);
    	popUpController.confirmRemoveButton.setDisable(false);
    	popUpController.popUpBuyButton.setVisible(false);
    	popUpController.popUpBuyButton.setDisable(true);
    	popUpController.popUpNotificationButton.setVisible(false);
    	popUpController.popUpNotificationButton.setDisable(true);
    	popUpController.confirmCloseButton.setVisible(false);
    	popUpController.confirmCloseButton.setDisable(true);
    	textWrong.setText("");
    	Employee e = employeeTableView.getSelectionModel().getSelectedItem();
    	if (e!= null)
    	{
    		popUpController.receiveEmployeeToRemove(e.getEmail());
    		popUpStage.setScene(new Scene(popUpRoot));
        	popUpStage.showAndWait();
        	if (popUpController.getCheck())
        	{
        		textWrong.setText("Remove is possible only if employee is not logged");
        		
        	}
        	this.setTableViewed();
    	}
    	else
    	{
    		textWrong.setText("Select an Employee");
    	}	
    }

    /**
    *
    * Handles the event of pressing the editEmployee button which opens a stage that
    * allows the insertion of the new credentials of the employee to modify, if it's not logged.
    *
    **/
    public void handleEditButton()
    {
    	Stage editStage = new Stage();
		FXMLLoader editLoader =  new FXMLLoader(getClass().getResource("editScene.fxml"));
    	Parent editRoot = null;
		try 
		{
			editRoot = (Parent) editLoader.load();
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
    	EditController editController = editLoader.getController();
    	textWrong.setText("");
    	editController.editConfirmButton.setVisible(true);
    	editController.editConfirmButton.setDisable(false);
    	editController.editConfirmButton.setDefaultButton(true);
    	editController.editEmployeeName.setEditable(false);
    	editController.editEmployeeSurname.setEditable(false);
    	Employee e = employeeTableView.getSelectionModel().getSelectedItem();
    	if (e!=null)
    	{
    		editController.receiveEmployeeEmail(e.getEmail());
    		editController.editEmployeeName.setText(e.getName());
        	editController.editEmployeeSurname.setText(e.getSurname());
        	editController.editEmployeeEmail.setText(e.getEmail());
        	editController.editEmployeePassword.setText(e.getPassword());
        	editStage.setScene(new Scene(editRoot));
        	editStage.showAndWait();
        	this.setTableViewed();;
    	}
    	else
    	{
    		textWrong.setText("Select an Employee");
    	}
    }

    /**
    *
    * Handles the event of pressing the logout button.
    * The admin is redirected to the general login stage.
    *
    **/
    public void handleLogoutButton()
    {
    	List<String> parameters = new ArrayList<String>();
    	parameters.add("Admin");
    	parameters.add(this.emailAdmin);
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
    		Stage loginStage = (Stage) adminLogoutButton.getScene().getWindow();
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
    }
  
    /**
    *
    * Sets the visible table based on the choice done by the admin in the choicebox.
    * The four possible choices are:
    * 0. customers' table
    * 1. employees' table
    * 2. wines' table
    * 3. orders' table
    *
    **/
    public void setTableViewed()
    {
    	wineTableView.getItems().clear();
    	orderTableView.getItems().clear();
    	customerTableView.getItems().clear();
    	employeeTableView.getItems().clear();
    	employeeTableView.setVisible(false);
		customerTableView.setVisible(false);
		orderTableView.setVisible(false);
		wineTableView.setVisible(false);
		addEmployeeButton.setDisable(true);
		removeEmployeeButton.setDisable(true);
		editEmployeeButton.setDisable(true);
    	int choice = adminChoiceBox.getSelectionModel().getSelectedIndex();    	
    	if(choice == 0)
    	{
        	textWrong.setText("");
    		customerTableView.setVisible(true);
    		List<String> parameters = new ArrayList<String>();
        	Request rqAddCustomer = new Request("CUSTOMERS", parameters);
        	Response rs = null;
        	try 
        	{
    			Main.c.sendRequest(rqAddCustomer);
    			rs =Main.c.receiveResponse();
    		} 
        	catch (IOException e) 
        	{
    			e.printStackTrace();
    		}
        	for (int x = 0; x<rs.getParameters().size(); x++)
        	{
        		String attributes[] = rs.getParameters().get(x).split(",");
        		Customer c = new Customer(attributes[0], attributes[1],attributes[2], attributes[3], attributes[4]);
        		customerList.add(c);
           	}
        	nameCustomerColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("Name"));
        	surnameCustomerColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("Surname"));
        	emailCustomerColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("Email"));
        	passwordCustomerColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("Password"));
        	loggedCustomerColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("LoginFlag"));
        	customerTableView.setItems(customerList);
    	}
    	else if (choice == 1)
    	{
    		employeeTableView.setVisible(true);
    		addEmployeeButton.setDisable(false);
    		removeEmployeeButton.setDisable(false);
    		editEmployeeButton.setDisable(false);
    		List<String> parameters = new ArrayList<String>();
        	Request rqAddEmployee = new Request("EMPLOYEES", parameters);
        	Response rs = null;
        	try 
        	{
    			Main.c.sendRequest(rqAddEmployee);
    			rs =Main.c.receiveResponse();
    		} 
        	catch (IOException e) 
        	{
    			e.printStackTrace();
    		}
        	for (int x = 0; x<rs.getParameters().size(); x++)
        	{
        		String attributes[] = rs.getParameters().get(x).split(",");
        		Employee e = new Employee(attributes[0], attributes[1],attributes[2], attributes[3], attributes[4]);
        		employeeList.add(e);
           	}
        	nameEmployeeColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("Name"));
        	surnameEmployeeColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("Surname"));
        	emailEmployeeColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("Email"));
        	passwordEmployeeColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("Password"));
        	loggedEmployeeColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("EmployeeLoginFlag"));
        	employeeTableView.setItems(employeeList);
    	}
    	else if(choice == 2) 
    	{
        	textWrong.setText("");
    		wineTableView.setVisible(true);
    		List<String> parameters = new ArrayList<String>();
        	Request rqAddWine = new Request("WINES", parameters);
        	Response rs = null;
        	try 
        	{
    			Main.c.sendRequest(rqAddWine);
    			rs = Main.c.receiveResponse();
    		} 
        	catch (IOException e) 
        	{
    			e.printStackTrace();
    		}
        	for (int x = 0; x< rs.getParameters().size(); x++)
        	{
        		String[] attributes = rs.getParameters().get(x).split(",");
        		Wine w = new Wine(attributes[0], attributes[1], Integer.parseInt(attributes[2]), attributes[3], attributes[4], Integer.parseInt(attributes[5]));
        		wineList.add(w);
           	}
        	nameWineColumn.setCellValueFactory(new PropertyValueFactory<Wine, String>("Name"));
        	winemakerWineColumn.setCellValueFactory(new PropertyValueFactory<Wine, String>("Winemaker"));
        	yearWineColumn.setCellValueFactory(new PropertyValueFactory<Wine, Integer>("Year"));
        	notesWineColumn.setCellValueFactory(new PropertyValueFactory<Wine, String>("Notes"));
        	vineWineColumn.setCellValueFactory(new PropertyValueFactory<Wine, String>("Vine"));
        	amountWineColumn.setCellValueFactory(new PropertyValueFactory<Wine, Integer>("Amount"));
        	wineTableView.setItems(wineList);
    	}
    	else if (choice == 3)
    	{
        	textWrong.setText("");
    		orderTableView.setVisible(true);
    		List<String> parameters = new ArrayList<String>();
        	Request rqAddOrder = new Request("ORDERS", parameters);
        	Response rs = null;
        	try 
        	{
    			Main.c.sendRequest(rqAddOrder);
    			rs =Main.c.receiveResponse();
    		} 
        	catch (IOException e)
        	{
    			e.printStackTrace();
    		}
        	for (int x = 0; x<rs.getParameters().size(); x++)
        	{
        		String attributes[] = rs.getParameters().get(x).split(",");
        		Order o = new Order(attributes[0], attributes[1],attributes[2], Integer.parseInt(attributes[3]));
        		orderList.add(o);
           	}
        	wineNameOrderColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("WineName"));
        	winemakerOrderColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("Winemaker"));
        	costumerOrderColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("CustomerEmail"));
        	quantityOrderColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("Quantity"));
        	orderTableView.setItems(orderList);
    	}	
    }
   
    /**
    *
    * Receives the email of the logged admin.
    *
    * @param email admin's email
    *
    **/
    public void receiveLoggedAdmin(String email)
    {
    	this.emailAdmin = email;
    }
   
    /**
    *
    * Initializes the scene by inserting and showing the table.
    * The default choice is case (2), so wine's table is shown at the beginning.
    *
    **/
    public void initialize()
    {
    	adminChoiceBox.setItems(menuItem);
    	adminChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
		{
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2)
			{
				adminChoiceBox.getSelectionModel().select((int) arg2);
				setTableViewed();
			}
		});
		adminChoiceBox.getSelectionModel().select(2);
    }
}