package agenda;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Description : An agenda that stores events
 */
public class Agenda {

    /**
     * Adds an event to this agenda
     *
     * @param e the event to add
     */
    private final List<Event> events = new ArrayList<>();

    public void addEvent(Event e) {
        // TODO : implémenter cette méthode
        events.add(e);    
    }

    /**
     * Computes the events that occur on a given day
     *
     * @param day the day toi test
     * @return a list of events that occur on that day
     */
    public List<Event> eventsInDay(LocalDate day) {
        // TODO : implémenter cette méthode
        List<Event> l = new ArrayList<>();
        for (Event e : events) {
            if (e.isInDay(day)){
                l.add(e);
            } 
        }
        return l;
    }

    /**
     * Trouver les événements de l'agenda en fonction de leur titre
     * @param title le titre à rechercher
     * @return les événements qui ont le même titre
     */
    public List<Event> findByTitle(String title) {
        List<Event> l = new ArrayList<>();
        for (Event e : events) {
            if (e.getTitle().equals(title)){
                l.add(e);
            } 
        }
        return l;
    }

    /**
     * Déterminer s’il y a de la place dans l'agenda pour un événement (aucun autre événement au même moment)
     * @param e L'événement à tester (on se limitera aux événements sans répétition)
     * @return vrai s’il y a de la place dans l'agenda pour cet événement
     */
    public boolean isFreeFor(Event e) {
        LocalDateTime start1 = e.getStart();
        LocalDateTime end1 = e.getStart().plus(e.getDuration());

        for (Event e1 : events) {
            if (e1.hasRepetition()){
                continue; 
            } 

            LocalDateTime start2 = e1.getStart();
            LocalDateTime end2 = e1.getStart().plus(e1.getDuration());

            if (start1.isBefore(end2) && start2.isBefore(end1))
                return false;
        }
        return true;
    }
}
