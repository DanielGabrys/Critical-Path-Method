package boil.cpm;

import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;

public class Activity {
    private String activity;
    private String time;
    private String sequence;
    public List<String> sequences = new ArrayList<>();

    public Activity(String activity, String time, String sequence)
    {
        this.activity = activity;
        this.time = time;
        this.sequence=sequence;
        this.sequences=sequences();

    }

    public List<String> sequences()
    {
        String[] segments = sequence.split(",");
        return new ArrayList<>(Arrays.asList(segments));
    }

    public void printAll()
    {
        System.out.println(activity+" "+time+" "+sequence);
    }

    public String getActivity() {
        return activity;
    }


    public String getSequence() {
        return sequence;
    }

    public String getTime() {
        return time;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setSequence(String sequence)
    {
        this.sequence = sequence;
        this.sequences=sequences();
    }
}
