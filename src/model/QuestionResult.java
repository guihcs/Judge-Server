package model;

import java.util.Objects;

public class QuestionResult {

    private double value, weight;
    private String questionName;

    public QuestionResult(String questionName, double value, double weight) {
        this.value = value;
        this.weight = weight;
        this.questionName = questionName;
    }

    public double getValue() {
        return value;
    }

    public double getWeight() {
        return weight;
    }

    public String getQuestionName() {
        return questionName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionResult that = (QuestionResult) o;
        return Double.compare(that.value, value) == 0 &&
                Double.compare(that.weight, weight) == 0 &&
                questionName.equals(that.questionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, weight, questionName);
    }


    @Override
    public String toString() {
        return "QuestionResult{" +
                "value=" + value +
                ", weight=" + weight +
                ", questionName='" + questionName + '\'' +
                '}';
    }
}
