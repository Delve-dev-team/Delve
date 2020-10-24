import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class DeathScreen extends Application implements EventHandler<ActionEvent> {
    Button newGame = new Button();
    Button quit = new Button();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Delve");

        //Creates the New Game Button
        newGame.setText("New Game");
        newGame.setOnAction(e -> closeWindow());
        newGame.setOnAction(this);

        //Creates the Quit Button
        quit.setText("Quit");
        quit.setOnAction(e -> closeWindow());

        HBox layout = new HBox();


        layout.getChildren().add(newGame);
        layout.getChildren().add(quit);
        layout.setSpacing(10);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 1000, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void handle(ActionEvent event){
        if(event.getSource() == newGame){
            System.out.println("Starting new game");
            //Code to Start Delve
        }
    }

    @Override
    public void stop () throws Exception{
        System.out.println("Closing Delve");
    }

    private void closeWindow(){
        Stage stage = (Stage) newGame.getScene().getWindow();
        stage.close();
    }
}
