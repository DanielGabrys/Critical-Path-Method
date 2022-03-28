package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

//WAŻNA UWAGA! klasa po skończeniu testów powinna udostępniać na zewnątrz tylko metodę determineCriticalPath() -
//z tego powodu w komentarzach znajdują się ,,prywatne wersje" metod i zmiennych, które powinny zastąpić ,,publiczne
//wersje" w finalnej wersji programu
public class CPMAlgorithm
{
    //pomocnicza zmienna numerująca zdarzenia - musi być wspólna dla metod, bo często potrzebna jest jej wartość
    //z poprzedniej metody
    //private static int eventNumber = 0;
    public static int eventNumber = 0;
    //pomocnicza zmienna służąca do nadawania unikalnych nazw czynnościom pozornym
    //private static int apparentEventNumber = 0;
    public static int apparentEventNumber = 0;

    //pomocnicza funkcja sprawdzająca czy listy zawierają te same elementy (kolejność elementów w liście nie
    //ma znaczenia, nie sprawdza czy wartości się powtarzają (zwróci true dla list {A,A,B} i {B,A}))
    private static <T> boolean doTheseListsContainTheSameElements(List<T> list1, List<T> list2)
    {
        return new HashSet<>(list1).equals(new HashSet<>(list2));
    }

    //,,wstępne" wyznaczenie numerów zdarzeń w każdej czynności i dodanie czynności pozornych ,,typu 1"
    //private static void determineStartAndEndEvents(List<Action> actions)
    public static void determineStartAndEndEvents(List<Action> actions)
    {
        //pomocnicza lista na czynności już przetworzone
        List<Action> processedActions = new ArrayList<Action>();
        //pętla po czynnościach wyznaczająca zdarzenia początkowe
        for(Action action : actions)
        {
            //każde zdarzenie początkowe odpowiada unikalnemu zestawowi czynności poprzedzających, więc trzeba dodać
            //nowe zdarzenie początkowe tylko jeśli wystąpił wcześniej niespotkany zestaw poprzedników
            for(int i=0; i<processedActions.size(); i++)
            {
                //,,synchronizacja" z istniejącym zdarzeniem początkowym
                if(doTheseListsContainTheSameElements(action.getPrecedingActions(), processedActions.get(i).getPrecedingActions()))
                {
                    action.setStartEvent(processedActions.get(i).getStartEvent());
                    break;
                }
            }
            //dodanie nowego zdarzenia początkowego jeśli przetwarzana czynność nadal takowego nie ma
            if(action.getStartEvent()==0)
            {
                action.setStartEvent(eventNumber);
                eventNumber++;
            }
            //dodanie czynności do listy czynności przetworzonych
            processedActions.add(action);
        }

        //pętla po czynnościach wyznaczająca ,,wstępną listę zdarzeń końcowych"
        for(Action action1 : actions)
        {
            for(Action action2 : actions)
            {
                //dodanie do listy zdarzeń końcowych początków czynności mających przetwarzaną czynność na liście
                //poprzedników [dodawane są tylko unikalne wartości, aby później algorytm się nie ,,wysypał"]
                if(action2.getPrecedingActions().contains(action1)
                        && !action1.getEndEventHelpList().contains(action2.getStartEvent()))
                {
                    action1.getEndEventHelpList().add(action2.getStartEvent());
                }
            }
        }

        //pomocnicza lista na czynności pozorne
        List<Action> apparentActions = new ArrayList<>();
        //zmienne pomocnicze
        boolean doesThisActionEndIndependently = false;
        int independentEventNumber = 0;
        //pętla wyznaczająca ,,ostateczne zdarzenia końcowe" i czynności pozorne
        for(Action action : actions)
        {
            //jeśli czynność ma tylko jedno zdarzenie końcowe na liście pomocniczej, to to zdarzenie staje się
            //jego zdarzeniem końcowym
            if(action.getEndEventHelpList().size()==1)
            {
                action.setEndEvent(action.getEndEventHelpList().get(0));
            }
            //jeśli czynność ma na liście pomocniczej wiele zdarzeń końcowych, to trzeba dodać czynność pozorną
            else if(action.getEndEventHelpList().size()>=2)
            {
                //sprawdzenie czy przetwarzana czynność kończy się gdzieś ,,samotnie" czy tylko razem z innymi
                for(Action actionHelp : actions)
                {
                    if(actionHelp.getPrecedingActions().size()==1 && actionHelp.getPrecedingActions().contains(action))
                    {
                        doesThisActionEndIndependently = true;
                        //zapisanie miejsca zakończenia ,,samotnej" czynności jeśli takie istnieje
                        independentEventNumber = actionHelp.getStartEvent();
                        break;
                    }
                }
                //jeśli czynność kończy się gdzieś ,,samotnie"
                if(doesThisActionEndIndependently)
                {
                    //pętla po wszystkich ,,zdarzeniach końcowych" przetwarzanej czynności
                    for(int endEvent : action.getEndEventHelpList())
                    {
                        //,,samotne" zakończenie czynności jest jej ,,realnym" zdarzeniem końcowym
                        if(endEvent==independentEventNumber)
                        {
                            action.setEndEvent(independentEventNumber);
                        }
                        //poprowadzenie czynności pozornej od ,,samotnego" zakończenia czynności do jej
                        //zakończenia ,,w zestawie z innymi"
                        else
                        {
                            //dodanie czynności pozornej
                            Action apparent = new Action("apparent"+apparentEventNumber, 0.F,
                                    new ArrayList<Action>(){{add(action);}}, independentEventNumber,
                                    endEvent);
                            apparentActions.add(apparent);
                            //,,synchronizacja" poprzedników
                            for(Action actionHelp : actions)
                            {
                                if(actionHelp.getStartEvent()==endEvent)
                                {
                                    actionHelp.getPrecedingActions().remove(action);
                                    actionHelp.getPrecedingActions().add(apparent);
                                }
                            }
                            apparentEventNumber++;
                        }
                    }
                }
                //jeśli czynność kończy się tylko razem z innymi
                else
                {
                    //dodanie pozornego ,,samotnego" zakończenia czynności
                    action.setEndEvent(eventNumber);
                    //poprowadzenie czynności pozornych od pozornego ,,samotnego" zakończenia czynności do jej
                    //zakończeń ,,w zestawie z innymi"
                    for(int endEvent : action.getEndEventHelpList())
                    {
                        //dodanie czynności pozornej
                        Action apparent = new Action("apparent"+apparentEventNumber, 0.F,
                                new ArrayList<Action>(){{add(action);}}, eventNumber,
                                endEvent);
                        apparentActions.add(apparent);
                        //,,synchronizacja" poprzedników
                        for(Action actionHelp : actions)
                        {
                            if(actionHelp.getStartEvent()==endEvent)
                            {
                                actionHelp.getPrecedingActions().remove(action);
                                actionHelp.getPrecedingActions().add(apparent);
                            }
                        }
                        apparentEventNumber++;
                    }
                    eventNumber++;
                }
                //,,wyzerowanie" zmiennych pomocniczych
                doesThisActionEndIndependently = false;
                independentEventNumber = 0;
            }
        }
        //dodanie czynności pozornych do listy wszystkich czynności
        for(Action action : apparentActions) actions.add(action);
    }

