package tests;

import classes.*;
import javafx.application.Application;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {


    @Test
    void main()throws IOException {
        Application.launch(GUI.class);
    }

    @Test
    void isGameOver() {
        GameController gameController = new GameController();
        GameController.getMap().getPlayer().setHP(0);
        assertTrue(gameController.isGameOver());
        GameController.getMap().getPlayer().setHP(1);
        assertFalse(gameController.isGameOver());
        GameController.getMap().getPlayer().setHP(-100);
        assertTrue(gameController.isGameOver());
    }

    @Test
    void isExitReach() {
        GameController gameController = new GameController();
        for (int row = 0; row < GameController.getMap().getTileArray().length; row ++)
        {
            for(int col = 0; col < GameController.getMap().getTileArray().length; col ++){
                if (GameController.getMap().getTileArray()[row][col].isExitHere())
                {
                    //teleport player to exit
                    GameController.getMap().getTileArray()[row][col].
                            addPlayer(GameController.getMap().
                                    getTileArray()
                                    [ GameController.getMap().getPlayerPosition().getRowPosition()]
                                    [GameController.getMap().getPlayerPosition().getColumnPosition()].
                                    removePlayer());
                }
            }
        }
        assertTrue(gameController.isExitReach());
        gameController = new GameController();
        assertFalse(gameController.isExitReach());
    }

    @Test
    void isShopReach() {
        GameController gameController = new GameController();
        for (int row = 0; row < GameController.getMap().getTileArray().length; row ++)
        {
            for(int col = 0; col < GameController.getMap().getTileArray().length; col ++){
                if (GameController.getMap().getTileArray()[row][col].isShopHere())
                {
                    //teleport player to shop
                    System.out.println("found shop");
                    GameController.getMap().getTileArray()[row][col].
                            addPlayer(
                                    GameController.getMap().getTileArray()
                                    [GameController.getMap().getPlayerPosition().getRowPosition()]
                                    [GameController.getMap().getPlayerPosition().getColumnPosition()].
                                    removePlayer());
                }
            }
        }
        int count = 0;
        for (Tile[] tiles : GameController.getMap().getTileArray()) {
            for (int col = 0; col < GameController.getMap().getTileArray()[0].length; col++) {
                if ((tiles[col].isShopHere()))
                    count ++;
            }
        }
        if (count != 0)
            assertTrue(gameController.isShopReach());
        else
            assertFalse(gameController.isShopReach());


    }

    @Test
    void isThereAvailableTarget() {
        GameController gameController = new GameController();
        Map map = GameController.getMap();

        if (gameController.availableTargets().isEmpty())
            assertFalse(gameController.isThereAvailableTarget());
        else
            assertTrue(gameController.isThereAvailableTarget());
    }

    @Test
    void availableTargets() {
        GameController gameController = new GameController();
        Map map = GameController.getMap();
        for (ObjectPosition positon: map.getEnemiesPositions())
        {
            if (map.shortestPath(map.getTileArray(),positon,map.getPlayerPosition()) <= map.getPlayer().getAttackRange())
                assertTrue(gameController.availableTargets().contains(positon));
        }
    }

    @Test
    void enterNextLevel() {
        GameController gameController = new GameController();
        gameController.enterNextLevel();
        assertEquals(GameController.getCurrentLevel(), 2);
    }

    @Test
    void getCurrentLevel() {
        GameController gameController = new GameController();
        assertEquals((int)(GameController.getMap().getEnemies().get(0).getHP() / 50), GameController.getCurrentLevel());
    }

    @Test
    void getMap() {
        GameController gameController = new GameController();
        assertEquals(GameController.getMap(),GameController.getMap());
    }

    @Test
    void enemiesMoveIfNotAttack() {
        
    }

    @Test
    void exitReachedNextLevel() {

    }
}