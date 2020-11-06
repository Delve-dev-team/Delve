package classes;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.awt.*;
import java.util.List;


public class GUI extends Application
{
    private Scene inventoryScreen;
    private Scene gameScreen;
    private GameController gameController = new GameController();
    private Map map;
    private Player player;
    private int goldValue;
    private static int round;

    //constant value:

    //screen size
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    final int SCREEN_WIDTH = gd.getDisplayMode().getWidth();
    final int SCREEN_HEIGHT = gd.getDisplayMode().getHeight();

    //determine the direction character is going
    boolean  goUp, goDown, goLeft, goRight, nextRoundPressed;

    @Override
    public void init() throws Exception {
        round = 1;
    }

    @Override
    public void start(Stage primaryStage)
    {

        //overall stats:
        map = GameController.getMap();
        player = map.getPlayer();

        //setting the title
        primaryStage.setTitle("Delve");

        //StartScreen scene
        //elements of startScreen:

        VBox startLayout = startScreen(primaryStage);

        Scene startScreen = new Scene(startLayout, SCREEN_WIDTH, SCREEN_HEIGHT);
        
        //GameScreen
        //button going to Inventory Screen
        Button toInventory = new Button("Inventory");
        toInventory.setOnAction(e -> primaryStage.setScene(inventoryScreen));

        //mapScreen scene
        //elements of mapScreen
        Label mapLabel = new Label("Map:");

        //Map of the Game and resizing the map
        GridPane guiMap = updateGuiMap(map);

        //Abilities Bar:
        Label abilityLabel = new Label("Abilities");
        HBox abilityMenu = updateAbilityMenu();

        //round number
        Label round = updateRound();

        //VBOX that holds everything on this scene
        VBox gameScreenLayout = new VBox(20);

        //next round button
        Button nextRound = new Button("end round");
        gameScreenLayout.getChildren().addAll(mapLabel, toInventory, round, guiMap, abilityLabel, abilityMenu, nextRound);
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

        //handle next round button
        nextRound.setOnAction(event -> {
            nextRoundPressed = true;
            nextRound.setDisable(true);
        });
        AnimationTimer timer = new AnimationTimer() {
            int enemyindex = 0;
            List<ObjectPosition> enemyPositions = map.getEnemiesPositions();
            @Override
            public void handle(long now) {
                if(player.getHP() <= 0)
                    deathScreen(primaryStage); //I'm not sure if this goes here
                if (goUp && player.getAp() > 0) {
                    //update the map
                    map.movePlayer(Direction.UP);
                    //update the grid
                    gameScreenLayout.getChildren().set(3, updateGuiMap(map));
                    //consume players ap
                    map.getPlayer().consumeAP(1);
                    gameScreenLayout.getChildren().set(5, updateAbilityMenu());
                }
                if (goDown && player.getAp() > 0) {
                    //update the map
                    map.movePlayer(Direction.DOWN);
                    //update the grid
                    gameScreenLayout.getChildren().set(3, updateGuiMap(map));
                    //consume players ap and update ability menu
                    map.getPlayer().consumeAP(1);
                    gameScreenLayout.getChildren().set(5, updateAbilityMenu());
                }
                if (goLeft && player.getAp() > 0) {
                    //update the map
                    map.movePlayer(Direction.LEFT);
                    //update the grid
                    gameScreenLayout.getChildren().set(3, updateGuiMap(map));
                    //consume players ap
                    map.getPlayer().consumeAP(1);
                    gameScreenLayout.getChildren().set(5, updateAbilityMenu());
                }
                if (goRight && player.getAp() > 0) {
                    //update the map
                    map.movePlayer(Direction.RIGHT);
                    //update the grid
                    gameScreenLayout.getChildren().set(3, updateGuiMap(map));
                    //consume players ap
                    map.getPlayer().consumeAP(1);
                    gameScreenLayout.getChildren().set(5, updateAbilityMenu());
                }

                // this part I used a very stupid way of updating enemies on the gui, when I used for loop, the enemies will all update at once,
                // I coded this part like this to update enemy one enemy at a time.
                if (nextRoundPressed) {
                    if (enemyindex == enemyPositions.size()) {
                        nextRoundPressed = false;
                        nextRound.setDisable(false);
                        //refresh Player's AP
                        player.refreshAp();
                        gameScreenLayout.getChildren().set(5, updateAbilityMenu());
                        //after all enemies have finished, enters next round
                        incrementRoundNumber();
                        gameScreenLayout.getChildren().set(2, updateRound());
                        //makes next round accesible again
                        nextRoundPressed = false;
                        nextRound.setDisable(false);
                        enemyindex = 0;
                    }
                    else{
                        ObjectPosition enemyPosition = enemyPositions.get(enemyindex);
                        System.out.println("operating an enemy...");
                        //enemies starts moving
                        Enemy enemy = map.getTileArray()[enemyPosition.getRowPosition()][enemyPosition.getColumnPosition()].getEnemy();
                        if (GameController.canXAttackY(enemyPosition, map.getPlayerPosition(), enemy.getAttackRange()))
                            enemy.attack(map);
                        else
                            map.moveEnemies(enemyPosition);
                        //enemies finished their moves, updating gui
                        //update map
                        gameScreenLayout.getChildren().set(3, updateGuiMap(map));
                        //update player stats
                        gameScreenLayout.getChildren().set(5, updateAbilityMenu());
                        enemyindex ++;
                    }
                }
            }
        };
        timer.start();

        //Inventory Screen:
        VBox inventoryMenu = updateInventory(primaryStage);

        //VBOX that holds everything on this scene
        //VBOX that holds everything on this scene
        VBox inventoryScreenLayout = new VBox(10);
        inventoryScreenLayout.getChildren().addAll(inventoryMenu);
        inventoryScreen = new Scene(inventoryScreenLayout, SCREEN_WIDTH, SCREEN_HEIGHT);

        primaryStage.setScene(startScreen);
        primaryStage.show();
    }

