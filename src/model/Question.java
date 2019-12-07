package model;

import java.util.Objects;

public class Question {

    private String title;
    private double weight;
    private String inputType = "number";

    public Question(String title, double weight) {
        this.title = title;
        this.weight = weight;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Double.compare(question.weight, weight) == 0 &&
                title.equals(question.title) &&
                Objects.equals(inputType, question.inputType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, weight, inputType);
    }
}
