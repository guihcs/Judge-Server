package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import network.NetworkAdapter;

public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        NetworkAdapter.getInstance();

        Parent root = FXMLLoader.load(getClass().getResource("../resources/layouts/main.fxml"));
        root.getStylesheets().add(getClass().getResource("../resources/styles/main.css").toExternalForm());
        stage.setScene(new Scene(root));

        stage.setTitle("Judge");
        stage.show();
    }

    @Override
    public void stop() throws Exception {

        super.stop();

        NetworkAdapter.getInstance().close();
    }
}
