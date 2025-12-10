package agenda;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Additional tests for Event class to achieve 100% coverage
 */
public class EventTest {

    LocalDate nov_1_2020 = LocalDate.of(2020, 11, 1);
    LocalDateTime nov_1_2020_22_30 = LocalDateTime.of(2020, 11, 1, 22, 30);
    Duration min_120 = Duration.ofMinutes(120);

    @Test
    public void testHasRepetitionReturnsFalseForSimpleEvent() {
        Event simple = new Event("Simple", nov_1_2020_22_30, min_120);
        assertFalse(simple.hasRepetition(),
            "Un événement simple ne doit pas avoir de répétition");
    }

    @Test
    public void testHasRepetitionReturnsTrueAfterSettingRepetition() {
        Event event = new Event("Repeating", nov_1_2020_22_30, min_120);
        event.setRepetition(ChronoUnit.DAYS);
        assertTrue(event.hasRepetition(),
            "Un événement doit avoir une répétition après l'avoir définie");
    }

    @Test
    public void testSetRepetitionDaily() {
        Event event = new Event("Daily", nov_1_2020_22_30, min_120);
        event.setRepetition(ChronoUnit.DAYS);
        assertTrue(event.hasRepetition());
    }

    @Test
    public void testSetRepetitionWeekly() {
        Event event = new Event("Weekly", nov_1_2020_22_30, min_120);
        event.setRepetition(ChronoUnit.WEEKS);
        assertTrue(event.hasRepetition());
    }

    @Test
    public void testSetRepetitionMonthly() {
        Event event = new Event("Monthly", nov_1_2020_22_30, min_120);
        event.setRepetition(ChronoUnit.MONTHS);
        assertTrue(event.hasRepetition());
    }

    @Test
    public void testAddExceptionToSimpleEventDoesNothing() {
        Event simple = new Event("Simple", nov_1_2020_22_30, min_120);
        simple.addException(nov_1_2020.plusDays(5));
        // Should not throw exception, just do nothing
        assertFalse(simple.hasRepetition());
    }

    @Test
    public void testAddExceptionToRepetitiveEvent() {
        Event event = new Event("Repeating", nov_1_2020_22_30, min_120);
        event.setRepetition(ChronoUnit.DAYS);
        event.addException(nov_1_2020.plusDays(5));
        
        assertTrue(event.isInDay(nov_1_2020.plusDays(4)));
        assertFalse(event.isInDay(nov_1_2020.plusDays(5)),
            "L'événement ne doit pas se produire le jour de l'exception");
        assertTrue(event.isInDay(nov_1_2020.plusDays(6)));
    }

    @Test
    public void testSetTerminationWithDateOnSimpleEvent() {
        Event simple = new Event("Simple", nov_1_2020_22_30, min_120);
        LocalDate terminationDate = LocalDate.of(2021, 1, 5);
        simple.setTermination(terminationDate);
        // Should not throw exception, just do nothing
        assertFalse(simple.hasRepetition());
    }

    @Test
    public void testSetTerminationWithOccurrencesOnSimpleEvent() {
        Event simple = new Event("Simple", nov_1_2020_22_30, min_120);
        simple.setTermination(10L);
        // Should not throw exception, just do nothing
        assertFalse(simple.hasRepetition());
    }

    @Test
    public void testGetNumberOfOccurrencesForSimpleEvent() {
        Event simple = new Event("Simple", nov_1_2020_22_30, min_120);
        assertEquals(Integer.MAX_VALUE, simple.getNumberOfOccurrences(),
            "Un événement simple sans répétition retourne MAX_VALUE");
    }

    @Test
    public void testGetNumberOfOccurrencesForRepetitiveEventWithoutTermination() {
        Event event = new Event("Never ending", nov_1_2020_22_30, min_120);
        event.setRepetition(ChronoUnit.DAYS);
        assertEquals(Integer.MAX_VALUE, event.getNumberOfOccurrences(),
            "Un événement répétitif sans terminaison retourne MAX_VALUE");
    }

    @Test
    public void testGetNumberOfOccurrencesForRepetitiveEventWithTermination() {
        Event event = new Event("Fixed", nov_1_2020_22_30, min_120);
        event.setRepetition(ChronoUnit.WEEKS);
        event.setTermination(5L);
        assertEquals(5, event.getNumberOfOccurrences(),
            "Un événement avec terminaison retourne le bon nombre d'occurrences");
    }

    @Test
    public void testGetTerminationDateForSimpleEvent() {
        Event simple = new Event("Simple", nov_1_2020_22_30, min_120);
        assertNull(simple.getTerminationDate(),
            "Un événement simple sans répétition retourne null");
    }

    @Test
    public void testGetTerminationDateForRepetitiveEventWithoutTermination() {
        Event event = new Event("Never ending", nov_1_2020_22_30, min_120);
        event.setRepetition(ChronoUnit.DAYS);
        assertNull(event.getTerminationDate(),
            "Un événement répétitif sans terminaison retourne null");
    }

