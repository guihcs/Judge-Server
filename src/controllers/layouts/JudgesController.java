package controllers.layouts;

import components.cells.JudgeCell;
import components.cells.QuestionCell;
import controllers.modals.AddJudgeModal;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import manager.JudgeManager;
import manager.QuestionManager;
import model.Judge;

public class JudgesController {

    @FXML
    private ListView<Judge> judgeListView;

    @FXML
    private void initialize(){

        judgeListView.setCellFactory( callback -> {
            JudgeCell judgeCell = new JudgeCell();
            judgeCell.onRemove(data -> {
                JudgeManager.getInstance().remove(data);
            });

            return judgeCell;
        });

        judgeListView.setItems(JudgeManager.getInstance().getJudgeList());
    }


    @FXML
    private void showModal(){
        AddJudgeModal modal = new AddJudgeModal("Adicionar Jurado");

        modal.onData( data -> {
            if (JudgeManager.getInstance().contains(data)) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);

                    alert.setTitle("Jurado Já Existe");
                    alert.setHeaderText("Um Jurado com esse nome já existe.");
                    alert.showAndWait();
                });
                return;
            }

            JudgeManager.getInstance().add(data);
        });

        modal.show();
    }
}
