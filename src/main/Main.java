package main;

import builder.DocumentParser;
import database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import manager.JudgeManager;
import manager.QuestionManager;
import manager.ResultManager;
import manager.TeamManager;
import network.NetworkAdapter;

import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main extends Application {


    public static void main(String[] args) {

        String data = Database.load("judges");

        if (data != null){
            JudgeManager.getInstance().setJudgeList(DocumentParser.parseJudges(data));
        }
        data = Database.load("questions");

        if (data != null){
            QuestionManager.getInstance().setQuestionList(DocumentParser.parseQuestions(data));
        }
        data = Database.load("results");

        if (data != null){
            ResultManager.getInstance().setResultList(DocumentParser.parseResults(data));
        }
        data = Database.load("teams");

        if (data != null){
            TeamManager.getInstance().setTeamList(DocumentParser.parseTeams(data));
        }

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        NetworkAdapter.getInstance();

        Parent root = FXMLLoader.load(getClass().getResource("/resources/layouts/main.fxml"));
        root.getStylesheets().add(getClass().getResource("/resources/styles/main.css").toExternalForm());
        stage.setScene(new Scene(root));

        stage.setTitle("Judge");
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        NetworkAdapter.getInstance().close();

        Database.save("judges", DocumentParser.buildJudges(JudgeManager.getInstance().getJudgeList()));
        Database.save("questions", DocumentParser.buildQuestions(QuestionManager.getInstance().getQuestionList()));
        Database.save("results", DocumentParser.buildTotals(ResultManager.getInstance().getTotals()));
        Database.save("teams", DocumentParser.buildTeams(TeamManager.getInstance().getTeamList()));

    }
}
