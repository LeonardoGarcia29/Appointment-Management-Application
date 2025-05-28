package CodeTesting;

import source.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.Assert.*;

public class AppointmentTest {
    private Appointment dailyAppointment;
    private Appointment onetimeAppointment;
    private Appointment monthlyAppointment;

    @Before
    //needed to change this since appointment class is no longer onstantiated but can be subclassed.
    public void setup() {
        dailyAppointment = new DailyAppointment("Daily Test", LocalDate.of(2024, 1, 10), LocalDate.of(2024, 1, 20));
        onetimeAppointment = new OnetimeAppointment("One-time Test", LocalDate.of(2024, 1, 15));
        monthlyAppointment = new MonthlyAppointment("Monthly Test", LocalDate.of(2024, 1, 10), LocalDate.of(2024, 3, 10));
    }

    // Original Tests (now applied to DailyAppointment)
    @Test
    public void testOccursBeforeStartDate() {
        LocalDate testDate = LocalDate.of(2024, 1, 9);
        boolean result = dailyAppointment.occursOn(testDate);
        assertEquals(false, result);
    }

    @Test
    public void testOccursOnStartDate() {
        LocalDate testDate = LocalDate.of(2024, 1, 10);
        boolean result = dailyAppointment.occursOn(testDate);
        assertEquals(true, result);
    }

    @Test
    public void testOccursBetweenStartAndEndDate() {
        LocalDate testDate = LocalDate.of(2024, 1, 15);
        boolean result = dailyAppointment.occursOn(testDate);
        assertEquals(true, result);
    }

    @Test
    public void testOccursOnEndDate() {
        LocalDate testDate = LocalDate.of(2024, 1, 20);
        boolean result = dailyAppointment.occursOn(testDate);
        assertEquals(true, result);
    }

    @Test
    public void testOccursAfterEndDate() {
        LocalDate testDate = LocalDate.of(2024, 1, 21);
        boolean result = dailyAppointment.occursOn(testDate);
        assertEquals(false, result);
    }

    // Additional Tests for OnetimeAppointment
    @Test
    public void testOnetimeAppointmentConstructor() {
        assertEquals(onetimeAppointment.getStartDate(), onetimeAppointment.getEndDate());
    }

    @Test
    public void testOnetimeAppointmentOccursOn() {
        LocalDate testDate = LocalDate.of(2024, 1, 15);
        assertTrue(onetimeAppointment.occursOn(testDate));
    }

    @Test
    public void testOnetimeAppointmentDoesNotOccurOnDifferentDate() {
        LocalDate testDate = LocalDate.of(2024, 1, 16);
        assertFalse(onetimeAppointment.occursOn(testDate));
    }

    // Additional Tests for MonthlyAppointment
    @Test
    public void testMonthlyAppointmentOccursOnSameDayOfMonth() {
        LocalDate testDate = LocalDate.of(2024, 2, 10);
        assertTrue(monthlyAppointment.occursOn(testDate));
    }

    @Test
    public void testMonthlyAppointmentDoesNotOccurOnDifferentDayOfMonth() {
        LocalDate testDate = LocalDate.of(2024, 2, 15);
        assertFalse(monthlyAppointment.occursOn(testDate));
    }

    // Test equals method for Appointment
    @Test
    public void testAppointmentsAreEqual() {
        Appointment anotherOnetimeAppointment = new OnetimeAppointment("One-time Test", LocalDate.of(2024, 1, 15));
        assertEquals(onetimeAppointment, anotherOnetimeAppointment);
    }

    @Test
    public void testAppointmentsAreNotEqual() {
        Appointment differentOnetimeAppointment = new OnetimeAppointment("Different Test", LocalDate.of(2024, 1, 15));
        assertNotEquals(onetimeAppointment, differentOnetimeAppointment);
    }

    // Test sorting of appointments
    @Test
    public void testAppointmentSorting() {
        Appointment earlierAppointment = new DailyAppointment("Early Test", LocalDate.of(2024, 1, 5), LocalDate.of(2024, 1, 10));
        Appointment laterAppointment = new DailyAppointment("Late Test", LocalDate.of(2024, 1, 15), LocalDate.of(2024, 1, 20));

        Appointment[] appointments = {laterAppointment, onetimeAppointment, dailyAppointment, earlierAppointment};
        Arrays.sort(appointments);

        assertArrayEquals(new Appointment[]{earlierAppointment, dailyAppointment, onetimeAppointment, laterAppointment}, appointments);
    }

    @Test
    public void testAddAppointment() {
        Appointment a1 = new DailyAppointment("a1", LocalDate.of(2024, 1, 5), LocalDate.of(2024, 1, 10));
        Appointment a2 = new DailyAppointment("a2", LocalDate.of(2024, 1, 5), LocalDate.of(2024, 1, 10));
        Appointment a3 = new DailyAppointment("a3", LocalDate.of(2024, 1, 5), LocalDate.of(2024, 1, 10));

        AppointmentManager e1 = new AppointmentManager();

        e1.add(a1);
        e1.add(a2);
        e1.add(a3);


    }
}