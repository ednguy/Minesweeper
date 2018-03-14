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
public class ScoreTest {

    public ScoreTest() {
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
     * Test of getDifficulty method, of class Score.
     */
    @Test
    public void testGetDifficulty() {
        System.out.println("getDifficulty");
        Score instance = new Score(7, "hard");

        String expResult = "hard";
        String result = instance.getDifficulty();
        assertEquals(expResult, result);

        instance = new Score(5, "medium");
        expResult = "medium";
        result = instance.getDifficulty();
        assertEquals(expResult, result);

        instance = new Score(4, "easy");
        expResult = "easy";
        result = instance.getDifficulty();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTime method, of class Score.
     */
    @Test
    public void testGetTime() {
        System.out.println("getTime");
        Score instance = new Score(7, "hard");

        int expResult = 7;
        int result = instance.getTime();
        assertEquals(expResult, result);

        instance = new Score(5, "medium");
        expResult = 5;
        result = instance.getTime();
        assertEquals(expResult, result);

        instance = new Score(4, "easy");
        expResult = 4;
        result = instance.getTime();
        assertEquals(expResult, result);
    }

    /**
     * Test of compareTo method, of class Score.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        Object t = new Score(4, "");
        Score instance = new Score(3, "");

        int expResult = -1;
        int result = instance.compareTo(t);
        assertEquals(expResult, result);

        instance = new Score(5, "");
        expResult = 1;
        result = instance.compareTo(t);
        assertEquals(expResult, result);

        instance = new Score(4, "");
        expResult = 0;
        result = instance.compareTo(t);
        assertEquals(expResult, result);
    }
}
