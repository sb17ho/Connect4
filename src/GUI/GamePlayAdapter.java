package GUI;

import Connect.*;

public class GamePlayAdapter {

    private static GamePlayAdapter single_instance = null;
    Board board;
    private boolean valid_move;
    private final Controller controller;

    //Create board instance
    GamePlayAdapter(Controller controller) {
        board = Board.getInstance();
        this.controller = controller;
    }

    //Check if the column has space ' ', if -1 then wrong move
    public boolean checkIfColumnNotFull(int column) {
        if (column == -1) {
            System.out.println("Wrong move");
            return false;
        } else {
            valid_move = board.valid_move(column);
            return valid_move;
        }
    }

    public boolean getValidMove() {
        return valid_move;
    }

    public Board getBoard() {
        return board;
    }

    public boolean checkIfFull() {
        return board.isFull();
    }

    public char[] findWinner() {
        return board.foundWinner();
    }

    public int updateBoard(char c, int column) {
        return board.updateBoard(c, column);
    }

    public void addDice(int row, int col) {
        controller.dropDie(row, col);
    }

    //Always called when a mouse Event occurs
    public void gamePlay(String gameChoice, int difficultyChoice, int column, int turn) {
        if (checkIfFull()) {
            System.out.println("Board Full\nStart New Game");
            System.exit(0);
        } else {
            if (gameChoice.toLowerCase().equals("playervsplayer"))
                GamePlay.playerVsPlayer(this, column, turn);
            else
                GamePlay.playerVsAI(difficultyChoice, this, column, turn);
        }
    }

    public void printBoard() {
        board.printBoard();
    }

    public int getColumn() {
        return controller.getColumn();
    }

    public void setTurn(int turn) {
        controller.setTurn(turn);
    }

    public void displayWinner(String message) {
        controller.displayMessage(message);
    }

    public static GamePlayAdapter getInstance(Controller controller) {
        if (single_instance == null)
            single_instance = new GamePlayAdapter(controller);

        return single_instance;
    }

}
