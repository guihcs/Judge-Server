package test;

import builder.DocumentParser;
import manager.JudgeManager;
import manager.TeamManager;
import model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void testFormParse(){
        Team team = new Team("team1");
        List<Question> questions = new ArrayList<>();

        questions.add(new Question("q1", 1));
        questions.add(new Question("q2", 2));
        questions.add(new Question("q3", 3));

        String result = DocumentParser.buildForm(team, questions);
        String expected = "{\"type\":\"form-request\",\"team\":{\"name\":\"team1\"},\"form\":[[{\"type\":\"label\",\"args\":{\"text\":\"q1\"}},{\"type\":\"input\",\"args\":{\"type\":\"number\"}}],[{\"type\":\"label\",\"args\":{\"text\":\"q2\"}},{\"type\":\"input\",\"args\":{\"type\":\"number\"}}],[{\"type\":\"label\",\"args\":{\"text\":\"q3\"}},{\"type\":\"input\",\"args\":{\"type\":\"number\"}}]]}";
        assertEquals(expected, result);


    }

    @Test
    public void testResultParse(){
        String doc = "{\"results\":[{\"label\":\"qwe\",\"result\":\"5\"},{\"label\":\"q\",\"result\":\"6\"},{\"label\":\"ew\",\"result\":\"7\"}],\"type\":\"form-response\",\"judge\":\"RVec\",\"team\":\"qwe\"}";
        Team team = new Team("qwe");
        Judge judge = new Judge("RVec", "RVec");
        TeamManager.getInstance().add(team);
        JudgeManager.getInstance().add(judge);

        Result result = new Result(judge, team, List.of(
                new QuestionResult("qwe", 5, 1),
                new QuestionResult("q", 6, 1),
                new QuestionResult("ew", 7, 1)
        ));



        Result parsedResult = DocumentParser.buildResult(doc);

        assertEquals(result.getJudge().getCode(), parsedResult.getJudge().getCode());
        assertEquals(result.getTeam().getName(), parsedResult.getTeam().getName());

        for (int i = 0; i < result.getResults().size(); i++) {
            assertEquals(result.getResults().get(i).getQuestionName(), parsedResult.getResults().get(i).getQuestionName());
            assertEquals(result.getResults().get(i).getValue(), parsedResult.getResults().get(i).getValue());
        }

        TeamTotal total = new TeamTotal(parsedResult.getTeam());

        total.addResult(parsedResult);

        assertEquals(18, total.getSum());

    }
}
