package algorithm;

import java.util.Comparator;

//Comparator służący do sortowania listy czynności (sortuje malejąco wg numerów zdarzeń kończących)
public class ActionDescendingComparator implements Comparator<Action> {
    @Override
    public int compare(Action first, Action second)
    {
        return Integer.compare(second.getEndEvent(), first.getEndEvent());
    }
}
