/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nguyen_finproj;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Edward
 */
public class BoardTest {

    public BoardTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of neighbourMines method, of class Board.
     */
    @Test
    public void testNeighbourMines() {
        System.out.println("neighbourMines");
        int cellRow = 1;
        int cellCol = 1;
        Board instance = new Board(3, 3);
        for (int i = 0; i < instance.grid.length; i++) {
            for (int j = 0; j < instance.grid[0].length; j++) {
                instance.grid[i][j] = 0;
            }
        }
        int expResult = 0;
        int result = instance.neighbourMines(cellRow, cellCol);
        assertEquals(expResult, result);

        instance.grid[0][0] = 1;
        expResult = 1;
        result = instance.neighbourMines(cellRow, cellCol);
        assertEquals(expResult, result);
    }

    /**
     * Test of safeZone method, of class Board.
     */
    @Test
    public void testSafeZone() {
        System.out.println("safeZone");
        int cellRow = 1;
        int cellCol = 1;
        Board instance = new Board(3, 3);
        for (int i = 0; i < instance.grid.length; i++) {
            for (int j = 0; j < instance.grid[0].length; j++) {
                instance.grid[i][j] = 0;
            }
        }

        boolean expResult = true;
        boolean result = instance.safeZone(cellRow, cellCol);
        assertEquals(expResult, result);

        instance.grid[0][0] = 1;
        expResult = false;
        result = instance.safeZone(cellRow, cellCol);
        assertEquals(expResult, result);

        instance.grid[0][0] = 3;
        expResult = true;
        result = instance.safeZone(cellRow, cellCol);
        assertEquals(expResult, result);

        instance.grid[0][0] = 4;
        expResult = false;
        result = instance.safeZone(cellRow, cellCol);
        assertEquals(expResult, result);
    }

    /**
     * Test of safeArea method, of class Board.
     */
    @Test
    public void testSafeArea() {
        System.out.println("safeArea");
        int cellRow = 1;
        int cellCol = 1;
        int result = 0;
        Board instance = new Board(3, 3);

        for (int i = 0; i < instance.grid.length; i++) {
            for (int j = 0; j < instance.grid[0].length; j++) {
                instance.grid[i][j] = 0;
            }
        }
        instance.safeArea(cellRow, cellCol);

        for (int i = 0; i < instance.grid.length; i++) {
            for (int j = 0; j < instance.grid[0].length; j++) {
                if (instance.grid[i][j] == 2) {
                    result++;
                }
            }
        }

        int expResult = 9;
        assertEquals(expResult, result);
    }

    /**
     * Test of gameEndWin method, of class Board.
     */
    @Test
    public void testGameEndWin() {
        System.out.println("gameEndWin");
        int game = 0;
        Board instance = new Board(3, 3);
        instance.count = 0;

        boolean expResult = true;
        boolean result = instance.gameEndWin();
        assertEquals(expResult, result);

        instance.count = 1;
        expResult = false;
        result = instance.gameEndWin();
        assertEquals(expResult, result);
    }

}
