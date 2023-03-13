package assegnamento;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
*
* The {@code EmployeeController} class manages all the operations that a logged employee can do.
* He is able to add new wines and restock the amount of the existing ones.
* He can also refresh the page and logout.
* Every event is handled through a new {@code Request} sent to the server and by its {@code Response}.
*
* @see Request
* @see Response
*
**/
public class EmployeeController 
{
    @FXML
    private Button employeeLogoutButton;   
    @FXML
    private Button restockWineButton;
    @FXML
    private Button addWineButton;   
    @FXML
    private Button refreshButton;    
    @FXML
    private Text textWrong;
    @FXML
    private TableView<Wine> wineEmployeeTableView;
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
    private String emailEmployee;
    private ObservableList<Wine> wines = FXCollections.observableArrayList();
 
    /**
    *
    * Handles the event of pressing the addWine button which opens a stage that allows the insertion
    * of all the details about wines to add.
    *
    **/
    public void handleAddWineButton()
    {
    	Stage addWineStage = new Stage();
		FXMLLoader addWineLoader =  new FXMLLoader(getClass().getResource("editWineScene.fxml"));
    	Parent addWineRoot = null;
		try 
		{
			addWineRoot = (Parent) addWineLoader.load();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
    	EditWineController addWineController = addWineLoader.getController();
    	addWineController.confirmAddWineButton.setVisible(true);
    	addWineController.confirmAddWineButton.setDisable(false);
    	addWineController.confirmAddWineButton.setDefaultButton(true);
    	textWrong.setText("");
    	addWineStage.setScene(new Scene(addWineRoot));
    	addWineStage.showAndWait();
		this.refresh();
    }

    /**
    *
    * Handles the event of pressing the editWine button which opens a stage that allows the insertion
    * of the quantity of wine's bottles that the employee wants to restock.
    *
    **/
    public void handleEditWineButton()
    {
    	Stage editWineStage = new Stage();
		FXMLLoader ediWineLoader =  new FXMLLoader(getClass().getResource("editWineScene.fxml"));
    	Parent editWineRoot = null;
		try 
		{
			editWineRoot = (Parent) ediWineLoader.load();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
    	EditWineController editWineController = ediWineLoader.getController();
    	editWineController.confirmEditWineButton.setVisible(true);
    	editWineController.confirmEditWineButton.setDisable(false);
    	editWineController.confirmEditWineButton.setDefaultButton(true);
    	Wine w = wineEmployeeTableView.getSelectionModel().getSelectedItem();
    	if (w!=null)
    	{
    		editWineController.editWineName.setText(w.getName());
    		editWineController.editWineWinemaker.setText(w.getWinemaker());
    		editWineController.editWineYear.setText(Integer.toString(w.getYear()));
    		editWineController.editWineNotes.setText(w.getNotes());
    		editWineController.editWineVine.setText(w.getVine());
    		editWineController.editWineName.setEditable(false);
    		editWineController.editWineWinemaker.setEditable(false);
    		editWineController.editWineYear.setEditable(false);
    		editWineController.editWineNotes.setEditable(false);
    		editWineController.editWineVine.setEditable(false);
        	textWrong.setText("");
        	editWineStage.setScene(new Scene(editWineRoot));
        	editWineStage.showAndWait();
    		this.refresh();
    	}
    	else
    	{
    		textWrong.setText("Select a wine");
    	}
    }
  
    /**
    *
    * Handles the event of pressing the logout button.
    * The employee is redirected to the general login stage.
    *
    **/
    public void handleLogoutButton() 
    {
    	List<String> parameters = new ArrayList<String>();
    	parameters.add("Employee");
    	parameters.add(this.emailEmployee);
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
    		Stage loginStage = (Stage) employeeLogoutButton.getScene().getWindow();
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
    * Searches a wine identified by the key name-winemaker.
    *
    * @param name wine's name
    * @param winemaker wine's producer
    *
    * @return the found wine or null if it's not in the list
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
    * Receives the email of the logged employee.
    *
    * @param email employee's email
    *
    **/
    public void receiveLoggedEmployee(String email)
    {
    	this.emailEmployee = email;
    }
 
    /**
    *
    * Refreshes the page by updating the information about wines.
    *
    **/
    public void refresh()
    {
    	textWrong.setText("");
    	wineEmployeeTableView.getItems().clear();
		List<String> parameters = new ArrayList<String>();
    	Request rqAddEmployee = new Request("WINES", parameters);
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
    		String[] attributes = rs.getParameters().get(x).split(",");
    		Wine w = new Wine(attributes[0], attributes[1], Integer.parseInt(attributes[2]), attributes[3], attributes[4], Integer.parseInt(attributes[5]));
    		wines.add(w);
       	}
    	wineEmployeeTableView.setItems(wines);
    }
    
    /**
    *
    * Initializes the scene by inserting and showing the available wines' table.
    *
    **/
    public void initialize()
    {
    	List<String> parameters = new ArrayList<String>();
    	Request rqAddWine = new Request("WINES", parameters);
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
       	}
    	nameWineColumn.setCellValueFactory(new PropertyValueFactory<Wine, String>("Name"));
    	winemakerWineColumn.setCellValueFactory(new PropertyValueFactory<Wine, String>("Winemaker"));
    	yearWineColumn.setCellValueFactory(new PropertyValueFactory<Wine, Integer>("Year"));
    	notesWineColumn.setCellValueFactory(new PropertyValueFactory<Wine, String>("Notes"));
    	vineWineColumn.setCellValueFactory(new PropertyValueFactory<Wine, String>("Vine"));
    	amountWineColumn.setCellValueFactory(new PropertyValueFactory<Wine, Integer>("Amount"));
    	wineEmployeeTableView.setItems(wines);
    }
}