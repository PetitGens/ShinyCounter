package fr.petitgens.shinycounter;

import javafx.beans.property.*;
import javafx.scene.input.KeyCode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Counter {
    private final SimpleIntegerProperty count;

    private final SimpleIntegerProperty increment;

    private String filename;

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

        if(filename != null){
            try {
                save();
            }
            catch (IOException e){
                //TODO Add error Dialog here
            }
        }
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

    public void setFileName(String filename){
        this.filename = filename;
    }

    public String getFileName(){
        return filename;
    }

    public void add(int value){
        try{
            setCount(count.get() + value);
        }
        catch (IllegalArgumentException e){
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

    public void load() throws IOException {
        Path filePath = Path.of(filename);
        List<String> lines = Files.readAllLines(filePath);
        if(lines.size() != 1){
            throw new IllegalArgumentException("File contains more than one line");
        }

        try{
            count.set(Integer.parseInt(lines.get(0)));
        }
        catch(NumberFormatException e){
            throw new IllegalArgumentException("Number parsing failure");
        }
    }

    public void save() throws IOException {
        Path filePath = Path.of(filename);
        Files.writeString(filePath, count.getValue().toString());
    }
}