    //dodanie czynności pozornych ,,typu 2"
    //private static void addApparentActions(List<Action> actions)
    public static void addApparentActions(List<Action> actions)
    {
        //lista pomocnicza na czynności pozorne
        List<Action> apparentActions = new ArrayList<>();
        //podwójna pętla po liście czynności (sprawdzanie każdej czynności z każdą)
        for(int i=0; i<actions.size(); i++)
        {
            for(int j=i+1; j<actions.size(); j++)
            {
                //dodanie czynności pozornej jeżeli dwie różne czynności mają te same zdarzenia początkowe
                //i końcowe
                if(actions.get(i) != actions.get(j)
                        && actions.get(i).getStartEvent() == actions.get(j).getStartEvent()
                        && actions.get(i).getEndEvent() == actions.get(j).getEndEvent())
                {
                    //dodanie czynności pozornej
                    int J = j;
                    Action apparent = new Action("apparent"+apparentEventNumber, 0.F,
                            new ArrayList<Action>(){{add(actions.get(J));}}, eventNumber,
                            actions.get(j).getEndEvent());
                    apparentActions.add(apparent);
                    actions.get(j).setEndEvent(eventNumber);
                    //,,synchronizacja" poprzedników
                    for(Action actionHelp : actions)
                    {
                        if(actionHelp.getStartEvent()==apparent.getEndEvent())
                        {
                            actionHelp.getPrecedingActions().remove(actions.get(J));
                            actionHelp.getPrecedingActions().add(apparent);
                        }
                    }
                    //zaktualizowanie zmiennych pomocniczych
                    eventNumber++;
                    apparentEventNumber++;
                }
            }
        }
        //dodanie czynności pozornych do listy wszystkich czynności w sieci
        for(Action action : apparentActions) actions.add(action);
    }

