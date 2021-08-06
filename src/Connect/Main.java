package Connect;

import java.util.Scanner;

public class Main {

    Main() {

        System.out.println("Welcome to Connect 4");
        int choice;
        System.out.println("Press:\n1) Player vs Player\n2) Player vs Computer");

        Scanner scanner = new Scanner(System.in);
        choice = scanner.nextInt();

        // If the player chooses option 1 to play as Player vs Player or choose 2 for AI vs Player

        if (choice == 1) {
            System.out.println("Player vs Player selected ");
            Board board = new Board();
            int player1turns = 0;
            int player2turns = 0;
            int turn = 0; //Keep track of the turn
            char c;

            //Until the board is full the game continues
            while (!(board.isFull(board))) {

                if (turn == 0) { //Player 1
                    System.out.println("Player 1: Enter column to insert");
                    System.out.println("Select a column from (1-7): ");
                    int player1column = scanner.nextInt() - 1;
                    if (player1column > 6) {
                        //If player makes a wrong choice suppose 8 then he must re-enter a correct column
                        System.out.println("Player 1 made an invalid move.\nPlease columns from (1-7):");
                        player1column = scanner.nextInt() - 1;
                    }
                    c = 'X';
                    if (board.valid_move(board, player1column)) {
                        //Checks if the move made by the player is valid or not
                        board.updateBoard(board, c, player1column);
                        //Update the boards according to the choice of column made by player
                        board.printBoard(board);
                        player1turns++;

                        //If the player 1 has made 4 moves then it checks if there is any winning case
                        if (player1turns >= 4) {
                            char[] check = board.foundWinner(board); //Checks of the winner on the entire board
                            if (check[0] == 'T') {
                                if (check[1] == 'X') System.out.println("Player 1 Wins");
                                break;
                            }
                        }

                        turn += 1;
                        turn = turn % 2;
                    } else {
                        System.out.println("Column is full, make a valid move");
                    }

                } else if (turn == 1) {  //Player 2
                    System.out.println("Player 2: Enter column to insert");
                    int player2column = scanner.nextInt() - 1;
                    if (player2column > 6) {
                        //If player chooses a wrong column say 8, then it will ask player to re-enter a right column
                        System.out.println("Player 2 made an invalid move.\nPlease columns from (1-7):");
                        player2column = scanner.nextInt() - 1;
                    }

                    c = 'O';
                    if (board.valid_move(board, player2column)) {
                        //checks if the move made player is valid or not
                        board.updateBoard(board, c, player2column);
                        //updates the board according to the column selected by player
                        board.printBoard(board);
                        player2turns++;
                        if (player2turns >= 4) {
                            //If the player has made 4 moves then check for a winning case
                            char[] check = board.foundWinner(board);
                            //checks if there is any win case in the entire board
                            if (check[0] == 'T') {
                                if (check[1] == 'O') System.out.println("Player 2 Wins");
                                break;
                            }
                        }
                        turn += 1;
                        turn = turn % 2;
                    } else {
                        System.out.println("Column is full, make a valid move");
                    }

                }
            }
        } else if (choice == 2) {
            //AI vs Human
            System.out.println("AI vs Human");
            Board board = new Board();
            GamePlay game = new GamePlay(board);
            int player1turns = 0;
            int player2turns = 0;
            int turn = 0;
            char c;

            System.out.println("Choose a depth from 1 - 7 to play against AI\t");
            int depthchoice = scanner.nextInt();

            //Until the board is full the game continues
            while (!(board.isFull(board))) {
                if (turn == 0) { //Human
                    System.out.println("Player 1: Enter column to insert");
                    int player1column = scanner.nextInt() - 1;
                    if (player1column > 6) {
                        //If a player chooses a wrong a column say 8, then it asks to enter a right column
                        System.out.println("Player 1 made an invalid move.\nPlease columns from (1-7):");
                        player1column = scanner.nextInt() - 1;
                    }
                    c = 'X';
                    if (board.valid_move(board, player1column)) {
                        //checks if the move made by the player is valid or not
                        board.updateBoard(board, c, player1column);
                        //updates the board according to the column input by the player
                        player1turns++;

                        if (player1turns >= 4) {
                            //if the player made 4 movies, checks for a win case
                            char[] check = board.foundWinner(board);
                            if (check[0] == 'T') {
                                if (check[1] == 'X') {
                                    board.printBoard(board);
                                    System.out.println("Player 1 Wins");
                                }
                                break;
                            }
                        }

                        turn += 1;
                        turn = turn % 2;
                    } else {
                        System.out.println("Column is full, make a valid move");
                    }

                } else if (turn == 1) { //AI
                    //AI looks for the best possible move as the move made by the human, provided if the plays optimally
                    int player2column = game.minimax(board, depthchoice, true);

                    System.out.println("AI made a move in column: " + (player2column + 1));
                    c = 'O';
                    board.updateBoard(board, c, player2column);
                    //updates the boards according to the best column selected by AI
                    board.printBoard(board);
                    player2turns++;
                    if (player2turns >= 4) {
                        char[] check = board.foundWinner(board);
                        if (check[0] == 'T') {
                            if (check[1] == 'O') System.out.println("Artificial Intelligence Wins");
                            break;
                        }
                    }
                    turn += 1;
                    turn = turn % 2;

                }
            }
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
