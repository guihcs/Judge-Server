package controllers.layouts;

import components.cells.QuestionCell;
import controllers.modals.AddQuestionModal;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import manager.QuestionManager;
import model.Question;

public class QuestionController {

    @FXML
    private ListView<Question> questionListView;

    @FXML
    private void initialize(){


        questionListView.setCellFactory( callback -> {
            //todo add edit question values
            QuestionCell questionCell = new QuestionCell();
            questionCell.onRemove(data -> QuestionManager.getInstance().remove(data));
            questionCell.onIndexChanged((current, next) -> {
                QuestionManager.getInstance().move(current, next);
            });
            return questionCell;
        });

        questionListView.setItems(QuestionManager.getInstance().getQuestionList());
    }


    @FXML
    private void showModal(){

        AddQuestionModal modal = new AddQuestionModal("Add Question");

        modal.onData( data -> {

            if(QuestionManager.getInstance().contains(data)){
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);

                    alert.setTitle("Question Already Exists");
                    alert.setHeaderText("A question with this name already exists.");
                    alert.showAndWait();
                });

                return;
            }

            QuestionManager.getInstance().add(data);
        });

        modal.show();

    }

}
