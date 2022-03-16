package boil.cpm;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Graph {

    public org.graphstream.graph.Graph graph;

    public Graph()
        {
            this.graph = new SingleGraph("CPM");

            // inluclude css styles
            graph.setAttribute("ui.stylesheet", styleSheet);
            graph.setAutoCreate(true);
            graph.setStrict(false);
            graph.display();


            graph.addEdge("A", "1", "2").setAttribute("length","3 A");
            graph.addEdge("B", "2", "3").setAttribute("length","4 B");
            graph.addEdge("C", "2", "4").setAttribute("length","6 C");
            graph.addEdge("D", "3", "5").setAttribute("length","7 D");
            graph.addEdge("E", "5", "7").setAttribute("length","1 E");
            graph.addEdge("F", "4", "7").setAttribute("length","2 F");
            graph.addEdge("G", "4", "6").setAttribute("length","3 G");
            graph.addEdge("H", "6", "7").setAttribute("length","4 H");
            graph.addEdge("I", "7", "8").setAttribute("length","1 I");
            graph.addEdge("J", "8", "9").setAttribute("length","2 K");

            //
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
            markCriticalPath(critical_path);
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
