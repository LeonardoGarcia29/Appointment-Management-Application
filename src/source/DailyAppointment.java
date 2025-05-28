package source;

import java.time.LocalDate;

/**
 * Represents an appointment that occurs daily within a specified date range.
 */
public class DailyAppointment extends Appointment {

    /**
     * Constructs a new DailyAppointment with the specified description, start date, and end date.
     *
     * @param description A brief description of the appointment.
     * @param startDate   The start date of the appointment.
     * @param endDate     The end date of the appointment.
     */
    public DailyAppointment(String description, LocalDate startDate, LocalDate endDate) {
        super(description, startDate, endDate);
    }

    /**
     * Returns a string for the type of appointment.
     *
     * @return A string for the type of appointment, Daily for this subclass.
     */
    @Override
    public String getAppointmentType() {
        return "Daily";
    }

    /**
     * Determines whether the appointment occurs on the specified date.
     *
     * @param date The date to check.
     * @return {@code true} if the specified date is between the start and end dates (inclusive); {@code false} otherwise.
     */
    @Override
    public boolean occursOn(LocalDate date) {
        return super.inBetween(date);
    }

    /**
     * Returns a string representation of this DailyAppointment.
     *
     * @return A string that includes the appointment's description and date range,
     * prefixed by "Daily Appointment-".
     */
    @Override
    public String toString() {
        return "Daily Appointment-" + super.toString();
    }
}
