package controllers.layouts;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainController {

    @FXML
    private ToggleGroup menu;
    @FXML
    private HBox rootContainer;

    private ToggleButton lastToggle;
    private Map<String, Parent> layoutMap = new HashMap<>();

    @FXML
    private void initialize(){

        loadMap();

        setScreen();

        menu.selectedToggleProperty().addListener((c) -> setScreen());
    }

    private void setScreen(){
        if (lastToggle != null) lastToggle.setDisable(false);

        ToggleButton selectedToggle = (ToggleButton) menu.getSelectedToggle();

        lastToggle = selectedToggle;

        String selected = (String) selectedToggle.getUserData();

        selectedToggle.setDisable(true);

        rootContainer.getChildren().clear();
        Parent root = layoutMap.get(selected);
        HBox.setHgrow(root , Priority.ALWAYS);
        rootContainer.getChildren().add(root);
    }


    private void loadMap(){
        try {

            layoutMap.put("send", FXMLLoader.load(getClass().getResource("../../resources/layouts/send.fxml")));
            layoutMap.put("question", FXMLLoader.load(getClass().getResource("../../resources/layouts/question.fxml")));
            layoutMap.put("teams", FXMLLoader.load(getClass().getResource("../../resources/layouts/teams.fxml")));
            layoutMap.put("judges", FXMLLoader.load(getClass().getResource("../../resources/layouts/judges.fxml")));
            layoutMap.put("result", FXMLLoader.load(getClass().getResource("../../resources/layouts/result.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
