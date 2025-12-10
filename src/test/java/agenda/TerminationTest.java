package agenda;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Termination class
 */
public class TerminationTest {

    LocalDate startDate = LocalDate.of(2020, 11, 1);
    LocalDate terminationDate = LocalDate.of(2021, 1, 5);

    @Test
    public void testConstructorWithTerminationDate() {
        Termination termination = new Termination(startDate, ChronoUnit.WEEKS, terminationDate);
        
        assertEquals(terminationDate, termination.terminationDateInclusive(),
            "La date de terminaison doit correspondre");
        assertEquals(10L, termination.numberOfOccurrences(),
            "Le nombre d'occurrences doit être calculé correctement");
    }

    @Test
    public void testConstructorWithNumberOfOccurrences() {
        long occurrences = 10L;
        Termination termination = new Termination(startDate, ChronoUnit.WEEKS, occurrences);
        
        assertEquals(occurrences, termination.numberOfOccurrences(),
            "Le nombre d'occurrences doit correspondre");
        assertEquals(LocalDate.of(2021, 1, 3), termination.terminationDateInclusive(),
            "La date de terminaison doit être calculée correctement");
    }

    @Test
    public void testDailyRepetitionTermination() {
        long occurrences = 5L;
        Termination termination = new Termination(startDate, ChronoUnit.DAYS, occurrences);
        
        assertEquals(5L, termination.numberOfOccurrences());
        assertEquals(startDate.plusDays(4), termination.terminationDateInclusive(),
            "Pour 5 occurrences quotidiennes, la date de fin est J+4");
    }

    @Test
    public void testMonthlyRepetitionTermination() {
        long occurrences = 3L;
        Termination termination = new Termination(startDate, ChronoUnit.MONTHS, occurrences);
        
        assertEquals(3L, termination.numberOfOccurrences());
        assertEquals(startDate.plusMonths(2), termination.terminationDateInclusive(),
            "Pour 3 occurrences mensuelles, la date de fin est M+2");
    }

    @Test
    public void testSingleOccurrenceTermination() {
        Termination termination = new Termination(startDate, ChronoUnit.DAYS, 1L);
        
        assertEquals(1L, termination.numberOfOccurrences());
        assertEquals(startDate, termination.terminationDateInclusive(),
            "Pour 1 seule occurrence, la date de fin est la date de début");
    }

    @Test
    public void testTerminationDateCalculationDaily() {
        LocalDate endDate = startDate.plusDays(10);
        Termination termination = new Termination(startDate, ChronoUnit.DAYS, endDate);
        
        assertEquals(11L, termination.numberOfOccurrences(),
            "Du 1er au 11, il y a 11 jours (inclus)");
    }

    @Test
    public void testTerminationDateCalculationWeekly() {
        LocalDate endDate = startDate.plusWeeks(5);
        Termination termination = new Termination(startDate, ChronoUnit.WEEKS, endDate);
        
        assertEquals(6L, termination.numberOfOccurrences(),
            "6 semaines incluses");
    }

    @Test
    public void testTerminationDateCalculationMonthly() {
        LocalDate endDate = startDate.plusMonths(3);
        Termination termination = new Termination(startDate, ChronoUnit.MONTHS, endDate);
        
        assertEquals(4L, termination.numberOfOccurrences(),
            "4 mois inclus");
    }

    @Test
    public void testTerminationDateInclusiveGetter() {
        Termination termination = new Termination(startDate, ChronoUnit.WEEKS, terminationDate);
        
        LocalDate result = termination.terminationDateInclusive();
        assertEquals(terminationDate, result,
            "terminationDateInclusive() doit retourner la date de terminaison");
    }

    @Test
    public void testNumberOfOccurrencesGetter() {
        Termination termination = new Termination(startDate, ChronoUnit.DAYS, 25L);
        
        long result = termination.numberOfOccurrences();
        assertEquals(25L, result,
            "numberOfOccurrences() doit retourner le nombre d'occurrences");
    }
}
