package boil.cpm;

import org.graphstream.graph.*;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import org.graphstream.graph.implementations.SingleGraph;
import org.kordamp.bootstrapfx.scene.layout.Panel;

import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {

    @FXML
    private Label title_label;

    @FXML
    private TableView<Activity> table_input;

    @FXML
    private TableColumn<Activity, String> activity;

    @FXML
    private TableColumn<Activity, Integer> time;

    @FXML
    private TableColumn<Activity, String> sequence;

    @FXML
    private Button add_activity;

    @FXML
    private Button load_data;

    @FXML
    private HBox input_panel;

    @FXML
    private TextField activity_input_field;

    @FXML
    private TextField time_input_field;

    @FXML
    private TextField sequence_input_field;

    @FXML
    void add_activity(ActionEvent event)
    {


        System.out.println(activity_input_field.getText());
        System.out.println(time_input_field.getText());
        System.out.println(sequence_input_field.getText());

        list.add(new Activity(activity_input_field.getText(),Integer.parseInt(time_input_field.getText()),sequence_input_field.getText()));
        table_input.setItems(list);
    }

    @FXML
    void load_data(ActionEvent event)
    {
        System.setProperty("org.graphstream.ui", "swing");
        Graph graph = new Graph();
    }



    ObservableList<Activity> list = FXCollections.observableArrayList(
            new Activity("A",3,"1-2"),
            new Activity("B",2,"2-3")
    );

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        activity.setCellValueFactory(new PropertyValueFactory<Activity,String>("activity"));
        time.setCellValueFactory(new PropertyValueFactory<Activity, Integer>("time"));
        sequence.setCellValueFactory(new PropertyValueFactory<Activity, String>("sequence"));

        table_input.setItems(list);
    }
}
