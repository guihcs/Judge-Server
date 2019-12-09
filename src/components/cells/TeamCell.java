package components.cells;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.Question;
import model.Team;

import java.util.function.Consumer;


public class TeamCell extends ListTile<Team> {

    private Label teamName = new Label();
    private Button removeButton = new Button("remover");
    private Team data;

    public TeamCell() {
        super();

        setTitle(teamName);
        setLeading(removeButton);
    }


    @Override
    protected void setData(Team team) {
        this.data = team;

        teamName.setText(team.getName());
    }

    public void onRemove(Consumer<Team> consumer){
        removeButton.setOnAction(e -> consumer.accept(data));
    }
}
