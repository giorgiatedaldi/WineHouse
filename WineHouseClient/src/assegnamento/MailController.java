package assegnamento;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

/**
*
* The {@code MailController} class deals with the displaying of client's notifications.
* The new messages are embedded in a list and removed as soon as the customer reads them.
*
**/
public class MailController 
{
	@FXML
    private ListView<String> mailListView;
	private ObservableList<String> displayNotifications = FXCollections.observableArrayList();
   
	/**
	 *
	 * Gets the notifications' observable list.
	 *
	 * @return notifications' observable list
	 *
	 **/
    public ObservableList<String> getDisplayNotifications()
    {
    	return this.displayNotifications;
    }

    /**
    *
    * Sets the updated items and displays them in the list.
    *
    **/
    public void initialize()
    {  	
    	mailListView.setItems(this.displayNotifications);	
    }
}