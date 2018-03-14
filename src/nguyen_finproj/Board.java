package nguyen_finproj;

/**
 *
 * @author Edward
 */
public class Board {

    Board temp;
    int[][] grid;
    int rows, columns;
    boolean[][] repeatCheck;
    int bombCount;
    int count;

    /**
     * Creates a board with dimensions x,y.
     * Sets bombs to random indexes;
     * pre: none.
     * post: board created.
     * @param y
     * @param x 
     */
    public Board(int y, int x) {
        grid = new int[y][x];
        rows = y;
        columns = x;
        bombCount = grid.length * grid[0].length / 5;
        repeatCheck = new boolean[grid.length][grid[0].length];
        count = grid.length * grid[0].length - bombCount;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = 0;
                repeatCheck[i][j] = false;
            }
        }

        do {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (bombCount != 0) {
                        if (grid[i][j] == 0) {
                            grid[i][j] = (int) (Math.random() * 10 + 1);
                            if (grid[i][j] == 1) {
                                bombCount--;
                            } else {
                                grid[i][j] = 0;
                            }
                        }
                    }
                }
            }
        } while (bombCount != 0);
    }

    /**
     * Sets board to given grid and repeatChecker;
     * pre: none.
     * post: board created with given values.
     * @param setUp
     * @param checker
     */
    public Board(int[][] setUp, boolean[][] checker) {
        grid = setUp;
        repeatCheck = checker;
    }

    /**
     * Checks number of adjacent mines at given coordinate.
     * pre: existing coordinate.
     * post: number of adjacent mines is returned.
     * @param cellRow
     * @param cellCol
     * @return 
     */
    public int neighbourMines(int cellRow, int cellCol) {
        int neighbours = 0;
        
        if (cellRow == 0) {
            if (cellCol == 0) {
                for (int i = cellRow; i <= cellRow + 1; i++) {
                    for (int j = cellCol; j <= cellCol + 1; j++) {
                        if (grid[i][j] == 1 || grid[i][j] == 4) {
                            neighbours++;
                        }
                    }
                }
            } else if (cellCol == grid.length - 1) {
                for (int i = cellRow; i <= cellRow + 1; i++) {
                    for (int j = cellCol - 1; j <= cellCol; j++) {
                        if (grid[i][j] == 1 || grid[i][j] == 4) {
                            neighbours++;
                        }
                    }
                }
            } else {
                for (int i = cellRow; i <= cellRow + 1; i++) {
                    for (int j = cellCol - 1; j <= cellCol + 1; j++) {
                        if (grid[i][j] == 1 || grid[i][j] == 4) {
                            neighbours++;
                        }
                    }
                }
            }
        } else if (cellRow == grid.length - 1) {
            if (cellCol == 0) {
                for (int i = cellRow - 1; i <= cellRow; i++) {
                    for (int j = cellCol; j <= cellCol + 1; j++) {
                        if (grid[i][j] == 1 || grid[i][j] == 4) {
                            neighbours++;
                        }
                    }
                }
            } else if (cellCol == grid[0].length - 1) {
                for (int i = cellRow - 1; i <= cellRow; i++) {
                    for (int j = cellCol - 1; j <= cellCol; j++) {
                        if (grid[i][j] == 1 || grid[i][j] == 4) {
                            neighbours++;
                        }
                    }
                }
            } else {
                for (int i = cellRow - 1; i <= cellRow; i++) {
                    for (int j = cellCol - 1; j <= cellCol + 1; j++) {
                        if (grid[i][j] == 1 || grid[i][j] == 4) {
                            neighbours++;
                        }
                    }
                }
            }
        } else if (cellCol == 0) {
            for (int i = cellRow - 1; i <= cellRow + 1; i++) {
                for (int j = cellCol; j <= cellCol + 1; j++) {
                    if (grid[i][j] == 1 || grid[i][j] == 4) {
                        neighbours++;
                    }
                }
            }
        } else if (cellCol == grid[0].length - 1) {
            for (int i = cellRow - 1; i <= cellRow + 1; i++) {
                for (int j = cellCol - 1; j <= cellCol; j++) {
                    if (grid[i][j] == 1 || grid[i][j] == 4) {
                        neighbours++;
                    }
                }
            }
        } else {
            for (int i = cellRow - 1; i <= cellRow + 1; i++) {
                for (int j = cellCol - 1; j <= cellCol + 1; j++) {
                    if (grid[i][j] == 1 || grid[i][j] == 4) {
                        neighbours++;
                    }
                }
            }
        }
        if (grid[cellRow][cellCol] == 1) {
            neighbours--;
        }
        return neighbours;
    }

    /**
     * Checks if there are no adjacent mines at given coordinate.
     * pre: existing coordinate.
     * post: returns true if no adjacent mines, false otherwise.
     * @param cellRow
     * @param cellCol
     * @return 
     */
    public boolean safeZone(int cellRow, int cellCol) {

        boolean safe;
        temp = new Board(grid, repeatCheck);

        if (temp.neighbourMines(cellRow, cellCol) == 0) {
            safe = true;
        } else {
            safe = false;
        }

        return safe;
    }

    /**
     * Checks if adjacent indexes are safe, sets values to all adjacent safeZones to 2.
     * pre: existing coordinates.
     * post: value of given coordinate and all adjacent safeZones are set to 2.
     * @param cellRow
     * @param cellCol 
     */
    public void safeArea(int cellRow, int cellCol) {

        if (!repeatCheck[cellRow][cellCol]) {
            temp = new Board(grid, repeatCheck);
            repeatCheck[cellRow][cellCol] = true;

            if (cellRow == 0) {
                if (cellCol == 0) {
                    for (int i = cellRow; i <= cellRow + 1; i++) {
                        for (int j = cellCol; j <= cellCol + 1; j++) {
                            if (temp.safeZone(cellRow, cellCol)) {
                                if (grid[i][j] != 3 && grid[i][j] != 4) {
                                    grid[i][j] = 2;
                                    temp.safeArea(i, j);
                                }
                            }
                        }
                    }
                } else if (cellCol == grid.length - 1) {
                    for (int i = cellRow; i <= cellRow + 1; i++) {
                        for (int j = cellCol - 1; j <= cellCol; j++) {
                            if (temp.safeZone(cellRow, cellCol)) {
                                if (grid[i][j] != 3 && grid[i][j] != 4) {
                                    grid[i][j] = 2;
                                    temp.safeArea(i, j);
                                }
                            }
                        }
                    }
                } else {
                    for (int i = cellRow; i <= cellRow + 1; i++) {
                        for (int j = cellCol - 1; j <= cellCol + 1; j++) {
                            if (temp.safeZone(cellRow, cellCol)) {
                                if (grid[i][j] != 3 && grid[i][j] != 4) {
                                    grid[i][j] = 2;
                                    temp.safeArea(i, j);
                                }
                            }
                        }
                    }
                }
            } else if (cellRow == grid.length - 1) {
                if (cellCol == 0) {
                    for (int i = cellRow - 1; i <= cellRow; i++) {
                        for (int j = cellCol; j <= cellCol + 1; j++) {
                            if (temp.safeZone(cellRow, cellCol)) {
                                if (grid[i][j] != 3 && grid[i][j] != 4) {
                                    grid[i][j] = 2;
                                    temp.safeArea(i, j);
                                }
                            }
                        }
                    }
                } else if (cellCol == grid[0].length - 1) {
                    for (int i = cellRow - 1; i <= cellRow; i++) {
                        for (int j = cellCol - 1; j <= cellCol; j++) {
                            if (temp.safeZone(cellRow, cellCol)) {
                                if (grid[i][j] != 3 && grid[i][j] != 4) {
                                    grid[i][j] = 2;
                                    temp.safeArea(i, j);
                                }
                            }
                        }
                    }
                } else {
                    for (int i = cellRow - 1; i <= cellRow; i++) {
                        for (int j = cellCol - 1; j <= cellCol + 1; j++) {
                            if (temp.safeZone(cellRow, cellCol)) {
                                if (grid[i][j] != 3 && grid[i][j] != 4) {
                                    grid[i][j] = 2;
                                    temp.safeArea(i, j);
                                }
                            }
                        }
                    }
                }
            } else if (cellCol == 0) {
                for (int i = cellRow - 1; i <= cellRow + 1; i++) {
                    for (int j = cellCol; j <= cellCol + 1; j++) {
                        if (temp.safeZone(cellRow, cellCol)) {
                            if (grid[i][j] != 3 && grid[i][j] != 4) {
                                grid[i][j] = 2;
                                temp.safeArea(i, j);
                            }
                        }
                    }
                }
            } else if (cellCol == grid[0].length - 1) {
                for (int i = cellRow - 1; i <= cellRow + 1; i++) {
                    for (int j = cellCol - 1; j <= cellCol; j++) {
                        if (temp.safeZone(cellRow, cellCol)) {
                            if (grid[i][j] != 3 && grid[i][j] != 4) {
                                grid[i][j] = 2;
                                temp.safeArea(i, j);
                            }
                        }
                    }
                }
            } else {
                for (int i = cellRow - 1; i <= cellRow + 1; i++) {
                    for (int j = cellCol - 1; j <= cellCol + 1; j++) {
                        if (temp.safeZone(cellRow, cellCol)) {
                            if (grid[i][j] != 3 && grid[i][j] != 4) {
                                grid[i][j] = 2;
                                temp.safeArea(i, j);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks if the game is over.
     * pre: existing game.
     * post: returns true when count is equal to zero, false otherwise.
     * @return 
     */
    public boolean gameEndWin() {

        if (count == 0) {
            return true;
        } else {
            return false;
        }
    }
}
