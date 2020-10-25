package classes;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class GUI extends Application
{
    Scene startScreen, inventoryScreen, gameScreen, endScreen;
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

        startButton.setOnAction(e -> primaryStage.setScene(gameScreen));
        VBox startLayout = new VBox(20);

        startLayout.getChildren().addAll(startLabel, startButton);
        startScreen = new Scene(startLayout, 300, 250);
        
        //GameScreen
        //button going to Inventory Screen
        Button toInventory = new Button("Inventory");
        toInventory.setOnAction(e -> primaryStage.setScene(inventoryScreen));

        //mapScreen scene
        //elements of mapScreen
        Label mapLabel = new Label("This is the Map:");

        //Map of the Game
        GridPane guiMap = generateGuiMap(map);

        //Abilities Bar:
        Label abilityLabel = new Label("Abilities");

        HBox abilityMenu = new HBox(5);
        Label health = new Label("Health: " + String.valueOf(healthValue));
        Button abilityOne = new Button("Ability 1");
        Button abilityTwo = new Button("Ability 2");
        Button abilityThree = new Button("Ability 3");
        Button abilityFour = new Button("Ability 4");
        Button abilityFive = new Button("Ability 5");
        Label mana = new Label("Mana: " + String.valueOf(manaValue));
        abilityMenu.getChildren().addAll(health, abilityOne, abilityTwo, abilityThree, abilityFour, abilityFive, mana);
        abilityMenu.setAlignment(Pos.BOTTOM_CENTER);

        //VBOX that holds everything on this scene
        VBox gameScreenLayout = new VBox(20);
        gameScreenLayout.getChildren().addAll(mapLabel, toInventory, guiMap, abilityLabel, abilityMenu);
        gameScreen = new Scene(gameScreenLayout, guiMap.getWidth() * 10, guiMap.getHeight() * 10);

        //Inventory Screen:
        VBox inventoryMenu = new VBox(10);
        Label inventoryLabel = new Label("Inventory");
        Label blankLabel = new Label();
        Label inventory_gold = new Label("Gold: " + String.valueOf(goldValue));

        HBox head = new HBox();
        Label headLabel = new Label("Head:");
        Label headEquiped = new Label(player.getHeadSlot());
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
        Button toGameScreen = new Button("Back to mapScreen");
        toGameScreen.setOnAction(e -> primaryStage.setScene(gameScreen));

        inventoryMenu.getChildren().addAll(inventoryLabel, inventory_gold, head, chest, arms, legs, hands, feet, toGameScreen);
        inventoryMenu.setAlignment(Pos.CENTER);
        //VBOX that holds everything on this scene
        //VBOX that holds everything on this scene
        VBox inventoryScreenLayout = new VBox(20);
        inventoryScreenLayout.getChildren().addAll(inventoryMenu);
        inventoryScreen = new Scene(inventoryScreenLayout, map.getTileArray().length * 10, map.getTileArray().length * 10);

        primaryStage.setScene(startScreen);
        primaryStage.show();
    }

    private GridPane generateGuiMap(Map map)
    {
        GridPane result = new GridPane();
        result.setHgap(10);
        for (int row = map.guiMapBoundaries().get(0); row < map.guiMapBoundaries().get(2); row ++) {
            for (int col = map.guiMapBoundaries().get(1); col < map.guiMapBoundaries().get(3); col++) {
                if (map.getTileArray()[row][col].isWallHere())
                    result.add(new Label("W"), row, col);
                else if (map.getTileArray()[row][col].isShopHere())
                    result.add(new Label("S"), row, col);
                else if (map.getTileArray()[row][col].isEnemyHere())
                    result.add(new Label("E"), row, col);
                else if (map.getTileArray()[row][col].isPlayerHere())
                    result.add(new Label("P"), row, col);
                else if (map.getTileArray()[row][col].isExitHere())
                    result.add(new Label("X"), row, col);
                else
                    result.add(new Label(" "), row, col);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
