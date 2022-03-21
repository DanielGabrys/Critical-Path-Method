package algorithm;

import java.util.List;

public class Action
{
    private String name;
    private float duration;
    private List<Action> precedingActions;
    private float earliestStart;
    private float latestStart;
    private float earliestFinish;
    private float latestFinish;
    private float reserve;
    private int startEvent = 0;
    private int endEvent = 0;

    public Action(String name, float duration, List<Action> precedingActions)
    {
        this.name = name;
        this.duration = duration;
        this.precedingActions = precedingActions;
    }

    Action(String name, float duration, List<Action> precedingActions, int startEvent, int endEvent)
    {
        this.name = name;
        this.duration = duration;
        this.precedingActions = precedingActions;
        this.startEvent = startEvent;
        this.endEvent = endEvent;
    }

    public void display()
    {
        System.out.println("Action "+name+"\nDuration: "+duration+"\nES: "+earliestStart+"\nEF: "
                +earliestFinish+"\nLS: "+latestStart+"\nLF: "+latestFinish+"\nTime reserve: "+reserve+"\nStart event: "
                +startEvent+"\nEnd event: "+endEvent+"\n");
    }

    public void displayWithList()
    {
        System.out.print("Action "+name+"\nDuration: "+duration+"\nES: "+earliestStart+"\nEF: "
                +earliestFinish+"\nLS: "+latestStart+"\nLF: "+latestFinish+"\nTime reserve: "+reserve+"\nStart event: "
                +startEvent+"\nEnd event: "+endEvent+"\nPreceding actions: ");
        int i=0;
        while(i<precedingActions.size()-1)
        {
            System.out.print(precedingActions.get(i).getName() + ", ");
            ++i;
        }
        if(!precedingActions.isEmpty()) System.out.println(precedingActions.get(i).getName()+"\n");
        else System.out.println("\n");
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public float getDuration() {
        return duration;
    }
    public void setDuration(float duration) {
        this.duration = duration;
    }
    public List<Action> getPrecedingActions() {
        return precedingActions;
    }
    public void setPrecedingActions(List<Action> precedingActions) {
        this.precedingActions = precedingActions;
    }
    public float getEarliestStart() {
        return earliestStart;
    }
    public void setEarliestStart(float earliestStart) {
        this.earliestStart = earliestStart;
    }
    public float getLatestStart() {
        return latestStart;
    }
    public void setLatestStart(float latestStart) {
        this.latestStart = latestStart;
    }
    public float getEarliestFinish() {
        return earliestFinish;
    }
    public void setEarliestFinish(float earliestFinish) {
        this.earliestFinish = earliestFinish;
    }
    public float getLatestFinish() {
        return latestFinish;
    }
    public void setLatestFinish(float latestFinish) {
        this.latestFinish = latestFinish;
    }
    public float getReserve() {
        return reserve;
    }
    public void setReserve(float reserve) {
        this.reserve = reserve;
    }
    public int getStartEvent() {
        return startEvent;
    }
    public void setStartEvent(int startEvent) {
        this.startEvent = startEvent;
    }
    public int getEndEvent() {
        return endEvent;
    }
    public void setEndEvent(int endEvent) {
        this.endEvent = endEvent;
    }
}
