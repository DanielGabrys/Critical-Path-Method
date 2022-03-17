package algorithm;

import java.util.ArrayList;
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
        List<Action> currentIteration = new ArrayList<>();
        List<Action> nextIteration = new ArrayList<>();
        List<Action> excludedActions = new ArrayList<>();
        for(Action action : actions)
        {
            //if (action.getStartEvent() == 1) currentIteration.add(action);
            if (action.getStartEvent() == 1) nextIteration.add(action);
        }

        eventNumber = 2;
        boolean canBeNumbered = true;
        boolean willIncrementEventNumber = false;
        while(!currentIteration.isEmpty())
        {
            currentIteration.addAll(nextIteration);
            for(int i=0; i<currentIteration.size(); i++)
            {
                for(int j=0; j<actions.size(); j++)
                {
                    if(actions.get(j).getStartEvent() == currentIteration.get(i).getEndEvent())
                    {
                        for(int k=0; k<actions.size(); k++)
                        {
                            if(j!=k && actions.get(j).getEndEvent() == actions.get(k).getEndEvent()
                                    && !excludedActions.contains(k))
                            {
                                canBeNumbered = false;
                                break;
                            }
                        }
                        if(canBeNumbered)
                        {
                            currentIteration.get(i).setEndEvent(eventNumber);
                            actions.get(j).setStartEvent(eventNumber);
                            willIncrementEventNumber = true;
                            //currentIteration.add(actions.get(j));
                            nextIteration.add(actions.get(j));
                        }
                    }
                    canBeNumbered = true;
                }
                if(willIncrementEventNumber)
                {
                    excludedActions.add(currentIteration.get(i));
                    //currentIteration.remove(i);
                    nextIteration.remove(currentIteration.get(i));
                    eventNumber++;
                }
                willIncrementEventNumber = false;
            }
        }

        //biorę czynności startowe i dodaję je do currentIteration
        //for po czynnościach z iteracji
        //znajdowanie czynności mającej początek w końcu czynności z iteracji
        //  jeśli jakakolwiek inna czynność [poza listą wykluczonych] ma koniec w końcu czynności z iteracji to
        //      zostaje zignorowana
        //  jeśli w początku znalezionej czynności nie kończy się żadna czynność poza czynnością z iteracji [oraz listą wykluczonych] to
        //      początek znalezionej czynności = koniec czynności z iteracji = eventNumber (trzeba inkrementować eventNumber dopiero po iteracji!)
        //      dodanie znalezionej czynności do następnej iteracji
        //usunięcie czynności z iteracji z iteracji [sic!], ale dopiero po pełnej iteracji, ale tylko gdy czynność została ,,użyta" + dodanie tej czynności do listy wykluczonych
        //rof
    }

    static void determineCriticalPath(List<Action> actions)
    {
        eventNumber = 2;
        determineStartAndEndEvents(actions);
        addApparentActions(actions);
        setEndingEvents(actions);
        //numberEvents(actions);
        //SORTOWANIE LISTY
        //stepForward(actions);
        //stepBackwardAndCalculateReserve(actions);
        eventNumber = 0;
    }
}
