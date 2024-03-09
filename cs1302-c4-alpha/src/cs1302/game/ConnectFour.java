
package cs1302.game;

import cs1302.gameutil.GamePhase;
import cs1302.gameutil.Token;
import cs1302.gameutil.TokenGrid;

/**
 * {@code ConnectFour} represents a two-player connection game involving a two-dimensional grid of
 * {@linkplain cs1302.gameutil.Token tokens}. When a {@code ConnectFour} game object is
 * constructed, several instance variables representing the game's state are initialized and
 * subsequently accessible, either directly or indirectly, via "getter" methods. Over time, the
 * values assigned to these instance variables should change so that they always reflect the
 * latest information about the state of the game. Most of these changes are described in the
 * project's <a href="https://github.com/cs1302uga/cs1302-c4-alpha#functional-requirements">
 * functional requirements</a>.
 */
public class ConnectFour {

    //----------------------------------------------------------------------------------------------
    // INSTANCE VARIABLES: You should NOT modify the instance variable declarations below.
    // You should also NOT add any additional instance variables. Static variables should
    // also NOT be added.
    //----------------------------------------------------------------------------------------------

    private int rows;        // number of grid rows
    private int cols;        // number of grid columns
    private Token[][] grid;  // 2D array of tokens in the grid
    private Token[] player;  // 1D array of player tokens (length 2)
    private int numDropped;  // number of tokens dropped so far
    private int lastDropRow; // row index of the most recent drop
    private int lastDropCol; // column index of the most recent drop
    private GamePhase phase; // current game phase

    //----------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //----------------------------------------------------------------------------------------------

    /**
     * Constructs a {@link cs1302.game.ConnectFour} game with a grid that has {@code rows}-many
     * rows and {@code cols}-many columns. All of the game's instance variables are expected to
     * be initialized by this constructor as described in the project's
     * <a href="https://github.com/cs1302uga/cs1302-c4-alpha#functional-requirements">functional
     * requirements</a>.
     *
     * @param rows the number of grid rows
     * @param cols the number of grid columns
     * @throws IllegalArgumentException if the value supplied for {@code rows} or {@code cols} is
     *     not supported. The following values are supported: {@code 6 <= rows <= 9} and
     *     {@code 7 <= cols <= 9}.
     */
    public ConnectFour(int rows, int cols)  {

        //Checking if values of rows and cols are supported
        if ((rows >= 6 && rows <= 9) && (cols >= 7 && cols <= 9)) {

            this.rows = rows;
            this.cols = cols;

        } else {

            throw new IllegalArgumentException("Number of columns or rows is not valid");

        }

        //Setting values for other variables as mentioned
        grid = new Token[rows][cols];
        player = new Token[2];
        numDropped = 0;
        lastDropRow = -1;
        lastDropCol = -1;
        phase = GamePhase.NEW;


    } // ConnectFour

    //----------------------------------------------------------------------------------------------
    // INSTANCE METHODS
    //----------------------------------------------------------------------------------------------

    /**
     * Return the number of rows in the game's grid.
     *
     * @return the number of rows
     */
    public int getRows() {

        return rows;

    } // getRows

    /**
     * Return the number of columns in the game's grid.
     *
     * @return the number of columns
     */
    public int getCols() {

        return cols;

    } // getCols

    /**
     * Return whether {@code row} and {@code col} specify a location inside this game's grid.
     *
     * @param row the position's row index
     * @param col the positions's column index
     * @return {@code true} if {@code row} and {@code col} specify a location inside this game's
     *     grid and {@code false} otherwise
     */
    public boolean isInBounds(int row, int col) {

        //checking if row and column index are in range
        if ((0 <= row && row < rows) && (0 <= col && col < cols)) {

            //returning true if provided index is in range
            return true;

        }

        return false;

    } // isInBounds

    /**
     * Return the grid {@linkplain cs1302.gameutil.Token token} located at the specified position
     * or {@code null} if no token has been dropped into that position.
     *
     * @param row the token's row index
     * @param col the token's column index
     * @return the grid token located in row {@code row} and column {@code col}, if it exists;
     *     otherwise, the value {@code null}
     * @throws IndexOutOfBoundsException if {@code row} and {@code col} specify a position that is
     *     not inside this game's grid.
     */
    public Token getTokenAt(int row, int col) {

        //Calling isInBounds method to verify if provide values are inbounds
        if (isInBounds(row, col) == false) {

            throw new IndexOutOfBoundsException("Either row or column is not in bounds");

        } else if (isInBounds(row, col) == true) {

            //returning wanted grid position if the token exists at the location
            return grid[row][col];

        } else {

            //returning null if the grid position is valid but no token is present
            return null;

        }

    } // getTokenAt

