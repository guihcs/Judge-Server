package components.cells;

import controllers.components.ListTileController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

import java.io.IOException;

abstract class ListTile<T> extends ListCell<T> {

    private Parent root;
    private ListTileController controller;

    public ListTile(){

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/components/list-tile.fxml"));

        try {
            root = loader.load();
            controller = loader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ListTile(String stylePath){

        this();
        root.getStylesheets().add(getClass().getResource(stylePath).toExternalForm());

    }


    @Override
    protected void updateItem(T item, boolean isEmpty) {
        super.updateItem(item, isEmpty);

        if(isEmpty){
            setGraphic(null);
        } else {
            setData(item);
            setGraphic(root);
        }
    }

    protected abstract void setData(T data);


    protected Node getTrailing() {
        return controller.getTrailing();
    }

    protected void setTrailing(Node trailing) {
        controller.setTrailing(trailing);
    }

    protected Node getLeading() {
        return controller.getLeading();
    }

    protected void setLeading(Node leading) {
        controller.setLeading(leading);
    }

    protected Node getTitle() {
        return controller.getTitle();
    }

    protected void setTitle(Node title) {
        controller.setTitle(title);
    }

    protected Node getSubtitle() {
        return controller.getSubtitle();
    }

    protected void setSubtitle(Node subtitle) {
        controller.setSubtitle(subtitle);
    }
}
