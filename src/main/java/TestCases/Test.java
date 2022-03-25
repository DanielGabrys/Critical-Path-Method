package TestCases;

import boil.cpm.Activity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Test
{
   public ObservableList<Activity> loadTest(Integer id)
   {
       //Tests 1-3 fake path
       ObservableList<Activity> a = FXCollections.observableArrayList();
       switch (id)
       {
           case 1:
           {
               a = FXCollections.observableArrayList(
                       new Activity("A", "6", "-"),
                       new Activity("B", "8", "-"),
                       new Activity("C", "12", "A,B"),
                       new Activity("D", "4", "C"),
                       new Activity("E", "6", "C"),
                       new Activity("F", "15", "D,E"),
                       new Activity("G", "12", "E"),
                       new Activity("H", "8", "F,G"));
               break;
           }
           case 2:
           {
               a= FXCollections.observableArrayList(
                       new Activity("A","5","-"),
                       new Activity("B","3","A"),
                       new Activity("C","4","-"),
                       new Activity("D","6","A"),
                       new Activity("E","4","D"),
                       new Activity("F","3","B,C,D"));
               break;
           }

           case 3:
           {
               a = FXCollections.observableArrayList(
                       new Activity("A","5","-"),
                       new Activity("B","7","A"),
                       new Activity("C","6","A,B"));
               break;
           }

           case 4:
           {
               a = FXCollections.observableArrayList(
                       new Activity("A","3","-"),
                       new Activity("B","4","A"),
                       new Activity("C","6","A"),
                       new Activity("D","7","B"),
                       new Activity("E","1","D"),
                       new Activity("F","2","C"),
                       new Activity("G","3","C"),
                       new Activity("H","4","G"),
                       new Activity("I","1","E,F,H"),
                       new Activity("J","2","I"));
               break;
           }

           case 5:
           {
               a = FXCollections.observableArrayList(
                       new Activity("A","5","-"),
                       new Activity("B","7","-"),
                       new Activity("C","6","A"),
                       new Activity("D","8","A"),
                       new Activity("E","3","B"),
                       new Activity("F","4","C"),
                       new Activity("G","2","C"),
                       new Activity("H","5","E,D,F"));
               break;
           }

       }
       return a;

   }
}