    //,,dodanie zdarzenia końcowego" w czynnościach kończących sieć
    //private static void setEndingEvents(List<Action> actions)
    public static void setEndingEvents(List<Action> actions)
    {
        for(Action action : actions)
        {
            if(action.getEndEvent()==0)
            {
                //wartość zmiennej eventNumber po poprzednich dwóch metodach to liczba zdarzeń w całej sieci
                action.setEndEvent(eventNumber);
            }
        }
    }

    //ponumerowanie zdarzeń wg zasad ich numerowania
    //private static void numberEvents(List<Action> actions)
    public static void numberEvents(List<Action> actions)
    {
        //zmienne pomocnicze
        int newStart = 0;
        int newEnd = 0;
        boolean areNumbersCorrect = false;
        while(!areNumbersCorrect)
        {
            areNumbersCorrect = true;
            //pętla iterująca po wszystkich czynnościach
            for (Action action : actions)
            {
                //trzeba zmienić numerację zdarzeń tylko jeżeli numer zdarzenia zaczynającego jest większy od numeru
                //zdarzenia kończącego
                if (action.getStartEvent() > action.getEndEvent())
                {
                    //,,zamiana numerów zdarzeń w obrębie czynności"
                    newEnd = action.getStartEvent();
                    newStart = action.getEndEvent();
                    action.setStartEvent(newStart);
                    action.setEndEvent(newEnd);
                    //zaktualizowanie numeru zmienionego zdarzenia w całej sieci
                    for (Action action1 : actions)
                    {
                        //nie zmieniamy tam, gdzie zmieniliśmy przed wewnętrzną pętlą
                        if (action != action1)
                        {
                            if (action1.getStartEvent() == newStart)
                            {
                                action1.setStartEvent(newEnd);
                            }
                            else if (action1.getEndEvent() == newEnd)
                            {
                                action1.setEndEvent(newStart);
                            }
                            else if (action1.getEndEvent() == newStart)
                            {
                                action1.setEndEvent(newEnd);
                            }
                            else if (action1.getStartEvent() == newEnd)
                            {
                                action1.setStartEvent(newStart);
                            }
                        }
                    }
                    //po jednej zamianie trzeba sprawdzić czy wcześniej sprawdzone czynności są dalej w porządku
                    areNumbersCorrect = false;
                    break;
                }
            }
        }
    }

    //wykonanie ,,kroku w przód" z wyznaczeniem ES i EF
    //private static void stepForward(List<Action> actions)
    public static void stepForward(List<Action> actions)
    {
        //zmienna pomocnicza
        float maxEF = 0.F;
        //wyznaczenie ES i EF we wszystkich czynnościach - listę trzeba przechodzić wg numerów zdarzeń, ponieważ
        //do wyznaczania wartości ES i EF potrzebne są te wartości z czynności poprzedzających (lista została
        //posortowana przed wejściem do tej metody)
        for(Action action : actions)
        {
            //wyznaczenie ES i EF dla czynności początkowych sieci
            //if(action.getPrecedingActions().isEmpty())
            if(action.getStartEvent()==1)
            {
                action.setEarliestStart(0.F);
                action.setEarliestFinish(action.getDuration());
            }
            //wyznaczenie ES i EF dla czynności innych niż początkowe
            else
            {
                //szukanie maksymalnego EF z czynności poprzedzających
                maxEF = 0.F;
                for(Action preAction : action.getPrecedingActions())
                {
                    if(preAction.getEarliestFinish() > maxEF) maxEF = preAction.getEarliestFinish();
                }
                action.setEarliestStart(maxEF);
                action.setEarliestFinish(maxEF + action.getDuration());
            }
        }
    }

