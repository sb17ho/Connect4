package Connect;

/*Board Class
 * This class handles everything related to the board;:
 * Handling valid moves
 * Making a deep copy of the original board
 * Updating original board depending on the column passed by the player or AI*/

//TODO Export depth (row) to Controller

import GUI.GamePlayAdapter;

public class Board {
    Token[][] token;
    private static Board single_instance = null;
    private int row;

    public Board() {
        token = new Token[6][7];
        makeboard(token);
    }

    //Makes a blank board in the start of the game
    void makeboard(Token[][] tokens) {
        for (int i = 0; i < tokens.length; i++) {
            for (int j = 0; j < tokens[0].length; j++) {
                tokens[i][j] = new Token(' ');
            }
        }
    }

    //Checks for the valid move on the column passed by the players or AI
    public boolean valid_move(int col) {
        return token[0][col].disk == ' ';
    }

    //Updates the board according to the columns given by the player or the best column selected by AI
    public int updateBoard(char c, int col) {
        int j;
        j = col;
        int depth = -1;
        for (int i = 0; i < token.length; i++) {
            if (token[i][j].disk == ' ') {
                depth++;
            }
        }
        token[depth][j] = new Token(c);
        return depth;
    }

    //Check for win state on the entire board
    public char[] foundWinner() {
        //int count = 0;
        char[] result = {'F', ' '};
        char[] arr = {',', 'l', 'k', 'f'};

        //check for right
        for (int i = 0; i < token.length; i++) {
            for (int j = 0; j < token[0].length - 3; j++) {
                for (int k = 0; k < 4; k++) {
                    if (token[i][j + k].disk != ' ')
                        arr[k] = token[i][j + k].disk;
                }
                if (arr[0] == arr[1] && arr[1] == arr[2] && arr[2] == arr[3]) {
                    result[0] = 'T';
                    result[1] = arr[0];
                    return result;
                } else {
                    arr = new char[]{'a', 'b', 'c', 'd'};
                }
            }
        }

        //check column
        arr = new char[]{',', 'l', 'k', 'f'};

        for (int i = 0; i < token.length - 3; i++) {
            for (int j = 0; j < token[0].length; j++) {
                for (int k = 0; k < 4; k++) {
                    if (token[i + k][j].disk != ' ')
                        arr[k] = token[i + k][j].disk;
                }
                if (arr[0] == arr[1] && arr[1] == arr[2] && arr[2] == arr[3]) {
                    result[0] = 'T';
                    result[1] = arr[0];
                    return result;
                } else {
                    arr = new char[]{'a', 'b', 'c', 'd'};
                }
            }

        }

        //diagonal-right check
        arr = new char[]{',', 'l', 'k', 'f'};
        for (int i = 0; i < token.length - 3; i++) {
            for (int j = 0; j < token[0].length - 3; j++) {
                for (int k = 0; k < 4; k++) {
                    if (token[i + k][j + k].disk != ' ')
                        arr[k] = token[i + k][j + k].disk;
                }
                if (arr[0] == arr[1] && arr[1] == arr[2] && arr[2] == arr[3]) {
                    result[0] = 'T';
                    result[1] = arr[0];
                    return result;
                } else {
                    arr = new char[]{'a', 'b', 'c', 'd'};
                }
            }
        }

        //diagonal opposite direction
        arr = new char[]{',', 'l', 'k', 'f'};

        for (int i = 0; i < token.length - 3; i++) {
            for (int j = 3; j < token[0].length; j++) {
                for (int k = 0; k < 4; k++) {
                    if (token[i + k][j - k].disk != ' ')
                        arr[k] = token[i + k][j - k].disk;
                }
                if (arr[0] == arr[1] && arr[1] == arr[2] && arr[2] == arr[3]) {
                    result[0] = 'T';
                    result[1] = arr[0];
                    return result;
                } else {
                    arr = new char[]{'a', 'b', 'c', 'd'};
                }
            }
        }
        return result;
    }

    //checks if the board is full or not
    public boolean isFull() {
        boolean result = false;
        int count = 0;
        for (int i = 0; i < token.length; i++) {
            for (int j = 0; j < token[0].length; j++) {
                if (token[i][j].disk != ' ') {
                    count++;
                }
            }
        }
        if (count == (token.length * token[0].length)) result = true;

        return result;
    }

    //prints the board after a move is made by a player or AI
    public void printBoard() {
        for (int i = 0; i < token.length; i++) {
            for (int j = 0; j < token[0].length; j++) {
                System.out.print(token[i][j]);
                System.out.print("  |  ");
            }
            System.out.println();
        }
        System.out.println("----------------");
    }

    //To make a deepcopy of the board for temporary updates and use by AI
    Board duplicate(Board board) {
        Board temp = new Board();
        for (int i = 0; i < token.length; i++) {
            for (int j = 0; j < token[0].length; j++) {
                temp.token[i][j] = board.token[i][j];
            }
        }
        return temp;
    }

    public static Board getInstance() {
        if (single_instance == null)
            single_instance = new Board();

        return single_instance;
    }

}
