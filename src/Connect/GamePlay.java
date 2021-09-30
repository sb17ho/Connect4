package Connect;

import GUI.GamePlayAdapter;

public class GamePlay {
    private static int player1turns = 0;
    private static int player2turns = 0;
    public static boolean gameEnd = false; //TODO Will need to use it where the animation function would be

    public static void playerVsPlayer(GamePlayAdapter gamePlayAdapter, int column,
                                      int turn) {
        char c;
        int row = -1;

        if (turn == -1) { //Player 1
            //Checks if the move made by the player is valid or not
            if (gamePlayAdapter.getValidMove()) {
                c = 'X';
                //Update the boards according to the choice of column made by player
                row = gamePlayAdapter.updateBoard(c, column);
                player1turns++;

                //If the player 1 has made 4 moves then it checks if there is any winning case
                if (player1turns >= 4) {
                    char[] check = gamePlayAdapter.findWinner(); //Checks of the winner on the entire board
                    if (check[0] == 'T') {
                        if (check[1] == 'X') {
                            gamePlayAdapter.displayWinner("Player 1 Wins");
                            gameEnd = true;
                        } //TODO Output this to GUI
                    }
                }
            } else {
                gamePlayAdapter.displayWinner("Column is full, make a valid move");
            }
        } else if (turn == 1) {  //Player 2
            //checks if the move made player is valid or not
            if (gamePlayAdapter.getValidMove()) {
                c = 'O';
                //updates the board according to the column selected by player
                row = gamePlayAdapter.updateBoard(c, column);
                player2turns++;

                if (player2turns >= 4) {
                    //If the player has made 4 moves then check for a winning case
                    char[] check = gamePlayAdapter.findWinner();
                    //checks if there is any win case in the entire board
                    if (check[0] == 'T') {
                        if (check[1] == 'O') {
                            gamePlayAdapter.displayWinner("Player 2 Wins");
                            gameEnd = true;
                        }
                    }
                }
            } else {
                gamePlayAdapter.displayWinner("Column is full, make a valid move");
            }
        }
        gamePlayAdapter.addDice(row, gamePlayAdapter.getColumn());
    }

    public static void playerVsAI(int difficultyChoice, GamePlayAdapter gamePlayAdapter, int column,
                                  int turn) {
        //AI vs Human
        AILogic game = new AILogic(gamePlayAdapter.getBoard());
        char c;
        int row = -1;

        if (turn == -1) { //Human
            if (gamePlayAdapter.getValidMove()) {
                c = 'X';
                //checks if the move made by the player is valid or not
                row = gamePlayAdapter.updateBoard(c, column);

                //updates the board according to the column input by the player
                player1turns++;
                gamePlayAdapter.printBoard();

                if (player1turns >= 4) {
                    //if the player made 4 movies, checks for a win case
                    char[] check = gamePlayAdapter.findWinner();
                    if (check[0] == 'T') {
                        if (check[1] == 'X') {
                            {
                                gamePlayAdapter.displayWinner("Player wins");
                                gameEnd = true;
                            }
                        }
                    }
                }
            } else {
                gamePlayAdapter.displayWinner("Column is full, make a valid move");
            }
            gamePlayAdapter.addDice(row, gamePlayAdapter.getColumn());
            turn *= -1;
            gamePlayAdapter.setTurn(turn);
        }
        if (turn == 1) { //AI
            //AI looks for the best possible move as the move made by the human, provided if the plays optimally
            int aiColumn = game.minimax(gamePlayAdapter.getBoard(), difficultyChoice, true);
            System.out.println("Difficulty: " + difficultyChoice);
            c = 'O';

            row = gamePlayAdapter.getBoard().updateBoard(c, aiColumn);

            //updates the boards according to the best column selected by AI
            gamePlayAdapter.printBoard();
            player2turns++;
            if (player2turns >= 4) {
                char[] check = gamePlayAdapter.findWinner();

                //Check[0] means winner found and check[1] checks which disk it is to determine the winner
                if (check[0] == 'T') {
                    if (check[1] == 'O') {
                        gamePlayAdapter.displayWinner("Artificial Intelligence Wins");
                        gameEnd = true;
                    }
                }
            }
            gamePlayAdapter.addDice(row, aiColumn);
        }
    }
}