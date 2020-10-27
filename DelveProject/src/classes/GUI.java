package classes;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.awt.*;


public class GUI extends Application
{
    Scene startScreen, inventoryScreen, gameScreen;
    GameController gameController = new GameController();
    Map map;
    Player player;
    int healthValue;
    int manaValue;
    int goldValue;

    //constant value:

    //screen size
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    final int SCREEN_WIDTH = gd.getDisplayMode().getWidth();
    final int SCREEN_HEIGHT = gd.getDisplayMode().getHeight();

    //determine the direction character is going
    boolean  goUp, goDown, goLeft, goRight;
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

        startLabel.setTextAlignment(TextAlignment.CENTER);
        startLabel.setAlignment(Pos.CENTER);

        startButton.setTextAlignment(TextAlignment.CENTER);
        startButton.setAlignment(Pos.CENTER);

        VBox startLayout = new VBox(20);
        startLayout.setAlignment(Pos.CENTER);

        startLayout.getChildren().addAll(startLabel, startButton);
        startScreen = new Scene(startLayout, SCREEN_WIDTH, SCREEN_HEIGHT);
        
        //GameScreen
        //button going to Inventory Screen
        Button toInventory = new Button("Inventory");
        toInventory.setOnAction(e -> primaryStage.setScene(inventoryScreen));

        //mapScreen scene
        //elements of mapScreen
        Label mapLabel = new Label("This is the Map:");

        //Map of the Game and resizing the map
        GridPane guiMap = generateGuiMap(map);
        guiMap.setAlignment(Pos.CENTER);
        guiMap.setPrefSize(SCREEN_WIDTH * 0.75,SCREEN_HEIGHT * 0.75);

        //Abilities Bar:
        Label abilityLabel = new Label("Abilities");

        HBox abilityMenu = new HBox(5);
        Label health = new Label("Health: " + healthValue);
        Button abilityOne = new Button("Ability 1");
        Button abilityTwo = new Button("Ability 2");
        Button abilityThree = new Button("Ability 3");
        Button abilityFour = new Button("Ability 4");
        Button abilityFive = new Button("Ability 5");
        Label mana = new Label("Mana: " + manaValue);
        abilityMenu.getChildren().addAll(health, abilityOne, abilityTwo, abilityThree, abilityFour, abilityFive, mana);
        abilityMenu.setAlignment(Pos.BOTTOM_CENTER);

        //VBOX that holds everything on this scene

        VBox gameScreenLayout = new VBox(20);
        gameScreenLayout.getChildren().addAll(mapLabel, toInventory, guiMap, abilityLabel, abilityMenu);
        gameScreen = new Scene(gameScreenLayout, SCREEN_WIDTH, SCREEN_HEIGHT);

        //handle the player movement
        gameScreen.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode())
                {
                    case W:    goUp = true; break;
                    case S:  goDown = true; break;
                    case A:  goLeft  = true; break;
                    case D: goRight  = true; break;
                }
            }
        });

        gameScreen.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode())
                {
                    case W:    goUp = false; break;
                    case S:  goDown = false; break;
                    case A:  goLeft  = false; break;
                    case D: goRight  = false; break;
                }
            }
        });

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (goUp) {
                    //update the map
                    map.movePlayer(Direction.UP);
                    //update the grid
                    gameScreenLayout.getChildren().set(2,generateGuiMap(map));

                }
                if (goDown) {
                    //update the map
                    map.movePlayer(Direction.DOWN);
                    //update the grid
                    gameScreenLayout.getChildren().set(2,generateGuiMap(map));

                }
                if (goLeft) {
                    //update the map
                    map.movePlayer(Direction.LEFT);
                    //update the grid
                    gameScreenLayout.getChildren().set(2,generateGuiMap(map));
                }
                if (goRight) {
                    //update the map
                    map.movePlayer(Direction.RIGHT);
                    //update the grid
                    gameScreenLayout.getChildren().set(2,generateGuiMap(map));
                }
            }
        };
        timer.start();

        //Inventory Screen:
        VBox inventoryMenu = new VBox(10);
        Label inventoryLabel = new Label("Inventory");
        Label inventory_gold = new Label("Gold: " + goldValue);

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
        VBox inventoryScreenLayout = new VBox(10);
        inventoryScreenLayout.getChildren().addAll(inventoryMenu);
        inventoryScreen = new Scene(inventoryScreenLayout, SCREEN_WIDTH, SCREEN_HEIGHT);

        primaryStage.setScene(startScreen);
        primaryStage.show();
    }

    //method that generate map
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
        result.setAlignment(Pos.CENTER);
        result.setPrefSize(SCREEN_WIDTH * 0.75,SCREEN_HEIGHT * 0.75);
        return result;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
