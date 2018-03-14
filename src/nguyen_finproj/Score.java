/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nguyen_finproj;

import java.io.Serializable;

/**
 *
 * @author Edward
 */
public class Score implements Comparable, Serializable  {

    int time;
    String difficulty;

    /**
     * Creates a score object.
     * pre: none.
     * post: sets score with time t, difficulty d.
     * @param t
     * @param d 
     */
    public Score(int t, String d) {
        time = t;
        difficulty = d;
    }

    /**
     * Returns difficulty.
     * pre: none.
     * post: returns difficulty.
     * @return 
     */
    public String getDifficulty() {
        return difficulty;
    }

    /**
     * Returns time.
     * pre: none.
     * post: returns time.
     * @return 
     */
    public int getTime() {
        return time;
    }

    /**
     * Compares the time of two Score objects.
     * pre: existing times.
     * post: compares the values of the times.
     * @param t
     * @return 
     */
    @Override
    public int compareTo(Object t) {
        Score obj = (Score) t;
        if (time > obj.getTime()) {
            return 1;
        } else if (time < obj.getTime()) {
            return -1;
        } else {
            return 0;
        }
    }
}