    @Test
    public void testGetTerminationDateForRepetitiveEventWithTermination() {
        Event event = new Event("Fixed", nov_1_2020_22_30, min_120);
        event.setRepetition(ChronoUnit.WEEKS);
        LocalDate terminationDate = LocalDate.of(2021, 1, 5);
        event.setTermination(terminationDate);
        assertEquals(terminationDate, event.getTerminationDate(),
            "Un événement avec terminaison retourne la bonne date");
    }

    @Test
    public void testGetTitle() {
        String title = "My Event";
        Event event = new Event(title, nov_1_2020_22_30, min_120);
        assertEquals(title, event.getTitle(),
            "getTitle() doit retourner le titre de l'événement");
    }

    @Test
    public void testGetStart() {
        Event event = new Event("Event", nov_1_2020_22_30, min_120);
        assertEquals(nov_1_2020_22_30, event.getStart(),
            "getStart() doit retourner la date de début");
    }

    @Test
    public void testGetDuration() {
        Event event = new Event("Event", nov_1_2020_22_30, min_120);
        assertEquals(min_120, event.getDuration(),
            "getDuration() doit retourner la durée");
    }

    @Test
    public void testIsInDayForRepetitiveEventNotOnFrequency() {
        Event event = new Event("Weekly", LocalDateTime.of(2020, 11, 1, 10, 0), Duration.ofMinutes(60));
        event.setRepetition(ChronoUnit.WEEKS);
        
        assertTrue(event.isInDay(LocalDate.of(2020, 11, 1)),
            "L'événement est présent le jour de début");
        assertFalse(event.isInDay(LocalDate.of(2020, 11, 2)),
            "L'événement n'est pas présent un jour qui ne correspond pas à la fréquence");
        assertTrue(event.isInDay(LocalDate.of(2020, 11, 8)),
            "L'événement est présent une semaine après");
        assertFalse(event.isInDay(LocalDate.of(2020, 11, 9)),
            "L'événement n'est pas présent un jour qui ne correspond pas à la fréquence");
    }

    @Test
    public void testIsInDayForMonthlyRepetition() {
        Event event = new Event("Monthly", LocalDateTime.of(2020, 11, 1, 10, 0), Duration.ofMinutes(60));
        event.setRepetition(ChronoUnit.MONTHS);
        
        assertTrue(event.isInDay(LocalDate.of(2020, 11, 1)));
        assertTrue(event.isInDay(LocalDate.of(2020, 12, 1)));
        assertTrue(event.isInDay(LocalDate.of(2021, 1, 1)));
        assertFalse(event.isInDay(LocalDate.of(2020, 11, 2)));
    }

    @Test
    public void testToStringContainsStartAndDuration() {
        Event event = new Event("Test", nov_1_2020_22_30, min_120);
        String str = event.toString();
        assertTrue(str.contains("Test"), "toString() doit contenir le titre");
        assertTrue(str.contains("2020-11-01"), "toString() doit contenir la date de début");
    }

    @Test
    public void testRepetitiveEventAfterTerminationDate() {
        Event event = new Event("Limited", nov_1_2020_22_30, min_120);
        event.setRepetition(ChronoUnit.DAYS);
        LocalDate terminationDate = LocalDate.of(2020, 11, 10);
        event.setTermination(terminationDate);
        
        assertTrue(event.isInDay(LocalDate.of(2020, 11, 10)),
            "L'événement doit se produire le jour de terminaison");
        assertFalse(event.isInDay(LocalDate.of(2020, 11, 11)),
            "L'événement ne doit pas se produire après la date de terminaison");
        assertFalse(event.isInDay(LocalDate.of(2020, 11, 15)),
            "L'événement ne doit pas se produire après la date de terminaison");
    }

    @Test
    public void testSimpleEventSpanningMultipleDays() {
        LocalDateTime startTime = LocalDateTime.of(2020, 11, 1, 20, 0);
        Duration longDuration = Duration.ofHours(30);
        Event event = new Event("Long event", startTime, longDuration);
        
        assertTrue(event.isInDay(LocalDate.of(2020, 11, 1)),
            "L'événement doit être présent le premier jour");
        assertTrue(event.isInDay(LocalDate.of(2020, 11, 2)),
            "L'événement doit être présent le deuxième jour (20h + 30h = 2h le jour 3)");
        assertTrue(event.isInDay(LocalDate.of(2020, 11, 3)),
            "L'événement doit être présent le troisième jour (se termine à 2h)");
        assertFalse(event.isInDay(LocalDate.of(2020, 11, 4)),
            "L'événement ne doit pas être présent le quatrième jour");
    }

    @Test
    public void testRepetitiveEventAtStartDate() {
        Event event = new Event("Daily", nov_1_2020_22_30, min_120);
        event.setRepetition(ChronoUnit.DAYS);
    
        assertTrue(event.isInDay(nov_1_2020),
            "L'événement doit se produire à sa date de début (steps = 0)");
    }
}
