package source;

import java.time.LocalDate;

/**
 * Represents an appointment that occurs only once on a specific date.
 */
public class OnetimeAppointment extends Appointment {

    /**
     * Constructs a new OnetimeAppointment with the specified description and date.
     * The start and end date are the same for a one-time appointment.
     *
     * @param description A brief description of the appointment.
     * @param startDate   The date of the appointment.
     */
    public OnetimeAppointment(String description, LocalDate startDate) {
        super(description, startDate, startDate);
    }


    /**
     * Returns a string for the type of appointment.
     *
     * @return A string for the type of appointment, One Time for this subclass.
     */
    @Override
    public String getAppointmentType() {
        return "One Time";
    }

    /**
     * Determines whether the appointment occurs on the specified date.
     *
     * @param date The date to check.
     * @return {@code true} if the specified date matches the appointment date; {@code false} otherwise.
     */
    @Override
    public boolean occursOn(LocalDate date) {
        return date.equals(getStartDate());
    }

    /**
     * Returns a string representation of this OnetimeAppointment.
     *
     * @return A string that includes the appointment's description and date,
     * prefixed by "Onetime Appointment-".
     */
    @Override
    public String toString() {
        return "Onetime Appointment-" + super.toString();
    }
}
