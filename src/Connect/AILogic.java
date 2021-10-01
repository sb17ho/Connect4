package Connect;

/*AILogic a.k.a AI class
 * Handles everything related to AI ie.:
 * Handling board evaluation based on the disk in the board and providing a heuristics score based on the best score evaluated by AI
 * Traverses the entire boards to provide the heuristics function rows and columns to evaluate score based on the disk in the board
 * Checks for the valid columns and provide an array of columns that are empty and suitable to take a move
 * Minimax: Works on making the child nodes of the root board and making evaluation based on the moves that AI can make for
 * the best heuristic score as well as checking the moves that the opponent can make and evaluating accordingly to provide
 * the possible score possible provided that the player plays optimally*/

import java.util.ArrayList;

public class AILogic {

    Board gameBoard;

    public AILogic(Board board) {
        gameBoard = board;
    }

    //Evaluates the board based on the rows and columns provided by the heuristics method and providing the score
    //based on the number of opponent and Player disks
    int evaluate_score(char[] check, char c) {
        int score = 0;
        int diskcount = 0;
        int blankcount = 0;
        int oppcount = 0;
        char opponent = 'X';

        if (c == 'X') {
            opponent = 'O';
        }

        //Player disk
        //TODO Trying to have more negative impact on player favour than positive towards AI win
        for (char value : check) {
            if (value == c) {
                diskcount++;
            } else if (value == ' ') {
                blankcount++;
            } else if (value == opponent) {
                oppcount++;
            }
        }

        if (diskcount == 4) {
            return 1000000; //TODO: Was 10000000
        } else if (oppcount == 4) {
            return -10000000;
        }

        if (diskcount == 3 && blankcount == 1) {
            score += 100; //TODO was 5
        } else if (diskcount == 2 && blankcount == 2) {
            score += 50; //TODO was 2
        }

        if (diskcount == 1 && blankcount == 1 && oppcount == 2) {
            score -= 10000; //TODO Was 3
        } else if (diskcount == 2 && blankcount == 1 && oppcount == 1) {
            score += 5;
        }

        if (oppcount == 3 && blankcount == 1) {
            score -= 100000; //TODO 100
        }
        return score;
    }

    //Traverse the entire board and provide the evaluation function an array of rows, columns and diagonal to return a score based on the number of
    //AI and Player disks for to function to return the score based on the move made by the AI.
    int heuristics_win(Board board, char c) {
        int moveScore = 0;

        //horizontal
        for (int i = 0; i < board.token.length; i++) {
            char[] row = new char[4];
            for (int j = 0; j < board.token[0].length - 3; j++) {
                for (int k = 0; k < 4; k++) {
                    row[k] = board.token[i][j + k].disk;
                }
                moveScore += evaluate_score(row, c);
            }
        }
        //vertical
        for (int i = 0; i < board.token.length - 3; i++) {
            char[] col = new char[4];
            for (int j = 0; j < board.token[0].length; j++) {
                for (int k = 0; k < 4; k++) {
                    col[k] = board.token[i + k][j].disk;
                }
                moveScore += evaluate_score(col, c);
            }
        }
        //diagonal
        for (int i = 0; i < board.token.length - 3; i++) {
            char[] diag = new char[4];
            for (int j = 0; j < board.token[0].length - 3; j++) {
                for (int k = 0; k < 4; k++) {
                    diag[k] = board.token[i + k][j + k].disk;
                }
                moveScore += evaluate_score(diag, c);
            }
        }
        //diagonal opposite direction
        for (int i = 0; i < board.token.length - 3; i++) {
            char[] slopdiag = new char[4];
            for (int j = 3; j < board.token[0].length; j++) {
                for (int k = 0; k < 4; k++) {
                    slopdiag[k] = board.token[i + k][j - k].disk;
                }
                moveScore += evaluate_score(slopdiag, c);
            }
        }
        return moveScore;
    }

    //Provides an array of valid columns where the AI can make moves
    ArrayList<Integer> valid_columns(Board board) {
        ArrayList<Integer> validcol = new ArrayList<>();
        for (int i = 0; i < board.token[0].length; i++) {
            if (board.valid_move(i)) {
                validcol.add(i);
            }
        }
        return validcol;
    }

    /* Minimax: Works on making the child nodes of the root board and making evaluation based on the moves that AI can make for
     *  the best heuristic score as well as checking the moves that the opponent can make and evaluating accordingly to provide
     *  the possible score possible provided that the player plays optimally*/

    public int minimax(Board board, int depth, boolean maxplayer) {
        char[] winner = board.foundWinner();
        boolean playerwin = false;
        boolean AI_win = false;

        if (winner[0] == 'T' && winner[1] == 'X') {
            playerwin = true;
        } else if (winner[0] == 'T' && winner[1] == 'O') {
            AI_win = true;
        }

        ArrayList<Integer> valid_locations = valid_columns(board);

        if (depth == 0 || board.isFull()) {
            if (playerwin) {
                return -1000000000;
            } else if (AI_win) {
                return 1000000000;
            }
            if (board.isFull()) {
                return 0;
            } else {
                return heuristics_win(board, 'O');
            }
        }
        if (maxplayer) {
            int value = Integer.MIN_VALUE;
            char c = 'O';
            //Random rand = new Random();
            int best_col = 0;
            for (Integer x : valid_locations) {
                Board temp = board.duplicate(board);
                temp.updateBoard(c, x);
                int score = minimax(temp, depth - 1, false);
                if (score > value) {
                    value = score;
                    best_col = x;
                }
            }
            return best_col;
        } else {
            int value = Integer.MAX_VALUE;
            int best_col = 0;
            char c = 'X';
            for (Integer x : valid_locations) {
                Board temp = board.duplicate(board);
                temp.updateBoard(c, x);
                int score = minimax(temp, depth - 1, true);
                if (score < value) {
                    value = score;
                    best_col = x;
                }
            }
            return best_col;
        }
    }
}
