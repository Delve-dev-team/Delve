package classes;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class GUI extends Application
{
    Scene startScreen, abilityScreen, mapScreen, inventoryScreen, endScreen;
    GameController gameController = new GameController();
    Map map;
    Player player;
    int healthValue;
    int manaValue;
    int goldValue;
    @Override
    public void start(Stage primaryStage)
    {
        //overall stats:
        map = GameController.getMap();
        player = map.getPlayer();
        healthValue = player.getHP();
        manaValue = player.getMP();
        goldValue = player.getGold();

        //setting the title
        primaryStage.setTitle("Delve");

        //StartScreen scene
        //elements of startScreen:
        Label startLabel = new Label("Welcome to Delve");
        Button startButton = new Button("Start Game!");

        startButton.setOnAction(e -> primaryStage.setScene(mapScreen));
        VBox startLayout = new VBox(20);

        startLayout.getChildren().addAll(startLabel, startButton);
        startScreen = new Scene(startLayout, 300, 250);

        //GameScreen
        //abilityScreen scene
        //elements of AbilityScreen:
       
        Label abilityLabel = new Label("Abilities");
        Button abilityToMapButton = new Button("back to mapScreen");

        HBox abilityMenu = new HBox(5);
        Button abilityOne = new Button("Ability 1");
        Button abilityTwo = new Button("Ability 2");
        Button abilityThree = new Button("Ability 3");
        Button abilityFour = new Button("Ability 4");
        Button abilityFive = new Button("Ability 5");
        abilityMenu.getChildren().addAll(abilityOne, abilityTwo, abilityThree, abilityFour, abilityFive);

        abilityToMapButton.setOnAction(e -> primaryStage.setScene(mapScreen));
        VBox abilityLayout = new VBox(20);

        abilityLayout.getChildren().addAll(abilityLabel, abilityToMapButton, abilityMenu);
        abilityScreen = new Scene(abilityLayout, 300, 250);


        //mapScreen scene
        //elements of mapScreen
        Label mapLabel = new Label("this is mapScreen");
        Button abilityButton = new Button("go to ability Screen");
        Button inventoryButton = new Button("go to inventory Screen");
        abilityButton.setOnAction(e -> primaryStage.setScene(abilityScreen));
        inventoryButton.setOnAction(e -> primaryStage.setScene(inventoryScreen));
        //VBOX that holds stats of the player
        VBox statsOfPlayer = new VBox(10);
        Label health = new Label("Health: " + String.valueOf(healthValue));
        Label mana = new Label("Mana: " + String.valueOf(manaValue));
        Label gold = new Label("Gold: " + String.valueOf(goldValue));
        statsOfPlayer.getChildren().addAll(health, mana, gold);
        statsOfPlayer.setAlignment(Pos.TOP_RIGHT);
        //map of the game
        String mapString = map.gridsOnGui(); //map.Print();
        Label gameMap = new Label(mapString);
        gameMap.setFont(new Font("Arial", 5));
        //VBOX that holds everything on this scene
        VBox mapLayout = new VBox(20);

        mapLayout.getChildren().addAll(mapLabel, statsOfPlayer, abilityButton, inventoryButton, gameMap);
        mapScreen = new Scene(mapLayout, map.getTileArray().length * 10, map.getTileArray().length * 10);

        //Inventory Screen:
        
        VBox inventoryMenu = new VBox(10);
        Label inventoryLabel = new Label("Inventory");
        Label blankLabel = new Label();
        Label gold = new Label("Gold: " + String.valueOf(goldValue));

        HBox head = new HBox();
        Label headLabel = new Label("Head:");
        Label headEquiped = new Label(player.getHeadSlot); 
        head.getChildren().addAll(headLabel, headEquiped);
        head.setAlignment(Pos.CENTER);

        HBox chest = new HBox();
        Label chestLabel = new Label("Chest:");
        Label chestEquiped = new Label(player.getChestlot()); 
        chest.getChildren().addAll(chestLabel, chestEquiped);
        chest.setAlignment(Pos.CENTER);

        HBox arms = new HBox();
        Label armsLabel = new Label("Arms:");
        Label armsEquiped = new Label(player.getArmSlot()); 
        arms.getChildren().addAll(armsLabel, armsEquiped);
        arms.setAlignment(Pos.CENTER);

        HBox legs = new HBox();
        Label legsLabel = new Label("Legs:");
        Label legsEquiped = new Label(player.getLegSlot()); 
        legs.getChildren().addAll(legsLabel, legsEquiped);
        legs.setAlignment(Pos.CENTER);

        HBox feet = new HBox();
        Label feetLabel = new Label("Feet:");
        Label feetEquiped = new Label(player.getFeetSlot()); 
        feet.getChildren().addAll(feetLabel, feetEquiped);
        feet.setAlignment(Pos.CENTER);
        
        HBox hands = new HBox();
        Label handLabel = new Label("Hands:");
        Label handEquiped = new Label(player.getHandSlot()); 
        hands.getChildren().addAll(handLabel, handEquiped);
        hands.setAlignment(Pos.CENTER);

        //Inventory Back Button
        back = new Button("Back to mapScreen");
        back.setOnAction(e -> primaryStage.setScene(mapScreen));

        inventoryMenu.getChildren().addAll(inventoryLabel, gold, head, chest, arms, legs, hands, feet, back);
        inventoryMenu.setAlignment(Pos.CENTER);
        
        primaryStage.setScene(startScreen);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
