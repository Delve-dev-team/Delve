//import classes.Map;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

public class GameScreen extends Application implements EventHandler<ActionEvent> {
    Button inventory = new Button();
    Button back;
    Stage window;
    Scene scene1, scene2;
    // Player player = GameController.getPlayer();
    int healthValue = 100; // player.getHP();
    int manaValue = 100; // player.getMP();
    int goldValue = 100; //player.getGold();
    //Map map = GameController.getMap();

    @Override
    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;

        primaryStage.setTitle("Delve");
        inventory.setText("Inventory");
        inventory.setOnAction(e -> window.setScene(scene2));

        VBox layout = new VBox(10);
        Label health = new Label("Health: " + String.valueOf(healthValue));
        Label mana = new Label("Mana: " + String.valueOf(manaValue));
        Label gold = new Label("Gold: " + String.valueOf(goldValue));
        layout.getChildren().addAll(inventory, health, mana, gold);
        layout.setAlignment(Pos.TOP_RIGHT);
        String mapString = "placeholder"; //map.Print();
        Label gameMapLabel = new Label(mapString);
        layout.getChildren().add(gameMapLabel);


        Scene scene1 = new Scene(layout, 1000, 1000);


        //Add Functionality to Open Inventory
        VBox layout1 = new VBox(10);

        //Label:
        Label inventoryLabel = new Label("This is the inventory");

        //Back Button:
        back = new Button("Back");
        back.setOnAction(e -> window.setScene(scene1));
        layout1.getChildren().add(back);
        layout1.getChildren().add(inventoryLabel);

        //Creating the Scene
        scene2 = new Scene(layout1, 200, 200 );
        //Add Functionality to Open Inventory


        primaryStage.setScene(scene1);
        primaryStage.show();

    }

    @Override
    public void handle(ActionEvent event) {
    }


    @Override
    public void stop() throws Exception {

    }

    private void closeWindow() {

    }
}
