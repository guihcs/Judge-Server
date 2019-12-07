package controllers.components;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class ListTileController {

    @FXML
    private HBox trailing;
    @FXML
    private HBox leading;
    @FXML
    private HBox title;
    @FXML
    private HBox subtitle;

    public HBox getTrailing() {
        return trailing;
    }

    public void setTrailing(Node trailing) {
        if (this.trailing.getChildren().size() >= 1) this.trailing.getChildren().set(0, trailing);
        else this.trailing.getChildren().add(0, trailing);
    }

    public Node getLeading() {
        return this.trailing.getChildren().get(0);
    }

    public void setLeading(Node leading) {
        if (this.leading.getChildren().size() >= 1) this.leading.getChildren().set(0, leading);
        else this.leading.getChildren().add(0, leading);
    }

    public Node getTitle() {
        return title;
    }

    public void setTitle(Node title) {
        if (this.title.getChildren().size() >= 1) this.title.getChildren().set(0, title);
        else this.title.getChildren().add(0, title);
    }

    public Node getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(Node subtitle) {
        if (this.subtitle.getChildren().size() >= 1) this.subtitle.getChildren().set(0, subtitle);
        else this.subtitle.getChildren().add(0, subtitle);
    }
}
