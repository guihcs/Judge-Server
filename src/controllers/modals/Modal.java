package controllers.modals;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;

import java.util.function.Consumer;

public class Modal<T> {

    private Stage window;
    private VBox layout = new VBox();
    private Button submitButton;
    private Button cancelButton;
    private Consumer<T> consumer;
    private Runnable onConfirm;
    private Runnable onCancel;

    public Modal(String title){
        window = new Stage();
        window.setTitle(title);
        layout.setPadding(new Insets(12));
        layout.getStylesheets().add(Main.class.getResource("/resources/styles/main.css").toExternalForm());
        layout.setPrefWidth(300);
        submitButton = new Button("OK");
        cancelButton = new Button("Cancelar");

        HBox buttonBar = new HBox(cancelButton, submitButton);
        buttonBar.setPadding(new Insets(8, 0, 0, 0));
        buttonBar.setSpacing(6);
        buttonBar.setAlignment(Pos.CENTER_RIGHT);
        layout.getChildren().add(buttonBar);

        onConfirm(() -> {});
        onCancel(() -> {});

        window.setScene(new Scene(layout));
        window.initModality(Modality.APPLICATION_MODAL);


    }

    public void onConfirm(Runnable runnable){
        onConfirm = runnable;
        submitButton.setOnAction(e -> submit());
    }


    protected void submit(){
        if(validate()) {
            window.close();
            if(onConfirm != null) onConfirm.run();
            if (consumer != null) consumer.accept(getData());
        }else {
            showError();
        }
    }

    public void onCancel(Runnable runnable){
        this.onCancel = runnable;

        cancelButton.setOnAction(e -> {
            window.close();
            if(onCancel != null) runnable.run();
        });
    }

    public void setContent(Node content){
        if(layout.getChildren().size() > 1) layout.getChildren().set(0, content);
        else layout.getChildren().add(0, content);
    }

    public void show(){
        window.showAndWait();
    }

    public void onData(Consumer<T> consumer){
        this.consumer = consumer;
    }


    protected T getData(){
        return null;
    }


    protected boolean validate(){
        return true;
    }

    protected void showError(){

    }
}
