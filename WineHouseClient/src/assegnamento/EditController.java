package assegnamento;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
*
* The {@code EditController} class deals with the management of employee's details and changes.
* Employees can be added as new or modified.
* Every event is handled through a new {@code Request} sent to the server and by its {@code Response}.
*
* @see Request
* @see Response
*
**/
public class EditController 
{
    @FXML
    public Button addConfirmButton;    
    @FXML
    public Button editConfirmButton;    
    @FXML
    private Text editTextWrong;    
    @FXML
    public TextField editEmployeeName;
    @FXML
    public TextField editEmployeeSurname;
    @FXML
    public TextField editEmployeeEmail;
    @FXML
    public TextField editEmployeePassword;    
    private String oldEmail;

    /**
    *
    * Handles the event of pressing the addEmployee button which enables the addition of a new employee.
    *
    **/
    public void handleConfirmAddButton()
    {    	
    	String name = editEmployeeName.getText();
    	String surname = editEmployeeSurname.getText();
    	String email = editEmployeeEmail.getText();
    	String password = editEmployeePassword.getText();
    	List<String> parameters = new ArrayList<String>();
    	parameters.add(name);
    	parameters.add(surname);
    	parameters.add(email);
    	parameters.add(password);
    	if (this.checkTextField(parameters))
    	{
    		Request rq = new Request("ADDEMPLOYEE", parameters);
        	Response rs = null;
        	try 
        	{
    			Main.c.sendRequest(rq);
    			rs = Main.c.receiveResponse();
    		} 
        	catch (IOException e) 
        	{
    			e.printStackTrace();
    		}
        	String feedback = rs.getParameters().get(0);
        	if (feedback.equals("Done"))
        	{
        		((Stage)addConfirmButton.getScene().getWindow()).close();
        	}
        	else if (feedback.equals("Invalid email") || feedback.equals("Invalid password") || feedback.equals("Invalid name") || feedback.equals("Invalid surname")) 
        	{
        		editTextWrong.setText(rs.getParameters().get(0));
        	}
        	else 
        	{
        		editTextWrong.setText("Already subscribed");
        	}
    	}
    	else
    	{
    		editTextWrong.setText("Enter all parameters");
    	}
    }

    /**
    *
    * Handles the event of pressing the editEmployee button which deals with the updating of an existing employee's credentials.
    *
    **/
    public void handleConfirmEditButton()
    {
    	String name = editEmployeeName.getText();
    	String surname = editEmployeeSurname.getText();
    	String email = editEmployeeEmail.getText();
    	String password = editEmployeePassword.getText();
    	List<String> parameters = new ArrayList<String>();
    	parameters.add(this.oldEmail);
    	parameters.add(name);
    	parameters.add(surname);
    	parameters.add(email);
    	parameters.add(password);
    	if (this.checkTextField(parameters))
    	{
    		Request rq = new Request("SETEMPLOYEE", parameters);
        	Response rs = null;
        	try
        	{
    			Main.c.sendRequest(rq);
    			rs = Main.c.receiveResponse();
    		} 
        	catch (IOException e) 
        	{
    			e.printStackTrace();
    		}
        	if (rs.getParameters().get(0).equals("Done"))
        	{
        		((Stage)editConfirmButton.getScene().getWindow()).close();
        	}
        	else
        	{
        		editTextWrong.setText(rs.getParameters().get(0));
        	}
    	}
    	else
    	{
    		editTextWrong.setText("Enter all parameters");
    	}
    }
  
    /**
    *
    * Receives an employee's email and saves it as old email. It will be used to search the employee in
    * the system before the admin modify its credentials.
    *
    * @param e employee's email
    *
    **/
    public void receiveEmployeeEmail (String e)
    {
    	this.oldEmail = e;
    }
    
    /**
    *
    * Checks if all the text fields are not empty.
    *
    * @param list of parameters taken by the text fields
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