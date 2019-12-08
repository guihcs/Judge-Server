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
                new Label("Nome do Critério:"),
                nameField,
                nameFieldError,
                new Label("Peso do Critério:"),
                weightField,
                weightFieldError
        );

        setContent(vbox);

        weightField.textProperty().addListener(c -> {

            if(!Validator.onlyNumber(weightField.getText())) {
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
        weightIsValid =  !(weightField.getText() == null || weightField.getText().isEmpty() || !Validator.onlyNumber(weightField.getText()));

        return nameIsValid && weightIsValid;
    }

    @Override
    protected void showError() {
        if(!nameIsValid) nameFieldError.setText("O nome não pode ser vazio.");
        if(!weightIsValid) weightFieldError.setText("O peso deve ser um número");
    }
}
