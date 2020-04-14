package Controller;

import Malom.MalomState;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    private MalomState state;
    private int fromIndex;
    private int whereIndex;
    private List<Integer> whereToMove = new ArrayList<>();

    @FXML
    private ImageView background;

    @FXML
    private Pane board;

    @FXML
    public void initialize() {

        state = new MalomState();

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

        if (!(view.getOpacity() == 0.0)) {

            Dragboard db = view.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putImage(view.getImage());
            db.setContent(content);
            int index = Integer.parseInt(view.getId());

            if (state.isStage2()) {
                whereToMove = state.whereCanThePieceMove(index);
            }

            fromIndex = index;
        }

    }

    public void dragOver(DragEvent dragEvent) {

        ImageView view = (ImageView) dragEvent.getSource();

        if (dragEvent.getGestureSource() != view && dragEvent.getDragboard().hasImage()) {

            if (state.isStage2() && view.getOpacity() == 0.0) {

                int index = Integer.parseInt(view.getId());

                if (whereToMove.contains(index)) {
                    dragEvent.acceptTransferModes(TransferMode.MOVE);
                }

            } else if (view.getOpacity() == 0.0) {
                dragEvent.acceptTransferModes(TransferMode.MOVE);
            }
        }

    }

    public void drop(DragEvent dragEvent) {

        ImageView view = (ImageView) dragEvent.getSource();
        Dragboard db = dragEvent.getDragboard();

        if (db.hasImage()) {

            view.setImage(db.getImage());
            view.setOpacity(100.0);
            dragEvent.setDropCompleted(true);

            whereIndex = Integer.parseInt(view.getId());
        }

    }

    public void dragDone(DragEvent dragEvent) {

        if (dragEvent.getTransferMode() == TransferMode.MOVE) {

            ImageView view = (ImageView) dragEvent.getSource();
            view.setImage(new Image(getClass().getResource("/Pictures/transparent.png").toExternalForm()));
            view.setOpacity(0.0);

            state.swap(fromIndex, whereIndex);
            System.out.println(state.toString());

            if (!state.isStage2()) {
                state.isStage2Started();
            }

        }

    }

}
