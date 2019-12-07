package model;

import java.util.List;
import java.util.Objects;

public class Result {

    private Judge judge;
    private Team team;
    private List<QuestionResult> results;

    public Result(Judge judge, Team team, List<QuestionResult> results) {
        this.judge = judge;
        this.team = team;
        this.results = results;
    }


    public Judge getJudge() {
        return judge;
    }

    public Team getTeam() {
        return team;
    }

    public List<QuestionResult> getResults() {
        return results;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return Objects.equals(judge, result.judge) &&
                Objects.equals(team, result.team) &&
                Objects.equals(results, result.results);
    }

    @Override
    public int hashCode() {
        return Objects.hash(judge, team, results);
    }

    @Override
    public String toString() {
        return "Result{" +
                "judge=" + judge +
                ", team=" + team +
                ", results=" + results +
                '}';
    }
}
