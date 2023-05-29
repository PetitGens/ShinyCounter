package fr.petitgens.shinycounter;

import javafx.beans.property.*;

import java.net.URL;

public class Counter {
    private SimpleIntegerProperty count;

    private URL file;

    private SimpleStringProperty name;

    private SimpleBooleanProperty selected;

    public Counter(String name){
        count = new SimpleIntegerProperty(0);
        this.name = new SimpleStringProperty(name);
        selected = new SimpleBooleanProperty(false);
    }

    public String getName(){
        return name.get();
    }

    public void setName(String name){
        this.name.set(name);
    }

    public StringProperty nameProperty(){
        return name;
    }

    public int getCount(){
        return count.get();
    }

    public SimpleIntegerProperty countProperty(){
        return count;
    }

    public void setCount(int value){
        if (value < 0){
            throw new IllegalArgumentException("Count should not be negative !");
        }

        count.set(value);
    }

    public boolean isSelected(){
        return selected.get();
    }

    public BooleanProperty selectedProperty(){
        return selected;
    }

    public void select(){
        selected.set(true);
    }

    public void unselect(){
        selected.set(false);
    }
}
