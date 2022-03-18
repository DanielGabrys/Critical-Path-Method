package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlgoTest {
    public static void main(String[] args)
    {
//        Action A = new Action("A", 5.F, new ArrayList<Action>());
//        Action B = new Action("B", 3.F, new ArrayList<Action>(){{add(A);}});
//        Action C = new Action("C", 4.F, new ArrayList<Action>());
//        Action D = new Action("D", 6.F, new ArrayList<Action>(){{add(A);}});
//        Action E = new Action("E", 4.F, new ArrayList<Action>(){{add(D);}});
//        Action F = new Action("F", 3.F, new ArrayList<Action>(){{add(B);add(C);add(D);}});
//        List<Action> testList = new ArrayList<>(){{add(A);add(B);add(C);add(D);add(E);add(F);}};
//
//        CPMAlgorithm.eventNumber = 2;
//        CPMAlgorithm.determineStartAndEndEvents(testList);
//        //for(Action action : testList) action.display();
//        CPMAlgorithm.addApparentActions(testList);
//        //for(Action action : testList) action.display();
//        CPMAlgorithm.setEndingEvents(testList);
//        //for(Action action : testList) action.displayWithList();
//        CPMAlgorithm.numberEvents(testList);
//        //for(Action action : testList) action.displayWithList();
//        Collections.sort(testList,  new ActionAscendingComparator());
//        CPMAlgorithm.stepForward(testList);
//        //for(Action action : testList) action.displayWithList();
//        //Collections.sort(testList, new ActionDescendingComparator());
//        CPMAlgorithm.stepBackwardAndCalculateReserve(testList);
//        //Collections.sort(testList,  new ActionAscendingComparator());
//        //for(Action action : testList) action.displayWithList();



        Action one = new Action("A", 5.F, new ArrayList<Action>());
        Action two = new Action("B", 7.F, new ArrayList<Action>());
        Action three = new Action("C", 6.F, new ArrayList<Action>(){{add(one);}});
        Action four = new Action("D", 8.F, new ArrayList<Action>(){{add(one);}});
        Action five = new Action("E", 3.F, new ArrayList<Action>(){{add(two);}});
        Action six = new Action("F", 4.F, new ArrayList<Action>(){{add(three);}});
        Action seven = new Action("G", 2.F, new ArrayList<Action>(){{add(three);}});
        Action eight = new Action("H", 5.F, new ArrayList<Action>(){{add(five);add(four);add(six);}});
        List<Action> testList2 = new ArrayList<>(){{add(one);add(two);add(three);add(four);add(five);add(six);add(seven);add(eight);}};
        CPMAlgorithm.determineCriticalPath(testList2);
        for(Action action : testList2) action.displayWithList();
    }
}
