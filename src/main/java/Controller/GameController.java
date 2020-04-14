package Controller;

import Malom.MalomState;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;

public class GameController {

    private MalomState state;
    private int fromIndex;
    private int whereIndex;

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

        Dragboard db;
        ImageView view;
        ClipboardContent content = new ClipboardContent();
        String id = ((ImageView) mouseEvent.getSource()).getId();

        for (int i = 0; i < board.getChildren().size(); i++) {

            if (board.getChildren().get(i).getId().equals(id)) {

                db = board.getChildren().get(i).startDragAndDrop(TransferMode.MOVE);
                view = (ImageView) board.getChildren().get(i);

                if (!(view.getOpacity() == 0.0)) {

                    content.putImage(view.getImage());
                    db.setContent(content);

                    fromIndex = i;
                }

                break;
            }

        }

    }

    public void dragOver(DragEvent dragEvent) {

        ImageView view;
        String id = ((ImageView) dragEvent.getSource()).getId();

        for (int i = 0; i < board.getChildren().size(); i++) {

            if (board.getChildren().get(i).getId().equals(id)) {

                if (dragEvent.getGestureSource() != board.getChildren().get(i) && dragEvent.getDragboard().hasImage()) {

                    view = (ImageView) board.getChildren().get(i);

                    if (view.getOpacity() == 0.0) {
                        dragEvent.acceptTransferModes(TransferMode.MOVE);
                    }

                }

                break;
            }

        }

    }

    public void drop(DragEvent dragEvent) {

        Dragboard db;
        ImageView view;
        String id = ((ImageView) dragEvent.getSource()).getId();

        for (int i = 0; i < board.getChildren().size(); i++) {

            if (board.getChildren().get(i).getId().equals(id)) {

                db = dragEvent.getDragboard();

                if (db.hasImage()) {

                    view = (ImageView) board.getChildren().get(i);
                    view.setImage(db.getImage());
                    view.setOpacity(100.0);
                    dragEvent.setDropCompleted(true);

                    whereIndex = i;
                }

                break;
            }

        }

    }

    public void dragDone(DragEvent dragEvent) {

        ImageView view;
        String id = ((ImageView) dragEvent.getSource()).getId();

        for (int i = 0; i < board.getChildren().size(); i++) {

            if (board.getChildren().get(i).getId().equals(id)) {

                if (dragEvent.getTransferMode() == TransferMode.MOVE) {
                    view = (ImageView) board.getChildren().get(i);
                    view.setImage(new Image(getClass().getResource("/Pictures/transparent.png").toExternalForm()));
                    view.setOpacity(0.0);

                    state.swap(fromIndex, whereIndex);
                    System.out.println(state.toString());
                }

                break;
            }

        }

    }

}
