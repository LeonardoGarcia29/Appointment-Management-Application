package source;

import java.time.LocalDate;

/**
 * Represents an appointment that occurs monthly on a specific day of the month.
 * The appointment is valid for dates between the start and end date and recurs on the same day of the month.
 */
public class MonthlyAppointment extends Appointment {

    /**
     * Constructs a new MonthlyAppointment with the specified description, start date, and end date.
     *
     * @param description A brief description of the appointment.
     * @param startDate   The start date of the appointment.
     * @param endDate     The end date of the appointment.
     */
    public MonthlyAppointment(String description, LocalDate startDate, LocalDate endDate) {
        super(description, startDate, endDate);
    }

    /**
     * Returns a string for the type of appointment.
     *
     * @return A string for the type of appointment, Monthly for this subclass.
     */
    @Override
    public String getAppointmentType() {
        return "Monthly";
    }

    /**
     * Determines whether the appointment occurs on the specified date.
     *
     * @param date The date to check.
     * @return {@code true} if the specified date is between the start and end dates
     * and falls on the same day of the month as the start date; {@code false} otherwise.
     */
    @Override
    public boolean occursOn(LocalDate date) {
        return super.inBetween(date) && date.getDayOfMonth() == getStartDate().getDayOfMonth();
    }

    /**
     * Returns a string representation of this MonthlyAppointment.
     *
     * @return A string that includes the appointment's description and date range,
     * prefixed by "MonthlyAppointment-".
     */
    @Override
    public String toString() {
        return "MonthlyAppointment- " + super.toString();
    }
}
