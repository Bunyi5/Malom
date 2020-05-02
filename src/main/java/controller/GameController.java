package controller;

import javafx.stage.Screen;
import lombok.extern.slf4j.Slf4j;
import malom.results.GameResult;
import malom.results.GameResultDao;
import malom.state.MalomState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class of the game screen.
 */
@Slf4j
public class GameController {

    private MalomState state;
    private String player1Name;
    private String player2Name;
    private String winner;
    private int fromIndex;
    private boolean gameGoes = true;
    private boolean mill = false;
    private Image transparent;
    private List<Integer> whereCanMove = new ArrayList<>();

    private GameResultDao gameResultDao;

    @FXML
    private ImageView background;

    @FXML
    private Pane board;

    @FXML
    private Label player1Label;

    @FXML
    private Label player2Label;

    @FXML
    private Button exitButton;

    /**
     * Saves the players names in {@code player1Name} and {@code player2Name}.
     *
     * @param player1 the name of player 1
     * @param player2 the name of player 2
     */
    public void initializeData(String player1, String player2) {
        this.player1Name = player1;
        this.player2Name = player2;
        player1Label.setText(this.player1Name + "'s turn");
    }

    /**
     * Draws the game board.
     */
    private void drawGame() {

        background.setImage(new Image(getClass().getResource("/pictures/board.png").toExternalForm()));

        Image blackImage = new Image(getClass().getResource("/pictures/black_piece.png").toExternalForm());
        Image whiteImage = new Image(getClass().getResource("/pictures/white_piece.png").toExternalForm());
        transparent = new Image(getClass().getResource("/pictures/transparent.png").toExternalForm());

        ImageView view;

        for (int i = 0; i < board.getChildren().size(); i++) {

            view = (ImageView) board.getChildren().get(i);

            if (i < 24) {
                view.setImage(transparent);
                view.setOpacity(0.0);
            } else if (i < 33) {
                view.setImage(blackImage);
            } else {
                view.setImage(whiteImage);
            }

        }

    }

    /**
     * Initializes the game fxml file.
     */
    @FXML
    public void initialize() {

        gameResultDao = GameResultDao.getInstance();

        state = new MalomState();

        exitButton.setDisable(true);

        drawGame();

    }

    /**
     * Starts a drag and drop gesture if the drag is valid.
     * Allows move transfer mode, puts the image on the drag board.
     * If all pieces were already on the board initialize {@code whereCanMove} list.
     *
     * @param mouseEvent a drag by the player
     */
    public void drag(MouseEvent mouseEvent) {

        ImageView view = (ImageView) mouseEvent.getSource();

        if (!(view.getOpacity() == 0.0) && !mill) {

            fromIndex = Integer.parseInt(view.getId());

            if (state.isThisColorNext(fromIndex) && state.isNotOnBoardOrAllPiecesWereOnBoard(fromIndex) && gameGoes) {

                Dragboard db = view.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putImage(view.getImage());
                db.setContent(content);

                if (state.isAllPiecesWereOnBoard()) {
                    whereCanMove = state.whereCanThePieceMove(fromIndex);
                }

            }

        }

    }

    /**
     * Data is dragged over the target, accept it only if it is valid.
     * Set accept transfer mode to move transfer.
     *
     * @param dragEvent a drag by the player
     */
    public void dragOver(DragEvent dragEvent) {

        ImageView view = (ImageView) dragEvent.getSource();

        if (view.getOpacity() == 0.0 && dragEvent.getDragboard().hasImage()) {

            if (state.isAllPiecesWereOnBoard()) {

                int index = Integer.parseInt(view.getId());

                if (whereCanMove.contains(index) || state.canItJump(fromIndex)) {
                    dragEvent.acceptTransferModes(TransferMode.MOVE);
                }

            } else {
                dragEvent.acceptTransferModes(TransferMode.MOVE);
            }

        }

    }

    /**
     * Data is dropped, if there is an image on the drag board reads it and uses it.
     * Swaps the pieces values on the board, changes the turn, checks if someone has mill,
     * checks that the game is over.
     *
     * @param dragEvent a drag by the player
     */
    public void drop(DragEvent dragEvent) {

        Dragboard db = dragEvent.getDragboard();

        if (db.hasImage()) {

            ImageView view = (ImageView) dragEvent.getSource();
            view.setImage(db.getImage());
            view.setOpacity(100.0);
            dragEvent.setDropCompleted(true);

            int index = Integer.parseInt(view.getId());
            state.swapPieceValues(fromIndex, index);

            changeTurn(index);
            millCheck(index);
            gameEndCheck(index);

        }

    }

    /**
     * The drag and drop gesture ended.
     * If the data was successfully moved clears it.
     *
     * @param dragEvent a drag by the player
     */
    public void dragDone(DragEvent dragEvent) {

        if (dragEvent.getTransferMode() == TransferMode.MOVE) {

            ImageView view = (ImageView) dragEvent.getSource();
            view.setImage(transparent);
            view.setOpacity(0.0);

            playerCanMoveInHisTurnCheck();

        }

    }

