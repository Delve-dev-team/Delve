package classes;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.awt.*;
import java.util.List;

import static javafx.scene.layout.GridPane.getColumnIndex;
import static javafx.scene.layout.GridPane.getRowIndex;


public class GUI extends Application
{
    private Scene inventoryScreen;
    private Scene gameScreen;
    private GameController gameController = new GameController();
    private Map map;
    private Player player;
    private int goldValue;
    private static int round;
    private int selectedTargetIndex = -1;
    private GridPane guiMap = new GridPane();
    private ChoiceBox<ObjectPosition> availableTargets = new ChoiceBox<>();
    //constant value:

    //screen size
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    final double SCREEN_WIDTH = gd.getDisplayMode().getWidth() * 0.95;
    final double SCREEN_HEIGHT = gd.getDisplayMode().getHeight() * 0.95;
    //final index value for ability menu
    final int ATTACK_INDEX = 1;
    //final index value for gamescreenlayout
    final int ROUND_NUM_INDEX = 2;
    final int MAPGUI_INDEX = 3;
    final int ABILITY_MENU_INDEX = 5;
    final int AVAILABLE_TARGETS = 6;
    //determine the direction character is going
    boolean  goUp, goDown, goLeft, goRight, nextRoundPressed, attackPressed,abilityOnePressed,abilityTwoPressed,abilityThreePressed,abilityFourPressed,isGameOver, attackButtonDisabled;

    @Override
    public void init() throws Exception {
        round = 1;
        map = GameController.getMap();
        player = map.getPlayer();
    }

