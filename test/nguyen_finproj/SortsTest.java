/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nguyen_finproj;

import java.util.ArrayList;
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
public class SortsTest {

    public SortsTest() {
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
     * Test of mergesort method, of class Sorts.
     */
    @Test
    public void testMergesort() {
        System.out.println("mergesort");
        Score score1 = new Score(3, "");
        Score score2 = new Score(2, "");
        Score score3 = new Score(5, "");
        ArrayList<Score> items = new ArrayList();
        items.add(score1);
        items.add(score2);
        items.add(score3);

        int start = 0;
        int end = items.size() - 1;
        Sorts.mergesort(items, start, end);

        Score[] sorted = new Score[items.size()];

        for (int i = 0; i < items.size(); i++) {
            sorted[i] = items.get(i);
        }

        int expResult = 3;
        int result = 0;

        if (sorted[0].getTime() == 2) {
            result++;
        }
        if (sorted[1].getTime() == 3) {
            result++;
        }
        if (sorted[2].getTime() == 5) {
            result++;
        }

        assertEquals(expResult, result);
    }
}
