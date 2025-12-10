package agenda;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class Event {

    /**
     * The myTitle of this event
     */
    private String myTitle;
    
    /**
     * The starting time of the event
     */
    private LocalDateTime myStart;

    /**
     * The durarion of the event 
     */
    private Duration myDuration;


    /**
     * Constructs an event
     *
     * @param title the title of this event
     * @param start the start time of this event
     * @param duration the duration of this event
     */
    private Repetition repetition;

    public Event(String title, LocalDateTime start, Duration duration) {
        this.myTitle = title;
        this.myStart = start;
        this.myDuration = duration;
    }
    public boolean hasRepetition() {
        return repetition != null;
    }

    public void setRepetition(ChronoUnit frequency) {
        // TODO : implémenter cette méthode
        this.repetition = new Repetition(frequency);
    }

    public void addException(LocalDate date) {
        // TODO : implémenter cette méthode
        if (repetition != null){
            repetition.addException(date);
        }    
    }

    public void setTermination(LocalDate terminationInc) {
        // TODO : implémenter cette méthode
        if (repetition != null){
            repetition.setTermination(new Termination(myStart.toLocalDate(), repetition.getFrequency(), terminationInc));
        }
    }

    public void setTermination(long numberOfOccurrences) {
        // TODO : implémenter cette méthode
        if (repetition != null){
            repetition.setTermination(new Termination(myStart.toLocalDate(), repetition.getFrequency(), numberOfOccurrences));
        }
    }

    public int getNumberOfOccurrences() {
        // TODO : implémenter cette méthode
        if (repetition == null || repetition.getTermination() == null)
            return Integer.MAX_VALUE; 
        return (int) repetition.getTermination().numberOfOccurrences();    }

    public LocalDate getTerminationDate() {
        // TODO : implémenter cette méthode
        if (repetition == null || repetition.getTermination() == null)
            return null;
        return repetition.getTermination().terminationDateInclusive();
    }

    /**
     * Tests if an event occurs on a given day
     *
     * @param aDay the day to test
     * @return true if the event occurs on that day, false otherwise
     */
    public boolean isInDay(LocalDate aDay) {
        // TODO : implémenter cette méthode
 // Cas événement simple
        if (repetition == null) {
            LocalDate startDay = myStart.toLocalDate();
            LocalDate endDay = myStart.plus(myDuration).toLocalDate();

            return !aDay.isBefore(startDay) && !aDay.isAfter(endDay);
        }

        // Cas répétitif :
        LocalDate startDate = myStart.toLocalDate();

        if (aDay.isBefore(startDate))
            return false;

        if (repetition.isException(aDay))
            return false;

        ChronoUnit freq = repetition.getFrequency();
        long steps = freq.between(startDate, aDay);

        if (steps < 0)
            return false;

        if (!startDate.plus(steps, freq).equals(aDay))
            return false;

        if (repetition.getTermination() != null) {
            Termination t = repetition.getTermination();

            if (aDay.isAfter(t.terminationDateInclusive()))
                return false;
        }

        return true;    }
   
    /**
     * @return the myTitle
     */
    public String getTitle() {
        return myTitle;
    }

    /**
     * @return the myStart
     */
    public LocalDateTime getStart() {
        return myStart;
    }


    /**
     * @return the myDuration
     */
    public Duration getDuration() {
        return myDuration;
    }

    @Override
    public String toString() {
        return "Event{title='%s', start=%s, duration=%s}".formatted(myTitle, myStart, myDuration);
    }
}
