package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CPMAlgorithm
{
    //private static int eventNumber = 0;
    public static int eventNumber = 0;

    //private static void determineStartAndEndEvents(List<Action> actions)
    public static void determineStartAndEndEvents(List<Action> actions)
    {
        List<Action> processedActions = new ArrayList<>();
        for(Action action : actions)
        {
            if(action.getPrecedingActions().isEmpty()) action.setStartEvent(1);
        }

        boolean isEnd = true;
        for(Action action : actions)
        {
            for(Action action1 : actions)
            {
                for(Action processed : processedActions)
                {
                    if (action1.getPrecedingActions().contains(action)
                            && action1.getPrecedingActions().contains(processed))
                    {
                        action.setEndEvent(processed.getEndEvent());
                        break;
                    }
                }
            }
            if(action.getStartEvent()==0)
            {
                for(Action preceder : action.getPrecedingActions())
                {
                    if(preceder.getEndEvent() != 0)
                    {
                        action.setStartEvent(preceder.getEndEvent());
                        break;
                    }
                }
                if(action.getStartEvent()==0)
                {
                    action.setStartEvent(eventNumber);
                    eventNumber++;
                }
            }
            if(action.getEndEvent()==0)
            {
                for(Action action1 : actions)
                {
                    if(action1.getPrecedingActions().contains(action))
                    {
                        isEnd = false;
                        break;
                    }
                }
                if(!isEnd)
                {
                    action.setEndEvent(eventNumber);
                    eventNumber++;
                }
                isEnd = true;
            }
            processedActions.add(action);
        }
    }

    //DO ZWERYFIKOWANIA
    //private static void addApparentActions(List<Action> actions)
    public static void addApparentActions(List<Action> actions)
    {
        int apparentEventNumber = 1;
        for(int i=0; i<actions.size(); i++)
        {
            for(int j=i+1; j<actions.size(); j++)
            {
                if(actions.get(i) != actions.get(j)
                        && actions.get(i).getStartEvent() == actions.get(j).getStartEvent()
                        && actions.get(i).getEndEvent() == actions.get(j).getEndEvent())
                {
                    int J = j;
                    actions.add(new Action("apparent"+apparentEventNumber, 0.F,
                            new ArrayList<Action>(){{add(actions.get(J));}}, eventNumber,
                            actions.get(j).getEndEvent()));
                    actions.get(j).setEndEvent(eventNumber);
//                    int I = i;
//                    actions.add(new Action("apparent"+apparentEventNumber, 0.F,
//                            new ArrayList<Action>(){{add(actions.get(I));}}, eventNumber,
//                            actions.get(i).getEndEvent()));
//                    actions.get(i).setEndEvent(eventNumber);

                    eventNumber++;
                    apparentEventNumber++;
                }
            }
        }
    }

    //private static void setEndingEvents(List<Action> actions)
    public static void setEndingEvents(List<Action> actions)
    {
        for(Action action : actions)
        {
            if(action.getEndEvent()==0)
            {
                action.setEndEvent(eventNumber);
            }
        }
    }

    //private static void numberEvents(List<Action> actions)
    public static void numberEvents(List<Action> actions)
    {
        int newStart = 0;
        int newEnd = 0;
        for(Action action : actions)
        {
            if(action.getStartEvent() > action.getEndEvent())
            {
                newEnd = action.getStartEvent();
                newStart = action.getEndEvent();
                action.setStartEvent(newStart);
                action.setEndEvent(newEnd);
                for(Action action1 : actions)
                {
                    if(action != action1) {
                        if (action1.getStartEvent() == newStart) {
                            action1.setStartEvent(newEnd);
                        } else if (action1.getEndEvent() == newEnd) {
                            action1.setEndEvent(newStart);
                        } else if (action1.getEndEvent() == newStart) {
                            action1.setEndEvent(newEnd);
                        } else if (action1.getStartEvent() == newEnd) {
                            action1.setStartEvent(newStart);
                        }
                    }
                }
            }
        }
    }

    //private static void stepForward(List<Action> actions)
    public static void stepForward(List<Action> actions)
    {
        float maxEF = 0.F;
        for(Action action : actions)
        {
            //if(action.getPrecedingActions().isEmpty())
            if(action.getStartEvent()==1)
            {
                action.setEarliestStart(0.F);
                action.setEarliestFinish(action.getDuration());
            }
            else
            {
                maxEF = 0.F;
                for(Action preAction : action.getPrecedingActions())
                {
                    if(preAction.getEarliestFinish() > maxEF) maxEF = preAction.getEarliestFinish();
                }
                action.setEarliestStart(maxEF);
                action.setEarliestFinish(maxEF + action.getDuration());
            }
        }
    }

    //private static void stepBackwardAndCalculateReserve(List<Action> actions)
    public static void stepBackwardAndCalculateReserve(List<Action> actions)
    {
        float minLS = 999999.F;
        float maxEF = 0.F;
        boolean isThisFirstHit = true;
        for(Action action : actions) if(action.getEarliestFinish() > maxEF) maxEF = action.getEarliestFinish();
        for(int i=actions.size()-1; i>=0; i--)
        //for(int i=0; i<actions.size(); i++)
        {
            if(actions.get(i).getEndEvent()==eventNumber)
            {
                actions.get(i).setLatestFinish(maxEF);
                actions.get(i).setLatestStart(maxEF-actions.get(i).getDuration());
            }
            else
            {
                for(Action action : actions)
                {
                    if(action.getPrecedingActions().contains(actions.get(i)))
                    {
                        if(isThisFirstHit)
                        {
                            minLS = action.getLatestStart();
                            isThisFirstHit = false;
                        }
                        else if(action.getLatestStart() < minLS)
                        {
                            minLS = action.getLatestStart();
                        }
                    }
                }
                actions.get(i).setLatestFinish(minLS);
                actions.get(i).setLatestStart(minLS - actions.get(i).getDuration());
                isThisFirstHit = true;
            }
            actions.get(i).setReserve(actions.get(i).getLatestFinish() - actions.get(i).getEarliestFinish());
        }
    }

    static void determineCriticalPath(List<Action> actions)
    {
        eventNumber = 2;
        determineStartAndEndEvents(actions);
        addApparentActions(actions);
        setEndingEvents(actions);
        numberEvents(actions);
        Collections.sort(actions, new ActionAscendingComparator());
        stepForward(actions);
        //Collections.sort(actions, new ActionDescendingComparator()); CZY TO POTRZEBNE? [jeśli tu zmiana, to
        // w funkcji stepBackward... trzeba odwrócić kierunek przechodzenia pętli]
        stepBackwardAndCalculateReserve(actions);
        //Collections.sort(actions, new ActionAscendingComparator());
        eventNumber = 0;
    }
}
