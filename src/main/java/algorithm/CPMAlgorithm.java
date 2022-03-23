package algorithm;

import java.util.ArrayList;
import java.util.Collections;
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

    //,,wstępne" wyznaczenie numerów zdarzeń w każdej czynności
    //private static void determineStartAndEndEvents(List<Action> actions)
    public static void determineStartAndEndEvents(List<Action> actions)
    {
        //pomocnicza lista czynności, które zostały już przetworzone
        List<Action> processedActions = new ArrayList<>();
        //,,zaznaczenie" czynności niemających poprzedników jako czynności startowe sieci
        for(Action action : actions)
        {
            if(action.getPrecedingActions().isEmpty()) action.setStartEvent(1);
        }

        //flaga służąca sprawdzeniu czy przetwarzana czynność jest czynnością końcową sieci
        boolean isEnd = true;
        //pętla po liście czynności nadająca numery zdarzeniom
        for(Action action : actions)
        {
            //wewnętrzna pętla po liście czynności (,,sprawdzenie każdej czynności z każdą")
            for(Action action1 : actions)
            {
                //pętla po liście czynności już przetworzonych
                for(Action processed : processedActions)
                {
                    //sprawdzenie czy czynność zawiera na liście zdarzeń poprzedzających czynność z iteracji oraz
                    //jakąkolwiek czynność już przetworzoną (ponieważ czynności przetworzone mają już wyznaczone
                    //numery zdarzeń końcowych (poza czynnościami kończącymi sieć), a trzeba zsynchronizować)
                    if (action1.getPrecedingActions().contains(action)
                            && action1.getPrecedingActions().contains(processed))
                    {
                        action.setEndEvent(processed.getEndEvent());
                        break;
                    }
                }
            }
            //wpisanie numeru zdarzenia początkowego czynnościom, które go jeszcze nie mają
            if(action.getStartEvent()==0)
            {
                //pętla po zdarzeniach poprzedzających zdrzenie z iteracji
                for(Action preceder : action.getPrecedingActions())
                {
                    //jeśli którykolwiek poprzednik czynności ma już wyznaczone zdarzenie końcowe, to w celu
                    //synchronizacji czynność następująca po nim musi się tam zaczynać
                    if(preceder.getEndEvent() != 0)
                    {
                        action.setStartEvent(preceder.getEndEvent());
                        break;
                    }
                }
                //jeżeli czynność nadal nie ma ustawionego zdarzenia początkowego, to przypisanie mu wartości
                //inkrementowanej zmiennej pomocniczej
                if(action.getStartEvent()==0)
                {
                    action.setStartEvent(eventNumber);
                    eventNumber++;
                }
            }
            //wpisanie numeru zdarzenia końcowego czynnościom, które go jeszcze nie mają
            if(action.getEndEvent()==0)
            {
                //sprawdzenie czy jakakolwiek czynność z sieci zawiera na liście poprzedników czynność z tej
                //iteracji (czy czynność z iteracji jest czynnością końcową sieci)
                for(Action action1 : actions)
                {
                    if(action1.getPrecedingActions().contains(action))
                    {
                        isEnd = false;
                        break;
                    }
                }
                //jeśli czynność nie jest czynnością końcową sieci, to przypisanie jej zdarzenia końcowego
                //wartości inkrementowanej zmiennej pomocniczej
                if(!isEnd)
                {
                    action.setEndEvent(eventNumber);
                    eventNumber++;
                }
                //przywrócenie fladze wartości ,,domyślnej" na następną iterację
                isEnd = true;
            }
            //dodanie czynności do listy przetworzonych
            processedActions.add(action);
        }
    }

    //DO ZWERYFIKOWANIA
    //private static void addApparentActions(List<Action> actions)
    public static void addApparentActions(List<Action> actions)
    {
        int apparentEventNumber = 1;
        for(int i=0; i<actions.size(); i++)
        {
            for(int j=i+1; j<actions.size(); j++)
            {
                if(actions.get(i) != actions.get(j)
                        && actions.get(i).getStartEvent() == actions.get(j).getStartEvent()
                        && actions.get(i).getEndEvent() == actions.get(j).getEndEvent())
                {
                    int J = j;
                    actions.add(new Action("apparent"+apparentEventNumber, 0.F,
                            new ArrayList<Action>(){{add(actions.get(J));}}, eventNumber,
                            actions.get(j).getEndEvent()));
                    actions.get(j).setEndEvent(eventNumber);
//                    int I = i;
//                    actions.add(new Action("apparent"+apparentEventNumber, 0.F,
//                            new ArrayList<Action>(){{add(actions.get(I));}}, eventNumber,
//                            actions.get(i).getEndEvent()));
//                    actions.get(i).setEndEvent(eventNumber);

                    eventNumber++;
                    apparentEventNumber++;
                }
            }
        }
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
        //pętla iterująca po wszystkich czynnościach
        for(Action action : actions)
        {
            //trzeba zmienić numerację zdarzeń tylko jeżeli numer zdarzenia zaczynającego jest większy od numeru
            //zdarzenia kończącego
            if(action.getStartEvent() > action.getEndEvent())
            {
                //,,zamiana numerów zdarzeń w obrębie czynności"
                newEnd = action.getStartEvent();
                newStart = action.getEndEvent();
                action.setStartEvent(newStart);
                action.setEndEvent(newEnd);
                //zaktualizowanie numeru zmienionego zdarzenia w całej sieci
                for(Action action1 : actions)
                {
                    //nie zmieniamy tam, gdzie zmieniliśmy przed wewnętrzną pętlą
                    if(action != action1)
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
        eventNumber = 2;
        //,,wstępne" wyznaczenie numerów zdarzeń w każdej czynności
        determineStartAndEndEvents(actions);
        //dodanie czynności pozornych
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
    }
}
