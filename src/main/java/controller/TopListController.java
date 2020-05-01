package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import malom.results.GameResult;
import malom.results.GameResultDao;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controller class of the top list screen.
 */
@Slf4j
public class TopListController {

    @FXML
    private TableView<GameResult> topListTable;

    @FXML
    private TableColumn<GameResult, String> player1;

    @FXML
    private TableColumn<GameResult, Integer> leftPiece1;

    @FXML
    private TableColumn<GameResult, String> player2;

    @FXML
    private TableColumn<GameResult, Integer> leftPiece2;

    @FXML
    private TableColumn<GameResult, String> winner;

    @FXML
    private TableColumn<GameResult, ZonedDateTime> date;

    private GameResultDao gameResultDao;

    /**
     * Loads back the launch screen when the player clicks on the main menu button.
     *
     * @param actionEvent a click by the player
     * @throws IOException if {@code fxmlLoader} can't load fxml file
     */
    public void back(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/launch.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setX((Screen.getPrimary().getBounds().getWidth()/2)-300);
        stage.setY((Screen.getPrimary().getBounds().getHeight()/2)-250);
        stage.show();
        log.info("Loading launch scene.");
    }

    /**
     * Initializes the top list fxml file.
     */
    @FXML
    public void initialize() {
        gameResultDao = GameResultDao.getInstance();

        List<GameResult> topList = gameResultDao.findEarliest(10);

        player1.setCellValueFactory(new PropertyValueFactory<>("player1"));
        leftPiece1.setCellValueFactory(new PropertyValueFactory<>("leftPiece1"));
        player2.setCellValueFactory(new PropertyValueFactory<>("player2"));
        leftPiece2.setCellValueFactory(new PropertyValueFactory<>("leftPiece2"));
        winner.setCellValueFactory(new PropertyValueFactory<>("winner"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        date.setCellFactory(column -> {
            TableCell<GameResult, ZonedDateTime> cell = new TableCell<GameResult, ZonedDateTime>() {
                private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

                @Override
                protected void updateItem(ZonedDateTime item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        setText(item.format(formatter));
                    }
                }
            };

            return cell;
        });

        ObservableList<GameResult> observableResult = FXCollections.observableArrayList();
        observableResult.addAll(topList);

        topListTable.setItems(observableResult);
    }

}
