package controllers.layouts;

import components.cells.ResultCell;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;
import manager.ResultManager;
import model.TeamTotal;

import java.util.Comparator;

public class ResultController {

    @FXML
    private Label titleLabel;
    @FXML
    private Label subtitleLabel;
    @FXML
    private ListView<TeamTotal> resultList;


    @FXML
    private void initialize(){
        refreshList();
        //todo add unsolvable draw notification
        resultList.setCellFactory(c -> new ResultCell());

        ResultManager.getInstance().getTotals().addListener((InvalidationListener) c -> refreshList());

    }


    private void refreshList(){
        ObservableList<TeamTotal> totalList = FXCollections.observableArrayList(
                ResultManager.getInstance().getTotals()
        );

        totalList.sort(Comparator.comparingDouble(TeamTotal::getSum).reversed());

        TeamTotal winner = totalList.size() > 0 ? totalList.get(0) : null;
        if(winner != null) {
            titleLabel.setText("Equipe vencedora: " +  winner.getTeam().getName());
            subtitleLabel.setText("Pontuação: " + winner.getSum());
            totalList.remove(0);
        }else {
            titleLabel.setText("");
            subtitleLabel.setText("");
        }

        //todo add accordion to show more info about result (show judge points, etc)

        resultList.setItems(totalList);
    }

    @FXML
    private void solveDraw(){
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);

        HBox root = new HBox(20);
        root.setMinWidth(200);
        root.setMinHeight(100);
        root.setAlignment(Pos.CENTER);
        root.getStylesheets().add(Main.class.getResource("/resources/styles/main.css").toExternalForm());

        stage.setTitle("Desempate");

        ObservableList<TeamTotal> items = resultList.getItems();

        if (items.size() > 0){
            root.getChildren().add(new Label(items.get(0).toString()));
        }
        if (items.size() > 1){
            root.getChildren().add(new Label(items.get(1).toString()));
        }



        stage.setScene(new Scene(root));
        stage.showAndWait();
        stage.centerOnScreen();
    }
}
