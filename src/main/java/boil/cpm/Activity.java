package boil.cpm;

import javafx.beans.property.StringProperty;

public class Activity {
    private String activity;
    private Integer time;
    private String sequence;
    private int previous_sequence;
    private int next_sequence;


    public Activity(String activity, Integer time, String sequence) {
        this.activity = activity;
        this.time = time;
        this.sequence=sequence;

        //this.previous_sequence= sequence.charAt(0);
       // this.next_sequence= sequence.charAt(2);
    }

    public String getActivity() {
        return activity;
    }

    public int getPrevious_sequence() {
        return previous_sequence;
    }

    public String getSequence() {
        return sequence;
    }

    public Integer getTime() {
        return time;
    }

    public int getNext_sequence() {
        return next_sequence;
    }
}