    /**
     * Set the first player token and second player token to {@code token0} and {@code token1},
     * respectively. If the current game phase is {@link cs1302.gameutil.GamePhase#NEW}, then
     * this method changes the game phase to {@link cs1302.gameutil.GamePhase#READY}, but only
     * if no exceptions are thrown.
     *.
     * @param token0 token for first player
     * @param token1 token for second player
     * @throws NullPointerException if {@code token0} or {@code token1} is {@code null}.
     * @throws IllegalArgumentException if {@code token0 == token1}.
     * @throws IllegalStateException if {@link #getPhase getPhase()} returns
     *     {@link cs1302.gameutil.GamePhase#PLAYABLE} or {@link cs1302.gameutil.GamePhase#OVER}.
     */
    public void setPlayerTokens(Token token0, Token token1) {

        //checking if one of the tokens is null and throwing an exception if so
        if (token0 == null || token1 == null) {

            throw new NullPointerException("One of the tokens is null");

        } else if (token0 == token1) {

            //throwing an exception if both tokens are the same
            throw new IllegalArgumentException("Token0 and Token1 cannot be the same");

        } else {

            //Setting player array indexs with each token if no exceptions are thrown
            player[0] = token0;
            player[1] = token1;

        }


        //Checking game phases
        if (getPhase().equals(GamePhase.PLAYABLE) || getPhase().equals(GamePhase.OVER)) {

            throw new IllegalStateException("The phase cannot be PLAYABLE or OVER");

        } else if (getPhase().equals(GamePhase.NEW)) {

            phase = GamePhase.READY;

        }


    } // setPlayerTokens

    /**
     * Return a player's token.
     *
     * @param player the player ({@code 0} for first player and {@code 1} for second player)
     * @return the token for the specified player
     * @throws IllegalArgumentException if {@code player} is neither {@code 0} nor {@code 1}
     * @throws IllegalStateException if {@link #getPhase getPhase()} returns
     *     {@link cs1302.gameutil.GamePhase#NEW}.
     */
    public Token getPlayerToken(int player) {

        //checking if player does not equals one or zero
        if (player != 0 && player != 1) {

            //throwing an exception if player integer is not 0 or 1
            throw new IllegalArgumentException("Player has to be 0 or 1");

        } else if (getPhase().equals(GamePhase.NEW)) {

            throw new IllegalStateException();

        } else {

            return this.player[player];

        }


    } // getPlayerToken

    /**
     * Return the number of tokens that have been dropped into this game's grid so far.
     *
     * @return the number of dropped tokens
     * @throws IllegalStateException if {@link #getPhase getPhase()} returns
     *     {@link cs1302.gameutil.GamePhase#NEW} or {@link cs1302.gameutil.GamePhase#READY}.
     */
    public int getNumDropped() {

        //Throwing an exception if game phase equals either of the two phases
        if (getPhase().equals(GamePhase.NEW) || getPhase().equals(GamePhase.READY)) {

            throw new IllegalStateException("Incorrect game phase");

        }

        return numDropped;

    } // getNumDropped

    /**
     * Return the row index of the last (i.e., the most recent) token dropped into this
     * game's grid.
     *
     * @return the row index of the last drop
     * @throws IllegalStateException if {@link #getPhase getPhase()} returns
     *     {@link cs1302.gameutil.GamePhase#NEW} or {@link cs1302.gameutil.GamePhase#READY}.
     */
    public int getLastDropRow() {

        //Throwing an exception if game phase equals either of the two phases
        if (getPhase().equals(GamePhase.NEW) || getPhase().equals(GamePhase.READY)) {

            throw new IllegalStateException("Incorrect game phase");
        }

        return lastDropRow;
    } // getLastDropRow

    /**
     * Return the col index of the last (i.e., the most recent) token dropped into this
     * game's grid.
     *
     * @return the column index of the last drop
     * @throws IllegalStateException if {@link #getPhase getPhase()} returns
     *     {@link cs1302.gameutil.GamePhase#NEW} or {@link cs1302.gameutil.GamePhase#READY}.
     */
    public int getLastDropCol() {

        //Throwing an exception if game phase equals either of the two phases
        if (getPhase().equals(GamePhase.NEW) || getPhase().equals(GamePhase.READY)) {

            throw new IllegalStateException("Incorrect game phase");

        }

        return lastDropCol;

    } // getLastDropCol

    /**
     * Return the current game phase.
     *
     * @return current game phase
     */
    public GamePhase getPhase() {

        //returning current game phase
        return phase;

    } // getPhase

