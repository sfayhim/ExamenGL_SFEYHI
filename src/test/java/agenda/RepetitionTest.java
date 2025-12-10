package agenda;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Repetition class
 */
public class RepetitionTest {

    LocalDate startDate = LocalDate.of(2020, 11, 1);
    LocalDate terminationDate = LocalDate.of(2021, 1, 5);
    
    Repetition dailyRepetition;
    Repetition weeklyRepetition;
    Repetition monthlyRepetition;

    @BeforeEach
    void setUp() {
        dailyRepetition = new Repetition(ChronoUnit.DAYS);
        weeklyRepetition = new Repetition(ChronoUnit.WEEKS);
        monthlyRepetition = new Repetition(ChronoUnit.MONTHS);
    }

    @Test
    public void testGetFrequencyDaily() {
        assertEquals(ChronoUnit.DAYS, dailyRepetition.getFrequency(),
            "La fréquence doit être DAYS");
    }

    @Test
    public void testGetFrequencyWeekly() {
        assertEquals(ChronoUnit.WEEKS, weeklyRepetition.getFrequency(),
            "La fréquence doit être WEEKS");
    }

    @Test
    public void testGetFrequencyMonthly() {
        assertEquals(ChronoUnit.MONTHS, monthlyRepetition.getFrequency(),
            "La fréquence doit être MONTHS");
    }

    @Test
    public void testAddException() {
        LocalDate exceptionDate = LocalDate.of(2020, 11, 5);
        dailyRepetition.addException(exceptionDate);
        assertTrue(dailyRepetition.isException(exceptionDate),
            "La date ajoutée doit être une exception");
    }

    @Test
    public void testIsExceptionReturnsFalseForNonException() {
        LocalDate normalDate = LocalDate.of(2020, 11, 5);
        assertFalse(dailyRepetition.isException(normalDate),
            "Une date non ajoutée ne doit pas être une exception");
    }

    @Test
    public void testMultipleExceptions() {
        LocalDate exception1 = LocalDate.of(2020, 11, 5);
        LocalDate exception2 = LocalDate.of(2020, 11, 10);
        LocalDate exception3 = LocalDate.of(2020, 11, 15);
        
        dailyRepetition.addException(exception1);
        dailyRepetition.addException(exception2);
        dailyRepetition.addException(exception3);
        
        assertTrue(dailyRepetition.isException(exception1));
        assertTrue(dailyRepetition.isException(exception2));
        assertTrue(dailyRepetition.isException(exception3));
        assertFalse(dailyRepetition.isException(LocalDate.of(2020, 11, 6)));
    }

    @Test
    public void testSetTermination() {
        Termination termination = new Termination(startDate, ChronoUnit.WEEKS, terminationDate);
        weeklyRepetition.setTermination(termination);
        
        assertNotNull(weeklyRepetition.getTermination(),
            "La terminaison ne doit pas être null après l'avoir définie");
        assertEquals(termination, weeklyRepetition.getTermination(),
            "La terminaison doit être celle qui a été définie");
    }

    @Test
    public void testGetTerminationWhenNull() {
        assertNull(dailyRepetition.getTermination(),
            "La terminaison doit être null si elle n'a pas été définie");
    }

    @Test
    public void testSetTerminationWithNumberOfOccurrences() {
        Termination termination = new Termination(startDate, ChronoUnit.DAYS, 10L);
        dailyRepetition.setTermination(termination);
        
        assertNotNull(dailyRepetition.getTermination());
        assertEquals(10L, dailyRepetition.getTermination().numberOfOccurrences());
    }
}