    /**
     * If it can remove a piece removes it from the board on click.
     *
     * @param mouseEvent a click by the player
     */
    public void pieceClick(MouseEvent mouseEvent) {

        if (mill && gameGoes) {

            ImageView view = (ImageView) mouseEvent.getSource();
            int index = Integer.parseInt(view.getId());

            if (state.isThisColorNext(index) && !state.isSomeoneHasMill(index)) {

                state.removePiece(index);

                view.setImage(transparent);
                view.setOpacity(0.0);

                afterRemoveCheckCanTheNextPlayerMove(index);

                mill = false;
            }

        }

    }

    private void changeTurn(int index) {

        if (state.isBlackTurn()) {
            state.setBlackTurn(false);
            player1Label.setText("");
            player2Label.setText(player2Name + "'s turn");
            log.info(player1Name + " moved piece from " + fromIndex + " to " + index + ", " + player2Name + " is next.");
        } else {
            state.setBlackTurn(true);
            player2Label.setText("");
            player1Label.setText(player1Name + "'s turn");
            log.info(player2Name + " moved piece from " + fromIndex + " to " + index + ", " + player1Name + " is next.");
        }

    }

    private void millCheck(int index) {

        if (state.canRemovePieceFromTheOtherPlayer(index) && (mill = state.isSomeoneHasMill(index))) {

            if (state.isBlackTurn()) {
                player1Label.setText("");
                player2Label.setText(player2Name + " removes");
                log.info(player2Name + " has a mill and can remove a piece.");
            } else {
                player2Label.setText("");
                player1Label.setText(player1Name + " removes");
                log.info(player1Name + " has a mill and can remove a piece.");
            }

        } else if(state.isSomeoneHasMill(index)) {

            if (state.isBlackTurn()) {
                player2Label.setText(player2Name + " can't remove");
                log.info(player2Name + " has a mill, but can't remove piece, " + player1Name + " is next.");
            } else {
                player1Label.setText(player1Name + " can't remove");
                log.info(player1Name + " has a mill, but can't remove piece, " + player2Name + " is next.");
            }

        }

    }

    private void gameEndCheck(int index) {

        if (state.isGameEnded(index)) {
            gameGoes = false;
            exitButton.setDisable(false);

            if (state.isBlackTurn()) {
                player1Label.setText("");
                player2Label.setText(player2Name + " wins");
                winner = player2Name;
                log.info(player2Name + " wins the game.");
            } else {
                player2Label.setText("");
                player1Label.setText(player1Name + " wins");
                winner = player1Name;
                log.info(player1Name + " wins the game.");
            }
        }

    }

    private void playerCanMoveInHisTurnCheck() {

        if (!state.canThePlayerMoveInHisTurn() && !mill) {

            state.setBlackTurn(!state.isBlackTurn());

            if (state.isBlackTurn()) {
                player2Label.setText("");
                player1Label.setText(player1Name + "'s turn again");
                log.info(player2Name + " can't move, " + player1Name + " goes again.");
            } else {
                player1Label.setText("");
                player2Label.setText(player2Name + "'s turn again");
                log.info(player1Name + " can't move, " + player2Name + " goes again.");
            }

        }

    }

    private void afterRemoveCheckCanTheNextPlayerMove(int index) {

        if (state.canThePlayerMoveInHisTurn()) {
            if (state.isBlackTurn()) {
                player2Label.setText("");
                player1Label.setText(player1Name + "'s turn");
                log.info(player2Name + " removed piece from " + index + ", " + player1Name + " is next.");
            } else {
                player1Label.setText("");
                player2Label.setText(player2Name + "'s turn");
                log.info(player1Name + " removed piece from " + index + ", " + player2Name + " is next.");
            }
        } else {
            state.setBlackTurn(!state.isBlackTurn());
            if (state.isBlackTurn()) {
                player2Label.setText("");
                player1Label.setText(player1Name + "'s turn again");
                log.info(player2Name + " removed piece from " + index + ".");
                log.info(player2Name + " can't move, " + player1Name + " goes again.");
            } else {
                player1Label.setText("");
                player2Label.setText(player2Name + "'s turn again");
                log.info(player1Name + " removed piece from " + index + ".");
                log.info(player1Name + " can't move, " + player2Name + " goes again.");
            }
        }

    }

    private GameResult getResult() {

        log.info("Creating game result.");
        return GameResult.builder()
                .player1(player1Name)
                .leftPiece1(state.blackPieceNum())
                .player2(player2Name)
                .leftPiece2(state.whitePieceNum())
                .winner(winner)
                .build();
    }

    /**
     * Loads the top list when the player clicks on the exit button.
     *
     * @param actionEvent a click by the player
     * @throws IOException if {@code fxmlLoader} can't load fxml file
     */
    public void finishGame(ActionEvent actionEvent) throws IOException {
        gameResultDao.persist(getResult());

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/toplist.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setX((Screen.getPrimary().getBounds().getWidth()/2)-350);
        stage.setY(0);
        stage.show();
        log.info("Finished game, loading Top List scene.");
    }

}
