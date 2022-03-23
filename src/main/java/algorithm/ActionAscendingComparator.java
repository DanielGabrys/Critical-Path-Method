package algorithm;

import java.util.Comparator;

//Comparator służący do sortowania listy czynności (sortuje rosnąco wg numerów zdarzeń rozpoczynających)
public class ActionAscendingComparator implements Comparator<Action> {
    @Override
    public int compare(Action first, Action second)
    {
        return Integer.compare(first.getStartEvent(), second.getStartEvent());
    }
}