    @Override
    public void start(Stage primaryStage)
    {
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
        updateGuiMap(map);

        //Abilities Bar:
        Label abilityLabel = new Label("Abilities");
        HBox abilityMenu = updateAbilityMenu();

        //round number
        Label round = updateRound();

        //VBOX that holds everything on this scene
        VBox gameScreenLayout = new VBox(20);

        //next round button
        Button nextRound = new Button("end round");

        //available targets window.
        updateAvailableTargets(guiMap);
        //layout
        gameScreenLayout.getChildren().addAll(mapLabel, toInventory, round, guiMap, abilityLabel, abilityMenu, availableTargets, nextRound);
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
            @Override
            public void handle(long now) {
                if (attackPressed || abilityOnePressed || abilityTwoPressed){
                    if (selectedTargetIndex!= -1) {
                        if (attackPressed) {
                            player.attack(selectedEnemy(selectedTargetIndex));
                            System.out.println("enemy health:"+selectedEnemy(selectedTargetIndex).getHP());
                            attackPressed = false;
                        }
                        if (abilityOnePressed) {
                            player.abilityOne(selectedEnemy(selectedTargetIndex));
                            System.out.println("enemy health:"+selectedEnemy(selectedTargetIndex).getHP());
                            abilityOnePressed = false;
                        }
                        if (abilityTwoPressed) {
                            player.abilityTwo(selectedEnemy(selectedTargetIndex));
                            System.out.println("enemy health:"+selectedEnemy(selectedTargetIndex).getHP());
                            abilityTwoPressed = false;
                        }
                        if (selectedEnemy(selectedTargetIndex).getHP() <= 0)
                        {
                            map.getTileArray()[selectedEnemy(selectedTargetIndex).getRowPosition()][selectedEnemy(selectedTargetIndex).getColPosition()].removeEnemy();
                            map.removeDeadEnemy();
                            updateGuiMap(map);
                        }

                        attackButtonDisabled = true;
                        availableTargets.setDisable(attackButtonDisabled);
                    }
                }
                if (abilityThreePressed){
                    player.abilityThree();
                    gameScreenLayout.getChildren().set(ABILITY_MENU_INDEX, updateAbilityMenu());
                    abilityThreePressed = false;
                }
                if (abilityFourPressed && player.getHP() > 100){
                    player.abilityFour();
                    gameScreenLayout.getChildren().set(ABILITY_MENU_INDEX, updateAbilityMenu());
                    abilityFourPressed = false;
                }
                if (gameController.isExitReach())
                {
                    Player temp_player = player;
                    gameController.enterNextLevel();
                    map = GameController.getMap();
                    int newRow = map.getPlayer().getRowPosition();
                    int newCol = map.getPlayer().getColPosition();
                    map.getTileArray()[newRow][newCol].removePlayer();
                    temp_player.setRowPosition(newRow);
                    temp_player.setColPosition(newCol);
                    map.getTileArray()[newRow][newCol].addPlayer(temp_player);
                    map.setPlayer(temp_player);
                    player = map.getPlayer();
                    guiMap.getChildren().clear();
                    updateGuiMap(map);
                    updateAvailableTargets(guiMap);
                    player.refreshAp();
                    player.setHP(player.getHP() + 50 * GameController.getCurrentLevel());
                    player.setMP(player.getMP() + 50 * GameController.getCurrentLevel());
                    attackButtonDisabled = false;
                    abilityMenu.getChildren().get(ATTACK_INDEX).setDisable(attackButtonDisabled);
                    updateAvailableTargets(guiMap);
                    availableTargets.setDisable(attackButtonDisabled);
                    updateGuiMap(map);
                    gameScreenLayout.getChildren().set(ABILITY_MENU_INDEX, updateAbilityMenu());
                    //after all enemies have finished, enters next round
                    incrementRoundNumber();
                    gameScreenLayout.getChildren().set(ROUND_NUM_INDEX, updateRound());
                    //makes next round accesible again
                    nextRoundPressed = false;
                    nextRound.setDisable(false);
                    enemyindex = 0;
                }
                if (isGameOver) {
                    primaryStage.setScene(deathScreen(primaryStage));
                    gameController = new GameController();
                    map = GameController.getMap();
                    player = map.getPlayer();
                    clearRound();
                    //update gui
                    updateGuiMap(map);
                    //update player stats
                    gameScreenLayout.getChildren().set(ABILITY_MENU_INDEX, updateAbilityMenu());
                    //update round num
                    gameScreenLayout.getChildren().set(ROUND_NUM_INDEX, updateRound());
                    isGameOver = false;
                }
                if (goUp && player.getAp() > 0) {
                    //update the map
                    map.movePlayer(Direction.UP);
                    updateGuiMap(map);
                    gameScreenLayout.getChildren().set(ABILITY_MENU_INDEX, updateAbilityMenu());
                    updateAvailableTargets(guiMap);
                }
                if (goDown && player.getAp() > 0) {
                    //update the map
                    map.movePlayer(Direction.DOWN);
                    updateGuiMap(map);
                    gameScreenLayout.getChildren().set(ABILITY_MENU_INDEX, updateAbilityMenu());
                    updateAvailableTargets(guiMap);

                }
                if (goLeft && player.getAp() > 0) {
                    //update the map
                    map.movePlayer(Direction.LEFT);
                    //update the grid
                    updateGuiMap(map);
                    gameScreenLayout.getChildren().set(ABILITY_MENU_INDEX, updateAbilityMenu());
                    updateAvailableTargets(guiMap);
                }
                if (goRight && player.getAp() > 0) {
                    //update the map
                    map.movePlayer(Direction.RIGHT);
                    //update the grid
                    //update available targets
                    updateGuiMap(map);
                    gameScreenLayout.getChildren().set(ABILITY_MENU_INDEX, updateAbilityMenu());
                    updateAvailableTargets(guiMap);
                }

                // this part I used a very stupid way of updating enemies on the gui, when I used for loop, the enemies will all update at once,
                // I coded this part like this to update enemy one enemy at a time.
                if (nextRoundPressed) {
                    List<ObjectPosition> enemyPositions = map.getEnemiesPositions();
                    //all enemies finished moving
                    if (enemyindex == enemyPositions.size()) {
                        nextRoundPressed = false;
                        nextRound.setDisable(false);
                        //refresh Player's AP
                        player.refreshAp();
                        attackButtonDisabled = false;
                        abilityMenu.getChildren().get(ATTACK_INDEX).setDisable(attackButtonDisabled);
                        availableTargets.setDisable(attackButtonDisabled);
                        updateGuiMap(map);
                        gameScreenLayout.getChildren().set(ABILITY_MENU_INDEX, updateAbilityMenu());
                        //after all enemies have finished, enters next round
                        incrementRoundNumber();
                        gameScreenLayout.getChildren().set(ROUND_NUM_INDEX, updateRound());
                        //makes next round accesible again
                        nextRoundPressed = false;
                        nextRound.setDisable(false);
                        enemyindex = 0;
                    }
                    //some enemies still hasn't moved
                    else{
                        ObjectPosition enemyPosition = enemyPositions.get(enemyindex);
                        System.out.println("operating an enemy " + enemyindex);
                        //enemies starts moving
                        Enemy enemy = map.getTileArray()[enemyPosition.getRowPosition()][enemyPosition.getColumnPosition()].getEnemy();
                        if (GameController.canXAttackY(enemyPosition, map.getPlayerPosition(), enemy.getAttackRange())){
                            enemy.attack(map);
                            //if enemy killed player
                            if (gameController.isGameOver()) {
                                isGameOver = true;
                            }
                        }
                        else
                            map.moveEnemies(enemyPosition);
                        //enemies finished their moves, updating gui
                        //update map
                        updateGuiMap(map);
                        //update player stats
                        gameScreenLayout.getChildren().set(ABILITY_MENU_INDEX, updateAbilityMenu());
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
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    //update the round (enemies' turns)
    //right now I just make the enemy to move if the enemy cannot attack, and attack if enemy can attack, the enemy only moves once
    private HBox updateAbilityMenu()
    {
        selectedTargetIndex = -1;
        int healthValue = player.getHP();
        int manaValue = player.getMP();
        goldValue = player.getGold();
        int playerAp = player.getAp();
        HBox abilityMenu = new HBox(5);
        Label health = new Label("Health: " +healthValue);
        Button attack = new Button("Attack!");
        attack.setOnAction(event -> attackPressed = true);
        Button abilityOne = new Button("Ability 1");
        abilityOne.setOnAction(event -> abilityOnePressed = true);
        Button abilityTwo = new Button("Ability 2");
        abilityTwo.setOnAction(event -> abilityTwoPressed = true);
        Button abilityThree = new Button("Ability 3");
        abilityThree.setOnAction(e -> abilityThreePressed = true);
        Button abilityFour = new Button("Ability 4");
        abilityFour.setOnAction(event -> abilityFourPressed = true);
        Label mana = new Label("Mana: " +manaValue);
        Label ap = new Label("AP: " +playerAp);
        abilityMenu.getChildren().addAll(health, attack, abilityOne, abilityTwo, abilityThree, abilityFour,  mana, ap);
        abilityMenu.setAlignment(Pos.BOTTOM_CENTER);
        return abilityMenu;
    }
    //method that generate map
    private void updateGuiMap(Map map)
    {
        for (int row = 0; row < map.getTileArray().length; row ++){
            for (int col = 0; col < map.getTileArray()[0].length; col ++){
                if (!(row <= map.getPlayer().getRowPosition() + 9 && row >= map.getPlayer().getRowPosition() - 9
                        && col <= map.getPlayer().getColPosition() + 9 && col >= map.getPlayer().getColPosition() - 9)) {
                    int finalRow = row;
                    int finalCol = col;
                    guiMap.getChildren().removeIf(node -> getColumnIndex(node) == finalRow && getRowIndex(node) == finalCol);
                }
            }
        }
        guiMap.setHgap(10);
        for (int row = map.guiMapBoundaries().get(0); row < map.guiMapBoundaries().get(2); row ++) {
            for (int col = map.guiMapBoundaries().get(1); col < map.guiMapBoundaries().get(3); col++) {
                int finalRow = row;
                int finalCol = col;
                guiMap.getChildren().removeIf(node -> getColumnIndex(node) == finalRow && getRowIndex(node) == finalCol);
                if (map.getTileArray()[row][col].isWallHere())
                    guiMap.add(new Label("W"), row, col);
                else if (map.getTileArray()[row][col].isShopHere())
                    guiMap.add(new Label("S"), row, col);
                else if (map.getTileArray()[row][col].isEnemyHere()) {
                    if (map.getTileArray()[row][col].getEnemy().isDisarmed())
                        guiMap.add(new Label("F"), row, col);
                    else
                        guiMap.add(new Label("E"), row, col);
                }
                else if (map.getTileArray()[row][col].isPlayerHere())
                    guiMap.add(new Label("P"), row, col);
                else if (map.getTileArray()[row][col].isExitHere())
                    guiMap.add(new Label("X"), row, col);
                else
                    guiMap.add(new Label(" "), row, col);
            }
        }
        guiMap.setAlignment(Pos.CENTER);
        guiMap.setScaleX(0.85);
        guiMap.setScaleY(0.85);
        guiMap.setMaxSize(SCREEN_WIDTH * 0.6, SCREEN_HEIGHT * 0.6);
    }

    private Label updateRound()
    {
        return new Label("Round: " +GUI.round);
    }

    private void incrementRoundNumber(){
        round ++;
    }

    private void clearRound(){
        round = 0;
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

    private Scene deathScreen (Stage primaryStage){
            HBox deathOptions = new HBox(10);

            Button startNewGame = new Button("Start New Game");
            startNewGame.setOnAction(e -> {
                        round = 1;
                        gameController = new GameController();
                        map = GameController.getMap();
                        player = map.getPlayer();
                        nextRoundPressed = false;
                            //refresh Player's AP
                        player.refreshAp();
                        attackButtonDisabled = false;
                        availableTargets.setDisable(attackButtonDisabled);
                        guiMap.getChildren().clear();
                        updateGuiMap(map);
                        //makes next round accesible again
                        nextRoundPressed = false;
                        primaryStage.setScene(new Scene(startScreen(primaryStage),SCREEN_WIDTH, SCREEN_HEIGHT));
                        selectedTargetIndex = 0;
                    }
            );

            Button endGame = new Button("End Game");
            endGame.setOnAction(e -> System.exit(0));

            deathOptions.getChildren().addAll(startNewGame, endGame);
            deathOptions.setAlignment(Pos.CENTER);
        return new Scene(deathOptions, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    private Enemy selectedEnemy(int selectedTargetIndex)
    {
        int row = gameController.availableTargets().get(selectedTargetIndex).getRowPosition();
        int col = gameController.availableTargets().get(selectedTargetIndex).getColumnPosition();
        return map.getTileArray()[row][col].getEnemy();
    }

    private void updateAvailableTargets(GridPane guiMap){
        if (!availableTargets.getItems().isEmpty()) {
            availableTargets.getItems().clear();
        }
        availableTargets.getItems().addAll(gameController.availableTargets());
        availableTargets.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            // if the item of the list is changed
            public void changed(ObservableValue ov, Number value, Number new_value)
            {
                Label selected = new Label("A");
                Label unselected = new Label("E");
                System.out.println(value.intValue() + " " + new_value.intValue());
                int newRow = gameController.availableTargets().get(new_value.intValue()).getRowPosition();
                int newCol = gameController.availableTargets().get(new_value.intValue()).getColumnPosition();
                guiMap.getChildren().removeIf(node -> node instanceof Label && getColumnIndex(node) == newRow && getRowIndex(node) == newCol);
                guiMap.add(selected,newRow,newCol);

                if (value.intValue() != -1) {
                    int oldRow = gameController.availableTargets().get(value.intValue()).getRowPosition();
                    int oldCol = gameController.availableTargets().get(value.intValue()).getColumnPosition();
                    guiMap.getChildren().removeIf(node -> node instanceof Label && getColumnIndex(node) == oldRow && getRowIndex(node) == oldCol);
                    guiMap.add(unselected, oldRow, oldCol);
                }
                selectedTargetIndex = new_value.intValue();
                selected.autosize();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}
