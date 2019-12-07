package manager;

import builder.DocumentParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Judge;
import network.NetworkOrigin;
import network.NetworkAdapter;

import java.util.HashSet;
import java.util.Set;

public class JudgeManager {

    private static JudgeManager instance;

    private ObservableList<Judge> judgeList = FXCollections.observableArrayList();
    private Set<String> registered = new HashSet<>();

    private JudgeManager(){
        NetworkAdapter networkAdapter = NetworkAdapter.getInstance();

        networkAdapter.onData(message -> {
            if(message.getOrigin() == NetworkOrigin.DATAGRAM) {
                if (registered.contains(message.getData())) return;
                registered.add(message.getData());
                for (Judge judge : judgeList) {
                    if (judge.getCode().equals(message.getData())) {
                        judge.setOnline(true);
                        judgeList.set(0, judgeList.get(0));
                        networkAdapter.send(message.getAddress(), DocumentParser.buildJudge(judge));
                        break;
                    }
                }
            }else if(message.getOrigin() == NetworkOrigin.SOCKET) {
                //todo add judge exit (set offline)
            }
        });

    }

    public static JudgeManager getInstance() {
        if (instance == null) instance = new JudgeManager();
        return instance;
    }


    public void add(Judge judge){
        judgeList.add(judge);
    }

    public void remove(Judge judge){
        judgeList.remove(judge);
    }

    public ObservableList<Judge> getJudgeList() {
        return judgeList;
    }

    public Judge get(String key){
        for (Judge judge : judgeList) {
            if (judge.getCode().equals(key)){
                return judge;
            }
        }
        return null;
    }


    public boolean contains(Judge judge){
        for (Judge judge1 : judgeList) {
            if (judge1.getName().equals(judge.getName())) return true;
        }

        return false;
    }
}
