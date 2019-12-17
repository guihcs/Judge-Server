package builder;

import manager.JudgeManager;
import manager.QuestionManager;
import manager.TeamManager;
import model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.CodeGenerator;

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
            builder.append(buildInput(question)).append(",");
        }
        builder.deleteCharAt(builder.length()-1);
        builder.append("]}");
        return builder.toString();
    }

    public static Result parseResult(String doc){

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

    public static String buildJudges(List<Judge> judgeList){
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (Judge judge : judgeList) {
            builder.append(buildJudge(judge)).append(",");
        }
        if(judgeList.size() > 0) builder.deleteCharAt(builder.length()-1);
        builder.append("]");
        return builder.toString();

    }


    public static String buildQuestions(List<Question> questionList){
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (Question question : questionList) {
            builder.append(buildQuestion(question)).append(",");
        }
        if(questionList.size() > 0) builder.deleteCharAt(builder.length()-1);
        builder.append("]");
        return builder.toString();
    }

    public static String buildTeams(List<Team> teamList){
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (Team team : teamList) {
            builder.append(buildTeam(team)).append(",");
        }
        if(teamList.size() > 0) builder.deleteCharAt(builder.length()-1);
        builder.append("]");
        return builder.toString();
    }

    public static String buildTotals(List<TeamTotal> totalList){

        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (TeamTotal total : totalList) {
            builder.append(buildTotal(total)).append(",");
        }
        if(totalList.size() > 0) builder.deleteCharAt(builder.length()-1);
        builder.append("]");
        return builder.toString();
    }

    public static List<Team> parseTeams(String data){

        JSONParser parser = new JSONParser();

        try {
            JSONArray parse = (JSONArray) parser.parse(data);
            List<Team> teamList = new ArrayList<>();
            for (Object object : parse) {
                JSONObject obj = (JSONObject) object;

                teamList.add(parseTeam(obj.toJSONString()));
            }

            return teamList;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Team parseTeam(String data){

        JSONParser parser = new JSONParser();
        try {
            JSONObject parse = (JSONObject) parser.parse(data);

            String name = (String) parse.get("name");

            return new Team(name);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<TeamTotal> parseResults(String data){

        JSONParser parser = new JSONParser();
        try {
            JSONArray parse = (JSONArray) parser.parse(data);
            List<TeamTotal> totalList = new ArrayList<>();
            for (Object object : parse) {
                JSONObject obj = (JSONObject) object;

                totalList.add(parseTotal(obj.toJSONString()));
            }

            return totalList;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static TeamTotal parseTotal(String data){

        JSONParser parser = new JSONParser();
        try {
            JSONObject parse = (JSONObject) parser.parse(data);

            Team team = parseTeam((String) parse.get("team"));

            JSONArray results = (JSONArray) parse.get("results");
            TeamTotal total = new TeamTotal(team);

            for (Object result : results) {
                JSONObject resultObject = (JSONObject) result;

                total.addResult(parseResult(resultObject.toJSONString()));
            }


            return total;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Judge> parseJudges(String data){
        JSONParser parser = new JSONParser();
        try {
            JSONArray parse = (JSONArray) parser.parse(data);
            List<Judge> judgeList = new ArrayList<>();
            for (Object object : parse) {
                JSONObject obj = (JSONObject) object;

                judgeList.add(parseJudge(obj.toJSONString()));
            }

            return judgeList;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Judge parseJudge(String judge){
        JSONParser parser = new JSONParser();

        try {
            JSONObject parse = (JSONObject) parser.parse(judge);

            String name = (String) parse.get("name");

            return new Judge(name, CodeGenerator.generate(4));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return null;

    }

    public static List<Question> parseQuestions(String data){

        JSONParser parser = new JSONParser();
        try {
            JSONArray parse = (JSONArray) parser.parse(data);
            List<Question> questionList = new ArrayList<>();
            for (Object object : parse) {
                JSONObject obj = (JSONObject) object;

                questionList.add(parseQuestion(obj.toJSONString()));
            }

            return questionList;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Question parseQuestion (String data){

        JSONParser parser = new JSONParser();

        try {
            JSONObject object = (JSONObject) parser.parse(data);
            String name = (String) object.get("title");
            double weight = Double.parseDouble((String) object.get("weight"));

            return new Question(name, weight);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return null;
    }

    private static String buildQuestion(Question question){
        return String.format("{\"title\":\"%s\",\"weight\":\"%s\"}", question.getTitle(), question.getWeight());
    }


    private static String buildTeam(Team team){

        return String.format("{\"name\":\"%s\"}", team.getName());
    }

    private static String buildInput(Question question){

        return String.format("[%s]", buildInput(question.getInputType(), question.getTitle()));
    }

    private static String buildLabel(String label){

        return String.format("{\"type\":\"label\",\"args\":{\"text\":\"%s\"}}", label);
    }

    private static String buildInput(String type, String label){
        return String.format("{\"type\":\"input\",\"args\":{\"type\":\"%s\",\"label\":\"%s\"}}", type, label);
    }

    public static String buildJudge(Judge judge){

        return String.format("{\"name\":\"%s\",\"code\":\"%s\"}", judge.getName(), judge.getCode());
    }

    public static String buildTotal(TeamTotal total){
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (Result result : total.getResultList()) {
            builder.append(buildResult(result)).append(",");
        }

        builder.deleteCharAt(builder.length()-1);
        builder.append("]");

        return String.format("{\"team\":%s,\"results\":%s}",
                buildTeam(total.getTeam()),
                builder.toString()
        );
    }

    public static String buildResult(Result result){
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (QuestionResult questionResult : result.getResults()) {
            builder.append(buildQuestionResult(questionResult)).append(",");
        }

        builder.deleteCharAt(builder.length()-1);
        builder.append("]");

        return String.format("{\"judge\":%s,\"team\":%s,\"results\":%s}",
                buildJudge(result.getJudge()),
                buildTeam(result.getTeam()),
                builder.toString()
        );
    }

    public static String buildQuestionResult(QuestionResult questionResult){

        return String.format("{\"name\":\"%s\",\"weight\":\"%s\",\"value\":%s}", questionResult.getQuestionName(), questionResult.getWeight(), questionResult.getValue());
    }
}
