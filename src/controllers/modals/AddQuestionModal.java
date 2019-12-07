package controllers.modals;


import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Question;
import utils.Validator;

public class AddQuestionModal extends Modal<Question> {

    private Label nameFieldError = new Label();
    private Label weightFieldError = new Label();

    private TextField nameField = new TextField();
    private TextField weightField = new TextField("1");

    private boolean nameIsValid = false;
    private boolean weightIsValid = false;

    public AddQuestionModal(String title) {
        super(title);
        VBox vbox = new VBox(
                new Label("Question name:"),
                nameField,
                nameFieldError,
                new Label("Question weight:"),
                weightField,
                weightFieldError
        );

        setContent(vbox);

        weightField.textProperty().addListener(c -> {

            if (weightField.getText().isEmpty()) weightField.setText("1");
            else if(!Validator.onlyNumber(weightField.getText())) {
                weightField.setText(weightField.getText().replaceAll("\\D+", ""));
            }
        });
    }

    @Override
    protected Question getData() {
        return new Question(nameField.getText(), Double.parseDouble(weightField.getText()));
    }


    @Override
    protected boolean validate() {

        nameIsValid = !(nameField.getText() == null || nameField.getText().isEmpty());
        weightIsValid =  !(nameField.getText() == null || nameField.getText().isEmpty() || weightField.getText().matches("\\D+"));

        return nameIsValid && weightIsValid;
    }

    @Override
    protected void showError() {
        if(!nameIsValid) nameFieldError.setText("Name can't be empty.");
        if(!weightIsValid) weightFieldError.setText("Weight only can be number.");
    }
}
