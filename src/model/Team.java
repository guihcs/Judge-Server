package model;

import java.io.Serializable;
import java.util.Objects;

public class Team implements Serializable {

    private String name;
    private boolean rated;

    public Team() {
    }

    public Team(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public boolean isRated() {
        return rated;
    }

    public void setRated(boolean rated) {
        this.rated = rated;
    }


    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", rated=" + rated +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return rated == team.rated &&
                Objects.equals(name, team.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, rated);
    }
}
