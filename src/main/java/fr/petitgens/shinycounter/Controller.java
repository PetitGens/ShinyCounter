package fr.petitgens.shinycounter;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML private GridPane countersGrid;

    @FXML private ToggleGroup counterMode;

    @FXML private ToggleButton singleMode;

    @FXML private ToggleButton multipleMode;

    private Configuration configuration;

    private static final Background selectedBackground = new Background(new BackgroundFill(Color.valueOf("0xfafa39"), CornerRadii.EMPTY, Insets.EMPTY));
    private static final Background unselectedBackground = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));

    Border defaultBorder = new Border(new BorderStroke(Color.BLACK,
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

    Border rowBorder = new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
            BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY));

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configuration = new Configuration("counters.txt");
        gridInit();

        counterMode.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals(singleMode)){
                for (Counter currentCounter : configuration.getCounters()){
                    currentCounter.unselect();
                }
            }
        });

        Counter test = new Counter("Test Counter");
        addCounter(test);
        test.setIncrementHotKey(KeyCode.ADD);
        test.setDecrementHotKey(KeyCode.SUBTRACT);
        test.setFileName("testCounter.txt");
        try {
            test.load();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //test.select();

        Counter test2 = new Counter("Test Counter 2");
        addCounter(test2);
        test2.setIncrementHotKey(KeyCode.ADD);
        test2.setDecrementHotKey(KeyCode.SUBTRACT);
        singleMode.setSelected(true);
    }

    public void gridInit(){
        countersGrid.setBorder(defaultBorder);

        countersGrid.getChildren().clear();

        countersGrid.getRowConstraints().clear();
        countersGrid.getColumnConstraints().clear();

        ColumnConstraints nameColumn = new ColumnConstraints();
        nameColumn.setPercentWidth(70);

        ColumnConstraints countColumn = new ColumnConstraints();
        countColumn.setPercentWidth(10);

        ColumnConstraints buttonsColumn = new ColumnConstraints();
        buttonsColumn.setPercentWidth(20);

        countersGrid.getColumnConstraints().addAll(nameColumn, countColumn, buttonsColumn);

        //countersGrid.setGridLinesVisible(true);
    }

    public void addCounter(Counter counter){
        int rowIndex = configuration.getCountersNumber();

        configuration.addCounter(counter);
        countersGrid.getRowConstraints().add(new RowConstraints(50));

        Label counterNameField = new Label();

        counterNameField.textProperty().bindBidirectional(counter.nameProperty());
        counterNameField.setPadding(new Insets(5));
        Pane namePane = new Pane(counterNameField);
        namePane.setBorder(rowBorder);
        counterNameField.setAlignment(Pos.CENTER_LEFT);

        Label countLabel = new Label();
        countLabel.textProperty().bind(counter.countProperty().asString());
        countLabel.setPadding(new Insets(5));
        Pane countPane = new Pane(countLabel);
        countPane.setBorder(rowBorder);
        countLabel.setAlignment(Pos.CENTER);

        HBox buttonsHBox = new HBox();
        buttonsHBox.getChildren().add(new Button("+"));
        buttonsHBox.getChildren().add(new Button("-"));
        buttonsHBox.setAlignment(Pos.CENTER);
        buttonsHBox.setSpacing(20);
        buttonsHBox.setBorder(rowBorder);

        countersGrid.add(namePane, 0, rowIndex);
        countersGrid.add(countPane, 1, rowIndex);
        countersGrid.add(buttonsHBox, 2, rowIndex);

        counter.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                namePane.setBackground(selectedBackground);
                countPane.setBackground(selectedBackground);
                buttonsHBox.setBackground(selectedBackground);
            }
            else {
                namePane.setBackground(unselectedBackground);
                countPane.setBackground(unselectedBackground);
                buttonsHBox.setBackground(unselectedBackground);
            }
        });

        namePane.setOnMouseClicked(event -> onClickedRow(rowIndex));
        countPane.setOnMouseClicked(event -> onClickedRow(rowIndex));
        buttonsHBox.setOnMouseClicked(event -> onClickedRow(rowIndex));
    }

    public void onClickedRow(int rowIndex){
        Counter clickedCounter = configuration.getCounter(rowIndex);
        if(counterMode.getSelectedToggle().equals(singleMode)){
            if (clickedCounter.isSelected()){
                return;
            }

            for (Counter counter : configuration.getCounters()){
                counter.unselect();
            }

            clickedCounter.select();
        }
        else{
            if(clickedCounter.isSelected()){
                clickedCounter.unselect();
            }
            else {
                clickedCounter.select();
            }
        }
    }

    public void onKeyPressed(KeyEvent keyEvent){
        KeyCode pressedKey = keyEvent.getCode();

        for (Counter currentCounter : configuration.getCounters()){
            if(currentCounter.isSelected()){
                if(currentCounter.getIncrementHotKey() == pressedKey){
                    currentCounter.increment();
                }
                else if(currentCounter.getDecrementHotKey() == pressedKey){
                    currentCounter.decrement();
                }
            }
        }
    }
}