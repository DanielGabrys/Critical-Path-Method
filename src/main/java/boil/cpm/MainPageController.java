package boil.cpm;

import algorithm.Action;
import algorithm.CPMAlgorithm;
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
import java.util.ArrayList;
import java.util.List;
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

        Activity a = new Activity(activity_input_field.getText(),Integer.parseInt(time_input_field.getText()),sequence_input_field.getText());
        list.add(a);
        table_input.setItems(list);

        System.out.println(a.getPrevious_sequence());
        System.out.println(a.getNext_sequence());
    }

    @FXML
    void load_data(ActionEvent event)
    {
        System.setProperty("org.graphstream.ui", "swing");
        Graph graph = new Graph();


        List<Action> testList2 = graph.inputAdapter(this.list);
        CPMAlgorithm.determineCriticalPath(testList2);
        for(Action action : testList2)
        {
            action.displayWithList();
        }

        graph.generateGraph(this.list,testList2);
    }

    ObservableList<Activity> list = FXCollections.observableArrayList(
            new Activity("A",5,"1-2"),
            new Activity("B",7,"1-3"),
            new Activity("C",6,"2-4"),
            new Activity("D",8,"2-5"),
            new Activity("E",3,"3-5"),
            new Activity("F",4,"4-5"),
            new Activity("G",2,"4-6"),
            new Activity("H",5,"5-6")
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
