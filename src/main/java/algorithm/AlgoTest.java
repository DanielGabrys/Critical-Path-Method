package algorithm;

import java.util.ArrayList;
import java.util.List;

public class AlgoTest {
    public static void main(String[] args)
    {
        Action A = new Action("A", 5.F, new ArrayList<Action>());
        Action B = new Action("B", 3.F, new ArrayList<Action>(){{add(A);}});
        Action C = new Action("C", 4.F, new ArrayList<Action>());
        Action D = new Action("D", 6.F, new ArrayList<Action>(){{add(A);}});
        Action E = new Action("E", 4.F, new ArrayList<Action>(){{add(D);}});
        Action F = new Action("F", 3.F, new ArrayList<Action>(){{add(B);add(C);add(D);}});
        List<Action> testList = new ArrayList<>(){{add(A);add(B);add(C);add(D);add(E);add(F);}};

        CPMAlgorithm.eventNumber = 2;
        CPMAlgorithm.determineStartAndEndEvents(testList);
        //for(Action action : testList) action.display();
        CPMAlgorithm.addApparentActions(testList);
        //for(Action action : testList) action.display();
        CPMAlgorithm.setEndingEvents(testList);
        //for(Action action : testList) action.displayWithList();
        CPMAlgorithm.numberEvents(testList);
        for(Action action : testList) action.displayWithList();
        System.out.println("\n"+CPMAlgorithm.eventNumber+"\n");
    }
}