    /**
     * Drop a player's token into a specific column in the grid. This method should not enforce turn
     * order -- that is the players' responsibility should they desire an polite and honest game.
     *
     * @param player the player ({@code 0} for first player and {@code 1} for second player)
     * @param col the grid column where the token will be dropped
     * @throws IndexOutOfBoundsException if {@code col} is not a valid column index
     * @throws IllegalArgumentException if {@code player} is neither {@code 0} nor {@code 1}
     * @throws IllegalStateException if {@link #getPhase getPhase()} does not return
     *    {@link cs1302.gameutil.GamePhase#READY} or {@link cs1302.gameutil.GamePhase#PLAYABLE}
     * @throws IllegalStateException if the specified column in the grid is full
     */
    public void dropToken(int player, int col) {

        //checking if player is passed is correct
        if (player != 0 && player != 1) {

            //Throwing an exception if the player passed is not 0 or 1
            throw new IllegalArgumentException("The player passed is incorrect");

        } else if (isLastDropConnectFour() == false) {

            //setting game phase to PLAYABLE is the game is not over
            phase = GamePhase.PLAYABLE;

        } else if (!getPhase().equals(GamePhase.READY) || !getPhase().equals(GamePhase.PLAYABLE)) {

            throw new IllegalStateException();

        }

        //A count variable to keep track of how many tokens there are in a given column
        int count = 0;

        //checking if column number passed is inbounds
        if (col < 0 || col > cols) {

            throw new IndexOutOfBoundsException("The column number passed in not in bounds");

        } else {

            for (int row = rows - 1; row >= 0; row--) {

                //checking if a passed column value has an open spot
                if (grid[row][col] == null) {

                    phase = GamePhase.PLAYABLE;
                    grid[row][col] = this.player[player]; //dropping the token in the column
                    lastDropRow = row;
                    lastDropCol = col;
                    numDropped++;
                    break; //breaking out of the loop so the column is filled completely

                } else {

                    //incrementing the count variable if a token is present in the given column
                    count++;

                }

            }

            //checking and throwing an exception if a column is full
            if (count == rows) {

                throw new IllegalStateException("The column is full");

            }

        }

    } // dropToken

    /**
     * Return true if there is a connect four in each direction (up, down, or  diagonally).
     *
     * @param player token for a player.
     *
     * @return {@code true} if there is a connect four and {@code false} otherwise.
     */

    public boolean checkConnectFour(Token player) {

        //Checking for horizontal win
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols - 3; col++) {
                if (grid[row][col] == player &&
                         grid[row][col + 1] == player &&
                         grid[row][col + 2] == player &&
                         grid[row][col + 3] == player) {

                    return true;
                }
            }
        }

        for (int row = 0; row < rows - 3; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] == player &&
                        grid[row + 1][col] == player &&
                        grid[row + 2][col] == player &&
                        grid[row + 3][col] == player) {

                    return true;
                }
            }
        }

        //Checking for a diagonal win from top left to bottom right
        for (int row = 3; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length - 3; col++) {
                if (grid[row][col] == player &&
                        grid[row - 1][col + 1] == player &&
                        grid[row - 2][col + 2] == player &&
                        grid[row - 3][col + 3] == player) {

                    return true;
                }
            }
        }

        //Checking for a diagonal win from bottom left to top right
        for (int row = 0; row < grid.length - 3; row++) {
            for (int col = 0; col < grid[0].length - 3; col++) {
                if (grid[row][col] == player &&
                        grid[row + 1][col + 1] == player &&
                        grid[row + 2][col + 2] == player &&
                        grid[row + 3][col + 3] == player) {

                    return true;
                }
            }
        }

        return false;

    }


    /**
     * Return {@code true} if the last token dropped via {@link #dropToken} created a
     * <em>connect four</em>. A <em>connect four</em> is a sequence of four equal tokens (i.e., they
     * have the same color) -- this sequence can occur horizontally, vertically, or diagonally.
     * If the grid is full or the last drop created a <em>connect four</em>, then this method
     * changes the game's phase to {@link cs1302.gameutil.GamePhase#OVER}.
     *
     * <p>
     * <strong>NOTE:</strong> The only instance variable that this method might change, if
     * applicable, is ``phase``.
     *
     * <p>
     * <strong>NOTE:</strong> If you want to use this method to determin a winner, then you must
     * call it after each call to {@link #dropToken}.
     *
     * @return {@code true} if the last token dropped created a <em>connect four</em>, else
     *     {@code false}
     */
    public boolean isLastDropConnectFour() {

        if (checkConnectFour(player[0]) == true) {

            phase = GamePhase.OVER;
            return true;

        } else if (checkConnectFour(player[1]) == true) {

            phase = GamePhase.OVER;
            return true;

        }


        int count = 0;
        int totalSpots = rows * cols; //calculating number of spots on the grid
        //Checking if all the spots on the grid are full
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] != null) {
                    count++;
                }
            }
        }
        if (count == totalSpots &&
              checkConnectFour(player[0]) == false &&
              checkConnectFour(player[1]) == false) {
            //changing the phase to over since board is full and there is no win
            phase = GamePhase.OVER;
        }
        return false;
    } // isLastDropConnectFour

    //----------------------------------------------------------------------------------------------
    // ADDITIONAL METHODS: If you create any additional methods, then they should be placed in the
    // space provided below.
    //----------------------------------------------------------------------------------------------




    //----------------------------------------------------------------------------------------------
    // DO NOT MODIFY THE METHODS BELOW!
    //----------------------------------------------------------------------------------------------

    /**
     * <strong>DO NOT MODIFY:</strong>
     * Print the game grid to standard output. This method assumes that the constructor
     * is implemented correctly.
     *
     * <p>
     * <strong>NOTE:</strong> This method should not be modified!
     */
    public void printGrid() {
        TokenGrid.println(this.grid);
    } // printGrid

} // ConnectFour
