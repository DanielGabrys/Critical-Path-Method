package algorithm;

import java.util.Comparator;

public class ActionDescendingComparator implements Comparator<Action> {
    @Override
    public int compare(Action first, Action second)
    {
        return Integer.compare(second.getEndEvent(), first.getEndEvent());
    }
}
