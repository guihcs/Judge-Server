package controllers.layouts;

import components.cells.ResultCell;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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

        TeamTotal winner = ResultManager.getInstance().getWinner();
        ObservableList<TeamTotal> totalList = FXCollections.observableArrayList(
                ResultManager.getInstance().getTotals()
        );


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
}
