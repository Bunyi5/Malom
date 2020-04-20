package Controller;

import Malom.MalomState;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    private MalomState state;
    private String player1;
    private String player2;
    private int fromIndex;
    private boolean mill = false;
    private List<Integer> whereCanMove = new ArrayList<>();

    @FXML
    private ImageView background;

    @FXML
    private Pane board;

    @FXML
    private Label player1Name;

    @FXML
    private Label player2Name;

    public void initializeData(String player1, String player2) {
        this.player1 = player1;
        this.player2 = player2;
        player1Name.setText(this.player1 + " turn");
    }

    @FXML
    public void initialize() {

        state = new MalomState();

        drawGame();

    }

    public void drawGame() {

        background.setImage(new Image(getClass().getResource("/Pictures/board.png").toExternalForm()));

        Image blackImage = new Image(getClass().getResource("/Pictures/black_piece.png").toExternalForm());
        Image whiteImage = new Image(getClass().getResource("/Pictures/white_piece.png").toExternalForm());
        Image transparent = new Image(getClass().getResource("/Pictures/transparent.png").toExternalForm());

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

    public void drag(MouseEvent mouseEvent) {

        ImageView view = (ImageView) mouseEvent.getSource();

        if (!(view.getOpacity() == 0.0) && !mill) {

            int index = Integer.parseInt(view.getId());

            if (state.isThisColorNext(index) && state.isInPieceStoreOrEmptyStore(index)) {

                Dragboard db = view.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putImage(view.getImage());
                db.setContent(content);

                if (state.isPieceStoreEmpty()) {
                    whereCanMove = state.whereCanThePieceMove(index);
                }

                fromIndex = index;
            }

        }

    }

    public void dragOver(DragEvent dragEvent) {

        ImageView view = (ImageView) dragEvent.getSource();

        if (view.getOpacity() == 0.0 && dragEvent.getDragboard().hasImage()) {

            if (state.isPieceStoreEmpty()) {

                int index = Integer.parseInt(view.getId());

                if (whereCanMove.contains(index) || state.canItJump(fromIndex)) {
                    dragEvent.acceptTransferModes(TransferMode.MOVE);
                }

            } else {
                dragEvent.acceptTransferModes(TransferMode.MOVE);
            }

        }

    }

    public void drop(DragEvent dragEvent) {

        Dragboard db = dragEvent.getDragboard();

        if (db.hasImage()) {

            ImageView view = (ImageView) dragEvent.getSource();
            view.setImage(db.getImage());
            view.setOpacity(100.0);
            dragEvent.setDropCompleted(true);

            int index = Integer.parseInt(view.getId());
            state.swapPieceValues(fromIndex, index);

            if (state.isBlackTurn()) {
                state.setBlackTurn(false);
                player1Name.setText("");
                player2Name.setText(player2 + " turn");
            } else {
                state.setBlackTurn(true);
                player2Name.setText("");
                player1Name.setText(player1 + " turn");
            }

            if (state.canItRemovePiece()) {

                if (mill = state.isSomeoneHasMill(index)) {

                    if (!state.isBlackTurn()) {
                        player2Name.setText("");
                        player1Name.setText(player1 + " remove a piece");
                    } else {
                        player1Name.setText("");
                        player2Name.setText(player2 + " remove a piece");
                    }

                }

            }

            if (state.isGameEnded(index)) {
                System.out.println("Ended");
            }

        }

    }

    public void dragDone(DragEvent dragEvent) {

        if (dragEvent.getTransferMode() == TransferMode.MOVE) {

            ImageView view = (ImageView) dragEvent.getSource();
            view.setImage(new Image(getClass().getResource("/Pictures/transparent.png").toExternalForm()));
            view.setOpacity(0.0);

            if (state.isTheNextPlayerCantMove() && !mill) {

                state.setBlackTurn(!state.isBlackTurn());

                if (state.isBlackTurn()) {
                    player2Name.setText("");
                    player1Name.setText(player1 + " turn again");
                } else {
                    player1Name.setText("");
                    player2Name.setText(player2 + " turn again");
                }

            }

        }

    }

    public void pieceClick(MouseEvent mouseEvent) {

        if (mill) {

            ImageView view = (ImageView) mouseEvent.getSource();
            int index = Integer.parseInt(view.getId());

            if (state.isThisColorNext(index) && !state.isSomeoneHasMill(index)) {

                state.removePiece(Integer.parseInt(view.getId()));

                view.setImage(new Image(getClass().getResource("/Pictures/transparent.png").toExternalForm()));
                view.setOpacity(0.0);

                if (state.isTheNextPlayerCantMove()) {
                    state.setBlackTurn(!state.isBlackTurn());
                    if (!state.isBlackTurn()) {
                        player1Name.setText("");
                        player2Name.setText(player2 + " turn again");
                    } else {
                        player2Name.setText("");
                        player1Name.setText(player1 + " turn again");
                    }
                } else {
                    if (state.isBlackTurn()) {
                        player2Name.setText("");
                        player1Name.setText(player1 + " turn");
                    } else {
                        player1Name.setText("");
                        player2Name.setText(player2 + " turn");
                    }
                }

                mill = false;
            }

        }

    }

}
