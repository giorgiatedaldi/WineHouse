package assegnamento;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
*
* The {@code SignUpController} class manages all the operations that concern the registration of a new customer.
* The customer can insert name, surname and credentials or go back to the start page.
* Every event is handled through a new {@code Request} sent to the server and by its {@code Response}.
*
* @see Request
* @see Response
*
**/
public class SignUpController
{ 
    @FXML
    private Button signUpButton;    
    @FXML
    private Button signUpSubscribeButton;    
    @FXML
    private Button goBackButton;    
    @FXML
    private TextField signUpNameTextField;
    @FXML
    private TextField signUpSurnameTextField;
    @FXML
    private TextField signUpEmailTextField;
    @FXML
    private TextField signUpPasswordTextField; 
    @FXML
    private Text signUpEmptyText;
  
    /**
    *
    * Handles the event of pressing the subscribe button.
    * If all the parameters are valid and unused by other customers he is added to the subscibers' list.
    *
    **/
    public void handleSubscribeButton()
    {
    	List<String> parameters = new ArrayList<String>();
    	String name = signUpNameTextField.getText();
    	String surname = signUpSurnameTextField.getText(); 
    	String email = signUpEmailTextField.getText();
    	String password = signUpPasswordTextField.getText();
    	parameters.add(name);
    	parameters.add(surname);
    	parameters.add(email);
    	parameters.add(password);
    	if (this.checkTextField(parameters))
    	{
    		Request rq = new Request("SIGNUP", parameters);
        	try 
        	{
				Main.c.sendRequest(rq);
			} 
        	catch (IOException e) 
        	{
				e.printStackTrace();
			}  
        	Response rs = Main.c.receiveResponse();
        	signUpEmptyText.setText("");
        	if (rs.getParameters().get(0).equals("Subscribed"))
        	{
        		signUpSubscribeButton.setDefaultButton(false);
        		signUpSubscribeButton.setDisable(true);
        		signUpButton.setDisable(false);
        		signUpButton.setDefaultButton(true);
        	}
        	else
        	{
        		signUpEmptyText.setText(rs.getParameters().get(0));
        	}
    	}
    	else
    	{
    		signUpEmptyText.setText("Please enter all values");
    	}
    }
     
    /**
    *
    * Handles the event of pressing the signUp button.
    * The customer is now logged and can do all the operations that the system provides.
    *
    **/
    public void handleSignUpButton()
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
    	actController.receiveLoggedUser(signUpEmailTextField.getText());
    	Stage actionStage = (Stage) signUpButton.getScene().getWindow();
        actionStage.setScene(new Scene(actionRoot));
        actionStage.show();
    }
    
    /**
    *
    * Handles the event of pressing the goBack button which allows the customer to return at the start page.
    *
    **/
    public void handleGoBackButton()
    {
    	Stage loginStage = (Stage) goBackButton.getScene().getWindow();
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
    * Checks if all the text fields are not empty.
    *
    * @param list of parameters taken from the text fields
    *
    * @return false if at least one of the text field is empty
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
}