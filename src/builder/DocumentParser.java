package builder;

import manager.JudgeManager;
import manager.QuestionManager;
import manager.TeamManager;
import model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class DocumentParser {

    private DocumentParser(){}



    public static String buildForm(Team team, List<Question> questions){
        StringBuilder builder = new StringBuilder();
        builder.append("{\"type\":\"form-request\",");
        builder.append("\"team\":").append(buildTeam(team)).append(",");
        builder.append("\"form\":[");

        for (Question question : questions) {
            builder.append(buildQuestion(question)).append(",");
        }
        builder.deleteCharAt(builder.length()-1);
        builder.append("]}");
        return builder.toString();
    }

    public static Result buildResult(String doc){

        JSONParser parser = new JSONParser();

        try {
            JSONObject object = (JSONObject) parser.parse(doc);

            String judgeCode = (String) object.get("judge");

            String teamName = (String) object.get("team");
            JSONArray resultsJson = (JSONArray) object.get("results");

            Judge judge = JudgeManager.getInstance().get(judgeCode);
            Team team = TeamManager.getInstance().get(teamName);

            List<QuestionResult> results = new ArrayList<>();

            for (Object result : resultsJson) {
                JSONObject obj = (JSONObject) result;
                String name = (String) obj.get("label");
                String value = (String) obj.get("result");
                Question question = QuestionManager.getInstance().get(name);
                results.add(new QuestionResult(name, Double.parseDouble(value), question.getWeight()));
            }

            return new Result(judge, team, results);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }


    private static String buildTeam(Team team){

        return String.format("{\"name\":\"%s\"}", team.getName());
    }

    private static String buildQuestion(Question question){

        return String.format("[%s,%s]", buildLabel(question.getTitle()), buildInput(question.getInputType()));
    }

    private static String buildLabel(String label){

        return String.format("{\"type\":\"label\",\"args\":{\"text\":\"%s\"}}", label);
    }

    private static String buildInput(String type){
        return String.format("{\"type\":\"input\",\"args\":{\"type\":\"%s\"}}", type);
    }

    public static String buildJudge(Judge judge){

        return String.format("{\"name\":\"%s\",\"code\":\"%s\"}", judge.getName(), judge.getCode());
    }
}
