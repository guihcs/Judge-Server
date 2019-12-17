package manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Judge;
import model.Question;

import java.io.Serializable;
import java.util.List;

public class QuestionManager implements Serializable {

    private static QuestionManager instance;


    private ObservableList<Question> questionList = FXCollections.observableArrayList();

    private QuestionManager(){

    }

    public static QuestionManager getInstance() {
        if (instance == null) instance = new QuestionManager();
        return instance;
    }



    public void add(Question question){
        questionList.add(question);
    }

    public void remove(Question question){
        questionList.remove(question);
    }

    public Question get(String name){
        for (Question question : questionList) {
            if (question.getTitle().equals(name)) return question;
        }

        return null;
    }

    public void move(int current, int next){
        Question question = questionList.remove(current);
        questionList.add(next, question);
    }

    public ObservableList<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList){
        this.questionList = FXCollections.observableArrayList(questionList);
    }

    public boolean contains(Question question){
        for (Question question1 : questionList) {
            if(question1.getTitle().equals(question.getTitle())) return true;
        }
        return false;
    }
}
