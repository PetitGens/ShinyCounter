package fr.petitgens.shinycounter;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

public class Configuration {
    private String saveFile;

    private ArrayList<Counter> counters;

    private boolean singleMode;

    public Configuration(String saveFile){
        this.saveFile = saveFile;
        counters = new ArrayList<Counter>();
        singleMode = true;
        save();
    }

    public void save(){
        //TODO implment configuration saving
    }

    public void addCounter(Counter counter){
        counters.add(counter);
    }

    public Collection<Counter> getCounters(){
        return counters;
    }

    public int getCountersNumber(){
        return counters.size();
    }

    public Counter getCounter(int index){
        return counters.get(index);
    }
}
