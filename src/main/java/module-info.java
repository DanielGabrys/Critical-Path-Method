module com.example.cpn {
    requires javafx.controls;
    requires javafx.fxml;
            
                        
    opens com.example.cpn to javafx.fxml;
    exports com.example.cpn;
}