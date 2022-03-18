package boil.cpm;


import algorithm.Action;
import javafx.collections.ObservableList;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.view.Viewer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static java.lang.Integer.parseInt;

public class Graph
{

    public org.graphstream.graph.Graph graph;

    public Graph()
        {

        }

    public void explore(Node source)
        {
            Iterator<? extends Node> k = source.getBreadthFirstIterator();
            while (k.hasNext()) {
                Node next = k.next();
                //next.setAttribute("ui.class", "marked");
                sleep();
            }
        }

    protected void sleep()
        {
            try { Thread.sleep(1000); } catch (Exception e) {}
        }

    void markCriticalPath(List<String> a)
        {
            for(int i=0;i<a.size();i++)
            {
                this.graph.getNode(a.get(i)).setAttribute("ui.class", "marked");
                sleep();
            }
        }

    public List inputAdapter(ObservableList<Activity> list )
    {
        List<Action> input_list = new ArrayList<>();
        for(int i=0;i<list.size();i++)
        {

            String action_name= list.get(i).getActivity();
            float duration = list.get(i).getTime();

            Action a = new Action(action_name,duration,new ArrayList<Action>());

            List<Action> sequence = new ArrayList<>();
            for(int j=0;j< list.size();j++)
            {

                if(parseInt(list.get(i).getPrevious_sequence())==parseInt(list.get(j).getNext_sequence()))
                {
                  sequence.add(input_list.get(j));
                }

            }
            a.setPrecedingActions(sequence);
            input_list.add(a);
        }

        return input_list;
    }

    public void generateGraph(ObservableList<Activity> list, List<Action> a)
    {
        this.graph = new SingleGraph("CPM");

        // inluclude css styles
        graph.setAttribute("ui.stylesheet", styleSheet);

        graph.setAutoCreate(true);
        graph.setStrict(false);
        Viewer viewer = graph.display();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);


        for(int i=0;i<list.size();i++)
        {
            String weight = list.get(i).getTime()+" "+list.get(i).getActivity();

            graph.addEdge(list.get(i).getActivity(),
                    list.get(i).getPrevious_sequence(),
                    list.get(i).getNext_sequence()).setAttribute("length",weight);

            //graph.addEdge("A", "1", "2").setAttribute("length","3 A");
        }

        //node fields

        for (Node node : graph)
        {
            //Action action = actionData()
            node.setAttribute("ui.label", node.getId());
        }

        SpriteManager sman = new SpriteManager(graph);

        //set edges
        for (int i = 0; i < graph.getEdgeCount(); i++)
        {
            Edge e = graph.getEdge(i);
            e.setAttribute("label", "" +  e.getAttribute("length"));

            Sprite s = sman.addSprite(e.getId());
            s.attachToEdge(e.getId());
            s.setPosition(0.5, 0.15, 0);

            float ES = a.get(i).getEarliestStart();
            float EF = a.get(i).getEarliestFinish();
            float LS = a.get(i).getLatestStart();
            float LF = a.get(i).getLatestFinish();
            float rez = a. get(i).getReserve();

            String data= ES+" | "+EF+" | "+LS+" | "+LF+" | "+rez;

            s.setAttribute("label", data);

        }

        //explore(graph.getNode("1"));

        //example critical path(nodes)
        List<String> critical_path = Arrays.asList("1", "2", "3","5", "7","8","9");
        // markCriticalPath(critical_path);
    }

    protected String styleSheet =
            "node   {" +
                    "fill-color: red;" +
                    "size: 50px;" +
                    "text-size: 20px;" +

                    "}" +
            "node.marked " +
                    "{" +
                    "fill-color: green;" +
                    "}" +
            "edge " +
                    "{"+
                    "fill-color: yellow;"+
                    "size: 3px;"+
                    "}"+
            "sprite " +
                    "{" +
                    "shape: box;"+
                    "fill-color: pink;"+
                    "size: 30px;" +
                    "size-mode: fit;"+
                    "text-alignment: center;"+
                    "text-size: 10px;" +
                    "}" ;

}
