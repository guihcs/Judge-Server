package controllers.modals;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Team;

public class AddTeamModal extends Modal<Team> {

    private Label nameFieldError = new Label();

    private TextField teamNameField = new TextField();

    private boolean nameIsValid = false;

    public AddTeamModal(String title) {
        super(title);
        VBox vbox = new VBox(
                new Label("Nome da Equipe:"),
                teamNameField,
                nameFieldError
        );

        teamNameField.setOnAction(e -> submit());

        nameFieldError.getStyleClass().add("error-message");
        setContent(vbox);
    }

    @Override
    protected Team getData() {
        return new Team(teamNameField.getText());
    }

    @Override
    protected boolean validate() {

        nameIsValid = !(teamNameField.getText() == null || teamNameField.getText().isEmpty());

        return nameIsValid;
    }

    @Override
    protected void showError() {
        if(!nameIsValid) nameFieldError.setText("O nome não pode ser vazio.");
    }
}
