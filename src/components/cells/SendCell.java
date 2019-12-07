package components.cells;


import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import model.Team;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class SendCell extends ListTile<Team> {

    private Label teamName = new Label();
    private CheckBox sendToTeam = new CheckBox();
    private Team data;

    public SendCell() {
        super();

        setTitle(teamName);
        setLeading(sendToTeam);

        setOnMouseClicked( e -> {
            sendToTeam.fire();
        });
    }

    @Override
    protected void setData(Team data) {
        this.data = data;
        teamName.setText(data.getName());
    }

    public void onSelect(BiConsumer<Team, SendCell> teamConsumer){
        sendToTeam.setOnAction( e -> {
            if (sendToTeam.isSelected()) teamConsumer.accept(data, this);
            else teamConsumer.accept(null, this);
        });
    }


    public void clear(){
        sendToTeam.setSelected(false);
    }


}
