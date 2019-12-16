package components.cells;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import manager.QuestionManager;
import model.Question;
import utils.Validator;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class QuestionCell extends ListTile<Question> {

    private Label questionName = new Label();
    private Label questionWeight = new Label();
    private TextField questionOrder = new TextField();
    private Button removeButton = new Button("remover");
    private Question data;
    private int currentIndex;

    private BiConsumer<Integer, Integer> indexChangedCallback;

    public QuestionCell() {
        super("/resources/styles/question.css");
        setTitle(questionName);
        setSubtitle(questionWeight);
        setTrailing(questionOrder);
        setLeading(removeButton);

        questionOrder.getStyleClass().add("order-field");

        questionOrder.setOnAction(e -> {
            if (validateIndex()) {
                indexChangedCallback.accept(currentIndex, Integer.parseInt(questionOrder.getText()) - 1);
            } else {
                questionOrder.setText(String.valueOf(currentIndex));
            }
        });

    }

    @Override
    protected void setData(Question data) {
        this.data = data;
        currentIndex = getIndex();
        questionOrder.setText(String.valueOf(currentIndex + 1));
        questionName.setText(data.getTitle());
        questionWeight.setText(String.valueOf(data.getWeight()));
    }

    private boolean validateIndex(){
        if(Validator.onlyNumber(questionOrder.getText())){
            int index = Integer.parseInt(questionOrder.getText());
            return index >= 1 && index <= QuestionManager.getInstance().getQuestionList().size();
        }
        return false;
    }

    public void onIndexChanged(BiConsumer<Integer, Integer> biConsumer){
        indexChangedCallback = biConsumer;
    }

    public void onRemove(Consumer<Question> consumer){
        removeButton.setOnAction(e -> consumer.accept(data));
    }
}
