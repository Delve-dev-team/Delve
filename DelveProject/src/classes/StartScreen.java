package classes;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

//Application.launch(StartScreen.class, args);

public class StartScreen extends Application implements EventHandler<ActionEvent> {
    Button startButton = new Button();



    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Delve");

        startButton.setText("Start");
        startButton.setOnAction(this);
        startButton.setOnAction(e -> closeProgram());

        StackPane layout = new StackPane();
        layout.getChildren().add(startButton);

        Scene scene = new Scene(layout, 1000, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    @Override
    public void handle(ActionEvent event){
        if(event.getSource() == startButton){
            System.out.println("The Start Button works");
            //Code to Start Delve
        }
    }

    @Override
    public void stop () throws Exception{
        System.out.println("Starting Delve");
    }

    private void closeProgram(){
        System.out.println("Closing the Start Window");
        Stage stage = (Stage) startButton.getScene().getWindow();
        stage.close();
    }


}
