package components.cells;

import javafx.scene.control.Label;
import model.TeamTotal;

public class ResultCell extends ListTile<TeamTotal> {

    private Label teamName = new Label();
    private Label teamTotal = new Label();;

    public ResultCell() {
        super();

        setTitle(teamName);
        setSubtitle(teamTotal);
    }

    @Override
    protected void setData(TeamTotal data) {
        teamName.setText(data.getTeam().getName());
        teamTotal.setText(String.valueOf(data.getSum()));
    }
}
