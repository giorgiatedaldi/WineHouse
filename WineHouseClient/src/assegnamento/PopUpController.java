package assegnamento;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * 
 * The class {@code PopUpController} manages the structures of pop ups for: buy wine or request a 
 * notification (action stage), close the server and remove an employee (admin stage). 
 * Every event is handled through a new {@code Request} sent to the server and by its {@code Response}.
 * 
 * @see Request
 * @see Response
 *
 **/
public class PopUpController
{
    @FXML
    public Button popUpBuyButton;   
    @FXML
    public Button popUpNotificationButton;    
    @FXML
    public Button confirmRemoveButton;   
    @FXML
    public Button confirmCloseButton;    
    @FXML
    public Text popUpText;
    public String toRemove;
    private boolean check = false;    
    private boolean closeWindow = false;   
    private List<String> parameters = new ArrayList<String>();
     
    /**
     * 
     * Handles the event of pressing the button to close server. 
     * 
     **/
    public void handleConfirmCloseButton()
    {
    	Request rq = new Request ("CLOSESERVER", this.parameters);
    	try 
    	{
			Main.c.sendRequest(rq);
		} 
    	catch (IOException e) 
    	{
			e.printStackTrace();
		}
    	Response rs = Main.c.receiveResponse();
    	if (rs.getParameters().get(0).equals("Close server"))
    	{
    		Main.c.close();
    	}
    }
   
    /**
     * 
     * Handles the event of pressing the button to request a notification. 
     * 
     **/
    public void handlePopUpNotificationButton()
    {
    	Request rq = new Request("NOTIFY", this.parameters);
    	try 
    	{
			Main.c.sendRequest(rq);
		} 
    	catch (IOException e) 
    	{
			e.printStackTrace();
		}
    	Response rs = Main.c.receiveResponse();
    	popUpText.setText(rs.getParameters().get(0));
    	popUpNotificationButton.setDisable(true);
    }
    
    /**
     * 
     * Handles the event of pressing the button to buy a wine. 
     * 
     **/
    public void handlePopUpBuyButton()
    {
    	this.check = true;
    	if (this.closeWindow)
    	{
    		((Stage) popUpBuyButton.getScene().getWindow()).close();
    	}
    	popUpNotificationButton.setDisable(false);
    	popUpBuyButton.setDisable(true);
    }
   
    /**
     * 
     * Handles the event of pressing the button to confirm the removal of an employee. 
     * It's possible only if the selected employee is logged out. 
     * 
     **/
    public void handleConfirmRemoveButton() 
    {
    	List<String> parameters = new ArrayList<String>();
    	parameters.add(this.toRemove);
    	Request rq = new Request("REMOVEEMPLOYEE", parameters);
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
    	if(rs.getParameters().get(0).equals("Not removed"))
    	{
    		this.check = true;
    	}
    	((Stage)confirmRemoveButton.getScene().getWindow()).close();
    }
    
    /**
     * 
     * Gets the boolean to check if the button to confirm the purchase is pressed (action stage) 
     * or if the employee selected to be removed is logged out (admin stage).
     * 
     * @return check it's true if the confirmBuy/confirmRemove button is pressed.
     * 
     **/
    public boolean getCheck() 
    {
    	return check;
    }
    
    /**
     * 
     * Gets the boolean to choose if the window has to be closed after pressing the confirmation
     * of purchase or not. If the amount of the wine is not enough for the customer the notification
     * button will be enabled.
     * 
     * @param b it's true if the window has to be closed.
     * 
     **/
   public void getCloseWindow(boolean b)
    {
    	this.closeWindow = b;
    }
    
   /**
    * 
    * Sets the attributes "parameters" to "list": email of the customer who made the notification
    * request, wine's name, wine's winemaker and quantity requested by the customer are the data.
    * 
    * @param list contains the parameters of the notification
    * 
    **/
    public void notifyParameters(List<String> list)
    {
    	this.parameters = list;
    }
    
    /**
     * 
     * This function is called in adminScene, when the remove button is pressed. Sets the attribute
     * "toRemove" to the employee's email to remove.
     * 
     * @param s employee's email to remove
     * 
     **/
    public void receiveEmployeeToRemove(String s)
    {
    	this.toRemove = s;
    }
}