package boil.cpm;

import javafx.beans.property.StringProperty;

import static java.lang.Integer.parseInt;

public class Activity {
    private String activity;
    private Integer time;
    private String sequence;
    private String previous_sequence;
    private String next_sequence;


    public Activity(String activity, Integer time, String sequence) {
        this.activity = activity;
        this.time = time;
        this.sequence=sequence;

        String segments[] = sequence.split("-");
        this.previous_sequence= segments[0];
        this.next_sequence= segments[segments.length - 1];
    }

    public String getActivity() {
        return activity;
    }

    public String getPrevious_sequence()
    {
        return previous_sequence;
    }

    public String getSequence() {
        return sequence;
    }

    public Integer getTime() {
        return time;
    }

    public String getNext_sequence() {
        return next_sequence;
    }
}
