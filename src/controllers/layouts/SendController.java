package controllers.layouts;

import builder.DocumentParser;
import components.cells.SendCell;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import manager.QuestionManager;
import manager.TeamManager;
import model.Team;
import network.NetworkAdapter;

public class SendController {

    @FXML
    private Button sendButton;

    @FXML
    private ListView<Team> teamListView;

    private Team selectedTeam;
    private SendCell lastCell;

    @FXML
    private void initialize(){
        refreshList();

        teamListView.setCellFactory( c -> {
            SendCell sendCell = new SendCell();
            sendCell.onSelect( (team, cell) -> {
                if(lastCell != null) lastCell.clear();
                lastCell = cell;

                if(team != null) selectedTeam = team;
            });
            return sendCell;
        });

        TeamManager.getInstance().getTeamList().addListener((InvalidationListener) c -> refreshList());

    }

    @FXML
    private void send(){
        if(selectedTeam == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("No Team Selected");
            alert.setHeaderText("Select a team to continue.");

            alert.showAndWait();
            return;
        }

        sendButton.setDisable(true);
        String document = DocumentParser.buildForm(selectedTeam, QuestionManager.getInstance().getQuestionList());
        NetworkAdapter.getInstance().sendToAll(document);
        sendButton.setDisable(false);
    }


    private void refreshList(){

        ObservableList<Team> teamList = FXCollections.observableArrayList(TeamManager.getInstance().getTeamList().filtered(team -> !team.isRated()));

        teamListView.setItems(teamList);
    }

}
