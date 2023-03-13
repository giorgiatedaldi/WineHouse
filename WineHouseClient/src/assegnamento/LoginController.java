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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * 
 * The {@code LoginController} class manages the login of admins, employees and customers.
 * It manages also the subscription of new customers.
 * Every event is handled through a new {@code Request} sent to the server and by its {@code Response}.
 *
 * @see Request
 * @see Response
 *
 **/
public class LoginController
{
    @FXML
    private Button loginSignUpButton;    
    @FXML
    private Button loginButton;    
    @FXML
    private Text loginWrongText;   
    @FXML
    private PasswordField loginPasswordField;
    @FXML
    private TextField loginEmailTextField;
    @FXML
    private ChoiceBox<String> loginChoiceBox;   
    private ObservableList<String> menuItem = FXCollections.observableArrayList("Admin", "Employee", "Customer");

    /**
     * 
     * Handles the event of pressing the signUp button. The login stage is substituted by the 
     * sign up stage.
     * 
     **/
    public void handleSignUpButton()
    {
    	Stage signUpStage = (Stage) loginSignUpButton.getScene().getWindow();
    	Parent signUpRoot = null;
		try
		{
			signUpRoot = FXMLLoader.load(getClass().getResource("signUpScene.fxml"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
        signUpStage.setScene(new Scene(signUpRoot));
        signUpStage.show();
    }
    
    /**
     * 
     * Handles the event of pressing the login button. If the user is an admin/employee/customer, a new
     * admin/employee/action stage is displayed. The user's data are sent in its scene's controller. 
     * 
     **/
    public void handleLoginButton()
    {
    	List<String> parameters = new ArrayList<String>();
    	String email = loginEmailTextField.getText();
    	String password = loginPasswordField.getText();
    	String user = menuItem.get(loginChoiceBox.getSelectionModel().getSelectedIndex());
    	parameters.add(user);
    	parameters.add(email);
    	parameters.add(password);
    	if (this.checkTextField(parameters))
    	{
    		Request rq = new Request("LOGIN", parameters);
        	try 
        	{
				Main.c.sendRequest(rq);
			} 
        	catch (IOException e) 
        	{
				e.printStackTrace();
			}
        	Response rs = Main.c.receiveResponse();
        	if(rs.getParameters().get(0).equals("Customer"))
        	{
        		FXMLLoader actionLoader =  new FXMLLoader(getClass().getResource("actionScene.fxml"));
            	Parent actionRoot = null;
				try 
				{
					actionRoot = (Parent) actionLoader.load();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
            	ActionController actController = actionLoader.getController();
            	actController.receiveData(false);
            	actController.receiveLoggedUser(email);
            	Stage actionStage = (Stage) loginButton.getScene().getWindow();
                actionStage.setScene(new Scene(actionRoot));
                actionStage.show();
        	}
        	else if (rs.getParameters().get(0).equals("Employee"))
        	{
        		FXMLLoader employeeLoader =  new FXMLLoader(getClass().getResource("employeeScene.fxml"));
            	Parent employeeRoot = null;
				try 
				{
					employeeRoot = (Parent) employeeLoader.load();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
            	EmployeeController employeeController = employeeLoader.getController();
            	employeeController.receiveLoggedEmployee(email);
            	Stage employeeStage = (Stage) loginButton.getScene().getWindow();
            	employeeStage.setScene(new Scene(employeeRoot));
            	employeeStage.show();
        	}
        	else if (rs.getParameters().get(0).equals("Admin"))
        	{
        		FXMLLoader adminLoader =  new FXMLLoader(getClass().getResource("adminScene.fxml"));
            	Parent adminRoot = null;
				try 
				{
					adminRoot = (Parent) adminLoader.load();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
            	AdminController adController = adminLoader.getController();
            	adController.receiveLoggedAdmin(email);
            	Stage adStage = (Stage) loginButton.getScene().getWindow();
            	adStage.setScene(new Scene(adminRoot));
            	adStage.show();
        	}
        	else 
        	{
        		loginWrongText.setText(rs.getParameters().get(0));
        	}
    	}
    	else
    	{
    		loginWrongText.setText("Enter email and password");
    	}
    }
    
    /**
     * 
     * Checks if all the text fields are not empty.
     * 
     * @param list parameter's taken from text fields
     * 
     * @return false is at least one of the text field is empty
     * 
     **/
    public boolean checkTextField(List<String> list)
    {
    	for (String s: list)
    	{
    		if (s.isEmpty())
    		{
    			return false;
    		}
    	}
    	return true;
    }
    
    /**
     * 
     * Initializes the loginScene by setting the menu's items.
     * 
     **/
    public void initialize()
    {
    	loginChoiceBox.setItems(menuItem);
    	loginChoiceBox.setValue("Customer");
    }
}