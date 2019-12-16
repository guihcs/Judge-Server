package model;

import java.util.ArrayList;
import java.util.List;

public class TeamTotal {

    private final List<Result> resultList = new ArrayList<>();
    private Team team;

    public TeamTotal(Team team) {
        this.team = team;
    }

    public void addResult(Result result){
        synchronized (resultList) {
            resultList.add(result);
        }
    }

    public Team getTeam() {
        return team;
    }

    public double getSum(){
        double total = 0;
        for (Result result : resultList) {
            for (QuestionResult questionResult : result.getResults()) {
                total += questionResult.getValue();
            }
        }

        return total;
    }

    public double getWeightedSum(){
        double total = 0;
        for (Result result : resultList) {
            for (QuestionResult questionResult : result.getResults()) {
                total += questionResult.getValue() * questionResult.getWeight();
            }
        }

        return total;
    }

    public List<Result> getResultList() {
        synchronized (resultList) {
            return resultList;
        }
    }

    @Override
    public String toString() {
        return "TeamTotal{" +
                "resultList=" + resultList +
                ", team=" + team +
                '}';
    }
}
