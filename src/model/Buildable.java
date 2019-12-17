package model;

public interface Buildable {
    //todo implement buildable in all models
    void parse(String data);
    String toJSON();
}
