package boil.cpm;

import javafx.collections.ObservableList;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Graph {

    public org.graphstream.graph.Graph graph;

    public Graph(ObservableList<Activity> list )
        {
            this.graph = new SingleGraph("CPM");

            // inluclude css styles
            graph.setAttribute("ui.stylesheet", styleSheet);

            graph.setAutoCreate(true);
            graph.setStrict(false);
            graph.display();

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
                node.setAttribute("ui.label", node.getId()+" | "+3 +" | "+3 +" | "+3);
            }

            //set edges
            graph.edges().forEach(e -> e.setAttribute("label", "" +  e.getAttribute("length")));
            //graph.edges().forEach(e -> e.setAttribute("label", "" +  e.getNumber("length")));

            //explore(graph.getNode("1"));

            //example critical path(nodes)
            List<String> critical_path = Arrays.asList("1", "2", "3","5", "7","8","9");
           // markCriticalPath(critical_path);
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

        protected String styleSheet =
                "node   {" +
                             "fill-color: red;" +
                             "size: 80px;" +
                             "shape: box;" +
                        "}" +
                "node.marked " +
                        "{" +
                            "fill-color: green;" +
                        "}" +
                "edge " +
                        "{"+
                        "fill-color: yellow;"+
                        "size: 3px;"+
                        "}";




}