    //wykonanie ,,kroku w tył" z wyznaczeniem LS i LF oraz policzenie rezerwy czasowej
    //private static void stepBackwardAndCalculateReserve(List<Action> actions)
    public static void stepBackwardAndCalculateReserve(List<Action> actions)
    {
        //zmienne pomocnicze
        float minLS = 999999.F;
        float maxEF = 0.F;
        //flaga wykorzystywana przy poszukiwaniu minimum
        boolean isThisFirstHit = true;
        //znalezienie maksymalnego EF w całej sieci
        for(Action action : actions) if(action.getEarliestFinish() > maxEF) maxEF = action.getEarliestFinish();
        //pętla iterująca po wszystkich czynnościach OD TYŁU, ponieważ teraz potrzebujemy pobierać wartości
        //z następników danej czynności ; pętla wyznacza LS, LF i rezerwę czasową
        for(int i=actions.size()-1; i>=0; i--)
        //for(int i=0; i<actions.size(); i++)
        {
            //wyznaczenie LS i LF dla czynności kończących sieci
            if(actions.get(i).getEndEvent()==eventNumber)
            {
                actions.get(i).setLatestFinish(maxEF);
                actions.get(i).setLatestStart(maxEF-actions.get(i).getDuration());
            }
            //wyznaczenie LS i LF dla czynności sieci innych niż kończące
            else
            {
                //poszukiwanie minimalnego LS następników danej czynności
                for(Action action : actions)
                {
                    if(action.getPrecedingActions().contains(actions.get(i)))
                    {
                        if(isThisFirstHit)
                        {
                            minLS = action.getLatestStart();
                            isThisFirstHit = false;
                        }
                        else if(action.getLatestStart() < minLS)
                        {
                            minLS = action.getLatestStart();
                        }
                    }
                }
                actions.get(i).setLatestFinish(minLS);
                actions.get(i).setLatestStart(minLS - actions.get(i).getDuration());
                isThisFirstHit = true;
            }
            //wyznaczenie rezerwy czasowej
            actions.get(i).setReserve(actions.get(i).getLatestFinish() - actions.get(i).getEarliestFinish());
        }
    }

    //statyczna metoda udostępniana na zewnątrz klasy przekształcająca listę czynności w ,,formacie" wczytanym
    //z GUI w listę czynności z wyznaczoną ścieżką krytyczną i parametrami potrzebnymi do jej wyznaczenia
    public static void determineCriticalPath(List<Action> actions)
    {
        //nadanie zmiennej eventNumber wartości potrzebnej do poprawnego działania determineStartAndEndEvents()
        eventNumber = 1;
        //nadanie zmiennej apparentEventNumber odpowiedniej wartości
        apparentEventNumber = 1;
        //,,wstępne" wyznaczenie numerów zdarzeń w każdej czynności i dodanie czynności pozornych ,,typu 1"
        determineStartAndEndEvents(actions);
        //dodanie czynności pozornych ,,typu 2"
        addApparentActions(actions);
        //,,dodanie zdarzenia końcowego" w czynnościach kończących sieć
        setEndingEvents(actions);
        //ponumerowanie zdarzeń wg zasad ich numerowania
        numberEvents(actions);
        //posortowanie listy czynności na podstawie wcześniej wyznaczonych numerów zdarzeń (potrzebne do stepForward())
        Collections.sort(actions, new ActionAscendingComparator());
        //wykonanie ,,kroku w przód" z wyznaczeniem ES i EF
        stepForward(actions);
        //Collections.sort(actions, new ActionDescendingComparator()); CZY TO POTRZEBNE? [jeśli tu zmiana, to
        // w funkcji stepBackward... trzeba odwrócić kierunek przechodzenia pętli]
        //wykonanie ,,kroku w tył" z wyznaczeniem LS i LF oraz policzenie rezerwy czasowej
        stepBackwardAndCalculateReserve(actions);
        //to jest potrzebne do poprawnego wyświetlania, jeśli wcześniej nastąpiło sortowanie malejące
        //Collections.sort(actions, new ActionAscendingComparator());
        //wyzerowanie zmiennej eventNumber
        eventNumber = 0;
        apparentEventNumber = 0;
    }
}
