package manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Judge;
import model.Question;
import model.Team;

import java.io.Serializable;
import java.util.List;

public class TeamManager implements Serializable {

    private static TeamManager instance;


    private ObservableList<Team> teamList = FXCollections.observableArrayList();

    private TeamManager(){

    }

    public static TeamManager getInstance() {
        if (instance == null) instance = new TeamManager();
        return instance;
    }


    public void add(Team team){
        teamList.add(team);
    }

    public void remove(Team team){
        teamList.remove(team);
    }

    public ObservableList<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Team> teamList){
        this.teamList = FXCollections.observableArrayList(teamList);
    }

    public Team get(String key){
        for (Team team : teamList) {
            if (team.getName().equals(key)) return team;
        }
        return null;
    }

    public void refresh(){
        if (teamList.size() > 0){
            teamList.set(0, teamList.get(0));
        }
    }

    public boolean contains(Team team){
        for (Team team1 : teamList) {
            if(team1.getName().equals(team.getName())) return true;
        }
        return false;
    }
}
