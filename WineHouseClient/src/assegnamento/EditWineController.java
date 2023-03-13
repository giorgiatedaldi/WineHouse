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
 * The {@code EditWineController} manages the the addition of new types of wines and the
 * restock of them.
 * Every event is handled through a new {@code Request} sent to the server and by its {@code Response}.
 *  
 * @see Request
 * @see Response
 *
 **/
public class EditWineController 
{
    @FXML
    public Button confirmAddWineButton;
    @FXML
    public Button confirmEditWineButton;    
    @FXML
    public TextField editWineName;
    @FXML
    public TextField editWineWinemaker;
    @FXML
    public TextField editWineYear;
    @FXML
    public TextField editWineNotes; 
    @FXML
    public TextField editWineVine;
    @FXML
    private TextField editWineAmount;
    @FXML
    private Text editTextWrong;   
 
    /**
     * 
     * Handles the event of pressing the addWine button.
     * 
     **/
    public void handleConfirmAddWineButton()
    {
    	String name = editWineName.getText();
    	String winemaker = editWineWinemaker.getText();
    	String year = editWineYear.getText();
    	String notes = editWineNotes.getText();
    	String vine = editWineVine.getText();
    	String amount = editWineAmount.getText();
    	List<String> parameters = new ArrayList<String>();
    	parameters.add(name);
    	parameters.add(winemaker);
    	parameters.add(year);
    	parameters.add(notes);
    	parameters.add(vine);
    	parameters.add(amount);
    	if (this.checkTextField(parameters) && year.matches("^[1-2][0-9]{3}") && amount.matches("^[1-9][0-9]{0,3}") 
    			&& name.length() <= 32 && winemaker.length() <= 32 && notes.length() <= 128 && vine.length() <=32)
    	{
    		Request rq = new Request("ADDWINE", parameters);
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
        	if (rs.getParameters().get(0).equals("Added"))
        	{
        		((Stage)confirmAddWineButton.getScene().getWindow()).close();
        	}
        	editTextWrong.setText("This wine already exists. Restock it!");
    	}
    	else if (!this.checkTextField(parameters))
    	{
    		editTextWrong.setText("Enter all parameters");
    	}
    	else
    	{
    		editTextWrong.setText("Insert valid values");
    	}
    }

    /**
     * 
     * Handles the event of pressing the editWine button. The only parameter that can be modified
     * is the amount. The server's response includes the list of all the notifications' request
     * of this specific wine. If the updated quantity of it is enough for a customer, the notification
     * is sent. 
     * 
     **/
    public void handleConfirmEditWineButton()
    {
    	String name = editWineName.getText();
    	String winemaker = editWineWinemaker.getText();
    	String amount = editWineAmount.getText();
    	List<String> parameters = new ArrayList<String>();
    	parameters.add(name);
    	parameters.add(winemaker);
    	parameters.add(amount);
    	if (this.checkTextField(parameters) && amount.matches("^[1-9][0-9]{0,3}"))
    	{
    		Request rq = new Request("RESTOCKWINE", parameters);
        	try 
        	{
    			Main.c.sendRequest(rq);
    			Main.c.receiveResponse();
    		} 
        	catch (IOException e) 
        	{
    			e.printStackTrace();
    		}
        	((Stage)confirmEditWineButton.getScene().getWindow()).close();	
    	}
    	else if (!this.checkTextField(parameters))
    	{
    		editTextWrong.setText("Enter all parameters");
    	}
    	else
    	{
    		editTextWrong.setText("Insert number in amount");
    	}
    }
    
    /**
     * 
     * Checks if all the text fields are not empty.
     * 
     * @param list of parameters taken from text fields
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
}