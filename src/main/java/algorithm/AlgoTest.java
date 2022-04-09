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
//        CPMAlgorithm.determineCriticalPath(testList);
//        for(Action action : testList) action.displayWithList();



//        Action one = new Action("A", 5.F, new ArrayList<Action>());
//        Action two = new Action("B", 7.F, new ArrayList<Action>());
//        Action three = new Action("C", 6.F, new ArrayList<Action>(){{add(one);}});
//        Action four = new Action("D", 8.F, new ArrayList<Action>(){{add(one);}});
//        Action five = new Action("E", 3.F, new ArrayList<Action>(){{add(two);}});
//        Action six = new Action("F", 4.F, new ArrayList<Action>());
//        Action seven = new Action("G", 2.F, new ArrayList<Action>());
//        Action eight = new Action("H", 5.F, new ArrayList<Action>(){{add(five);add(four);add(six);}});
//        List<Action> testList2 = new ArrayList<>(){{add(one);add(two);add(three);add(four);add(five);add(six);add(seven);add(eight);}};
//        CPMAlgorithm.determineCriticalPath(testList2);
//        for(Action action : testList2) action.displayWithList();



        Action a = new Action("A", 6.F, new ArrayList<Action>());
        Action b = new Action("B", 8.F, new ArrayList<Action>());
        Action c = new Action("C", 12.F, new ArrayList<Action>(){{add(a);add(b);}});
        Action d = new Action("D", 4.F, new ArrayList<Action>(){{add(c);}});
        Action e = new Action("E", 6.F, new ArrayList<Action>(){{add(c);}});
        Action f = new Action("F", 15.F, new ArrayList<Action>(){{add(d);add(e);}});
        Action g = new Action("G", 12.F, new ArrayList<Action>(){{add(e);}});
        Action h = new Action("H", 8.F, new ArrayList<Action>(){{add(f);add(g);}});
        List<Action> testList3 = new ArrayList<Action>(){{add(a);add(b);add(c);add(d);add(e);add(f);add(g);add(h);}};
        CPMAlgorithm.determineCriticalPath(testList3);
        for(Action action : testList3) action.displayWithList();
    }
}
