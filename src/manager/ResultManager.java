package manager;

import builder.DocumentParser;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;
import network.NetworkOrigin;
import network.NetworkAdapter;

import java.util.Comparator;

public class ResultManager {

    private static ResultManager instance;
    private ObservableList<TeamTotal> totals = FXCollections.observableArrayList();

    private ResultManager() {
        NetworkAdapter.getInstance().onData(message -> {
            if(message.getOrigin() == NetworkOrigin.SOCKET){
                Result result = DocumentParser.buildResult(message.getData());
                handleResult(result);
            }
        });

    }

    private void handleResult(Result result){
        TeamTotal total = getTotal(result.getTeam());

        if (total == null){
            total = new TeamTotal(result.getTeam());
            total.addResult(result);
            totals.add(total);
        }else {
            total.addResult(result);
        }

        if(total.getResultList().size() >= JudgeManager.getInstance().getJudgeList().size()) {
            result.getTeam().setRated(true);

            Platform.runLater(() -> TeamManager.getInstance().refresh());
        }

    }

    private TeamTotal getTotal(Team team){
        for (TeamTotal total : totals) {
            if (total.getTeam().equals(team)) return total;
        }

        return null;
    }

    public static ResultManager getInstance() {
        if(instance == null) instance = new ResultManager();
        return instance;
    }


    public ObservableList<TeamTotal> getTotals() {
        return totals;
    }

}
