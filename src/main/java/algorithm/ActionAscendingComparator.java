package algorithm;

import java.util.Comparator;

public class ActionAscendingComparator implements Comparator<Action> {
    @Override
    public int compare(Action first, Action second)
    {
        return Integer.compare(first.getStartEvent(), second.getStartEvent());
    }
}
