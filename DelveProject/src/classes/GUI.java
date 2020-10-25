package classes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class GUI extends Application
{
    Scene startScreen, abilityScreen, mapScreen, inventoryScreen, endScreen;
    @Override
    public void start(Stage primaryStage)
    {
        //setting the title
        primaryStage.setTitle("Delve");

        //StartScreen scene
        //elements of startScreen:
        Label startLabel = new Label("this is StartScreen");
        Button startButton = new Button("go to abilityScreen");
        startButton.setOnAction(e -> primaryStage.setScene(abilityScreen));
        VBox startLayout = new VBox(20);
        startLayout.getChildren().addAll(startLabel, startButton);
        startScreen = new Scene(startLayout, 300, 250);

        //abilityScreen scene
        //elements of AbilityScreen:
        Label abilityLabel = new Label("this is abilityScreen");
        Button mapButton = new Button("back to StartScreen");
        startButton.setOnAction(e -> primaryStage.setScene(startScreen));
        VBox abilityLayout = new VBox(20);
        abilityLayout.getChildren().addAll(abilityLabel, mapButton);
        abilityScreen = new Scene(abilityLayout, 300, 250);

        primaryStage.setScene(startScreen);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
