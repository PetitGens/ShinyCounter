module fr.petitgens.shinycounter {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
                            
    opens fr.petitgens.shinycounter to javafx.fxml;
    exports fr.petitgens.shinycounter;
}