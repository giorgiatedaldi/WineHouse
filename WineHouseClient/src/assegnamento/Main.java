package assegnamento;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * @author Giorgia Tedaldi
 * @author Martina Frati
 * @author Diletta Quarticelli
 * 
 **/

/**
 * 
 * The {@code Main} class creates new clients and loads every time a new action stage.
 *
 **/
public class Main extends Application 
{	
	public static Client c;

	/**
	 * 
	 * Starts a new stage and creates a client.
	 * 
	 * @param actionStage starting stage
	 * 
	 **/
    @Override
    public void start(Stage actionStage)
    {
    	c = new Client();
    	Parent loginRoot = null;
		try 
		{
			loginRoot = FXMLLoader.load(getClass().getResource("actionScene.fxml"));
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
        actionStage.setTitle("WineHouse");
        actionStage.setScene(new Scene(loginRoot));
        actionStage.show();
    }
 
    /**
     * 
     * The method is ignored in correctly deployed JavaFX application.
     * It serves in case the application can not be launched through deployment artifacts.
     *
     * @param args the command line arguments
     * 
     **/
    public static void main(String[] args) 
    {
        launch(args);
    }
}