    //update the round (enemies' turns)
    //right now I just make the enemy to move if the enemy cannot attack, and attack if enemy can attack, the enemy only moves once
    private HBox updateAbilityMenu()
    {
        int healthValue = player.getHP();
        int manaValue = player.getMP();
        goldValue = player.getGold();
        int playerAp = player.getAp();
        HBox abilityMenu = new HBox(5);
        Label health = new Label("Health: " +healthValue);
        Button abilityOne = new Button("Ability 1");
        Button abilityTwo = new Button("Ability 2");
        Button abilityThree = new Button("Ability 3");
        Button abilityFour = new Button("Ability 4");
        Button abilityFive = new Button("Ability 5");
        Label mana = new Label("Mana: " +manaValue);
        Label ap = new Label("AP: " +playerAp);
        abilityMenu.getChildren().addAll(health, abilityOne, abilityTwo, abilityThree, abilityFour, abilityFive, mana, ap);
        abilityMenu.setAlignment(Pos.BOTTOM_CENTER);
        return abilityMenu;
    }
    //method that generate map
    private GridPane updateGuiMap(Map map)
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
        result.setPrefSize(SCREEN_WIDTH * 0.7,SCREEN_HEIGHT * 0.7);
        return result;
    }

    private Label updateRound()
    {
        Label round = new Label("Round: " +GUI.round);
        return round;
    }

    private void incrementRoundNumber(){
        round ++;
    }

    private VBox updateInventory(Stage primaryStage)
    {
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

        return inventoryMenu;
    }

    private VBox startScreen (Stage primaryStage){
        Label startLabel = new Label("Welcome to Delve");
        Button startButton = new Button("Start Game!");
        startButton.setOnAction(e -> primaryStage.setScene(gameScreen));

        startLabel.setTextAlignment(TextAlignment.CENTER);

        startButton.setTextAlignment(TextAlignment.CENTER);

        VBox startLayout = new VBox(20);
        startLayout.setAlignment(Pos.CENTER);

        startLayout.getChildren().addAll(startLabel, startButton);

        return startLayout;
    }

    private void deathScreen (Stage primaryStage){
        if (player.getHP() == 0 || player.getHP() < 0) {
            HBox deathOptions = new HBox(10);

            Button startNewGame = new Button("Start New Game");
            startNewGame.setOnAction(e -> {
                        primaryStage.close();

                        Stage stage = new Stage();
                        VBox startGame = startScreen(stage);
                        Scene newGameScene = new Scene(startGame);
                        stage.setScene(newGameScene);
                        stage.show();
                    }
            );

            Button endGame = new Button("End Game");
            endGame.setOnAction(e -> System.exit(0));

            deathOptions.getChildren().addAll(startNewGame, endGame);
            Scene deathScene = new Scene(deathOptions);
            primaryStage.setScene(deathScene);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }


}
