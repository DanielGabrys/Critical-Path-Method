package algorithm;

import java.util.ArrayList;
import java.util.List;

public class Action
{
    private String name;                    //nazwa
    private float duration;                 //czas trwania
    private List<Action> precedingActions;  //lista zdarzeń poprzedzających
    private float earliestStart;            //ES
    private float latestStart;              //LS
    private float earliestFinish;           //EF
    private float latestFinish;             //LF
    private float reserve;                  //rezerwa czasowa
    private int startEvent = 0;             //zdarzenie rozpoczynające czynność
    private int endEvent = 0;               //zdarzenie kończące czynność
    private List<Integer> endEventHelpList = new ArrayList<>(); //pomocnicza lista do wyznaczania czynności pozornych


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

    //wyświetlanie wartości pól obiektu (bez listy zdarzeń poprzedzających)
    public void display()
    {
        System.out.println("Action "+name+"\nDuration: "+duration+"\nES: "+earliestStart+"\nEF: "
                +earliestFinish+"\nLS: "+latestStart+"\nLF: "+latestFinish+"\nTime reserve: "+reserve+"\nStart event: "
                +startEvent+"\nEnd event: "+endEvent+"\n");
    }

    //wyświetlanie wartości pól obiektu (razem z listą zdarzeń poprzedzających)
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

    //GETTERY I SETTERY
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
    public List<Integer> getEndEventHelpList() {
        return endEventHelpList;
    }
    public void setEndEventHelpList(List<Integer> endEventHelpList) {
        this.endEventHelpList = endEventHelpList;
    }
}
