# WineHouse
Software for the management of a Wine House implemented using Java-FX.

## Requirements
- [Java](https://www.java.com/it/download/) 
- [Eclipse](https://www.eclipse.org/downloads/)
- [JavaFx](https://gluonhq.com/products/javafx/)
-apache
-connector

## Download and Setup of the projects
### Download WineHouse code
Clone the github repository
```

git clone https://github.com/giorgiatedaldi/WineHouse/

```

###Create Server Project



###Create Client Project
Open Eclipse and go to **preferences > Java > Build Path > User Libraries ** and press the **New** button and name it **JavaFX**.
Select the library you just created and click on **Add External JARs** and select all the .jar files from the directory where JavaFX library is saved.

Now open Eclipse's preferences and select **Java > Installed JREs**
Select the JRE version you are using and **duplicate** it. In the **JRE Definition** change the JRE name in **JRE+JavaFX** and in the **default VM arguments** past the following line on **Linux** or **Mac**:

```

--module-path /path/to/javafx-sdk-17/lib --add-modules javafx.controls,javafx.fxml

```
or this line for **Windows**

```

--module-path /Users/pragways/Documents/javafx-sdk-17.0.1/lib --add-modules javafx.controls,javafx.fxml
```

Click on **Add External JARs** and select all the .jar files from the /lib/ folder of the JavaFX folder, click **Open** and then **Finish**

Create a new **Java Project** and select **Use a project specific JRE** and select **Java+JavaFX**
You can know copy and paste the


##Add configurations
-requisiti
-setupprogetto
-database
