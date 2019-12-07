package components.cells;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.RadioButton;
import model.Judge;
import model.Question;

import java.util.function.Consumer;

public class JudgeCell extends ListTile<Judge> {

    private Label judgeName = new Label();
    private Label judgeCode = new Label();
    private RadioButton radioButton = new RadioButton();
    private Button removeButton = new Button("remove");
    private Judge data;

    public JudgeCell() {
        super();
        radioButton.setDisable(true);
        setTitle(judgeName);
        setSubtitle(judgeCode);

        setTrailing(radioButton);
        setLeading(removeButton);
    }

    @Override
    protected void setData(Judge data) {
        this.data = data;
        judgeName.setText(data.getName());
        judgeCode.setText(data.getCode());
        radioButton.setSelected(data.isOnline());
    }

    public void onRemove(Consumer<Judge> consumer){
        removeButton.setOnAction(e -> consumer.accept(data));
    }
}
