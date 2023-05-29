package fr.petitgens.shinycounter;

import javafx.beans.property.*;
import javafx.scene.input.KeyCode;

public class Counter {
    private final SimpleIntegerProperty count;

    private final SimpleIntegerProperty increment;

    private String file;

    private final SimpleStringProperty name;

    private final SimpleBooleanProperty selected;

    private KeyCode incrementHotKey;

    private KeyCode decrementHotKey;

    public Counter(String name){
        count = new SimpleIntegerProperty(0);
        increment = new SimpleIntegerProperty(1);
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

    public int getIncrement(){
        return increment.get();
    }

    public void setIncrement(int increment){
        if (increment < 1){
            throw new IllegalArgumentException("Counter increment has to be greater than 0");
        }
        this.increment.set(increment);
    }

    public IntegerProperty incrementProperty(){
        return increment;
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

    public void add(int value){
        count.set(count.get() + value);

        if(count.get() < 0){
            count.set(0);
        }
    }

    public void increment(){
        add(increment.get());
    }

    public void decrement(){
        add(- increment.get());
    }

    public void setIncrementHotKey(KeyCode hotKey){
        if(decrementHotKey == hotKey){
            decrementHotKey = null;
        }
        incrementHotKey = hotKey;
    }

    public KeyCode getIncrementHotKey(){
        return incrementHotKey;
    }

    public void setDecrementHotKey(KeyCode hotKey){
        if(incrementHotKey == hotKey){
            incrementHotKey = null;
        }
        decrementHotKey = hotKey;
    }

    public KeyCode getDecrementHotKey(){
        return decrementHotKey;
    }
}
