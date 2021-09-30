package GUI;

import Connect.GamePlay;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    private ComboBox<String> gamePlayChoice;

    @FXML
    private ComboBox<String> difficultyChoice;

    @FXML
    private Button startGame;

    @FXML
    private Button quitGame;

    @FXML
    private TextField winnerInfo;

    @FXML
    private Pane pane;

    private List<ArrayList<Circle>> circles;
    private double[] mouseInput;
    private GamePlayAdapter gamePlayAdapter;
    private int turn = -1;
    private final Color player1Die = Color.RED;
    private final Color player2Die = Color.YELLOW;
    private final int COLUMNS = 7;
    private final int ROWS = 6;
    private final int RADIUS = 60;
    private String message;

    @FXML
    void initialize() {
        circles = new ArrayList<>();
        for (int i = 0; i < ROWS; i++) {
            circles.add(new ArrayList<>());
        }
        mouseInput = new double[2];
        gamePlayAdapter = GamePlayAdapter.getInstance(this);

        quitGame.setOnMouseClicked(event -> System.exit(0));
        pane.addEventFilter(MouseEvent.ANY, e -> pane.requestFocus());
        pane.setStyle("-fx-background-color: #0077B6");
        createBoard(pane);
        pane.setDisable(true);

        gamePlayChoice.setOnAction((actionEvent -> {
            difficultyChoice.setDisable(!gamePlayChoice.getValue().equalsIgnoreCase("playervscomputer"));
        }));

        startGame.setOnMouseClicked(event -> {
            pane.setVisible(true);
            gamePlayChoice.setDisable(true);
            difficultyChoice.setDisable(true);
            pane.setDisable(false);
            startGame.setDisable(true);
        });
    }

    //Create Game Board
    void createBoard(Pane mainPane) {
        double r = RADIUS + 2, x = 70, y = 68;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                circles.get(i).add(new Circle(x, y, r, Color.WHITE));
                x = x + (r * 2) + 5;
            }
            y = y + (r * 2) + 5;
            x = 70;
        }

        circles.forEach(circle -> mainPane.getChildren().addAll(circle));
    }

    //Returns user mouse click
    public void mouseClick(MouseEvent mouseEvent) {
        mouseInput[0] = mouseEvent.getSceneX() - 210;
        mouseInput[1] = mouseEvent.getSceneY();

        if (!gamePlayAdapter.checkIfColumnNotFull(getColumn())) {
            System.out.println("Wrong Input - Controller");
            System.out.println(mouseInput[0] + " " + mouseInput[1]);
        } else {
            gamePlayAdapter.gamePlay(getGamePlayChoice(), getDifficultyChoice(), getColumn(), turn);
            turn *= -1;
        }
    }

    public void dropDie(int row, int col) {
        Circle circle;

        if (turn == -1) {
            circle = new Circle(circles.get(0).get(col).getCenterX(), circles.get(0).get(col).getCenterY(), RADIUS, player1Die);
        } else {
            circle = new Circle(circles.get(0).get(col).getCenterX(), circles.get(0).get(col).getCenterY(), RADIUS, player2Die);
        }
        addAnimation(circle, row, col);

        if (GamePlay.gameEnd) {
            pane.setDisable(true);
            winnerInfo.setText(message);
        }
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    private void addAnimation(Circle circle, int row, int col) {
        pane.getChildren().add(circle);
        TranslateTransition a = new TranslateTransition(Duration.seconds(0.5), circle);
        a.setToY(circles.get(row).get(col).getCenterY() - RADIUS - 7.5);
        a.play();
    }

    public int getColumn() {
        return (int) mouseInput[0] / 140;
    }

    public String getGamePlayChoice() {
        return gamePlayChoice.getValue();
    }

    //Displays Message on GUI
    public void displayMessage(String message) {
        this.message = message;
        if (!message.toLowerCase().contains("wins")) {
            Alert alert = new Alert(Alert.AlertType.NONE, message, ButtonType.OK);
            alert.show();
        } else if (message.toLowerCase().contains("wins")) {
            Alert alert = new Alert(Alert.AlertType.NONE, message, ButtonType.OK);
            alert.show();
        }
    }

    public int getDifficultyChoice() {
        if (difficultyChoice.getValue().equalsIgnoreCase("easy")) return 1;
        else if (difficultyChoice.getValue().equalsIgnoreCase("normal")) return 3;
        else if (difficultyChoice.getValue().equalsIgnoreCase("medium")) return 5;
        else return 7;
    }

}
